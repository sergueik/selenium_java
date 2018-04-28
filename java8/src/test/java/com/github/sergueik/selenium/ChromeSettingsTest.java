package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.github.sergueik.selenium.BaseTest;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

/**
 * Interacting with Chrome Preferences pages using Selenium WebDriver 
 * based on
 * https://github.com/intoli/intoli-article-materials/tree/master/articles/clear-the-chrome-browser-cache 
 * 
 *  
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class ChromeSettingsTest extends BaseTest {

	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		// Chrome only, run with profile
		// mvn -Pchrome test
		super.beforeMethod(method);
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error(s) in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	private static final By clearBrowsingButton = new By.ByCssSelector(
			"* /deep/ #clearBrowsingDataConfirm");

	@Test(enabled = true, priority = 10)
	public void testClearCach() {

		// Arrange
		driver.get("chrome://settings/clearBrowserData");
		// Wait page to load
		wait.until(
				ExpectedConditions.urlToBe("chrome://settings/clearBrowserData"));
		System.err.println("Waited URL to be: " + driver.getCurrentUrl());
		WebElement clearBrowsingButtonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(clearBrowsingButton)));
		assertThat(clearBrowsingButtonElement, notNullValue());
		System.err.println("Clicking on button: "
				+ clearBrowsingButtonElement.getAttribute("outerHTML"));
		clearBrowsingButtonElement.click();

		// wait for the button to be gone before returning
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(clearBrowsingButton));

	}
}
