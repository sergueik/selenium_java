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
import java.io.StringReader;
import java.net.URISyntaxException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
// https://qna.habr.com/q/726053
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

// http://magicmonster.com/kb/prg/java/xml/dom/merging_nodes_diff_docs.html
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
			// NOTE: no querySelectorAll
			String searchTag1 = "filter";
			String searchTag2 = "filter-name";
			NodeList nodeList2 = document.getElementsByTagName(searchTag1);
			int nodeListLength = nodeList2.getLength();
			for (int cnt2 = 0; cnt2 < nodeListLength; ++cnt2) {
				Node node2 = nodeList2.item(cnt2);
				if (node2.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node2;
					NodeList nodeList3 = node2.getOwnerDocument()
							.getElementsByTagName(searchTag2);
					System.err.println("node list length : " + nodeList3.getLength());
					for (int cnt3 = 0; cnt3 < /* nodeList3.getLength( )*/ 3; ++cnt3) {
						Node node3 = nodeList3.item(cnt3);
						if (node3.getNodeType() == Node.ELEMENT_NODE) {
							Element element3 = (Element) node3;
							if (element3.getTextContent()
									.equalsIgnoreCase("failedRequestFilter")) {
								String tagName = "filter";
								// https://stackoverflow.com/questions/3405055/java-dom-inserting-an-element-after-another

								String xmlRecords = "<data><filter>"
										+ "<filter-name>responseHeadersFilter</filter-name>"
										+ "<filter-class>example.ResponseHeadersFilter</filter-class>"
										+ "<init-param>" + "<param-name>Expires</param-name>"
										+ "<param-value>0</param-value>" + "</init-param>"
										+ "</filter>" + "<filter-mapping>"
										+ "<filter-name>responseHeadersFilter</filter-name>"
										+ "<url-pattern>/*</url-pattern>" + "</filter-mapping>"
										+ "</data>";

								DocumentBuilder db = DocumentBuilderFactory.newInstance()
										.newDocumentBuilder();
								InputSource is = new InputSource();
								is.setCharacterStream(new StringReader(xmlRecords));

								Document doc = db.parse(is);
								Element newElement = document.createElement(tagName);

								Node newChild2 = doc.getElementsByTagName("filter").item(0);
								// newElement.appendChild(newChild2);
								//
								// Node newChild4 = (Node) newElement;

								Node newChild4 = NewFilter.addfilterElement(document, 0);

								Node refChild2 = element2.getNextSibling();
								System.err.println("Adding " + cnt3);
								element2.getParentNode().insertBefore(newChild4, refChild2);
								/*
								 Exception (ignored): 
								 org.w3c.dom.DOMException: WRONG_DOCUMENT_ERR: 
								 A node is used in a different document than the one that created it.
								 */
							}
						}
					}
				}
			}
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

	private static class NewFilter {
		public static Node addfilterElement(Document doc, int id) {
			String filterName = "responseHeadersFilter";
			String filterClass = "example.ResponseHeadersFilter";
			String paramName = "Expires";
			String paramValue = "0";

			Element filterElement = doc.createElement("filter");
			// filterElement.setAttribute("id", Integer.toString(id));
			filterElement
					.appendChild(setInnerElement(doc, "filter-name", filterName));
			filterElement
					.appendChild(setInnerElement(doc, "filter-class", filterClass));
			Node param = doc.createElement("init-param");
			param.appendChild(setInnerElement(doc, "param-name", paramName));
			param.appendChild(setInnerElement(doc, "param-value", paramValue));

			filterElement.appendChild(param);

			return filterElement;
		}

		private static Node setInnerElement(Document doc, String name,
				String value) {
			Element node = doc.createElement(name);
			node.appendChild(doc.createTextNode(value));
			return node;
		}
	}
}
