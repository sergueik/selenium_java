package com.github.sergueik.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Property file Reader for Selenium WebDriver Keyword Driven Library
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class PropertiesParser {
	private static boolean debug = false;

	public static Map<String, String> getProperties(final String fileName) {
		Properties p = new Properties();
		Map<String, String> propertiesMap = new HashMap<>();
		if (debug) {
			System.err
					.println(String.format("Reading properties file: '%s'", fileName));
		}
		try {
			p.load(new FileInputStream(fileName));
			@SuppressWarnings("unchecked")
			Enumeration<String> e = (Enumeration<String>) p.propertyNames();
			for (; e.hasMoreElements();) {
				String key = e.nextElement();
				String val = p.get(key).toString();
				if (debug) {
					System.err.println(String.format("Reading: '%s' = '%s'", key, val));
				}
				propertiesMap.put(key, resolveEnvVars(val));
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					String.format("Properties file was not found: '%s'", fileName));
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Properties file is not readable: '%s'", fileName));
		}
		return (propertiesMap);
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{(\\w+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = System.getenv(envVarName);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}