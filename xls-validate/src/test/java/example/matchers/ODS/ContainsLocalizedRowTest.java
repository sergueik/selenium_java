package example.matchers.ODS;

/**
 * Copyright 2023 Serguei Kouzmine
 */

// import java.util.Date;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;

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
import static example.ODS.containsRow;

public class ContainsLocalizedRowTest {
	private ODS sheet;

	@Before
	public void before() throws Exception {
		sheet = new ODS(Objects.requireNonNull(
				getClass().getClassLoader().getResource("localized.ods")));
	}

	@Test
	public void rowContainsCells_partial() throws IOException {
		for (String[] texts : Arrays.asList(
				new String[][] { { "1", "junit", "202" }, { "2", "testng", "52" },
						{ "3", "spock", "22" }, { "4", "allure", "50" } })) {
			assertThat(sheet, containsRow(texts));

		}
	}

	@Test
	public void rowContainsCells_all() throws IOException {
		/*
		@SuppressWarnings("deprecation")
		Date date = new Date("07/23/2023");
		String dateString = DateFormat.getDateInstance(DateFormat.DEFAULT)
				.format(date);
				*/
		String dateString = "Sun Jul 23 00:00:00 EDT 2023";
		for (String[] texts : Arrays.asList(new String[][] {
				{ "1", "junit", "202", dateString },
				{ "2", "testng", "52", dateString }, { "3", "spock", "22", dateString },
				{ "4", "allure", "50", dateString }, { "5", "хабр", "123", dateString },
				{ "6", "разработка", "192", dateString },
				{ "7", "фриланс", "83", dateString },
				{ "4", "allure", "50", dateString },
				{ "8", "расчёт", "9", dateString } })) {
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
