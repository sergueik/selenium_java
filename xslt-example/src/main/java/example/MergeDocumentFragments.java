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
		commandLineParser.saveFlagValue("tag1");
		commandLineParser.saveFlagValue("tag2");
		commandLineParser.saveFlagValue("tag3");
		commandLineParser.saveFlagValue("name");

		String tag1 = commandLineParser.getFlagValue("tag1");
		if (tag1 == null) {
			tag1 = "filter";
		}
		String tag2 = commandLineParser.getFlagValue("tag2");
		if (tag2 == null) {
			tag2 = "filter-mapping";
		}
		String tag3 = commandLineParser.getFlagValue("tag3");
		if (tag3 == null) {
			tag3 = "filter-name";
		}
		String name = commandLineParser.getFlagValue("name");
		if (name == null) {
			name = "failedRequestFilter";
		}

		commandLineParser.parse(args);
		String outFile = commandLineParser.getFlagValue("out");
		String inputUri = Paths.get(commandLineParser.getFlagValue("in")).toUri()
				.toString();
		if (debug) {
			System.err.println("Loaded: " + inputUri);
		}
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(inputUri);
			Document configDocument = documentBuilder
					.parse(getPageContent("fragment.xml"));

			insertNode(document, tag1, tag3, name, document.importNode(
					(Element) configDocument.getElementsByTagName(tag1).item(0), true));
			insertNode(document, tag2, tag3, name, document.importNode(
					(Element) configDocument.getElementsByTagName(tag2).item(0), true));

			try {
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(document);
				StreamResult file = new StreamResult(new File(outFile));
				transformer.transform(source, file);
			} catch (TransformerException e) {
				System.err.println("Exception (ignored): " + e.toString());
			}

			if (debug) {
				System.err.println("Written: " + outFile);
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
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

	private static void insertNode(Document document, String tag1, String tag2,
			String nodeText, Node newChild) {
		NodeList nodeList1 = document.getElementsByTagName(tag1);
		int maxcnt1 = nodeList1.getLength();
		if (maxcnt1 == 0) {
			// Put as the first child is an Element.
			((Element) document.getFirstChild()).appendChild(newChild);
		} else {
			boolean done = false;
			for (int cnt1 = 0; cnt1 < maxcnt1; cnt1++) {
				Node node1 = nodeList1.item(cnt1);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element element1 = (Element) node1;
					if (debug) {
						System.err.println("Exploring node: " + node1.getNodeName());
					}
					NodeList nodeList2 = node1.getOwnerDocument()
							.getElementsByTagName(tag2);
					if (debug) {
						System.err.println("Subnode list length: " + nodeList2.getLength());
					}
					int maxcnt2 = nodeList2.getLength();
					for (int cnt2 = 0; cnt2 < maxcnt2; cnt2++) {
						if (!done) {
							Node node2 = nodeList2.item(cnt2);
							if (debug) {
								System.err
										.println("Exploring nested node: " + node2.getNodeName());
							}
							if (node2.getTextContent().equalsIgnoreCase(nodeText)) {
								if (debug) {
									System.err.println(String.format("Found text %s (index %d)",
											nodeText, cnt2));
								}
								element1.getParentNode().insertBefore(newChild,
										element1.getNextSibling());
								if (debug) {
									System.err.println("Added " + newChild.getNodeName());
								}
								done = true;
							}
						}
					}
				}
			}
		}
	}
}
