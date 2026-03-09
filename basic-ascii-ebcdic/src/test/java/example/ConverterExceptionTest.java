package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.nio.charset.StandardCharsets;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import example.Converter;
import example.ValidationResult;

class ConverterExceptionTest {

	@Test
	@DisplayName("validation should abort when none decoder and rangeValidator is provied")
	void testValidateGenericThrows() {
		byte[] data = "test".getBytes(StandardCharsets.US_ASCII);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> Converter.validateGeneric(data, "CP1047", null, null, null));

		// Modern Hamcrest-style assertion to verify exception message
		assertThat("Exception message should mention invalid arguments", thrown.getMessage(),
				containsString("both decoder and rangeValidator are null"));
	}
	@Test
	@DisplayName("validation should abort when none decoder and rangeValidator is provied (AssertJ style)")
	void testValidateGenericThrowsAssertJ() {

	    byte[] data = "test".getBytes(StandardCharsets.US_ASCII);

	    assertThatThrownBy(() ->
	        Converter.validateGeneric(data, "CP1047", null, null, null)
	    )
	    .isInstanceOf(IllegalArgumentException.class)
	    .hasMessageContaining("both decoder and rangeValidator are null");
	}}