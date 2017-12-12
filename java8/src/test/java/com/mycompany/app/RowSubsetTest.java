package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
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

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class RowSubsetTest extends BaseTest {

	private static String baseURL = "https://datatables.net/extensions/rowgroup/examples/initialisation/customRow.html";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String text = "Developer";
	private static Pattern pattern = Pattern.compile(String.format("^%s", text));
	private static final String browser = "chrome";

	@AfterSuite
	public static void tearDown() throws Exception {
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseURL);
	}

	// collect column data from all rows until the 'Developer' in that column #2
	@Test(enabled = true)
	public void test1() {
		// Arrange
		wait.until(e -> e.findElement(By.cssSelector("#example")));
		// Act
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(element -> {
					if (element.getText().matches(text)) {
						return false;
					}
					try {
						// find the following element with specific text
						element.findElement(By.xpath(String
								.format("../following::tr/td[contains(text(), '%s')]", text)));
						return true;
					} catch (NoSuchElementException ex) {
						return false;
					}
				}).collect(Collectors.toList());
		// Assert
		assertThat(elements.size(), is(6));
		System.err.println("Found elements: " + elements.size());
		elements.stream().forEach(e -> highlight(e));
		elements.stream().forEach(e -> System.err.println(e.getText()));
		// will be 6 - should be 5
	}

	// collect column data from all rows until the 'Developer' in that column #2
	// same test refactored /
	// test1,2 fail to achieve result

	@Test(enabled = true)
	public void test2() {
		// Arrange
		wait.until(e -> e.findElement(By.cssSelector("#example")));
		// Act
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(
						element -> (boolean) (element
								.findElements(By.xpath(String.format(
										"../following::tr/td[contains(text(), '%s')]", text)))
								.size() >= 1))
				.filter(element -> {
					List<WebElement> followingElements = element
							.findElements(By.xpath(String.format(
									"../following::tr/td[contains(text(), '%s')]", text)));
					System.err.println("element: " + element.getText());
					for (WebElement followingElement : followingElements) {
						System.err
								.println("Following element: " + followingElement.getText());
					}
					return (boolean) (followingElements.size() >= 1);
				}

				).collect(Collectors.toList());
		// Assert
		assertThat(elements.size(), is(7));
		// will be 7 - should be 5
		System.err.println("Found elements: " + elements.size());
		elements.stream().forEach(e -> highlight(e));
		elements.stream().forEach(e -> System.err.println(e.getText()));
	}

	@Test(enabled = true)
	public void test3() {
		// Arrange
		wait.until(e -> e.findElement(By.cssSelector("#example")));
		// Act
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]"));
		List<WebElement> elementsFiltered = new ArrayList<>();
		boolean foundText = false;
		for (WebElement element : elements) {
			List<WebElement> followingElements = element.findElements(By.xpath(
					String.format("../following::tr/td[contains(text(), '%s')]", text)));
			System.err.println("element: " + element.getText());
			for (WebElement followingElement : followingElements) {
				System.err.println("Following element: " + followingElement.getText());
			}
			foundText = (boolean) ((followingElements.size() >= 1)
					&& !element.getText().matches(text));
			if (foundText) {
				elementsFiltered.add(element);
			} else {
				break;
			}
		}
		// Assert
		assertThat(elementsFiltered.size(), is(5));
		System.err.println("Found elements: " + elementsFiltered.size());
		elementsFiltered.stream().forEach(e -> highlight(e));
		elementsFiltered.stream().forEach(e -> System.err.println(e.getText()));
	}

	// NOTE: if the node name is changing
	// (this is possible to test with XPath)
	// a much shorter solution is available
	// "//parent-node-name/node-name[not(preceding::alternative-node-name)]"
	@Test(enabled = true)
	public void test4() {
		// Arrange
		wait.until(e -> e.findElement(By.cssSelector("#example")));
		// Act
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(_element -> {
					if (pattern.matcher(_element.getText()).find()) {
						return false;
					}
					try {
						// find the preceding element with specific text
						// NOTE: one has to collect all preceding elements and inspect every
						// one
						// of them
						List<WebElement> _precedingElements = _element
								.findElements(By.xpath(String.format(
										"../preceding::tr/td[contains(text(), '%s')]", text)));
						for (WebElement _precedingElement : _precedingElements) {
							if (pattern.matcher(_precedingElement.getText()).find()) {
								return false;
							} else {
								// continue
							}
						}
						return true;
					} catch (NoSuchElementException ex) {
						// no preceding elements
						return true;
					}
				}).collect(Collectors.toList());
		// Assert
		assertThat(elements.size(), is(5));
		/*
		System.err.println("Found elements: " + elements.size());
		elements.stream().forEach(e -> highlight(e));
		elements.stream().forEach(e -> System.err.println(e.getText()));
		*/
	}

	@Test(enabled = true)
	public void test5() {
		// Arrange
		wait.until(e -> e.findElement(By.cssSelector("#example")));
		// Act
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(_element -> {
					if (pattern.matcher(_element.getText()).find()) {
						return false;
					}
					// Count the number of preceding elements starting with specific text
					List<WebElement> _precedingElements = _element
							.findElements(By.xpath(String.format(
									"../preceding::tr/td[starts-with(text(), '%s')]", text)));
					return (boolean) (_precedingElements.size() == 0);
				}).collect(Collectors.toList());

		// Assert
		System.err.println("Found elements: " + elements.size());
		elements.stream().forEach(e -> highlight(e));
		elements.stream().forEach(e -> System.err.println(e.getText()));
		assertThat(elements.size(), is(5));
		/*
		System.err.println("Found elements: " + elements.size());
		elements.stream().forEach(e -> highlight(e));
		elements.stream().forEach(e -> System.err.println(e.getText()));
		*/
	}

}