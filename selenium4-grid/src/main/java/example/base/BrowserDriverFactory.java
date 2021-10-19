package example.base;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserDriverFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private ThreadLocal<DevTools> devTools = new ThreadLocal<>();
	private String browser;
	private Logger logger = LoggerFactory.getLogger(BrowserDriverFactory.class);
	protected static String osName = getOSName();
	private Map<String, Object> results = new HashMap<>();

	public BrowserDriverFactory(String browser, Logger logger) {
		this.browser = browser.toLowerCase();
		this.logger = logger;
	}

	public Map<String, Object> createDriver() {
		logger.info("Create local driver: " + browser);

		switch (browser) {
		case "chrome":
			// TODO: use Downloads directory for drivers
			// Make sure to upgrade chromedriver to work with your browser version:
			// https://chromedriver.chromium.org/downloads
			System.setProperty("webdriver.chrome.driver",
					osName.equals("windows")
							? (new File("c:/java/selenium/chromedriver.exe"))
									.getAbsolutePath()
							: Paths.get(System.getProperty("user.home")).resolve("Downloads")
									.resolve("chromedriver").toAbsolutePath().toString());
			System.setProperty(
					ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver.set(new ChromeDriver());
			break;

		case "firefox":
			// Make sure to upgrade geckodriver to work with your browser version:
			// https://github.com/mozilla/geckodriver/releases
			System.setProperty("webdriver.gecko.driver",
					osName.equals("windows")
							? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
							: Paths.get(System.getProperty("user.home")).resolve("Downloads")
									.resolve("geckodriver").toAbsolutePath().toString());
			System
					.setProperty("webdriver.firefox.bin",
							osName.equals("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");

			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,
					"true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
					"/dev/null");
			driver.set(new FirefoxDriver());
			break;

		default:
			logger
					.debug("Do not know how to start: " + browser + ", starting chrome.");
			System.setProperty("webdriver.chrome.driver",
					osName.equals("windows")
							? (new File("c:/java/selenium/chromedriver.exe"))
									.getAbsolutePath()
							: Paths.get(System.getProperty("user.home")).resolve("Downloads")
									.resolve("chromedriver").toAbsolutePath().toString());
			System.setProperty(
					ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver.set(new ChromeDriver());
			break;
		}
		// set additional logging in Selenium
		java.util.logging.Logger.getLogger("org.openqa.selenium")
				.setLevel(Level.SEVERE);
		logger.info("Packing driver and chromeDevTools");
		try {
			DevTools chromeDevTools = ((HasDevTools) driver.get()).getDevTools();
			devTools.set(chromeDevTools);
			results.put("devtools", devTools.get());
		} catch (DevToolsException e) {
			logger.info("Exception: " + e.toString());
			results.put("devtools", null);
		}
		results.put("driver", driver.get());
		return results;
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
