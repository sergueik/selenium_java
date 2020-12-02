package com.github.sergueik.junitparams;
/**
 * Copyright 2019 Serguei Kouzmine
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.custom.CustomParameters;

/**
 * @ExcelParameters Annotation interface for ExcelParametersProvider JUnitparams data provider
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@RunWith(JUnitParamsRunner.class)
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = GoogleSheetParametersProvider.class)
public @interface GoogleSheetParameters {
	String filepath();
	String applicationName() default "Google Sheets Example";
	String sheetName() default "*";
	boolean loadEmptyColumns() default false;
	// TODO: parameter for column names
	boolean debug() default false;
	String secretFilePath(); // TODO: add a a default
}