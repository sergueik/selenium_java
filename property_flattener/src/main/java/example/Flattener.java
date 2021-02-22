package example;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import example.CommandLineParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
		String data;
		try {
			File file = new File(inFile);
			final String nls = "#";
			final String nonls = "[^#]";
			final String delimiter = "|";
			final String nodelimiter = "[^\\|]";
			String result = "";
			StringBuffer contents = new StringBuffer();
			BufferedReader reader = null;

			reader = new BufferedReader(new FileReader(file));
			String text = null;
			file = new File(outFile);
			StringBuilder sb = new StringBuilder();
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(nls
				/* System.getProperty("line.separator") */ );
			}
			reader.close();

			data = contents.toString();
			final String grammar = "^(?:(" + nodelimiter + "+)" + nls + ")*( " + nonls + "+)" + ": *" + delimiter + nls
					+ "((?:" + nonls + "+" + nls + "?)*)" + nls + nls + "(.*$)";
			Pattern p = Pattern.compile(grammar);
			System.err.println("Pattern: " + grammar);
			boolean proceed = true;
			sb = new StringBuilder();
			while (proceed) {
				Matcher m = p.matcher(data);
				System.err.println("Data: " + data);
				if (m.find()) {
					String regular_config = m.group(1);
					String property_name = m.group(2);
					String property_values = m.group(3);
					data = m.group(4);
					sb.append(property_name + ":" + String.join(",", property_values.split(nls)));
					if (regular_config != null) {
						for (String line : regular_config.split(nls)) {
							sb.append(line);
						}
					}
				} else {
					for (String line : data.split(nls)) {
						sb.append(line);
					}
					break;
				}
			}
			result = sb.toString();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)), true);
			out.println(result);
			out.close();
		} catch (IOException e) {
		}
	}
}
