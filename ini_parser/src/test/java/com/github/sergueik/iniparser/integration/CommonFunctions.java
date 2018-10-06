package com.github.sergueik.iniparser.integration;

//import static com.jcabi.matchers.RegexMatchers;

import java.io.IOException;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.github.sergueik.iniparser.IniParser;

/**
 * Common functions for integration testing of iniparser
 * 
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
