package xml2json;

import java.io.File;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import xml2json.Converter;

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
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new File(argv[0]), converter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
