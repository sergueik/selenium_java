package com.github.sergueik.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Get;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

// origin: the example http://www.seleniumeasy.com/selenium-tutorials/read-data-from-google-spreadsheet-using-api

public class GoogleSheetAPI {

	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	private static FileDataStoreFactory DATA_STORE_FACTORY;

	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart.
	 * If modifying these scopes, delete your previously saved credentials
	 * at ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays
			.asList(SheetsScopes.SPREADSHEETS_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	// NOTE: the code path leading to invocation of
	// GoogleAuthorizeUtil.authorize() is uncertain

	public static Credential authorize() throws IOException {

		InputStream in = GoogleSheetAPI.class
				.getResourceAsStream("/client_secret.json");
		// no longer used
		String secretFilePath = Paths.get(System.getProperty("user.home"))
				.resolve(".secret").resolve("client_secret.json").toAbsolutePath()
				.toString();
		System.err.println("Credentials(1)  read from " + secretFilePath);
		in = new FileInputStream(new File(secretFilePath));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
						.setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline")
						.build();
		// not reached
		System.err
				.println("The Credentials being created by GoogleSheetAPI.authorize()");
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.err
				.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public List<List<Object>> getSpreadSheetRecords(String spreadsheetId,
			String range) throws IOException {
		Sheets service = getSheetsService();
		Spreadsheets spreadsheets = service.spreadsheets();
		Get get = spreadsheets.values().get(spreadsheetId, range);
		System.err.println(
				"Examine spreadsheets in specified id and range:" + get.toString());

		ValueRange response = get.execute();
		/*
		 {
		"error" : "invalid_grant",
		"error_description" : "Bad Request"
		}
		 */
		List<List<Object>> values = response.getValues();
		if (values != null && values.size() != 0) {
			return values;
		} else {
			System.err.println("No data found.");
			return null;
		}
	}
}
