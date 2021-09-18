package example;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Parser {

	private Logger logger = LogManager.getLogger(Parser.class.getName());
	private static String[] HEADERS = { "column1", "column2" };

	private static Parser instance = new Parser();

	private boolean debug = false;

	public void setDebug(boolean value) {
		debug = value;
	}

	private Parser() {
	}

	public static Parser getInstance() {
		return instance;
	}

	public List<List<Float>> parseCSV(String resource) {
		List<List<Float>> data = new ArrayList<>();
		Iterable<CSVRecord> records;
		try {
			Reader in;
			in = new FileReader(resource);
			records = CSVFormat.DEFAULT.withHeader(HEADERS).parse(in);
			for (CSVRecord record : records) {
				String columnOne = record.get("column1");
				String columnTwo = record.get("column2");
				List<Float> row = new ArrayList<>();
				row.add(Float.parseFloat(columnOne));
				row.add(Float.parseFloat(columnTwo));
				data.add(row);
			}
		} catch (IOException e) {
			data.clear();
		}
		return data;
	}

	public List<List<Float>> parseCSV2(String resource) {
		List<List<Float>> data = new ArrayList<>();
		Iterable<CSVRecord> records;
		Reader in;
		try {
			in = new FileReader(resource);
			data.clear();
			records = CSVFormat.DEFAULT.parse(in);
			for (CSVRecord record : records) {
				String columnOne = record.get(0);
				String columnTwo = record.get(1);
				List<Float> row = new ArrayList<>();
				row.add(Float.parseFloat(columnOne));
				row.add(Float.parseFloat(columnTwo));
				data.add(row);
			}
			in.close();
			logger.info("data: " + data);
		} catch (IOException e) {
			data.clear();
		}
		return data;
	}

	List<List<Object>> parseJSON(String resource) {
		List<List<Object>> data2 = new ArrayList<>();
		final Gson gson = new Gson();
		String payload = null;
		String datafilePath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "data.json";
		try {
			payload = readFile(resource, Charset.forName("UTF-8"));
			logger.info("payload: " + payload);
		} catch (IOException e) {
			data2.clear();
		}
		try {
			JSONArray p1 = new JSONArray(payload);
			int l1 = p1.length();
			for (int i1 = 0; i1 < l1; i1++) {
				JSONObject p2 = p1.getJSONObject(i1);
				logger.info("Can process object: " + p2.toString());

				Iterator<String> p2Iterator = p2.keys();

				while (p2Iterator.hasNext()) {
					String p2Key = p2Iterator.next();
					logger.info("Processing Row key: " + p2Key);
				}
				// Float.parseFloat("1450754160000");
				// 1450754179072.000000
				String target = p2.getString("target");
				if (!target.equals("test"))
					continue;
				logger.info("Processing target: " + target);
				JSONArray p3 = p2.getJSONArray("datapoints");
				int l3 = p3.length();
				for (int i3 = 0; i3 < l3; i3++) {
					JSONArray p4 = p3.getJSONArray(i3);
					data2.add(new ArrayList(Arrays
							.asList(new Object[] { Float.parseFloat(p4.get(0).toString()),
									Long.parseLong(p4.get(1).toString()) })));
				}
			}
		} catch (JSONException e) {
		}
		return data2;
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
