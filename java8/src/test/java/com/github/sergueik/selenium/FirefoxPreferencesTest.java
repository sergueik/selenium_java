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
 * Interacting with Firefox Preferences pages using Selenium WebDriver 
 * based on https://github.com/intoli/intoli-article-materials/blob/master/articles/clear
 * https://github.com/intoli/intoli-article-materials/tree/master/articles/clear-the-firefox-browser-cache
 * added tests reflecting for Firefox browser version-specific
 * Preferences pages layouts
 * version 40 and 59
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class FirefoxPreferencesTest extends BaseTest {

	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		// Firefox only, run with profile
		// mvn -Pfirefox test
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

	// the Preferences pages layout varies with browser version (v.40 vs. v.59)
	// for Firefox version 59
	@Test(enabled = true, priority = 10)
	public void testClearCacheNew() {

		// Arrange
		driver.get("about:preferences#privacy");
		// Wait page to load
		wait.until(ExpectedConditions.urlToBe("about:preferences#privacy"));
		System.err.println("Waited URL to be: " + driver.getCurrentUrl());
		WebElement clearCacheButtonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("clearCacheButton"))));
		assertThat(clearCacheButtonElement, notNullValue());
		System.err.println("Clicking on button: "
				+ clearCacheButtonElement.getAttribute("outerHTML"));
		clearCacheButtonElement.click();

		// Click the "Clear All Data" button under "Site Data"
		// this button is no longer shown in preferences, keep the code
		WebElement clearSiteDataButtonElement = null;
		try {
			clearSiteDataButtonElement = wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.id("clearSiteDataButton"))));
		} catch (NoSuchElementException e) {
			return;
		}
		assertThat(clearSiteDataButtonElement, notNullValue());
		clearSiteDataButtonElement.click();
		// and accept the alert
		// TODO: Generics ?
		wait.until(ExpectedConditions.alertIsPresent());
		try {
			alert = driver.switchTo().alert();
			String alertText = alert.getText();
			// System.err.println("alert Text: " + alertText);
			assertThat(alertText,
					containsString("clear all cookies and site data stored by Firefox"));
			// confirm alert
			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}

	}

	// for Firefox version 40
	@Test(enabled = false, priority = 20)
	public void testClearCacheOld() {
		// Arrange
		driver.get("about:preferences#advanced");
		// Wait page to load
		wait.until(ExpectedConditions.urlContains("#advanced"));
		System.err.println("Waited on URL contains: " + driver.getCurrentUrl());
		// Click the "Clear Now" button under "Cached Web Content"
		WebElement networkTabElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("networkTab"))));
		assertThat(networkTabElement, notNullValue());
		try {
			highlight(networkTabElement);
		} catch (JavaScriptException e) {
			// org.openqa.selenium.JavascriptException: waiting for doc.body
			// failed
			System.err.println("Exception (ignored) " + e.toString());
		}

		System.err.println(
				"Clicking on tab: " + networkTabElement.getAttribute("outerHTML"));

		// TODO: figure which method is working
		networkTabElement.click();
		networkTabElement.sendKeys(Keys.SPACE);
		actions.moveToElement(networkTabElement).click().build().perform();
		WebElement clearCacheButtonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("clearCacheButton"))));
		assertThat(clearCacheButtonElement, notNullValue());
		System.err.println("Clicking on button: "
				+ clearCacheButtonElement.getAttribute("outerHTML"));
		clearCacheButtonElement.click();

		WebElement clearOfflineAppCacheButtonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("clearOfflineAppCacheButton"))));
		assertThat(clearOfflineAppCacheButtonElement, notNullValue());
		System.err.println("Clicking on button: "
				+ clearOfflineAppCacheButtonElement.getAttribute("outerHTML"));
		clearOfflineAppCacheButtonElement.click();

		// Click the "Clear All Data" button under "Site Data"
		// this button is no longer shown in preferences, keep the code
		WebElement clearSiteDataButtonElement = null;
		try {
			clearSiteDataButtonElement = wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.id("clearSiteDataButton"))));
		} catch (NoSuchElementException e) {
			return;
		}
		assertThat(clearSiteDataButtonElement, notNullValue());
		clearSiteDataButtonElement.click();
		// and accept the alert
		// TODO: Generics ?
		wait.until(ExpectedConditions.alertIsPresent());
		try {
			alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	// for Firefox version 40
	@Test(enabled = false, priority = 30)
	public void testClearHistoryld() {

		// Arrange
		driver.get("about:preferences#privacy");
		// Wait page to load
		wait.until(ExpectedConditions.urlToBe("about:preferences#privacy"));
		System.err.println("Waited URL to be: " + driver.getCurrentUrl());

		WebElement historyRememberClearButtonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("historyRememberClear"))));
		assertThat(historyRememberClearButtonElement, notNullValue());
		System.err.println("Clicking on button: "
				+ historyRememberClearButtonElement.getAttribute("outerHTML"));

		historyRememberClearButtonElement.click();
		// TODO: handle "Clear Recent History" dialog
	}

}
