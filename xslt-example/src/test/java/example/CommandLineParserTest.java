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
import org.junit.Test;
import example.CommandLineParser;

/**
 * Unit Tests for CommandLineParser
 * 
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
			System.err.println("blankArgumensTest: flags: " + Arrays.asList(commandLineParser.getFlags()));
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
			System.err.println("argumentCountsTest: arguments: " + Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(2));

		if (debug) {
			System.err.println("argumentCountsTest: flags: " + Arrays.asList(commandLineParser.getFlags()));
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
		System.err.println("argumentNamesValuesTest(): arguments: " + Arrays.asList(commandLineParser.getArguments()));
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
			System.err.println("argumentValueLessTest: flags: " + Arrays.asList(commandLineParser.getFlags()));
		}
	}

	@Test
	public void argumentextractExtraArgsTest() {
		final String[] argsArray = new String[] { "-a",
				"{count:0, type: navigate, size:100.1, flag:true, deep:{ignore:true}}" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		assertThat(commandLineParser.extractExtraArgs(commandLineParser.getFlagValue("a")), notNullValue());
		final Map<String, String> extraArgData = commandLineParser
				.extractExtraArgs(commandLineParser.getFlagValue("a"));
		assertThat(extraArgData.containsKey("count"), is(true));
		assertThat(extraArgData.containsKey("type"), is(true));
		assertThat(extraArgData.containsKey("size"), is(true));
		assertThat(extraArgData.containsKey("flag"), is(true));
		assertThat(extraArgData.containsKey("deep"), is(false));

	}
}
