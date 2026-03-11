package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import example.Converter;
import example.ValidationResult;

@TestInstance(Lifecycle.PER_CLASS)
public class InternationalEBCDICValidationTest {

	private Converter converter = null;
	final String inputFile = null;
	final String outputFile = null;
	final String data = null;
	final String codePage = "cp037";
	Long threshold = 90L;

	@BeforeAll
	public void beforeall() {

	}

	// NOTE: CP1047 limitations:
	// only ASCII letters be properly encoded, all accented letters
	// are silently replaced with fallback bytes
	static Stream<Arguments> samples3() {
		return Stream.of(Arguments.of("Spanish accented characters in CP1047",
				"El veloz murciélago hindú comía feliz cardillo y kiwi; la cigüeña tocaba el saxofón detrás del palenque de paja",
				true),
				Arguments.of("Canadian French accented characters in CP1047",
						"Voix ambiguë d'un cœur " + "qui au zéphyr préfère les jattes de kiwi", true));
	}

	// @Disabled
	@DisplayName("EBCDIC strict validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test1(String description, String data, boolean expected) {

		threshold = null;
		converter = new Converter(inputFile, outputFile, codePage, threshold, toCp1047Hex(data));
		// validate encoded string
		ValidationResult result = converter.validateGeneric();

		assertThat(description + " data=" + data + " message=" + result.getMessage(), result.isValid(), is(expected));

	}

	// @Disabled
	@DisplayName("EBCDIC threshold validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test2(String description, String data, boolean expected) {
		// Validate encoded string with threshold
		threshold = 90L;
		converter = new Converter(inputFile, outputFile, codePage, threshold, toCp1047Hex(data));
		ValidationResult result = converter.validateGeneric();

		assertThat(description + " data=" + data + " message=" + result.getMessage(), result.isValid(), is(expected));
	}

	static String toCp1047Hex(String data) {
		byte[] bytes = data.getBytes(Charset.forName("CP1047"));
		StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}
		return stringBuilder.toString();
	}
}
