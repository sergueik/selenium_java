package example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;
import example.Utils;
import example.Configuration;

public class LaunchDirect {

	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	private static String inputFile;
	private static String outputFile;
	private static String role;
	private static String dc;
	private static String op;
	private static String env;
	private static String fileName = "classification.yaml";
	private static String encoding = "UTF-8";

	public static void main(String[] args)
			throws IOException, URISyntaxException {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("input");
		commandLineParser.saveFlagValue("output");
		// --input <classification file>
		commandLineParser.saveFlagValue("role");
		commandLineParser.saveFlagValue("env");
		commandLineParser.saveFlagValue("dc");
		commandLineParser.saveFlagValue("op");
		commandLineParser.saveFlagValue("if");
		// TODO: input format

		commandLineParser.parse(args);

		//
		role = commandLineParser.getFlagValue("role");
		if (role == null) {
			System.err.println("Missing required argument: role");
			return;
		}

		inputFile = commandLineParser.getFlagValue("input");
		// TODO: look up in workspace if not specified
		if (inputFile == null) {
			inputFile = String.join(System.getProperty("file.separator"),
					Arrays.asList(System.getProperty("user.dir"), "src", "main",
							"resources", fileName));
			System.err
					.println(String.format("Using default inputFile: %s", inputFile));
		}

		dc = commandLineParser.getFlagValue("dc");
		if (dc == null) {
			System.err.println("Missing required argument: dc");
			return;
		}
		env = commandLineParser.getFlagValue("env");
		if (env == null) {
			System.err.println("Missing required argument: env");
			return;
		}
		// optional flags
		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		// operation
		op = commandLineParser.getFlagValue("op");
		if (op == null) {
			System.err.println("Missing required argument: op");
			return;
		}

		if (op.equalsIgnoreCase("dump")) {
			dump();
		}
		
		if (op.equalsIgnoreCase("typed")) {
			dumpTyped();
		}

		if (op.equalsIgnoreCase("excel")) {
			outputFile = commandLineParser.getFlagValue("output");
			if (outputFile == null) {
				outputFile = "test.xlsx";
				System.err
						.println(String.format("Using default outputFile: %s", outputFile));
			}
			excel();

		}
		if (debug) {
			System.err.println("Done: " + op);
		}
	}

	public static void dump() {
		LinkedHashMap<String, Map<String, String>> nodes = Utils
				.loadConfiguration(inputFile);
		for (String key : nodes.keySet()) {
			Map<String, String> row = nodes.get(key);
			if (debug) {
				System.err.println(String.format("host:\n%s\n", row.toString()));
				System.err
						.println(String.format("host:\n%s\n", Arrays.asList(row.keySet())));
				System.err.println(String.format("host role:\n%s\n", row.get("role")));
			}
			if (matchedValue(row, "datacenter", dc)
					&& matchedValue(row, "environment", env)
					&& matchedValue(row, "role", role)) {
				System.err.println(String.format("host:\n%s\n", row.toString()));
			}
		}
	}

	public static void excel() {

		List<Map<Integer, String>> tableData = new ArrayList<>();
		Map<Integer, String> rowData = new HashMap<>();

		LinkedHashMap<String, Map<String, String>> nodes = Utils
				.loadConfiguration(inputFile);
		for (String key : nodes.keySet()) {
			Map<String, String> row = nodes.get(key);
			rowData = new HashMap<>();
			rowData.put(0, key);
			List<String> columns = Arrays
					.asList(row.keySet().toArray(new String[row.size()]));

			for (int col = 0; col != columns.size(); col++) {
				rowData.put(col + 1, row.get(columns.get(col)));
			}
			tableData.add(rowData);
		}
		Utils.setExcelFileName(outputFile);
		Utils.setSheetName("SWT Test");
		Utils.setTableData(tableData);

		try {
			Utils.setSheetFormat("Excel 2007");
			Utils.writeXLSXFile();
		} catch (Exception e) {
		}
	}

	private static boolean matchedValue(Map<String, String> row, String key,
			String value) {
		if (debug) {
			System.err
					.println(String.format("Comparing value of: %s with %s", key, value));

		}
		return (row.containsKey(key)
				&& row.get(key).matches(String.format("^%s.*$", value)));

	}
}
