package example;

// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.CoreMatchers.notNullValue;
// import static org.hamcrest.MatcherAssert.assertThat;

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

// import org.apache.commons.cli.CommandLine;
// import org.apache.commons.cli.CommandLineParser;
// import org.apache.commons.cli.DefaultParser;
// import org.apache.commons.cli.Options;
// import org.apache.commons.cli.ParseException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import example.CommandLineParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * based on Oracle's example at
 * https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
 */

@SuppressWarnings("restriction")
public class ChartEx extends Application {
	private static Logger logger = LogManager.getLogger(ChartEx.class.getName());
	private static String[] HEADERS = { "column1", "column2" };

	// private static CommandLineParser commandLineparser = new DefaultParser();
	// private static CommandLine commandLine = null;
	// private final static Options options = new Options();
	private static List<List<Float>> data = new ArrayList<>();
	private static List<List<Object>> data2 = new ArrayList<>();
	private static CommandLineParser commandLineParser;
	private static String resource = "data.csv";

	public static void main(String[] args) {
		// options.addOption("h", "help", false, "Help");
		// options.addOption("d", "debug", false, "Debug");
		// options.addOption("r", "resource", true, "Resource");
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("resource");

		commandLineParser.parse(args);

		try {
			// commandLine = commandLineparser.parse(options, args);
			resource = commandLineParser.getFlagValue("resource");
			// String resource = commandLine.getOptionValue("resource");
			if (resource == null) {
				logger.info("Missing required argument: resource");
				return;
			}
			Reader in;
			/*
			Iterable<CSVRecord> records;
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
			in.close();
			data.clear();
			in = new FileReader("data.csv");
			records = CSVFormat.DEFAULT.parse(in);
			for (CSVRecord record : records) {
				String columnOne = record.get(0);
				String columnTwo = record.get(1);
				List<Float> row = new ArrayList<>();
				row.add(Float.parseFloat(columnOne));
				row.add(Float.parseFloat(columnTwo));
				data.add(row);
			}
			logger.info("data: " + data);
			data.clear();
						*/

			// https://stackoverflow.com/questions/17613363/gson-deserializing-an-array-of-objects
			final Gson gson = new Gson();
			String datafilePath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "data.json";
			String payload = readFile(datafilePath, Charset.forName("UTF-8"));
			logger.info("payload: " + payload);
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
						// String p5 = p4.get(1).toString().replaceAll("0000$", "");
						// java.lang.NumberFormatException: For input string:
						// "1450754160000"
						String p5 = p4.get(1).toString();
						// logger.info("Can process object: " + p4.toString());
						// logger.info("Can process object: " + p4.get(0) + "," + p4.get(1)
						// + " | " + p5);

						List<Float> row = new ArrayList<>();

						row.add(Float.parseFloat(p4.get(0).toString()));
						// row.add((float) Long.parseLong(p5));
						row.add(1.0f * Long.parseLong(p5));
						logger.info(String.format("loading datapoints: %f, %9d %f ",
								Float.parseFloat(p4.get(0).toString()), Long.parseLong(p5),
								(0.1f + Long.parseLong(p5))));
						// row.get(1),
						data.add(row);
						data2.add(new ArrayList(Arrays.asList(new Object[] {
								Float.parseFloat(p4.get(0).toString()), Long.parseLong(p5) })));
					}
				}
			} catch (JSONException e) {
			}
			/*
						QueryTimeserieResponse[] payloadObj = (QueryTimeserieResponse[]) gson
								.fromJson(payload, QueryTimeserieResponse[].class);
						for (QueryTimeserieResponse entry : payloadObj) {
							if (!entry.getTarget().equals("test")) {
								continue;
							}
							// logger.info(entry.getDatapoints());
							List<List<Float>> datapoints = (List<List<Float>>) entry
									.getDatapoints();
							for (List<Float> dataentry : datapoints) {
								logger.info(String.format("loading datapoints: %f, %f",
										dataentry.get(1), dataentry.get(0)));
								data.add(dataentry);
							}
						}
						*/
			data = formatDataetries(data);
			logger.info("data: " + data);
			logger.info("data2: " + data2);
			for (int cnt = 0; cnt != data2.size(); cnt++) {
				List<Object> row = (List<Object>) data2.get(cnt);
				float y = (float) row.get(0);
				long x = (long) row.get(1);
				// logger.info("processed row: " + row);
				logger.info(String.format("processed row: %d,%f", x, y));
			}
			launch(args);
			// } catch (ParseException e) {
		} catch (IOException e) {
		}
	}

	private static List<List<Float>> formatDataetries(List<List<Float>> data) {
		List<List<Float>> result = new ArrayList<>();
		float x = data.get(0).get(1);
		for (int cnt = 0; cnt != data.size(); cnt++) {
			List<Float> dataentry = data.get(cnt);
			List<Float> row = new ArrayList<>();
			logger.info(String.format("loading row: %f, %f", dataentry.get(1),
					dataentry.get(0)));
			// swap columns
			row.add((dataentry.get(1) - x) / 1000);
			row.add(dataentry.get(0));

			logger.info("processed row: " + row);
			result.add(row);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {

		stage.setTitle("Line Chart Sample");
		// defining the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		// creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

		lineChart.setTitle("Chart");
		XYChart.Series series = new XYChart.Series();
		series.setName("test");
		/*
		for (int cnt = 0; cnt != data.size(); cnt++) {
			List<Float> row = data.get(cnt);
			float x = row.get(0);
			float y = row.get(1);
			logger.info(String.format("data point %d (%f %f)", cnt, x, y));
			series.getData().add(new XYChart.Data(x, y));
		}
		*/
		long x0 = (long) data2.get(0).get(1);
		for (int cnt = 0; cnt != data2.size(); cnt++) {
			List<Object> row = data2.get(cnt);
			float y = (float) row.get(0);
			long x = ((long) row.get(1) - x0) / 1000;
			// logger.info("processed row: " + row);
			logger.info(String.format("processed row: %d,%f", x, y));
			series.getData().add(new XYChart.Data(x, y));
		}
		/*
				XYChart.Series series1 = new XYChart.Series();
				series1.setName("Status");
				for (int cnt = 0; cnt != data.size(); cnt++) {
					List<Float> row = data.get(cnt);
					series1.getData().add(new XYChart.Data(cnt, row.get(1)));
				}
		*/
		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);
		// lineChart.getData().add(series1);

		stage.setScene(scene);
		stage.show();
	}

	public static void help() {
		System.exit(1);
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
