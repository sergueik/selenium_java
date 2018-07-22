package com.github.sergueik.selenium;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.Nullable;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class EscapeXPathTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(EscapeXPathTest.class);
	private List<String> texts = new ArrayList<>(
			Arrays.asList(new String[] { "Burj Khalifa", "\"Burj\" 'Khalifa'",
					"\"Burj\" Khalifa", "Burj 'Khalifa'" }));

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
		List<WebElement> elements = new ArrayList<>();

		for (String text : texts) {
			System.err.println(
					String.format("test1 (1): %s => %s ", text, escapeXPath(text)));
			System.err.println(
					String.format("test1 (2): %s => %s ", text, escapeXPath2(text)));
			/*			System.err.println(
								String.format("test1 (2): %s => %s ", text, escapeXPath3(text)));
			*/
			try {
				elements = driver.findElements(By.xpath(
						String.format("//*[contains(text(), %s)]", escapeXPath(text))));
				assertTrue(elements.size() > 0);
				highlight(elements.get(0));
				elements.clear();
				elements = driver.findElements(By.xpath(
						String.format("//*[contains(text(), %s)]", escapeXPath2(text))));
				assertTrue(elements.size() > 0);
				highlight(elements.get(0));
				elements.clear();
				/*	elements = driver.findElements(By.xpath(
								String.format("//*[contains(text(), %s)]", escapeXPath3(text))));
						assertTrue(elements.size() > 0);
						highlight(elements.get(0));
						elements.clear();
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
		List<WebElement> elements = new ArrayList<>();

		for (String text : texts) {
			System.err.println(String.format("test2: %s", text));

			try {
				elements = driver.findElements(
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

		for (String text : texts) {
			System.err.println(String.format("test3: %s", text));

			Predicate<WebElement> textCheck = o -> {
				String t = o.getText();
				// System.err.println("in stream filter (3): Text = " + t);
				return (Boolean) (t.contains(text));
			};
			Optional<WebElement> element = driver
					.findElements(By.cssSelector("th[scope='row']")).stream()
					.filter(textCheck).findFirst();

			assertTrue(element.isPresent());
			highlight(element.get());
			sleep(500);
		}
	}

	// https://sqa.stackexchange.com/questions/362/a-way-to-match-on-text-using-css-locators
	// NOTE: uses jQuery
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
		return String.format("0x%02x", b);
	}

	// http://utf8-chartable.de/unicode-utf8-table.pl?start=8064&names=-&utf8=0x
	private String replaceSpecials() {
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

}
