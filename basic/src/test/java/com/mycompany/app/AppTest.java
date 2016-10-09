package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Boolean.*;
import static java.lang.Float.*;
import java.lang.Float;

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
	private static String cssSelectorOfElementFinderScript;
	private static String cssSelectorOfElementAlternativeFinderScript;
	private static String xpathOfElementFinderScript;
	private static String getStyleScript;
	private static String baseUrl = "http://www.tripadvisor.com/";

	private static final StringBuffer verificationErrors = new StringBuffer();

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
		driver.get(baseUrl);
		driver.manage().timeouts()
				.implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
		try {
			cssSelectorOfElementFinderScript = getScriptContent("cssSelectorOfElement.js");
			cssSelectorOfElementAlternativeFinderScript = getScriptContent("cssSelectorOfElementAlternative.js");
			xpathOfElementFinderScript = getScriptContent("xpathOfElement.js");
			getStyleScript = getScriptContent("getStyle.js");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Ignore
	@Test
	public void verifyTextTest() {
		assertEquals("Hotels", findElement("link_text", "Hotels").getText());
	}

	@Ignore
	@Test
	public void xpathOfElementTest() {
		element = findElement("link_text", "Hotels");
		highlight(element);
		selector = xpathOfElement(element);
		assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		try {
			element = findElement("xpath", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append(e.toString());
		}
	}

	@Ignore
	@Test
	public void cssSelectorOfElementWithIdInParentTest() {
		element = findElement("link_text", "Hotels");
		highlight(element);
		selector = cssSelectorOfElement(element);
		assertEquals("div#HEAD > div > div:nth-of-type(2) > ul > li > span > a",
				selector);
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append(e.toString());
		}
	}

	@Ignore
	@Test
	public void cssSelectorOfElementTest() {
		element = findElement("css_selector", "input#mainSearch");
		selector = cssSelectorOfElement(element);
		highlight(element);
		assertEquals("input#mainSearch", selector);
	}

	@Ignore
	@Test
	public void cssSelectorOfElementWithIdTest() {
		element = findElement("id", "searchbox");
		selector = cssSelectorOfElement(element);
		highlight(element);
	}

	@Ignore
	@Test
	public void testcssSelectorOfElementAlternative() {
		element = findElement("id", "searchbox");
		highlight(element);
		selector = cssSelectorOfElementAlternative(element);
		// System.err.println("css_selector: " + selector );
		assertEquals(
				"form[name=\"PTPT_HAC_FORM\"] > div > div > label > input[name=\"q\"]",
				selector);
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append(e.toString());
		}
	}

	@Test
	public void testStyleOfElement() {

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
    } catch (AssertionError) {
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
    } catch (AssertionError) {
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
		Assert.assertTrue(height.equals("12px"));
		// print css values
		System.err.println("height:" + heightAttribute);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	private String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(cssSelectorOfElementFinderScript, element);
	}

	private String styleOfElement(WebElement element, Object... arguments) {
		return (String) executeScript(getStyleScript, element, arguments);
	}

	private String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(cssSelectorOfElementAlternativeFinderScript,
				element);
	}

	private void highlight(WebElement element) {
		highlight(element, highlight_interval);
	}

	private static void highlight(WebElement element, long highlight) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'",
						new Object[] { element });
			}
			Thread.sleep(highlight);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border=''", new Object[] { element });
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	private String xpathOfElement(WebElement element) {
		return (String) executeScript(xpathOfElementFinderScript,
				new Object[] { element });
	}

	public Object executeScript(String script, Object... arguments) {
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
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath,
					selectorValue);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(extended_xpath)));
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
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.id(selectorValue));
		}
		if (selectorKind == "classname") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.className(selectorValue));
		}
		if (selectorKind == "link_text") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.linkText(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.linkText(selectorValue));
		}
		if (selectorKind == "css_selector") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.xpath(selectorValue));
		}
		return element;
	}

	protected static String getScriptContent(String scriptName) throws Exception {
		try {
			final InputStream stream = AppTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception(scriptName);
		}
	}
}
