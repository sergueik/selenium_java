package com.github.sergueik.jprotractor.integration;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;

/**
 * Tests of Javascript scrroll
 * http://www.globalsqa.com/angularJs-protractor/Scrollable/ based on
 * https://github.com/PiotrWysocki/globalsqa.com/blob/master/src/main/java/
 * scrollablepages/ScrollablePage.java
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public class NgScrollableIntegrationTest {
	private static String fullStackTrace;
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private static int implicitWait = 10;
	private static int flexibleWait = 5;
	private static long pollingInterval = 500;
	private static int width = 600;
	private static int height = 400;
	// set to true for Desktop, false for headless browser testing
	private static boolean isCIBuild = false;
	private static String baseUrl = "http://www.globalsqa.com/angularJs-protractor/Scrollable/";

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Before
	public void beforeEach() {
		ngDriver.navigate().to(baseUrl);
	}

	// @Ignore
	@Test
	public void testRowsVisibility() {

		// Given we are using scrollable table

		WebElement divTableContainer = wait.until( // Wait page to load
				ExpectedConditions.visibilityOf(
						ngDriver.findElement(By.className("table-container"))));

		List<WebElement> rows = ngDriver
				.findElements(NgBy.repeater("row in rowCollection"));
		assertThat(rows, notNullValue());
		assertTrue(rows.size() > 1);
		int size = rows.size();
		System.err.println(String.format("Protractor finds: %d rows", size));
		for (WebElement rowElement : rows) {
			try {
				if (!rowElement.isDisplayed()) {
					break;
				}
				highlight(rowElement);
				System.err.println(
						rowElement.getText() + " " + rowElement.getLocation().getY());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				scrollDown(rowElement.getSize().height, divTableContainer);
				actions.moveToElement(rowElement).build().perform();
			} catch (StaleElementReferenceException e) {

				System.err.println("ignored StaleElementReferenceException");
			}
		}
		//
		rows = ngDriver.findElements(By.cssSelector(
				".ng-scope .table-container table[st-table='rowCollection'] > tbody > tr"));
		assertThat(rows, notNullValue());
		assertTrue(rows.size() >= size);
		System.err.println(String.format("Selenium finds: %d rows", rows.size()));
		WebElement row = rows.get(size - 1);
		System.err.println(row.getText());
		highlight(row);

		for (int cnt = size - 1; cnt != rows.size(); cnt++) {
			WebElement rowElement = rows.get(cnt);
			System.err.println(rowElement.getText() + " " + rowElement.isDisplayed());
			if (!rowElement.isDisplayed()) {
				break;
			}
		}
		//
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

	// Scroll down page
	public void scrollDown(int pixels, WebElement element) {
		CommonFunctions.executeScript("arguments[0].scrollTop += " + pixels + ";",
				element);
	}

	// Scroll up page
	public void scrollUp(int pixels, WebElement element) {
		CommonFunctions.executeScript("arguments[0].scrollTop -= " + pixels + ";",
				element);
	}

	// Get scroll Y position
	public Long getScrollYPosition(WebElement element) {
		Long value = (Long) CommonFunctions
				.executeScript("return arguments[0].scrollTop;", element);
		return value;
	}
}
