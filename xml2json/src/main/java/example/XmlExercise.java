package example;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.jdom.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmlExercise {
	public static int PRETTY_PRINT_INDENT_FACTOR = 4; // String 返回的空格数量，方便直接转换为
																										// json

	// Exception in thread "main" java.lang.NoClassDefFoundError:
	// nu/xom/ParentNode at
	// com.yhxd.tools.XmlExercise.xml2json(XmlExercise.java:25)
	public static String xml2json(String xmlString) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xmlString);
		return json.toString(1);
	}

	public static String xml2json(Document xmlDocument) {
		return xml2json(xmlDocument.toString());
	}

	public static String json2xml(String jsonString) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
		// return
		// xmlSerializer.write(JSONArray.fromObject(jsonString));//这种方式只支持JSON数组
	}

	public static com.alibaba.fastjson.JSONObject xml2fastjson(
			String xmltostring) {
		String jsonPrettyPrintString = "";
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(xmltostring);
			jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return com.alibaba.fastjson.JSON.parseObject(jsonPrettyPrintString);
	}
}