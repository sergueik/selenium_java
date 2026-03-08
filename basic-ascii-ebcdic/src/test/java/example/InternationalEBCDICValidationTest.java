import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import example.Converter;
import example.ValidationResult;

public class InternationalEBCDICValidationTest {

	static Double threshold = 0.6; // example threshold
	private static final String codePage = "CP1047";
	private static final Charset charset = Charset.forName(codePage);

	// NOTE: CP1047 limitations:
	// only ASCII letters be properly encoded, all accented letters
	// in Spanish: "Cómo estás" or French "Ça va bien" are silently replaced with
	// fallback bytes )
	static Stream<Arguments> samples3() {
		return Stream.of(Arguments.of("Spanish accented characters in CP1047", "Cómo estás", true),
				Arguments.of("Canadian French accented characters in CP1047", "Ça va bien", true));
	}

	@DisplayName("EBCDIC strict validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test1(String description, String input, boolean expected) {

		// validate encoded string
		ValidationResult result = Converter.validateGeneric(input.getBytes(Charset.forName(codePage)), codePage,
				charset, Converter.getIntPredicate(codePage), null);

		assertThat(description + " input=" + input + " message=" + result.getMessage(), result.isValid(), is(expected));

	}

	@DisplayName("EBCDIC threshold validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test2(String description, String input, boolean expected) {
		// Validate encoded string with threshold
		ValidationResult result = Converter.validateGeneric(input.getBytes(Charset.forName(codePage)), codePage,
				charset, Converter.getIntPredicate(codePage), threshold);

		assertThat(description + " input=" + input + " message=" + result.getMessage(), result.isValid(), is(expected));
	}
}