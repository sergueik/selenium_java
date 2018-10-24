package com.github.sergueik.iniparser.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

// import static com.jcabi.matchers.RegexMatchers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.sergueik.iniparser.IniNewParser;

/**
 * Local file Integration tests of modified ini parser
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class IniNewParserIntegrationTest {

	private static IniNewParser parser = IniNewParser.getInstance();
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

	private static String[] entries = { "message", "flag", "number", "empty" };
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

}
