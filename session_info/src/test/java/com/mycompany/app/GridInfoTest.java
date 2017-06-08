
package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mycompany.app.ActiveNodeDeterminer;

import java.net.URL;

public class GridInfoTest {
	private static final String IP = "localhost";
	private static final int PORT = 4444;

	private RemoteWebDriver driver;
	private ActiveNodeDeterminer determiner = new ActiveNodeDeterminer();
	private static String osName;

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	@BeforeClass
	public void setup() throws Exception {
		determiner = new ActiveNodeDeterminer();
		determiner.setGridPort(PORT);
		determiner.setGridHostName(IP);

		URL url = new URL("http://" + IP + ":" + PORT + "/wd/hub");
		getOsName();
		System.setProperty("webdriver.gecko.driver",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
						: "/tmp/geckodriver");
		System.setProperty("webdriver.firefox.bin",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		// use legacy FirefoxDriver
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", false);
		driver = new RemoteWebDriver(url, capabilities);
	}

	@Test
	public void test() {
		Reporter.log(
				"Node : " + determiner.getNodeInfoForSession(driver.getSessionId()),
				true);
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
