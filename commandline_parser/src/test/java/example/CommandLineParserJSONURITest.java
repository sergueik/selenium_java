package example;

/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CommandLineParserJSONURITest {

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
	public void addFromFileTest() {

		PrintWriter out = openWriter("/tmp/a.json");
		out.println("{\"foo\":1,\n" + "	\"bar\":2}");
		out.close();
		argsArray = new String[] { "-in", "file:///tmp/a.json" };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		assertThat(commandLineParser.getFlagValue("in"), is("foo|bar"));
		System.err.println("argumentNamesValuesTest(): flag value: " + commandLineParser.getFlagValue("in"));
	}

	@Test
	public void addFromUrlTest() {

		argsArray = new String[] { "-in", "http://echo.jsontest.com/key/value/one/two" };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		assertThat(commandLineParser.getFlagValue("in"), is("one|key"));
		System.err.println("argumentNamesValuesTest(): flag value: " + commandLineParser.getFlagValue("in"));
	}

	private static PrintWriter openWriter(String name) {
		try {
			File file = new File(name);
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)), true);
			return out;
		} catch (IOException e) {
			System.err.println("I/O Error");
			System.exit(0);
		}
		return null;
	}

}
