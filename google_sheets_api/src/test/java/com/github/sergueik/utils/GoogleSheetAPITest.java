package com.github.sergueik.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CopyPasteRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

// import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class GoogleSheetAPITest {

	private static GoogleSheetAPI sheetAPI;
	private static Sheets sheetsService;

	// this id can be replaced with your spreadsheet id
	// otherwise be advised that multiple people may run this test and update the
	// public spreadsheet.
	// receiving Error 403 is indicator of a wrong SPREADSHEET_ID being used
	// private static final String SPREADSHEET_ID =
	// "1sILuxZUnyl_7-MlNThjt765oWshN3Xs-PPLfqYe4DhI";
	private static final String SPREADSHEET_ID = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
	// https://docs.google.com/spreadsheets/d/1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ/edit
	// Sample spreadsheet to test Reading data via the Google Sheets API

	@BeforeClass
	public static void setup() throws GeneralSecurityException, IOException {
		sheetsService = SheetsServiceUtil.getSheetsService();
	}

	@Ignore
	@Test
	public void whenWriteSheet_thenReadSheetOk() throws IOException {
		ValueRange body = new ValueRange().setValues(Arrays.asList(
				Arrays.asList("Expenses January"), Arrays.asList("books", "30"),
				Arrays.asList("pens", "10"), Arrays.asList("Expenses February"),
				Arrays.asList("clothes", "20"), Arrays.asList("shoes", "5")));
		UpdateValuesResponse result = sheetsService.spreadsheets().values()
				.update(SPREADSHEET_ID, "A1", body).setValueInputOption("RAW")
				.execute();
		/*
		com.google.api.client.googleapis.json.GoogleJsonResponseException: 403 Forbidden
		
		{
		"code" : 403,
		"errors" : [ {
		"domain" : "global",
		"message" : "The caller does not have permission",
		"reason" : "forbidden"
		} ],
		"message" : "The caller does not have permission",
		"status" : "PERMISSION_DENIED"
		}
		 */

		List<ValueRange> data = new ArrayList<>();
		data.add(new ValueRange().setRange("D1")
				.setValues(Arrays.asList(Arrays.asList("January Total", "=B2+B3"))));
		data.add(new ValueRange().setRange("D4")
				.setValues(Arrays.asList(Arrays.asList("February Total", "=B5+B6"))));

		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
				.setValueInputOption("USER_ENTERED").setData(data);
		BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets()
				.values().batchUpdate(SPREADSHEET_ID, batchBody).execute();

		List<String> ranges = Arrays.asList("E1", "E4");
		BatchGetValuesResponse readResult = sheetsService.spreadsheets().values()
				.batchGet(SPREADSHEET_ID).setRanges(ranges).execute();

		ValueRange januaryTotal = readResult.getValueRanges().get(0);
		// assertThat(januaryTotal.getValues().get(0).get(0)).isEqualTo("40");
		assertThat(januaryTotal.getValues().get(0).get(0), equalTo("40"));

		ValueRange febTotal = readResult.getValueRanges().get(1);
		// assertThat(febTotal.getValues().get(0).get(0)).isEqualTo("25");
		assertThat(febTotal.getValues().get(0).get(0), equalTo("25"));
		ValueRange appendBody = new ValueRange()
				.setValues(Arrays.asList(Arrays.asList("Total", "=E1+E4")));
		AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
				.append(SPREADSHEET_ID, "A1", appendBody)
				.setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS")
				.setIncludeValuesInResponse(true).execute();

		ValueRange total = appendResult.getUpdates().getUpdatedData();
		// assertThat(total.getValues().get(0).get(1)).isEqualTo("65");
		assertThat(total.getValues().get(0).get(1), equalTo("65"));

	}

	@Ignore
	@Test
	public void whenUpdateSpreadSheetTitle_thenOk() throws IOException {

		UpdateSpreadsheetPropertiesRequest updateRequest = new UpdateSpreadsheetPropertiesRequest()
				.setFields("*")
				.setProperties(new SpreadsheetProperties().setTitle("Expenses"));

		CopyPasteRequest copyRequest = new CopyPasteRequest()
				.setSource(new GridRange().setSheetId(0).setStartColumnIndex(0)
						.setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1))
				.setDestination(new GridRange().setSheetId(1).setStartColumnIndex(0)
						.setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1))
				.setPasteType("PASTE_VALUES");

		List<Request> requests = new ArrayList<>();

		requests.add(new Request().setCopyPaste(copyRequest));
		requests.add(new Request().setUpdateSpreadsheetProperties(updateRequest));

		BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest()
				.setRequests(requests);

		sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
	}

	@Ignore
	// NOTE: working in the method
	// failing in the identital code called
	// through public method in from the GoogleSheetAPI class
	@Test
	public void readExistingSpreadSheetTest() throws IOException {
		final String userName = "georgeh";
		final String spreadsheetId = "1KxijTuuipLER6kKmov4BFEhGsgRydCd1bh-9pzisRQ0";
		final String range = "Users!A2:C";

		GoogleSheetAPI sheetAPI = new GoogleSheetAPI();

		List<List<Object>> values = sheetAPI.getSpreadSheetRecords(spreadsheetId,
				range);
		/*
		com.google.api.client.auth.oauth2.TokenResponseException: 400 Bad Request
		{
		  "error" : "invalid_grant",
		  "error_description" : "Bad Request"
		}
		*/
		for (List<Object> row : values) {
			if (row.get(0).equals(userName)) {
				System.err.println(row);
				break;
			}
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

	@Ignore
	@Test
	public void createBlankSpreadSheetTest() throws IOException {
		Spreadsheet spreadSheet = new Spreadsheet()
				.setProperties(new SpreadsheetProperties().setTitle("My Spreadsheet"));
		Spreadsheet result = sheetsService.spreadsheets().create(spreadSheet)
				.execute();

		String spreadsheetId = result.getSpreadsheetId();
		// result.getSheets()
		assertThat(spreadsheetId, notNullValue());
		System.err.println(
				String.format("Successfully created spreadsheet: %s", spreadsheetId));
	}
}