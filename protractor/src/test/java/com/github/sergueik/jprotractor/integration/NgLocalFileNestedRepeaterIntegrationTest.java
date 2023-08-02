package com.github.sergueik.jprotractor.integration;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static java.lang.Boolean.parseBoolean;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Local file Integration tests
 *
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgLocalFileNestedRepeaterIntegrationTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	public static String localFile;
	static StringBuilder sb;
	static Formatter formatter;
	private static String text = null;
	private static Object data = null;
	private static WebElement element;
	private static WebElement element2;
	private static WebElement element3;
	private static List<WebElement> elements = new ArrayList<>();
	private static List<WebElement> elements2 = new ArrayList<>();
	private static NgWebElement ngElement;
	private static final boolean debug = false;

	@BeforeClass
	public static void setup() throws IOException {
		sb = new StringBuilder();
		formatter = new Formatter(sb, Locale.US);
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Ignore
	// TODO: report in Angular Protractor / Angular bug that a DOM element between
	// repeater and binding is needed for the call to succeed:
	// <ul><li ng:repeat="item in data"><span>{{item.name}}</span></li>
	// searchable by repeater then by binding
	// <ul><li ng:repeat="item in data">{{item.name}}</li></ul>
	// is not
	@Test
	public void test1() {
		getPageContent("ng_nested_repeater.htm");
		List<WebElement> itemElements = ngDriver
				.findElements(NgBy.repeater("item in data"));
		assertThat(itemElements, notNullValue());
		assertThat(itemElements.size(), greaterThan(1));

		for (WebElement itemElement : itemElements) {
			if (debug)
				System.err
						.println("item element: " + itemElement.getAttribute("outerHTML"));

			// NOTE: the nested find by NGBy.binding is having NPE issues with the
			// result
			// probably a wrong type
			NgWebElement ngItemElement = new NgWebElement(ngDriver, itemElement);
			highlight(ngItemElement.getWrappedElement());
			data = ngItemElement.evaluate("item.name");
			text = data.toString();
			System.err.println(String.format("%s has children:", text));
			elements = itemElement.findElements(
					By.xpath("./child::node()[contains(text(), '{{item.name}}') ]"));
			// SyntaxError: The expression is not a legal expression.
			if (elements.size() != 0) {

				try {
					ngElement = ngItemElement.findElement(NgBy.binding("item.name"));
					assertThat(ngElement, notNullValue());
					System.err
							.println("item.name ng element text: " + ngElement.getText());
					element = ngElement.getWrappedElement();
					assertThat(element, notNullValue());
					System.err.println("item.name element: " + element.getText());
				} catch (java.lang.AssertionError e2) {
					System.err.println("Exception (ignored): " + e2.toString());

				} catch (Exception e3) {
					System.err.println("Exception (ignored): " + e3.toString());
				}
			}

			List<WebElement> childrenElements = ngItemElement
					.findElements(NgBy.repeater("child in item.children"));
			for (WebElement childElement : childrenElements) {
				NgWebElement ngChildElement = new NgWebElement(ngDriver, childElement);
				highlight(ngChildElement.getWrappedElement());

				data = ngChildElement.evaluate("child.name");
				text = data.toString();

				List<WebElement> grandchildrenElements = ngChildElement
						.findElements(NgBy.repeater("grand_child in child.children"));
				if (grandchildrenElements.size() == 0) {
					System.err.println(String.format("%s has no grand-children", text));
					continue;
				}
				System.err.println(String.format("%s has grand-children:", text));
				for (WebElement grandchildElement : grandchildrenElements) {
					NgWebElement ngGrandchildElement = new NgWebElement(ngDriver,
							grandchildElement);
					highlight(ngGrandchildElement.getWrappedElement());
					try {
						element = ngGrandchildElement
								.findElement(NgBy.binding("grand_child.name"));
						System.err.println(String.format("name: %s", element.getText()));
					} catch (java.lang.AssertionError e2) {
						System.err.println("Exception (ignored): " + e2.toString());

					} catch (Exception e3) {
						System.err.println("Exception (ignored): " + e3.toString());
					}
					if (debug)
						System.err.println("grand child element: "
								+ grandchildElement.getAttribute("outerHTML"));
					data = ngGrandchildElement.evaluate("grand_child.name");
					text = data.toString();
					if (text == null)
						text = ngGrandchildElement.getText();
					System.err.println("name: " + text);

				}
			}

		}
	}

	@Ignore
	@Test
	public void test2() {
		getPageContent("ng_local_service.htm");
		for (WebElement currentElement : ngDriver
				.findElements(NgBy.repeater("person in people"))) {
			if (currentElement.getText().isEmpty()) {
				break;
			}
			WebElement personName = new NgWebElement(ngDriver, currentElement)
					.findElement(NgBy.binding("person.Name"));
			assertThat(personName, notNullValue());
			highlight(personName);
			Object personCountry = new NgWebElement(ngDriver, currentElement)
					.evaluate("person.Country");
			assertThat(personCountry, notNullValue());
			System.err.println(personName.getText() + " " + personCountry.toString());
		}
	}

	// @Ignore
	@Test
	public void test5() {
		getPageContent("ng_nested_repeater2.htm");
		String payload = null;
		List<String> keyColumns = Arrays
				.asList(new String[] { "Chrome", "Firefox" });

		Map<String, String> data = new HashMap<>();
		for (WebElement element : ngDriver
				.findElements(NgBy.repeater("row in data"))) {
			payload = element.getText();
			if (payload.isEmpty()) {
				break;
			}
			System.err.println("Text of the element: " + payload);

			String[] lines = payload.split("\r?\n");
			int index = 0;
			for (index = 0; index != lines.length - 1; index++) {
				String line = lines[index];
				Stream<String> keyColumnStream = keyColumns.stream();
				// NOTE: cannot define keyColumnStream outside of the loop and use more
				// than once:
				// java.lang.IllegalStateException:
				// stream has already been operated upon or closed

				List<String> columns = keyColumnStream.filter(o -> line.contains(o))
						.collect(Collectors.toList());

				if (columns != null && columns.size() > 0) {
					String column = columns.get(0);
					data.put(column, lines[index + 1]);
				}
			}
			System.err.println("data collected: "
					+ data.entrySet().stream().map(e -> e.getKey() + " " + e.getValue())
							.collect(Collectors.toList()));
		}

	}

	@Ignore
	@Test
	public void test3() {
		getPageContent("ng_nested_repeater2.htm");
		for (WebElement element : ngDriver
				.findElements(NgBy.repeater("row in data"))) {
			if (element.getText().isEmpty()) {
				break;
			}
			System.err.println("Text of the element: " + element.getText());
			System.err.println("Scan child elements of " + element.getTagName());
			String binding = "row.name";
			ngElement = new NgWebElement(ngDriver, element);
			// NOTE: with "./descendant::node()" and "descendant::node()"
			// getting
			// InvalidSelectorError: The result of the xpath expression is : [object
			// Text]. It should be an element.
			elements = element.findElements(By.xpath(".//*[text()!='' ]"));
			boolean found = false;
			for (WebElement element2 : elements) {
				System.err.println("Descendant element text:\n" + element.getText());
				// NOTE: it will never contain *the* binding
				if (element.getText().contains(binding))
					found = true;
			}

			if (found) {
				System.err.println("Get data via bining search");
				element2 = ngElement.findElement(NgBy.binding(binding));
				assertThat(element2, notNullValue());
				highlight(element2);
				System.err
						.println(String.format("row name is: %s", element2.getText()));
			} else {
				System.err.println("Get data via eval");
				data = ngElement.evaluate(binding);
				assertThat(data, notNullValue());
				text = data.toString();
				System.err.println(String.format("row name is: %s", text));

			}
			elements2 = ngElement
					.findElements(NgBy.repeater("column in row.columns"));
			for (WebElement element3 : elements2) {
				NgWebElement ngElement = new NgWebElement(ngDriver, element3);
				highlight(ngElement.getWrappedElement());
				binding = "column.name";
				System.err.println("Scan child elements of " + element3.getTagName());
				elements = element3
						.findElements(By.xpath("./child::node()[text()!='' ]"));
				if (elements.size() != 0) {
					System.err.println("Get data via bining search");
					NgWebElement ngElement2 = ngElement
							.findElement(NgBy.binding(binding));
					assertThat(ngElement2, notNullValue());
					WebElement element2 = ngElement2.getWrappedElement();
					// NPE in the next line if not checked
					if (element2 != null) {
						try {
							System.err.println(
									String.format("column name is: %s", element2.getText()));
							highlight(element2);
						} catch (NullPointerException e) {
						}
					}
				} else {
					System.err.println("Get data via eval");
					data = ngElement.evaluate(binding);
					assertThat(data, notNullValue());
					text = data.toString();
					System.err.println(String.format("column name is: %s", text));
				}
			}
		}
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	// NOTE: a different implementation exists in CommonFunctions
	private static void getPageContent(String pagename) {
		String baseUrl = CommonFunctions.getPageContent(pagename);
		ngDriver.navigate().to(baseUrl);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

	private static String getIdentity(WebElement element) {
		String result = null;
		String script = "return angular.identity(angular.element(arguments[0])).html();";
		// returns too little HTML information in Java
		try {
			result = CommonFunctions.executeScript(script, element).toString();
		} catch (Exception e) {
		}
		return result;
	}

	private static void dragAndDrop(WebElement sourceElement,
			WebElement destinationElement) {
		String script = "simulateDragDrop.js";
		try {
			CommonFunctions.executeScript(CommonFunctions.getScriptContent(script),
					sourceElement, destinationElement);
		} catch (Exception e) {
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
