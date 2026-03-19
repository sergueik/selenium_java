package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {
	
	// origin: 2b623f329c45753046d5cfe0025183bbf53f57ac

	private static final Logger log = LoggerFactory.getLogger(Converter.class);

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

	public static void encodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
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

	public static void decodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
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