package com.github.sergueik.utils;

import static org.hamcrest.CoreMatchers.is;

// import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
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
	private static final String secretFilePath = Paths
			.get(System.getProperty("user.home")).resolve(".secret")
			.resolve("client_secret.json").toAbsolutePath().toString();
	private final boolean debug = true;

	@Before
	public void setup() throws GeneralSecurityException, IOException {
		sheetsServiceUtil.setApplicationName(applicationName);
		sheetsServiceUtil.setSecretFilePath(secretFilePath);
		sheetsServiceUtil.setDebug(debug);
		sheetsService = sheetsServiceUtil.getSheetsService();
	}

	@Test
	public void test1() throws IOException {

		final String id = "1KxijTuuipLER6kKmov4BFEhGsgRydCd1bh-9pzisRQ0";
		final String range = "Users!A2:C";

		System.err.println(
				"Examine spreadsheets in specified id: " + id + " and range: " + range);
		List<List<Object>> values = sheetsService.spreadsheets().values()
				.get(id, range).execute().getValues();
		assertThat(values, notNullValue());
		assertThat(values.size() != 0, is(true));

		System.err.println("Reading " + values.size() + " value rows");
		for (List<Object> row : values) {
			System.err.println("Got: " + row);
		}
	}

	@Test
	public void Test2() throws IOException {
		final String spreadsheetId = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
		final String range = "UserInfo!A2:D";

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

	@Test
	public void test3() throws IOException {
		final String name = "UserInfo";
		final String Id = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
		List<Object[]> result = readGoogleSpreadsheet(Id, name);

	}

	public List<Object[]> readGoogleSpreadsheet(String spreadsheetId,
			String sheetName) {
		String range = String.format("%s!A2:Z", sheetName);
		List<Object[]> result = new LinkedList<>();

		try {
			ValueRange response = sheetsService.spreadsheets().values()
					.get(spreadsheetId, range).execute();

			List<List<Object>> resultRows = response.getValues();
			assertThat(resultRows, notNullValue());
			assertThat(resultRows.size() != 0, is(true));

			System.err.println("Got " + resultRows.size() + " result rows");
			for (List<Object> resultRow : resultRows) {
				// System.err.println("Got: " + resultRow);
				result.add(resultRow.toArray());
			}
		} catch (IOException e) {
		}
		return result;
	}
}
