package com.github.sergueik.junitparams;

/**
 * Copyright 2019 Serguei Kouzmine
 */
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

/**
 * Common google sheet v4 proxy class for testng dataProviders on Google Spreadsheet
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class SheetsServiceUtil {
	private static SheetsServiceUtil instance = new SheetsServiceUtil();

	private SheetsServiceUtil() {
	}

	public static SheetsServiceUtil getInstance() {
		return instance;
	}

	private boolean debug = false;

	public void setDebug(boolean data) {
		this.debug = data;
	}

	private String applicationName = null;

	public void setApplicationName(String data) {
		this.applicationName = data;
	}

	private String secretFilePath = null;

	public void setSecretFilePath(String data) {
		this.secretFilePath = data;
	}

	public Sheets getSheetsService()
			throws IOException, GeneralSecurityException {
		GoogleAuthorizeUtil.setDebug(this.debug);
		Credential credential = GoogleAuthorizeUtil.authorize(this.secretFilePath);
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential)
						.setApplicationName(this.applicationName).build();
	}

}