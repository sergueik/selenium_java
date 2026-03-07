package example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EbcdicValidationTest {

	@ParameterizedTest
	@MethodSource("samples")
	void testEbcdicValidation(String hex, boolean expectedValid) {

		byte[] data = Converter.hexToByteArray(hex);

		ValidationResult result = Converter.validateEbcdic(data);

		assertEquals(expectedValid, result.isValid(), "hex=" + hex + " message=" + result.getMessage());
	}

	static Stream<Arguments> samples() {
		return Stream.of(

				// HELLO
				Arguments.of("C8C5D3D3D6", true),

				// hello
				Arguments.of("8885939396", true),

				// digits
				Arguments.of("F1F2F3F4F5", true),

				// space + punctuation
				Arguments.of("40C8C5D3D34BC8C5D3D3D6", true), // HELLO.HELLO

				// null byte
				Arguments.of("C8C500D3D6", false),

				// control-like garbage
				Arguments.of("151515", false),

				// binary-like
				Arguments.of("C8C5FFFFD3", false),

				// mixed good/bad
				Arguments.of("C8C5D315D6", false));
	}
}
