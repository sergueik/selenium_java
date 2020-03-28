package example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import example.CommandLineParser;

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
// https://www.programcreek.com/java-api-examples/?class=org.w3c.dom.Document&method=importNode

public class MergeDocumentFragments {
	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	public static void main(String args[]) {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("in");
		commandLineParser.saveFlagValue("out");

		commandLineParser.parse(args);
		String outFile = commandLineParser.getFlagValue("out");
		String inputPath = Paths.get(commandLineParser.getFlagValue("in")).toUri()
				.toString();
		if (debug) {
			System.err.println("Loading input: " + inputPath);
		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document webDocument = documentBuilder.parse(inputPath);
			Document configDocument = documentBuilder
					.parse(getPageContent("fragment.xml"));
			NodeList filterNodeList = configDocument.getElementsByTagName("filter");
			Node filterNode = filterNodeList.item(0);
			Element filterElement = (Element) filterNode;

			Element filterMappingElement = (Element) configDocument
					.getElementsByTagName("filter-mapping").item(0);

			insertNode(webDocument, "filter", "filter-name", "failedRequestFilter",
					webDocument.importNode(filterElement, true));
			insertNode(webDocument, "filter-mapping", "filter-name",
					"failedRequestFilter",
					webDocument.importNode(filterMappingElement, true));

			try {
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(webDocument);
				StreamResult file = new StreamResult(new File(outFile));
				transformer.transform(source, file);
			} catch (TransformerException e) {
				System.err.println("Exception (ignored): " + e.toString());
			}

			if (debug) {
				System.err.println("Updated the " + outFile);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = MergeDocumentFragments.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = MergeDocumentFragments.class.getClassLoader()
					.getResource(pagename).toURI();
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static void insertNode(Document webDocument, String tag1, String tag2,
			String text, Node newChild4) {
		NodeList nodeList2 = webDocument.getElementsByTagName(tag1);
		int maxcnt2 = nodeList2.getLength();
		boolean done = false;
		for (int cnt2 = 0; cnt2 < maxcnt2; cnt2++) {
			Node node2 = nodeList2.item(cnt2);
			if (node2.getNodeType() == Node.ELEMENT_NODE) {
				Element element2 = (Element) node2;
				if (debug) {
					System.err.println("Exploring node: " + node2.getNodeName());
				}
				NodeList nodeList3 = node2.getOwnerDocument()
						.getElementsByTagName(tag2);
				if (debug) {
					System.err.println("Subnode list length: " + nodeList3.getLength());
				}
				int maxcnt3 = nodeList3.getLength();
				for (int cnt3 = 0; cnt3 < maxcnt3; cnt3++) {
					if (!done) {
						Node node3 = nodeList3.item(cnt3);
						if (debug) {
							System.err
									.println("Exploring nested node: " + node3.getNodeName());
						}
						if (node3.getTextContent().equalsIgnoreCase(text)) {
							if (debug) {
								System.err.println(
										String.format("Found text %s (index %d)", text, cnt3));
							}
							element2.getParentNode().insertBefore(newChild4,
									element2.getNextSibling());
							if (debug) {
								System.err.println("Added " + newChild4.getNodeName());
							}

							done = true;
						}
					}
				}
			}
		}
	}
}
