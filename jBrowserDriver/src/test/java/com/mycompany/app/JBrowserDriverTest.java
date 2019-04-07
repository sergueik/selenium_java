package com.mycompany.app;

/**
 * Selected test scenarios for Selenium jBrowserDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.RuntimeException;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.text.DateFormat;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
// TODO: what is the replacement in 3.13.x+
// import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.Boolean.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class JBrowserDriverTest {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static WebElement element = null;
	private static String selector = null;
	private static long implicit_wait_interval = 3;

	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long afterTest_interval = 1000;
	private static long highlight_interval = 100;
	private static String cssSelectorOfElementFinderScript;
	private static String cssSelectorOfElementAlternativeFinderScript;
	private static String xpathOfElementFinderScript;
	private static String baseURL = "http://www.tripadvisor.com/";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static StringBuilder loggingSb;
	private static String testFileName = "test.txt";
	private static String testFilePath = new File(testFileName).getAbsolutePath()
			.replaceAll("\\\\", "/");
	private static final Logger log = LogManager
			.getLogger(JBrowserDriverTest.class);

	@BeforeClass
	public static void setUp() throws Exception {
		loggingSb = new StringBuilder();
		driver = new JBrowserDriver(
				Settings.builder().timezone(Timezone.AMERICA_NEWYORK).build());
		// driver = new PhantomJSDriver();
		// driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
		cssSelectorOfElementFinderScript = getScriptContent(
				"cssSelectorOfElement.js");
		cssSelectorOfElementAlternativeFinderScript = getScriptContent(
				"cssSelectorOfElementAlternative.js");
		xpathOfElementFinderScript = getScriptContent("xpathOfElement.js");
		driver.get(baseURL);
	}

	@Before
	public void beforeTest() {
	}

	@After
	public void afterTest() {
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(afterTest_interval);
		if (driver instanceof JBrowserDriver) {
			// jbrowserDriver does not support quit() yet
			// https://github.com/MachinePublishers/jBrowserDriver/issues/213
			// driver.close();
			driver.quit();
		} else {
			driver.close();
			driver.quit();
		}
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	// the jquery file upload plugin issue:
	// https://github.com/MachinePublishers/jBrowserDriver/issues/110
	// the file upload plugin issue:
	// https://github.com/MachinePublishers/jBrowserDriver/issues/143

	
	 @Ignore
	@Test
	public void test1SendKeys() {
		System.err.println("test1SendKeys");
		driver.get("http://blueimp.github.io/jQuery-File-Upload/basic.html");
		element = driver.findElement(By.id("fileupload"));
		assertThat(element, notNullValue());
		// highlight(element);

		assertTrue(element.getAttribute("multiple") != null);
		executeScript("$('#fileupload').removeAttr('multiple');");
		element.sendKeys(testFilePath);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		element = driver.findElement(By.className("progress-bar"));
		assertThat(element.getAttribute("class"),
				containsString("progress-bar-success"));
		element = driver.findElement(By.id("files"));
		assertThat(element.getText(), containsString(testFileName));
	}

	@Ignore
	@Test
	public void test2SendKeys() {
		System.err.println("test2SendKeys");
		driver.get("http://siptv.eu/converter/");
		element = driver
				.findElement(By.cssSelector("div#container form#file_form input#file"));
		assertThat(element, notNullValue());
		highlight(element);

		element.sendKeys(testFilePath);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		assertThat(element.getAttribute("value"), containsString(testFileName));

		element = driver.findElement(
				By.cssSelector("div#container form#file_form input#submit"));
		element.click();
	}

	@Ignore
	@Test
	public void testExecutePhantomJS() {
		if (driver instanceof PhantomJSDriver) {
			System.err.println("testExecutePhantomJS");
			driver.get("http://siptv.eu/converter/");
			element = driver.findElement(
					By.cssSelector("div#container form#file_form input#file"));
			assertThat(element, notNullValue());

			((PhantomJSDriver) driver).executePhantomJS(String.format(
					"var page = this; page.uploadFile('input[id=file]', '%s' );",
					testFilePath));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			assertThat(element.getAttribute("value"), containsString(testFileName));
		}
	}

	// @Ignore
	@Test
	public void verifyTextTest() throws Exception {
		try {
			assertEquals("Hotels", findElement("link_text", "Hotels").getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@Ignore
	@Test
	public void xpathOfElementTest() throws Exception {
		element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		selector = xpathOfElement(element);
		// Assert
		assertThat(selector, notNullValue());
		assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		element = findElement("xpath", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// NOTE: this test is hanging the jbrowserdriver
	// after the test is run orphaned java processess require a taskkill
	@Ignore
	@Test
	public void test13_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
				By.cssSelector(".container .row .intro-message iframe")));

		// Act
		WebElement buttonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("h3 button"))));

		assertThat(buttonElement, notNullValue());
		buttonElement.click();
		// Assert
		try {
			// confirm alert
			Alert alert = driver.switchTo().alert();
			String alert_text = alert.getText();
			assertThat(alert_text, containsString("You clicked on Green"));
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	// NOTE: org.junit.ComparisonFailure
	@Ignore
	@Test
	public void cssSelectorOfElementWithIdInParentTest() throws Exception {
		element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		selector = cssSelectorOfElement(element);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				// NOTE: old script was
				// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a",
				selector);
		assertThat(selector, notNullValue());
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// @Ignore
	@Test
	public void cssSelectorOfElementTest() throws Exception {
		try {
			element = findElement("css_selector", "input#mainSearch");
			selector = cssSelectorOfElement(element);
			highlight(element);
			assertEquals("input#mainSearch", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@Ignore
	@Test
	public void cssSelectorOfElementWithIdTest() throws Exception {
		try {
			element = findElement("id", "searchbox");
			selector = cssSelectorOfElement(element);
			highlight(element);
			assertEquals("input#searchbox", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	// @Ignore
	@Test
	public void testcssSelectorOfElementAlternative() throws Exception {

		try {
			element = findElement("id", "searchbox");
			highlight(element);
			selector = cssSelectorOfElementAlternative(element);
			System.err.println("css_selector: " + selector);
			assertEquals(
					"form[name=\"PTPT_HAC_FORM\"] > div > label > input[name=\"q\"]",
					selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {

			verificationErrors.append(e.toString());
		}
	}

	private String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(cssSelectorOfElementFinderScript, element);
	}

	private String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(cssSelectorOfElementAlternativeFinderScript,
				element);
	}

	private String xpathOfElement(WebElement element) {
		return (String) executeScript(xpathOfElementFinderScript, element);
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue, WebElement parent) {
		SearchContext finder;
		String parent_css_selector = null;
		String parent_xpath = null;

		List<WebElement> elements = null;
		Hashtable<String, Boolean> supportedSelectorStrategies = new Hashtable<String, Boolean>();
		supportedSelectorStrategies.put("css_selector", true);
		supportedSelectorStrategies.put("xpath", true);

		if (selectorKind == null
				|| !supportedSelectorStrategies.containsKey(selectorKind)
				|| !supportedSelectorStrategies.get(selectorKind)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = cssSelectorOfElement(parent);
			parent_xpath = xpathOfElement(parent);
			finder = parent;
		} else {
			finder = driver;
		}

		if (selectorKind == "css_selector") {
			String extended_css_selector = String.format("%s  %s",
					parent_css_selector, selectorValue);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath,
					selectorValue);

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(selectorValue));
		}
		return elements;
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue) {
		return findElements(selectorKind, selectorValue, null);
	}

	private WebElement findElement(String selectorKind, String selectorValue) {
		WebElement element = null;
		Hashtable<String, Boolean> supportedSelectorStrategies = new Hashtable<String, Boolean>();
		supportedSelectorStrategies.put("id", true);
		supportedSelectorStrategies.put("css_selector", true);
		supportedSelectorStrategies.put("xpath", true);
		supportedSelectorStrategies.put("partial_link_text", false);
		supportedSelectorStrategies.put("link_text", true);
		supportedSelectorStrategies.put("classname", false);

		if (selectorKind == null
				|| !supportedSelectorStrategies.containsKey(selectorKind)
				|| !supportedSelectorStrategies.get(selectorKind)) {
			return null;
		}
		if (selectorKind == "id") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.id(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.id(selectorValue));
		}
		if (selectorKind == "classname") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.className(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.className(selectorValue));
		}
		if (selectorKind == "link_text") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.linkText(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.linkText(selectorValue));
		}
		if (selectorKind == "css_selector") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.xpath(selectorValue));
		}
		return element;
	}

	private void highlight(WebElement element) {
		highlight(element, highlight_interval, "yellow");
	}

	public void highlight(WebElement element, long highlightInterval,
			String color) {
		System.err.println("Color: " + color);
		if (wait == null) {
			wait = new WebDriverWait(driver, flexible_wait_interval);
		}
		// Selenium Driver version sensitive code: 3.13.0 vs. 3.8.0 and older
		// https://stackoverflow.com/questions/49687699/how-to-remove-deprecation-warning-on-timeout-and-polling-in-selenium-java-client
		// wait.pollingEvery(Duration.ofMillis(wait_polling_interval));
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);

		// wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript(String.format("arguments[0].style.border='3px %s'", color),
					element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Exception (ignored): " + e.toString());
		}
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	protected static String getScriptContent(String scriptName) throws Exception {
		try {
			final InputStream stream = JBrowserDriverTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception(scriptName);
		}
	}

}
