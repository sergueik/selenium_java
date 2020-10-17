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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

	// stop keeping it
	@Before
	public void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@Test
	public void addLoadTest() {
		final String[] argsArray = new String[] { "-a", "42", "-b", "41" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(2));
		assertThat(commandLineParser.getFlagValue("b"), nullValue());
		commandLineParser.saveFlagValue("b");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(2));

		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		assertThat(commandLineParser.hasFlag("b"), is(true));
		assertThat(commandLineParser.getFlagValue("b"), notNullValue());
		assertThat(commandLineParser.getFlagValue("z"), nullValue());
		System.err.println("argumentNamesValuesTest(): arguments: "
				+ Arrays.asList(commandLineParser.getArguments()));
	}

	@Test
	public void blankArgumensTest() {
		commandLineParser.parse(new String[] {});
		if (debug) {
			System.err.println("blankArgumensTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
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
			System.err.println("argumentCountsTest: arguments: "
					+ Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(2));

		if (debug) {
			System.err.println("argumentCountsTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
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
		System.err.println("argumentNamesValuesTest(): arguments: "
				+ Arrays.asList(commandLineParser.getArguments()));
	}

	@Test
	public void argumentValueLessTest() {
		final String[] argsArray = new String[] { "-a", "-b" };
		commandLineParser.saveFlagValue("c");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));
		// TODO: fix processing - argument is lost
		assertThat(commandLineParser.hasFlag("b"), is(true));
		assertThat(commandLineParser.hasFlag("c"), is(false));
		assertThat(commandLineParser.hasFlag("d"), is(false));
		assertThat(commandLineParser.getFlagValue("a"), nullValue());

		if (debug) {
			System.err.println("argumentValueLessTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
		}
	}

	@Test
	public void argumentValueLessMixedTest() {
		final String[] argsArray = new String[] { "-foo", "bar", "-debug",
				"-verbose", "-answer", "42" };
		commandLineParser.saveFlagValue("foo");
		commandLineParser.saveFlagValue("answer");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("debug"), is(true));
		// TODO: fix processing - argument is lost
		assertThat(commandLineParser.hasFlag("verbose"), is(true));
		assertThat(commandLineParser.getFlagValue("debug"), nullValue());
		assertThat(commandLineParser.getFlagValue("verbose"), nullValue());
		assertThat(commandLineParser.hasFlag("dummy"), is(false));
		assertThat(commandLineParser.hasFlag("foo"), is(true));
		assertThat(commandLineParser.hasFlag("answer"), is(true));

		if (debug) {
			System.err.println("argumentValueLessTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
		}
	}

	@Test
	public void extractExtraArgsTest() {
		final String[] argsArray = new String[] { "-a",
				"{count:0, type: navigate, size:100.1, flag:true}" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		assertThat(
				commandLineParser.extractExtraArgs(commandLineParser.getFlagValue("a")),
				notNullValue());
		final Map<String, String> extraArgData = commandLineParser
				.extractExtraArgs(commandLineParser.getFlagValue("a"));
		assertThat(extraArgData.containsKey("count"), is(true));
		assertThat(extraArgData.containsKey("type"), is(true));
		assertThat(extraArgData.containsKey("size"), is(true));
		assertThat(extraArgData.containsKey("flag"), is(true));
		assertThat(extraArgData.containsKey("missing"), is(false));

	}

	// https://www.baeldung.com/junit-assert-exception
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void argumentExtraArgsGuardTest() {
		final String[] argsArray = new String[] { "-a",
				"{count:0,deep:{key:value}}" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		final Map<String, String> extraArgData = commandLineParser
				.extractExtraArgs(commandLineParser.getFlagValue("a"));
	}

	// TODO: enabling the test below leads two other tests to start failing:
	// argumentValueLessTest, argumentCountsTest
	@Ignore
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

