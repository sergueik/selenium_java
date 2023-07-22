package example.matchers.ODS;

/**
 * Copyright 2023 Serguei Kouzmine
 */

import static example.ODS.containsRow;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import example.ODS;

public class ContainsRowTest {
	private ODS sheet;

	@Before
	public void before() throws Exception {
		sheet = new ODS(Objects.requireNonNull(
				getClass().getClassLoader().getResource("testdata.ods")));
	}

	@Test
	public void rowContainsCells_all() throws IOException {
		for (String[] texts : Arrays.asList(
				new String[][] { { "1", "junit", "202" }, { "2", "testng", "52" },
						{ "3", "spock", "22" }, { "4", "allure", "50" } })) {
			assertThat(sheet, containsRow(texts));

		}
	}

	@Ignore
	@Test
	public void rowContainsCells_withGaps() throws Exception {
		assertThat(sheet, containsRow("PP028000"));
		assertThat(sheet, containsRow("PP028000", "281814930"));
		assertThat(sheet, containsRow("281814930"));
		assertThat(sheet, containsRow("Итого по терминалу", "48,271.00"));
	}

	@Test
	public void rowContainsCells_noMatch() throws IOException {
		assertThat(sheet, not(containsRow("foobar")));
		assertThat(sheet, not(containsRow("2", "testng", "51")));
		assertThat(sheet, not(containsRow("1", "testng", "52")));
	}

	@Test
	public void errorDescription() throws IOException {
		String text = "wrong data";
		String message = String.format("Expected: a ODS containing row \"[%s]\"",
				text);
		try {
			assertThat(sheet, containsRow(text));
			fail("expected AssertionError");
		} catch (AssertionError expected) {
			assertThat(expected.getMessage(), containsString(message));
		}
	}
}
