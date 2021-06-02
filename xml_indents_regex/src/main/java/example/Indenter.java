package example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

// origin: https://github.com/jlam55555/xml-pretty-print (broken)
// https://github.com/zsoltkiss/xml-pretty-print
public class Indenter {
	public static void main(String args[]) {

		try {

			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = dbf.newDocumentBuilder();

			// System.err.println("Reading File...");
			Document doc = builder.parse(new File(args[0]));

			final Element root = doc.getDocumentElement();

			final String data = Indenter.dom2String(root);
			// System.out.println(data);

			// System.err.println("Writing File...");
			final BufferedWriter bufferedWriter = Files
					.newBufferedWriter(Paths.get(args[1]), Charset.forName("UTF-8"));
			bufferedWriter.write(data, 0, data.length());
			bufferedWriter.close();

		} catch (IOException | TransformerException | SAXException
				| ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static String dom2String(Node node) throws TransformerException {
		final Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final StreamResult result = new StreamResult(new StringWriter());
		final DOMSource source = new DOMSource(node);
		transformer.transform(source, result);
		return result.getWriter().toString();
	}

}
