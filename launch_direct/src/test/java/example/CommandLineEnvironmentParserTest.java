package example;

/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import example.CommandLineParser;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineEnvironmentParserTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	@BeforeClass
	public static void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@Test
	public void argumentEnvionmentValueTest() {
		final String[] argsArray = new String[] { "-a", "env:JAVA_HOME", "-b",
				"env:java_home" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.saveFlagValue("b");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"),
				is(System.getenv("JAVA_HOME")));
		assertThat(commandLineParser.hasFlag("b"), is(true));
		assertThat(commandLineParser.getFlagValue("b"),
				is(System.getenv("JAVA_HOME")));
	}

}
