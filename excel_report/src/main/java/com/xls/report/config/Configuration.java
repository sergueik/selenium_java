package com.xls.report.config;
//based on: https://github.com/rahulrathore44/ExcelReportGenerator

public class Configuration {
	public static final String[] header = { "TestCase Method", "Status",
			"Exception", "Exception Message", "Locator" };

	public static final String testNode = "test";
	public static final String classNode = "class";
	public static final String testMethodNode = "test-method";
	public static final String exceptionNode = "exception";
	public static final String nameAttribute = "name";
	public static final String statusAttribute = "status";
	public static final String valueAttribute = "value";
	public static final String dataProviderAttribute = "data-provider";
	public static final String messageAttribute = "message";
	public static final String transformKeyword = "Build";
	public static final String transformKeywordTwo = "For documentation";

	public static final int firstIndex = 0;
	public static final int testNameIndex = 0;
	public static final int testStatusIndex = 1;
	public static final int exceptionMsgIndex = 2;
	public static final int exceptionStackTrace = 3;
	public static final int locatorIndex = 4;
}
