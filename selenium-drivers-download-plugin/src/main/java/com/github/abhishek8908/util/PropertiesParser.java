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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Common configuration / properties file parser
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class PropertiesParser {

	public static Map<String, String> getProperties(
			final String propertiesFileName, boolean fromTheJar) {
		final InputStream stream;
		Properties p = new Properties();
		Map<String, String> propertiesMap = new HashMap<>();

		try {
			// only works when jar has been packaged?
			if (fromTheJar) {
				System.err.println(String.format(
						"Reading properties file from the jar: '%s'", propertiesFileName));
				stream = PropertiesParser.class.getClassLoader()
						.getResourceAsStream(propertiesFileName);
			} else {
				System.err.println(String.format(
						"Reading properties file from disk: '%s'", propertiesFileName));
				stream = new FileInputStream(String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));
			}
			p.load(stream);
			@SuppressWarnings("unchecked")
			Enumeration<String> e = (Enumeration<String>) p.propertyNames();
			for (; e.hasMoreElements();) {
				String key = e.nextElement();
				String val = resolveEnvVars(p.get(key).toString());
				System.err.println(String.format("Reading: '%s' = '%s'", key, val));
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
		System.err.println("Interpolating " + name);
		// compatible with
		// org.apache.commons.configuration.PropertiesConfiguration.interpolatedConfiguration
		// https://commons.apache.org/proper/commons-configuration/userguide_v1.10/howto_utilities.html
		if (value == null) {

			Pattern p = Pattern.compile("^(\\w+:)(\\w+)$");
			Matcher m = p.matcher(name);
			if (m.find()) {
				String propertyName = m.replaceFirst("$2");
				System.err.println("Interpolating " + propertyName);
				value = System.getProperty(propertyName);
			}
			if (value == null) {
				System.err.println("Trying environment " + name);
				value = System.getenv(name);
				if (value == null) {
					System.err.println("Nothing found for " + name);
					value = defaultValue;
				}
			}
		}
		return value;
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{([\\w.:]+)\\}|([\\w.:]+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			System.err.println("Processing " + envVarName);
			String envVarValue = getPropertyEnv(envVarName, envVarName);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	// origin:
	// https://github.com/abhishek8908/selenium-drivers-download-plugin/blob/master/src/main/java/com/github/abhishek8908/util/DriverUtil.java
	public static String readProperty(String propertyName)
			throws ConfigurationException {
		String resourcePath = "";
		try {
			resourcePath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
		} catch (NullPointerException e) {
			System.out.println(resourcePath + e.getMessage());
		}
		Configuration config = new PropertiesConfiguration(
				resourcePath + "driver.properties");
		Configuration extConfig = ((PropertiesConfiguration) config)
				.interpolatedConfiguration();
		return extConfig.getProperty(propertyName).toString();

	}

}