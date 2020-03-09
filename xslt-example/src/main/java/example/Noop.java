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
