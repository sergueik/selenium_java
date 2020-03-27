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

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
// https://www.programcreek.com/java-api-examples/?class=org.w3c.dom.Document&method=importNode
public class MergeDocumentFragments {

	public static void main(String args[]) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

			String inputPath = Paths.get(args[0]).toUri().toString();
			System.err.println("Loading input: " + inputPath);
			Document webDocument = documentBuilder.parse(inputPath);
			Document configDocument = documentBuilder
					.parse(getPageContent("fragment.xml"));
			// Assume the first child is an Element.
			Element web = (Element) webDocument.getFirstChild();
			NodeList filterNodeList = configDocument.getElementsByTagName("filter");
			Node filterNode = filterNodeList.item(0);
			Element filterElement = (Element) filterNode;
			web.appendChild(webDocument.importNode(filterElement, true));

			NodeList filterMappingNodeList = configDocument
					.getElementsByTagName("filter-mapping");
			Node filterMappingNode = filterMappingNodeList.item(0);
			Element filterMappingElement = (Element) filterMappingNode;
			web.appendChild(webDocument.importNode(filterMappingElement, true));

			try {
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(webDocument);
				StreamResult file = new StreamResult(new File(args[1]));
				transformer.transform(source, file);
			} catch (TransformerException e) {
				System.err.println("Exception (ignored): " + e.toString());

			}

			System.out.println("Updated the " + args[1]);

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
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

}
