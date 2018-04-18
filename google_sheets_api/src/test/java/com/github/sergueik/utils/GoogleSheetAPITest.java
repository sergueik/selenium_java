package com.github.sergueik.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.github.sergueik.utils.GoogleSheetAPI;

public class GoogleSheetAPITest {

	private static GoogleSheetAPI sheetAPI;
	private String spreadsheetId = "1Ra_emAL4FGw5rYEdbpIuXA5gBLx8xQlWZdwbeatjscQ";
	private String range = "UserInfo!A2:D";
	String userName = "johnl";

	@BeforeClass
	public static void testSetup() {
		sheetAPI = new GoogleSheetAPI();
	}

	// Verify ability to load spreadsheet data
	// @Ignore
	@Test
	public void loadSpreadsheetDataTest() throws IOException {
		String[] userProfileInfo = new String[] { "johnl", "John", "Lennon", "20" };
		List<String> userData = Arrays.asList(userProfileInfo);
		List<List<Object>> values = sheetAPI.getSpreadSheetRecords(spreadsheetId,
				range); // all data is returned, range is ignored
		for (List<Object> row : values) {
			System.err.println("Read row of data: " + row);
			if (row.get(0).equals(userName)) {
				Assert.assertEquals(userData, row);
				// break;
			}
		}
	}

	@AfterClass
	public static void tearDown() {
	}

}