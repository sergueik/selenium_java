package example.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	private Utils utils;
	private boolean debug = false;

	private String osName = getOSName();

	private static ThreadLocal<Utils> instance = new ThreadLocal<Utils>();

	public void setDebug(boolean value) {
		debug = value;
	}

	public static Utils getInstance() {
		if (instance.get() == null) {
			instance.set(new Utils());
		}
		return instance.get();
	}

	private Properties prop;

	public Properties getProperties() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = JDBCUtils.class.getClassLoader()
					.getResourceAsStream("application.properties");
			prop.load(input);
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
		return prop;
	}

	public String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{([\\w.]+)\\}|([\\w.]+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			// System.err.println("Replacing " + envVarName);
			String envVarValue = prop.containsKey(envVarName)
					? prop.get(envVarName).toString() : System.getenv(envVarName);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
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
