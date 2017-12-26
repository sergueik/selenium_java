package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class JqueryDatePickerTest extends BaseTest {

	private WebElement element = null;
	private WebDriver frame;
	private String selector = null;
	private static int afterTest = 10000;

	private static String baseURL = "http://jqueryui.com/datepicker/#buttonbar";
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
		// for 4-digit year for locale-specific dateFormat, use workaround
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
		String buttonBarSelector = "#content > div.demo-list > ul > li.active > a[href *= '/buttonbar.html']";
		WebElement element = driver.findElement(By.cssSelector(buttonBarSelector));
		highlight(element);
		element.click();
		frame = driver.switchTo()
				.frame(driver.findElement(By.cssSelector("iframe.demo-frame")));
	}

	@AfterMethod
	public void afterMethod() {
		driver.switchTo().defaultContent();
	}

	@AfterClass
	public void afterClass() {
		sleep(afterTest);
		try {
			super.afterClass();
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
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
	// keyboard navigation works with some jqueryUI-derived datepicker widgets
	// e.g. https://yt.ua/ru/railway
	// TODO
	@Test(priority = 2, enabled = false)
	public void keyboardNavigationTest() throws Exception {
		String cssSelector = "#datepicker";
		WebElement element = frame.findElement(By.cssSelector(cssSelector));
		highlight(element, 1000);
		element.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id = 'ui-datepicker-div' ]")));
		WebElement dateWidget = driver
				.findElement(By.xpath("//*[@id = 'ui-datepicker-div' ]"));
		dateWidget.sendKeys(Keys.ARROW_RIGHT);
		dateWidget.sendKeys(Keys.ENTER);
	}

	@Test(priority = 3, enabled = true)
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
				// NOTE: flash->() has no visual effect ?
				flash(currentElement);
				System.err.println("day of month: " + currentElement.getText());
			}
		}
		WebElement monthElement = driver.findElement(By.xpath(
				"//div[@class = 'ui-datepicker-title']/span[@class = 'ui-datepicker-month' ]"));
		flash(monthElement);
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
