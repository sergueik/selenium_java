package example;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Copyright Copyright 2020,2021 Serguei Kouzmine2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class URITest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;
	private String[] argsArray = {};
	private String arg = null;
	protected static String osName = getOSName();
	private static final String dataFileName = "a.properties";
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
		commandLineParser.setValueFormat("NONE");
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

	@Test
	public void addDataFromFileURITest() {

		PrintWriter out = openWriter(dataFilePath);
		out.println("foo:1\nbar:2\n");
		out.flush();
		out.close();
		argsArray = new String[] { "-in", dataFileUri };
		commandLineParser.saveFlagValue("in");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.getNumberOfFlags(), is(1));
		assertThat(commandLineParser.hasFlag("in"), is(true));
		assertThat(commandLineParser.getFlagValue("in"), notNullValue());
		System.err.println("addDataFromFileURITest(): flag value: "
				+ commandLineParser.getFlagValue("in"));
		String value = commandLineParser.getFlagValue("in");
		assertThat(value, containsString("foo:1"));
		assertThat(value, containsString("bar:2"));
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
