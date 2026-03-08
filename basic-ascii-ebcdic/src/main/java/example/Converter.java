package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.function.IntPredicate;

import example.CommandLineParser;

public class Converter {
	private static boolean debug = false;
	private static String codepage = "CP1047";
	private static CommandLineParser commandLineParser;

	public static void main(String[] args) throws IOException {

		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("inputfile");
		commandLineParser.saveFlagValue("data");
		commandLineParser.saveFlagValue("codepage");
		commandLineParser.saveFlagValue("outputfile");
		commandLineParser.saveFlagValue("operation");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		if (commandLineParser.hasFlag("help")) {
			System.err.println(String.format(
					"Usage: %s -operation=[encode|decode] -data <string> -inputfile <filename> -outputfile <filename> -codepage <codepage>",
					"jar"));
			return;
		}
		String data = commandLineParser.getFlagValue("data");
		String outputFile = commandLineParser.getFlagValue("outputfile");
		String inputFile = commandLineParser.getFlagValue("inputfile");
		String operation = commandLineParser.getFlagValue("operation");
		if (commandLineParser.hasFlag("codepage"))
			codepage = commandLineParser.getFlagValue("codepage");

		if (operation == null) {
			System.err.println("Missing required argument: operation");
			return;
		}

		if (operation.equalsIgnoreCase("encode")) {
			encodeFile(inputFile, outputFile, data, StandardCharsets.US_ASCII, Charset.forName(codepage));
		}

		if (operation.equalsIgnoreCase("decode")) {
			decodeFile(inputFile, outputFile, data, Charset.forName(codepage), StandardCharsets.US_ASCII);
		}

		if (operation.equalsIgnoreCase("validate")) {
			Charset charset = codepage == null ? StandardCharsets.US_ASCII
					: (codepage.equalsIgnoreCase("ebcdic") || codepage.equalsIgnoreCase("cp037"))
							? Charset.forName("CP1047")
							: (codepage.equalsIgnoreCase("us-ascii") || codepage.equalsIgnoreCase("ascii"))
									? StandardCharsets.US_ASCII
									: Charset.forName(codepage);
			validate(inputFile, data, charset);
		}
		if (debug) {
			System.err.println("Done: " + operation);
		}
	}

	public static byte[] convertBytes(byte[] input, Charset sourceCharset, Charset targetCharset) {

		String unicode = new String(input, sourceCharset);
		return unicode.getBytes(targetCharset);
	}

	public static byte[] convertString(String input, Charset sourceCharset, Charset targetCharset) {
		return convertBytes(input.getBytes(sourceCharset), sourceCharset, targetCharset);
	}

