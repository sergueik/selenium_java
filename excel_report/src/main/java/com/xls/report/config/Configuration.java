package com.xls.report.config;

/**
 * @author - rahul.rathore
 */
public class Configuration {
	public static final String[] aHeader = { "TestCase Method", "Status",
			"Exception", "Exception Message", "Locator" };

	public static final String aTestNode = "test";
	public static final String aClassNode = "class";
	public static final String aTestMethodNode = "test-method";
	public static final String aExceptionNode = "exception";
	public static final String aNameAttribute = "name";
	public static final String aStatusAttribute = "status";
	public static final String aValueAttribute = "value";
	public static final String aDataProviderAttribute = "data-provider";
	public static final String aMessageAttribute = "message";
	public static final String aTransformKeyword = "Build";
	public static final String aTransformKeywordTwo = "For documentation";

	public static final int aFirstIndex = 0;
	public static final int aTestNameIndex = 0;
	public static final int aTestStatusIndex = 1;
	public static final int aExceptionMsgIndex = 2;
	public static final int aExceptionStackTrace = 3;
	public static final int aLocatorIndex = 4;
}
