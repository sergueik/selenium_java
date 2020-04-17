package example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.xml.transform.TransformerException;

import example.RemoveNode;
import example.CommentNode;
import example.MergeDocumentFragments;

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
		if (op.equalsIgnoreCase("comment")) {
			try {
				CommentNode.main(args);
			} catch (TransformerException e) {
			}
		}
		if (debug) {
			System.err.println("Done: " + op);
		}
	}
}
