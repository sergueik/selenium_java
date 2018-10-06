package com.github.sergueik.iniparser.integration;

// import static com.jcabi.matchers.RegexMatchers;

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
 * Local file Integration tests of iniparser
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class IniParserIntegrationTest {

	private static IniParser parser = IniParser.getInstance();
	private static boolean debug = false;
	private static String iniFile = String.format("%s/src/main/resources/%s",
			System.getProperty("user.dir"), "data.ini");
	private static Map<String, Map<String, Object>> data = new HashMap<>();

	private static Map<String, Object> sectionData = new HashMap<>();

	private static Set<String> sections = new HashSet<>();
	static {
		sections.add("Section1");
		sections.add("Section 2");
		sections.add("Section 3");
	}
	private static List<String> keywordList = new ArrayList<>();
	private static Object[] keywordArray = new Object[] {}; // empty
	private static List<String> supportedKeywordList = new ArrayList<>();

	private static List<Object> result = new ArrayList<>();

	private static String[] helpTopics = { "Testsuite Program creation",
			"Keyword-driven Framework flow creation",
			"Saving and restoring sessions" };
	private static Map<String, String> help;

	@BeforeClass
	public static void loadIniFileResource() throws IOException {
		parser.parseFile(iniFile);
		data = parser.getData();
		sectionData = data.get("Section1");
	}

	@Test
	public void basicTest() {
		String value = parser.getString("Section1", "message");
		assertThat(value, notNullValue());
		// assertFalse(keywordTable.keySet().containsAll(supportedKeywords));
	}

	@Test
	public void dataTest() {
		String value = (String) sectionData.get("message");
		assertThat(value, equalTo("data"));
		assertTrue(sections.containsAll(data.keySet()));
		assertFalse(data.keySet().containsAll(sections));
	}

	private static Map<String, Function<List<String>, ? extends Object>> dataExtractors = new HashMap<>();
	static {
		dataExtractors.put("string", IniParserIntegrationTest::getString);
		dataExtractors.put("boolean", IniParserIntegrationTest::getBoolean);
		dataExtractors.put("integer", IniParserIntegrationTest::getInteger);
		dataExtractors.put("int", IniParserIntegrationTest::getInteger);
		// for result.TYPE.getName()
		dataExtractors.put("java.lang.Integer",
				IniParserIntegrationTest::getInteger);
		// for result.getClass().getName()
	}

	@Test
	public void sectionEntriesTest() {
		parser.setDebug(true);
		Map<String, String> entryTypes = new HashMap<>();
		entryTypes.put("message", "String");
		entryTypes.put("flag", "boolean");
		entryTypes.put("empty", "String");
		entryTypes.put("number", "Integer");
		String section = "Section1";

		Object value = null;
		String key = "message";
		value = extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo("data"));
		key = "number";
		value = extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo(42));
		key = "flag";
		value = extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo(true));
		key = "empty";
		value = extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo(""));
	}

	@Test
	public void typedEntryVaueExtractionTest() {
		String section = "Section1";
		parser.setDebug(true);
		Integer result = 0;

		System.err.println("result type name: " + result.getClass().getName());

		String key = "number";
		result = (Integer) extract(section, key,
				dataExtractors.get(result.TYPE.getName().toLowerCase()));
		assertThat(result, equalTo(42));
		result = (Integer) extract(section, key,
				dataExtractors.get(result.getClass().getName()));
		assertThat(result, equalTo(42));
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
