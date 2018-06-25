package com.github.abhishek8908.util;

import com.github.abhishek8908.driver.logger.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;

import org.apache.maven.plugin.logging.Log;
// TODO: get rid of
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DriverUtil extends Logger {

	private static boolean useEmbeddedResource = true;
	private static boolean renameDriver = true;

	public static void setUseEmbeddedResource(boolean useEmbeddedResource) {
		DriverUtil.useEmbeddedResource = useEmbeddedResource;
	}

	static Map<String, String> downloadURLs = new HashMap<>();
	static {
		downloadURLs.put("chromedriver", "chromedriver.download.url");
		downloadURLs.put("geckodriver", "geckodriver.download.url");
		downloadURLs.put("safaridriver", "safaridriver.download.url");
		downloadURLs.put("IEdriver", "iedriver.download.url");
		downloadURLs.put("edgedriver", "edgedriver.download.url");
	}

	public static boolean isRenameDriver() {
		return renameDriver;
	}

	public static void setRenameDriver(boolean renameDriver) {
		DriverUtil.renameDriver = renameDriver;
	}

	private static Log log = new Logger().getLog();

	public DriverUtil() {
	}

	public static void download(String driverName, String targetDirectory,
			String version) throws IOException, ConfigurationException {
		String sourceURL = getSourceUrl(driverName);
		String fileName = getFileNameFromUrl(sourceURL);
		String toFile = targetDirectory + File.separator + fileName;
		FileUtils.copyURLToFile(new URL(sourceURL), new File(toFile), 10000, 10000);
		unzipFile(targetDirectory + File.separator + fileName, targetDirectory);
		// TODO: make configurable to prevent modifications to test
		if (renameDriver) {
			changeFileName(targetDirectory + File.separator + driverName,
					targetDirectory + File.separator + driverName + "-" + version);
		}
		cleanDir(targetDirectory);
	}

	public static void unzipFile(String source, String destinationPath)
			throws IOException {
		if (source.contains(".zip")) {
			Archiver archiver = ArchiverFactory.createArchiver("zip");
			archiver.extract(new File(source), new File(destinationPath));
		} else {
			Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
			archiver.extract(new File(source), new File(destinationPath));
		}
		log.info("Decompressing file: " + source);

	}

	public static void changeFileName(String fileName, String fileOut)
			throws IOException {
		String os = System.getProperty("os"); //
		String ext = (os.toLowerCase().contains("win")) ? ".exe" : "";

		FileUtils.moveFile(FileUtils.getFile(fileName + ext),
				FileUtils.getFile(fileOut + "-" + os + ext));
		log.info("File: " + fileName + " renameded to " + fileOut);

	}

	public static String getFileNameFromUrl(String url) {
		String[] newUrl = url.split("/");
		log.info("File Name: " + newUrl[newUrl.length - 1]);
		return newUrl[newUrl.length - 1];

	}

	// currently plugin extracts os and version of the driver from the filename
	// alternatively one can make a soft link to the same
	public static boolean checkDriverVersionExists(String driverName,
			String version, String dir) throws IOException {
		boolean fileExists = false;
		String os = System.getProperty("os");

		final DirectoryStream<Path> stream = Files.newDirectoryStream(
				Paths.get(dir), driverName + "-" + version + "-" + os + "{,.*}");
		for (final Path entry : stream) {
			if (!entry.toString().isEmpty()) {
				fileExists = true;
			}
		}
		return fileExists;
	}

	public static void cleanDir(String dir) {
		File folder = new File(dir);
		Arrays.stream(folder.listFiles((f, p) -> p.endsWith(".zip")))
				.forEach(File::delete);
		Arrays.stream(folder.listFiles((f, p) -> p.endsWith(".tar.gz")))
				.forEach(File::delete);
		log.info("***** Cleaning Dir:" + dir);
	}

	private static Map<String, String> properties = new HashMap<>();

	public static String readProperty(String propertyName)
			throws ConfigurationException {
		if (properties.isEmpty()) {
			properties = PropertiesParser.getProperties("driver.properties",
					useEmbeddedResource);
		}
		return properties.get(propertyName);
	}

	public static String getSourceUrl(String browserDriver)
			throws ConfigurationException {
		return readProperty(downloadURLs.get(browserDriver));
	}

	public static void checkDriverDirExists(String dirName) throws IOException {

		File directory = new File(dirName);
		if (!directory.exists()) {
			log.info("Driver directory does not exists - creating.");
			FileUtils.forceMkdir(directory);
		}

	}

}
