package example;
/**
 * Copyright 2014 - 2019 Serguei Kouzmine
 */

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * More advanced Unit Tests for CommandLineParser 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineParserAdvancedTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	private static Map<String, String> argTable = new HashMap<>();
	private static final String[] argsArray = new String[] { "a", "b", "c" };
	private static final List<Object> argsList = new ArrayList<>();

	@BeforeClass
	public static void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

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