package jcucumberng.framework.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import jcucumberng.framework.exceptions.LoggerConfigException;
import jcucumberng.framework.exceptions.NoSuchKeyException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code ConfigLoader} handles actions for reading/loading various
 * configuration and properties files.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class ConfigLoader {

	private ConfigLoader() {
		// Prevent instantiation
	}

	/**
	 * Reads framework config from {@code framework.properties}.
	 * 
	 * @param key the config key (Example: {@code browser=CHROME32}, key =
	 *            {@code browser})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code browser=CHROME32}, value = {@code CHROME32})
	 * @throws IOException
	 */
	public static String frameworkConf(String key) throws IOException {
		String propsFileName = "framework.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/framework/");
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY + builder.toString());
		}

		return value.trim();
	}

	/**
	 * Reads project config from {@code project.properties}.
	 * 
	 * @param key the config key (Example: {@code base.url=www.google.com}, key =
	 *            {@code base.url})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code base.url=www.google.com}, value = {@code www.google.com})
	 * @throws IOException
	 */
	public static String projectConf(String key) throws IOException {
		String propsFileName = "project.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/project/");
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY + builder.toString());
		}

		return value.trim();
	}

	/**
	 * Loads {@code log4j2.conf.file} from {@code framework.properties}.
	 */
	public static void loggerConf() {
		String cfgFile = null;
		try {
			cfgFile = ConfigLoader.frameworkConf("log4j2.conf.file");
		} catch (IOException ioe) {
			throw new LoggerConfigException(Messages.LOGGER_CONFIG_FAIL + cfgFile);
		}

		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/framework/");
		builder.append(cfgFile);

		File log4j2File = new File(builder.toString());
		System.setProperty("log4j2.configurationFile", log4j2File.toURI().toString());
	}

	/**
	 * Reads web elements from {@code ui-map.properties}.
	 * 
	 * @param key the element key (Example: {@code first.name.txt=id:firstName}, key
	 *            = {@code first.name.txt})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code first.name.txt=id:firstName}, value = {@code id:firstName})
	 * @throws IOException
	 */
	public static String uiMap(String key) throws IOException {
		String propsFileName = "ui-map.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/project/");
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY + builder.toString());
		}

		return value.trim();
	}

}
