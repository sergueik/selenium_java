package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class EllocateTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String baseURL = "http://jqueryui.com/datepicker/#buttonbar";

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
	}

	@AfterMethod
	public void afterMethod() {
	}

	@AfterClass
	public void afterClass() {
		try {
			super.afterClass();
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
		if (verificationErrors.length() != 0) {
			System.err.println("Errors in tests: " + verificationErrors.toString());
		}
	}

	@Test(priority = 1, enabled = true)
	public void ellocateTest() throws Exception {
		// String cssSelector = "#sidebar > aside:nth-child(2) > ul >
		// li:nth-child(4) >
		// a";
		String cssSelector = "#sidebar";
		// Arrange
		WebElement element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(cssSelector)));

		highlight(element);
		// Assert
		@SuppressWarnings("unchecked")
		Map<String, String> result = (Map<String, String>) executeScript(
				String.format("%s;\nreturn $('%s').ellocate();\n",
						getScriptContent("ellocate.js"), cssSelector));
		if (result != null) {
			for (String resultKey : result.keySet()) {
				System.err.println(resultKey + ": " + result.get(resultKey));
			}
		} else {
			System.err.println("result is null");

		}
		if (result != null) {
			try {
				element = wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(result.get("css"))));
				assertThat(result, notNullValue());
			} catch (TimeoutException e1) {
				verificationErrors.append(e1.toString());
			} catch (AssertionError e2) {
				verificationErrors.append(e2.toString());
			}
			try {
				element = wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(result.get("xpath"))));
				assertThat(result, notNullValue());
			} catch (TimeoutException e1) {
				verificationErrors.append(e1.toString());
			} catch (AssertionError e2) {
				verificationErrors.append(e2.toString());
			}
		}
	}
}
