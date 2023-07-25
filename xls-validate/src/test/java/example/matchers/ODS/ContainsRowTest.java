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
		for (String[] texts : Arrays.asList(new String[][] {
				{ "1", "junit", "202", "07/23/23" },
				{ "2", "testng", "52", "07/23/23" }, { "3", "spock", "22", "07/23/23" },
				{ "4", "allure", "50", "07/23/23" }, { "5", "хабр", "123", "07/23/23" },
				{ "6", "разработка", "192", "07/23/23" },
				{ "7", "фриланс", "83", "07/23/23" } })) {
			assertThat(sheet, containsRow(texts));

		}
	}

	@Test
	public void rowContainsCells_partial() throws IOException {
		for (String[] texts : Arrays.asList(new String[][] {
				{ "1", "junit", "202" }, { "2", "testng", "52" },
				{ "3", "spock", "22" }, { "4", "allure", "50" }, { "5", "хабр", "123" },
				{ "6", "разработка", "192" }, { "7", "фриланс", "83" } })) {
			assertThat(sheet, containsRow(texts));

		}
	}

	@Test
	public void rowContainsCells_noMatch() throws IOException {
		for (String[] texts : Arrays.asList(new String[][] { { "foobar" },
				{ "2", "testng", "51" }, { "2", "testng", "testng", "52" },
				{ "7", "фриланс", "83", "123", "456" } })) {
			assertThat(sheet, not(containsRow(texts)));
		}
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
