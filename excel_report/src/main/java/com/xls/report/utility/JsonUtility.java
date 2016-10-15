package com.xls.report.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xls.report.config.ElementLocator;

public class JsonUtility {
	public static String getJsonString(String text) {
		int sIndex = text.indexOf('{');
		int eIndex = text.indexOf('}');

		return (sIndex == -1 || eIndex == -1) ? "" : text.substring(sIndex, (eIndex + 1));

	}

	public static String deserializeJsonObject(String text) {
		Gson gson = new GsonBuilder().create();
		ElementLocator locator = gson.fromJson(getJsonString(text),
				ElementLocator.class);
		return (locator == null) ? "" : locator.toString();
	}
}
