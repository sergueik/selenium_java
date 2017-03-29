package com.janprach.multiplatformswt.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiPlatformSwtHelper {
	private final static Logger LOGGER = Logger
			.getLogger(MultiPlatformSwtHelper.class.getName());

	private static final String MULTIPLATFORM_SWT_PROPERTIES = "META-INF/multiplatform-swt.properties";
	private static final String SWT_JAR_FILE_NAME_PREFIX = "org.eclipse.swt";
	private static final String SWT_JAR_FILE_NAME_PATTERN = ".*"
			+ SWT_JAR_FILE_NAME_PREFIX + ".*.jar";
	private static final String SWT_PLATFORM_DEPENDENT_JAR_PATH = getSwtPlatformDependentJarPath();

	public static boolean isCorrectPlatformSwtJarOrTrue(
			final String classPathEntry) {
		if (classPathEntry.matches(SWT_JAR_FILE_NAME_PATTERN)) {
			if (SWT_PLATFORM_DEPENDENT_JAR_PATH == null) {
				LOGGER.severe("Unknown correct SWT jar file name for this platform!");
				return false;
			} else {
				final boolean isCorrectPlatformSwtJar = classPathEntry
						.equals(SWT_PLATFORM_DEPENDENT_JAR_PATH);
				final String isCorrectPlatformSwtJArMessage = isCorrectPlatformSwtJar
						? "INCLUDED" : "EXCLUDED";
				LOGGER.fine("SWT jar file '" + classPathEntry + "': "
						+ isCorrectPlatformSwtJArMessage);
				return isCorrectPlatformSwtJar;
			}
		} else {
			return true;
		}
	}

	public static String getSwtPlatformDependentJarPath() {
		try {
			final String platformString = getSwtFileNameOsSuffix()
					+ getSwtFileArchSuffix();
			return getLibDirectory() + "/" + SWT_JAR_FILE_NAME_PREFIX + platformString
					+ getSwtVersionSuffix() + ".jar";
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,
					"Unable to determine platform dependent SWT jar file name.", e);
			return null;
		}
	}

	public static URL getSwtPlatformDependentJarFileUrl()
			throws MalformedURLException {
		return new URL("file:" + getSwtPlatformDependentJarPath());
	}

	public static String getSwtFileArchSuffix() {
		final String osName = System.getProperty("os.name").toLowerCase();
		final String osArch = System.getProperty("os.arch").toLowerCase();
		final String swtFileNameArchSuffix;
		if (osArch.contains("64")) {
			swtFileNameArchSuffix = ".x86_64";
		} else {
			swtFileNameArchSuffix = osName.contains("mac") ? "" : ".x86";
		}
		return swtFileNameArchSuffix;
	}

	public static String getSwtFileNameOsSuffix() {
		final String osName = System.getProperty("os.name").toLowerCase();
		final String swtFileNameOsPart;
		if (osName.contains("win")) {
			swtFileNameOsPart = ".win32.win32";
		} else if (osName.contains("mac")) {
			swtFileNameOsPart = ".cocoa.macosx";
		} else if (osName.contains("linux") || osName.contains("nix")) {
			swtFileNameOsPart = ".gtk.linux";
		} else {
			throw new RuntimeException("Unsupported OS name: " + osName);
		}
		return swtFileNameOsPart;
	}

	private static String getLibDirectory() {
		return extractValueFromMultiplatformSwtProperties("lib.directory");
	}

	public static String getSwtVersionSuffix() {
		final String swtVersion = extractValueFromMultiplatformSwtProperties(
				"swt.version");
		return (swtVersion != null) ? "-" + swtVersion : "";
	}

	private static String extractValueFromMultiplatformSwtProperties(
			final String key) {
		final ClassLoader classLoader = MultiPlatformSwtHelper.class
				.getClassLoader();
		final InputStream inputStream = classLoader
				.getResourceAsStream(MULTIPLATFORM_SWT_PROPERTIES);
		final Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (final IOException e) {
			LOGGER.log(Level.WARNING,
					"Unable to read " + MULTIPLATFORM_SWT_PROPERTIES, e);
		}
		return properties.getProperty(key);
	}
}
