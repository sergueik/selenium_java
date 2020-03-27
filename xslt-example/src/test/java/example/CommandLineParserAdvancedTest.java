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
 * Unit Tests for CommandLineParser 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineParserAdvancedTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	@BeforeClass
	public static void load() {
		commandLineParser = new CommandLineParser();
	}

	private static Map<String, String> argTable = new HashMap<>();
	private static final String[] argsArray = new String[] { "a", "b", "c" };
	private static final List<Object> argsList = new ArrayList<>();

	@BeforeClass
	public static void convertSetsToArrays() {

		for (String arg : argsArray) {
			argTable.put(arg, arg);
			commandLineParser.saveFlagValue(arg);
			argsList.add(String.format("-%s", arg));
			argsList.add(String.format("%s", arg));
		}

	}

	@Test
	public void argumentCollectionTest() {
		commandLineParser
				.parse((String[]) argsList.toArray(new String[argsList.size()]));
		assertTrue(new HashSet<Object>(commandLineParser.getFlags())
				.containsAll(new HashSet<Object>(Arrays.asList(argsArray))));
	}
}