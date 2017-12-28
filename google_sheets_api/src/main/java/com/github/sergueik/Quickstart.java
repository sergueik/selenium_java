package com.github.sergueik;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;

public class Quickstart {
	/** Application name. */
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart.
	 *
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

	/**
	  * Creates an authorized Credential object.
	  * @return an authorized Credential object.
	  * @throws IOException
	  */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		String fileName = "client_secret.json";

		InputStream in = Quickstart.class
				.getResourceAsStream("/client_secret.json");
		/*
		in = Files.newInputStream(Paths.get(String.format("%s/src/main/resources/%s", System.getProperty("user.dir"), fileName)));
		*/
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
						.setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline")
						.build();
		/*
		Exception in thread "main" java.lang.IllegalArgumentException
        at com.google.api.client.repackaged.com.google.common.base.Preconditions
.checkArgument(Preconditions.java:111)
        at com.google.api.client.util.Preconditions.checkArgument(Preconditions.
java:37)
        at com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.getD
etails(GoogleClientSecrets.java:82)
        at com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeF
low$Builder.<init>(GoogleAuthorizationCodeFlow.java:195)
        at com.github.sergueik.Quickstart.authorize(Quickstart.java:82)
        at com.github.sergueik.Quickstart.getSheetsService(Quickstart.java:99)
        at com.github.sergueik.Quickstart.main(Quickstart.java:106) 
		 */
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.out
				.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public static void main(String[] args) throws IOException {
		// Build a new authorized API client service.
		Sheets service = getSheetsService();

		// Prints the names and majors of students in a sample spreadsheet:
		// https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
		String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
		String range = "Class Data!A2:E";
		ValueRange response = service.spreadsheets().values()
				.get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		} else {
			System.out.println("Name, Major");
			for (List row : values) {
				// Print columns A and E, which correspond to indices 0 and 4.
				System.out.printf("%s, %s\n", row.get(0), row.get(4));
			}
		}
	}

	/*
	 
	 OAuth client
	 Here is your client ID
	 1024570958289-j5vs05duot5i0l7ccruk38j8ik2p99q8.apps.googleusercontent.com
	 Here is your client secret
	 ZAVkzXVxjbjaeVrCRo63Nj_X
	 */
}
