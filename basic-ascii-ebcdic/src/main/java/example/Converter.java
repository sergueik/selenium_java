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
		byte[] input;
		if (inputFile != null)
			input = Files.readAllBytes(Path.of(inputFile));
		else
			input = data.getBytes(StandardCharsets.US_ASCII); // console
		byte[] converted = convertBytes(input, sourceCharset, targetCharset);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		System.out.println(byteArrayToHex(converted));
	}

	private static void decodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
			throws IOException {

		byte[] input;

		if (inputFile != null) {
			input = Files.readAllBytes(Path.of(inputFile));
		} else {
			input = hexToByteArray(data);
		}

		byte[] converted = convertBytes(input, source, target);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		// Console-safe
		System.out.println(new String(converted, target));
	}

	private static void validate(String inputFile, String data, Charset charset) throws IOException {
		byte[] input;
		if (inputFile != null)
			input = Files.readAllBytes(Path.of(inputFile));
		else
			input = hexToByteArray(data); // console
		ValidationResult validationResult = (charset == StandardCharsets.US_ASCII) ? validateAscii(input)
				: (charset == StandardCharsets.UTF_8) ? validateUtf8(input) : validateEbcdic(input);
		System.err.println(validationResult.isValid() ? "valid" : "invalid");
		if (debug)
			System.err.println(validationResult.getMessage());

	}

	private static ValidationResult validateUtf8(byte[] data) {
		boolean status = false;
		String message = null;
		try {
			CharsetDecoder characterDecoder = StandardCharsets.UTF_8.newDecoder();
			characterDecoder.decode(ByteBuffer.wrap(data));
			status = true;
		} catch (CharacterCodingException e) {
			message = String.format("invalid: %s", e.getMessage());
		}
		return new ValidationResult(status, message);
	}

	public static ValidationResult validateEbcdic(byte[] data) {
		boolean status = false;
		String message = null;
		try {
			CharsetDecoder characterDecoder = Charset.forName("CP1047").newDecoder();
			characterDecoder.decode(ByteBuffer.wrap(data));
			status = true;
			// range probing
			for (int cnt = 0; cnt != data.length; cnt++) {
				int b = data[cnt] & 0xFF;
				if (b == 0) {
					status = false;
					message = String.format("null character on %d", cnt);
				}
				boolean valid = b == 0x40 || // space
						(b >= 0xF0 && b <= 0xF9) || // digits

						// uppercase
						(b >= 0xC1 && b <= 0xC9) || (b >= 0xD1 && b <= 0xD9) || (b >= 0xE2 && b <= 0xE9) ||

						// lowercase
						(b >= 0x81 && b <= 0x89) || (b >= 0x91 && b <= 0x99) || (b >= 0xA2 && b <= 0xA9) ||

						// basic punctuation window
						(b >= 0x4A && b <= 0x6F);
				if (!valid)
					message = String.format("invalid EBCDIC character 0x%02X on %d", b, cnt);
				status &= valid;
			}
		} catch (CharacterCodingException e) {
			message = String.format("invalid: %s", e.getMessage());
		}
		return new ValidationResult(status, message);
	}

	// range probing
	private static ValidationResult validateAscii(byte[] data) {
		boolean status = true;
		String message = null;
		// range probing
		for (int cnt = 0; cnt != data.length; cnt++) {
			int b = data[cnt] & 0xFF;
			if (b > 127) {
				status = false;
				message = String.format("invalid US-ASCII character 0x%02X on %d", b, cnt);
			}
		}
		return new ValidationResult(status, message);
	}
}
