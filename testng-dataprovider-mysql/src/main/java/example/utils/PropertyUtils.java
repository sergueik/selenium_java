package example.utils;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import java.util.Arrays;

public final class PropertyUtils {

	private static final CompositeConfiguration MIXED_CONFIG;

	static {
		try {
			MIXED_CONFIG = new CompositeConfiguration(Arrays.asList(new SystemConfiguration(),
					new PropertiesConfiguration("config.properties")));
		} catch (Exception ex) {
			throw new IllegalArgumentException("Can't load properties", ex);
		}
	}

	public static final class Constants {
		public static final int MIN_POOL_SIZE = getIntValue("min.pool.size.arg");
		public static final int MAX_POOL_SIZE = getIntValue("max.pool.size.arg");
		public static final boolean IS_POOL_FAIR = getBooleanValue("is.pool.fair.arg");
		public static final String DEV_URL = getStringValue("dev.server.url.arg");
		public static final String DATA_SOURCE_CONFIG = getStringValue("data.source.config.arg");

		private Constants() {
			throw new UnsupportedOperationException("Illegal access to private constructor");
		}
	}

	public static String getStringValue(final String key) {
		return MIXED_CONFIG.getString(key);
	}

	public static int getIntValue(final String key) {
		return MIXED_CONFIG.getInt(key);
	}

	public static boolean getBooleanValue(final String key) {
		return MIXED_CONFIG.getBoolean(key);
	}

	private PropertyUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
