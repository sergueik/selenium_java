package example.base;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserDriverFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private Logger logger = LoggerFactory.getLogger(BrowserDriverFactory.class);

	public BrowserDriverFactory(String browser, Logger logger) {
		this.browser = browser.toLowerCase();
		this.logger = logger;
	}

	public WebDriver createDriver() {
		logger.info("Create local driver: " + browser);

		switch (browser) {
		case "chrome":
			// TODO: use Downloads directory for drivers
			// Make sure to upgrade chromedriver to work with your browser version:
			// https://chromedriver.chromium.org/downloads
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver.set(new ChromeDriver());
			break;

		case "firefox":
			// Make sure to upgrade geckodriver to work with your browser version:
			// https://github.com/mozilla/geckodriver/releases
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
			driver.set(new FirefoxDriver());
			break;

		default:
			logger.debug("Do not know how to start: " + browser + ", starting chrome.");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver.set(new ChromeDriver());
			break;
		}
		// set additional logging in Selenium
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver.get();
	}
}
