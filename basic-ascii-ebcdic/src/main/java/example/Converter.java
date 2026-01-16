package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.charset.CharacterCodingException;

import java.nio.charset.Charset;

import java.nio.charset.CharsetDecoder;

import java.nio.charset.CharsetEncoder;

import example.CommandLineParser;

public class Converter {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args) throws IOException {

		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("inputfile");
		commandLineParser.saveFlagValue("data");
		commandLineParser.saveFlagValue("outputfile");
		commandLineParser.saveFlagValue("operation");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String data = commandLineParser.getFlagValue("data");

		String outputFile = commandLineParser.getFlagValue("outputfile");
		if (outputFile == null) {
			System.err.println("Missing required argument: outputfile");
			return;
		}

		String inputFile = commandLineParser.getFlagValue("inputfile");
		String operation = commandLineParser.getFlagValue("operation");
		if (operation == null) {
			System.err.println("Missing required argument: operation");
			return;
		}

		if (operation.equalsIgnoreCase("encode")) {
			encodeFile(inputFile, outputFile, data, StandardCharsets.US_ASCII, Charset.forName("CP1047"));
		}

		if (operation.equalsIgnoreCase("decode")) {
			decodeFile(inputFile, outputFile, data, Charset.forName("CP037"), StandardCharsets.US_ASCII);
		}
		if (debug) {
			System.err.println("Done: " + operation);
		}
	}

	public static String Convert(String strToConvert, String in, String out) {
		try {
			System.out.println("Original String is: " + strToConvert);

			Charset charset_in = Charset.forName(out);
			System.out.println("charset_in is: " + charset_in.displayName());
			Charset charset_out = Charset.forName(in);
			System.out.println("charset_out is: " + charset_out.displayName());
			CharsetDecoder decoder = charset_out.newDecoder();

			CharsetEncoder encoder = charset_in.newEncoder();

			CharBuffer uCharBuffer = CharBuffer.wrap(strToConvert);
			System.out.println("uCharBuffer is : " + uCharBuffer.length());

			ByteBuffer bbuf = encoder.encode(uCharBuffer);
			System.out.println("bbuf is : " + bbuf.array().length);
			CharBuffer cbuf = decoder.decode(bbuf);
			System.out.println("cbuf is : " + cbuf.array().length);

			String s = cbuf.toString();

			System.out.println("Original String is: " + s);
			return s;

		} catch (CharacterCodingException e) {

			System.out.println("Character Coding Error: " + e.getMessage());
			return "";

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
		System.err.println(byteArrayToHex(converted));
	}

	private static void decodeFile(String inputFile, String outputFile,  String data, Charset source, Charset target)
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
