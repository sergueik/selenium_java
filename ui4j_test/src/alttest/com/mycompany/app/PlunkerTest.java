package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import static java.lang.Boolean.*;
import static java.lang.Float.*;
import java.lang.Float;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class PlunkerTest {

	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private static int flexibleWait = 5;
	private static int implicitWait = 1;
	private static long pollingInterval = 500;
	private static String baseURL = "https://embed.plnkr.co/";
	private static String projectId = "ggL5oAvGgucDA5HCYk7K";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(TripAdvisorTest.class);
	private static String osName;

	private static Pattern pattern;
	private static Matcher matcher;
	private static final String browser = "chrome";

	@BeforeSuite
	@SuppressWarnings("deprecation")
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		// origin:
		// https://sqa.stackexchange.com/questions/26275/how-to-disable-chrome-save-your-password-selenium-java
		// http://learn-automation.com/disable-chrome-notifications-selenium-webdriver/

		HashMap<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_settings.popups", 0);
		// Put this into prefs map to switch off browser notification
		prefs.put("profile.default_content_setting_values.notifications", 2);
		// Put this into prefs map to switch off save password notification
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		String downloadPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		prefs.put("download.default_directory", downloadPath);
		prefs.put("enableNetwork", "true");
		options.setExperimentalOption("prefs", prefs);

		for (String optionAgrument : (new String[] {
				"--allow-running-insecure-content", "--allow-insecure-localhost",
				"--enable-local-file-accesses", "--disable-notifications",
				/* "start-maximized" , */
				"--browser.download.folderList=2", "--disable-web-security",
				"--no-proxy-server",
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
				String.format("--browser.download.dir=%s", downloadPath)
				/* "--user-data-dir=/path/to/your/custom/profile"  , */
		})) {
			options.addArguments(optionAgrument);
		}

		// options for headless
		/*
		for (String optionAgrument : (new String[] { "headless",
		    "window-size=1200x600", })) {
		  options.addArguments(optionAgrument);
		}
		*/
		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	@AfterSuite
	public static void tearDown() throws Exception {
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@BeforeMethod
	public void loadBaseURL() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void testEditor() {
		driver.get(String.format("https://plnkr.co/edit/%s/?p=info", projectId));
		// "?p=preview"
		WebElement runButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("body > nav button i.icon-play")));
		assertThat(runButton, notNullValue());
		highlight(runButton);
		runButton.click();
		WebElement previewIframe = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("iframe[name='plunkerPreviewTarget']")));
		assertThat(previewIframe, notNullValue());
	}

	@Test(enabled = false)
	public void testEmbed() {
		driver.get("https://embed.plnkr.co/" + projectId);
	}

	@Test(enabled = false)
	public void testFullScreeen() {
		driver.get("https://embed.plnkr.co/" + projectId);
	}

	public static String getBaseURL() {
		System.err.println("Get base URL: " + driver.getCurrentUrl());
		log.info("Get base URL: {}", driver.getCurrentUrl());
		String currentURL = driver.getCurrentUrl();
		String protocol = null;
		String domain = null;

		try {
			URL url = new URL(currentURL);
			protocol = url.getProtocol();
			domain = url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// System.err.println("Returning: " + protocol + "://" + domain);
		return protocol + "://" + domain;
	}

	public static void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}

	public static void highlight(By locator, long highlight_interval)
			throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		try {
			Thread.sleep(highlight_interval);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
		executeScript("arguments[0].style.border=''", element);
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'", element);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	private static Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}
}
