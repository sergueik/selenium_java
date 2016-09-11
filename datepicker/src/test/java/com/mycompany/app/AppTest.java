package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;

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

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

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
import static java.lang.Boolean.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

	private static WebDriver driver;
	private static WebDriver frame;
	public static WebDriverWait wait;
	public static Actions actions;
	private WebElement element = null;
	private String selector = null;
	private static long implicitWait = 3;
	private static int flexibleWait = 5;
	private static long polling = 500;
	private static long highlight = 100;
	private static long afterTest = 10000;
	private static String baseURL = "http://jqueryui.com/datepicker/";
	private static String dateString;
	private static Calendar calendar;
	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setUp() throws Exception {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(polling, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, 4);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    // for 4-digit yer for locale-specific dateFormat, use workaround 
    // http://stackoverflow.com/questions/7796321/simpledateformat-pattern-based-on-locale
    DateFormat dateFormatLocale =  DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
    SimpleDateFormat simpleDateFormatLocale = (SimpleDateFormat) dateFormatLocale;
    String pattern = simpleDateFormatLocale.toPattern().replaceAll("\\byy\\b", "yyyy");
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateString = dateFormat.format(calendar.getTime());
    System.err.println("Testing date: " + dateString);
		driver.get(baseURL);
	}

	@Before
	public void beforeTest() {
		driver.get(baseURL);
		frame = driver.switchTo().frame(
				driver.findElement(By.cssSelector("iframe.demo-frame")));
	}

	@After
	public void afterTest() {
		driver.switchTo().defaultContent();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(afterTest);
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@Test
	public void getDateTest() throws Exception {
		String cssSelector = "#datepicker";
		WebElement element = frame.findElement(By.cssSelector(cssSelector));
		highlight(element);
		setDate(frame, cssSelector, dateString);
		System.err.println("datepicker input value: "
			+ element.getAttribute("value"));
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
		WebElement dateWidget = driver.findElement(By
			.xpath("//*[@id = 'ui-datepicker-div' ]"));
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
		WebElement dateWidget = driver.findElement(By
			.xpath("//*[@id = 'ui-datepicker-div' ]"));
		Enumeration<WebElement> elements = Collections.enumeration(dateWidget
			.findElements(By.tagName("td")));
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
		WebElement monthElement = driver
			.findElement(By
				.xpath("//div[@class = 'ui-datepicker-title']/span[@class = 'ui-datepicker-month' ]"));
		assertEquals(new SimpleDateFormat("MMMM").format(calendar.getTime()),
			monthElement.getText());
		System.err.println("month: " + monthElement.getText());
		WebElement yearElement = driver
			.findElement(By
				.xpath("//div[@class = 'ui-datepicker-title']/span[@class = 'ui-datepicker-year' ]"));
		assertEquals(String.format("%d", calendar.get(Calendar.YEAR)),
			yearElement.getText());
		System.err.println("year: " + yearElement.getText());
	}

	private String getDate(WebDriver driver, String cssSelector)
		throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
			.cssSelector(cssSelector)));
		Object result = executeScript(String.format(
			"$('%s').datepicker('getDate')", cssSelector));
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
			.cssSelector(cssSelector)));
		// alternatively use defaultDate instead of setDate:
		// $('#dateselector').datepicker('option', 'defaultDate', targetDate);
		// http://stackoverflow.com/questions/606463/jquery-datepicker-set-selected-date-on-the-fly
		String script = "var targetDate = arguments[1]; if (!targetDate) { targetDate = new Date(); targetDate.setDate(targetDate.getDate() + 1);} var selector = arguments[0] || '#datepicker';$(selector).datepicker('setDate', targetDate);";
		// alternative:
		// String script = String.format("$('%s').datepicker('setDate', '%s')",
		// cssSelector, date);
		executeScript(script, cssSelector, date);
	}

	private void highlight(WebElement element) throws InterruptedException {
		highlight(element, highlight);
	}

	private void highlight(WebElement element, long highlight)
		throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight);
		executeScript("arguments[0].style.border=''", element);
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
				.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}
}
