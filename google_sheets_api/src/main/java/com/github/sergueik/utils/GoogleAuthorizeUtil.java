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

public class GoogleAuthorizeUtil {
	public static Credential authorize()
			throws IOException, GeneralSecurityException {
		String secretFilePath = Paths.get(System.getProperty("user.home"))
				.resolve(".secret").resolve("client_secret.json").toAbsolutePath()
				.toString();
		System.err
				.println("GoogleAuthorizeUtil.authorize() reads credentials from file: "
						+ secretFilePath);
		InputStream in = new FileInputStream(new File(secretFilePath));

		GoogleClientSecrets clientSecrets = GoogleClientSecrets
				.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

		List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
						.setDataStoreFactory(new MemoryDataStoreFactory())
						.setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		return credential;
	}

}