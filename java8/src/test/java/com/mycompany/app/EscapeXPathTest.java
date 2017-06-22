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
		// TODO: Observed user agent problem with firefox - mobile version of
		// tripadvisor is rendered
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
    
		String text = "\"Burj\" 'Khalifa'";
		System.err.println("test1 " + escapeXPath(text));
		System.err.println("test1 " + escapeXPath2(text));
		System.err.println("test1 " + escapeXPath3(text));

		List<WebElement> elements = driver.findElements(By.xpath(String.format("//*[contains(text(), %s)]", escapeXPath3(text))));
		assertTrue(elements.size() > 0);
		highlight(elements.get(0));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	@Test(enabled = true)
	public void test2() {
		String text = "\"Burj\" Khalifa";
		System.err.println("test1 " + escapeXPath(text));
		System.err.println("test1 " + escapeXPath2(text));
		System.err.println("test1 " + escapeXPath3(text));

		List<WebElement> elements = driver.findElements(By.xpath(String.format("//*[contains(text(), %s)]", escapeXPath3(text))));
		assertTrue(elements.size() > 0);
		highlight(elements.get(0));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
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
	// one can escape quotes and apostrophes in XPath 2.0 by doubling them, but
	// there is no way of doing it in XPath 1.0.
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
  // broken
	public static String escapeXPath(String value) {
		// If value doesn't have any " then enclose value in "
		if (!value.contains("\""))
			return String.format("\"%s\"", value);

		// If there is no ' then enclose in '
		if (!value.contains("'"))
			return String.format("'%s'", value);

		// If value has both " and ' in the string so must use xpath's concat
		StringBuilder sb = new StringBuilder("concat(");

		// Search for "
		// number of arguments to concat.
		int lastPos = 0;
		int nextPos = value.indexOf("\"");
		while (nextPos != -1) {
			// note if this is not the first occurence
			if (lastPos != 0)
				sb.append(",");

			sb.append(String.format("\"%s\",'\"'",
					value.substring(lastPos, nextPos - lastPos)));
			lastPos = ++nextPos;

			// Find next occurence
			nextPos = value.indexOf("\"", lastPos);
		}

		sb.append(")");
		return sb.toString();
	}

	// @Nullable
	public static String escapeXPath2(@Nullable String string) {
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

	public static String escapeXPath3(@Nullable String value) {
		if (!value.contains("'"))
			return '\'' + value + '\'';

		else if (!value.contains("\""))
			return '"' + value + '"';

		else
			return "concat('" + value.replace("'", "',\"'\",'") + "')";
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = EscapeXPathTest.class.getClassLoader().getResource(pagename)
					.toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

}
