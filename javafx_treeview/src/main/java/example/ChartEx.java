package example;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
	private final static List<List<Float>> data = new ArrayList<>();
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
			launch(args);
			// } catch (ParseException e) {
		} catch (IOException e) {
		}
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
		series.setName("Load Average");
		for (int cnt = 0; cnt != data.size(); cnt++) {
			List<Float> row = data.get(cnt);
			series.getData().add(new XYChart.Data(cnt, row.get(0)));
		}

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Status");
		for (int cnt = 0; cnt != data.size(); cnt++) {
			List<Float> row = data.get(cnt);
			series1.getData().add(new XYChart.Data(cnt, row.get(1)));
		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);
		lineChart.getData().add(series1);

		stage.setScene(scene);
		stage.show();
	}

	public static void help() {
		System.exit(1);
	}
}