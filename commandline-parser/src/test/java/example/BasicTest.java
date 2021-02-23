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

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class BasicTest {

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
		argsArray = new String[] { "-a", "42", "-b", "41" };
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
			System.err.println("blankArgumensTest: pass-through arguments: "
					+ Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(0));
		assertThat(commandLineParser.getArguments(), is(new String[] {}));
	}

	@Test
	public void getArgumensTest() {
		commandLineParser.parse(new String[] { "a", "b", "c" });
		if (debug) {
			System.err.println("getArgumensTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
			System.err.println("getArgumensTest: pass-through arguments: "
					+ Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(0));
		assertThat(commandLineParser.getArguments(), notNullValue());
		assertThat(commandLineParser.getArguments().length, is(3));
	}

	@Test
	public void argumentCountsTest() {
		argsArray = new String[] { "-a", "42", "-b", "41" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfArguments(), is(1));
		if (debug)
			System.err.println("argumentCountsTest: arguments: "
					+ Arrays.asList(commandLineParser.getArguments()));

		assertThat(commandLineParser.getNumberOfFlags(), is(2));

		if (debug)
			System.err.println("argumentCountsTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));

	}

	@Test
	public void argumentNamesValuesTest() {

		argsArray = new String[] { "-a", "42", "-b", "41" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);

		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		assertThat(commandLineParser.getFlagValue("z"), nullValue());
		System.err.println("argumentNamesValuesTest(): arguments: "
				+ Arrays.asList(commandLineParser.getArguments()));
	}

	@Test
	public void getLastArgumenTest() {
		commandLineParser.saveFlagValue("a");
		commandLineParser.saveFlagValue("c");
		commandLineParser.parse(new String[] { "-a", "b", "-c", "-" });
		if (debug) {
			System.err.println("getArgumensTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
			System.err.println("getArgumensTest: pass-through arguments: "
					+ Arrays.asList(commandLineParser.getArguments()));
		}
		assertThat(commandLineParser.getNumberOfFlags(), is(2));
		assertThat(commandLineParser.getArguments(), is(new String[] {}));
		assertThat(commandLineParser.hasFlag("c"), is(true));
		assertThat(commandLineParser.getFlagValue("c"), notNullValue());
		assertThat(commandLineParser.getFlagValue("c"), is("-"));
	}

	@Test
	public void argumentValueLessTest() {
		argsArray = new String[] { "-a", "-b" };
		commandLineParser.saveFlagValue("c");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));

		assertThat(commandLineParser.hasFlag("b"), is(true));
		// TODO: fix processing - argument "c" is lost
		assertThat(commandLineParser.hasFlag("c"), is(false));
		assertThat(commandLineParser.hasFlag("d"), is(false));
		assertThat(commandLineParser.getFlagValue("a"), nullValue());

		if (debug)
			System.err.println("argumentValueLessTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
	}

	@Test
	public void argumentValueLessMixedTest() {
		argsArray = new String[] { "-foo", "bar", "-debug", "-verbose", "-answer",
				"42" };
		commandLineParser.saveFlagValue("foo");
		commandLineParser.saveFlagValue("answer");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("debug"), is(true));
		assertThat(commandLineParser.hasFlag("verbose"), is(true));
		assertThat(commandLineParser.getFlagValue("debug"), nullValue());
		assertThat(commandLineParser.getFlagValue("verbose"), nullValue());
		assertThat(commandLineParser.hasFlag("dummy"), is(false));
		assertThat(commandLineParser.hasFlag("foo"), is(true));
		assertThat(commandLineParser.hasFlag("answer"), is(true));
		assertThat(commandLineParser.getFlagValue("answer"), is("42"));

		if (debug)
			System.err.println("argumentValueLessTest: flags: "
					+ Arrays.asList(commandLineParser.getFlags()));
	}

	@Test
	public void extractExtraArgsTest() {
		argsArray = new String[] { "-a",
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
	@SuppressWarnings("unused")
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void argumentExtraArgsGuardTest() {
		argsArray = new String[] { "-a", "{count:0,deep:{key:value}}" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		final Map<String, String> extraArgData = commandLineParser
				.extractExtraArgs(commandLineParser.getFlagValue("a"));
	}
	// TODO: https://m.habr.com/ru/post/346782/
	// Java 8 Optional: Java 8 Кот Шрёдингера

	// TODO: enabling the test below leads two other tests to start failing:
	// argumentValueLessTest, argumentCountsTest
	@Ignore
	@Test
	public void argumentEnvionmentValueTest() {
		argsArray = new String[] { "-a", "env:JAVA_HOME", "-b", "env:java_home" };
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

	@Test
	public void embeddedArgTest1() {
		arg = "a=b,c=d,e=f";
		Map<String, String> result = commandLineParser.parseEmbeddedMultiArg(arg);
		assertThat(result.keySet().size(), is(3));
	}

	@Test
	public void embeddedArgTest2() {
		arg = "a = b,c  =	d, e=f";
		Map<String, String> result = commandLineParser.parseEmbeddedMultiArg(arg);
		assertThat(result.keySet().size(), is(3));
		assertThat(result.keySet(), hasItems(new String[] { "a", "c", "e" }));
	}

	@Test
	public void embeddedArgTest3() {
		arg = "a=b,c=d,e=f";
		Map<String, String> result = commandLineParser.parseEmbeddedMultiArg2(arg);
		assertThat(result.keySet().size(), is(3));
	}

	@Test
	public void embeddedArgRejectTest1() {
		arg = "a=1,a=2,a=3";
		Map<String, String> result = commandLineParser.parseEmbeddedMultiArg(arg);
		assertThat(result, nullValue());
	}

	@Test
	public void embeddedArgRejectTest2() {
		arg = "a=1,a=2,a=3";
		Map<String, String> result = commandLineParser.parseEmbeddedMultiArg2(arg);
		assertThat(result, nullValue());
	}

	@Test
	public void valueFormatTest1() {
		assertThat(commandLineParser.getValueFormat(), is("NONE"));
	}

	@Test
	public void valueFormatTest2() {
		commandLineParser.setValueFormat("GSON");
		assertThat(commandLineParser.getValueFormat(), is("GSON"));
		commandLineParser.setValueFormat("JSON");
		assertThat(commandLineParser.getValueFormat(), is("JSON"));
	}

	@Test
	public void valueFormatTest3() {
		commandLineParser.setValueFormat(example.CommandLineParser.Lib.GSON);
		assertThat(commandLineParser.getValueFormat(), is("GSON"));
	}

}
