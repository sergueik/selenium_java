package example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
// https://qna.habr.com/q/726053
import org.w3c.dom.NodeList;

public class Noop {

	public static void main(String[] args)
			throws IOException, URISyntaxException, TransformerException {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.err.println(String.format("transforming %s	", args[0]));

			Document document = builder.parse(new File(args[0]));
			document.getDocumentElement().normalize();
			Element root = document.getDocumentElement();
			String tagName = "filter";
			String attrributeName = "stepName";
			String attrributeValue = "attribute value";
			// https://stackoverflow.com/questions/3405055/java-dom-inserting-an-element-after-another
			Element newElement = document.createElement(tagName);
			// Element to be inserted
			newElement.setAttribute(attrributeName, attrributeValue);

			String searchTag = "filter";
			String searchNameAttribute = "some name";
			NodeList nodeList = document.getElementsByTagName(searchTag);
			for (int cnt = 0; cnt < nodeList.getLength(); ++cnt) {
				Node node1 = nodeList.item(cnt);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element element1 = (Element) node1;
					if (element1.getAttribute("name").equals(searchNameAttribute)) {
						Node newChild = (Node) newElement;
						Node refChild = element1.getNextSibling();
						element1.getParentNode().insertBefore(newChild, refChild);
					}
				}
			}
			// NOTE: no querySelectorAll
			String searchTag1 = "filter";
			String searchTag2 = "filter-name";
			NodeList nodeList2 = document.getElementsByTagName(searchTag);
			for (int cnt2 = 0; cnt2 < nodeList2.getLength(); ++cnt2) {
				Node node2 = nodeList2.item(cnt2);
				if (node2.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node2;
					NodeList nodeList3 = node2.getOwnerDocument()
							.getElementsByTagName(searchTag2);
					for (int cnt3 = 0; cnt3 < nodeList3.getLength(); ++cnt3) {
						Node node3 = nodeList3.item(cnt3);
						if (node3.getNodeType() == Node.ELEMENT_NODE) {
							Element element3 = (Element) node3;
							if (element3.getTextContent()
									.equalsIgnoreCase("failedRequestFilter")) {
								Node newChild2 = (Node) newElement;
								Node refChild2 = element2.getNextSibling();
								element2.getParentNode().insertBefore(newChild2, refChild2);
							}
						}
					}
				}
			}
			root.appendChild(Elements.getCountry(document, 10));
			document.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult file = new StreamResult(new File(args[1]));
			transformer.transform(source, file);
			System.out.println("Updated the " + args[1]);
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.toString());

			e.printStackTrace();
		}
	}

	private static class Country {
		private int id;
		private String continent;
		private String name;
		private int area;
		private int population;
		private String minerals;

		public Country(int id) {
			this.id = id;
		}

		public void setId(int data) {
			id = data;
		}

		public int getId() {
			return id;
		}

		public void setContinent(String data) {
			continent = data;
		}

		public void setName(String data) {
			name = data;
		}

		public void setArea(int data) {
			area = data;
		}

		public void setPopulation(int data) {
			population = data;
		}

		public void setMinerals(String data) {
			minerals = data;
		}

		public String getContinent() {
			return continent;
		}

		public String getName() {
			return name;
		}

		public int getArea() {
			return area;
		}

		public int getPopulation() {
			return population;
		}

		public String getMinerals() {
			return minerals;
		}
	}

	private static class Elements {
		public static Node getCountry(Document doc, int id) {
			String continent = "x";
			String name = "x";
			String area = "x";
			String population = "x";
			String minerals = "x";

			Element country = doc.createElement("country");
			country.setAttribute("id", Integer.toString(id));
			country.appendChild(getCountryElement(doc, "continent", continent));
			country.appendChild(getCountryElement(doc, "name", name));
			country.appendChild(getCountryElement(doc, "area", area));
			country.appendChild(getCountryElement(doc, "population", population));
			country.appendChild(getCountryElement(doc, "minerals", minerals));
			return country;
		}

		private static Node getCountryElement(Document doc, String name,
				String value) {
			Element node = doc.createElement(name);
			node.appendChild(doc.createTextNode(value));
			return node;
		}
	}
}
