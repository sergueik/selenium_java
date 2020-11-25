package example;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.IllegalStateException;
import java.net.URL;

// package org.apache.commons.io does not exist

// import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

// based on: http://www.java2s.com/Code/Java/Development-Class/ArepresentationofthecommandlineargumentspassedtoaJavaclassmainStringmethod.htm
// see also: 
// https://github.com/freehep/freehep-argv/blob/master/src/main/java/org/freehep/util/argv/ArgumentParser.java

public class CommandLineParser {

	private boolean debug = false;
	private static final String keyValueSeparator = ":";
	private static final String entrySeparator = ",";

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean value) {
		debug = value;
	}

	// pass-through
	private String[] arguments = null;

	public String[] getArguments() {
		return arguments;
	}

	private Map<String, String> flags = new HashMap<>();

	//
	// the flag values that are expected to be followed with a value
	// that allows the application to process the flag.
	//
	private Set<String> flagsWithValues = new HashSet<>();

	public Map<String, Object> processApply() {
		return processApply("apply");
	}

	public Map<String, Object> processApply(String data) {
		@SuppressWarnings("unchecked")
		Map<String, Object> members = (flags.containsKey(data))
				? (LinkedHashMap<String, Object>) new Yaml().load(flags.get(data))
				: new HashMap<>();
		return members;
	}

	public Set<String> getFlags() {
		Set<String> result = flags.keySet();
		return result;
	}

	public String getFlagValue(String name) {
		return flags.get(name);
	}

	public int getNumberOfArguments() {
		return arguments.length;
	}

	public int getNumberOfFlags() {
		return flags.size();
	}

	public boolean hasFlag(String name) {
		return flags.containsKey(name);
	}

	// contains no constructor nor logic to discover unknown flags
	public void parse(String[] args) {
		boolean evaluating = false;
		List<String> regularArgs = new ArrayList<>();
		// TODO: recognize special args like k8-style "-apply" [filename]
		// TODO: add a warning that -apply only work with a file argument
		for (int n = 0; n < args.length; ++n) {
			String name = null;
			String value = null;
			if (args[n].charAt(0) == '-' && args[n].length() > 1) {
				name = args[n].replaceFirst("-", "");
				value = null;
				evaluating = true;
				// remove the dash
				if (debug)
					System.err.println("About to set the flag: " + name);

				// TODO: tweak to allow last arg to be the "-"
				if (flagsWithValues.contains(name)
						&& ((n == args.length - 2 && args[n + 1].equals("-"))
								|| (n < args.length - 1 && !args[n + 1].matches("^-")))) {

					String data = args[++n];
					value = processData(name, data);

				} else {
					if (debug)
						System.err.println("Set the flag without value for " + name);
					evaluating = false;
				}
				flags.put(name, value);
			} else {
				if (debug)
					System.err.println("About to add to regular args: " + args[n]);
				if (!evaluating)
					regularArgs.add(args[n]);
				evaluating = false;
			}
		}

		arguments = regularArgs.toArray(new String[regularArgs.size()]);
		if (debug)
			System.err.println("Regular args count: " + arguments.length);

	}

	public String processData(final String name, final String data) {
		String value = null;
		if (debug)
			System.err.println("Inspecting the data argument: " + data);

		// https://www.baeldung.com/java-case-insensitive-string-matching
		if (data.matches("(?i)^env:[a-z_0-9]+")) {
			value = System.getenv(data.replaceFirst("(?i)^env:", ""));
			if (debug)
				System.err.println(
						"Evaluate data from environment for: " + name + " = " + value);

		} else if (data.matches("(?i)^@[a-z_0-9./:]+")) {
			String datafilePath = Paths.get(System.getProperty("user.dir"))
					.resolve(data.replaceFirst("^@", "")).toAbsolutePath().toString();
			if (debug)
				System.err.println(
						"Reading data for: " + name + " from file: " + datafilePath);
			try {
				value = (name.equalsIgnoreCase("apply"))
						? readFile(datafilePath, Charset.forName("UTF-8"))
						: readFile(datafilePath, Charset.forName("UTF-8"))
								.replaceAll(" *\\r?\\n *", ",");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else if (data.matches("(?i)^(?:file|http|https)://[a-z_0-9./:]+")) {
			// non standard format, just for trying the new option format
			try {
				value = getValueData(data, Lib.JSON);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		} else {
			value = data;
			if (debug)
				System.err.println("Set value for: " + name + " = " + value);
		}
		return value;
	}

	private String getValueData(String uri, Lib flag) throws IOException {
		if (debug)
			System.err.println("Using " + flag.name());
		switch (flag) {
		case JSON:
			return JsonReader.readJSONObject(uri).toString(0);
		case GSON:
			Map<String, Object> obj = JsonReader.readJSONData(uri);
			return String.join("|", obj.keySet());
		default:
			return JsonReader.readRawJSON(uri);
		}
	}

	public void saveFlagValue(String flagName) {
		flagsWithValues.add(flagName);
	}

	private List<String[]> parsePairs(final String data) {
		List<String[]> pairs = Arrays.asList(data.split(" *, *")).stream()
				.map(o -> o.split(" *= *")).filter(o -> o.length == 2)
				.collect(Collectors.toList());
		return pairs;
	}

	public Map<String, String> parseEmbeddedMultiArg(final String data) {
		Map<String, String> result = new HashMap<>();
		List<String[]> pais = parsePairs(data);
		List<String> keys = pais.stream().map(o -> o[0]).distinct()
				.collect(Collectors.toList());
		if (pais.size() == keys.size()) {
			if (debug)
				pais.stream().map(o -> String.format("Collected: " + Arrays.asList(o)))
						.forEach(System.err::println);

			// result = rawdata.stream().collect(Collectors.toMap(o -> o[0], o ->
			// o[1]));
			result = pais.stream()
					.map(o -> new AbstractMap.SimpleEntry<String, String>(o[0], o[1]))
					.collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue()));
		} else {
			System.err.println("Duplicate embedded argument(s) detected, aborting");
			result = null;
		}
		return result;
	}

	public Map<String, String> parseEmbeddedMultiArg2(final String data) {
		Map<String, String> result = new HashMap<>();
		try {
			List<String[]> pairs = parsePairs(data);
			result = pairs.stream().collect(Collectors.toMap(o -> o[0], o -> o[1]));
		} catch (IllegalStateException e) {
			System.err.println("Duplicate embedded argument(s) detected, aborting");
			result = null;
		}
		return result;
	}

	// Example data:
	// -argument "{count:0, type:navigate, size:100, flag:true}"
	// NOTE: not using org.json to reduce size
	public Map<String, String> extractExtraArgs(String argument)
			throws IllegalArgumentException {

		final Map<String, String> extraArgData = new HashMap<>();
		argument = argument.trim().substring(1, argument.length() - 1);

		if (argument.indexOf("{") > -1 || argument.indexOf("}") > -1) {
			if (debug)
				System.err.println("Found invalid nested data");

			throw new IllegalArgumentException("Nested JSON athuments not supprted");
		}
		final String[] pairs = argument.split(entrySeparator);

		for (String pair : pairs) {
			String[] values = pair.split(keyValueSeparator);

			if (debug)
				System.err.println("Collecting pair: " + pair);

			extraArgData.put(values[0].trim(), values[1].trim());
		}
		return extraArgData;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	// see
	// also:https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	//
	private static class JsonReader {

		private static Gson gson = new Gson();
		private static JSONObject jsonObject = null;
		private static String text;
		private static boolean debug = false;
		// cannot access encloure class debug which is not static

		@SuppressWarnings("unchecked")
		public static Map<String, Object> readJSONData(String url)
				throws IOException {
			text = readRawJSON(url);
			if (debug)
				System.err.println("Read JSON data: " + text);
			// effectively parse the data
			final Map<String, Object> data = gson.fromJson(text, Map.class);

			return data;
		}

		public static String readRawJSON(String url) throws IOException {
			InputStream is = new URL(url).openStream();
			try {
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is, Charset.forName("UTF-8")));
				text = readAll(rd);
				if (debug)
					System.err.println("Read JSON data: " + text);
				return text;
			} finally {
				is.close();
			}
		}

		public static JSONObject readJSONObject(String url) throws IOException {
			text = readRawJSON(url);
			if (debug)
				System.err.println("Read JSON data: " + text);
			jsonObject = new JSONObject(text);
			return jsonObject;
		}

		private static String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();
		}
	}

	enum Lib {

		JSON(0x01), GSON(0x02), NONE(0x04);

		private int bit;

		Lib(int value) {
			bit = value;
		}

		public int getBitNumber() {
			return (bit);
		}

	}
}
