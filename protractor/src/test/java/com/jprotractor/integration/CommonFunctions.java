package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

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
	// set to true for Desktop, false for headless browser testing
	static boolean isDestopTesting = true;
	static boolean isCIBuild = false;
	private static long highlightInterval = 100;
	private static Map<String, String> env = System.getenv();

	public static WebDriver getSeleniumDriver() throws IOException {
		checkEnvironment();
		if (isDestopTesting) {
			// For desktop browser testing, run a Selenium node and Selenium hub on
			// port 4444
			// For Vagrant box browser testing have localhost port 4444 forwarded to
			// the hub 4444

			DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
					Platform.ANY);
			// seleniumDriver = new FirefoxDriver(capabilities);
			// FirefoxProfile profile = new ProfilesIni().getProfile("default");
			// profile.setEnableNativeEvents(false);
			// capabilities.setCapability("firefox_profile", profile);

			seleniumDriver = new RemoteWebDriver(new URL(
					"http://127.0.0.1:4444/wd/hub"), capabilities);
			return seleniumDriver;
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

	public static void highlight(WebElement element) throws InterruptedException {
		int flexibleWait = 5;
		long pollingInterval = 500;
		WebDriverWait wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlightInterval);
		executeScript("arguments[0].style.border=''", element);
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
		new WebDriverWait(seleniumDriver, flexibleWait).pollingEvery(
				pollingInterval, TimeUnit.SECONDS).until(
				new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver o) {
						return (o.findElements(_locator).size() == 0);
					}
				});

	}
}
