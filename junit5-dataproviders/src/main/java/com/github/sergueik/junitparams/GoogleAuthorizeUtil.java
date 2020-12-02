package com.github.sergueik.junitparams;

/**
 * Copyright 2019 Serguei Kouzmine
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

/**
 * Common google api v4 utilities class for testng dataProviders on Google
 * Spreadsheet
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public class GoogleAuthorizeUtil {
	private static Credential credential = null;
	private static boolean debug = false;
	private static Utils utils = Utils.getInstance();

	public static void setDebug(boolean data) {
		GoogleAuthorizeUtil.debug = data;
	}

	public static Credential authorize(String secretFilePath) throws IOException, GeneralSecurityException {
		if (credential == null || credential.getExpirationTimeMilliseconds() < 120_000) {
			System.err.println("GoogleAuthorizeUtil.authorize() reads credentials from file: " + secretFilePath);

			InputStream in = new FileInputStream(new File(secretFilePath));

			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
					new InputStreamReader(in));

			List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets,
					scopes).setDataStoreFactory(new MemoryDataStoreFactory()).setAccessType("offline").build();
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		} else {
			if (debug) {
				System.err.println(String.format("Using cached credential (%s remaining)", utils.getDurationBreakdown(
						credential.getExpirationTimeMilliseconds() - System.currentTimeMillis())));
			}
		}
		return credential;
	}

}