	public static String byteArrayToHex(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}
		return stringBuilder.toString();
	}

	public static byte[] hexToByteArray(String hexString) {
		if (debug)
			System.err.println("hexString " + hexString);

		// deal with dash or whitespace formatted hex strings
		hexString = hexString.replaceAll("[^0-9A-Fa-f]", "");
		if ((hexString.length() & 1) != 0) {
			throw new IllegalArgumentException("Odd-length hex string");
		}

		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			bytes[i / 2] = (byte) Integer.parseInt(hexString.substring(i, i + 2), 16);
		}
		if (debug)
			System.err.println(String.format("Read %d bytes", bytes.length));
		return bytes;
	}

	private static void encodeFile(String inputFile, String outputFile, String data, Charset sourceCharset,
			Charset targetCharset) throws IOException {
		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile))
				: data.getBytes(StandardCharsets.US_ASCII); // console
		byte[] converted = convertBytes(input, sourceCharset, targetCharset);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		System.out.println(byteArrayToHex(converted));
	}

	private static void decodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
			throws IOException {

		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile)) : hexToByteArray(data); // console

		byte[] converted = convertBytes(input, source, target);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		// Console-safe
		System.out.println(new String(converted, target));
	}

	// NOTE map may be over-engineering if one only handles 3 charmaps:
	/*
	 * private static final Map<Charset, Function<byte[], ValidationResult>>
	 * VALIDATORS = new HashMap<>();
	 * 
	 * static { VALIDATORS.put(StandardCharsets.US_ASCII, Convertor::validateASCII);
	 * VALIDATORS.put(StandardCharsets.UTF_8, Convertor::validateUTF8); }
	 */
	enum Validator {
		ASCII {
			ValidationResult validate(byte[] data) {
				return validateASCII(data);
			}
		},
		UTF8 {
			ValidationResult validate(byte[] data) {
				return validateUTF8(data);
			}
		},
		EBCDIC {
			ValidationResult validate(byte[] data) {
				return validateEBCDIC(data);
			}
		};

		abstract ValidationResult validate(byte[] data);
	}

	private static void validate(String inputFile, String data, Charset charset) throws IOException {
		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile)) : hexToByteArray(data); // console

		Validator validator = (charset == StandardCharsets.US_ASCII) ? Validator.ASCII
				: (charset == StandardCharsets.UTF_8) ? Validator.UTF8 : Validator.EBCDIC;

		ValidationResult validationResult = validator.validate(input);
		System.err.println(validationResult.isValid() ? "valid" : "invalid");
		if (debug)
			System.err.println(validationResult.getMessage());

	}

	private static ValidationResult validateUTF8(byte[] data) {
		boolean status = false;
		String message = null;
		try {
			StandardCharsets.UTF_8.newDecoder().decode(ByteBuffer.wrap(data));
			status = true;
		} catch (CharacterCodingException e) {
			message = String.format("invalid: %s", e.getMessage());
		}
		return new ValidationResult(status, message);
	}

	private static IntPredicate isValidChar = (int charCode) ->
	// space
	charCode == 0x40 ||
	// digits
			(charCode >= 0xF0 && charCode <= 0xF9) ||
			// uppercase
			(charCode >= 0xC1 && charCode <= 0xC9) || (charCode >= 0xD1 && charCode <= 0xD9)
			|| (charCode >= 0xE2 && charCode <= 0xE9) ||
			// lowercase
			(charCode >= 0x81 && charCode <= 0x89) || (charCode >= 0x91 && charCode <= 0x99)
			|| (charCode >= 0xA2 && charCode <= 0xA9) ||
			// basic punctuation
			(charCode >= 0x4A && charCode <= 0x6F);

	// ASCII predicate: 7-bit printable region is continuous
	// tilde
	private static IntPredicate isAsciiValidChar = charCode -> charCode >= 0x20 && charCode <= 0x7E;

	// strict ASCII validator (trusted)
	public static ValidationResult validateASCII(byte[] data) {
		boolean status = true;
		String message = null;
		// valid 7-bit ASCII range probing
		for (int cnt = 0; cnt != data.length; cnt++) {
			int charCode = data[cnt] & 0xFF; // unsigned
			if (!isAsciiValidChar.test(charCode)) {
				status = false;
				message = String.format("invalid US-ASCII character 0x%02X on %d", charCode, cnt);
			}
		}
		return new ValidationResult(status, message);
	}

	// new threshold tolerance-based ASCII validator
	public static ValidationResult validateASCII(byte[] data, double threshold) {
		boolean status = true;
		String message = null;
		int validCount = 0;

		for (int i = 0; i < data.length; i++) {
			int charCode = data[i] & 0xFF;
			if (isAsciiValidChar.test(charCode)) {
				validCount++;
			}
		}

		double ratio = (double) validCount / data.length;
		if (ratio < threshold) {
			status = false;
			message = String.format("valid ASCII byte ratio %.2f below threshold %.2f", ratio, threshold);
		}

		return new ValidationResult(status, message);
	}

	// minimum valid byte ratio

	public static ValidationResult validateEBCDIC(byte[] data, double threshold) {
		boolean status = false;
		String message = null;

		try {
			// quick decode check
			Charset.forName("CP1047").newDecoder().decode(ByteBuffer.wrap(data));
			status = true;

			// picture range predicate
			int validCount = 0;
			for (int i = 0; i < data.length; i++) {
				int charCode = data[i] & 0xFF; // unsigned
				if (charCode == 0) {
					status = false;
					message = String.format("null character on %d", i);
				}
				if (isValidChar.test(charCode))
					validCount++;
			}

			// compute ratio
			double ratio = (double) validCount / data.length;
			if (ratio < threshold) {
				status = false;
				message = String.format("valid byte ratio %.2f below threshold %.2f", ratio, threshold);
			}

		} catch (CharacterCodingException e) {
			message = String.format("invalid: %s", e.getMessage());
			status = false;
		}

		return new ValidationResult(status, message);
	}

	// strict validator
	public static ValidationResult validateEBCDIC(byte[] data) {
		boolean status = false;
		String message = null;
		try {
			Charset.forName("CP1047").newDecoder().decode(ByteBuffer.wrap(data));
			status = true;
			// EBCDIC picture range probing
			// EBCDIC isn’t contiguous like ASCII
			for (int cnt = 0; cnt != data.length; cnt++) {
				int charCode = data[cnt] & 0xFF; // unsigned
				if (charCode == 0) {
					status = false;
					message = String.format("null character on %d", cnt);
				}
				boolean valid = isValidChar.test(charCode);
				if (!valid)
					message = String.format("invalid EBCDIC character 0x%02X on %d", charCode, cnt);
				status &= valid;
			}
		} catch (CharacterCodingException e) {
			message = String.format("invalid: %s", e.getMessage());
		}
		return new ValidationResult(status, message);
	}

}
