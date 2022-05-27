package com.github.sergueik.iniparser.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

// import static com.jcabi.matchers.RegexMatchers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.github.sergueik.iniparser.IniNewParser;

/**
 * Local file Integration tests of modified ini parser
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class IniNewParserIntegrationTest {

	private static IniNewParser parser = IniNewParser.getInstance();
	private static String iniFile = String.format("%s/src/main/resources/%s",
			System.getProperty("user.dir"), "data.ini");
	private static Map<String, Map<String, Object>> data = new HashMap<>();
	private static Map<String, Object> sectionData = new HashMap<>();
	private static String[] entries = { "message", "flag", "number", "empty" };
	private static Set<String> sections = new HashSet<>();
	static {
		sections.add("Section1");
		sections.add("Section 2");
		sections.add("Section 3");
	}

	@Before
	public void loadIniFileResource() throws IOException {
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

	// @Ignore
	@Test
	public void dataTest() {
		String value = (String) sectionData.get("message");
		System.err.println(
				"Got section data keys:" + Arrays.asList(sectionData.keySet()));
		assertThat("unexpected value: " + value, value, equalTo("data"));
		assertTrue(
				"unexpected (extra?) keys: " + Arrays.asList(sectionData.keySet()),
				sectionData.keySet().containsAll(Arrays.asList(entries)));
		assertFalse(data.keySet().containsAll(sections));
	}

}
