package example;

import static java.lang.System.err;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/*
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

*/

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	private static Map<String, String> env = System.getenv();
	private static boolean isCIBuild = checkEnvironment();

	protected static final boolean debug = Boolean
			.parseBoolean(getPropertyEnv("DEBUG", "false"));

	protected static WebDriver driver = null;
	protected static ShadowDriver shadowDriver = null;
	private static String browser = getPropertyEnv("BROWSER",
			getPropertyEnv("webdriver.driver", "chrome"));

	// export BROWSER=firefox or
	// use -Pfirefox to override
	public static String getBrowser() {
		return browser;
	}

	private static final boolean headless = Boolean
			.parseBoolean(getPropertyEnv("HEADLESS", "false"));

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void injectShadowJS() {
		err.println("Launching " + browser);
		if (isCIBuild) {
			if (browser.equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} // TODO: finish for other browser
			if (browser.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} // TODO: finish for other browser

		} else {
			String osName = getOSName();
			final Map<String, String> browserDrivers = new HashMap<>();
			browserDrivers.put("chrome",
					osName.equals("windows") ? "chromedriver.exe" : "chromedriver");
			browserDrivers.put("firefox",
					osName.equals("windows") ? "geckodriver.exe" : "geckodriver");
			browserDrivers.put("edge", "MicrosoftWebDriver.exe");
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						Paths.get(System.getProperty("user.home")).resolve("Downloads")
								.resolve(browserDrivers.get("chrome")).toAbsolutePath()
								.toString());

				// https://peter.sh/experiments/chromium-command-line-switches/
				ChromeOptions options = new ChromeOptions();
				// options for headless
				if (headless) {
					for (String arg : (new String[] { "headless",
							"window-size=1200x800" })) {
						options.addArguments(arg);
					}
				}

				driver = new ChromeDriver(options);
			}
			if (browser.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						Paths.get(System.getProperty("user.home")).resolve("Downloads")
								.resolve(browserDrivers.get("firefox")).toAbsolutePath()
								.toString());
				// NOTE: there are both 32 and 64 bit firefox
				System
						.setProperty("webdriver.firefox.bin",
								osName.equals("windows") ? new File(
										"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
												.getAbsolutePath()
										: "/usr/bin/firefox");
				// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				// use legacy FirefoxDriver
				// for Firefox v.59 no longer possible ?
				// org.openqa.selenium.WebDriverException: Missing 'marionetteProtocol'
				// field in handshake
				// org.openqa.selenium.WebDriverException: Timed out waiting 45 seconds
				// for Firefox to start.

				capabilities.setCapability("marionette", true);
				// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
				capabilities.setCapability("locationContextEnabled", false);
				capabilities.setCapability("acceptSslCerts", true);
				capabilities.setCapability("elementScrollBehavior", 1);
				FirefoxProfile profile = new FirefoxProfile();
				// NOTE: the setting below may be too restrictive
				// http://kb.mozillazine.org/Network.cookie.cookieBehavior
				// profile.setPreference("network.cookie.cookieBehavior", 2);
				// no cookies are allowed
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/octet-stream,text/csv");
				profile.setPreference("browser.helperApps.neverAsk.openFile",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				// TODO: cannot find symbol: method
				// addPreference(java.lang.String,java.lang.String)location: variable
				// profile of type org.openqa.selenium.firefox.FirefoxProfile
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				// http://learn-automation.com/handle-untrusted-certificate-selenium/
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(true);

				// NOTE: ERROR StatusLogger No log4j2 configuration file found. Using
				// default configuration: logging only errors to the console.
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
				logPrefs.enable(LogType.PROFILER, Level.INFO);
				logPrefs.enable(LogType.BROWSER, Level.INFO);
				logPrefs.enable(LogType.CLIENT, Level.INFO);
				logPrefs.enable(LogType.DRIVER, Level.INFO);
				logPrefs.enable(LogType.SERVER, Level.INFO);
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

				profile.setPreference("webdriver.firefox.logfile", "/dev/null");
				// NOTE: the next setting appears to have no effect.
				// does one really need os-specific definition?
				// like /dev/null for Linux vs. nul for Windows
				System.setProperty("webdriver.firefox.logfile",
						osName.equals("windows") ? "nul" : "/dev/null");

				// no longer supported as of Selenium 3.8.x
				// profile.setEnableNativeEvents(false);
				profile.setPreference("dom.webnotifications.enabled", false);
				// optional
				/*
				 * profile.setPreference("general.useragent.override",
				 * "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0");
				 */
				// err.println(System.getProperty("user.dir"));
				capabilities.setCapability(FirefoxDriver.PROFILE, profile);
				try {
					driver = new FirefoxDriver(capabilities);
					// driver.setLogLevel(FirefoxDriverLogLevel.ERROR);
				} catch (WebDriverException e) {
					e.printStackTrace();
					throw new RuntimeException(
							"Cannot initialize Firefox driver: " + e.toString());
				}
			} // TODO: finish for other browser
		}
		shadowDriver = new ShadowDriver(driver);
		shadowDriver.setDebug(debug);
	}

	@After
	public void AfterMethod() {
		driver.get("about:blank");
	}

	@AfterClass
	public static void tearDownAll() {
		driver.close();
	}

	// Utilities
	public static String getOSName() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("windows")) {
			osName = "windows";
		}
		return osName;
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (debug) {
			err.println("system property " + name + " = " + value);
		}
		if (value == null || value.length() == 0) {
			value = System.getenv(name);
			if (debug) {
				err.println("system env " + name + " = " + value);
			}
			if (value == null || value.length() == 0) {
				value = defaultValue;
				if (debug) {
					err.println("default value  = " + value);
				}
			}
		}
		return value;
	}

	public static boolean checkEnvironment() {
		boolean result = false;
		if (env.containsKey("TRAVIS") && env.get("TRAVIS").equals("true")) {
			result = true;
		}
		return result;
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = BaseTest.class.getClassLoader().getResource(pagename).toURI();
			err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// origin: https://reflectoring.io/conditional-junit4-junit5-tests/
	// probably an overkill
	public static class BrowserChecker {
		private String browser;

		public BrowserChecker(String browser) {
			this.browser = browser;
		}

		public boolean testingChrome() {
			return (this.browser.equals("chrome"));
		}
	}

	// based on:
	// https://github.com/yashaka/NSelene/blob/master/NSeleneTests/Given.cs
	public void setPage(String html) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		shadowDriver.waitForPageLoaded();
		javascriptExecutor
				.executeScript("document.getElementsByTagName('body')[0].innerHTML = \""
						+ html.replace("\n", "").replace("\r", "").replace("\"", "\\\"")
						+ "\";");
	}

	public void setPageWithTimeout(String html, int timeout) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		shadowDriver.waitForPageLoaded();
		javascriptExecutor.executeScript("setTimeout(function(){ "
				+ "document.getElementsByTagName('body')[0].innerHTML = \""
				+ html.replace("\n", "").replace("\r", "").replace("\"", "\\\"")
				+ "\" } ," + timeout + ");");
	}
}
