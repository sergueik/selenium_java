package example;
/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit Tests for CommandLineParser 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineParserTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	@BeforeClass
	public static void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@Test
	public void blankArgumensTest() {
		commandLineParser.parse(new String[] {});
		if (debug) {
			System.err
					.println("Flags: " + Arrays.asList(commandLineParser.getFlags()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(0));
	}

	@Test
	public void argumentCountsTest() {
		final String[] argsArray = new String[] { "-a", "42", "-b", "41" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfArguments(), is(1));
		if (debug) {
			System.err.println(
					"Arguments: " + Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(2));

		if (debug) {
			System.err
					.println("Flags: " + Arrays.asList(commandLineParser.getFlags()));
		}
	}

	@Test
	public void argumentNamesValuesTest() {

		final String[] argsArray = new String[] { "-a", "42", "-b", "41" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);

		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		assertThat(commandLineParser.getFlagValue("z"), nullValue());
		System.err.println(
				"Arguments: " + Arrays.asList(commandLineParser.getArguments()));
	}

	@Test
	public void argumentValueLessTest() {
		final String[] argsArray = new String[] { "-a", "-b" };
		commandLineParser.saveFlagValue("c");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.hasFlag("b"), is(true));
		assertThat(commandLineParser.hasFlag("c"), is(false));
		assertThat(commandLineParser.hasFlag("d"), is(false));
		assertThat(commandLineParser.getFlagValue("a"), nullValue());

		if (debug) {
			System.err
					.println("Flags: " + Arrays.asList(commandLineParser.getFlags()));
		}
	}
}
