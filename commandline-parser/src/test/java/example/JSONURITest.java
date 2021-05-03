package example;

/**
 * Copyright 2020,2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
// import static org.junit.Assert.assertThat;

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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import example.CommandLineParser;

import org.json.JSONException;
import org.json.JSONObject;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class JSONURITest {

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
		commandLineParser.setValueFormat("GSON");
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

	@Ignore
	// This application is temporarily over its serving quota. Please try again
	// later.
	// @Test
	public void addStringJSONFromUrlTest() {

		argsArray = new String[] { "-in",
				"http://echo.jsontest.com/key/value/one/two" };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		// JSON is valid
		assertThat(new JSONObject(commandLineParser.getFlagValue("in")),
				notNullValue());
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
		assertThat(new JSONObject(commandLineParser.getFlagValue("in")),
				notNullValue());
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

	// Setting valueFormat to: GSON(2)
	// @Test(expected = JsonSyntaxException.class)
	@Test
	public void addBadJSONFromFileTest() {
		commandLineParser.setValueFormat("GSON");
		if (!new File(dataFilePath).exists()) {
			PrintWriter out = openWriter(dataFilePath);
			out.println("\"foo\":\"bar\"");
			out.flush();
			out.close();
		}
		argsArray = new String[] { "-a", "@a.json" };
		@SuppressWarnings("unchecked")
		Map<String, Object> result = new Gson()
				.fromJson(commandLineParser.getFlagValue("a"), Map.class);
		assertThat(result, nullValue());
	}

	@Ignore
	// @Test(expected = JSONException.class)
	@Test
	public void addBadJSONFromFileTest2() {
		commandLineParser.setValueFormat("NONE");
		if (!new File(dataFilePath).exists()) {
			PrintWriter out = openWriter(dataFilePath);
			out.println("\"foo\":\"bar\"");
			out.flush();
			out.close();
		}
		argsArray = new String[] { "-a", "@" + dataFilePath };
		System.err.println("Calling with: " + Arrays.asList(argsArray));
		assertThat(commandLineParser.getFlagValue("a"), is("foo,bar"));
		assertThat(commandLineParser.getFlagValue("a"), notNullValue());
		@SuppressWarnings("unchecked")
		JSONObject result = new JSONObject(commandLineParser.getFlagValue("a"));
	}

	@Test
	public void addDataGSONFromFileTest() {
		commandLineParser.setValueFormat("GSON");
		if (!new File(dataFilePath).exists()) {
			PrintWriter out = openWriter(dataFilePath);
			out.println("{\"foo\":1,\n" + "	\"bar\":2}");
			out.flush();
			out.close();
		}
		argsArray = new String[] { "-in", dataFileUri };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		System.err.println("addDataGSONFromFileTest(): flag value: "
				+ commandLineParser.getFlagValue("in"));
		assertThat(
				new Gson().fromJson(commandLineParser.getFlagValue("in"), Map.class),
				notNullValue());
		Map<String, Object> result = new Gson()
				.fromJson(commandLineParser.getFlagValue("in"), Map.class);
		assertThat(result.keySet().size(), greaterThan(0));
		try {
			@SuppressWarnings("unchecked")
			final Map<String, Object> data = (new Gson())
					.fromJson(commandLineParser.getFlagValue("in"), Map.class);
			assertThat(data.containsKey("foo"), is(true));
			assertThat(data.containsKey("bar"), is(true));
		} catch (JSONException e) {
			System.err.println("Unexpected error: " + e.toString());
			throw e;
		}
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
