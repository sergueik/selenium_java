package com.github.sergueik.utils;

import static org.hamcrest.CoreMatchers.is;

// import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.BatchGet;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Get;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetAPITest {

	private static final String applicationName = "Google Sheets Example";
	private static Sheets sheetsService;
	private static final String secretFilePath = Paths
			.get(System.getProperty("user.home")).resolve(".secret")
			.resolve("client_secret.json").toAbsolutePath().toString();

	@BeforeClass
	public static void setup() throws GeneralSecurityException, IOException {
		sheetsService = SheetsServiceUtil.getSheetsService(applicationName,
				secretFilePath);
	}

	@Test
	public void readExistingSpreadSheetTest() throws IOException {
		final String userName = "paul";
		final String spreadsheetId = "1KxijTuuipLER6kKmov4BFEhGsgRydCd1bh-9pzisRQ0";
		// Try a wild range, far outside of what data is present
		final String range = "Users!A2:Z";

		Spreadsheets spreadsheets = sheetsService.spreadsheets();

		BatchGet batchGet = spreadsheets.values().batchGet(spreadsheetId);
		System.err.println("Examine " + batchGet.size() + " batch get entri(es)");
		for (Entry<String, Object> entry : batchGet.entrySet()) {
			System.err.println("key:" + entry.getKey() + "\t" + "value: "
					+ entry.getValue().toString());
		}

		System.err.println("Examine ranges: " + batchGet.getRanges());

		Values values = spreadsheets.values();
		Get get = values.get(spreadsheetId, range);
		System.err.println(
				"Examine spreadsheets in specified id and range:" + get.toString());

		ValueRange response = get.execute();

		List<List<Object>> rows = response.getValues();
		assertThat(rows, notNullValue());
		assertThat(rows.size() != 0, is(true));

		System.err.println("Got " + rows.size() + " value rows");
		for (List<Object> row : rows) {
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

	@Test
	public void createDataFromGoogleSpreadsheetTest() throws IOException {
		final String sheetName = "UserInfo";
		final String spreadsheetId = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
		List<Object[]> result = createDataFromGoogleSpreadsheet(spreadsheetId,
				sheetName);
		
	}

	// preparing the @GoogleSheetParametersProvider container class for testng
	// dataProvider method
	public static List<Object[]> createDataFromGoogleSpreadsheet(
			String spreadsheetId, String sheetName) {
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