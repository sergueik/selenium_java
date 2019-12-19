package com.github.sergueik.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

public class SheetsServiceUtil {

	private static SheetsServiceUtil instance = new SheetsServiceUtil();

	private SheetsServiceUtil() {
	}

	public static SheetsServiceUtil getInstance() {
		return instance;
	}

	private boolean debug = false;

	private String applicationName = null;

	public void setApplicationName(String data) {
		this.applicationName = data;
	}

	public Sheets getSheetsService()
			throws IOException, GeneralSecurityException {
		Credential credential = GoogleAuthorizeUtil.authorize();
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential)
						.setApplicationName(this.applicationName).build();
	}

}