package xml2json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class JSON2Xml {
	private static final XMLSerializer xmlSerializer = new XMLSerializer();

	public static String json2xml(String jsonString) {
		return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
	}

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
			} else {
				System.out.println(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}