package example;

/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import example.CommandLineParser;
import static org.hamcrest.Matchers.hasItems;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class CommandLineApplyArgumentTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;
	private String[] argsArray = {};
	private String arg = null;

	// stop keeping it
	@Before
	public void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@Test
	public void addLoadTest() {
		argsArray = new String[] { "-apply",
			// @formatter:off		
		"---\n" 
		+ "a: 1\n" 
		+ "b: true\n" 
		+ "c: string" 
		// @formatter:on
		};
		commandLineParser.saveFlagValue("apply");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.getFlagValue("apply"), notNullValue());
		assertThat(commandLineParser.processApply(), notNullValue());
		Map<String, Object> data = commandLineParser.processApply();
		final String[] keywordArray = new String[] { "a", "b", "c" };
		assertThat(data.keySet(), hasItems(keywordArray));

		// System.err.println("argumentNamesValuesTest(): apply arguments: "
		// + Arrays.asList(commandLineParser.processApply().));
	}
}
