package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
public class ValidatorTests {

	private Long threshold = 90L;
	private Validator validator = null;
	final String data = null;

	@DisplayName("strict pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test1(String description, String data, String codePage, boolean expected) {
		threshold = null;
		validator = new Validator(data, codePage, threshold);
		ValidationResult result = validator.validate();
		final String info = description + " data=" + data + " message=" + result.getMessage();
		assertThat(info, result.isValid(), is(expected));
	}

	@DisplayName("threshold pass or fail")
	@ParameterizedTest
	@MethodSource("samples")
	void test2(String description, String data, String codePage, boolean expected) {

		threshold = 90L;
		validator = new Validator(data, codePage, threshold);
		ValidationResult result = validator.validate();
		final String info = description + " data=" + data + " message=" + result.getMessage();
		assertThat(info, result.isValid(), is(expected));
	}

	static Stream<Arguments> samples() {
		return Stream.of(

				Arguments.of("uppercase HELLO", "C8C5D3D3D6", "cp037", true),
				Arguments.of("lowercase hello", "8885939396", "cp037", true),
				Arguments.of("digits 12345", "F1F2F3F4F5", "cp037", true),
				Arguments.of("HELLO.HELLO punctuation", "C8C5D3D3D64BC8C5D3D3D6", "cp037", true),
				Arguments.of("contains NULL byte", "C8C500D3D6", "cp037", false),
				Arguments.of("control characters 0x15", "151515", "cp037", false),
				Arguments.of("mixed valid and invalid", "C8C5D315D6", "cp037", false),
				// --- encoding confusion cases ---
				Arguments.of("UTF-8 string 'HELLO'", "48454C4C4F", "cp037", false),
				Arguments.of("ASCII digits '12345'", "3132333435", "cp037", false),
				Arguments.of("UTF-16BE 'HELLO'", "00480045004C004C004F", "cp037", false),
				Arguments.of("UTF-16 BOM + text", "FEFF00480045004C004C004F", "cp037", false),
				Arguments.of("cp037 digits 12345 claimed to be utf-8", "F1F2F3F4F5", "utf-8", false),
				Arguments.of("cp037 digits 12345 claimed to be ascii", "F1F2F3F4F5", "ascii", false),
				// there is overlap between valid picture EBCDIC byte ranges and UTF-8
				Arguments.of("UTF-8 string 'é' (C3 A9)", "C3A9", "cp037", true));
			// NOTE: when passing  the arguments incorrectly
			// org.junit.jupiter.api.extension.ParameterResolutionException:
			// Error converting parameter at index 2: No built-in converter for source type
			// java.lang.Boolean and target type java.lang.String
	}

}
