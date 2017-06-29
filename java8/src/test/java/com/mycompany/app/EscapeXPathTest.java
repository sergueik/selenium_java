package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.testng.internal.Nullable;
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

public class EscapeXPathTest {

	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(EscapeXPathTest.class);

	private static Pattern pattern;
	private static Matcher matcher;

	@BeforeSuite
	@SuppressWarnings("deprecation")
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver",
				new File("c:/java/selenium/geckodriver.exe").getAbsolutePath());
		System.setProperty("webdriver.firefox.bin",
				new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
						.getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		// use legacy FirefoxDriver
		capabilities.setCapability("marionette", false);
		// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
		capabilities.setCapability("locationContextEnabled", false);
		capabilities.setCapability("acceptSslCerts", true);
		capabilities.setCapability("elementScrollBehavior", 1);
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(true);
		profile.setEnableNativeEvents(false);

		System.out.println(System.getProperty("user.dir"));
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		try {
			driver = new FirefoxDriver(capabilities);
		} catch (WebDriverException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot initialize Firefox driver");
		}
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
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
	public void loadPage() {
		driver.navigate().to(getPageContent("test.htm"));
	}

	@Test(enabled = true)
	public void test1() {
		ArrayList<String> texts = new ArrayList<>(
				Arrays.asList(new String[] { "Burj Khalifa", "\"Burj\" 'Khalifa'",
						"\"Burj\" Khalifa", "Burj 'Khalifa'" }));

		for (String text : texts) {

			System.err.println(
					String.format("test1 (1): %s => %s ", text, escapeXPath(text)));
			System.err.println(
					String.format("test1 (2): %s => %s ", text, escapeXPath2(text)));
			/*			System.err.println(
								String.format("test1 (2): %s => %s ", text, escapeXPath3(text)));
			*/
			try {
				List<WebElement> elements1 = driver.findElements(By.xpath(
						String.format("//*[contains(text(), %s)]", escapeXPath(text))));
				assertTrue(elements1.size() > 0);
				highlight(elements1.get(0));
				List<WebElement> elements2 = driver.findElements(By.xpath(
						String.format("//*[contains(text(), %s)]", escapeXPath2(text))));
				assertTrue(elements2.size() > 0);
				highlight(elements2.get(0));
				/*				List<WebElement> elements3 = driver.findElements(By.xpath(
										String.format("//*[contains(text(), %s)]", escapeXPath3(text))));
								assertTrue(elements3.size() > 0);
								highlight(elements3.get(0));
				*/
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}

			} catch (InvalidSelectorException e) {
				// ignore
				// InvalidSelectorError:
				// Unable to locate an element with the xpath expression
				// //*[contains(text(), '""Burj"" ''Khalifa'')] because of the following
				// error:
				// SyntaxError: The expression is not a legal expression.
				System.err.println("Test1 Ignored: " + e.toString());
			}
		}
	}

	// origin:
	// https://saucelabs.com/resources/articles/selenium-tips-css-selectors
	// NOTE: all failing
	@Test(enabled = false)
	public void test2() {
		ArrayList<String> texts = new ArrayList<>(
				Arrays.asList(new String[] { "Burj Khalifa", "\"Burj\" 'Khalifa'",
						"\"Burj\" Khalifa", "Burj 'Khalifa'" }));

		for (String text : texts) {
			System.err.println(String.format("test2: %s", text));

			try {
				List<WebElement> elements = driver.findElements(
						By.cssSelector(String.format("th:contains('%s')", text)));
				assertTrue(elements.size() > 0);
				highlight(elements.get(0));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}

			} catch (InvalidSelectorException e) {
				// ignore
				// InvalidSelectorError:
				// Unable to locate an element with the xpath expression
				// //*[contains(text(), '""Burj"" ''Khalifa'')] because of the following
				// error:
				// SyntaxError: The expression is not a legal expression.
				System.err.println("Test2 Ignored: " + e.toString());
			}
		}
	}

	@Test(enabled = true)
	public void test3() {
		ArrayList<String> texts = new ArrayList<>(
				Arrays.asList(new String[] { "Burj Khalifa", "\"Burj\" 'Khalifa'",
						"\"Burj\" Khalifa", "Burj 'Khalifa'" }));

		for (String text : texts) {
			System.err.println(String.format("test3: %s", text));

			Optional<WebElement> element = driver
					.findElements(By.cssSelector("th[scope='row']")).stream()
					.filter(o -> {
						String t = o.getText();
						// System.err.println("in stream filter (3): Text = " + t);
						return (Boolean) (t.contains(text));
					}).findFirst();

			assertTrue(element.isPresent());
			highlight(element.get());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

		}
	}

	// https://sqa.stackexchange.com/questions/362/a-way-to-match-on-text-using-css-locators
	// requires jQuery
	public static WebElement findByText(WebDriver driver,
			String cssSelectorParent, String text) {
		WebElement parent = driver.findElement(By.cssSelector(cssSelectorParent));
		Object element = ((JavascriptExecutor) driver).executeScript(String.format(
				"var x = $(arguments[0]).find(\":contains('%s')\"); return x;", text),
				parent);
		return (WebElement) element;
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

	public static void highlight(WebElement element) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexible_wait_interval);
			wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		}
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight_interval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	public static void highlight(By locator) throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		executeScript("arguments[0].style.border=''", element);
	}

	// origin:
	// https://stackoverflow.com/questions/642125/encoding-xpath-expressions-with-both-single-and-double-quotes
	// alternative: use java.text.Normalizer
	// alternative: use built-in xpath translate function e.g.
	// "//*[contains(translate(@name,'áàâäéèêëíìîïóòôöúùûü','aaaaeeeeiiiioooouuuu'),'converted')]"
	// possibly a better way yet is to use streams to query XML node texts in a
	// way that allows one to compare the value of interest directly.
	// not relying on possibly fragile XPath composition, but the purpose is the
	// same
	/**
	 * Escapes the quote characters inside the text condition in an XPath expression.
	 * Because there is no escape character in XPath string values, this method is not really escapes passed string, but
	 * breaks it into pars and uses XPath concat function.
	 *
	 * @param  value String to escape.
	 * @return Escaped value.
	 */

	public static String escapeXPath(@Nullable String string) {
		if (string == null) {
			return null;
		} else if (string.contains("'")) {
			StringBuilder sb = new StringBuilder();
			sb.append("concat('");

			for (int i = 0; i < string.length(); i++) {
				char ch = string.charAt(i);
				if ('\'' == ch) {
					sb.append("',\"").append(ch).append("\",'");
				} else {
					sb.append(ch);
				}
			}

			sb.append("')");
			return sb.toString();
		} else {
			return "'" + string + "'";
		}
	}

	// shorter version of escapeXPath
	public static String escapeXPath2(@Nullable String value) {
		if (!value.contains("'"))
			return '\'' + value + '\'';
		else if (!value.contains("\""))
			return '"' + value + '"';
		else
			return "concat('" + value.replace("'", "',\"'\",'") + "')";
	}

	// one can escape quotes and apostrophes in XPath 2.0 by doubling them, but
	// there is no way of doing it in XPath 1.0.
	// NOTE: does not work
	public static String escapeXPath3(@Nullable String value) {
		return "'" + value.replace("'", "''").replace("\"", "\"\"");
	}

	// alternative: use built-in xpath translate function e.g.
	// "//*[contains(translate(@name,'áàâäéèêëíìîïóòôöúùûü','aaaaeeeeiiiioooouuuu'),'converted')]"
	// other XPath 2.0 Solutions
	// "//*/text()[contains(lower-case(.),'test')]"
	// "//*/text()[matches(.,'test', 'i')]"
	// "//*/text()[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'test')]

	protected static String getPageContent(String pagename) {
		try {
			URI uri = EscapeXPathTest.class.getClassLoader().getResource(pagename)
					.toURI();
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

}
