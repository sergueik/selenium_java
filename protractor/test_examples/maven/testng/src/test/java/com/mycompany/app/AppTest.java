package com.mycompany.app;

import org.apache.commons.lang.StringUtils;
import static java.lang.Boolean.*;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.RuntimeException;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.By.*;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

public class AppTest {

	public static VideoRecorder recorder;
	private static WebDriver seleniumDriver;
	private static NgWebDriver ngDriver;
	private static String baseUrl = "http://dalelotts.github.io/angular-bootstrap-datetimepicker/";

	private static Alert alert;

	private static int implicitWait = 10;
	private static int flexibleWait = 5;
	private static long pollingInterval = 500;
	private static int width = 1600;
	private static int height = 1200;
	private static WebDriverWait wait;
	private static Actions actions;

	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeSuite
	public static void setUp() throws Exception {
		final FirefoxProfile profile = new FirefoxProfile();
		profile.setEnableNativeEvents(false);
		seleniumDriver = new FirefoxDriver(profile);
    // NOTE: can be unstable if test run on desktop and chrome browser allowed to do updates
		// System.setProperty("webdriver.chrome.driver",
		//		"c:/java/selenium/chromedriver.exe");
		// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// seleniumDriver = new ChromeDriver(capabilities);

		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		actions = new Actions(seleniumDriver);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		ngDriver = new NgWebDriver(seleniumDriver);
		// seleniumDriver.get("about:blank");
		// Thread.sleep(10000);
		recorder = new VideoRecorder();
		recorder.startRecording(seleniumDriver);
	}

	@BeforeMethod
	public void resetBrowser() {
		ngDriver.navigate().to(baseUrl);
	}

	@Test(enabled = true)
	public void testDatePickerDirectSelect() throws Exception {
		NgWebElement ng_result;
		try {
			ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput"));
		} catch (WebDriverException e) {
			assertTrue(e.getMessage().contains(
					"no injector found for element argument to getTestability"));
			System.err.println(" Ignoring exception: " + e.getMessage());
		} catch (Exception e) {
			throw (e);
		}

		ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput",
				"[data-ng-app]"));
		actions.moveToElement(ng_result.getWrappedElement()).build().perform();
		execute_script("scroll(0, 550)");
		Thread.sleep(300);
		execute_script("scroll(0, 550)");
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
		// seleniumDriver.manage().window()
		// .setSize(new Dimension(datepicker_width, datepicker_heght));
		NgWebElement ng_dropdown = ngDriver.findElement(By
				.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		actions.moveToElement(ng_dropdown.getWrappedElement()).build().perform();
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
			System.err.println("Enexpected exception");
			throw (e);
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
			System.err.println("Ignoring StaleElementReferenceException");
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

			throw (e);
		}
	}

	@AfterSuite
	public static void tearDown() throws Exception {
		recorder.stopRecording("Recording");
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element, 500);
	}

	private static void highlight(WebElement element, long highlight_interval)
			throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		execute_script("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		execute_script("arguments[0].style.border=''", element);
	}

	public static Object execute_script(String script, Object... args) {
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) seleniumDriver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException(
					"Script execution failed.");
		}
	}
}
