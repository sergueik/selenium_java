package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class XmlTest {
	public static String json2xml(String jsonString) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		// Exception in thread "main" java.lang.NoClassDefFoundError:
		// nu/xom/ParentNode
		return xmlSerializer.write(JSONSerializer.toJSON(jsonString));

	}

	public static void main(String[] argv) {
		if (argv.length < 1) {
			System.out.println("Usage: java -jar xml2json.jar input.xml output.json");
			return;
		}
		try {

			PrintStream fileoutStream = null;

			File file = new File(argv[0]);
			StringBuffer contents = new StringBuffer();
			BufferedReader reader = null;

			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(System.getProperty("line.separator"));
			}
			reader.close();
			System.out.println(json2xml(contents.toString()));

			/*
			if (argv.length > 1) {
				fileoutStream = new PrintStream(argv[1]);
				json2xml();
				converter.setPrintStream(fileoutStream);
			} else {
				converter.setPrintStream(System.out);
			}
			*/

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}