package example;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import example.MergeDocumentFragments;

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
public class RemoveNode {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args) throws IOException, URISyntaxException {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("in");
		commandLineParser.saveFlagValue("out");
		commandLineParser.saveFlagValue("tag1");
		commandLineParser.saveFlagValue("tag3");
		commandLineParser.saveFlagValue("name");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String outFile = commandLineParser.getFlagValue("out");
		if (outFile == null) {
			System.err.println("Missing required argument: out");
			return;
		}

		String tag1 = commandLineParser.getFlagValue("tag1");
		if (tag1 == null) {
			tag1 = "filter";
		}
		String tag3 = commandLineParser.getFlagValue("tag3");
		if (tag3 == null) {
			tag3 = "filter-name";
		}
		String name = commandLineParser.getFlagValue("name");
		if (name == null) {
			name = "responseHeadersFilter";
		}

		if (commandLineParser.getFlagValue("in") == null) {
			System.err.println("Missing required argument: in");
			return;
		}
		String inputUri = Paths.get(commandLineParser.getFlagValue("in")).toUri().toString();
		if (debug) {
			System.err.println("Loaded: " + inputUri);
		}
		Transformer transformer = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputUri);
			if (!MergeDocumentFragments.searchNode(document, tag1, tag3, name)) {
				if (debug) {
					System.err.println(String.format("Not found the node: //%s/%s[text()=\"%s\"]", tag1, tag3, name));
				}
				return;

			}
			removeNode(document, tag1, tag3, name);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult file = new StreamResult(new File(outFile));
			transformer.transform(source, file);
		} catch (ParserConfigurationException | TransformerException | SAXException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}

		if (debug) {
			System.err.println("Written: " + outFile);
		}
	}

	// based on
	// http://www.java2s.com/Tutorials/Java/XML/Delete_element_from_XML_document_using_Java_DOM.htm
	private static void removeNode(Document document, String tag1, String tag2, String nodeText) {
		if (debug) {
			System.err.println(String.format("Removing the node: //%s/%s[text()=\"%s\"]", tag1, tag2, nodeText));
		}

		Node node1 = null;
		boolean status = false;
		NodeList nodeList1 = document.getElementsByTagName(tag1);
		int maxcnt1 = nodeList1.getLength();
		if (maxcnt1 != 0) {
			for (int cnt1 = 0; cnt1 < maxcnt1; cnt1++) {
				node1 = nodeList1.item(cnt1);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					if (debug) {
						System.err.println("Exploring node: " + node1.getNodeName());
					}
					NodeList nodeList2 = ((Element) node1).getElementsByTagName(tag2);
					int maxcnt2 = nodeList2.getLength();
					if (maxcnt2 != 0) {
						for (int cnt2 = 0; cnt2 < maxcnt2; cnt2++) {
							Node node2 = nodeList2.item(cnt2);
							if (node2.getTextContent().equalsIgnoreCase(nodeText)) {
								if (debug) {
									System.err.println(String.format("Found node://%s/%s[test()=\"%s\"] %s ",
											node2.getOwnerDocument().getNodeName(), node2.getNodeName(),
											node2.getTextContent(), nodeText));
								}
								status = true;
							}
						}
					}
				}
			}
		}

		if (status && node1 != null) {
			Node parent = node1.getParentNode();
			if (debug) {
				System.err.println("Removing the node: " + node1.getNodeName());
			}
			parent.removeChild(node1);
			parent.normalize();
		}
	}

}
