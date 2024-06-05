package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import org.testng.annotations.BeforeClass;
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

public class EscapeXPathTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(EscapeXPathTest.class);

	@SuppressWarnings("unused")
	private static Pattern pattern;
	private static Matcher matcher;

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
	}

	@BeforeMethod
	public void loadPage() {
		driver.navigate().to(getPageContent("test.htm"));
	}

	@Test(enabled = true)
	public void test1() {
		List<String> texts = new ArrayList<>(
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
				sleep(500);
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
	@Test(enabled = true)
	public void test2() {
		// sleep(10000);
		List<String> texts = new ArrayList<>(
				Arrays.asList(new String[] { "Burj Khalifa", "\"Burj\" 'Khalifa'",
						"\"Burj\" Khalifa", "Burj 'Khalifa'" }));

		for (String text : texts) {
			System.err.println(String.format("test2: %s", text));

			try {
				List<WebElement> elements = driver.findElements(
						By.cssSelector(String.format("th:contains('%s')", text)));
				assertTrue(elements.size() > 0);
				highlight(elements.get(0));
				sleep(500);
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
		List<String> texts = new ArrayList<>(
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
			sleep(500);
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

	// origin: https://groups.google.com/forum/#!topic/selenium-users/sTWcaVbnU6c

	public static String toHexStr(byte b) {
		String str = "0x" + String.format("%02x", b);
		return str;
	}

	private String replaceSpecials() {
		// http://utf8-chartable.de/unicode-utf8-table.pl?start=8064&names=-&utf8=0x
		String pageSource = "";
		pageSource = driver.getPageSource();
		byte[] allBytes = null;
		try {
			allBytes = pageSource.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		String translate = "";
		for (int i = 0; i < allBytes.length; i++) {
			// newBytes[i + offset] = allBytes[i];

			if (allBytes[i] == ((byte) 0xE2)) {
				// System.out.println(String.format("0xE2%s%s " , toHexStr(allBytes[i +
				// 1]), " " + toHexStr(allBytes[i + 2])));
				switch (allBytes[i + 2]) {
				case (byte) 0x98: // opening single quote
				case (byte) 0x99: // closing single quote
					translate += "'";
					i += 2;
					break;
				case (byte) 0x94: // dash, also 0xe292, 0xe293
					translate += "-";
					i += 2;
					break;
				case (byte) 0x9c: // opening double quote
				case (byte) 0x9d: // closing double quote
					translate += "\"";
					i += 2;
					break;
				}
			} else {
				translate += (char) allBytes[i];
			}
		}

		// pageSource = translate;
		return translate;
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

	public String escapeXPath(@Nullable String string) {
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
	public String escapeXPath2(@Nullable String value) {
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
	public String escapeXPath3(@Nullable String value) {
		return "'" + value.replace("'", "''").replace("\"", "\"\"");
	}

	// alternative: use built-in xpath translate function e.g.
	// "//*[contains(translate(@name,'áàâäéèêëíìîïóòôöúùûü','aaaaeeeeiiiioooouuuu'),'converted')]"
	// other XPath 2.0 Solutions
	// "//*/text()[contains(lower-case(.),'test')]"
	// "//*/text()[matches(.,'test', 'i')]"
	// "//*/text()[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'test')]

	protected String getPageContent(String pagename) {
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
