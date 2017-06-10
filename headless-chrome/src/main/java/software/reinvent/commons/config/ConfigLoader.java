package software.reinvent.commons.config;

import com.google.common.base.Charsets;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The convenience method ConfigLoader.load() loads the following (first-listed are higher priority):
 * <ol type="1">
 * <li>system properties</li>
 * <li>file path from system property "provided.config"</li>
 * <li>{username}.conf in classpath resources</li>
 * <li>application.conf (all resources on classpath with this name)</li>
 * <li>application.json (all resources on classpath with this name)</li>
 * <li>application.properties (all resources on classpath with this name)</li>
 * <li>reference.conf (all resources on classpath with this name)</li>
 * </ol>
 */
public class ConfigLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

	public static Config load() {
		Config config = ConfigFactory.parseProperties(System.getProperties());
		config = withProvidedValues(config);
		config = withUserValues(config);
		return config.withFallback(ConfigFactory.load()).resolve();
	}

	public static CachedConfig loadCached() {
		Config config = ConfigFactory.parseProperties(System.getProperties());
		config = withProvidedValues(config);
		config = withUserValues(config);
		return new CachedConfig(
				config.withFallback(ConfigFactory.load()).resolve());
	}

	private static Config withProvidedValues(final Config config) {
		final String providedConfig = System.getProperty("provided.config");
		if (isNotBlank(providedConfig)) {
			return config
					.withFallback(ConfigFactory.parseFile(new File(providedConfig)));
		}
		return config;
	}

	private static Config withUserValues(final Config config) {
		try {
			final String userConfigFile = defaultString(
					System.getProperty("user.name")) + ".conf";
			final String pathToUserConfig = "/" + userConfigFile;
			if (isNotBlank(userConfigFile)
					&& ConfigLoader.class.getResource(pathToUserConfig) != null) {
				final String confString = IOUtils.toString(
						ConfigLoader.class.getResourceAsStream(pathToUserConfig),
						Charsets.UTF_8);
				final Config parsedConfig = ConfigFactory.parseString(confString);
				return config.withFallback(parsedConfig);
			}

		} catch (Exception e) {
			LOG.error("Could not load user config.", e);
		}
		return config;
	}
}
