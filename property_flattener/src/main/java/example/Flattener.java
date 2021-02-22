package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flattener {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args) throws IOException, URISyntaxException {

		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("in");
		commandLineParser.saveFlagValue("out");
		commandLineParser.saveFlagValue("op");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String outFile = commandLineParser.getFlagValue("out");
		if (outFile == null) {
			System.err.println("Missing required argument: out");
			return;
		}
		String inFile = commandLineParser.getFlagValue("in");
		if (inFile == null) {
			System.err.println("Missing required argument: in");
			return;
		}
		String op = commandLineParser.getFlagValue("op");
		if (op == null) {
			System.err.println("Missing required argument: op");
			return;
		}
		if (op.equalsIgnoreCase("flatten")) {
			// external style sheet
			flatten(inFile, outFile);
		}
		if (debug) {
			System.err.println("Done: " + op);
		}
	}

	private static void flatten(String inFile, String outFile) {
		try {
			String data;
			boolean proceed = true;
			final String nls = "#";
			final String nonls = "[^#]";
			final String delimiter = "\\|";
			final String nodelimiter = "[^\\|]";
			String result = "";
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = null;
			String text = null;
			File file = new File(inFile);

			reader = new BufferedReader(new FileReader(file));
			file = new File(outFile);
			while ((text = reader.readLine()) != null) {
				sb.append(text).append(nls);
			}
			reader.close();

			data = sb.toString();
			final String grammar = "^(?:(" + nodelimiter + "+)" + nls + ")*(" + nonls + "+)" + ": *" + delimiter + nls
					+ "((?:" + nonls + "+" + nls + "?)*)" + nls + nls + "(.*$)";
			Pattern p = Pattern.compile(grammar);
			if (debug) {
				System.err.println("Pattern: " + grammar);
			}
			sb = new StringBuilder();
			while (proceed) {
				Matcher m = p.matcher(data);
				if (debug) {
					System.err.println("Data: " + data);
				}
				if (m.find()) {
					String regular_config = m.group(1);
					String property_name = m.group(2);
					String property_values = m.group(3);
					data = m.group(4);
					sb.append(property_name + ":" + String.join(",", property_values.split(nls)))
							.append(System.getProperty("line.separator"));
					if (regular_config != null) {
						for (String line : regular_config.split(nls)) {
							sb.append(line).append(System.getProperty("line.separator"));
						}
					}
				} else {
					for (String line : data.split(nls)) {
						sb.append(line).append(System.getProperty("line.separator"));
					}
					break;
				}
			}
			result = sb.toString();

			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)), true);
			printWriter.println(result);
			printWriter.close();
		} catch (IOException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
	}
}
