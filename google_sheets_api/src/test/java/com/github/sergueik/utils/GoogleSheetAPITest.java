package com.github.sergueik.utils;

import static org.hamcrest.CoreMatchers.is;

// import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Get;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetAPITest {

	private static final String applicationName = "Google Sheets Example";
	private Sheets sheetsService;
	private SheetsServiceUtil sheetsServiceUtil = SheetsServiceUtil.getInstance();

	@Before
	public void setup() throws GeneralSecurityException, IOException {
		sheetsServiceUtil.setApplicationName(applicationName);
		sheetsService = sheetsServiceUtil.getSheetsService();
	}

	@Test
	public void readExistingSpreadSheetTest() throws IOException {
		final String userName = "paul";
		final String spreadsheetId = "1KxijTuuipLER6kKmov4BFEhGsgRydCd1bh-9pzisRQ0";
		final String range = "Users!A2:C";

		Spreadsheets spreadsheets = sheetsService.spreadsheets();
		Get get = spreadsheets.values().get(spreadsheetId, range);
		System.err.println(
				"Examine spreadsheets in specified id and range:" + get.toString());

		ValueRange response = get.execute();

		List<List<Object>> values = response.getValues();
		assertThat(values, notNullValue());
		assertThat(values.size() != 0, is(true));

		System.err.println("Got " + values.size() + " value rows");
		for (List<Object> row : values) {
			System.err.println("Got: " + row);
		}
	}

	@Test
	public void readExistingSpreadSheet2Test() throws IOException {
		final String range = "UserInfo!A2:D";
		final String userName = "georgeh";
		final String spreadsheetId = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";

		ValueRange response = sheetsService.spreadsheets().values()
				.get(spreadsheetId, range).execute();

		List<List<Object>> values = response.getValues();
		assertThat(values, notNullValue());
		assertThat(values.size() != 0, is(true));

		System.err.println("Got " + values.size() + " value rows");
		for (List<Object> row : values) {
			System.err.println("Got: " + row);
		}
	}
}