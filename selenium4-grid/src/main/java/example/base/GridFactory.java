package example.base;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private ThreadLocal<DevTools> devTools = new ThreadLocal<>();
	private String browser;
	private String platform;
	private Logger logger = LoggerFactory.getLogger(GridFactory.class);

	public GridFactory(String browser, String platform, Logger logger) {
		this.browser = browser.toLowerCase();
		this.platform = platform;
		this.logger = logger;
	}

	public WebDriver createDriver() {
		logger.info("Connecting to the node with: : " + browser);

		DesiredCapabilities capabilities = new DesiredCapabilities();

		switch (browser) {
		case "chrome":
			capabilities.setBrowserName("chrome");
			break;

		case "firefox":
			capabilities.setBrowserName("firefox");
			break;

		default:
			capabilities.setBrowserName("chrome");
			break;
		}

		switch (platform) {
		case "WIN10":
			capabilities.setPlatform(Platform.WIN10);
			break;

		case "MAC":
			capabilities.setPlatform(Platform.MAC);
			break;

		case "LINUX":
			capabilities.setPlatform(Platform.LINUX);
			break;

		default:
			capabilities.setPlatform(Platform.LINUX);
			break;
		}

		try {
			URL url = new URL("http://localhost:4444");
			WebDriver webDriver = new RemoteWebDriver(url, capabilities);
			webDriver = new Augmenter().augment(webDriver);
			DevTools chromeDevTools = ((HasDevTools) webDriver).getDevTools();

			driver.set(webDriver);
			devTools.set(chromeDevTools);
			// set additional logging in Selenium
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
			return driver.get();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
