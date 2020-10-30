package example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LaunchDirect {

	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	private static List<String> nodelist = new LinkedList<>();
	private static String inputFile;
	private static String outputFile;
	private static String inventoryFile;
	private static String role;
	private static String dc;
	private static String op;
	private static String env;
	private static String fileName = "classification.yaml";
	private static String encoding = "UTF-8";
	private static List<String> columnHeaders = new ArrayList<>();
	private static String newColumnHeader = null;
	private static List<String> extractColumnHeaders = Arrays
			.asList("Hostname,Role,Location,Notes".split(" *, *"));
	private static String sheetColumnHeader = "Env";

	public static void main(String[] args)
			throws IOException, URISyntaxException {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("input");
		commandLineParser.saveFlagValue("inventory");
		commandLineParser.saveFlagValue("output");
		// --input <classification file>
		commandLineParser.saveFlagValue("role");
		commandLineParser.saveFlagValue("env");
		commandLineParser.saveFlagValue("dc");
		commandLineParser.saveFlagValue("op");
		commandLineParser.saveFlagValue("nodes");
		commandLineParser.saveFlagValue("columns");
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

		if (op.matches("(?:known|unknown)")) {
			if (commandLineParser.hasFlag("nodes")) {
				nodelist = Arrays
						.asList(commandLineParser.getFlagValue("nodes").split(" *, *"));
				if (debug) {
					System.err.println("Loaded node list: " + nodelist);
				}
			}
		}

		if (op.equalsIgnoreCase("known")) {
			filter(true);
		}

		if (op.equalsIgnoreCase("unknown")) {
			filter(false);
		}

		if (op.equalsIgnoreCase("excel")) {
			outputFile = commandLineParser.getFlagValue("output");
			if (outputFile == null) {
				outputFile = "result.xlsx";
				System.err.println(
						String.format("Using default output filename: %s", outputFile));
			}
			excel();
		}
		if (op.equalsIgnoreCase("import")) {
			if (commandLineParser.hasFlag("columns")) {
				extractColumnHeaders = Arrays
						.asList(commandLineParser.getFlagValue("columns").split(","));
			}
			inventoryFile = commandLineParser.getFlagValue("inventory");
			if (inventoryFile == null) {
				System.err.println("Missing required argument for operation " + op
						+ ": inventoryfile.");
				return;
			}
			importExcel();
		}
		if (debug) {
			System.err.println("Done: " + op);
		}
	}

	public static void filter(final boolean collectKnown) {
		LinkedHashMap<String, Map<String, String>> nodes = Utils
				.loadConfiguration(inputFile);
		List<String> knownNodes = Arrays
				.asList(nodes.keySet().toArray(new String[nodes.size()]));

		for (String hostname : nodelist) {
			if (collectKnown == true && knownNodes.contains(hostname)) {
				System.err.println(String.format("hostname:\n%s\n", hostname));
			}
			if (collectKnown == false && !knownNodes.contains(hostname)) {
				System.err.println(String.format("host:\n%s\n", hostname));
			}
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
					&& matchedValue(row, "role", role, true)) {
				System.err.println(String.format("host:\n%s\n", row.toString()));
			}
		}
	}

	public static void importExcel() {
		ExcelFileUtils excelFileUtils = new ExcelFileUtils();
		List<Map<Integer, String>> tableData = new ArrayList<>();
		excelFileUtils.setExtractColumnHeaders(extractColumnHeaders);
		excelFileUtils.setSheetColumnHeader(sheetColumnHeader);
		excelFileUtils.setSpreadsheetFilePath(inventoryFile);
		excelFileUtils.setSheetName("Data");
		excelFileUtils.setTableData(tableData);
		excelFileUtils.setDebug(debug);
		columnHeaders = excelFileUtils.readColumnHeaders();
		System.err.println("Read column headers: " + columnHeaders);
		if (!columnHeaders.containsAll(extractColumnHeaders)) {
			System.err.println(
					"There is not enough data in the inventory file: " + inventoryFile);
			return;
		}
		List<String> sheetNames = excelFileUtils.readSheetNames();
		System.err.println("Read sheet names: " + sheetNames);
		excelFileUtils.readSpreadsheet();
	}

	public static void excel() {
		ExcelFileUtils excelFileUtils = new ExcelFileUtils();
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
		excelFileUtils.setSpreadsheetFilePath(outputFile);
		excelFileUtils.setSheetName("Data");
		excelFileUtils.setTableData(tableData);

		try {
			excelFileUtils.setSheetFormat("Excel 2007");
			excelFileUtils.writeSpreadsheet();
		} catch (Exception e) {
		}
	}

	public static void dumpTyped() {
		List<NodeConfiguration> nodes = Utils.loadTypedConfiguration(inputFile);
		if (debug) {
			System.err.println(String.format("nodes:\n%s\n", nodes.toString()));
		}
		for (NodeConfiguration node : nodes) {
			if (matchedValue(node.getDatacenter(), dc)
					&& matchedValue(node.getRole(), role, true)
					&& matchedValue(node.getEnvironment(), env)) {
				System.err.println(String.format("node:\n%s\n", node.toString()));
			}
		}
	}

	private static boolean matchedValue(final Map<String, String> row,
			final String key, final String value) {
		return matchedValue(row, key, value, false);
	}

	private static boolean matchedValue(final Map<String, String> row,
			final String key, final String value, boolean relaxedMatch) {
		if (debug) {
			System.err
					.println(String.format("Comparing value of: %s with %s", key, value));

		}
		// used for most keys except role,
		// with which a less restrictive match is performed
		return (row.containsKey(key) && row.get(key)
				.matches(String.format((relaxedMatch) ? "^.*%s.*$" : "^%s.*$", value)));
	}

	private static boolean matchedValue(final String data, final String value) {
		return matchedValue(data, value, false);
	}

	private static boolean matchedValue(final String data, final String value,
			boolean relaxedMatch) {
		if (debug) {
			System.err
					.println(String.format("Comparing data: %s with %s", data, value));

		}
		return (data
				.matches(String.format((relaxedMatch) ? "^.*%s.*$" : "^%s.*$", value)));
	}
}
