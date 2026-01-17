package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */
import java.io.IOException;
import java.nio.charset.Charset;
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
		if (debug) {
			System.err.println("Done: " + operation);
		}
	}

	public static byte[] convertBytes(byte[] input, Charset source, Charset target) {

		String unicode = new String(input, source);
		return unicode.getBytes(target);
	}

	public static byte[] convertString(String input, Charset source, Charset target) {
		return convertBytes(input.getBytes(source), source, target);
	}

	public static String byteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	public static byte[] hexToByteArray(String hex) {
		hex = hex.replaceAll("[^0-9A-Fa-f]", "");
		if ((hex.length() & 1) != 0) {
			throw new IllegalArgumentException("Odd-length hex string");
		}

		byte[] data = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			data[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
		}
		return data;
	}

	private static void encodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
			throws IOException {
		byte[] input;
		if (inputFile != null)
			input = Files.readAllBytes(Path.of(inputFile));
		else
			input = data.getBytes(StandardCharsets.US_ASCII);
		byte[] converted = convertBytes(input, source, target);
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

}
