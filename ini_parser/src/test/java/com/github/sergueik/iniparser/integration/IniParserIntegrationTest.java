package com.github.sergueik.iniparser.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
// NOTE:
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
import java.util.function.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.sergueik.iniparser.IniParser;

/**
 * Local file Integration tests of ini parser
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class IniParserIntegrationTest {

	private static IniParser parser = IniParser.getInstance();
	private static boolean debug = false;
	private static String iniFile = String.format("%s/src/main/resources/%s",
			System.getProperty("user.dir"), "data.ini");
	private static Map<String, Map<String, Object>> data = new HashMap<>();
	private CommonFunctions helper = new CommonFunctions(parser);
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

	@Test
	public void dataTest() {
		// trouble with @BeforeClass - the sectionData was redefined
		String value = (String) sectionData.get("message");
		System.err.println(
				"Got section data keys:" + Arrays.asList(sectionData.keySet()));
		assertThat("unexpected value: " + value, value, equalTo("data"));
		// considerds a comment to be a sectiondata key
		// assertTrue(sections.containsAll(data.keySet()));
		assertFalse(data.keySet().containsAll(sections));
	}

	private static Map<String, Function<List<String>, ? extends Object>> dataExtractors = new HashMap<>();
	static {
		dataExtractors.put("string", CommonFunctions::getString);
		dataExtractors.put("boolean", CommonFunctions::getBoolean);
		dataExtractors.put("integer", CommonFunctions::getInteger);
		dataExtractors.put("int", CommonFunctions::getInteger);
		// for result.TYPE.getName()
		dataExtractors.put("java.lang.Integer", CommonFunctions::getInteger);
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
		value = CommonFunctions.extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo("data"));
		key = "number";
		value = CommonFunctions.extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo(42));
		key = "flag";
		value = CommonFunctions.extract(section, key,
				dataExtractors.get(entryTypes.get(key).toLowerCase()));
		assertThat(value, equalTo(true));
		key = "empty";
		// see
		// https://www.codementor.io/eh3rrera/using-java-8-method-reference-du10866vx
		// about apply with instance
		value = CommonFunctions.extract(section, key,
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
		result = (Integer) CommonFunctions.extract(section, key,
				dataExtractors.get(result.TYPE.getName().toLowerCase()));
		assertThat(result, equalTo(42));
		result = (Integer) CommonFunctions.extract(section, key,
				dataExtractors.get(result.getClass().getName()));
		assertThat(result, equalTo(42));
	}

}
