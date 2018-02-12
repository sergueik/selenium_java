package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on:
// https://raw.githubusercontent.com/TsvetomirSlavov/DynamicDataTablesAndCallendarsTsvetomir/master/src/main/java/DatePickerAndHighlightAndScroll.java
public class DatePickerTest extends BaseTest {

	public String baseURL = "https://www.skyscanner.com";
	private Calendar cal = Calendar.getInstance();
	private Date today = new Date();
	private SimpleDateFormat simpDate = new SimpleDateFormat(
			"MMMMM" /* EEEE MMMMM dd yyyy kk:mm:ss */);

	@BeforeClass
	public void beforeClass() throws IOException {
		cal.setTime(today);
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
		try {
			driver.manage().window().fullscreen();
		} catch (WebDriverException e) {
			System.err.println("Exception (ignored) " + e.toString());
			// unimplemented command
			// Firefox: org.openqa.selenium.UnsupportedCommandException
		}

		// NOTE: with Chrome, occasionally breaks the test, redirecting the user to
		// 'As you were browsing www.skyscanner.com something about your browser
		// made us think you were a bot'
		// page
		try {
			// press F11 key of keyboard to switch the browser to full screen
			Robot robot = new Robot();
			sleep(2000);
			robot.keyPress(KeyEvent.VK_F11);
		} catch (AWTException e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	// chooses a date in the middle of the month next year to exercise browsing
	// through months and inspecting dates
	@Test(priority = 1, enabled = true)
	public void datePickTest() {

		wait.until((WebDriver d) -> {
			WebElement e = null;
			try {
				e = d.findElement(By.id("js-depart-input"));
			} catch (Exception ex) {
				return null;
			}
			return (e.isDisplayed()) ? e : null;
		});
		driver.findElement(By.id("js-depart-input")).click();
		// adjust the browser window
		scroll(15, 100);
		// zoomMinus();
		sleep(1000);
		;
		String expectedMonth = String.format("%s %s", simpDate.format(today),
				cal.get(Calendar.YEAR) + 1);
		System.err.println("Scrolling to: " + expectedMonth);
		String currentMonth = driver
				.findElement(By.cssSelector("div.depart span.current")).getText();
		// TODO: coverage
		if (expectedMonth.equals(currentMonth)) {
			System.out.println("Intended month is already current!");
		} else {
			for (int i = 1; i < 12; i++) {
				driver.findElement(By.cssSelector("div.depart button.next")).click();
				sleep(500);
				currentMonth = driver
						.findElement(By.cssSelector("div.depart span.current")).getText();
				if (expectedMonth.equals(currentMonth)) {
					highlight(
							driver.findElement(By.cssSelector("div.depart span.current")));
					System.out.println("Selected: " + currentMonth);
					break;
				}
			}
		}
		sleep(1000);
		String expectedDate = String.format("%s-%s-15", cal.get(Calendar.YEAR) + 1,
				cal.get(Calendar.MONTH) + 1);
		System.err.println("Locating: " + expectedDate);
		WebElement datePicker = driver.findElement(
				By.cssSelector("div.depart div.container-body table tbody"));
		List<WebElement> dates = datePicker.findElements(By.tagName("td"));
		System.err
				.println("Start with date : " + dates.get(0).getAttribute("data-id"));

		for (WebElement date : dates) {
			String calDate = date.getAttribute(("data-id"));
			if (calDate.equals(expectedDate)) {
				flash(date);
				date.click();
				System.out.println("Date Selected: " + calDate);
				break;
			}
		}
		sleep(1000);
	}

	// Scroll
	public void scroll(final int x, final int y) {
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i <= x; i = i + 50) {
			js.executeScript("scroll(" + i + ",0)");
		}
		for (int j = 0; j <= y; j = j + 50) {
			js.executeScript("scroll(0," + j + ")");
		}
	}

	// Zoom current browser window -1 via keyboard shortcut
	public void zoomMinus() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.SUBTRACT).perform();
		actions = new Actions(driver);
		actions.keyUp(Keys.CONTROL).perform();
	}

}
