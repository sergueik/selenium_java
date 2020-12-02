package com.github.sergueik.junitparams;
/**
 * Copyright 2017-2019 Serguei Kouzmine
 */

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import junitparams.custom.ParametersProvider;
import junitparams.mappers.DataMapper;

// NOTE: junitparams.mappers.BufferedReaderDataMapper 
// is not public in junitparams.mappers and cannot be accessed from outside package

/**
 * JSON mapper for JUnitparams data provider.
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class JSONMapper implements DataMapper {

	private Boolean debug = false;
	private String testName = "test";
	private String value;

	public void getValue(String value) {
		System.err.println("messing ip with value");
	}

	private List<String> columns = new ArrayList<>();

	private List<String> getColumns(String payload) {
		List<String> columns = new ArrayList<>();

		payload = payload.replaceAll("\n", " ").substring(1, payload.length() - 1);
		if (debug)
			System.err.println("row: " + payload);

		String[] pairs = payload.split(",");

		for (String pair : pairs) {
			String[] values = pair.split(":");

			String column = values[0].substring(1, values[0].length() - 1).trim();
			if (debug) {
				// the order of keys will be random
				System.err.println("column: " + column);
			}
			columns.add(column);
		}
		return columns;

	}

	// NOTE: column order is indeterministic
	public Object[] map(Reader reader) {

		List<Object[]> result = new LinkedList<>();
		List<Object> resultRow = new LinkedList<>();
		String rawData = "{}";
		JSONObject allTestData = new JSONObject();
		JSONArray rows = new JSONArray();

		try {
			List<String> lines = new LinkedList<>();
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			String[] data = new String[lines.size()];
			lines.toArray(data);
			rawData = String.join("\n", data);
			// System.err.println("Read rawdata: " + rawData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			List<String> hashes = new ArrayList<>();
			// possible org.json.JSONException
			allTestData = new JSONObject(rawData);
			assertTrue(allTestData.has(testName));
			try {
				String dataString = allTestData.getString(testName);
				rows = new JSONArray(dataString);
			} catch (JSONException e) {
				// JSONObject["test"] not a string.
				// compatibility with JSON 2007
				rows = allTestData.optJSONArray(testName);
			}
			for (int i = 0; i < rows.length(); i++) {
				// JSONArray[0] not a string
				try {
					hashes.add(rows.getString(i));
				} catch (JSONException e) {
					hashes.add(rows.getJSONObject(i).toString());
				}
			}
			assertTrue(hashes.size() > 0);
			// NOTE: order of keys after invoking JSON library will be
			// non-deterministic
			// https://stackoverflow.com/questions/4515676/keep-the-order-of-the-json-keys-during-json-conversion-to-csv
			columns = getColumns(hashes.get(0));

			for (String entry : hashes) {
				resultRow = new LinkedList<>();
				if (debug)
					System.err.println("payload = " + entry);
				JSONObject rowObj = new JSONObject(entry);

				for (String column : columns) {
					resultRow.add(rowObj.get(column).toString());
				}
				result.add(resultRow.toArray());
				/*
				@SuppressWarnings("unchecked")
				Iterator<String> entryKeyIterator = rowObj.keys();
				while (entryKeyIterator.hasNext()) {
					String entryKey = entryKeyIterator.next();
					String entryData = rowObj.get(entryKey).toString();
					switch (entryKey) {
					case "keyword":
						keyword = entryData;
						break;
					case "count":
						count = Double.valueOf(entryData);
						break;
					}
				result.add(new Object[] { keyword, count });
				*/
			}
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
		if (debug) {
			System.err.println("result: ");
			for (Object[] row : result) {
				for (int cnt = 0; cnt != row.length; cnt++) {
					System.err.print(row[cnt] + " ");
				}
			}
			System.err.println("end");
		}
		return result.toArray();

	}
}
