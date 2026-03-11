package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import example.Converter;
import example.ValidationResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class InternationalEBCDICValidationTest {

	private Converter converter = null;
	final String inputFile = null;
	final String outputFile = null;
	final String data = null;
	final String codepage = "cp037";
	Long threshold = 90L;

	@BeforeAll
	public void beforeall() {
		converter = new Converter(data, codepage, outputFile, codepage, threshold);
	}

	// NOTE: CP1047 limitations:
	// only ASCII letters be properly encoded, all accented letters
	// are silently replaced with fallback bytes
	static Stream<Arguments> samples3() {
		return Stream.of(Arguments.of("Spanish accented characters in CP1047",
				"El veloz murciélago hindú comía feliz cardillo y kiwi; la cigüeña tocaba el saxofón detrás del palenque de paja",
				true),
				Arguments.of("Canadian French accented characters in CP1047",
						"Voix ambiguë d'un cœur qui au zéphyr préfère les jattes de kiwi", true));
	}

	@DisplayName("EBCDIC strict validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test1(String description, String input, boolean expected) {

		// validate encoded string
		ValidationResult result = converter.validateGeneric(input.getBytes(Charset.forName(codepage)), codepage,
				Charset.forName(codepage), converter.getIntPredicate(codepage), null);

		assertThat(description + " input=" + input + " message=" + result.getMessage(), result.isValid(), is(expected));

	}

	@DisplayName("EBCDIC threshold validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test2(String description, String input, boolean expected) {
		// Validate encoded string with threshold
		ValidationResult result = converter.validateGeneric(input.getBytes(Charset.forName(codepage)), codepage,
				Charset.forName(codepage), converter.getIntPredicate(codepage), threshold * .01);

		assertThat(description + " input=" + input + " message=" + result.getMessage(), result.isValid(), is(expected));
	}
}
