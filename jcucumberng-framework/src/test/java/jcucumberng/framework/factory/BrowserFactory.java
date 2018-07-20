package jcucumberng.framework.factory;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import jcucumberng.framework.enums.Browser;
import jcucumberng.framework.exceptions.UnsupportedBrowserException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code BrowserFactory} handles actions for instantiating or terminating the
 * Selenium WebDriver.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class BrowserFactory {

	private BrowserFactory() {
		// Prevent instantiation
	}

	/**
	 * Gets the Selenium WebDriver instance.
	 * 
	 * @param browserConfig the {@code browser} specified in
	 *                      {@code framework.properties}
	 * @return WebDriver - the Selenium WebDriver
	 */
	public static WebDriver getBrowser(String browserConfig) {
		WebDriver driver = null;

		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/framework/drivers/");
		String driverPath = builder.toString().trim();

		try {
			Browser browser = Browser.valueOf(browserConfig.toUpperCase());
			switch (browser) {
			case CHROME32:
				System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
				driver = new ChromeDriver();
				break;
			case CHROME32_NOHEAD:
				System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
				ChromeOptions chromeOpts = new ChromeOptions();
				chromeOpts.addArguments("--headless");
				driver = new ChromeDriver(chromeOpts);
				break;
			case FF32:
				System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
				driver = new FirefoxDriver();
				break;
			case FF32_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(driverPath, "geckodriver_win32.exe");
				break;
			case FF64:
				System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
				driver = new FirefoxDriver();
				break;
			case FF64_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(driverPath, "geckodriver_win64.exe");
				break;
			case EDGE:
				System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
				break;
			case IE32:
				System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win32.exe");
				driver = new InternetExplorerDriver();
				break;
			case IE64:
				System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win64.exe");
				driver = new InternetExplorerDriver();
				break;
			default:
				// Handled in try-catch
				break;
			}
		} catch (IllegalArgumentException iae) {
			if (StringUtils.isBlank(browserConfig)) {
				browserConfig = "BLANK";
			}
			throw new UnsupportedBrowserException(Messages.UNSUPPORTED_BROWSER + browserConfig);
		}

		return driver;
	}

	/**
	 * Quits all instances of the driver (close all windows).
	 * 
	 * @param driver the Selenium WebDriver
	 */
	public static void quitBrowser(WebDriver driver) {
		driver.quit();
	}

	private static WebDriver initFirefoxNoHead(String driverPath, String driverBinary) {
		WebDriver driver = null;
		System.setProperty("webdriver.gecko.driver", driverPath + driverBinary);
		FirefoxBinary ffBin = new FirefoxBinary();
		ffBin.addCommandLineOptions("--headless");
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setBinary(ffBin);
		ffOpts.setLogLevel(FirefoxDriverLogLevel.WARN);
		driver = new FirefoxDriver(ffOpts);
		return driver;
	}

}
