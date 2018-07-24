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
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.Nullable;

/**
 * Selected test scenarios for Selenium WebDriver
 * based on:  https://testerslittlehelper.wordpress.com/
 * 
 * use XPath ancestor navigation to manipulate heavily styled page.
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class XPathNavigationTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager
			.getLogger(XPathNavigationTest.class);

	private static String baseURL = "about:blank";

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
	}

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void test1() {
		// does not work very  with headless browser
		// Arrange
		String baseURL = "https://spb.rt.ru/packages/tariffs";
		driver.get(baseURL);
		List<WebElement> elements = new ArrayList<>();
		elements = driver.findElements(By.cssSelector("*[data-fee]"));

		List<String> fees = elements.stream().map(_e -> _e.getAttribute("data-fee"))
				.collect(Collectors.toList());
		fees.stream().forEach(System.err::println);
		List<WebElement> buttons = fees.stream()
				.filter(fee -> Integer.parseInt(fee) > 0).map(fee -> {
					String xpath = String.format(
							"//*[@data-fee='%s']/ancestor::div[contains(@class,'js-price')]//a[contains(@class, 'button')]",
							fee);
					WebElement buttonElement = null;
					try {
						buttonElement = driver.findElement(By.xpath(xpath));
						executeScript(
								"arguments[0].scrollIntoView({ behavior: \"smooth\" });",
								buttonElement);
						highlight(buttonElement.findElement(By.xpath("..")));
						// System.err.println(buttonElement.getAttribute("outerHTML"));
						// System.err.println(buttonElement.findElement(By.xpath("..")).getAttribute("outerHTML"));
						System.err.println(String.format("Connection fee: %s", fee));
						// NOTE: funny console output of cyrillic word:
						// Подключить
						// чить
						// ь
						System.err.println(
								String.format("Button Text: |%s|", buttonElement.getText()));
						System.err.println(xpathOfElement(buttonElement));
					} catch (Exception e) {
						// temporarily catch all exceptions.
						System.err.println("Exception: " + e.toString());
					}
					return buttonElement;
				}).collect(Collectors.toList());
	}

	// a debug version of test1.
	// NOTE: slower
	@Test(enabled = false)
	public void test2() {
		// Arrange
		String baseURL = "https://spb.rt.ru/packages/tariffs";
		driver.get(baseURL);
		List<WebElement> elements = new ArrayList<>();
		elements = driver.findElements(By.cssSelector("*[data-fee]"));

		List<WebElement> buttons = elements.stream().map(_element -> {
			String fee = _element.getAttribute("data-fee");
			WebElement containerElement = null;
			WebElement buttonElement = null;
			if (Integer.parseInt(fee) > 0) {
				String xpath = String
						.format("ancestor::div[contains(@class,'js-price')]", fee);
				try {
					containerElement = _element.findElement(By.xpath(xpath));
					if (containerElement != null) {

						// System.err.println("Container element: "
						// + containerElement.getAttribute("innerHTML"));
						try {
							buttonElement = containerElement
									.findElement(By.cssSelector("a[class *= 'button']"));
							if (buttonElement != null) {
								executeScript(
										"arguments[0].scrollIntoView({ behavior: \"smooth\" });",
										buttonElement);
								highlight(buttonElement.findElement(By.xpath("..")));
								System.err.println(String.format("Connection fee: %s", fee));
								System.err.println(String.format("Button Text: |%s|",
										buttonElement.getText()));
								System.err.println(cssSelectorOfElement(buttonElement));

							}
						} catch (TimeoutException e2) {
							System.err.println(
									"Exception finding the button element: " + e2.toString());
						}
					}
				} catch (TimeoutException e1) {
					System.err.println(
							"Exception finding the container element: " + e1.toString());
				}
			}
			return buttonElement;
		}).collect(Collectors.toList());
	}

	// https://habr.com/company/ruvds/blog/416539/
	// https://developer.mozilla.org/en-US/docs/Web/API/Element/closest
	@Test(enabled = false)
	public void test3() {
		// Arrange
		String baseURL = "https://spb.rt.ru/packages/tariffs";
		driver.get(baseURL);

		List<WebElement> elements = new ArrayList<>();
		elements = driver.findElements(By.cssSelector("*[data-fee]"));

		List<String> fees = elements.stream().map(_e -> _e.getAttribute("data-fee"))
				.collect(Collectors.toList());
		fees.stream().forEach(System.err::println);
		fees.stream().filter(fee -> Integer.parseInt(fee) > 0).forEach(fee -> {
			String xpath = String.format("//*[@data-fee='%s']", fee);
			WebElement element = driver.findElement(By.xpath(xpath));

			boolean debug = false;
			List<String> scripts = new ArrayList<>();
			if (debug) {
				scripts = new ArrayList<>(Arrays.asList(new String[] {
						// immediate ancestor, not the one test is looking for, but
						// helped finding the following one
						"var element = arguments[0];\n"
								+ "var locator = 'div.tariff-desc__cost_m-cell';"
								+ "var targetElement = element.closest(locator);\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.outerHTML;",
						// next in the ancestor chain, located and printed the outerHTML of
						// element for debugging purposes
						"var element = arguments[0];\n"
								+ "var locator = 'div.tariff-desc__cost.tariff-desc__cost_reset.js-price-blocks';"
								+ "var targetElement = element.closest(locator);\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.outerHTML;",
						// relevant ancestor chain, chained with a quesySelector call
						// but with full classes making it hard to read and fragile
						"var element = arguments[0];\n"
								+ "var locator = 'div.tariff-desc__cost.tariff-desc__cost_reset.js-price-blocks';"
								+ "var targetElement = element.closest(locator).querySelector('a.button-3');\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.innerHTML;",
						// final selector
						"var element = arguments[0];\n"
								+ "var locator = 'div.js-price-blocks';"
								+ "var targetElement = element.closest(locator).querySelector('a.button-3');\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.innerHTML;" }));
				for (String script : scripts) {
					System.err.println("Running the script:\n" + script);
					try {
						String result = (String) js.executeScript(script, element);
						System.err.println("Found:\n" + result);
						// assertThat(result, equalTo("text to find"));
					} catch (Exception e) {
						// temporarily catch all exceptions.
						System.err.println("Exception: " + e.toString());
					}
				}
			} else {
				// convert to function
				String script = "var element = arguments[0];\n"
						+ "var ancestorLocator = arguments[1];"
						+ "var targetElementLocator = arguments[2];"
						+ "/* alert('ancestorLocator = ' + ancestorLocator); */"
						+ "var targetElement = element.closest(ancestorLocator).querySelector(targetElementLocator);\n"
						+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
						+ "return targetElement.text;";
				try {
					System.err.println("Running the script:\n" + script);
					String result = (String) js.executeScript(script, element,
							"div.js-price-blocks", "a.button-3");
					System.err.println("Found:\n" + result);
					// assertThat(result, equalTo("text to find"));
				} catch (Exception e) {
					// temporarily catch all exceptions.
					System.err.println("Exception: " + e.toString());
				}

			}
		});
	}

	// https://habr.com/company/ruvds/blog/416539/
	// https://developer.mozilla.org/en-US/docs/Web/API/Element/closest
	@Test(enabled = true)
	public void test4() {
		String baseURL = "http://store.demoqa.com/products-page/";
		driver.get(baseURL);

		// Arrange
		List<WebElement> elements = new ArrayList<>();
		elements = driver
				.findElements(By.cssSelector("span.currentprice:nth-of-type(1)"));
		elements.stream().forEach(element -> {
			executeScript("arguments[0].scrollIntoView({ behavior: \"smooth\" });",
					element);
			highlight(element, 1000);
			boolean debug = false;
			if (debug) {
				List<String> scripts = new ArrayList<>(Arrays.asList(new String[] {
						// immediate ancestor, not the one test is looking for, but
						// helped finding the following one
						"var element = arguments[0];\n"
								+ "var locator = 'div.wpsc_product_price';"
								+ "var targetElement = element.closest(locator);\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.outerHTML;",
						// next in the ancestor chain, located and printed the outerHTML of
						// element for debugging purposes
						"var element = arguments[0];\n" + "var locator = 'form';\n"
								+ "var targetElement = element.closest(locator);\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.outerHTML;",
						// relevant ancestor chain, chained with a quesySelector call
						// but with full classes making it hard to read and fragile
						"var element = arguments[0];\n"
								+ "var ancestorLocator = 'div.productcol';"
								+ "var targetElementLocator = 'div[class=\"input-button-buy\"]';"
								+ "var targetElement = element.closest(ancestorLocator).querySelector(targetElementLocator);\n"
								+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
								+ "return targetElement.innerHTML;" }));
				for (String script : scripts) {
					System.err.println("Running the script:\n" + script);
					try {
						String result = (String) js.executeScript(script, element);
						System.err.println("Found:\n" + result);
						// assertThat(result, equalTo("text to find"));
					} catch (Exception e) {
						// temporarily catch all exceptions.
						System.err.println("Exception: " + e.toString());
					}
				}
			} else {
				String script = "var element = arguments[0];\n"
						+ "var ancestorLocator = arguments[1];"
						+ "var targetElementLocator = arguments[2];"
						+ "/* alert('ancestorLocator = ' + ancestorLocator); */"
						+ "var targetElement = element.closest(ancestorLocator).querySelector(targetElementLocator);\n"
						+ "targetElement.scrollIntoView({ behavior: 'smooth' });\n"
						+ "return targetElement.text || targetElement.getAttribute('value');";
				try {
					System.err.println("Running the script:\n" + script);
					String result = (String) js.executeScript(script, element, "form",
							"input[type='submit']");
					System.err.println("Found:\n" + result);
					assertTrue(result.equalsIgnoreCase("add to cart"), result);
				} catch (Exception e) {
					// temporarily catch all exceptions.
					System.err.println("Exception: " + e.toString());
				}
			}
		});
	}

}
