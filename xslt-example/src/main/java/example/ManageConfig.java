package example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.transform.TransformerException;

import example.RemoveNode;
import example.TransformDocument;
import example.MergeDocumentFragments;
import example.CommandLineParser;

public class ManageConfig {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args)
			throws IOException, URISyntaxException {

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

		if (commandLineParser.getFlagValue("in") == null) {
			System.err.println("Missing required argument: in");
			return;
		}
		String op = commandLineParser.getFlagValue("op");
		if (op == null) {
			System.err.println("Missing required argument: op");
			return;
		}
		if (op.equalsIgnoreCase("add")) {
			MergeDocumentFragments.main(args);
		}
		if (op.equalsIgnoreCase("remove")) {
			RemoveNode.main(args);
		}
		if (op.equalsIgnoreCase("transform")) {
			// external style sheet
			try {
				TransformDocument.main(args);
			} catch (TransformerException e) {
			}
		}
		if (op.equalsIgnoreCase("comment")) {
			// embedded style sheet
			try {
				TransformDocument.main(args);
			} catch (TransformerException e) {
			}
		}
		if (debug) {
			System.err.println("Done: " + op);
		}
	}

	public static void mainYAMLTest(String[] args)
			throws IOException, URISyntaxException {

		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("apply");
		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
			// snakeyaml will make the values Integer, Bolean etc
			Map<String, Object> data = commandLineParser.processApply();

			for (String key : data.keySet()) {
				System.err.println("key: " + key + " value: " + data.get(key));
			}
		}
	}
}
