package example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	private static DocumentBuilder dBuilder = null;
	private static Document doc = null;
	private static final Map<String, List<List<String>>> connectors = new HashMap<>();
	private static final Map<String, String> shapes = new HashMap<>();
	private static NodeList nodeList1, nodeList2, nodeList3, nodeList4;
	private static Element element1, element2, element3, element4;
	private static Node node1, node2, node3, node4;
	private static Set connectorIds;
	private static Deque<String> others = new ArrayDeque<>();
	private static List<List<String>> entries = new ArrayList<>();
	private static final List<List<String>> edges = new ArrayList<>();
	private static final Map<String, List<List<String>>> looseConnectors = new HashMap<>();
	private static String id, text, fromCell, toCell, fromSheet, toSheet, label, connectorId, beginTarget, endTarget;
	private static List<String> texts = new ArrayList<>();

	public static void main(String[] args) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		try {
			doc = dBuilder.parse(new File("example.xml"));
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		doc.getDocumentElement().normalize();
		nodeList1 = doc.getElementsByTagName("Shapes");

		for (int i1 = 0; i1 < nodeList1.getLength(); i1++) {
			node1 = nodeList1.item(i1);
			if (node1.getNodeType() == Node.ELEMENT_NODE) {
				element1 = (Element) node1;

				nodeList2 = element1.getElementsByTagName("Shape");

				for (int i2 = 0; i2 < nodeList2.getLength(); i2++) {
					node2 = nodeList2.item(i2);

					if (node2.getNodeType() == Node.ELEMENT_NODE) {
						element2 = (Element) node2;

						id = element2.getAttribute("ID");
						texts = new ArrayList<>();
						nodeList3 = element2.getElementsByTagName("Text");

						for (int i3 = 0; i3 < nodeList3.getLength(); i3++) {
							node3 = nodeList3.item(i3);
							element3 = (Element) node3;

							text = element3.getTextContent();
							if (text != null)
								texts.add(text.trim());
						}
						text = String.join(" ", texts);
						logger.debug("Shape ID: {}, Text: {}", id, text);
						shapes.put(id, text);
					}
				}
			}
		}
		for (Entry<String, String> shape : shapes.entrySet()) {
			text = shape.getValue();
			if (text.isEmpty())
				text = "?";
			System.err.println(String.format("N%s[\"%s\"]", shape.getKey(), text));
		}
		doc.getDocumentElement().normalize();

		nodeList1 = doc.getElementsByTagName("Connects");
		int cnt = 0;
		for (int i1 = 0; i1 < nodeList1.getLength(); i1++) {
			node1 = nodeList1.item(i1);

			if (node1.getNodeType() == Node.ELEMENT_NODE) {
				element1 = (Element) node1;
				nodeList2 = element1.getElementsByTagName("Connect");
				for (int i2 = 0; i2 < nodeList2.getLength(); i2++) {
					node2 = nodeList2.item(i2);
					if (node2.getNodeType() == Node.ELEMENT_NODE) {
						element2 = (Element) node2;
						// Connect@ID is not provided
						cnt++;
						id = String.valueOf(cnt);
						fromSheet = element2.getAttribute("FromSheet");
						toSheet = element2.getAttribute("ToSheet");
						toCell = element2.getAttribute("ToCell");
						fromCell = element2.getAttribute("FromCell").toLowerCase();
						if (fromCell == null)
							fromCell = "";
						if (fromSheet == null || toSheet == null)
							continue;
						if (!connectors.containsKey(fromSheet))
							connectors.put(fromSheet, new ArrayList<List<String>>());
						connectors.get(fromSheet).add(Arrays.asList(fromCell, toSheet));
						logger.debug("Connect ID: {}, FromSheet: {}, FromCell: {}, ToSheet: {}, ToCell: {}", id,
								fromSheet, fromCell, toSheet, (toCell != null ? toCell : "?"));
					}
				}
			}
		}
		connectorIds = connectors.keySet();
		for (Map.Entry<String, List<List<String>>> connector : connectors.entrySet()) {
			id = connector.getKey();
			entries = connector.getValue();
			beginTarget = null;
			endTarget = null;
			others.clear();

			for (List<String> entry : entries) {
				fromCell = entry.get(0);
				toSheet = entry.get(1);
				if (fromCell.startsWith("begin"))
					beginTarget = toSheet;
				else if (fromCell.startsWith("end"))
					endTarget = toSheet;
				else
					others.push(endTarget);
				if (beginTarget == null && !others.isEmpty())
					beginTarget = others.pop();
				if (endTarget == null && !others.isEmpty())
					endTarget = others.pop();
				if (endTarget != null && beginTarget != null && !beginTarget.equals(endTarget)) {
					text = shapes.get(id);
					edges.add(Arrays.asList(beginTarget, endTarget, text));
				} else
					looseConnectors.put(id, entries);
			}
		}
		for (List<String> edge : edges) {
			beginTarget = edge.get(0);
			endTarget = edge.get(1);
			text = edge.get(2);
			System.err.println(String.format("N%s --> N%s", beginTarget, endTarget));
		}
	}
}
