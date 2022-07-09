package com.github.sergueik.iniparser.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

// import static com.jcabi.matchers.RegexMatchers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.github.sergueik.iniparser.IniParser;

/**
 * Local file Integration tests of ini parser
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class IniParserUsageIntegrationTest {

	private final String section = "Metrics";
	private String tag = null;
	private String name = null;
	private String expression = null;
	private String key = null;
	private String value = null;
	private Pattern pattern;
	private Matcher matcher;
	private final String line = "rpm: 100 200";

	private static IniParser parser = IniParser.getInstance();
	private static boolean debug = true;
	private static String iniFile = String.format("%s/src/main/resources/%s",
			System.getProperty("user.dir"), "data.ini");
	private static Map<String, Map<String, Object>> data = new HashMap<>();
	private static Map<String, Object> sectionData = new HashMap<>();

	@Before
	public void loadIniFileResource() throws IOException {
		parser.parseFile(iniFile);
		data = parser.getData();
		sectionData = data.get(section);
	}

	@Test
	public void test1() {
		for (String referencedSection : sectionData.keySet()) {
			if (debug)
				System.err.println("Extracting section " + referencedSection);
			Map<String, Object> referencedSectionData = data.get(referencedSection);

			tag = (String) referencedSectionData.get("tag");
			assertThat(tag, notNullValue());
			name = (String) referencedSectionData.get("name");
			assertThat(name, notNullValue());
			expression = (String) referencedSectionData.get("expression");
			assertThat(expression, notNullValue());
		}
	}

	@Test
	public void test2() {
		value = null;

		expression = parser.getString("rpm", "expression");
		name = parser.getString("rpm", "name");
		tag = parser.getString("rpm", "tag");
		pattern = Pattern.compile("^\\s*(?:" + tag + ")" + ": " + expression);
		matcher = pattern.matcher(line);
		if (matcher.find()) {
			key = name;
			value = matcher.group(1);
			if (debug)
				System.err.println(String.format("Found data for metric %s: %s %s", key,
						expression, value));
		} else {
			System.err.println("no match found for " + expression);
		}
		assertThat(value, notNullValue());
		assertThat("unexpected value read: " + value, value, equalTo("200"));
		// java.lang.IllegalArgumentException: Section doesn't exist:
		// rpm_bad_expression
	}

	@Test
	public void test3() {
		value = null;
		expression = parser.getString("rpm_double", "expression");
		name = parser.getString("rpm_double", "name");
		tag = parser.getString("rpm_double", "tag");
		pattern = Pattern.compile("^\\s*(?:" + tag + ")" + ": " + expression);
		matcher = pattern.matcher(line);
		if (matcher.find()) {
			key = name;
			value = matcher.group(1);
			if (debug)
				System.err.println(String.format("Found data for metric %s: %s %s", key,
						expression, value));
		} else {
			System.err.println("no match found for " + expression);
		}
		assertThat(value, nullValue());
	}

}
