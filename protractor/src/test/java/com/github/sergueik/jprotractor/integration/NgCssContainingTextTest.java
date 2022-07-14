package com.github.sergueik.jprotractor.integration;

import static org.exparity.hamcrest.date.DateMatchers.sameOrBefore;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuppressWarnings("deprecation")
public class NgCssContainingTextTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 800;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	// "https://datatables.net/examples/api/form.html";
	private static String baseUrl = "https://datatables.net/examples/api/highlight.html";

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		// wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait = new WebDriverWait(seleniumDriver, Duration.ofSeconds(flexibleWait));
		wait.pollingEvery(Duration.ofMillis(pollingInterval));
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Before
	public void beforeEach() {
		ngDriver.navigate().to(baseUrl);
	}

	@Test
	public void test1() {
		String cellText = "Software Engineer";
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			actions.moveToElement(tableElement).build().perform();
		} catch (RuntimeException timeoutException) {
			return;
		}

		List<WebElement> cellElements = seleniumDriver.findElements(
				By.cssSelector("#example > tbody > tr > td:nth-child(2)"));
		// Assert
		assertThat(cellElements.size(), greaterThan(0));
		// Act
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.forEach(CommonFunctions::highlight);
		// Assert
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.map(o -> o.getText()).forEach(System.err::println);
	}

	@Test
	public void test2() {
		String cellText = "Software Engineer";
		WebElement tableElement = null;
		// Arrange
		try {
			tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(tableElement, notNullValue());

		actions.moveToElement(tableElement).build().perform();
		List<WebElement> cellElements = tableElement
				.findElements(By.cssSelector("tbody > tr > td:nth-child(2)"));
		// Assert
		assertThat(cellElements.size(), greaterThan(0));
		// Act
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.forEach(CommonFunctions::highlight);
		// Assert
		cellElements.stream()
				.filter(o -> o.getText().contains((CharSequence) cellText))
				.map(o -> o.getText()).forEach(System.err::println);
	}

	@Test
	public void test3() {
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			actions.moveToElement(tableElement).build().perform();
		} catch (RuntimeException timeoutException) {
			return;
		}

		// Assert
		List<WebElement> cellElements = ngDriver.findElements(
				NgBy.cssContainingText("#example > tbody > tr > td:nth-child(2)",
						"Software Engineer"));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);
	}

	@Test
	public void test4() {
		NgWebElement ngTableElement = null;
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			ngTableElement = new NgWebElement(ngDriver, tableElement);
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(ngTableElement, notNullValue());

		actions.moveToElement(ngTableElement.getWrappedElement()).build().perform();

		List<WebElement> cellElements = ngTableElement
				.findElements(NgBy.cssContainingText("tbody > tr > td:nth-child(2)",
						"Software Engineer"));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);
	}

	@Test
	public void test5() {
		Object[] results = new Object[] { "Software Engineer",
				"Senior Javascript Developer" };
		String cellTextPackedRegexp = "__REGEXP__/(?:Software Engineer|Senior Javascript Developer)/i";
		NgWebElement ngTableElement = null;
		// Arrange
		try {
			WebElement tableElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("example")));
			ngTableElement = new NgWebElement(ngDriver, tableElement);
		} catch (RuntimeException timeoutException) {
			return;
		}
		// Assert
		assertThat(ngTableElement, notNullValue());

		actions.moveToElement(ngTableElement.getWrappedElement()).build().perform();

		List<WebElement> cellElements = ngTableElement
				.findElements(NgBy.cssContainingText("tbody > tr > td:nth-child(2)",
						cellTextPackedRegexp));
		assertThat(cellElements.size(), greaterThan(0));
		cellElements.stream().forEach(CommonFunctions::highlight);
		cellElements.stream().map(o -> o.getText()).forEach(System.err::println);

		assertThat(
				cellElements.stream().map(o -> o.getText()).collect(Collectors.toSet()),
				hasItems(results));

	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

}
