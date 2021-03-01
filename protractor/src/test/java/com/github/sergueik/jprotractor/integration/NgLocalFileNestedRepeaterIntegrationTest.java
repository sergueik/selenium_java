package com.github.sergueik.jprotractor.integration;

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

	// @Ignore
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
			data = ngItemElement.evaluate("item.name");
			text = data.toString();
			System.err.println(String.format("%s has children:", text));
			try {
				ngElement = ngItemElement.findElement(NgBy.binding("item.name"));
				assertThat(ngElement, notNullValue());
				System.err.println("item.name ng element text: " + ngElement.getText());
				element = ngElement.getWrappedElement();
				assertThat(element, notNullValue());
				System.err.println("item.name element: " + element.getText());
			} catch (java.lang.AssertionError e2) {
				System.err.println("Exception (ignored): " + e2.toString());

			} catch (Exception e3) {
				System.err.println("Exception (ignored): " + e3.toString());
			}

			List<WebElement> childrenElements = ngItemElement
					.findElements(NgBy.repeater("child in item.children"));
			for (WebElement childElement : childrenElements) {
				NgWebElement ngChildElement = new NgWebElement(ngDriver, childElement);

				data = ngChildElement.evaluate("child.name");
				text = data.toString();
				System.err.println(String.format("%s has grand-children:", text));
				/*
								ngElement = ngChildElement.findElement(NgBy.binding("child.name"));
								System.err.println(
										String.format("%s has grand-children:", ngElement.getText()));
										*/
				List<WebElement> grandchildrenElements = ngChildElement
						.findElements(NgBy.repeater("grand_child in child.children"));
				for (WebElement grandchildElement : grandchildrenElements) {
					NgWebElement ngGrandchildElement = new NgWebElement(ngDriver,
							grandchildElement);
					/*
					element = ngGrandchildElement
							.findElement(NgBy.binding("grand_child.name"));
					System.err.println(String.format("name: ", element.getText()));	
											*/
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
