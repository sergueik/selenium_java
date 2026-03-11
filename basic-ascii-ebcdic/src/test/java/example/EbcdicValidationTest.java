package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class EbcdicValidationTest {

	private Long threshold = 90L;
	private Converter converter = null;
	final String inputFile = "example.in";
	final String outputFile = "sample.out";
	final String data = null;
	private String codePage = "cp037";

	@BeforeAll
	public void beforeall() {
		converter = new Converter(data, codePage, outputFile, codePage, threshold);
	}

	@DisplayName("strict pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test1(String description, String data, boolean expected) {
		codePage = "cp037";
		byte[] input = converter.hexToByteArray(data);
		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder(codePage),
				converter.getIntPredicate(codePage), Double.valueOf(threshold * .01));
		final String info = description + " data=" + data + " message=" + result.getMessage();
		assertThat(info, result.isValid(), is(expected));
	}

	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test2(String description, String data, boolean expected) {

		byte[] input = converter.hexToByteArray(data);
		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder(codePage),
				converter.getIntPredicate(codePage), Double.valueOf(threshold * .01));
		final String info = description + " data=" + data + " message=" + result.getMessage();
		assertThat(info, result.isValid(), is(expected));
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
	void test3(String data, boolean status) {
		codePage = "ASCII";
		StringBuffer stringBuffer = new StringBuffer();
		data.chars().mapToObj(ch -> String.format("%02X", ch)).forEach(stringBuffer::append);
		byte[] input = converter.hexToByteArray(stringBuffer.toString());
		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder(codePage),
				converter.getIntPredicate(codePage), null);
		assertThat(result.getMessage(), result.isValid(), is(status));

	}

	// @Disabled
	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples2")
	void test4(String data, boolean status) {

		byte[] input = converter
				.hexToByteArray(data.chars().mapToObj(ch -> String.format("%02X", ch)).collect(Collectors.joining()));
		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder(codePage),
				converter.getIntPredicate(codePage), Double.valueOf(threshold * .01));
		assertThat(result.getMessage(), result.isValid(), is(status));
	}

}
