package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Datetime Picker tests
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgDatePickerTest {

	private static String fullStackTrace;
	private static NgWebDriver ngDriver;
	private static WebDriver driver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	// need a little more room for datepicker
	private static int width = 900;
	private static int height = 800;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	private static String baseUrl = "http://dalelotts.github.io/angular-bootstrap-datetimepicker/";

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		driver = CommonFunctions.getSeleniumDriver();
		driver.manage().window().setSize(new Dimension(width, height));
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
		ngDriver = new NgWebDriver(driver);
	}

	@Before
	public void beforeEach() {
		// TODO: investigate the failure under TRAVIS
		assumeFalse(isCIBuild);
		ngDriver.navigate().to(baseUrl);
	}

	// @Ignore
	@Test
	// uses Embedded calendar
	public void testHighlightCurrentMonthDays() {
		NgWebElement ng_datepicker = null;
		// Arrange
		final String searchText = "Embedded calendar";
		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Iterator<WebElement> elements = d
							.findElements(By.className("col-sm-6")).iterator();
					Boolean result = false;
					WebElement element = null;
					while (elements.hasNext() && !result) {
						element = elements.next();
						String text = element.getText();
						result = text.contains(searchText);
					}
					if (result) {
						actions.moveToElement(element).build().perform();
					}
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		// Act
		try {
			ng_datepicker = ngDriver
					.findElement(NgBy.model("data.embeddedDate", "[data-ng-app]"));
			assertThat(ng_datepicker, notNullValue());
			actions.moveToElement(ng_datepicker.getWrappedElement()).build()
					.perform();
			highlight(ng_datepicker);
			// System.err.println("Embedded: " + ng_datepicker.getText());
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		WebElement[] dates = ng_datepicker
				.findElements(NgBy.repeater("dateObject in week.dates"))
				.toArray(new WebElement[0]);
		assertTrue(dates.length >= 28);
		Boolean foundDate = false;
		int start = 0, end = dates.length;
		for (int cnt = 0; cnt != dates.length; cnt++) {
			if (start == 0 && Integer.parseInt(dates[cnt].getText()) == 1) {
				start = cnt;
			}
			if (cnt > start && Integer.parseInt(dates[cnt].getText()) == 1) {
				end = cnt;
			}
		}
		for (int cnt = start; cnt != end; cnt++) {
			WebElement date = dates[cnt];
			highlight(date);
			if (date.getAttribute("class").contains("current")) {
				foundDate = true;
			}
		}
		// Assert
		assertTrue(foundDate);
	}

	// @Ignore
	@Test
	// uses DateTime Picker dropdown with input box
	public void testBrowse() {
		// Arrange
		final String searchText = "Drop-down Datetime with input box";
		WebElement contaiter = null;
		try {
			contaiter = wait.until(
					ExpectedConditions.visibilityOf(driver.findElement(By.xpath(String
							.format("//div[@class='col-sm-6']//*[contains(text(),'%s')]",
									searchText)))));
			highlight(contaiter);
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		try {
			contaiter = wait
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
							String.format("//*[text()[contains(.,'%s')]]", searchText)))));
			highlight(contaiter);
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Iterator<WebElement> elements = d
							.findElements(By.className("col-sm-6")).iterator();
					Boolean result = false;
					WebElement element = null;
					while (elements.hasNext() && !result) {
						element = elements.next();
						String text = elements.next().getText();
						// System.err.println("got: " + text);
						result = text.contains(searchText);
					}
					if (result) {
						actions.moveToElement(element).build().perform();
					}
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// Act
		NgWebElement ng_datepicker_input = ngDriver
				.findElement(NgBy.model("data.dateDropDownInput"));
		actions.moveToElement(ng_datepicker_input.getWrappedElement()).build()
				.perform();
		highlight(ng_datepicker_input);
		NgWebElement ng_calendar_button = ngDriver
				.findElement(By.cssSelector(".input-group-addon"));
		assertThat(ng_calendar_button, notNullValue());
		highlight(ng_calendar_button);
		actions.moveToElement(ng_calendar_button.getWrappedElement()).click()
				.build().perform();
		// NgWebElement ng_datepicker
		NgWebElement ng_dropdown = ngDriver
				.findElement(By.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		// Assert
		NgWebElement ng_display = ng_dropdown.findElement(
				NgBy.binding("data.previousViewDate.display", "[data-ng-app]"));
		assertThat(ng_display, notNullValue());
		Pattern pattern = Pattern.compile("\\d{4}\\-(?<month>\\w{3})");
		Matcher matcher = pattern.matcher(ng_display.getText());
		assertTrue(matcher.find());
		// Act
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Dec", "Jan" };
		String display_month = matcher.group("month");
		String next_month = months[java.util.Arrays.asList(months)
				.indexOf(display_month) + 1];
		System.err.println("Current month: " + display_month);
		System.err.println("Expect to find next month: " + next_month);
		WebElement ng_next_month = ng_display.findElement(By.xpath(".."))
				.findElement(By.className("right"));
		assertThat(ng_next_month, notNullValue());
		highlight(ng_next_month);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		ng_next_month.click();
		assertTrue(ng_display.getText().contains(next_month));
		highlight(ng_display);
		// Assert
		System.err.println("Next month: " + ng_display.getText());
	}

	// @Ignore
	@Test
	// uses DateTime Picker drop down with input box
	public void testDirectSelect() {

		NgWebElement ng_datepicker;
		// Arrange
		final String searchText = "Drop-down Datetime with input box";
		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Iterator<WebElement> elements = d
							.findElements(By.className("col-sm-6")).iterator();
					Boolean result = false;
					WebElement element = null;
					while (elements.hasNext() && !result) {
						String text = elements.next().getText();
						// System.err.println("got: " + text);
						result = text.contains(searchText);
					}
					if (result) {
						actions.moveToElement(element).build().perform();
					}
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		try {
			ng_datepicker = ngDriver
					.findElement(NgBy.model("data.dateDropDownInput"));
		} catch (WebDriverException e) {
			assertTrue(e.getMessage().contains(
					"no injector found for element argument to getTestability"));
			System.err.println(" Ignoring exception: " + e.getMessage());
		} catch (Exception e) {
			throw (e);
		}
		ng_datepicker = ngDriver
				.findElement(NgBy.model("data.dateDropDownInput", "[data-ng-app]"));
		assertThat(ng_datepicker, notNullValue());
		assertTrue(ng_datepicker.getAttribute("type").matches("text"));
		// Act, Assert
		actions.moveToElement(ng_datepicker.getWrappedElement()).build().perform();
		highlight(ng_datepicker);
		NgWebElement ng_calendar = ngDriver
				.findElement(By.cssSelector(".input-group-addon"));
		assertThat(ng_calendar, notNullValue());
		highlight(ng_calendar);
		actions.moveToElement(ng_calendar.getWrappedElement()).click().build()
				.perform();
		NgWebElement ng_dropdown = ngDriver
				.findElement(By.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		String monthDate = "12";
		WebElement dateElement = ng_dropdown
				.findElements(NgBy.cssContainingText("td.ng-binding", monthDate))
				.get(0);
		assertThat(dateElement, notNullValue());
		highlight(dateElement);
		System.err.println("Mondh Date: " + dateElement.getText());
		dateElement.click();
		ngDriver.waitForAngular();
		ng_datepicker = ng_dropdown
				.findElement(NgBy.model("data.dateDropDownInput", "[data-ng-app]"));
		assertThat(ng_datepicker, notNullValue());
		highlight(ng_datepicker);
		// System.err.println(ng_datepicker.getAttribute("innerHTML"));
		List<WebElement> ng_dataDates = ng_dropdown
				.findElements(NgBy.repeater("dateObject in data.dates"));
		assertThat(ng_dataDates.size(), equalTo(24));
		// Thread.sleep(10000);

		String timeOfDay = "6:00 PM";
		WebElement ng_hour = ng_datepicker
				.findElements(NgBy.cssContainingText("span.hour", timeOfDay)).get(0);
		assertThat(ng_hour, notNullValue());
		highlight(ng_hour);
		System.err.println("Hour of the day: " + ng_hour.getText());
		ng_hour.click();
		ngDriver.waitForAngular();
		String specificMinute = "6:35 PM";
		// reload,
		try {
			ng_datepicker = ng_dropdown
					.findElement(NgBy.model("data.dateDropDownInput", "[data-ng-app]"));
			assertThat(ng_datepicker, notNullValue());
			highlight(ng_datepicker);
			// System.err.println("Dropdown: " + ng_datepicker.getText());
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
			ng_minute = ng_datepicker
					.findElements(NgBy.cssContainingText("span.minute", specificMinute))
					.get(0);
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
			ng_datepicker = ngDriver
					.findElement(NgBy.model("data.dateDropDownInput", "[data-ng-app]"));
			highlight(ng_datepicker);
			System.err.println(
					"Selected Date/time : " + ng_datepicker.getAttribute("value"));
			// "Thu May 12 2016 18:35:00 GMT-0400 (Eastern Standard Time)"
			assertTrue(ng_datepicker.getAttribute("value").matches(
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
		driver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}
}
