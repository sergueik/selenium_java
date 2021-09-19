package example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	private Logger logger = LogManager.getLogger(Utils.class.getName());

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

	public Map<String, String> getProperties(final String fileName) {
		Properties p = new Properties();
		final InputStream stream;
		Map<String, String> propertiesMap = new HashMap<>();
		if (debug) {
			System.err.println(String.format("Reading properties file: '%s'", fileName));
		}
		stream = Utils.class.getClassLoader().getResourceAsStream(fileName);

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
				propertiesMap.put(key, resolveEnvVars(val));
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("Properties file was not found: '%s'", fileName));
		} catch (IOException e) {
			throw new RuntimeException(String.format("Properties file is not readable: '%s'", fileName));
		}
		return (propertiesMap);
	}

	public String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{([\\.\\w]+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = getPropertyEnv(envVarName, "");
			m.appendReplacement(sb, null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public String getPropertyEnv(String name, String defaultValue) {
		String value = null;
		if (debug)
			logger.info("getting property: " + name);
		value = System.getProperty(name);
		if (debug)
			logger.info("system property: " + value);
		if (value == null) {
			Map<String, String> propertiesMap = getProperties("application.properties");
			value = propertiesMap.get(name);

			logger.info("application.properties property: " + value);
			if (value == null) {
				value = System.getenv(name);
				if (debug)
					logger.info("environment property: " + value);
				if (value == null) {
					value = defaultValue;
				}
			}
		}
		return value;
	}
}
