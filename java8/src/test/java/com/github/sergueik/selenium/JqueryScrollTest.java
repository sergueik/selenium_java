package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on
// https://stackoverflow.com/questions/4884839/how-do-i-get-an-element-to-scroll-into-view-using-jquery
public class JqueryScrollTest extends BaseTest {

	private String baseURL = "http://demos.flesler.com/jquery/scrollTo/old/";

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
	}

	@BeforeMethod
	public void loadPageInBrowser() {
		driver.get(baseURL);
		waitJQueryDone();
	}

	@Test(priority = 2, enabled = true)
	public void computeOffsetTest() {
		List<Integer> fragmentIds = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7));
		for (int fragmentId : fragmentIds) {
			WebElement fragmentElement = null;
			String fragmentLocator = String.format("#y > ul > li:nth-child(%d) > a",
					fragmentId);
			Map<String, Double> result = new HashMap<>();
			String data = null;
			String script = String.format(
					"var element = $('%s'); return JSON.stringify(element.offset());",
					fragmentLocator);

			List<WebElement> fragmentElements = driver
					.findElements(By.cssSelector(fragmentLocator));
			assertTrue(fragmentElements.size() > 0);

			fragmentElement = fragmentElements.get(0);

			data = (String) js.executeScript(script);
			result = parseOffsets(data);
			System.err.println("Fragment " + fragmentId + ":");
			for (String dimension : result.keySet()) {
				System.err.println(
						String.format("%s = %2$,.2f", dimension, result.get(dimension)));
			}
			try {
				data = (String) js.executeScript(
						"var element = arguments[0]; return element.offset()",
						fragmentElement);
			} catch (WebDriverException e) {
				// System.err.println("Exception (ignored): " + e.toString());
				// unknown error: element.offset is not a function
				// ignore
			}

			// scroll the fragment
			WebElement label1 = driver.findElement(By.xpath(String
					.format("//*[contains(text(), '%s')]", "Scroll the window to")));
			highlight(label1);
			Assert.assertTrue(checkElementAttribute(label1, "txt_screen", "for"));
			WebElement input1 = label1.findElement(By
					.cssSelector(String.format("input#%s", label1.getAttribute("for"))));
			highlight(input1);
			Assert.assertTrue(checkElementAttribute(input1, "text", "type"));
			WebElement label2 = label1.findElement(By.xpath("following::label"));
			highlight(label2);
			WebElement input2 = label2.findElement(By.xpath("input[@type='text']"));
			highlight(input2);
			input2.clear();
			input2.sendKeys(String.format("%d", fragmentId));
			WebElement button = driver
					.findElement(By.cssSelector("body > label:nth-child(6) > button"));
			highlight(button);
			button.click();

			waitJQueryDone();
			sleep(1000);
			highlight(fragmentElement);

			data = (String) js.executeScript(script);
			result = parseOffsets(data);
			System.err.println("Fragment " + fragmentId + " (after scroll):");
			for (String dimension : result.keySet()) {
				System.err.println(
						String.format("%s = %2$,.2f", dimension, result.get(dimension)));
			}
			sleep(1000);
		}
	}

	@Test(priority = 1, enabled = true)
	public void compareOffsetsTest() {
		int fragmentId = 5;
		WebElement fragmentElement = null;
		WebElement containerElement = null;
		String fragmentLocator = String.format("#y > ul > li:nth-child(%d) > a",
				fragmentId);
		String containerLocator = "#y";

		Map<String, Double> result = new HashMap<>();
		String data1 = null;
		String data2 = null;
		String script1 = String.format(
				"var element = $('%s'); return JSON.stringify(element.offset());",
				fragmentLocator);
		String script2 = String.format(
				"var element = $('%s'); return JSON.stringify(element.offset());",
				containerLocator);

		List<WebElement> fragmentElements = driver
				.findElements(By.cssSelector(fragmentLocator));
		assertTrue(fragmentElements.size() > 0);

		fragmentElement = fragmentElements.get(0);
		// containerElement = fragmentElement.findElement(By.xpath(".."));
		containerElement = driver.findElement(By.cssSelector(containerLocator));
		Assert.assertTrue(checkElementAttribute(containerElement, "container"));

		data2 = (String) js.executeScript(script2);
		result = parseOffsets(data2);
		System.err.println("Container: ");
		for (String dimension : result.keySet()) {
			System.err.println(
					String.format("%s = %2$,.2f", dimension, result.get(dimension)));
		}

		// scroll the fragment
		WebElement input = driver.findElement(
				By.cssSelector("body > label:nth-child(6) > input[type='text']"));
		highlight(input);
		input.clear();
		input.sendKeys(String.format("%d", fragmentId));
		WebElement button = driver
				.findElement(By.cssSelector("body > label:nth-child(6) > button"));
		highlight(button);
		button.click();

		waitJQueryDone();
		sleep(1000);
		highlight(fragmentElement);

		data1 = (String) js.executeScript(script1);
		result = parseOffsets(data1);
		System.err.println("Fragment " + fragmentId + " (after scroll):");
		for (String dimension : result.keySet()) {
			System.err.println(
					String.format("%s = %2$,.2f", dimension, result.get(dimension)));
		}
	}

	@AfterMethod
	public void resetBrowser() {
		if (driver != null) {
			driver.get("about:blank");
		}
	}

	// utils

	private void waitJQueryDone() {
		// Wait until form is rendered, lambda semantics
		wait.until((WebDriver driver) -> {
			// System.err.println("Wait for form to finish rendering");
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			Boolean done = (Boolean) ((Long) js
					.executeScript("return jQuery.active") == 0);
			if (done) {
				// System.err.println("Done");
			}
			return done;
		});

	}

	private Map<String, Double> parseOffsets(String payload) {
		/* {"top":1167.4000244140625,"left":44} */
		Map<String, Double> result = new HashMap<>();

		payload = payload.substring(1, payload.length() - 1);
		String[] pairs = payload.split(",");

		for (String pair : pairs) {
			String[] values = pair.split(":");
			result.put(values[0].trim(), Double.parseDouble(values[1]));
		}
		return result;

	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
