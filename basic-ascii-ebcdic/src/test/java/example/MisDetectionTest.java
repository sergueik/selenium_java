package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class MisDetectionTest {

	private Converter converter = null;
	final String inputFile = null;
	final String outputFile = null;
	final String data = null;
	final String codePage = "cp037";
	Long threshold = 90L;
	// bytes that are invalid in CP037 but decoder may not throw
	byte[] input = new byte[] { (byte) 0x10, // control
			(byte) 0xDE // outside main letter/digit ranges
	};

	@BeforeAll
	public void beforeall() {
		converter = new Converter(data, codePage, outputFile, codePage, threshold);
	}

	@DisplayName("Decoder-only misdetects invalid bytes")
	@Test
	void ebcdicDecoderAloneAllowsInvalidBytes() throws Exception {

		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder("CP037"), null, null);
		assertThat(result.isValid(), is(true));
	}

	@DisplayName("Range validator correctly flags invalid bytes")
	@Test
	void ebcdicWithRangeValidatorDetectsInvalidBytes() throws Exception {

		ValidationResult result = converter.validateGeneric(input, codePage, converter.getDecoder(codePage),
				converter.getIntPredicate(codePage), null);
		assertThat(result.isValid(), is(false));

	}
}
