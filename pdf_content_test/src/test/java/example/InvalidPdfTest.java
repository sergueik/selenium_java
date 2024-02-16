package example;

import org.junit.Test;

import example.PDF;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class InvalidPdfTest {
	@Test(expected = NoSuchFileException.class)
	public void throws_IOException_ifFailedToReadPdfFile() throws IOException {
		new PDF(new File("src/test/resources/invalid-file.pdf"));
	}
	// NOTE: need to create the file
	// touch dummy.pdf
	@Test
	public void throws_IllegalArgumentException_inCaseOfInvalidPdfFile()
			throws IOException {
		final String filename = "dummy.pdf";
		try {
			new PDF(new File(filename));
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
			assertThat(expected.getMessage(), is("Invalid PDF file: "
					+ System.getProperty("user.dir") + "/" + filename));
			assertThat(expected.getCause(), is(notNullValue()));
		}
	}
}
