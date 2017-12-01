package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;

import java.lang.RuntimeException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static java.lang.Boolean.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class JqueryDatePickerTest extends BaseTest {

	private WebElement element = null;
	private WebDriver frame;
	private String selector = null;
	private static int afterTest = 10000;

	private static String baseURL = "http://jqueryui.com/datepicker/";
	private static String dateString;
	private static Calendar calendar;
	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
		calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, 4);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		// for 4-digit yer for locale-specific dateFormat, use workaround
		// http://stackoverflow.com/questions/7796321/simpledateformat-pattern-based-on-locale
		DateFormat dateFormatLocale = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.US);
		SimpleDateFormat simpleDateFormatLocale = (SimpleDateFormat) dateFormatLocale;
		String pattern = simpleDateFormatLocale.toPattern().replaceAll("\\byy\\b",
				"yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateString = dateFormat.format(calendar.getTime());
		System.err.println("Testing date: " + dateString);
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseURL);
		frame = driver.switchTo()
				.frame(driver.findElement(By.cssSelector("iframe.demo-frame")));
	}

	@AfterMethod
	public void afterMethod() {
		driver.switchTo().defaultContent();
	}

	@AfterClass
	public void afterTest() {
		sleep(afterTest);
		super.afterTest();
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(verificationErrors.toString());
		}
	}

	@Test(priority = 1, enabled = true)
	public void getDateTest() throws Exception {
		String cssSelector = "#datepicker";
		WebElement element = frame.findElement(By.cssSelector(cssSelector));
		highlight(element);
		setDate(frame, cssSelector, dateString);
		System.err
				.println("datepicker input value: " + element.getAttribute("value"));
		System.err.println("datepicker getDate(): " + getDate(frame, cssSelector));
	}

	// http://software-testing.ru/forum/index.php?/topic/31835-peredacha-daty-v-pole/
	// keyboard navigation works with some jqueryUI-deriver datepicker widgets
	// e.g. https://yt.ua/ru/railway
	@Test
	public void keyboardNavigationTest() throws Exception {
		String cssSelector = "#datepicker";
		WebElement element = frame.findElement(By.cssSelector(cssSelector));
		highlight(element);
		element.click();
		WebElement dateWidget = driver
				.findElement(By.xpath("//*[@id = 'ui-datepicker-div' ]"));
		dateWidget.sendKeys(Keys.ARROW_RIGHT);
		dateWidget.sendKeys(Keys.ENTER);
	}

	@Test
	public void setDateTest() throws Exception {
		String cssSelector = "#datepicker";
		WebElement element = frame.findElement(By.cssSelector(cssSelector));
		highlight(element);
		setDate(frame, cssSelector, dateString);
		String xpath = "//*[@id='datepicker']";
		element = frame.findElement(By.xpath(xpath));

		element.click();
		WebElement dateWidget = driver
				.findElement(By.xpath("//*[@id = 'ui-datepicker-div' ]"));
		Enumeration<WebElement> elements = Collections
				.enumeration(dateWidget.findElements(By.tagName("td")));
		String dayString = String.format("%d", calendar.get(Calendar.DAY_OF_MONTH));
		while (elements.hasMoreElements()) {
			WebElement currentElement = elements.nextElement();
			if (currentElement.getText().isEmpty()) {
				break;
			}
			if (currentElement.getText().equals(dayString)) {
				highlight(currentElement);
				System.err.println("day of month: " + currentElement.getText());
			}
		}
		WebElement monthElement = driver.findElement(By.xpath(
				"//div[@class = 'ui-datepicker-title']/span[@class = 'ui-datepicker-month' ]"));
		assertEquals(new SimpleDateFormat("MMMM").format(calendar.getTime()),
				monthElement.getText());
		System.err.println("month: " + monthElement.getText());
		WebElement yearElement = driver.findElement(By.xpath(
				"//div[@class = 'ui-datepicker-title']/span[@class = 'ui-datepicker-year' ]"));
		assertEquals(String.format("%d", calendar.get(Calendar.YEAR)),
				yearElement.getText());
		System.err.println("year: " + yearElement.getText());
	}

	private String getDate(WebDriver driver, String cssSelector)
			throws InterruptedException {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		Object result = executeScript(
				String.format("$('%s').datepicker('getDate')", cssSelector));
		if (result != null) {
			return result.toString();
		} else {
			return "";
		}
	}

	private void setDate(WebDriver driver, String cssSelector, String date)
			throws InterruptedException {
		// Java 8 style
		/*
		 * new WebDriverWait(driver, flexible_wait).until( (WebDriver d) ->
		 * d.findElement(By.cssSelector(cssSelector)).isDisplayed() );
		 */
		// Java 7 style
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		// alternatively use defaultDate instead of setDate:
		// $('#dateselector').datepicker('option', 'defaultDate', targetDate);
		// http://stackoverflow.com/questions/606463/jquery-datepicker-set-selected-date-on-the-fly
		String script = "var targetDate = arguments[1]; if (!targetDate) { targetDate = new Date(); targetDate.setDate(targetDate.getDate() + 1);} var selector = arguments[0] || '#datepicker';$(selector).datepicker('setDate', targetDate);";
		// alternative:
		// String script = String.format("$('%s').datepicker('setDate', '%s')",
		// cssSelector, date);
		executeScript(script, cssSelector, date);
	}
}
