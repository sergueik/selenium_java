package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Boolean.*;
import static java.lang.Float.*;
import java.lang.Float;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTest {

	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private WebElement element = null;
	private String selector = null;
	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;
	private static String baseURL = "http://www.tripadvisor.com/";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(AppTest.class);

	private static Pattern pattern;
	private static Matcher matcher;

	@BeforeClass
	public static void setUp() {

		/*
		 * FirefoxProfile profile = new FirefoxProfile();
		 * profile.setPreference("browser.download.folderList",2);
		 * profile.setPreference("browser.download.manager.showWhenStarting",false);
		 * profile.setPreference("browser.download.dir","c:\downloads");
		 * profile.setPreference
		 * ("browser.helperApps.neverAsk.saveToDisk","text/csv"); WebDriver driver =
		 * new FirefoxDriver(profile); //new RemoteWebDriver(new
		 * URL("http://localhost:4444/wd/hub"), capability);
		 */
		driver = new FirefoxDriver();

		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@Ignore
	@Test
	public void verifyTextTest() {
		assertEquals("Hotels", findElement("link_text", "Hotels").getText());
	}

	@Ignore
	@Test
	public void test1() {
		element = findElement("link_text", "Hotels");
		highlight(element);
		selector = xpathOfElement(element);
		assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		try {
			element = findElement("xpath", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 1: " + e.toString());
		}
	}

	@Ignore
	@Test
	public void test2() {
		element = findElement("link_text", "Hotels");
		highlight(element);
		selector = cssSelectorOfElement(element);
		System.err.println("test 2: selector: " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 2: " + e.toString());
		}
	}

	// @Ignore
	@Test
	public void test3() {
		element = findElement("link_text", "Hotels");
		highlight(element);
		selector = cssSelectorOfElement(element);
		System.err.println("test 3: selector (standard): " + selector);
		selector = cssSelectorOfElementAlternative(element);
		System.err.println("test 3: selector (alternative) : " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 3: " + e.toString());
		}
	}

	@Ignore
	@Test
	public void test4() {
		element = findElement("css_selector", "input#mainSearch");
		selector = cssSelectorOfElement(element);
		// System.err.println("test 4: selector: " + selector);
		assertEquals("input#mainSearch", selector);
		highlight(element);
	}

	@Ignore
	@Test
	public void test5() {
		element = findElement("css_selector", "span#rdoFlights")
				.findElement(By.cssSelector("div span.label"));
		selector = cssSelectorOfElement(element);
		// System.err.println("test 5: selector: " + selector);
		assertEquals("span#rdoFlights > div.header > span.label", selector);
		highlight(element);
	}

	@Ignore
	@Test
	public void test6() {
		element = findElement("id", "searchbox");
		selector = cssSelectorOfElement(element);
		// System.err.println("test 6: selector: " + selector);
		assertEquals("input#searchbox", selector);
		highlight(element);
	}

	@Ignore
	@Test
	public void test7() {
		element = findElement("id", "searchbox");
		highlight(element);
		selector = cssSelectorOfElementAlternative(element);
		// System.err.println("test 7: selector (alternative): " + selector);
		assertEquals(
				"form[name=\"PTPT_HAC_FORM\"] > div.metaFormWrapper > div.metaFormLine.flex > label.ptptLabelWrap > input[name=\"q\"]",
				selector);
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 7: " + e.toString());
		}
	}

	@Ignore
	@Test
	public void test8() {

		element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		String style = styleOfElement(element);
		System.err.println("style:\n" + style);
		String colorAttribute = styleOfElement(element, "color");
		String heightAttribute = styleOfElement(element, "height");
		String widthAttribute = styleOfElement(element, "width");

		// assertions of certain CSS attributes
		try {
			Assert.assertTrue(colorAttribute.equals("rgb(255, 255, 255)"));
		} catch (AssertionError e) {
			// slurp
		}
		pattern = Pattern.compile("\\((\\d+),");
		matcher = pattern.matcher(colorAttribute);
		if (matcher.find()) {
			int red = Integer.parseInt(matcher.group(1).toString());
			Assert.assertTrue(red > 254);
		}
		System.err.println("color:" + colorAttribute);

		try {
			Assert.assertTrue(widthAttribute.equals("36.6667px")); // fragile !
		} catch (AssertionError e) {
			// slurp
		}
		pattern = Pattern.compile("([\\d\\.]+)px");
		matcher = pattern.matcher(widthAttribute);
		if (matcher.find()) {
			Float mask = new Float("20.75f");
			Float width = mask.parseFloat(matcher.group(1).toString());
			Assert.assertTrue(width > 36.5);
		}
		System.err.println("width:" + widthAttribute);

		System.err.println("examine height attribute: " + heightAttribute);
		// broken after 431eac6a3baa
		// Assert.assertTrue(height.equals("12px"));
		// print css values
		// System.err.println("height:" + heightAttribute);
	}

	private String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("cssSelectorOfElement.js"),
				element);
	}

	private String styleOfElement(WebElement element, Object... arguments) {
		return (String) executeScript(getScriptContent("getStyle.js"), element,
				arguments);
	}

	private String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(
				getScriptContent("cssSelectorOfElementAlternative.js"), element);
	}

	private void highlight(WebElement element) {
		highlight(element, highlight_interval);
	}

	private static void highlight(WebElement element, long highlight) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'",
					new Object[] { element });
			Thread.sleep(highlight);
			executeScript("arguments[0].style.border=''", new Object[] { element });

		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	private String xpathOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("xpathOfElement.js"),
				new Object[] { element });
	}

	public static Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver); // a.k.a. (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
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

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = AppTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file: " + scriptName);
		}
	}

	public static void waitForElementVisible(By locator) {
		log.info("Waiting for element visible for locator: {}", locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementVisible(By locator, long timeout) {
		log.info("Waiting for element visible for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator) {
		log.info("Waiting for element present  for locator: {}", locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator, long timeout) {
		log.info("Waiting for element present for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForPageLoad() {
		log.info("Wait for page load via JS...");
		String state = "";
		int counter = 0;

		do {
			try {
				state = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
			log.info(("Browser state is: " + state));
		} while (!state.equalsIgnoreCase("complete") && counter < 20);

	}

	public static boolean isAttributePresent(By locator, String attribute) {
		log.info("Is Attribute Present for locator: {}, attribute: {}", locator,
				attribute);
		return driver.findElement(locator).getAttribute(attribute) != null;
	}

	public static void selectDropdownByIndex(By locator, int index) {
		log.info("Select Dropdown for locator: {} and index: {}", locator, index);
		try {
			Select select = new Select(driver.findElement(locator));
			select.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getBaseURL() {
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
		return protocol + "://" + domain;
	}

	public static void clickJS(By locator) {
		log.info("Clicking on locator via JS: {}", locator);
		wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(locator));
	}

	public static void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}

	public static void mouseOver(By locator) {
		log.info("Mouse over: {}", locator);
		actions.moveToElement(driver.findElement(locator)).build().perform();
	}

	public static void click(By locator) {
		log.info("Clicking: {}", locator);
		driver.findElement(locator).click();
	}

	public static void clear(By locator) {
		log.info("Clearing input: {}", locator);
		driver.findElement(locator).clear();
	}

	public static void sendKeys(By locator, String text) {
		log.info("Typing \"{}\" into locator: {}", text, locator);
		driver.findElement(locator).sendKeys(text);
	}

	public static String getText(By locator) {
		String text = driver.findElement(locator).getText();
		log.info("The string at {} is: {}", locator, text);
		return text;
	}

	public static String getAttributeValue(By locator, String attribute) {
		String value = driver.findElement(locator).getAttribute(attribute);
		log.info("The attribute \"{}\" value of {} is: {}", attribute, locator,
				value);
		return value;
	}

	public static boolean isElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return true;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return false;
		}
	}

	// custom wait while Login Lightbox is visible

	public static void waitWhileElementIsVisible(By locator) {
		final By locatorFinal = locator;
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver o) {
				return (o.findElements(locatorFinal).size() == 0);
			}
		});
	}

	public static boolean isElementNotVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		}
	}

	public static String getBodyText() {
		log.info("Getting boby text");
		return driver.findElement(By.tagName("body")).getText();
	}

	public static void highlight(By locator) throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		executeScript("arguments[0].style.border=''", element);
	}
}
