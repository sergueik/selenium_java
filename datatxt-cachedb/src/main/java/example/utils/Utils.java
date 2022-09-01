package example.utils;

/**
 * Copyright 2022 Serguei Kouzmine
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	private Utils utils;
	private boolean debug = false;

	private String osName = getOSName();

	private static ThreadLocal<Utils> instance = new ThreadLocal<Utils>();

	private Map<String, String> propertiesMapCache;

	public Map<String, String> getPropertiesMapCache() {
		return propertiesMapCache;
	}

	public void setDebug(boolean value) {
		debug = value;
	}

	public static Utils getInstance() {
		if (instance.get() == null) {
			instance.set(new Utils());
		}
		return instance.get();
	}

	private Properties propertiesObject;

	public Properties getPropertiesObject() {
		propertiesObject = new Properties();
		InputStream input = null;
		try {
			input = this.getClass().getClassLoader()
					.getResourceAsStream("application.properties");
			propertiesObject.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return propertiesObject;
	}

	// TODO: fix with absolute path in filename
	public Map<String, String> getProperties(final String fileName) {
		Properties p = new Properties();
		final InputStream stream;
		propertiesMapCache = new HashMap<>();
		if (debug) {
			System.err
					.println(String.format("Reading properties file: '%s'", fileName));
		}
		if (new File(fileName).exists()) {
			try {
				stream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				return (propertiesMapCache);
			}
		} else {
			String resourcefileName = fileName.replaceAll("^.*/", "");
			if (debug) {
				System.err.println(String.format(
						"Reading properties resource stream: '%s'", resourcefileName));
			}
			stream = Utils.class.getClassLoader()
					.getResourceAsStream(resourcefileName);
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
					propertiesMapCache.put(key, val);
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

			for (Entry<String, String> propertyEntry : propertiesMapCache
					.entrySet()) {
				System.err.println(String.format("Read: '%s' = '%s'",
						propertyEntry.getKey(), propertyEntry.getValue()));
			}
		}
		return (propertiesMapCache);
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
		value = propertiesMapCache.get(name);
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

	public String getScriptContent(String scriptName) {
		try {
			final InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	public String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public void waitFor(long interval) {
		try {
			TimeUnit.SECONDS.sleep(interval);
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sleep(long interval) {
		waitFor(interval);
	}

}
