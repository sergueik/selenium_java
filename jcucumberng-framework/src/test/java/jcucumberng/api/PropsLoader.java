package jcucumberng.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class handles actions for configuring the test framework.
 * 
 * @author Kat Rollo (rollo.katherine@gmail.com)
 */
public final class PropsLoader {

	private PropsLoader() {
		// Prevent instantiation
	}

	/**
	 * Reads a config.properties file by passing the key of a configuration or
	 * setting. The file must be located in /src/test/resources/.
	 * 
	 * @param key the name corresponding to the value in the key-value pair
	 * @return String - the value corresponding to the given key
	 * @throws IOException
	 */
	public static String readConfig(String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/config.properties");

		InputStream inStream = new FileInputStream(builder.toString());
		Properties properties = new Properties();
		properties.load(inStream);

		return properties.getProperty(key).trim();
	}

	/**
	 * Reads a ui-map.properties file by passing the key of a locator used to find
	 * elements in web pages. The file must be located in /src/test/resources/.
	 * 
	 * @param key the name corresponding to the value in the key-value pair
	 * @return String - the value corresponding to the given key
	 * @throws IOException
	 */
	public static String readLocator(String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/ui-map.properties");

		InputStream inStream = new FileInputStream(builder.toString());
		Properties properties = new Properties();
		properties.load(inStream);

		return properties.getProperty(key).trim();
	}

}
