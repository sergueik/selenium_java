package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Ignore;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgDatePickerTest {

	private static String fullStackTrace;
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	private static String baseUrl = "http://dalelotts.github.io/angular-bootstrap-datetimepicker/";

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

	@Test
	public void testDatePickerDirectSelect() throws Exception {
		NgWebElement ng_result;
		try {
			ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput"));
		} catch (WebDriverException e) {
			// Assert.assertThat(e.getMessage(),
			// CoreMatchers.containsString("no injector found for element argument to getTestability"));
			assertTrue(e.getMessage().contains(
					"no injector found for element argument to getTestability"));
			// System.err.println( "exception information" +
			// e.getAdditionalInformation());
			System.err.println("exception message(expected): " + e.getMessage());
		} catch (Exception e) {
			throw (e);
		}

		ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput",
				"[data-ng-app]"));
		assertThat(ng_result, notNullValue());
		assertTrue(ng_result.getAttribute("type").matches("text"));
		actions.moveToElement(ng_result.getWrappedElement()).build().perform();
		highlight(ng_result);
		NgWebElement ng_calendar = ngDriver.findElement(By
				.cssSelector(".input-group-addon"));
		assertThat(ng_calendar, notNullValue());
		highlight(ng_calendar);
		actions.moveToElement(ng_calendar.getWrappedElement()).click().build()
				.perform();
		// need a little more room
		int datepicker_width = 900;
		int datepicker_heght = 800;
		seleniumDriver.manage().window()
				.setSize(new Dimension(datepicker_width, datepicker_heght));
		NgWebElement ng_dropdown = ngDriver.findElement(By
				.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		String monthDate = "12";
		WebElement dateElement = ng_dropdown.findElements(
				NgBy.cssContainingText("td.ng-binding", monthDate)).get(0);
		assertThat(dateElement, notNullValue());
		highlight(dateElement);
		System.err.println("Mondh Date: " + dateElement.getText());
		dateElement.click();
		ngDriver.waitForAngular();
		NgWebElement ng_element = ng_dropdown.findElement(NgBy.model(
				"data.dateDropDownInput", "[data-ng-app]"));
		assertThat(ng_element, notNullValue());
		highlight(ng_element);
		// System.err.println(ng_element.getAttribute("innerHTML"));
		List<WebElement> ng_dataDates = ng_dropdown.findElements(NgBy
				.repeater("dateObject in data.dates"));
		assertThat(ng_dataDates.size(), equalTo(24));
		// Thread.sleep(10000);

		String timeOfDay = "6:00 PM";
		WebElement ng_hour = ng_element.findElements(
				NgBy.cssContainingText("span.hour", timeOfDay)).get(0);
		// java.lang.NullPointerException
		assertThat(ng_hour, notNullValue());
		highlight(ng_hour);
		System.err.println("Hour of the day: " + ng_hour.getText());
		ng_hour.click();
		ngDriver.waitForAngular();
		String specificMinute = "6:35 PM";
		// reload,
		try {
			ng_element = ng_dropdown.findElement(NgBy.model("data.dateDropDownInput",
					"[data-ng-app]"));
			assertThat(ng_element, notNullValue());
			highlight(ng_element);
			// System.err.println("Dropdown: " + ng_element.getText());
		} catch (StaleElementReferenceException e) {
			// org.openqa.selenium.StaleElementReferenceException in Phantom JS
			// works fine with desktop browsers
			if (isCIBuild) {
				System.err.println("Ignoring StaleElementReferenceException");
			} else {
				System.err.println("Enexpected exception");
				throw (e);
			}
		}
		WebElement ng_minute;
		try {
			ng_minute = ng_element.findElements(
					NgBy.cssContainingText("span.minute", specificMinute)).get(0);
			assertThat(ng_minute, notNullValue());
			highlight(ng_minute);
			System.err.println("Time of the day: " + ng_minute.getText());
			ng_minute.click();
		} catch (StaleElementReferenceException e) {
			if (isCIBuild) {
				System.err.println("Ignoring StaleElementReferenceException");
			} else {
				throw (e);
			}
		}
		try {
			ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput",
					"[data-ng-app]"));
			highlight(ng_result);
			System.err.println("Selected Date/time : "
					+ ng_result.getAttribute("value"));
			// "Thu May 12 2016 18:35:00 GMT-0400 (Eastern Standard Time)"
			assertTrue(ng_result.getAttribute("value").matches(
					"\\w{3} \\w{3} \\d{1,2} \\d{4} 18:35:00 GMT[+-]\\d{4} \\(.+\\)"));
		} catch (StaleElementReferenceException e) {
			if (isCIBuild) {
				System.err.println("Ignoring StaleElementReferenceException");
			} else {
				throw (e);
			}
		}
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) throws InterruptedException {
		CommonFunctions.highlight(element);
	}

}
