package com.github.abhishek8908.util;
/**
 * Copyright 2014 - 2017 Serguei Kouzmine
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common configuration / properties file parser
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class PropertiesParser {

	/*
	
		private static String propertiesFileName = "test.properties";
		Map<String, String> propertiesMap = PropertiesParser
						.getProperties(String.format("%s/src/main/resources/%s",
								System.getProperty("user.dir"), propertiesFileName));
		String username = propertiesMap.get("username");
		String password =  propertiesMap.get("password");
		StringBuilder loggingSb = new StringBuilder();
		Formatter formatter = new Formatter(loggingSb, Locale.US)
	*/
	protected static String getFileContent(String propertiesFileName,
			boolean fromTheJar) {
		final InputStream stream;
		try {
			if (fromTheJar) {
				stream = /* this.getClass().getClassLoader()*/
						PropertiesParser.class.getClassLoader()
								.getResourceAsStream(propertiesFileName);
			} else {
				stream = new FileInputStream(propertiesFileName);
			}

			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (

		IOException e) {
			throw new RuntimeException(propertiesFileName);
		}
	}

	public static Map<String, String> getProperties(
			final String propertiesFileName) {
		System.err.println(String.format("Reading properties file: \"%s\"",
				String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName)));
		Map<String, String> propertiesMap = PropertiesParser
				.getPropertiesBroken(String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));
		StringBuilder loggingSb = new StringBuilder();
		Formatter formatter = new Formatter(loggingSb, Locale.US);
		return (propertiesMap);
	}

	public static Map<String, String> getPropertiesBroken(
			final String propertiesFileName, boolean fromTheJar) {
		final InputStream stream;
		Properties p = new Properties();
		Map<String, String> propertiesMap = new HashMap<>();
		System.err.println("contents:" + getFileContent(propertiesFileName, false));
		try {
			System.err.println(
					String.format("Reading properties file: '%s'", propertiesFileName));
			// only works when jar has been packaged?
			if (fromTheJar) {
				stream = PropertiesParser.class.getClassLoader()
						.getResourceAsStream(propertiesFileName);
			} else {
				stream = new FileInputStream(String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));
			}
			p.load(stream);
			@SuppressWarnings("unchecked")
			Enumeration<String> e = (Enumeration<String>) p.propertyNames();
			for (; e.hasMoreElements();) {
				String key = e.nextElement();
				String val = p.get(key).toString();
				System.out.println(String.format("Reading: '%s' = '%s'", key, val));
				propertiesMap.put(key, resolveEnvVars(val));
			}

		} catch (FileNotFoundException e) {
			System.err.println(String.format("Properties file was not found: '%s'",
					propertiesFileName));
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(String.format("Properties file is not readable: '%s'",
					propertiesFileName));
			e.printStackTrace();
		}
		return (propertiesMap);
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
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