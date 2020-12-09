package com.github.sergueik.junitparams;
/**
 * Copyright 2018,2019 Serguei Kouzmine
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Class with static methods for loading data from the JSON or YAML file  
 * through Juit4 @Parameter annotation into specially constructed test class public properties
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class DataSourceStatic {

	// TODO: provide nulls or empty strings for missing input
	private static List<String> columns = Arrays
			.asList(new String[] { "row", "keyword", "count" });

	private static String dataFormat = "JSON";
	private static String dataFilePath = null;
	private static String defaultColumn = "row";

	// reserved
	private static String encoding = "UTF-8";

	private static boolean debug = false;

	// can be used for ordering the data in rowset
	private static String skipRowColumn = "skip";
	// can be used for filtering the data in json
	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;

	public static void setDataFormat(String value) {
		dataFormat = value;
	}

	public static void setDataFilePath(String value) {
		dataFilePath = value;
	}

	// NOTE: with gson one will either annotated all differences
	// or (default) to name serializable properties in Java
	// source the same name as JSON keys

	public static String getdefaultColumn() {
		return defaultColumn;
	}

	public static void setDefaultColumn(String value) {
		defaultColumn = value;
	}

	public static void setSkipRowColumn(String value) {
		skipRowColumn = value;
	}

	public static void setColumns(List<String> value) {
		columns = value;
	}

	public static void setDebug(boolean value) {
		debug = value;
	}

	// De-serialize the JSON file into a rowset of String data parameters
	public static Collection<Object[]> getdata() {
		if (dataFormat.matches("(?i:JSON)")) {
			try {
				return Arrays.asList(createDataFromJSON());
			} catch (JSONException e) {
				if (debug) {
					System.err
							.println("Failed to load data from datafile: " + dataFilePath);
				}
				return new ArrayList<Object[]>();
			}

			//
		} else if (dataFormat.matches("(?i:yaml)")) {
			if (debug) {
				System.err.println("Loading data format: " + dataFormat);
			}
			return Arrays.asList(createDataFromYAML());
		} else {
			if (debug) {
				System.err.println("Unrecognized data format: " + dataFormat);
			}
			return new ArrayList<Object[]>();
		}
	}

	public static Object[][] createDataFromJSON() throws org.json.JSONException {

		if (debug) {
			System.err.println("Data file path: " + dataFilePath + "\n"
					+ "Data columns: " + (Arrays.deepToString(columns.toArray())) + "\n"
					+ "Default column: " + defaultColumn + "\n" + "Skip row column: "
					+ skipRowColumn);
		}

		List<Object[]> testData = new ArrayList<>();
		List<Object> testDataRow = new LinkedList<>();
		List<String> hashes = new ArrayList<>();

		JSONArray rows = new JSONArray();

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(dataFilePath));
			rows = new JSONArray(new String(encoded, Charset.forName(encoding)));
		} catch (org.json.JSONException e) {
			System.err
					.println(String.format("Exception reading JSON from %s (ignored): %s",
							dataFilePath, e.toString()));
		} catch (IOException e) {
			System.err
					.println(String.format("Exception reading file %s (ignored): %s",
							dataFilePath, e.toString()));
		}

		for (int i = 0; i < rows.length(); i++) {
			try {
				hashes.add(rows.getString(i));
			} catch (JSONException e) {
				hashes.add(rows.getJSONObject(i).toString());
			}
			if (debug) {
				System.err.println("Loaded " + hashes);
			}
		}
		Assert.assertTrue(hashes.size() > 0);

		String firstRow = hashes.get(0);

		// NOTE: apparently after invoking org.json.JSON library the order of keys
		// inside the firstRow will be non-deterministic
		// https://stackoverflow.com/questions/4515676/keep-the-order-of-the-json-keys-during-json-conversion-to-csv
		firstRow = firstRow.replaceAll("\n", " ").substring(1,
				firstRow.length() - 1);
		if (debug)
			System.err.println("1st row: " + firstRow);

		List<String> actualColumns = new ArrayList<>();
		String[] pairs = firstRow.split(",");

		for (String pair : pairs) {
			String[] values = pair.split(":");

			String column = values[0].substring(1, values[0].length() - 1).trim();
			if (debug) {
				System.err.println("column: " + column);
			}
			actualColumns.add(column);
		}

		for (String entry : hashes) {
			JSONObject entryObj = new JSONObject();
			testDataRow = new LinkedList<>();
			try {
				entryObj = new JSONObject(entry);
			} catch (org.json.JSONException e) {
				e.printStackTrace();
			}

			if (skipRowColumn != null && entryObj.has(skipRowColumn)
					&& Boolean.parseBoolean(entryObj.get(skipRowColumn).toString())) {
				if (debug) {
					System.err.println("Will skip loading row: " + entry.toString());
				}
				continue;
			}

			// actualColumns is ignored
			// order of columns is preserved
			for (String column : columns) {
				testDataRow.add(entryObj.get(column).toString());
			}
			testData.add(testDataRow.toArray());
		}
		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

	@SuppressWarnings("unchecked")
	public static Object[][] createDataFromYAML() {
		if (yaml == null) {
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(options);
		}

		List<Map<String, Object>> yamlData = new ArrayList<>();
		try (InputStream in = Files.newInputStream(Paths.get(dataFilePath))) {
			yamlData = yaml.loadAs(in, yamlData.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// crude
		// Object[][] testDataArray = new Object[testData.values().size()][];
		// testData.values().toArray(testDataArray);

		List<Object[]> testData = new ArrayList<>();
		List<Object> testDataRow = new LinkedList<>();
		Map<String, Object> yamlDataRow = new HashMap<>();
		for (int cnt = 0; cnt != yamlData.size(); cnt++) {
			yamlDataRow = yamlData.get(cnt);

			// lame
			// maybe fill in the right order?
			testDataRow = new LinkedList<>();
			for (String column : columns) {
				testDataRow.add(yamlDataRow.getOrDefault(column, null).toString());
			}

			testData.add(testDataRow.toArray());
		}

		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}
}
