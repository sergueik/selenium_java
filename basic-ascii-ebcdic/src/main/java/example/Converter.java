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
import java.util.function.Function;

public class Converter {

	private final boolean debug = false;

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

	// NOTE map may be over-engineering if one only handles 3 charmaps:
	/*
	 * private final Map<Charset, Function<byte[], ValidationResult>> VALIDATORS =
	 * new HashMap<>();
	 * 
	 * { VALIDATORS.put(StandardCharsets.US_ASCII, Convertor::validateASCII);
	 * VALIDATORS.put(StandardCharsets.UTF_8, Convertor::validateUTF8); }
	 */

	public void validate(String inputFile, String data, String codepage) throws IOException {

		byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile)) : hexToByteArray(data);

		ValidationResult result;

		if (codepage == null || codepage.equalsIgnoreCase("ascii") || codepage.equalsIgnoreCase("us-ascii")) {

			result = validateASCII(input);

		} else if (codepage.equalsIgnoreCase("utf8") || codepage.equalsIgnoreCase("utf-8")) {

			result = validateUTF8(input);

		} else {

			// default EBCDIC family
			result = validateEBCDIC(input);
		}

		System.err.println(result.isValid() ? "valid" : "invalid");

		if (debug)
			System.err.println(result.getMessage());
	}

	// EBCDIC predicate: non-contiguous valid ranges, including digits, letters,
	// punctuation and fallback
	// EBCDIC isn’t contiguous like ASCII
	private IntPredicate isValidEBCDICChar = charCode ->
	// space
	charCode == 0x40 ||
	// digits
			(charCode >= 0xF0 && charCode <= 0xF9) ||
			// uppercase letters
			(charCode >= 0xC1 && charCode <= 0xC9) || (charCode >= 0xD1 && charCode <= 0xD9)
			|| (charCode >= 0xE2 && charCode <= 0xE9) ||
			// lowercase letters
			(charCode >= 0x81 && charCode <= 0x89) || (charCode >= 0x91 && charCode <= 0x99)
			|| (charCode >= 0xA2 && charCode <= 0xA9) ||
			// basic punctuation
			(charCode >= 0x4A && charCode <= 0x6F) ||
			// generic fallback bytes for Western European accented characters
			// Use with caution: feeding it arbitrary unknown input
			// e.g., passport names or company names entered from localized keyboards
			// may pass validation even though the bytes do not accurately represent the
			// original characters
			charCode == 0x3F || // '?' fallback for unmapped characters
			charCode == 0x45 || // generic accented/fallback
			charCode == 0x49 || // generic accented/fallback
			charCode == 0x7D || // generic accented/fallback
			charCode == 0xCE || // generic accented/fallback
			charCode == 0xDE || // generic accented/fallback
			charCode == 0xD3 || // generic accented/fallback
			charCode == 0xC7 || // generic accented/fallback
			charCode == 0xE9 || // generic accented/fallback
			charCode == 0xDC; // generic accented/fallback

	// ASCII predicate: 7-bit printable region is continuous
	private IntPredicate isValidAsciiChar = charCode -> charCode >= 0x20 && charCode <= 0x7E;

	public IntPredicate getIntPredicate(final String codePage) {
		return codePage.contains("ASCII") ? isValidAsciiChar
				: codePage.equalsIgnoreCase("UTF_8") ? null : isValidEBCDICChar;
	}

	// generic validator: strict or threshold
	// null threshold = strict mode, otherwise threshold mode
	public ValidationResult validateGeneric(final byte[] data, final String codePage, final Charset decoder,
			final IntPredicate rangeValidator, final Double threshold) {
		boolean status = true;
		String message = null;
		int validCount = 0;
		boolean strict = (threshold == null);
		if (decoder == null && rangeValidator == null) {
			throw new IllegalArgumentException("Invalid arguments: both decoder and rangeValidator are null");
		}
		// optional decoder check
		if (decoder != null) {
			try {
				decoder.newDecoder().decode(ByteBuffer.wrap(data));
			} catch (CharacterCodingException e) {
				status = false;
				message = String.format("failed to decode in code page %s: %s", codePage, e.getMessage());
				return new ValidationResult(status, message);
			}
		}

		if (rangeValidator != null) {
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
				boolean valid = rangeValidator.test(charCode);
				if (valid) {
					validCount++;
				}

				// strict mode: any invalid char fails immediately
				if (!valid && strict) {
					status = false;
					if (message == null) {
						message = String.format("invalid code page %s character 0x%02X at position %d", codePage,
								charCode, pos);
					}
				}
			}

			// threshold mode: ratio check
			if (!strict) {
				double ratio = (double) validCount / data.length;
				if (ratio < threshold) {
					status = false;
					message = String.format("valid byte ratio %.2f below threshold %.2f for code page %s", ratio,
							threshold, codePage);
				}
			}
		}

		return new ValidationResult(status, message);
	}

	// strict validators
	public ValidationResult validateASCII(byte[] data) {
		return validateGeneric(data, "ASCII", null, isValidAsciiChar, null);
	}

	public ValidationResult validateASCII(byte[] data, double threshold) {
		return validateGeneric(data, "ASCII", null, isValidAsciiChar, threshold * 0.01);
	}

	public ValidationResult validateEBCDIC(byte[] data) {
		return validateGeneric(data, "CP1047", Charset.forName("CP1047"), isValidEBCDICChar, null);
	}

	public ValidationResult validateEBCDIC(byte[] data, double threshold) {
		return validateGeneric(data, "CP1047", Charset.forName("CP1047"), isValidEBCDICChar, threshold * 0.01);
	}

	public ValidationResult validateUTF8(byte[] data) {
		return validateGeneric(data, "UTF-8", StandardCharsets.UTF_8, null, null);
	}

}
