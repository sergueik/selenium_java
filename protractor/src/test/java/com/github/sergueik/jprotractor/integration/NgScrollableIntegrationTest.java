package com.github.sergueik.jprotractor.integration;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;
/**
 * Tests of Javascript scroll
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
	private static final int implicitWait = 10;
	private static final int flexibleWait = 5;
	private static long pollingInterval = 500;
	private static final int width = 600;
	private static final int height = 400;

	private static final int maxRows = 5;
	private static int rowCnt = 0;

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
		wait.pollingEvery(Duration.ofMillis(pollingInterval));
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Before
	public void beforeEach() {
		ngDriver.navigate().to(baseUrl);
	}

	@Ignore
	@Test
	public void testRowsScrollVisibility() {
		// Wait page to load
		WebElement tableContainer = wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));

		List<WebElement> rows = ngDriver
				.findElements(NgBy.repeater("row in rowCollection"));
		assertThat(rows, notNullValue());
		assertTrue(rows.size() > 1);
		int size = rows.size();
		System.err.println(String.format("Protractor finds: %d rows", size));
		rowCnt = 0;
		for (WebElement rowElement : rows) {
			if (rowCnt++ > maxRows) {
				break;
			}
			try {
				if (!rowElement.isDisplayed()) {
					break;
				}
				highlight(rowElement);
				System.err.println(String.format("row %d: %s\ny position: %d", rowCnt,
						rowElement.getText(), rowElement.getLocation().getY()));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				scrollDown(rowElement.getSize().height, tableContainer);
				actions.moveToElement(rowElement).build().perform();
			} catch (StaleElementReferenceException e) {
				System.err.println("Exception(ignored) " + e.toString());
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

	// @Ignore
	@Test
	public void testCountRows() {

		// Wait page to load
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
		List<WebElement> rows = ngDriver
				.findElements(NgBy.repeater("row in rowCollection"));
		List<WebElement> lastNameRows = ngDriver.findElements(new ByChained(
				By.className("table-container"), NgBy.repeater("row in rowCollection"),
				NgBy.binding("row.lastName")));
		System.err.println("Rows: " + rows.size());
		System.err.println("Last names: " + lastNameRows.size());
	}

	@Ignore
	@Test
	public void testEvaluateRowData() {

		// Wait page to load
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
		List<WebElement> nameRows = ngDriver.findElements(new ByChained(
				NgBy.repeater("row in rowCollection"), NgBy.binding("row.firstName")));
		rowCnt = 0;
		for (WebElement rowElement : nameRows) {
			if (rowCnt++ > maxRows) {
				break;
			}
			NgWebElement ngRowElement = new NgWebElement(ngDriver, rowElement);
			System.err.println(String.format("Row %d Name: %s %s", rowCnt,
					(String) ngRowElement.evaluate("row.firstName"),
					(String) ngRowElement.evaluate("row.lastName")));
		}
	}

	@Ignore
	@Test
	public void testRowColumnsWithByChained() {
		// Wait page to load
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
		List<WebElement> nameRows = ngDriver.findElements(new ByChained(
				NgBy.repeater("row in rowCollection"), NgBy.binding("row.firstName")));
		rowCnt = 0;
		for (WebElement rowElement : nameRows) {
			if (rowCnt++ > maxRows) {
				break;
			}
			try {
				if (!rowElement.isDisplayed()) {
					break;
				}
				highlight(rowElement);
				System.err.println(String.format("First name in row %d: %s ", rowCnt,
						rowElement.getText()));
			} catch (StaleElementReferenceException e) {
				System.err.println("Exception(ignored) " + e.toString());
			}
		}
	}

	// https://stackoverflow.com/questions/25914156/difference-between-findall-and-findbys-annotations-in-webdriver-page-factory
	@Ignore
	@Test
	public void testRowColumnsWithByAll() {
		// Wait page to load
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
		List<WebElement> rows = ngDriver
				.findElements(new ByAll(NgBy.repeater("row in rowCollection")));
		rowCnt = 0;
		for (WebElement rowElement : rows) {
			if (rowCnt++ > maxRows) {
				break;
			}
			try {
				if (!rowElement.isDisplayed()) {
					break;
				}
				highlight(rowElement);
				System.err.println(
						String.format("Row %d: %s ", rowCnt, rowElement.getText()));
			} catch (StaleElementReferenceException e) {
				System.err.println("Exception(ignored) " + e.toString());
			}
		}
	}

	@Ignore
	@Test
	public void testRowsAndColumns() {
		// Wait page to load
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
		List<WebElement> rows = ngDriver
				.findElements(NgBy.repeater("row in rowCollection"));
		assertThat(rows, notNullValue());
		assertTrue(rows.size() > 1);
		int size = rows.size();
		System.err.println(String.format("Protractor finds: %d rows", size));
		rowCnt = 0;
		// NOTE: birthdate is empty
		// <a ng-href="mailto:{{row.email}}">email</a></td>
		// appears impossible to locate
		List<String> columnBindings = Arrays.asList("row.firstName", "row.lastName"
		/*, "row.birthDate" */, "row.balance" /*,  "row.email" */);
		System.err.println("Inspecting first " + maxRows + " rows.");
		for (WebElement rowElement : rows) {
			if (rowCnt++ > maxRows) {
				break;
			}
			System.err
					.println(String.format("row %d: %s", rowCnt, rowElement.getText()));
			highlight(rowElement);
			for (String columnBinding : columnBindings) {
				WebElement columnElement = new NgWebElement(ngDriver, rowElement)
						.findElement(NgBy.binding(columnBinding));
				System.err.println(columnBinding + ": " + columnElement.getText());
				// will miss
				highlight(columnElement);
			}
		}
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
