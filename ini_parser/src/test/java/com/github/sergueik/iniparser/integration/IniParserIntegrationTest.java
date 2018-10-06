package com.github.sergueik.iniparser.integration;

// import static com.jcabi.matchers.RegexMatchers;
import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.github.sergueik.iniparser.IniParser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Local file Integration tests
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class IniParserIntegrationTest {

	private static IniParser parser = IniParser.getInstance();
	private static Map<String, Object> keywordTable = new HashMap<>();
	private static Set<String> supportedKeywords = new HashSet<>();
	private static String iniFile = null;
	private static List<String> keywordList = new ArrayList<>();
	private static Object[] keywordArray = new Object[] {}; // empty
	private static List<String> supportedKeywordList = new ArrayList<>();

	private static List<Object> result = new ArrayList<>();

	private static boolean debug = false;

	private static String[] helpTopics = { "Testsuite Program creation",
			"Keyword-driven Framework flow creation",
			"Saving and restoring sessions" };
	private static Map<String, String> help;

	@BeforeClass
	public static void loadIniFileResource() throws IOException {
		iniFile = String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), "data.ini");
		parser.parseFile(iniFile);
		Map<String, Map<String, Object>> data = parser.getData();

		keywordTable = data.get("Keywords");
	}

	@Test
	public void basicTest() {
		String data = parser.getString("Section1", "test");
		assertThat(data, notNullValue());
		// assertTrue(supportedKeywords.containsAll(keywordTable.keySet()));
		// assertFalse(keywordTable.keySet().containsAll(supportedKeywords));
	}

}
