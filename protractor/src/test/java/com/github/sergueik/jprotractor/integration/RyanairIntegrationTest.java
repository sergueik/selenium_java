package com.github.sergueik.jprotractor.integration;

import static java.lang.Boolean.parseBoolean;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Based on https://github.com/olyv/ryanair
 * (converted from Node.js to Java)
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class RyanairIntegrationTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 3;
	static int flexibleWait = 5;
	static long pollingInterval = 250;
	static int width = 1024;
	static int height = 768;
	// Legacy setting PhantomJS time frame:
	// set to true for Desktop,
	// false for headless browser Travis CI testing
	static boolean isCIBuild = false;
	public static String localFile;
	static StringBuilder sb;
	static Formatter formatter;
	private static String fullStackTrace;
	private final static String baseUrl = "http://www.ryanair.com";
	private final static String defaultEmail = "automationnewuser24@gmail.com";
	private final static String defaultPassword = "autOm@ted24";
	private final static String defaultUserName = "John";

	@BeforeClass
	public static void setup() throws IOException {
		sb = new StringBuilder();
		formatter = new Formatter(sb, Locale.US);
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
		CommonFunctions.setHighlightTimeout(1000);
	}

	@Before
	public void beforeEach() {
		// Given I am on Home page
		ngDriver.navigate().to(baseUrl);
	}

	// @Ignore
	@Test
	// do no want to deal with the UI leading to login page atm
	public void testSiteLogint() {
		String login_url = "https://www.ryanair.com/gb/en/#";
		String email = CommonFunctions.getPropertyEnv("TEST_EMAIL", defaultEmail);
		String userName = CommonFunctions.getPropertyEnv("TEST_USERNAME",
				defaultUserName);
		String password = CommonFunctions.getPropertyEnv("TEST_PASSWORD",
				defaultPassword);
		WebDriver driver = ngDriver.getWrappedDriver();

		driver.navigate().to(login_url);
		// agree on cookies

		if (driver.findElement(By.id("cookie-popup")).isDisplayed()) {
			WebElement closeBox = wait
					.until(ExpectedConditions.visibilityOfElementLocated(
							By.cssSelector("#cookie-popup > div.cookie-popup__close > div")));
			closeBox.click();
			System.err.println("Waiting while Cookie Popup Box is visible");
			CommonFunctions.waitWhileElementIsVisible(By.id("cookie-popup"));
		}

		sleep(1000);
		// log in
		WebElement loadBox = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
						"span.username[translate='MYRYANAIR.LAYOUT.HEADER.MYRYANAIR_LOGIN']")));
		loadBox.click();
		WebElement loadForm = wait.until(ExpectedConditions.visibilityOf(
				driver.findElement(By.cssSelector("div.modal-form-container"))));
		highlight(loadForm);

		// enter user email
		WebElement emailAddress = loadForm
				.findElement(By.cssSelector("input[name='emailAddress']"));
		actions.moveToElement(emailAddress).build().perform();
		highlight(emailAddress);
		emailAddress.sendKeys(email);

		// enter password
		WebElement passwordInput = loadForm
				.findElement(By.cssSelector("password-input > input[name='password']"));
		actions.moveToElement(passwordInput).build().perform();
		highlight(passwordInput);
		passwordInput.sendKeys(password);

		// click "Login"
		WebElement loginButton = loadForm.findElement(By.cssSelector(
				"[button-text='MYRYANAIR.AUTHORIZATION.LOGIN.LOGIN_AUTHORIZATION']"));
		actions.moveToElement(loginButton).build().perform();
		highlight(loginButton);

		loginButton.click();
		// wait until the login Popup box disappears, maybe it does so quickly
		// that code never sees one
		// NOTE: not using findElement(...).isDisplayed()
		// because of org.openqa.selenium.NoSuchElementException
		if (driver
				.findElements(By
						.cssSelector("#ngdialog1 div.ngdialog-content div#login.popupbox"))
				.size() > 0) {
			System.err.println("Waiting while Login Popup Box is visible");
			CommonFunctions.waitWhileElementIsVisible(
					By.cssSelector("#ngdialog1 div.ngdialog-content div#login.popupbox"));
		}
		System.err.println("Login Popup Box is no longer diplayed");
		// wait.Until(d =>
		// (d.FindElements(By.CssSelector("#ngdialog1 div.ngdialog-content
		// div#login.popupbox")).Count == 0));

		sleep(1000);

		// read who is greeted
		WebElement greeterBox = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("span.username")));
		System.err.println(greeterBox.getText());
		assertThat(greeterBox.getText(), containsString(userName));
		highlight(greeterBox);
		// greeterBox.click();
		// sleep(1000);

		NgWebElement departuteAirportInput = new NgWebElement(ngDriver,
				ngDriver.getWrappedDriver()
						.findElements(NgBy.model("$parent.value")).stream().filter(o -> o
								.getAttribute("placeholder").matches("(?i)Departure Airport"))
						.findFirst().get());

		departuteAirportInput.click();

		String departureCountry = "Poland";

		// Note: wait returns WebElement, not NgWebElement
		WebElement departureCountryElement = wait
				.until(ExpectedConditions.visibilityOf(ngDriver.findElement(
						NgBy.cssContainingText("div.core-list-item", departureCountry))));
		assertThat(departureCountryElement, notNullValue());
		highlight(departureCountryElement);
		scrollElementIntoViewAndClick(departureCountryElement);

		String departureCity = "Wroclaw";
		WebElement departureCityElement = wait
				.until(ExpectedConditions.visibilityOf(ngDriver
						.findElement(NgBy.cssContainingText("span", departureCity))));
		assertThat(departureCityElement, notNullValue());
		highlight(departureCityElement);
		scrollElementIntoViewAndClick(departureCityElement);
		sleep(1000);
		WebElement destintionAirportInput = ngDriver
				.findElements(NgBy.model("$parent.value")).stream()
				.filter( // NOTE: case sensitive
						e -> e.getAttribute("placeholder").contains("Destination airport"))
				.findFirst().get();
		assertThat(destintionAirportInput, notNullValue());
		System.err.println("destintionAirportInput: "
				+ destintionAirportInput.getAttribute("outerHTML"));

		// The "destination Airport" input is not really needed to be clicked
		// explicitly
		// moreover click "destination Airport" input hides the selection input
		// this needs testing
		// destintionAirportInput.click();
		// sleep(1000);
		// destintionAirportInput.click();
		// sleep(1000);

		// when there is only one destintion available, the behaviour changes
		// TODO: "enabled" attribute check
		String destinationCountry = "Portugal";
		WebElement destinationCountryElement = wait
				.until(ExpectedConditions.visibilityOf(ngDriver.findElement(
						NgBy.cssContainingText("div.core-list-item", destinationCountry))));
		assertThat(destinationCountryElement, notNullValue());
		highlight(destinationCountryElement);
		scrollElementIntoViewAndClick(destinationCountryElement);

		String destinationCity = "Lisbon";
		WebElement destinationCityElement = wait
				.until(ExpectedConditions.visibilityOf(ngDriver
						.findElement(NgBy.cssContainingText("span", destinationCity))));
		assertThat(destinationCityElement, notNullValue());
		highlight(destinationCityElement);
		scrollElementIntoViewAndClick(destinationCityElement);

		WebElement oneWayTripRadio = wait.until(
				ExpectedConditions.visibilityOf(ngDriver.getWrappedDriver().findElement(
						By.cssSelector("div[class='flight-search-type-option one-way']"))));
		oneWayTripRadio.click();

		WebElement departureDateInput = wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(NgBy.model("dateRange.startDate"))));
		assertThat(departureDateInput, notNullValue());

		departureDateInput.click();
		WebElement departureDate = wait
				.until(ExpectedConditions.visibilityOf(ngDriver.getWrappedDriver()
						.findElement(By.cssSelector("[data-id = '" + new Date() + "']"))));
		assertThat(departureDate, notNullValue());
		highlight(departureDate);		
		departureDate.click();

		// log out
		greeterBox = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("span.username")));
		System.err.println(greeterBox.getText());
		assertThat(greeterBox.getText(), containsString(userName));
		highlight(greeterBox);
		greeterBox.click();
		sleep(1000);
		WebElement logoutLink = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.user-dropdown a[ng-click='logout()']")));
		System.err.println(logoutLink.getText());
		highlight(logoutLink);
		logoutLink.click();

	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void scrollElementIntoViewAndClick(WebElement element) {
		CommonFunctions.executeScript("arguments[0].scrollIntoView();", element);
		element.click();
	};

	// NOTE: a different implementation exists in CommonFunctions
	private static void getPageContent(String pagename) {
		String baseUrl = CommonFunctions.getPageContent(pagename);
		ngDriver.navigate().to(baseUrl);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
