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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.function.IntPredicate;
import java.util.Map;
import java.util.function.Function;

public class Converter {

	private final boolean debug = false;
	private static final Logger log = LoggerFactory.getLogger(Converter.class);

	private String codepage = "CP1047";
	private String inputfile;
	private String outputFile;
	private Long threshold = 90L;

	public Converter(String data, String inputfile, String outputFile, String codepage, Long threshold) {
		this.inputfile = inputfile;
		this.outputFile = outputFile;
		this.codepage = codepage;
		this.threshold = threshold;
	}

	public byte[] convertBytes(byte[] input, Charset sourceCharset, Charset targetCharset) {

		String unicode = new String(input, sourceCharset);
		return unicode.getBytes(targetCharset);
	}

	public byte[] convertString(String input, Charset sourceCharset, Charset targetCharset) {
		return convertBytes(input.getBytes(sourceCharset), sourceCharset, targetCharset);
	}

	public String byteArrayToHex(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}
		return stringBuilder.toString();
	}

	public byte[] hexToByteArray(String hexString) {
		log.debug("hexString: {}", hexString);

		// deal with dash or whitespace formatted hex strings
		hexString = hexString.replaceAll("[^0-9A-Fa-f]", "");
		if ((hexString.length() & 1) != 0) {
			throw new IllegalArgumentException("Odd-length hex string");
		}

		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			bytes[i / 2] = (byte) Integer.parseInt(hexString.substring(i, i + 2), 16);
		}
		log.debug("Read {} bytes", bytes.length);
		return bytes;
	}

	public void encodeFile(String inputFile, String outputFile, String data, Charset sourceCharset,
			Charset targetCharset) throws IOException {
		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile))
				: data.getBytes(StandardCharsets.US_ASCII); // console
		byte[] converted = convertBytes(input, sourceCharset, targetCharset);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		System.out.println(byteArrayToHex(converted));
	}

	public void decodeFile(String inputFile, String outputFile, String data, Charset source, Charset target)
			throws IOException {

		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile)) : hexToByteArray(data); // console

		byte[] converted = convertBytes(input, source, target);
		if (outputFile != null)
			Files.write(Path.of(outputFile), converted);
		// Console-safe
		System.out.println(new String(converted, target));
	}

	// NOTE: possible to use Charset.availableCharsets()
	private static final Map<String, String> CODEPAGE_ALIASES = Map.ofEntries(Map.entry("cp037", "CP1047"),
			Map.entry("cp1047", "CP1047"), Map.entry("ibm1047", "CP1047"), Map.entry("ascii", "ASCII"),
			Map.entry("us-ascii", "ASCII"), Map.entry("utf8", "UTF_8"), Map.entry("utf-8", "UTF_8"));

	private static final Map<String, IntPredicate> PREDICATES = Map.of(
			// ASCII predicate: 7-bit printable region is continuous
			"ASCII", c -> c >= 0x20 && c <= 0x7E,

			// EBCDIC isn’t contiguous
			"CP1047", c -> c == 0x40 || // space
					(c >= 0xF0 && c <= 0xF9) || // digits
					(c >= 0xC1 && c <= 0xC9) || // uppercase
					(c >= 0xD1 && c <= 0xD9) || (c >= 0xE2 && c <= 0xE9) || (c >= 0x81 && c <= 0x89) || // lowercase
					(c >= 0x91 && c <= 0x99) || (c >= 0xA2 && c <= 0xA9) || (c >= 0x4A && c <= 0x6F) || // punctuation

					// fallback bytes for Western European accents
					c == 0x3F || c == 0x45 || c == 0x49 || c == 0x7D || c == 0xCE || c == 0xDE || c == 0xD3 || c == 0xC7
					|| c == 0xE9 || c == 0xDC);

	public IntPredicate getIntPredicate(String codePage) {
		return PREDICATES.get(CODEPAGE_ALIASES.getOrDefault(codePage.toLowerCase(), codePage.toUpperCase()));
	}

	@FunctionalInterface
	public interface DecoderFunction {
		CharBuffer apply(byte[] data) throws CharacterCodingException;
	}

	public /* Function<byte[], CharBuffer> */ DecoderFunction getDecoder(final String codePage) {
		final Charset charset = codePage.equalsIgnoreCase("UTF_8") ? StandardCharsets.UTF_8 : Charset.forName(codePage);
		return codePage.contains("ASCII") ? null
				: (byte[] data) -> charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
						.onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(data));
	}

	// generic validator: strict or threshold
	// null threshold = strict mode, otherwise threshold mode
	public ValidationResult validateGeneric(final byte[] data, final String codePage, final DecoderFunction decoder,
			final IntPredicate rangeValidator, final Double threshold) {

		boolean status = true;
		String message = null;
		int validCount = 0;
		boolean strict = (threshold == null);
		if (decoder == null && rangeValidator == null) {
			throw new IllegalArgumentException("Invalid arguments: both decoder and rangeValidator are null");
		}
		// optional decoder apply
		if (decoder != null) {
			try {
				decoder.apply(data);
			} catch (CharacterCodingException e) {
				status = false;
				log.debug("failed to decode in code page {}: {}", codePage, e.getMessage(), e);
				message = String.format("failed to decode in code page %s: %s", codePage, e.getMessage());
				return new ValidationResult(status, message);
			}
		}
		// optional range validator run
		if (rangeValidator != null) {
			boolean valid = false;
			for (int pos = 0; pos < data.length; pos++) {
				int charCode = data[pos] & 0xFF; // unsigned

				// null character check
				if (0 == charCode) {
					status = false;
					if (message == null) {
						message = String.format("null character at position %d", pos);
					}
				}
				// range check
				valid = rangeValidator.test(charCode);
				if (valid) {
					validCount++;
				} else {
					// strict mode: any invalid char fails immediately
					if (strict) {
						status = false;
						message = String.format("invalid code page %s character 0x%02X at position %d", codePage,
								charCode, pos);
						log.debug(message);
					}
				}
			}
			// threshold mode: compute ratio
			if (!strict) {
				double ratio = (double) validCount / data.length;
				log.debug(String.format("threshold mode:  valid byte ratio %.2f threshold %.2f", ratio, threshold));
				if (ratio < threshold) {
					status = false;
					message = String.format("valid byte ratio %.2f below threshold %.2f for code page %s", ratio,
							threshold, codePage);
				}
			}
		}
		if (message != null)
			log.debug(message);
		return new ValidationResult(status, message);

	}

}
