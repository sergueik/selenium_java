package org.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
	public static String requiredKey = "key";

	public static String writeDataJSON(Map<String, String> data, String defaultData) {
		String result = defaultData;
		JSONObject json = new JSONObject();
		try {
			for (String key : data.keySet()) {
				json.put(key, data.get(key));
			}
			StringWriter wr = new StringWriter();
			json.write(wr);
			result = wr.toString();
		} catch (JSONException e) {
			System.err.println("Excpetion (ignored): " + e);
		}
		return result;
	}

	public static String readData(Optional<Map<String, String>> parameters) {
		return readData(null, parameters);
	}

	public static String readData(String payload,
			Optional<Map<String, String>> parameters) {

		Boolean collectResults = parameters.isPresent();
		Map<String, String> collector = (collectResults) ? parameters.get()
				: new HashMap<>();

		String data = (payload == null) ? "{  }" // empty
				: payload;
		try {
			JSONObject elementObj = new JSONObject(data);
			@SuppressWarnings("unchecked")
			Iterator<String> propIterator = elementObj.keys();
			while (propIterator.hasNext()) {
				String propertyKey = propIterator.next();

				String propertyVal = elementObj.getString(propertyKey);
				System.err.println(propertyKey + ": " + propertyVal);
				collector.put(propertyKey, propertyVal);
			}
		} catch (JSONException e) {
			System.err.println("Ignored exception: " + e.toString());
			return null;
		}
		return collector.get(requiredKey);
	}

}
