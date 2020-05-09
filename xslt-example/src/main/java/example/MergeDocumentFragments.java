package example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
// https://www.programcreek.com/java-api-examples/?class=org.w3c.dom.Document&method=importNode

public class MergeDocumentFragments {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	private static final String newName = "responseHeadersFilter";

	public static void main(String args[]) {
		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("in");
		commandLineParser.saveFlagValue("out");
		commandLineParser.saveFlagValue("tag1");
		commandLineParser.saveFlagValue("tag2");
		commandLineParser.saveFlagValue("tag3");
		commandLineParser.saveFlagValue("name");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
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

		String outFile = commandLineParser.getFlagValue("out");
		if (outFile == null) {
			System.err.println("Missing required argument: out");
		}
		if (commandLineParser.getFlagValue("in") == null) {
			System.err.println("Missing required argument: in");
		}
		String inputUri = Paths.get(commandLineParser.getFlagValue("in")).toUri().toString();
		if (debug) {
			System.err.println("Loaded: " + inputUri);
		}
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputUri);

			if (searchNode(document, tag1, tag3, newName)) {
				if (debug) {
					System.err.println(
							String.format("Already have the node: //%s/%s[text()=\"%s\"]", tag1, tag3, newName));
					return;
				}
			}
			Document configDocument = documentBuilder.parse(Utils.getPageContent("fragment.xml"));

			insertNode(document, tag1, tag3, name,
					document.importNode((Element) configDocument.getElementsByTagName(tag1).item(0), true));
			insertNode(document, tag2, tag3, name,
					document.importNode((Element) configDocument.getElementsByTagName(tag2).item(0), true));

			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
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

	static boolean searchNode(Document document, String tag1, String tag2, String nodeText) {
		boolean status = false;
		NodeList nodeList1 = document.getElementsByTagName(tag1);
		int maxcnt1 = nodeList1.getLength();
		if (maxcnt1 != 0) {
			for (int cnt1 = 0; cnt1 < maxcnt1; cnt1++) {
				Node node1 = nodeList1.item(cnt1);
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
								status = true;
							}
						}
					}
				}
			}
		}
		return status;
	}

	private static void insertNode(Document document, String tag1, String tag2, String nodeText, Node newChild) {
		NodeList nodeList1 = document.getElementsByTagName(tag1);
		int maxcnt1 = nodeList1.getLength();
		if (maxcnt1 == 0) {
			// Put as the first child in the document - no similar tag names
			nodeList1 = document.getChildNodes();
			int maxcnt2 = nodeList1.getLength();
			if (maxcnt2 == 0) {
				if (debug) {
					System.err.println("Adding to document" + newChild.getNodeName());
				}
				document.appendChild(newChild);
				// NOTE: probably never here
			} else {
				for (int cnt1 = 0; cnt1 < maxcnt2; cnt1++) {
					Node node1 = nodeList1.item(cnt1);
					if (node1.getNodeType() == Node.ELEMENT_NODE) {
						try {
							Element element1 = (Element) node1;
							if (debug) {
								System.err.println("Adding child node to " + element1.getNodeName() + " "
										+ newChild.getNodeName());
							}
							element1.appendChild(newChild);
						} catch (ClassCastException e) {
							// com.sun.org.apache.xerces.internal.dom.DeferredCommentImpl cannot be cast to
							// org.w3c.dom.Element
							// when not filtering by node type
							System.err.println("Exception (ignored): " + e.toString());
						} catch (DOMException e) {
							// HIERARCHY_REQUEST_ERR:
							// An attempt was made to insert a node where it is not permitted
							// when adding after root element
							System.err.println("Exception (ignored): " + e.toString());
						}
					}
				}
			}
		} else {
			boolean done = false;
			for (int cnt1 = 0; cnt1 < maxcnt1; cnt1++) {
				Node node1 = nodeList1.item(cnt1);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element element1 = (Element) node1;
					if (debug) {
						System.err.println("Exploring node: " + node1.getNodeName());
					}
					NodeList nodeList2 = node1.getOwnerDocument().getElementsByTagName(tag2);
					if (debug) {
						System.err.println("Subnode list length: " + nodeList2.getLength());
					}
					int maxcnt2 = nodeList2.getLength();
					for (int cnt2 = 0; cnt2 < maxcnt2; cnt2++) {
						if (!done) {
							Node node2 = nodeList2.item(cnt2);
							if (debug) {
								System.err.println("Exploring nested node: " + node2.getNodeName());
							}
							if (node2.getTextContent().equalsIgnoreCase(nodeText)) {
								if (debug) {
									System.err.println(String.format("Found text %s (index %d)", nodeText, cnt2));
								}
								element1.getParentNode().insertBefore(newChild, element1.getNextSibling());
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
