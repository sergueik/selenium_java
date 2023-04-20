package example.test;
/**
 * Copyright 2022 Serguei Kouzmine
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	private static Utils instance = new Utils();

	private boolean debug = false;

	public void setDebug(boolean value) {
		debug = value;
	}

	private Utils() {
	}

	public static Utils getInstance() {
		return instance;
	}

	private Map<String, String> propertiesMap;

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	// NOTE: put inside "WEB-INF/classes" for web hosted app
	public String getScriptContent(String resourceFileName) {
		try {
			if (debug) {
				System.err
						.println("Script contents: " + getResourceURI(resourceFileName));
			}
			final InputStream stream = getResourceStream(resourceFileName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// NOTE: getResourceURI may not work with standalone or web hosted
	// application
	public String getResourceURI(String resourceFileName) {
		try {
			if (debug) {
				System.err.println("Getting resource URI for: " + resourceFileName);
			}

			URI uri = this.getClass().getClassLoader().getResource(resourceFileName)
					.toURI();
			if (debug) {
				System.err.println("Resource URI: " + uri.toString());
			}
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public InputStream getResourceStream(String resourceFilePath) {
		return this.getClass().getClassLoader()
				.getResourceAsStream(resourceFilePath);
	}

	public String getResourcePath(String resourceFileName) {
		final String resourcePath = String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
		if (debug) {
			System.err.println("Project based resource path: " + resourcePath);
		}
		return resourcePath;
	}

	// TODO: fix with absolute path in filename
	public Map<String, String> getProperties(final String fileName) {
		Properties p = new Properties();
		final InputStream stream;
		propertiesMap = new HashMap<>();
		if (debug) {
			System.err
					.println(String.format("Reading properties file: '%s'", fileName));
		}
		if (new File(fileName).exists()) {
			try {
				stream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				return (propertiesMap);
			}
		} else {
			String resourcefileName = fileName.replaceAll("^.*/", "");
			if (debug) {
				System.err.println(String.format(
						"Reading properties resource stream: '%s'", resourcefileName));
			}
			stream = getResourceStream(resourcefileName);
		}
		if (stream != null) {
			try {
				p.load(stream);
				@SuppressWarnings("unchecked")
				Enumeration<String> e = (Enumeration<String>) p.propertyNames();
				for (; e.hasMoreElements();) {
					String key = e.nextElement();
					String val = p.get(key).toString();
					if (debug) {
						System.err.println(String.format("Reading: '%s' = '%s'", key, val));
					}
					// NOTE: calling resolveEnvVars is too early
					propertiesMap.put(key, val);
				}

			} catch (FileNotFoundException e) {
				throw new RuntimeException(
						String.format("Properties file was not found: '%s'", fileName));
			} catch (IOException e) {
				throw new RuntimeException(
						String.format("Properties file is not readable: '%s'", fileName));
			}
		}
		if (debug) {

			for (Entry<String, String> propertyEntry : propertiesMap.entrySet()) {
				System.err.println(String.format("Read: '%s' = '%s'",
						propertyEntry.getKey(), propertyEntry.getValue()));
			}
		}
		return (propertiesMap);
	}

	public String resolveEnvVars(String input) {
		return resolveEnvVars(input, "");
	}

	public String resolveEnvVars(String input, String defaultValue) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{([\\.\\w]+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = getPropertyEnv(envVarName, defaultValue);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public String getPropertyEnv(String name, String defaultValue) {
		String value = null;
		if (debug)
			System.err.println("getting property: " + name);
		value = propertiesMap.get(name);
		// TODO: check if one even needs it
		// value = System.getProperty(name);
		if (debug)
			System.err.println("Configuration file property: " + value);
		if (value == null) {
			value = System.getenv(name);
			if (debug)
				System.err.println("environment property: " + value);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}
}
