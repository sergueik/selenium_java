package com.github.sergueik.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import java.security.GeneralSecurityException;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.concurrent.TimeUnit;

public class GoogleAuthorizeUtil {
	// TODO: hash with secretFilePath
	private static Credential credential = null;

	private static boolean debug = false;

	public static void setDebug(boolean data) {
		GoogleAuthorizeUtil.debug = data;
	}

	public static Credential authorize(String secretFilePath)
			throws IOException, GeneralSecurityException {
		if (debug) {
			System.err.println(
					"GoogleAuthorizeUtil.authorize() reads credentials from file: "
							+ secretFilePath);
		}
		InputStream in = new FileInputStream(new File(secretFilePath));
		if (credential == null
				|| credential.getExpirationTimeMilliseconds() < 120_000) {
			GoogleClientSecrets clientSecrets = GoogleClientSecrets
					.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

			List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
							.setDataStoreFactory(new MemoryDataStoreFactory())
							.setAccessType("offline").build();
			credential = new AuthorizationCodeInstalledApp(flow,
					new LocalServerReceiver()).authorize("user");
		} else {
			if (debug) {
				System.err
						.println(String.format("Using cached credential (%s remaining)",
								getDurationBreakdown(credential.getExpirationTimeMilliseconds()
										- System.currentTimeMillis())));
			}
			// TODO:
		}
		return credential;
	}

	// origin:
	// https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		sb.append(days);
		sb.append(" Days ");
		sb.append(hours);
		sb.append(" Hours ");
		sb.append(minutes);
		sb.append(" Minutes ");
		sb.append(seconds);
		sb.append(" Seconds");

		return (sb.toString());
	}

}
