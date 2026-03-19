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

import example.Validator;
import example.ValidationResult;

@TestInstance(Lifecycle.PER_CLASS)
public class InternationalEBCDICValidationTest {

	private Validator validator = null;
	final String data = null;
	final String codePage = "CP1047";  // "cp037";
	Long threshold = 90L;

	@BeforeAll
	public void beforeall() {

	}

	// NOTE: CP1047 limitations:
	// only ASCII letters be adequately encoded, all accented letters
	// are silently replaced with fallback bytes
	static Stream<Arguments> samples3() {
		return Stream.of(Arguments.of("Spanish accented characters in CP1047",
				"El veloz murciélago hindú comía feliz cardillo y kiwi; la cigüeña tocaba el saxofón detrás del palenque de paja",
				true),
				Arguments.of("Canadian French accented characters in CP1047",
						"Voix ambiguë d'un cœur " + "qui au zéphyr préfère les jattes de kiwi", true),
				Arguments.of("European banking text with Euro sign", "La banque européenne a reçu 100€ pour le dépôt",
						false),
				Arguments.of("European smart quote", "Voix ambiguë d’un cœur", true));
	}

	static Stream<Arguments> samples4() {
		return Stream.of(Arguments.of("Spanish accented characters in CP1047",
				"El veloz murciélago hindú comía feliz cardillo y kiwi; la cigüeña tocaba el saxofón detrás del palenque de paja",
				true),
				Arguments.of("Canadian French accented characters in CP1047",
						"Voix ambiguë d'un cœur " + "qui au zéphyr préfère les jattes de kiwi", true),
				Arguments.of("European banking text with Euro sign", "La banque européenne a reçu 100€ pour le dépôt",
						true),
				Arguments.of("European smart quote", "Voix ambiguë d’un cœur", true));
	}

	@DisplayName("EBCDIC strict validation for non-US")
	@ParameterizedTest
	@MethodSource("samples3")
	void test1(String description, String data, boolean expected) {

		threshold = null;
		validator = new Validator(hexString(data,codePage), codePage, threshold);
		// validate encoded string
		ValidationResult result = validator.validate();

		assertThat(description + " data=" + data + " message=" + result.getMessage(), result.isValid(), is(expected));

	}

	@DisplayName("EBCDIC threshold validation for non-US")
	@ParameterizedTest
	@MethodSource("samples4")
	void test2(String description, String data, boolean expected) {
		// Validate encoded string with threshold
		threshold = 90L;
		validator = new Validator(hexString(data,codePage), codePage, threshold);
		ValidationResult result = validator.validate();

		assertThat(description + " data=" + data + " message=" + result.getMessage(), result.isValid(), is(expected));
	}

	/*
	 * Hex:
	 * C59340A5859396A94094A499838951938187964088899584DE4083969455814086859389A940838199848993939640A8409289A6895E40938140838987DC85498140A3968381828140859340A281A79686CE95408485A39945A24084859340978193859598A4854084854097819181
	 * Hex:
	 * E59689A7408194828987A45340847DA49540833FA4994098A4894081A440A9519788A8994097995186549985409385A2409181A3A385A2408485409289A689
	 * Hex:
	 * D3814082819598A4854085A49996975185959585408140998548A440F1F0F03F409796A49940938540845197CBA3
	 * Hex: E59689A7408194828987A45340843FA49540833FA499
	 */

	static String hexString(String data, String codePage) {
		byte[] bytes = data.getBytes(Charset.forName(codePage));
		StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}
		System.err.println("Hex: " + stringBuilder.toString());
		return stringBuilder.toString();
	}
}
