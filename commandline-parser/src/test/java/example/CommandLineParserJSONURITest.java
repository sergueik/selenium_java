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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import example.CommandLineParser;

import org.json.JSONException;
import org.json.JSONObject;

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
	protected static String osName = getOSName();
	private static final String dataFileName = "a.json";
	private static final String dataFilePath = osName.equals("windows")
			? Paths.get(System.getProperty("user.home")).resolve("Desktop")
					.resolve(dataFileName).toString()
			: "/tmp/" + dataFileName;

	private static final String dataFileUri = osName.equals("windows")
			? "file:///" + dataFilePath.replaceAll("\\\\", "/")
			: "file://" + dataFilePath;
	private static File file;

	// stop keeping it
	@Before
	public void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@SuppressWarnings("unused")
	@AfterClass
	public static void cleanup() throws FileNotFoundException, IOException {

		if (!file.exists())
			throw new FileNotFoundException(file.getAbsolutePath());
		if (!file.delete()) {
			Object[] filler = { file.getAbsolutePath() };
			throw new IOException("Delete failed: " + file.getAbsolutePath());
		}

	}
	// early tests created for GSON

	@Test
	public void addStringJSONFromUrlTest() {

		argsArray = new String[] { "-in",
				"http://echo.jsontest.com/key/value/one/two" };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		// NOTE: whitespace and indent-sensitive
		// assertThat(commandLineParser.getFlagValue("in"),
		// is("{\"one\":\"two\",\"key\":\"value\"}"));
		// early tests created for GSON
		// assertThat(commandLineParser.getFlagValue("in"), is("one|key"));
		try {
			JSONObject jsonObject = new JSONObject(
					commandLineParser.getFlagValue("in"));
			assertThat(jsonObject.has("one"), is(true));
			assertThat(jsonObject.has("key"), is(true));
		} catch (JSONException e) {
			System.err.println("Unexpected error: " + e.toString());
			throw e;
		}
		System.err.println("argumentNamesValuesTest(): flag value: "
				+ commandLineParser.getFlagValue("in"));
	}

	@Test
	public void addDataJSONFromFileTest() {

		PrintWriter out = openWriter(dataFilePath);
		// TODO: debug
		// addDataJSONFromFileTest(example.CommandLineParserJSONURITest):
		// A JSONObject text must begin with '{' at 1 [character 2 line 1]
		// obseved with Lib.NONE Lib.GSON
		out.println("{\"foo\":1,\n" + "	\"bar\":2}");
		out.flush();
		out.close();
		argsArray = new String[] { "-in", dataFileUri };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		System.err.println("addDataJSONFromFileTest(): flag value: "
				+ commandLineParser.getFlagValue("in"));
		try {
			JSONObject jsonObject = new JSONObject(
					commandLineParser.getFlagValue("in"));
			assertThat(jsonObject.has("foo"), is(true));
			assertThat(jsonObject.has("bar"), is(true));
		} catch (JSONException e) {
			System.err.println("Unexpected error: " + e.toString());
			throw e;
		}
		System.err.println("argumentNamesValuesTest(): flag value: "
				+ commandLineParser.getFlagValue("in"));
	}

	private static PrintWriter openWriter(String name) {
		try {
			boolean append = true;
			file = new File(name);
			OutputStream otputStream = new FileOutputStream(file, append);
			PrintWriter out = new PrintWriter(
					new OutputStreamWriter(otputStream, "UTF8"));
			return out;
		} catch (IOException e) {
			System.err.println("I/O Error");
			System.exit(0);
		}
		return null;
	}

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
