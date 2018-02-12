package com.github.sergueik.selenium;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
		Predicate<WebElement> byFollowingNodeText = element -> {
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
		};
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(byFollowingNodeText).collect(Collectors.toList());
		// Assert
		assertThat(elements.size(), is(6));
		System.err.println("Found elements: " + elements.size());
		// BaseTest.highlight is not static - cannot use method reference
		elements.stream().forEach(e -> highlight(e));
		elements.stream().map(e -> e.getText()).forEach(System.err::println);
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
		Predicate<WebElement> precedingElementText = element -> {
			if (pattern.matcher(element.getText()).find()) {
				return false;
			}
			try {
				// find the preceding element with specific text
				// NOTE: one has to collect all preceding elements and inspect every
				// one
				// of them
				List<WebElement> precedingElements = element
						.findElements(By.xpath(String
								.format("../preceding::tr/td[contains(text(), '%s')]", text)));
				for (WebElement precedingElement : precedingElements) {
					if (pattern.matcher(precedingElement.getText()).find()) {
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
		};
		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(precedingElementText).collect(Collectors.toList());
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
		Predicate<WebElement> countPrecedingElementsWithText = element -> {
			if (pattern.matcher(element.getText()).find()) {
				return false;
			}
			// Count the number of preceding elements starting with specific text
			List<WebElement> precedingElements = element.findElements(By.xpath(String
					.format("../preceding::tr/td[starts-with(text(), '%s')]", text)));
			return (boolean) (precedingElements.size() == 0);
		};

		List<WebElement> elements = driver
				.findElements(By.xpath("//*[@id=\"example\"]/tbody/tr/td[2]")).stream()
				.filter(countPrecedingElementsWithText).collect(Collectors.toList());

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