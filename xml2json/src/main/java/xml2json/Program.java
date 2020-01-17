package xml2json;

import java.io.File;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.PrintStream;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Program {
	public static void main(String[] argv) {
		if (argv.length < 1) {
			System.out.println("Usage: java -jar xml2json.jar input.xml output.json");
			return;
		}
		try {
			Converter converter = new Converter();
			PrintStream fileoutStream = null;
			if (argv.length > 1) {
				fileoutStream = new PrintStream(argv[1]);
				converter.setPrintStream(fileoutStream);
			} else {
				converter.setPrintStream(System.out);
			}
			SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
			SAXParser parser = sParserFactory.newSAXParser();
			parser.parse(new File(argv[0]), converter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class Converter extends DefaultHandler {
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

}
