package example;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
public class TransformDocument {
	private static boolean debug = false;
	private static final String defaultStyleSheeet = "comment_node.xsl";
	private static CommandLineParser commandLineParser;

	public static void main(String[] args)
			throws IOException, URISyntaxException, TransformerException {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("in");
		commandLineParser.saveFlagValue("out");
		commandLineParser.saveFlagValue("xsl");
		commandLineParser.saveFlagValue("name");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String outFile = commandLineParser.getFlagValue("out");
		if (outFile == null) {
			System.err.println("Missing required argument: out");
		}
		String inFile = commandLineParser.getFlagValue("in");
		if (inFile == null) {
			System.err.println("Missing required argument: in");
		}

		Transformer transformer = null;
		String name = commandLineParser.getFlagValue("name");
		if (name == null) {
			name = "responseHeadersFilter";
		}

		String xsl = commandLineParser.getFlagValue("xsl");
		// https://www.programcreek.com/java-api-examples/javax.xml.transform.Source
		if (xsl != null) {
			if (debug) {
				System.err
						.println(String.format("transforming %s with %s", inFile, xsl));
			}
			transformer = (TransformerFactory.newInstance())
					.newTransformer(new StreamSource(new File(xsl)));
		} else {
			if (debug) {
				System.err
						.println(String.format("transforming %s with embedded stylesheet",
								inFile, defaultStyleSheeet));
			}
			transformer = (TransformerFactory.newInstance())
					.newTransformer(new StreamSource(new StringReader(Utils.replaceXPath(
							Utils.getScriptContent(defaultStyleSheeet), name, false))));
		}
		Source text = new StreamSource(new File(inFile));
		if (debug) {
			System.err.println("Loaded: " + inFile);
		}
		transformer.transform(text, new StreamResult(new File(outFile)));
		if (debug) {
			System.err.println("Written: " + outFile);
		}
	}

}