package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * see also other practice sites https://techlisticspace.blogspot.com/2016/06/top-6-demo-websites-for-practice.html
 * https://www.utest.com/articles/best-websites-to-practice-test-automation-using-selenium-webdriver
 * https://www.ultimateqa.com/best-test-automation-websites-to-practice-using-selenium-webdriver/
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class Way2AutomationTest extends BaseTest {

	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.beforeMethod(method);
		// ?
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error(s) in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void test0_4() {
		// Arrange
		// http://way2automation.com/way2auto_jquery/dropdown.php

		driver
				.get("http://way2automation.com/way2auto_jquery/dropdown/default.html");
		// Wait page to load
		WebElement element = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//select"))));
		assertThat(element, notNullValue());
		// Act
		String selectText = "Angola";
		Select elementSelect = new Select(element);
		elementSelect.selectByVisibleText(selectText);
		Predicate<WebElement> textCheck = _element -> {
			String _selected = _element.getAttribute("selected");
			String _text = _element.getText();
			System.err.println(
					"in stream filter (3): Text = " + _text + " selected: " + _selected);
			return _selected != null && Boolean.parseBoolean(_selected);
		};
		WebElement selectedElement = null;
		try {
			selectedElement = wait.until(new ExpectedCondition<WebElement>() {

				@Override
				public WebElement apply(WebDriver d) {
					Optional<WebElement> e = d
							.findElements(By.cssSelector("select > option")).stream()
							.filter(textCheck).findFirst();
					return (e.isPresent()) ? e.get() : (WebElement) null;
				}
			});
		} catch (TimeoutException e) {
			// none of the select elements will have "selected" attribute set.
		}

		if (selectedElement != null) {
			assertThat("selected element text check",
					selectedElement.getAttribute("innerHTML"),
					containsString(selectText));
			System.err.println(
					"selected element: " + selectedElement.getAttribute("innerHTML"));
		}
		WebElement firstSelectedOption = elementSelect.getFirstSelectedOption();
		System.out.println(firstSelectedOption.getText()); // prints "Option"
		assertThat("selected option check", firstSelectedOption.getText(),
				is(selectText)); // case

		element = wait.until(ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//select/option[@selected]"))));
		assertThat(element, notNullValue());
		assertThat("selected option is displayed check", element.isDisplayed(),
				is(true));

		element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.xpath("//select/option[normalize-space(text()) ='Albania']"))));
		assertThat(element, notNullValue());

		assertThat("non-selected option is not selected check",
				element.isSelected(), is(false));
		// NOTE: the following fails: the non-selected option will still be reported displayed
		// assertThat("non-selected option is not displayed check",
		// element.isDisplayed(), is(false));

	}
	// based on:
	// https://saucelabs.com/resources/articles/selenium-tips-css-selectors
}
