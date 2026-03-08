package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EbcdicValidationTest {

	private double threshold = 0.96;

	@DisplayName("strict pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test1(String description, String hex, boolean expectedValid) {

		byte[] data = Converter.hexToByteArray(hex);

		ValidationResult result = Converter.validateEBCDIC(data);

		assertThat(description + " hex=" + hex + " message=" + result.getMessage(), result.isValid(),
				is(expectedValid));
	}

	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test2(String description, String hex, boolean expectedValid) {

		byte[] data = Converter.hexToByteArray(hex);

		ValidationResult result = Converter.validateEBCDIC(data, threshold);

		assertThat(description + " hex=" + hex + " message=" + result.getMessage(), result.isValid(),
				is(expectedValid));
	}

	static Stream<Arguments> samples() {
		return Stream.of(

				Arguments.of("uppercase HELLO", "C8C5D3D3D6", true),
				Arguments.of("lowercase hello", "8885939396", true), Arguments.of("digits 12345", "F1F2F3F4F5", true),
				Arguments.of("HELLO.HELLO punctuation", "C8C5D3D3D64BC8C5D3D3D6", true),
				Arguments.of("contains NULL byte", "C8C500D3D6", false),
				Arguments.of("control characters 0x15", "151515", false),
				Arguments.of("mixed valid and invalid", "C8C5D315D6", false),
				// --- encoding confusion cases ---
				Arguments.of("UTF-8 string 'HELLO'", "48454C4C4F", false),
				Arguments.of("ASCII digits '12345'", "3132333435", false),
				Arguments.of("UTF-16BE 'HELLO'", "00480045004C004C004F", false),
				Arguments.of("UTF-16 BOM + text", "FEFF00480045004C004C004F", false),
				// there is overlap between valid picture EBCDIC byte ranges and UTF-8
				Arguments.of("UTF-8 string 'é' (C3 A9)", "C3A9", true));
	}

	static Stream<Arguments> samples2() {
		return Stream.of(Arguments.of("HELLO", true), Arguments.of("привет", false));
	}

	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples2")
	void test3(String input, boolean status) {

		StringBuffer stringBuffer = new StringBuffer();
		input.chars().mapToObj(ch -> String.format("%02X", ch)).forEach(stringBuffer::append);
		byte[] data = Converter.hexToByteArray(stringBuffer.toString());
		ValidationResult result = Converter.validateASCII(data);
		assertThat(result.getMessage(), result.isValid(), is(status));

	}

	// @Disabled
	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples2")
	void test4(String input, boolean status) {

		byte[] data = Converter
				.hexToByteArray(input.chars().mapToObj(ch -> String.format("%02X", ch)).collect(Collectors.joining()));
		ValidationResult result = Converter.validateASCII(data, threshold);
		assertThat(result.getMessage(), result.isValid(), is(status));
	}

}
