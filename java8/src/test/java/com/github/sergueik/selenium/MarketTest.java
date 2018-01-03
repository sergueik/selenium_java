package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class MarketTest extends BaseTest {

	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.setFlexibleWait(1);
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

	// NOTE: quite unstable
	@Test(enabled = true)
	public void test1() {
		// turn off implicit wait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		// Arrange
		long startTime = System.currentTimeMillis();
		// Assert
		long retryInterval = 1000;
		long maxRetry = 120;
		long checkRetryDelay = 0;
		driver.get(
				"https://market.yandex.ru/catalog/54913/list?hid=90566&track=fr_ctlg&lr=0&local-offers-first=0&deliveryincluded=0&onstock=1");
		actions.moveToElement(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.xpath("//div[@class='n-filter-panel-aside__content']/div[4]")))));

		// Act
		List<WebElement> elements = driver.findElements(By.xpath(
				"//div[@class='n-filter-panel-aside__content']/div[4]//span[@class='checkbox__box']/input[@type='checkbox']"));
		assertTrue(elements.size() > 0);
		System.err
				.println(String.format("Inspecting %d elements", elements.size()));
		assertThat(elements.get(0), notNullValue());
		int cnt = 0;
		for (WebElement element : elements) {
			System.err
					.println("Selecting the checkbox:\n" + element.getAttribute("id"));
			actions.moveToElement(element);
			element.sendKeys(Keys.SPACE);
			cnt++;
			try {
				highlight(element, 100);
			} catch (Exception e) {
				System.err.println("highlight() Exception " + e.getMessage());
				// Exception Expected condition failed: waiting for visibility of
				// element
			}

			// check how long is the ongoing test running
			checkRetryDelay = System.currentTimeMillis() - startTime;
			if (Math.ceil(checkRetryDelay / retryInterval) > maxRetry + 1) {
				String message = String.format(
						"Only managed to activate %d checkboxes in %d second\n", cnt,
						checkRetryDelay);
				System.err.format("Exception: %s", message);
				throw new RuntimeException(message);
			}

			/*
			try {
				highlightNew(element, 100);
			} catch (Exception e) {
				System.err.println("highlightNew() Exception: " + e.getMessage());
				// Exception unknown command: session/.../element/.../rect
			}
			*/
			try {
				element.click();
			} catch (Exception e) {
				System.err.println("click() Exception: " + e.getMessage());
				// Element is not clickable at point
				// Other element would receive the click
				// Test runs very slowly, possibly due to that
			}
			sleep(100);
		}
	}

	@Test(enabled = true)
	public void test2() {
		// Arrange
		driver.get(
				"https://market.yandex.ru/catalog/54913/list?hid=90566&track=fr_ctlg&lr=0&local-offers-first=0&deliveryincluded=0&onstock=1");
		actions.moveToElement(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.xpath("//div[@class='n-filter-panel-aside__content']/div[4]")))));

		// Act
		List<WebElement> elements = driver.findElements(By.xpath(
				"//div[@class='n-filter-panel-aside__content']/div[4]//span[span/@class='checkbox__box']/label[@class='checkbox__label']"));
		System.err.println("Elements size=" + elements.size());
		assertThat(elements.get(0), notNullValue());
		for (WebElement element : elements) {
			actions.moveToElement(element);
			highlight(element);
			System.err.println("Click on label:" + element.getAttribute("for"));
			element.click();
			try {
				element.sendKeys(Keys.SPACE);
			} catch (Exception e) {
				// unknown error: cannot focus element
				System.err.println("sendKeys() Exception " + e.getMessage());
			}
		}
	}

}
