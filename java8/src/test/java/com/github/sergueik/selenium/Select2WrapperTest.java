package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

/*
 * This class tests the Select 2 Element https://select2.github.io/examples.html
 * intended to create a custom class implementing subset of ISelect interface
 * influenced by [selenium select2 wrapper project by sskorol](https://github.com/sskorol/sslect2-wrapper)
 * https://qa-automation-notes.blogspot.com/2015/11/webdriver-vs-select2.html
*/

public class Select2WrapperTest extends BaseTest {

	private static String baseURL = "https://select2.github.io/examples.html";
	private static final StringBuffer verificationErrors = new StringBuffer();

	private static final String selectOptionScript = "var selector = arguments[0]; var o_val = arguments[1]; var s2_obj = $(selector).select2(); option = s2_obj.val(o_val); option.trigger('select');";
	private static final String querySelectedValueScript = "var selector = arguments[0]; var s2_obj = $(selector).select2(); return s2_obj.val();";
	private static final String selectByVisibleTextScript = "var selector = arguments[0]; var s2_obj = $(selector).select2(); var text = arguments[1]; var found_opt = s2_obj.find('option:contains(\"' + text + '\")').val(); return found_opt";
	private static final String selectMultiOptionClearScript = "var selector = arguments[0]; $(selector).select2().val(null).trigger('change');";

