package xml2json;

import java.io.PrintStream;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Converter extends DefaultHandler {
	private boolean isFirstChild = false;
	private PrintStream stream;

	public void setPrintStream(PrintStream fileoutStream) {
		this.stream = fileoutStream;
	}

	public void startDocument() {
		stream.print("{ \"name\": \"@document\", \"contents\": [");
		isFirstChild = true;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		stream.println(isFirstChild ? "" : ",");
		stream.print("{ \"name\": \"");
		stream.print(qName);
		stream.print("\", \"attributes\": {");
		int n = attributes.getLength();
		for (int i = 0; i < n; ++i) {
			stream.print("\"");
			stream.print(attributes.getLocalName(i));
			stream.print("\": \"");
			stream.print(attributes.getValue(i));
			stream.print("\"");
			if (i + 1 < n) {
				stream.print(",");
			}
		}
		stream.print("}, \"contents\": [");
		isFirstChild = true;
	}

	public void characters(char[] ch, int offset, int length) {
		String text = new String(ch, offset, length).trim();
		if (text.length() > 0) {
			stream.println(isFirstChild ? "" : ",");
			stream.print("{ \"name\": \"@text\", \"value\": \"");
			stream.print(text);
			stream.print("\" }");
			isFirstChild = false;
		}
	}

	public void endElement(String uri, String localName, String qName) {
		stream.print("] }");
		isFirstChild = false;
	}

	public void endDocument() {
		stream.println("] }");
		isFirstChild = false;
	}
}
