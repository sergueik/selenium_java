package example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class ReportFieldTest {

	private static ArtistSerializer serializer = new ArtistSerializer();

	private final static String fileName = "group.yaml";;
	private ArrayList<LinkedHashMap<Object, Object>> members;
	private static final String encoding = "UTF-8";
	private List<JsonElement> group = new ArrayList<>();

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
		Artist artist = new Artist(1, "paul", "vocals", "field1 data",
				"field2 data");

		JsonElement rowJson = serializer.serialize(artist, null, null);
		group.add(rowJson);
		System.err.println("JSON serialization or artist:\n" + rowJson.toString());
	}

	// no fields, well, some
	@Test
	public void test2() throws Exception {
		System.err.println("only \"required\" fields.");
		serializer.setReportFields("non-existing field");
		for (LinkedHashMap<Object, Object> row : members) {
			Artist artist = new Artist((int) row.get("id"), (String) row.get("name"),
					(String) row.get("plays"));

			JsonElement rowJson = serializer.serialize(artist, null, null);
			group.add(rowJson);
			System.err
					.println("JSON serialization or artist:\n" + rowJson.toString());
		}
		// optionally use custom serilizer class with no fitering enforced this time
		System.err.println("JSON serialization or one group:\n" + (new GsonBuilder()
				.registerTypeAdapter(Artist.class, new ArtistSerializer()).create())
						.toJson(group));
	}

	// filtered fields
	@Test
	public void test3() throws Exception {
		System.err.println("selected fields: ");
		serializer.setReportFields("name");
		for (LinkedHashMap<Object, Object> row : members) {
			// mockup test result constructor
			Artist artist = new Artist((int) row.get("id"), (String) row.get("name"),
					(String) row.get("plays"));

			JsonElement rowJson = serializer.serialize(artist, null, null);
			group.add(rowJson);
			System.err
					.println("JSON serialization or artist:\n" + rowJson.toString());
		}
		System.err.println(
				"JSON serialization or one group:\n" + new Gson().toJson(group));
	}

	// same, but also save JSON to the file
	@Test
	public void test4() throws Exception {

		final Gson gson = new Gson();

		try {
			FileOutputStream fos = new FileOutputStream("report.json");
			OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
			for (LinkedHashMap<Object, Object> row : members) {
				// mockup test result constructor
				Artist artist = new Artist((int) row.get("id"),
						(String) row.get("name"), (String) row.get("plays"));

				JsonElement rowJson = serializer.serialize(artist, null, null);
				group.add(rowJson);
				System.err
						.println("JSON serialization or artist:\n" + rowJson.toString());
			}
			final String payload = gson.toJson(group);
			System.err.println("JSON serialization or one group:\n" + payload);
			writer.write(payload);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<LinkedHashMap<Object, Object>> loadData(String fileName) {

		InputStream in;
		ArrayList<LinkedHashMap<Object, Object>> members = null;
		try {
			in = Files.newInputStream(
					Paths.get(String.join(System.getProperty("file.separator"),
							Arrays.asList(System.getProperty("user.dir"), "src", "test",
									"resources", fileName))));

			members = (ArrayList<LinkedHashMap<Object, Object>>) new Yaml().load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return members;
	}

}