	@Test(enabled = true)
	public void selectByOptionValueTest() {
		String selectOption = "FL";
		String select2Locator = "select.js-states";
		// Arrange
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(select2Locator))));
		// Act

		executeScript(selectOptionScript, select2Locator, selectOption);
		// TODO: appears without running the second script the result is not updated
		String result = (String) executeScript(querySelectedValueScript,
				select2Locator);
		System.err.println(
				String.format("Result of selecting by the option value (%s): %s",
						selectOption, result));

		sleep(150);
		// Assert
		// TODO: Update the Expectation condition with Iterator and String methods
		WebElement selectionElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.selection span.select2-selection span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		assertTrue(selectionElement.isDisplayed());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	@Test(enabled = true)
	public void selectByVisibleOptionTextTest() {
		// Arrange
		String select2Locator = "select.js-states";
		String selectVisible = "Florida";
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(select2Locator))));
		// Act
		String result = (String) executeScript(selectByVisibleTextScript,
				select2Locator, selectVisible);
		System.err.println(String.format(
				"Result of selecting via visible text(%s): %s", selectVisible, result));

		sleep(150);
		// Assert
		// TODO: Update the Expectation condition with Iterator and String methods
		WebElement selectionElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.selection span.select2-selection span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	// multiple
	@Test(enabled = true)
	public void selectBySelectWrapperObjectMultipleOptionsTest() {
		// Arrange
		String select2Locator = "select.js-example-basic-multiple.js-states";
		Select2Object element = new Select2Object(driver, select2Locator);
		// Act
		highlight(element.getWrappedElement());
		// Assert
		assertThat(element, notNullValue());
		// Act
		Map<String, String> stateMap = new HashMap<>();
		stateMap.put("Florida", "FL");
		stateMap.put("Washington", "WA");
		Iterator<String> stateiIterator = stateMap.keySet().iterator();
		while (stateiIterator.hasNext()) {
			String stateFullName = stateiIterator.next();
			String result = element.selectByVisibleText(stateFullName);
			// Assert
			assertTrue(result.equals(stateMap.get(stateFullName)),
					String.format("State %s code should be %s but was %s", stateFullName,
							stateMap.get(stateFullName), result));
			System.err.println("Result: " + result);

			sleep(150);
		}
	}

	@Test(enabled = true)
	public void selectBySelectWrapperObjectTest() {
		// Arrange
		String select2Locator = "select.js-states";
		Select2Object element = new Select2Object(driver, select2Locator);
		// Act
		highlight(element.getWrappedElement());
		// Assert
		assertThat(element, notNullValue());
		// Act
		Map<String, String> stateMap = new HashMap<>();
		stateMap.put("Florida", "FL");
		stateMap.put("Washington", "WA");
		Iterator<String> stateiIterator = stateMap.keySet().iterator();
		while (stateiIterator.hasNext()) {
			String stateFullName = stateiIterator.next();
			String result = element.selectByVisibleText(stateFullName);
			// Assert
			assertTrue(result.equals(stateMap.get(stateFullName)),
					String.format("State %s code should be %s but was %s", stateFullName,
							stateMap.get(stateFullName), result));
			System.err.println("Result: " + result);

			sleep(150);
		}
	}

	@Test(enabled = true)
	public void select2VisualVerifyTest1() {
		String selectOption = "FL";
		String select2Selector = "select.js-example-basic-single.js-states";

		// Arrange
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(select2Selector))));
		// Act
		executeScript(selectOptionScript, select2Selector, selectOption);
		String result = (String) executeScript(querySelectedValueScript,
				select2Selector);
		// Assert
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected via Javascript: " + result);
		sleep(150);
		// Act
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();

		sleep(150);
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));
		List<WebElement> options = resultsElement
				.findElements(By.cssSelector("li.select2-results__option"));
		// available options
		// options.stream().forEach(o ->
		// System.err.println(o.getText()));
		WebElement highlightedOption = options.stream()
				.filter(o -> o.getAttribute("class").contains("highlighted"))
				.findFirst().get();
		System.err.println(
				"Selected options visible text: " + highlightedOption.getText());
		highlight(highlightedOption);
		highlightedOption.click();

		sleep(150);
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver
						.findElement(By.cssSelector("span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection rendered: " + selectionElement.getText());
	}

	// temporarily broken
	@Test(enabled = false)
	public void select2VisualVerifyTest2() {
		List<String> selectOptions = new ArrayList<>(
				Arrays.asList(new String[] { "FL", "NC", "MN" }));
		String select2Selector = "select.js-example-basic-multiple.js-states";

		String divText = "Multiple select boxes";

		// Arrange
		WebElement selectElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(select2Selector))));
		List<WebElement> divElements = driver.findElements(By.xpath(String
				.format("//h2[@id = 'multiple'][contains(text(), '%s')]", divText)));
		assertTrue(divElements.size() > 0);
		WebElement divElement = divElements.get(0);
		actions.moveToElement(divElement).build().perform();
		highlight(divElement);

		WebElement spanElement = selectElement
				.findElement(By.xpath("following-sibling::span"));
		actions.moveToElement(spanElement).build().perform();
		highlight(spanElement);

		// Act
		for (String selectOption : selectOptions) {
			executeScript(selectOptionScript, select2Selector, selectOption);
			@SuppressWarnings("unchecked")
			List<String> results = (ArrayList<String>) executeScript(
					querySelectedValueScript, select2Selector);
			System.err.println("size = " + results.size());
			String result = results.get(0);
			// Assert
			assertTrue(result.equals(selectOption), String
					.format("State code should be %s but was %s", selectOption, result));
			System.err.println("Selected via Javascript: " + result);
		}

		sleep(5000);
		// Act
		// following-sibling js-example-basic-multiple js-states
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection--multiple"));
		highlight(arrowElement);
		arrowElement.click();

		sleep(150);
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));
		List<WebElement> optionElements = resultsElement
				.findElements(By.cssSelector("li.select2-results__option"));
		// available option Elements
		// optionElements.stream().forEach(o ->
		// System.err.println(o.getText()));
		WebElement highlightedOption = optionElements.stream()
				.filter(o -> o.getAttribute("class").contains("highlighted"))
				.findFirst().get();
		System.err.println(
				"Selected options visible text: " + highlightedOption.getText());
		highlight(highlightedOption);
		// highlightedOption.click();

		sleep(150);
		// Assert
		WebElement selectionElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.select2-selection--multiple ul.select2-selection__rendered li.select2-selection__choice"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection rendered: " + selectionElement.getText());
	}

	// TODO: refactor
	@Test(enabled = true)
	public void select2VisualActionVerify2Test() {
		String selectOption = "FL";

		// Arrange
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.cssSelector("select.js-example-basic-single.js-states"))));
		// Act
		String result = (String) executeScript(selectByVisibleTextScript,
				"select.js-states", "Florida");
		System.err.println("Selected via Javascript: " + result);
		// Assert
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected via Javascript: " + result);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Act
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();

		sleep(150);
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));

		WebElement highlightedOption = resultsElement
				.findElements(By.cssSelector("li.select2-results__option")).stream()
				.filter(o -> o.getAttribute("class").contains("highlighted"))
				.findFirst().get();
		System.err.println("Selected Text: " + highlightedOption.getText());
		highlight(highlightedOption);
		highlightedOption.click();

		sleep(150);
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver
						.findElement(By.cssSelector("span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	@Test(enabled = false)
	public void select2VisualActionSelectTest() {
		String selectOption = "MN";

		// Arrange
		WebElement select2 = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("select.js-example-theme-single.js-states"))));
		actions.moveToElement(select2).build().perform();
		// Act
		WebElement arrowElement = driver.findElement(
				By.cssSelector("div.s2-example span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();
		// TODO: convert to flexible

		sleep(150);
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));
		System.err.println(resultsElement.getAttribute("innerHTML"));
		// TODO: scroll somehow
		// TODO: filter out role="group"
		WebElement selectedOption = resultsElement
				.findElements(By.cssSelector("li.select2-results__option")).stream()
				.filter(o -> o.getAttribute("role").matches("treeitem")).filter(o -> {
					String text = o.getText();
					System.err.println(String.format("Option text: \"%s\"", text));
					actions.moveToElement(o).build().perform();
					if (text.contains("Minnesota")) {
						return true;
					} else {
						return false;
					}
				}).findFirst().get();
		System.err.println("Selected Visible Text: " + selectedOption.getText());
		highlight(selectedOption);
		actions.moveToElement(selectedOption).click().build().perform();

		sleep(5000);

		// Assert
		String result = (String) executeScript(querySelectedValueScript,
				"select.js-example-theme-single.js-states");
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected: " + result);
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.cssSelector("div.s2-example span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getAttribute("title"));
	}

	@AfterSuite
	public void afterTest() {
		try {
			driver.close();
		} catch (WebDriverException e) {
			// NOTE: the inner
			// exception java.net.ConnectException is never thrown by driver
			System.err.println("Exception (ignored) " + e.toString());
		}
		try {
			driver.quit();
		} catch (WebDriverException e) {
			// NOTE: the inner
			// exception java.net.ConnectException is never thrown by driver
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	// inspired by:
	// https://github.com/sskorol/select2-wrapper/blob/master/src/main/java/com/tools/qaa/elements/Select2.java
	// https://github.com/SeleniumHQ/selenium/blob/master/java/client/src/org/openqa/selenium/support/ui/Select.java
	public static class Select2Object {

		private WebDriver driver = null;
		private WebElement element;
		private WebDriverWait wait;
		private boolean isMulti = false;
		private String elementCssSelector;
		private int flexibleWait = 5;
		private long pollingInterval = 500;

		public WebElement getWrappedElement() {
			return this.element;
		}

		public Select2Object(WebDriver driver, String elementCssSelector) {
			this.elementCssSelector = elementCssSelector;
			this.driver = driver;
			this.wait = new WebDriverWait(driver, this.flexibleWait);
			this.wait.pollingEvery(this.pollingInterval, TimeUnit.MILLISECONDS);
			this.element = wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
		}

		public String selectByVisibleText(final String text) {
			String selectByVisibleTextScript = "var s2_obj = $('"
					+ this.elementCssSelector
					+ "').select2(); var text = arguments[0] ; var found_opt = s2_obj.find('option:contains(\"' + text + '\")').val(); return found_opt";
			wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
			String optionValue = (String) executeScript(selectByVisibleTextScript,
					text);
			return optionValue;
		}

		public String getSelectedText() {
			wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
			// TODO:
			return (String) executeScript(
					"return $('" + this.elementCssSelector + "').select2('data').text;");
		}

		private Object executeScript(String script, Object... arguments) {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
						.cast(driver);
				return javascriptExecutor.executeScript(script, arguments);
			} else {
				throw new RuntimeException("Script execution failed.");
			}
		}
	}
}
