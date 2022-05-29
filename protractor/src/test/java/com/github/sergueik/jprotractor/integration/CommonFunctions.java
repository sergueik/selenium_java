package com.github.sergueik.jprotractor.integration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Common functions for integration testing
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommonFunctions {
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	private static final boolean debug = false;
	// set to true for Desktop, false for headless browser testing
	static boolean isDestopTesting = true;
	static boolean isCIBuild = false;
	private static long highlightInterval = 100;
	private static Map<String, String> env = System.getenv();
	private static final String browser = "chrome";
	private static String chromeDriverPath = null;
	private static String osName = null;

	@SuppressWarnings("deprecation")
	public static WebDriver getSeleniumDriver() throws IOException {
		checkEnvironment();
		if (isDestopTesting) {
			// For desktop browser testing, run a Selenium node and Selenium hub on
			// port 4444
			// For Vagrant box browser testing have localhost port 4444 forwarded to
			// the hub 4444
			// Alternatively make the test class launch the browser
			osName = System.getProperty("os.name");
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						new File((chromeDriverPath == null)
								? osName.toLowerCase().startsWith("windows")
										? "C:\\java\\selenium\\chromedriver.exe"
										: "/tmp/chromedriver"
								: chromeDriverPath).getAbsolutePath());
				// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("chrome");
				ChromeOptions options = new ChromeOptions();

				Map<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				String downloadFilepath = System.getProperty("user.dir")
						+ System.getProperty("file.separator") + "target"
						+ System.getProperty("file.separator");
				chromePrefs.put("download.default_directory", downloadFilepath);
				chromePrefs.put("enableNetwork", "true");
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("allow-running-insecure-content");
				options.addArguments("allow-insecure-localhost");
				options.addArguments("enable-local-file-accesses");
				options.addArguments("disable-notifications");
				// options.addArguments("start-maximized");
				options.addArguments("browser.download.folderList=2");
				options.addArguments(
						"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
				options.addArguments("browser.download.dir=" + downloadFilepath);
				// options.addArguments("user-data-dir=/path/to/your/custom/profile");
				// capabilities
				// .setBrowserName(DesiredCapabilities.chrome().getBrowserName());

				capabilities.setBrowserName("chrome");

				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				seleniumDriver = new ChromeDriver(capabilities);
			} else if (browser.equals("firefox")) {
				// alternatively one can add Geckodriver to system path
				System.setProperty("webdriver.gecko.driver",
						"c:/java/selenium/geckodriver.exe");
				// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
				// DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("firefox");

				// use legacy FirefoxDriver
				capabilities.setCapability("marionette", false);
				// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
				capabilities.setCapability("locationContextEnabled", false);
				capabilities.setCapability("acceptSslCerts", true);
				capabilities.setCapability("elementScrollBehavior", 1);
				FirefoxProfile profile = new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(true);
				// no longer supported as of Selenium 3.8.x
				// profile.setEnableNativeEvents(false);

				System.out.println(System.getProperty("user.dir"));
				// TODO: probably won't work with simply "profile"
				capabilities.setCapability("profile", profile);
				// capabilities.setCapability(FirefoxDriver.PROFILE, profile);
				try {
					// java.lang.ClassCastException:
					// org.openqa.selenium.remote.service.DriverCommandExecutor cannot be
					// cast to
					// org.openqa.selenium.firefox.FirefoxDriver$LazyCommandExecutor
					seleniumDriver = new FirefoxDriver(capabilities);
				} catch (WebDriverException e) {
					e.printStackTrace();
					throw new RuntimeException("Cannot initialize Firefox driver");
				}
			}
		} else {
			DesiredCapabilities capabilities = new DesiredCapabilities("phantomjs",
					"", Platform.ANY);
			capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
					new String[] { "--web-security=false", "--ssl-protocol=any",
							"--ignore-ssl-errors=true", "--local-to-remote-url-access=true",
							// prevent local file test XMLHttpRequest Exception 101
							"--webdriver-loglevel=INFO"
					// set to DEBUG for a really verbose console output
					});

			seleniumDriver = new PhantomJSDriver(capabilities);
		}
		return seleniumDriver;
	}

	public static boolean checkEnvironment() {

		if (env.containsKey("TRAVIS") && env.get("TRAVIS").equals("true")) {
			isCIBuild = true;
			isDestopTesting = false;
		}
		return isCIBuild;
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = CommonFunctions.class.getClassLoader().getResource(pagename)
					.toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

	protected static String getScriptContent(String scriptName) throws Exception {
		try {
			final InputStream stream = CommonFunctions.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			// System.err.println(new String(bytes, "UTF-8"));
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception(scriptName);
		}
	}

	public static void setHighlightTimeout(long value) {
		highlightInterval = value;
	}

	public static void highlight(WebElement element) {
		if (wait == null) {
			// wait = new WebDriverWait(seleniumDriver, flexibleWait);
			wait = new WebDriverWait(seleniumDriver,
					Duration.ofSeconds(flexibleWait));
			wait.pollingEvery(Duration.ofMillis(pollingInterval));
		}
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	public static Object executeScript(String script, Object... args) {
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) seleniumDriver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	// custom wait e.g. while Login light box is visible
	public static void waitWhileElementIsVisible(By locator) {
		final By _locator = locator;
		// new WebDriverWait(seleniumDriver, flexibleWait)
		new WebDriverWait(seleniumDriver, Duration.ofSeconds(flexibleWait))

				.pollingEvery(Duration.ofMillis(pollingInterval))
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver o) {
						System.err.println("Size: " + o.findElements(_locator).size());
						return (o.findElements(_locator).size() == 0);
					}
				});

	}

	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (debug) {
			System.err.println("Getting propety or environment: " + name);
		}
		// compatible with
		// org.apache.commons.configuration.PropertiesConfiguration.interpolatedConfiguration
		// https://commons.apache.org/proper/commons-configuration/userguide_v1.10/howto_utilities.html
		if (value == null) {

			Pattern p = Pattern.compile("^(\\w+:)(\\w+)$");
			Matcher m = p.matcher(name);
			if (m.find()) {
				String propertyName = m.replaceFirst("$2");
				if (debug) {
					System.err.println("Interpolating " + propertyName);
				}
				value = System.getProperty(propertyName);
			}
			if (value == null) {
				if (debug) {
					System.err.println("Trying environment " + name);
				}
				value = System.getenv(name);
				if (value == null) {
					if (debug) {
						System.err.println("Nothing found for " + name);
					}
					value = defaultValue;
				}
			}
		}
		return value;
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{(\\w+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = System.getenv(envVarName);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	// origin:
	// https://github.com/abhishek8908/selenium-drivers-download-plugin/blob/master/src/main/java/com/github/abhishek8908/util/DriverUtil.java
	public static String readProperty(String propertyName) {
		String resourcePath = "";
		try {
			resourcePath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			System.err.println(
					String.format("The application resource path: \"%s\"", resourcePath));
		} catch (NullPointerException e) {
			System.err.println(
					"Exception for resourcePath: " + resourcePath + " (ignored) :");
			e.printStackTrace();
		}
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(
					resourcePath + "application.properties");

			Configuration extConfig = ((PropertiesConfiguration) config)
					.interpolatedConfiguration();
			return extConfig.getProperty(propertyName).toString();
		} catch (ConfigurationException e) {
			return null;
		}
	}
}
