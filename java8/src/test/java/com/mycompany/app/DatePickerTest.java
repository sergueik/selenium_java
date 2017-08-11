package com.mycompany.app;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

// origin: https://raw.githubusercontent.com/TsvetomirSlavov/DynamicDataTablesAndCallendarsTsvetomir/master/src/main/java/DatePickerAndHighlightAndScroll.java
public class DatePickerTest extends BaseTest {

	public String baseURL = "https://www.skyscanner.com";

	@BeforeClass
	public void beforeClass() throws IOException {
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

	@Test(priority = 1, enabled = true)
	public void datePickTest() {
		wait.until((WebDriver driver) -> {
			WebElement element = null;
			try {
				element = driver.findElement(By.id("js-depart-input"));
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		});
		//
		driver.findElement(By.id("js-depart-input")).click();
		scroll(15, 100);
		// zoomMinus();
		sleep(1000);
		String expectedMonth = "December 2017";
		String currentMonth = driver
				.findElement(By.cssSelector("div.depart span.current")).getText();
		if (expectedMonth.equals(currentMonth)) {
			System.out.println("Month is already selected!");
		} else {
			for (int i = 1; i < 12; i++) {
				driver.findElement(By.cssSelector("div.depart button.next")).click();
				sleep(500);
				currentMonth = driver
						.findElement(By.cssSelector("div.depart span.current")).getText();
				if (expectedMonth.equals(currentMonth)) {
					System.out.println("Month Selected: " + currentMonth);
					break;
				}
			}
		}
		sleep(1000);
		WebElement datePicker = driver.findElement(
				By.cssSelector("div.depart div.container-body table tbody"));
		List<WebElement> dates = datePicker.findElements(By.tagName("td"));
		for (WebElement date : dates) {
			String calDate = date.getAttribute(("data-id"));
			if (calDate.equals("2017-12-30")) {
				flash(date);
				date.click();
				System.out.println("Date Selected: " + calDate);
				break;
			}
		}
		sleep(1000);
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 3; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor, element);
		}
	}

	public void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'",
				element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
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
