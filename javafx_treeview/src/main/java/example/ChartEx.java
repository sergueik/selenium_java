package example;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * based on Oracle's example at
 * https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
 */

@SuppressWarnings("restriction")
public class ChartEx extends Application {
	private static Logger logger = LogManager.getLogger(ChartEx.class.getName());
	
	// private static CommandLineParser commandLineparser = new DefaultParser();
	// private static CommandLine commandLine = null;
	// private final static Options options = new Options();
	private static List<List<Float>> data = new ArrayList<>();
	private static List<List<Object>> data2 = new ArrayList<>();
	private static List<List<Object>> data3 = new ArrayList<>();
	private static CommandLineParser commandLineParser;
	private static String resource = "data.csv";
	private static Parser parser = Parser.getInstance();

	public static void main(String[] args) {
		// options.addOption("h", "help", false, "Help");
		// options.addOption("d", "debug", false, "Debug");
		// options.addOption("r", "resource", true, "Resource");
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("resource");
		commandLineParser.saveFlagValue("type");

		commandLineParser.parse(args);

		// try {
		// commandLine = commandLineparser.parse(options, args);
		resource = commandLineParser.getFlagValue("resource");
		// String resource = commandLine.getOptionValue("resource");
		if (resource == null) {
			logger.info("Missing required argument: resource");
			return;
		}
		String type = commandLineParser.getFlagValue("type");
		if (type == null || !type.matches("csv|json")) {
			logger.info("Missing required argument: type");
			return;
		}
		if (type.equals("csv")) {
			data = parser.parseCSV(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + resource);
			data = parser.parseCSV2(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + resource);
		}
		if (type.equals("json")) {
			data2 = parser.parseJSON(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + resource);
			data3 = parser.parseJSON2(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + resource);
		}
		logger.info("data3 (bad): " + data3);
		data3.clear();
		logger.info("data2: " + data2);
		data3 = parser.formatData(data2);
		logger.info("data3: " + data3);
		launch(args);
		// } catch (ParserException e) {
		// }
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

		for (int cnt = 0; cnt != data3.size(); cnt++) {
			List<Object> row = data3.get(cnt);
			long x = (long) row.get(0);
			float y = (float) row.get(1);
			logger.info(String.format("data point %d (%d %f)", cnt, x, y));
			series.getData().add(new XYChart.Data(x, y));
		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);

		stage.setScene(scene);
		stage.show();
	}

	public static void help() {
		System.exit(1);
	}

}
