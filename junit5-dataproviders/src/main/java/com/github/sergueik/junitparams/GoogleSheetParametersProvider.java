package com.github.sergueik.junitparams;
/**
 * Copyright 2017-209 Serguei Kouzmine
 */

import java.io.IOException;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;

import junitparams.JUnitParamsRunner;
import junitparams.custom.ParametersProvider;

/**
 * ExcelParametersProvider JUnitparams data providers for MS Excel and
 * OpenOffice spreadsheet files
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
@RunWith(JUnitParamsRunner.class)
public class GoogleSheetParametersProvider
		implements ParametersProvider<GoogleSheetParameters> {

	private Utils utils = Utils.getInstance();

	// store the spreadsheet id through the path parameter:
	// with Google Sheet it has no verbatim meaning
	private String spreadsheetId = null;
	// use name parameter
	private String applicationName = "Google Sheets Example";
	private String sheetName = "*";
	private String secretFilePath = null;

	private String columnNames = "*";
	private boolean debug = false;

	// TODO: pass flag to skip / collect the first row through ExcelParameters
	// interface annotation - may be an overkill
	// private static Boolean skipFirstRow = false;
	@Override
	public void initialize(GoogleSheetParameters parametersAnnotation,
			FrameworkMethod frameworkMethod) {
		applicationName = parametersAnnotation.applicationName();
		spreadsheetId = parametersAnnotation.filepath();
		sheetName = parametersAnnotation.sheetName();
		secretFilePath = parametersAnnotation.secretFilePath();
		debug = parametersAnnotation.debug();
		utils.setDebug(debug);
		utils.setApplicationName(applicationName);
		utils.setSecretFilePath(secretFilePath);
		utils.setSheetName(sheetName);
		utils.setColumnNames(columnNames);
	}

	@Override
	public Object[] getParameters() {
		List<Object[]> result = utils
				.createDataFromGoogleSpreadsheet(spreadsheetId);
		return result.toArray();
	}

}
