package example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class LaunchDirect {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	private static String role;
	private static String dc;
	private static String op;
	private static String env;

	public static void main(String[] args) throws IOException, URISyntaxException {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("input");
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

		// TODO: look up in workspace if not specified
		if (commandLineParser.getFlagValue("input") == null) {
			System.err.println("Missing required argument: input");
			return;
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
		if (debug) {
			System.err.println("Done: " + op);
		}
	}

	public static void dump() {
		String fileName = "classification.yaml";
		String encoding = "UTF-8";
		try {
			InputStream in = Files.newInputStream(Paths.get(String.join(System.getProperty("file.separator"),
					Arrays.asList(System.getProperty("user.dir"), "src", "main", "resources", fileName))));
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Map<String, String>> nodes = (LinkedHashMap<String, Map<String, String>>) new Yaml()
					.load(in);

			for (String key : nodes.keySet()) {
				Map<String, String> row = nodes.get(key);
				if (debug) {
					System.err.println(String.format("host:\n%s\n", row.toString()));
					System.err.println(String.format("host:\n%s\n", Arrays.asList(row.keySet())));
					System.err.println(String.format("host role:\n%s\n", row.get("role")));
				}
				if (row.containsKey("role") && row.get("role").matches(String.format("^%s.*$", role))) {
					System.err.println(String.format("host:\n%s\n", row.toString()));
				}
				if (matchedValue(row, "datacenter", dc) && matchedValue(row, "environment", env)
						&& matchedValue(row, "role", role)) {
					System.err.println(String.format("host:\n%s\n", row.toString()));
				}
			}
		} catch (IOException e) {
			System.err.println("Excption (ignored) " + e.toString());
		}
	}

	private static boolean matchedValue(Map<String, String> row, String key, String value) {
		return (row.containsKey(key) && row.get(key).matches(String.format("^%s.*$", value)));

	}
}
