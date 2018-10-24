package com.github.sergueik.iniparser.integration;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.github.sergueik.iniparser.IniParser;

/**
 * Common functions for integration testing of ini parser
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommonFunctions {
	private static IniParser parser;

	public CommonFunctions(IniParser value) {
		CommonFunctions.parser = value;
	}

	// this method dispatches the input into the function passed as its argument
	public static Object extract(String section, String value,
			Function<List<String>, ? extends Object> func) {
		return func.apply(Arrays.asList(new String[] { section, value }));
	}

	// these methods references will be found by type of the receiver
	public static String getString(List<String> locator) {
		return parser.getString(locator.get(0), locator.get(1));
	}

	public static Integer getInteger(List<String> locator) {
		return parser.getInteger(locator.get(0), locator.get(1));
	}

	public static Boolean getBoolean(List<String> locator) {
		return parser.getBoolean(locator.get(0), locator.get(1));
	}

	public static Float getFloat(List<String> locator) {
		return parser.getFloat(locator.get(0), locator.get(1));
	}
}
