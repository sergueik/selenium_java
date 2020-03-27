package example;
/**
 * Copyright 2014 - 2019 Serguei Kouzmine
 */

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
// https://self-learning-java-tutorial.blogspot.com/2018/03/hamcrest-arraycontaininginanyorder.html
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import example.CommandLineParser;

/**
 * Test for CommandLineParser 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineParserTest {

	private static String[] argsArray = new String[] { "-a", "42", "-b", "41" };
	// empty
	private static Map<String, String> keywordTable = new HashMap<>();
	private static Set<String> supportedKeywords = new HashSet<>();
	private static String yamlFile = null;
	private static String internalConfiguration = String.format(
			"%s/src/main/resources/%s", System.getProperty("user.dir"),
			"internalConfiguration.yaml");
	// found character '\t(TAB)' that cannot start any token. (Do not use \t(TAB)
	// for indentation)
	private static List<String> keywordList = new ArrayList<>();
	private static Object[] keywordArray = new Object[] {}; // empty
	private static List<String> supportedKeywordList = new ArrayList<>();

	private static List<Object> result = new ArrayList<>();

	private static boolean debug = false;

	private static String[] helpTopics = { "Testsuite Program creation",
			"Keyword-driven Framework flow creation",
			"Saving and restoring sessions" };
	private static Map<String, String> help;
	private static CommandLineParser commandLineParser = new CommandLineParser();

	@BeforeClass
	public static void load() {

	}

	@BeforeClass
	public static void convertSetsToArrays() {

		for (String k : keywordTable.keySet()) {
			keywordList.add(k);
		}
		Collections.sort(keywordList);
		System.err.format("Loaded %d keys from YAML:\n",
				keywordTable.keySet().size());
		if (debug) {
			System.err.println(keywordList);
		}
		// e.g. CLOSE_BROSWER will not be in the keywordTable

		for (String k : supportedKeywords) {
			supportedKeywordList.add(k);
		}
		Collections.sort(supportedKeywordList);
		System.err.format("Loaded %d keys from JAR:\n", supportedKeywords.size());
		if (debug) {
			System.err.println(supportedKeywordList);
			/*
			for (String k : supportedKeywordList) {
				System.err.println("[" + k + "]");
			}
			*/
		}
		keywordArray = new Object[keywordList.size()];
		keywordList.toArray(keywordArray);
		if (debug) {
			System.err.println("Keyword array:");
			for (int cnt = 0; cnt != keywordArray.length; cnt++) {
				System.err.println("[" + keywordArray[cnt] + "]");
			}
		}
	}

	@Test
	public void blankArgumensTest() {
		commandLineParser.parse(new String[] {});
		assertThat(commandLineParser.getNumberOfFlags(), is(0));
	}

	// NOTE: containsAll method operates sets and does not show the outliers
	@Test
	public void argumentCountsTest() {
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfArguments(), is(1));
		System.err.println(
				"Arguments: " + Arrays.asList(commandLineParser.getArguments()));
		assertThat(commandLineParser.getNumberOfFlags(), is(2));

	}

	// NOTE: containsAll method operates sets and does not show the outliers
	@Test
	public void obserreArgumentCountsTest() {
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());

		//
		System.err.println(
				"Arguments: " + Arrays.asList(commandLineParser.getArguments()));
		// assertTrue(supportedKeywords.containsAll(keywordTable.keySet()));
		// assertFalse(keywordTable.keySet().containsAll(supportedKeywords));
	}

}