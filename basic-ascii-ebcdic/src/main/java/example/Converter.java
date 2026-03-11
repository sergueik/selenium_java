package example;
/**
 * Copyright 2026 Serguei Kouzmine
 */
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Map;
import java.util.function.IntPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {

	private static final Logger log = LoggerFactory.getLogger(Converter.class);

	// Immutable fields
	private final String codepage;
	private final String inputFile;
	private final String outputFile;
	private final Long threshold;
	private final byte[] data;

	private final IntPredicate rangeValidator;
	private final Charset charset;

	// Canonical codepage mapping
	private static final Map<String, String> CODEPAGE_ALIASES = Map.ofEntries(Map.entry("cp037", "CP1047"),
			Map.entry("cp1047", "CP1047"), Map.entry("ibm1047", "CP1047"), Map.entry("ascii", "ASCII"),
			Map.entry("us-ascii", "ASCII"), Map.entry("utf8", "UTF_8"), Map.entry("utf-8", "UTF_8"));

	private static final Map<String, IntPredicate> PREDICATES = Map.of("ASCII", c -> c >= 0x20 && c <= 0x7E, "CP1047",
			c -> c == 0x40 || (c >= 0xF0 && c <= 0xF9) || (c >= 0xC1 && c <= 0xC9) || (c >= 0xD1 && c <= 0xD9)
					|| (c >= 0xE2 && c <= 0xE9) || (c >= 0x81 && c <= 0x89) || (c >= 0x91 && c <= 0x99)
					|| (c >= 0xA2 && c <= 0xA9) || (c >= 0x4A && c <= 0x6F) || c == 0x3F || c == 0x45 || c == 0x49
					|| c == 0x7D || c == 0xCE || c == 0xDE || c == 0xD3 || c == 0xC7 || c == 0xE9 || c == 0xDC);

	/**
	 * Constructor: full immutable state
	 * 
	 * @param inputFile  may be null if using in-memory data
	 * @param outputFile may be null
	 * @param codepage   canonical codepage string
	 * @param threshold  may be null for strict validation
	 * @param data       optional byte array for memory input; can be null if
	 *                   reading from inputFile
	 */

	public Converter(String inputFile, String outputFile, String codepage, Long threshold, String data) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.threshold = threshold;

		this.codepage = CODEPAGE_ALIASES.getOrDefault(codepage.toLowerCase(), codepage.toUpperCase());
		this.rangeValidator = PREDICATES.get(this.codepage);
		this.charset = this.codepage.equals("UTF_8") ? StandardCharsets.UTF_8 : Charset.forName(this.codepage);

		// normalize data once
		this.data = (data != null) ? hexToByteArray(data) : null;
	}

	public byte[] convertBytes(byte[] input, Charset targetCharset) {

		String unicode = new String(input, charset);
		return unicode.getBytes(targetCharset);
	}

	public byte[] convertString(String input, Charset targetCharset) {
		return convertBytes(input.getBytes(charset), targetCharset);
	}

	public String byteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes)
			sb.append(String.format("%02X", b));
		return sb.toString();
	}

	public byte[] hexToByteArray(String hexString) {
		hexString = hexString.replaceAll("[^0-9A-Fa-f]", "");
		if ((hexString.length() & 1) != 0)
			throw new IllegalArgumentException("Odd-length hex string");
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2)
			bytes[i / 2] = (byte) Integer.parseInt(hexString.substring(i, i + 2), 16);
		return bytes;
	}

	/** Encode file or in-memory data using internal codepage */
	public void encodeFile() throws IOException {
		byte[] input = (data != null) ? data
				: (inputFile != null ? Files.readAllBytes(Path.of(inputFile)) : new byte[0]);
		Charset target = StandardCharsets.US_ASCII; // encode to ASCII
		byte[] converted = convertBytes(input, target);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		System.out.println(byteArrayToHex(converted));
	}

	/** Decode file or in-memory data using internal codepage */
	public void decodeFile() throws IOException {
		byte[] input = (data != null) ? data
				: (inputFile != null ? Files.readAllBytes(Path.of(inputFile)) : new byte[0]);
		Charset target = StandardCharsets.US_ASCII; // decode to ASCII
		byte[] converted = convertBytes(input, target);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		System.out.println(new String(converted, target));
	}

	@FunctionalInterface
	public interface DecoderFunction {
		CharBuffer apply(byte[] data) throws CharacterCodingException;
	}

	public DecoderFunction getDecoder() {
		if (codepage.contains("ASCII"))
			return null;
		return (byte[] d) -> charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(d));
	}

	/** Validate internal data; throws if data is null */
	public ValidationResult validateGeneric() {
		if (data == null)
			throw new IllegalArgumentException("Data cannot be null for validation");

		DecoderFunction decoder = getDecoder();
		IntPredicate rangeVal = this.rangeValidator;
		boolean status = true;
		String message = null;
		int validCount = 0;
		boolean strict = (threshold == null);

		if (decoder == null && rangeVal == null)
			throw new IllegalArgumentException("Decoder and validator both null");

		// Decoder step
		if (decoder != null) {
			try {
				decoder.apply(data);
			} catch (CharacterCodingException e) {
				status = false;
				message = String.format("failed to decode in code page %s: %s", codepage, e.getMessage());
				return new ValidationResult(status, message);
			}
		}

		// Range validation
		if (rangeVal != null) {
			for (int pos = 0; pos < data.length; pos++) {
				int c = data[pos] & 0xFF;
				if (c == 0) {
					status = false;
					if (message == null)
						message = String.format("null char at %d", pos);
				}
				boolean valid = rangeVal.test(c);
				if (valid)
					validCount++;
				else if (strict) {
					status = false;
					message = String.format("invalid code page %s char 0x%02X at %d", codepage, c, pos);
				}
			}
			if (!strict) {
				double ratio = (double) validCount / data.length;
				double thresholdRatio = 0.01 * threshold;
				if (ratio < thresholdRatio) {
					status = false;
					message = String.format("valid byte ratio %.2f below threshold %.2f for code page %s", ratio,
							thresholdRatio, codepage);
				}
			}
		}
		return new ValidationResult(status, message);
	}

}