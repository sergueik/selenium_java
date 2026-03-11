package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.nio.charset.StandardCharsets;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import example.Converter;
import example.ValidationResult;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)

class ConverterExceptionTest {

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

	@Test
	@DisplayName("validation should abort when none decoder and rangeValidator is provied")
	void testValidateGenericThrows() {

		byte[] data = "test".getBytes(StandardCharsets.US_ASCII);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> converter.validateGeneric(data, codepage, null, null, null));

		// Modern Hamcrest-style assertion to verify exception message
		assertThat("Exception message should mention invalid arguments", thrown.getMessage(),
				containsString("both decoder and rangeValidator are null"));
	}

	@Test
	@DisplayName("validation should abort when none decoder and rangeValidator is provied (AssertJ style)")
	void testValidateGenericThrowsAssertJ() {

		byte[] data = "test".getBytes(StandardCharsets.US_ASCII);

		assertThatThrownBy(() -> converter.validateGeneric(data, codepage, null, null, null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("both decoder and rangeValidator are null");
	}
}