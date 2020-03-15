package example;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JSON2Xml {
	private static final XMLSerializer xmlSerializer = new XMLSerializer();

	public static String json2xml(String jsonString) {
		return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
	}

	@SuppressWarnings("restriction")
	public static void main(String[] argv) {
		if (argv.length < 1) {
			System.out.println("Usage: java -jar JSON2Xml.jar input.xml [output.json]");
			return;
		}
		try {
			File file = new File(argv[0]);
			StringBuffer contents = new StringBuffer();
			BufferedReader reader = null;

			reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {
				contents.append(text).append(System.getProperty("line.separator"));
			}
			reader.close();
			String data = json2xml(contents.toString());

			if (argv.length > 1) {
				PrintStream fileoutStream = new PrintStream(argv[1]);
				fileoutStream.print(data);
				fileoutStream.flush();
				fileoutStream.close();

				if (argv.length > 2) {
					// Exception! :java.lang.IllegalAccessException: Class
					// example.Json$SafeTreeMap with modifiers "public"
					// sun.reflect.misc.Trampoline can not access a member of class
					Object jsonObject = Json.parse(contents.toString());

					// https://howtodoinjava.com/jaxb/write-object-to-xml/
					// https://stackoverflow.com/questions/14057932/javax-xml-bind-jaxbexception-class-nor-any-of-its-super-class-is-known-to-t
					try {
						// Create JAXB Context
						@SuppressWarnings("restriction")
						JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);

						// Create Marshaller
						@SuppressWarnings("restriction")
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

						// Required formatting??
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

						// Print XML String to Console
						StringWriter sw = new StringWriter();

						// Write XML to StringWriter
						jaxbMarshaller.marshal(jsonObject, sw);

						// Verify XML Content
						String xmlContent = sw.toString();
						System.out.println(xmlContent);

					} catch (JAXBException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// https://howtodoinjava.com/java/serialization/xmlencoder-and-xmldecoder-example/
	// https://qna.habr.com/q/731687
	// https://qna.habr.com/q/726053?e=8732359#clarification_847799
	private static void serializeToXML(Object settings, String fileName) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		XMLEncoder encoder = new XMLEncoder(fileOutputStream);
		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception e) {
				System.err.println("Exception :" + e.toString());
			}
		});
		encoder.writeObject(settings);
		encoder.close();
		fileOutputStream.close();
	}
}