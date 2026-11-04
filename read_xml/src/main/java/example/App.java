package example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.List;
import java.util.Map;

public class App {
	public static void main(String[] args) {
		Map<String, String> shapes = new HashMap<>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(new File("example.xml"));
			doc.getDocumentElement().normalize(); // Recommended to prevent parsing anomalies

			NodeList nodeList1 = doc.getElementsByTagName("Shapes");

			for (int i1 = 0; i1 < nodeList1.getLength(); i1++) {
				Node node1 = nodeList1.item(i1);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element element1 = (Element) node1;

					NodeList nodeList2 = element1.getElementsByTagName("Shape");

					for (int i2 = 0; i2 < nodeList2.getLength(); i2++) {
						Node node2 = nodeList2.item(i2);

						if (node2.getNodeType() == Node.ELEMENT_NODE) {
							Element element2 = (Element) node2;

							String id2 = element2.getAttribute("ID");
							List<String> texts2 = new ArrayList<>();
							NodeList nodeList3 = element2.getElementsByTagName("Text");

							for (int i3 = 0; i3 < nodeList3.getLength(); i3++) {
								Node node3 = nodeList3.item(i3);
								Element element3 = (Element) node3;

								String text3 = element3.getTextContent();
								if (text3 != null)
									texts2.add(text3.trim());
							}
							System.out.println("Shape ID: " + id2 + ", Text: " + String.join(" ", texts2));
							shapes.put(id2, String.join(" ", texts2));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, List<List<String>>> connectors = new HashMap<>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(new File("example.xml"));
			doc.getDocumentElement().normalize(); // Recommended to prevent parsing anomalies

			NodeList nodeList1 = doc.getElementsByTagName("Connects");

			for (int i1 = 0; i1 < nodeList1.getLength(); i1++) {
				Node node1 = nodeList1.item(i1);

				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element element1 = (Element) node1;
					NodeList nodeList2 = element1.getElementsByTagName("Connect");
					for (int i2 = 0; i2 < nodeList2.getLength(); i2++) {
						Node node2 = nodeList2.item(i2);
						if (node2.getNodeType() == Node.ELEMENT_NODE) {
							Element element2 = (Element) node2;

							String id = element2.getAttribute("ID");
							String fromSheet = element2.getAttribute("FromSheet");
							String toSheet = element2.getAttribute("ToSheet");
							String fromCell = element2.getAttribute("FromCell").toLowerCase();
							if (fromCell == null)
								fromCell = "";
							if (fromSheet == null || toSheet == null)
								continue;
							if (!connectors.containsKey(fromSheet))
								connectors.put(fromSheet, new ArrayList<List<String>>());
							connectors.get(fromSheet).add(Arrays.asList(fromCell, toSheet));
							System.out.println("Connect ID: " + id + ", FromSheet: " + fromSheet + ", FromCell: "
									+ fromCell + ", ToSheet: " + toSheet);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<List<String>> edges = new ArrayList<>();
		Set connectorIds = connectors.keySet();
		for (Map.Entry<String, List<List<String>>> connector : connectors.entrySet()) {
			String connectorId = connector.getKey();
			List<List<String>> entries = connector.getValue();
			String beginTarget = null;
			String endTarget = null;
			Deque<String> others = new ArrayDeque<>();

			for (List<String> entry : entries) {
				String fromCell = entry.get(0);
				String toSheet = entry.get(1);
				if (fromCell.startsWith("begin"))
					beginTarget = toSheet;
				else if (fromCell.startsWith("end"))
					endTarget = toSheet;
				else
					others.push(endTarget);
				if (beginTarget == null && others.size() != 0)
					beginTarget = others.pop();
				if (endTarget == null && others.size() != 0)
					endTarget = others.pop();
				if (endTarget != null && beginTarget != null && !beginTarget.startsWith(endTarget)) {
					String label = shapes.get(connectorId);
					edges.add(Arrays.asList(beginTarget, endTarget, label));

				}
			}
		}
	}
}
