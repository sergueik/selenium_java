package example.matchers.ODS;

/**
 * Copyright 2023 Serguei Kouzmine
 */

import static example.ODS.containsText;
import static example.ODS.doesNotContainText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import example.ODS;

public class ContainsTextTest {
	private ODS sheet;

	@Before
	public void before() throws Exception {
		sheet = new ODS(Objects.requireNonNull(
				getClass().getClassLoader().getResource("testdata.ods")));
	}

	@Test
	public void canAssertThatXlsContainsText() throws IOException {
		for (String text : Arrays
				.asList(new String[] { "junit", "testng", "allure" })) {
			assertThat(sheet, containsText(text));
		}
	}

	@Test
	public void canAssertXlsDoesNotContainText() throws IOException {
		assertThat(sheet, doesNotContainText("xunit"));
	}

	@Test
	public void errorDescriptionForDoesNotContainTextMatcher() {
		String text = "junit";
		try {
			assertThat(sheet, doesNotContainText(text));
			fail("expected AssertionError");
		} catch (AssertionError expected) {
			assertThat(expected.getMessage(), containsString(
					String.format("Expected: a ODS not containing text \"%s\"", text)));
		}
	}

	@Test
	public void errorDescriptionForContainsTextMatcher() {
		String text = "wrong text";
		String message = String.format("Expected: a ODS containing text \"%s\"",
				text);
		try {
			assertThat(sheet, containsText(text));
			fail("expected AssertionError");
		} catch (AssertionError expected) {
			assertThat(expected.getMessage(), containsString(message));
		}
	}
}
