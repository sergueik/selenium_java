package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import com.jayway.jsonpath.JsonPath;

public class ReportFieldTest {

	private static ArtistSerializer serializer = new ArtistSerializer();
	private final static String fileName = "group.yaml";
	private static final String encoding = "UTF-8";
	private static Artist artist = null;
	private static JsonElement rowJson = null;
	private static String rawData = null;
	private List<JsonElement> group = new ArrayList<>();
	private ArrayList<LinkedHashMap<Object, Object>> members;
	private Collection<String> jsonKeys = null;

	@Before
	public void setup() {

		group.clear();
		serializer.reset();
		members = loadData(fileName);
	}

	// all fields
	@Test
	public void test1() throws Exception {
		System.err.println("all fields: ");
		// mockup test result constructor
		artist = new Artist(1, "paul", "vocals", "field1 data", "field2 data");

		rowJson = serializer.serialize(artist, null, null);
		group.add(rowJson);

		// https://github.com/json-path/JsonPath/README.md
		// keys() Provides the property keys (An alternative for terminal tilde ~)
		// the $.*~ is suggested in
		// https://stackoverflow.com/questions/46471516/get-keys-in-json
		// NOTE: this assumes flat
		// rawData = rowJson.toString();
		// jsonKeys = JsonPath.read(rawData, "$.keys()");
		// assertThat("Unexpected keys in JSON: " + jsonKeys, jsonKeys.size(), is(6));
		// custom assertion
		measureKeysCheck(rowJson, 6);
		System.err.println("JSON serialization or artist:\n" + rawData);

	}

	// no fields, well, some
	// @Ignore
	@Test
	public void test2() throws Exception {
		System.err.println("only \"required\" fields.");
		serializer.setReportFields("non-existing field");
		for (LinkedHashMap<Object, Object> row : members) {
			artist = new Artist((int) row.get("id"), (String) row.get("name"), (String) row.get("plays"));

			rowJson = serializer.serialize(artist, null, null);
			// custom assertion
			measureKeysCheck(rowJson, 2);
			// expect only "id" and "staticInfo"
			group.add(rowJson);
			System.err.println("JSON serialization or artist:\n" + rowJson.toString());
		}
		// optionally use custom serializer class with no filtering enforced this time

		System.err.println("JSON serialization or one group:\n"
				+ (new GsonBuilder().registerTypeAdapter(Artist.class, new ArtistSerializer()).create()).toJson(group));
	}

	// filtered fields
	// @Ignore
	@Test
	public void test3() throws Exception {
		System.err.println("selected fields: ");
		serializer.setReportFields("name");
		for (LinkedHashMap<Object, Object> row : members) {
			// mockup test result constructor
			artist = new Artist((int) row.get("id"), (String) row.get("name"), (String) row.get("plays"), "dummy",
					"dummy");

			rowJson = serializer.serialize(artist, null, null);
			// custom assertion
			measureKeysCheck(rowJson, 3);
			group.add(rowJson);
			System.err.println("JSON serialization or artist:\n" + rowJson.toString());
		}
		System.err.println("JSON serialization or one group:\n" + new Gson().toJson(group));
	}

	// same, but also save JSON to the file

	private static Object[] fieldArray = new Object[] { "name", "id", "staticInfo" };

	@SuppressWarnings("unchecked")
	// @Ignore
	@Test
	public void test4() throws Exception {
		String payload = null;
		final Gson gson = new Gson();
		serializer.setReportFields("name");
		try {
			FileOutputStream fos = new FileOutputStream("report.json");
			OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
			for (LinkedHashMap<Object, Object> row : members) {
				// mockup test result constructor
				artist = new Artist((int) row.get("id"), (String) row.get("name"), (String) row.get("plays"));

				rowJson = serializer.serialize(artist, null, null);
				// custom assertion
				measureKeysCheck(rowJson, 3);

				group.add(rowJson);
				payload = rowJson.toString();
				Map<String, Object> payloadObj = (Map<String, Object>) gson.fromJson(payload, java.util.Map.class);
				assertThat(payloadObj, notNullValue());
				assertThat(payloadObj.keySet().containsAll(new HashSet<Object>(Arrays.asList(fieldArray))), is(true));
				assertThat(payloadObj.keySet().contains("plays"), is(false));

				assertTrue(new HashSet<Object>(Arrays.asList(fieldArray))
						.containsAll(new HashSet<Object>(payloadObj.keySet())));
				System.err.println("JSON serialization or artist:\n" + rowJson.toString());

				payloadObj.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);

			}
			payload = gson.toJson(group);
			System.err.println("JSON serialization or one group:\n" + payload);
			writer.write(payload);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test5() throws Exception {
		System.err.println("selected fields: ");
		serializer.setReportFields("field1", "field2");
		for (LinkedHashMap<Object, Object> row : members) {
			// mockup test result constructor
			artist = new Artist((int) row.get("id"), (String) row.get("name"), (String) row.get("plays"), "dummy",
					"dummy");

			rowJson = serializer.serialize(artist, null, null);
			// custom assertion
			measureKeysCheck(rowJson, 4);
			
			// old fashioned test
			assertThat(((Map<String, String>) JsonPath.read(rowJson.toString(), "$")).keySet().size(), is(4));

			group.add(rowJson);
			System.err.println("JSON serialization or artist:\n" + rowJson.toString());
		}
		System.err.println("JSON serialization or one group:\n" + new Gson().toJson(group));
	}

	private void measureKeysCheck(JsonElement jsonElement, int size) {
		rawData = jsonElement.toString();
		jsonKeys = JsonPath.read(rawData, "$.keys()");
		assertThat("Unexpected keys in JSON: " + jsonKeys, jsonKeys.size(), is(size));
		return;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<LinkedHashMap<Object, Object>> loadData(String fileName) {

		InputStream in;
		ArrayList<LinkedHashMap<Object, Object>> members = null;
		try {
			in = Files.newInputStream(Paths.get(String.join(System.getProperty("file.separator"),
					Arrays.asList(System.getProperty("user.dir"), "src", "test", "resources", fileName))));

			members = (ArrayList<LinkedHashMap<Object, Object>>) new Yaml().load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return members;
	}

}
