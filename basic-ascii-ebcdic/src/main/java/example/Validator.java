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

public class Validator {

	private static final Logger log = LoggerFactory.getLogger(Validator.class);

	// Immutable fields
	private final String codepage;
	private final Long threshold;
	private final byte[] data;
	private final Charset charset;

	// Canonical codepage mapping
	private static final Map<String, String> CODEPAGE_ALIASES = Map.ofEntries(Map.entry("ebcdic", "CP1047"),
			Map.entry("cp037", "CP1047"), Map.entry("cp1047", "CP1047"), Map.entry("ibm1047", "CP1047"),
			Map.entry("ascii", "ASCII"), Map.entry("us-ascii", "ASCII"), Map.entry("utf8", "UTF_8"),
			Map.entry("utf-8", "UTF_8"));

	public String toString() {
		return String.format("codepage=%s threshold=%d data=%s", codepage, threshold, Converter.byteArrayToHex(data));
	}

	private static final Map<String, IntPredicate> PREDICATES = Map.of("ASCII", c -> c >= 0x20 && c <= 0x7E, "CP1047",
			c -> c == 0x40 || (c >= 0xF0 && c <= 0xF9) || (c >= 0xC1 && c <= 0xC9) || (c >= 0xD1 && c <= 0xD9)
					|| (c >= 0xE2 && c <= 0xE9) || (c >= 0x81 && c <= 0x89) || (c >= 0x91 && c <= 0x99)
					|| (c >= 0xA2 && c <= 0xA9) || (c >= 0x4A && c <= 0x6F) || c == 0x3F || c == 0x45 || c == 0x49
					|| c == 0x7D || c == 0xCE || c == 0xDE || c == 0xD3 || c == 0xC7 || c == 0xE9 || c == 0xDC);

	public Validator(String data, String codepage, Long threshold) {
		this.threshold = threshold;
		this.codepage = CODEPAGE_ALIASES.getOrDefault(codepage.toLowerCase(), codepage.toUpperCase());
		this.charset = this.codepage.equals("UTF_8") ? StandardCharsets.UTF_8 : Charset.forName(this.codepage);
		this.data = (data != null) ? Converter.hexToByteArray(data) : null;
		log.debug(this.toString());
	}

	public Validator(byte[] data, String codepage, Long threshold) {
		this.threshold = threshold;
		this.codepage = CODEPAGE_ALIASES.getOrDefault(codepage.toLowerCase(), codepage.toUpperCase());
		this.charset = this.codepage.equals("UTF_8") ? StandardCharsets.UTF_8 : Charset.forName(this.codepage);
		this.data = data;
		log.debug(this.toString());
	}

	@FunctionalInterface
	public interface DecoderFunction {
		CharBuffer apply(byte[] data) throws CharacterCodingException;
	}

	public ValidationResult validate() {
		if (data == null)
			throw new IllegalArgumentException("Data cannot be null for validation");

		DecoderFunction decoder = (codepage.contains("ASCII")) ? null
				: (byte[] data) -> charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
						.onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(data));
		IntPredicate rangeValidator = PREDICATES.get(codepage);

		boolean status = true;
		String message = null;
		int validCount = 0;
		boolean strict = (threshold == null);

		if (decoder == null && rangeValidator == null)
			throw new IllegalArgumentException("Decoder and validator both null");

		if (decoder != null) {
			try {
				decoder.apply(data);
			} catch (CharacterCodingException e) {
				status = false;
				message = String.format("failed to decode in code page %s: %s", codepage, e.getMessage());
				return new ValidationResult(status, message);
			}
		}

		if (rangeValidator != null) {
			for (int pos = 0; pos < data.length; pos++) {
				int charCode = data[pos] & 0xFF;
				if (charCode == 0) {
					status = false;
					if (message == null)
						message = String.format("null char at %d", pos);
				}
				boolean valid = rangeValidator.test(charCode);
				if (valid)
					validCount++;
				else if (strict) {
					status = false;
					message = String.format("invalid code page %s char 0x%02X at %d", codepage, charCode, pos);
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