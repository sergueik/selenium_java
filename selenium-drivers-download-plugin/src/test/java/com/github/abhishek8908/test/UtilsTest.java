package com.github.abhishek8908.test;

import com.github.abhishek8908.driver.ChromeDriver;
import com.github.abhishek8908.driver.DriverSettings;
import com.github.abhishek8908.driver.EdgeDriver;
import com.github.abhishek8908.driver.GeckoDriver;
import com.github.abhishek8908.plugin.Driver;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.github.abhishek8908.util.DriverUtil.*;

public class UtilsTest {

	@Ignore
	@Test
	public static void newDownload() throws IOException {

		String fromFile = "https://github.com/mozilla/geckodriver/releases/download/v0.20.1/geckodriver-v0.20.1-win32.zip";
		String toFile = "c:/temp/geckodriver-v0.20.1-win32.zip";
		// connectionTimeout, readTimeout = 10 seconds
		FileUtils.copyURLToFile(new URL(fromFile), new File(toFile), 10000, 10000);
	}

	@Ignore
	@Test
	public void testSystemProperty() throws ConfigurationException {
		System.setProperty("ver", "2.39");
		System.setProperty("os", "linux64");
		System.setProperty("ext", "zip");
		System.out.println(DriverUtil.readProperty("chromedriver.download.url"));
	}

	@Ignore
	@Test
	public void urlTest() {
		getFileNameFromUrl(
				"https://chromedriver.storage.googleapis.com/2.39/chromedriver_win32.zip");
		getFileNameFromUrl(
				"https://download.microsoft.com/download/C/0/7/C07EBF21-5305-4EC8-83B1-A6FCC8F93F45/MicrosoftWebDriver.exe");
	}

	@Ignore
	@Test
	public void fileRename() throws IOException {
		changeFileName("c:\\temp\\chromedriver.exe",
				"c:\\temp\\chromedriver-" + "2.38" + ".exe");
	}

	@Test
	public void fileLink() throws IOException {
		changeFileName("c:\\temp\\geckodriver",
				"c:\\temp\\geckodriver-" + "0.21.0" + "linux64.lnk", true);
	}

	@Ignore
	@Test
	public void testCleanDir() {
		cleanDir("c:\\temp");
	}

	@Ignore
	@Test
	public void testDriverExists() throws IOException {
		System.out
				.println(checkDriverVersionExists("chromedriver", "2.38", "c:/temp"));
	}

	@Ignore
	@Test
	public void testProperty() throws ConfigurationException {
		System.out.println(readProperty("chrome.download.url"));
		System.getProperty("os.name");
	}

	@Test(enabled = false)
	public void testChromeDriverConstructor() {
		DriverSettings settings = new DriverSettings();

		settings.setVer("2.39");
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new ChromeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = false)
	public void testChromeDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();

		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new ChromeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = true)
	public void testGeckoDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();
		System.clearProperty("ver");
		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new GeckoDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = false)
	public void testEdgeDriverConstructorAction() {
		DriverSettings settings = new DriverSettings();

		settings.setVer(null);
		settings.setOs("win32");
		settings.setDriverDir("c:/temp");
		try {
			new EdgeDriver(settings).getDriver().setDriverInSystemProperty();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}
}
