package example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Parser {

	private Logger logger = LogManager.getLogger(Parser.class.getName());
	private static String[] HEADERS = { "column1", "column2" };

	private static Parser instance = new Parser();

	private boolean debug = false;

	private String target = "test";

	public void setTarget(String value) {
		target = value;
	}

	public void setDebug(boolean value) {
		debug = value;
	}

	private Parser() {
	}

	public static Parser getInstance() {
		return instance;
	}

	List<List<Object>> parseJSON(String resource) {
		List<List<Object>> data = new ArrayList<>();
		final Gson gson = new Gson();
		String payload = null;
		String datafilePath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + resource;
		try {
			payload = readFile(resource, Charset.forName("UTF-8"));
			logger.info("payload: " + payload);
		} catch (IOException e) {
			return data;
		}
		try {
			JSONArray p1 = new JSONArray(payload);
			int l1 = p1.length();
			for (int i1 = 0; i1 < l1; i1++) {
				JSONObject json = p1.getJSONObject(i1);
				logger.info("Can process object: " + json.toString());

				@SuppressWarnings("unchecked")
				Iterator<String> p2Iterator = json.keys();

				while (p2Iterator.hasNext()) {
					String p2Key = p2Iterator.next();
					logger.info("observed row key: " + p2Key);
				}
				// Float.parseFloat("1450754160000");
				// 1450754179072.000000
				String p5 = json.getString("target");
				if (!p5.equals(target)) {
					logger.info("Skipping target:  " + p5);
					continue;
				} else {
					logger.info("Processing target: " + p5);
					JSONArray datapoints = json.getJSONArray("datapoints");
					int l3 = datapoints.length();
					for (int i3 = 0; i3 < l3; i3++) {
						JSONArray datapoint = datapoints.getJSONArray(i3);
						data.add(new ArrayList(Arrays.asList(
								new Object[] { Float.parseFloat(datapoint.get(0).toString()),
										Long.parseLong(datapoint.get(1).toString()) })));
					}
				}
			}
		} catch (JSONException e) {
		}
		return data;
	}

	// https://stackoverflow.com/questions/17613363/gson-deserializing-an-array-of-objects
	List<List<Object>> parseJSON2(String resource) {
		List<List<Object>> data3 = new ArrayList<>();
		String payload = null;
		final Gson gson = new Gson();

		try {
			payload = readFile(resource, Charset.forName("UTF-8"));
			logger.info("payload: " + payload);
		} catch (IOException e) {
			data3.clear();
		}

		QueryTimeserieResponseFix[] payloadObj = (QueryTimeserieResponseFix[]) gson
				.fromJson(payload, QueryTimeserieResponseFix[].class);
		for (QueryTimeserieResponseFix entry : payloadObj) {
			if (!entry.getTarget().equals("test")) {
				continue;
			}
			// logger.info(entry.getDatapoints());
			List<List<Object>> datapoints = (List<List<Object>>) entry
					.getDatapoints();
			for (List<Object> dataentry : datapoints) {
				logger.info(String.format("loading datapoints: %f, %f",
						(double) dataentry.get(0), (double) dataentry.get(1)));
				data3.add(dataentry);
			}
		}
		return data3;

	}

	public List<List<Object>> formatData(List<List<Object>> data) {
		List<List<Object>> result = new ArrayList<>();
		long x0 = (long) data.get(0).get(1);
		for (int cnt = 0; cnt != data.size(); cnt++) {
			List<Object> row = (List<Object>) data.get(cnt);
			float y = (float) row.get(0);
			long x = ((long) row.get(1) - x0) / 1000;
			// logger.info("processed row: " + row);
			logger.info(String.format("processed row: %d,%f", x, y));
			result.add(new ArrayList(Arrays.asList(new Object[] { x, y })));
		}
		return result;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
