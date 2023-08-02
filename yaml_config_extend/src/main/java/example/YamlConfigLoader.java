package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class YamlConfigLoader {

	private final static boolean debug = false;

	public static YamlConfig getYamlConfig(final String propertiesFileName) {
		return getYamlConfig(propertiesFileName, null, false);
	}

	public static YamlConfig getYamlConfig(final String propertiesFileName,
			boolean fromThread) {
		return getYamlConfig(propertiesFileName, null, fromThread);
	}

	public static YamlConfig getYamlConfig(final String propertiesFileName,
			String propertiesFilePath) {
		return getYamlConfig(propertiesFileName, propertiesFilePath, false);
	}

	public static YamlConfig getYamlConfig(final String propertiesFileName,
			String propertiesFilePath, boolean fromThread) {
		final InputStream stream;
		String resourcePath = null;
		final YamlConfig yamlConfig;

		try {
			// only works when jar has been packaged?
			if (propertiesFilePath == null || propertiesFilePath.isEmpty()) {
				if (debug)
					System.err.println(
							String.format("Reading properties file \"%s\" from the jar",
									propertiesFileName));
				stream = YamlConfigLoader.class.getClassLoader()
						.getResourceAsStream(propertiesFileName);
			} else if (fromThread) {
				try {
					resourcePath = Thread.currentThread().getContextClassLoader()
							.getResource("").getPath();
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
				if (debug)
					System.err.println(String.format(
							"Reading properties file \"%s\" from the thread context",
							resourcePath + propertiesFileName));
				stream = new FileInputStream(resourcePath + propertiesFileName);

			} else {
				propertiesFilePath = String.format("%s/%s", propertiesFilePath,
						propertiesFileName);
				if (debug)
					System.err.println(String.format(
							"Reading properties file from disk: '%s'", propertiesFilePath));
				stream = new FileInputStream(propertiesFilePath);
			}
			yamlConfig = YamlConfig.load(stream);
			stream.close();
		} catch (IOException e) {
			System.err.println(String.format(
					"Properties file was not found or not readable: \"%s\". %s",
					propertiesFileName, e.toString()));
			return null;
		}
		return (yamlConfig);
	}

}
