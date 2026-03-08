package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
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

	// predicates
	private static IntPredicate isValidChar = charCode -> charCode == 0x40 || (charCode >= 0xF0 && charCode <= 0xF9)
			|| (charCode >= 0xC1 && charCode <= 0xC9) || (charCode >= 0xD1 && charCode <= 0xD9)
			|| (charCode >= 0xE2 && charCode <= 0xE9) || (charCode >= 0x81 && charCode <= 0x89)
			|| (charCode >= 0x91 && charCode <= 0x99) || (charCode >= 0xA2 && charCode <= 0xA9)
			|| (charCode >= 0x4A && charCode <= 0x6F);

	private static IntPredicate isAsciiValidChar = charCode -> charCode >= 0x20 && charCode <= 0x7E;

	// generic validator: strict or threshold,
	// WARNING: using arguments to guide the logic - need to use boxed double
	// null = strict mode
	public static ValidationResult validateGeneric(final byte[] data, final String codePage, final Charset decoder,
			final IntPredicate rangeValidator, final Double threshold) {
		boolean status = true;
		String message = null;
		int validCount = 0;
		// Strict mode if threshold is null
		boolean strict = (threshold == null);

		// optional decoder check
		if (decoder != null) {
			try {
				decoder.newDecoder().decode(ByteBuffer.wrap(data));
			} catch (CharacterCodingException e) {
				status = false;
				message = String.format("failed to decode in code page %s: %s", decoder.name(), e.getMessage());
				return new ValidationResult(status, message);
			}
		}

		if (rangeValidator != null) {
			for (int i = 0; i < data.length; i++) {
				int charCode = data[i] & 0xFF;
				if (charCode == 0) {
					status = false;
					if (message == null)
						message = String.format("null character at position %d", i);
				}

				boolean valid = rangeValidator.test(charCode);
				if (valid) {
					validCount++;
				}

				if (!valid && strict) {
					status = false;
					if (message == null) {
						message = String.format("invalid character 0x%02X at position %d", charCode, i);
					}
				}
			}

			if (!strict) {
				double ratio = (double) validCount / data.length;
				if (ratio < threshold) {
					status = false;
					message = String.format("valid byte ratio %.2f below threshold %.2f", ratio, threshold);
				}
			}
		}

		return new ValidationResult(status, message);
	}

	// strict validators
	public static ValidationResult validateASCII(byte[] data) {
		return validateGeneric(data, "ASCII", null, isAsciiValidChar, null);
	}

	public static ValidationResult validateASCII(byte[] data, double threshold) {
		return validateGeneric(data, "ASCII", null, isAsciiValidChar, threshold);
	}

	public static ValidationResult validateEBCDIC(byte[] data) {
		return validateGeneric(data, "CP1047", Charset.forName("CP1047"), isValidChar, null);
	}

	public static ValidationResult validateEBCDIC(byte[] data, double threshold) {
		return validateGeneric(data, "CP1047", Charset.forName("CP1047"), isValidChar, threshold);
	}

	public static ValidationResult validateUTF8(byte[] data) {
		return validateGeneric(data, "UTF-8", StandardCharsets.UTF_8, null, null);
	}

}