package com.github.abhishek8908.util;

import static javax.xml.xpath.XPathFactory.newInstance;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.maven.plugin.logging.Log;

// TODO: get rid of
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.github.abhishek8908.driver.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

@SuppressWarnings("deprecation")
public class DriverUtil extends Logger {

	private static boolean useEmbeddedResource = true;
	private static boolean renameDriver = true;

	// based on:
	// https://github.com/bonigarcia/webdrivermanager/blob/master/src/main/java/io/github/bonigarcia/wdm/WebDriverManager.java
	private static List<URL> getDriversFromJSON(String releaseUrl) {
		DefaultHttpClient client = new DefaultHttpClient();
		List<URL> urls = new ArrayList<>();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getMethod = new HttpGet(releaseUrl);
		GeckoDriverReleases[] releaseArray = null;
		try {
			String responseBody = client.execute(getMethod, responseHandler);
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			releaseArray = gson.fromJson(new StringReader(responseBody),
					GeckoDriverReleases[].class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for (GeckoDriverReleases release : releaseArray) {
			if (release != null) {
				List<LinkedTreeMap<String, Object>> assets = release.getAssets();
				Pattern pattern = Pattern.compile(
						"(?:" + System.getProperty("os") + ")", Pattern.CASE_INSENSITIVE);
				for (LinkedTreeMap<String, Object> asset : assets) {
					try {
						String path = asset.get("browser_download_url").toString();
						Matcher matcher = pattern.matcher(path);
						if (matcher.find()) {
							log.info("Found gecko driver: " + path);
							urls.add(new URL(path));
						}
					} catch (MalformedURLException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return urls;
	}

	// based on:
	// https://github.com/bonigarcia/webdrivermanager/blob/master/src/main/java/io/github/bonigarcia/wdm/WebDriverManager.java
	private static List<URL> getDriversFromXml(String releaseUrl)
			throws IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getMethod = new HttpGet(releaseUrl);
		String responseBody = client.execute(getMethod, responseHandler);
		List<URL> urls = new ArrayList<>();
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseBody));

			Document doc = db.parse(is);

			Element documentElement = doc.getDocumentElement();
			NodeList nodes = (org.w3c.dom.NodeList) newInstance().newXPath().evaluate(
					String.format("//Contents/Key[contains(text(), '%s')]",
							"chromedriver" /* readProperty(driverNames.get(browserDriver));  */),
					documentElement, javax.xml.xpath.XPathConstants.NODESET);

			// TODO: OS filtering does not apply to IE and Edge driver
			Pattern pattern = Pattern.compile("(?:" + System.getProperty("os") + ")",
					Pattern.CASE_INSENSITIVE);

			for (int i = 0; i < nodes.getLength(); ++i) {
				Element e = (Element) nodes.item(i);
				String path = e.getChildNodes().item(0).getNodeValue();
				Matcher matcher = pattern.matcher(path);
				if (matcher.find()) {
					log.info("Found chrome driver: " + path);
					urls.add(new URL(releaseUrl + path));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return urls;
	}

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

	static Map<String, String> driverNames = new HashMap<>();
	static {
		downloadURLs.put("chromedriver", "chromedriver.name");
		downloadURLs.put("geckodriver", "geckodriver.name");
		downloadURLs.put("safaridriver", "safaridriver.name");
		downloadURLs.put("IEdriver", "iedriver.name");
		downloadURLs.put("edgedriver", "edgedriver.name");
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
		List<URL> driverUrls = getDriversFromXml(
				"https://chromedriver.storage.googleapis.com/");
		driverUrls = getDriversFromJSON(
				"https://api.github.com/repos/mozilla/geckodriver/releases");

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

	public static String getFileNameFromUrl(String location) {
		String fileName = null;
		try {
			URL url = new URL(location);
			fileName = url.getFile();
		} catch (MalformedURLException e) {
			e.printStackTrace();

		}
		return fileName;
		/*
		String[] newUrl = location.split("/");
		log.info("File Name: " + newUrl[newUrl.length - 1]);
		return newUrl[newUrl.length - 1];
		*/
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
		log.info("Clean Dir:" + dir);
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

	public static class GeckoDriverReleases {

		@SerializedName("tag_name")
		private String tagName;

		private String name;
		private List<LinkedTreeMap<String, Object>> assets;

		public String getTagName() {
			return tagName;
		}

		public String getName() {
			return name;
		}

		public List<LinkedTreeMap<String, Object>> getAssets() {
			return assets;
		}

	}

}
