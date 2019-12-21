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
		if (debug) {
			System.err.println("Examine spreadsheets in specified id: " + id
					+ " and range: " + range);
		}
		// wild guess
		Get get = sheetsService.spreadsheets().values().get(id, "*!A2:Z");
		if (debug) {
			System.err.println(
					"Examine spreadsheets url: " + get.buildHttpRequestUrl().build());
		}
		get = sheetsService.spreadsheets().values().get(id, range);
		// NOTE: when attempted to open in the browser the url
		// https://sheets.googleapis.com/v4/spreadsheets/1KxijTuuipLER6kKmov4BFEhGsgRydCd1bh-9pzisRQ0/values/Users!A2:C
		// status will be
		// "code": 403,
		// "message": "The request is missing a valid API key.",
		// "status": "PERMISSION_DENIED"
		// when using the wrong in the speadhseet name in the range argument
		/*
		 "code" : 400,
		"errors" : [ {
		  "domain" : "global",
		  "message" : "Unable to parse range: UserInfo!A1:Z",
		  "reason" : "badRequest"
		} ],
		 */

		ValueRange response = get.execute();
		// added intermediate variables for later exploring api
		List<List<Object>> valueRows = response.getValues();
		assertThat(valueRows, notNullValue());
		assertThat(valueRows.size() != 0, is(true));

		System.err.println("Reading " + valueRows.size() + " value rows");
		// for (List<Object> valueRow : valueRows) {
		// System.err.println("Got: " + valueRow);
		// }
	}

	@Test
	public void test2() throws IOException {
		final String spreadsheetId = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
		final String range = "UserInfo!A2:D";
		List<List<Object>> valueRows = sheetsService.spreadsheets().values()
				.get(spreadsheetId, range).execute().getValues();
		assertThat(valueRows, notNullValue());
		assertThat(valueRows.size() != 0, is(true));

		System.err.println("Reading " + valueRows.size() + " value rows");
		for (List<Object> valueRow : valueRows) {
			System.err.println("Got: " + valueRow);
		}
	}

	@Test
	public void test3() throws IOException {
		final String sheetName = "Test Data";
		final String spreadsheetId = "17ImW6iKSF7g-iMvPzeK4Zai9PV-lLvMsZkl6FEkytRg";

		final String range = String.format("%s!A1:Z", sheetName);
		List<List<Object>> headerRows = sheetsService.spreadsheets().values()
				.get(spreadsheetId, range).execute().getValues();
		assertThat(headerRows, notNullValue());
		assertThat(headerRows.size() != 0, is(true));

		System.err.println("Reading " + headerRows.size() + " header rows");
		for (List<Object> headerRow : headerRows) {
			System.err.println("Got: " + headerRow);
		}

		List<Object[]> result = readGoogleSpreadsheet(spreadsheetId, sheetName);
		for (Object[] resultRow : result) {
			Integer numberOfCols = resultRow.length;
			for (int col = 0; col != numberOfCols; col++) {
				System.err
						.println(String.format("column[%d]: %s ", col, resultRow[col]));
			}
		}
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
