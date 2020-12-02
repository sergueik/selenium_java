package com.github.sergueik.junitparams;
/**
 * Copyright 2018,2019 Serguei Kouzmine
 */

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import org.junit.Assert;

import org.json.JSONArray;
import org.json.JSONException;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Class with an (optional) Singleton constructor for loading the data 
 * from a JSON or YAML data file  
 * through Juit4 @Parameter annotation into specially constructed  test class parameterized constructor
 * The poi and OpenDoc formats are also possible  
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class DataSourceSingleton {

	private List<String> columns = Arrays
			.asList(new String[] { "row", "keyword", "count" });

	private boolean debug = true;

	private String dataFormat = "JSON";
	private String dataFile = "";
	private String dataKey = "datakey";

	private static String encoding = "UTF-8";
	private String filePath = null;

	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;

	// currently unused
	// private String defaultKey = "row";

	// DataSource does not have to be a singleton
	private static DataSourceSingleton instance = new DataSourceSingleton();

	private DataSourceSingleton() {
	}

	public static DataSourceSingleton getInstance() {
		return instance;
	}

	// De-serialize from the JSON file under caller provided path into a row set
	// of String or strongly-typed (?)
	// for injection into the test class instance via @Parameters annotated
	// constructor
	public Collection<Object[]> getdata() {
		if (dataFormat.matches("(?i:JSON)")) {
			// NOTE: not an exception: "PatternSyntax Unclosed group near"
			try {
				// NOTE: temporarily store a close replica of JSONMapper class method
				return Arrays.asList(createDataFromJSON());
			} catch (JSONException e) {
				if (debug) {
					System.err.println("Failed to load data from datafile: " + dataFile);
				}
				return new ArrayList<Object[]>();
			}
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

	public void setDataFormat(String value) {
		this.dataFormat = value;
	}

	public void setDataFile(String value) {
		this.dataFile = value;
		filePath = String.format("%s/%s", System.getProperty("user.dir"),
				this.dataFile);
	}

	public String getDataKey() {
		return this.dataKey;
	}

	public void setDataKey(String value) {
		this.dataKey = value;
	}

	public void setColumns(List<String> value) {
		this.columns = value;
	}

	public void setDebug(boolean value) {
		this.debug = value;
	}

	@SuppressWarnings("unchecked")
	public Object[][] createDataFromYAML() {
		if (yaml == null) {
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(options);
		}

		List<Map<String, Object>> yamlData = new ArrayList<>();
		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
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

			// NOTE: somewhat lame
			// maybe fill in the right order?
			testDataRow = new LinkedList<>();
			for (String column : columns) {
				// NOTE: un-strong-type
				testDataRow.add(yamlDataRow.getOrDefault(column, null).toString());
			}

			testData.add(testDataRow.toArray());
		}

		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;

	}

	public Object[][] createDataFromJSON() throws org.json.JSONException {

		if (debug) {
			System.err.println(
					"File path: " + filePath + "\n" + "Data key: " + dataKey + "\n"
							+ "Data Columns: " + Arrays.deepToString(columns.toArray()));
		}

		JSONObject obj = new JSONObject();
		List<Object[]> testData = new ArrayList<>();
		List<Object> testDataRow = new LinkedList<>();
		List<String> hashes = new ArrayList<>();

		JSONArray rows = new JSONArray();

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filePath));
			obj = new JSONObject(new String(encoded, Charset.forName(encoding)));
		} catch (org.json.JSONException e) {
			System.err
					.println(String.format("Exception reading JSON from %s (ignored): %s",
							filePath, e.toString()));
		} catch (IOException e) {
			System.err.println(String.format(
					"Exception reading file %s (ignored): %s", filePath, e.toString()));
		}

		Assert.assertTrue("Verifying presence of " + dataKey, obj.has(dataKey));
		try {
			String dataString = obj.getString(dataKey);
			if (debug) {
				System.err.println(
						"Loaded data as string for key: " + dataKey + " as " + dataString);
			}
			rows = new JSONArray(dataString);
		} catch (org.json.JSONException e) {
			// JSON-20170516 and later: JSONObject["datakey"] not a string.
			rows = obj.getJSONArray(dataKey);
		}
		if (debug) {
			System.err.println("Loaded data rows for key: " + dataKey + " "
					+ rows.length() + " rows.");
		}
		for (int i = 0; i < rows.length(); i++) {
			try {
				String entry = rows.getString(i);
				hashes.add(entry);
			} catch (org.json.JSONException e) {
				// System.err.println("Exception (ignored) : " + e.toString());
				hashes.add(rows.getJSONObject(i).toString());
			}
		}
		Assert.assertTrue("Verified the data is not empty", hashes.size() > 0);

		String firstRow = hashes.get(0);

		// NOTE: apparently after invoking org.json.JSON library the order of keys
		// inside the firstRow can be non-deterministic
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
			// actualColumns is ignored
			// order of columns is preserved
			for (String column : columns) {
				testDataRow.add(entryObj.get(column).toString());
			}
			testData.add(testDataRow.toArray());

			/*
			@SuppressWarnings("unchecked")
			Iterator<String> entryKeyIterator = entryObj.keys();
			
			while (entryKeyIterator.hasNext()) {
				String entryKey = entryKeyIterator.next();
				String entryData = entryObj.get(entryKey).toString();
				// System.err.println(entryKey + " = " + entryData);
				switch (entryKey) {
				case "keyword":
					search_keyword = entryData;
					break;
				case "count":
					expected_count = Double.valueOf(entryData);
					break;
				}
			}
			testData.add(new Object[] { search_keyword, expected_count });
			*/
		}

		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

}
