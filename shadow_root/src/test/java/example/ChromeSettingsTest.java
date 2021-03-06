package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;

// Explore Chrome Setting screen
// this test methods will get skipped when run on Firefox
public class ChromeSettingsTest extends BaseTest {

	private final static String baseUrl = "chrome://settings/";
	private static String urlLocator = null;
	private static String shadowLocator = null;
	private static String shadow2Locator = null;
	private static List<WebElement> elements = new ArrayList<>();
	private static WebElement element = null;

	@Test
	public void test1() {
		Assume.assumeTrue(super.getBrowser().equals("chrome"));
		urlLocator = "#basicPage > settings-section[page-title=\"Search engine\"]";
		shadowLocator = "#card";
		driver.navigate().to(baseUrl);
		elements = shadowDriver.findElements(urlLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		System.err.println(
				String.format("Located %d %s elements:", elements.size(), urlLocator));
		// NOTE: default toString() is not be particularly useful
		elements.stream().forEach(System.err::println);
		elements.stream().map(o -> o.getTagName()).forEach(System.err::println);
		elements.stream()
				.map(o -> String.format("innerHTML: %s", o.getAttribute("innerHTML")))
				.forEach(System.err::println);
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(System.err::println);
	}

	@Test
	public void test2() {
		Assume.assumeTrue(browserChecker.testingChrome());
		urlLocator = "#basicPage > settings-section[page-title=\"Search engine\"]";
		shadowLocator = "#card";
		driver.navigate().to(baseUrl);
		element = shadowDriver.findElement(urlLocator);
		System.err.println(
				String.format("outerHTML: %s", element.getAttribute("outerHTML")));
		elements = shadowDriver.findElements(element, shadowLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		System.err.println(String.format("Found %d elements: ", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(System.err::println);
	}

	@Test
	public void test3() {
		Assume.assumeTrue(browserChecker.testingChrome());
		urlLocator = "#basicPage > settings-section[page-title=\"Default browser\"]";
		shadowLocator = "settings-default-browser-page";
		shadow2Locator = "div#canBeDefaultBrowser";
		driver.navigate().to(baseUrl);
		element = shadowDriver.findElement(urlLocator);
		System.err.println(
				String.format("outerHTML: %s", element.getAttribute("outerHTML")));
		try {
			/*
			 * 
			 * actions.moveToElement(element).build().perform(); sleep(1000);
			 * actions.click().build().perform(); sleep(1000);
			 */
			element.click();
			sleep(1000);
		} catch (ElementClickInterceptedException e) {
			System.err.println("Exception (ignored): " + e.getMessage());
			// element click intercepted: Element is not clickable at point
		}
		// shadowLocator = "*";
		// anything! - does not work either
		try {
			WebElement element2 = shadowDriver.findElement(element, shadowLocator);
			assertThat(element2, notNullValue());
			WebElement element3 = shadowDriver.findElement(element2, shadow2Locator);
			assertThat(element3, notNullValue());
		} catch (ElementNotVisibleException e) {
			System.err.println("Exception (ignored): " + e.getMessage());
			// Element with CSS settings-default-browser-page is not present on screen
		}
	}
}
