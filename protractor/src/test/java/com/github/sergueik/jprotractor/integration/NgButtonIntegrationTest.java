package com.github.sergueik.jprotractor.integration;

// import static com.jcabi.matchers.RegexMatchers;
import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Local file Integration tests
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class NgButtonIntegrationTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static boolean state = false;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	public static String localFile;

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

	@Test
	public void testButtonNgIf() {
		// if (isCIBuild) { // Alert not handled by PhantomJS
		// return;
		// }
		getPageContent("ng_watch_ng_if.htm");

		WebElement button = seleniumDriver
				.findElement(By.cssSelector("button.btn"));
		ngDriver.waitForAngular();
		CommonFunctions.highlight(button);
		NgWebElement ng_button = new NgWebElement(ngDriver, button);
		assertThat(ng_button, notNullValue());
		assertThat(ng_button.getAttribute("ng-click"),
				equalTo("house.frontDoor.open()"));
		assertThat(ng_button.getText(), equalTo("Open Door"));
		ngDriver.waitForAngular();
		try {
			state = ((Boolean) ng_button.evaluate("!house.frontDoor.isOpen"))
					.booleanValue();
		} catch (Exception ex) {
			System.err.println(
					"evaluate was not handled : " + ex.getStackTrace().toString());
		}
		assertTrue(state);
		button.click();
		try {
			// confirm alert
			seleniumDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present - ignore
		} catch (WebDriverException ex) {
			System.err
					.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		button = seleniumDriver.findElement(By.cssSelector("button.btn"));
		CommonFunctions.highlight(button);
		ng_button = new NgWebElement(ngDriver, button);
		assertThat(ng_button, notNullValue());
		assertThat(ng_button.getAttribute("ng-click"),
				equalTo("house.frontDoor.close()"));
		assertThat(ng_button.getText(), equalTo("Close Door"));
		try {
			state = ((Boolean) ng_button.evaluate("house.frontDoor.isOpen"))
					.booleanValue();
		} catch (Exception ex) {
			// [JavaScript Error: "e is null"] ?
			System.err.println(
					"evaluate was not handled : " + ex.getStackTrace().toString());
		}
		assertTrue(state);
	}

	@Test
	public void testButtonStateText() {
		// if (isCIBuild) { // Alert not handled by PhantomJS
		// return;
		// }
		getPageContent("ng_watch_ng_if.htm");
		WebElement button = seleniumDriver
				.findElement(By.cssSelector("button.btn"));
		ngDriver.waitForAngular();
		CommonFunctions.highlight(button);
		NgWebElement ng_status = ngDriver
				.findElement(NgBy.binding("house.frontDoor.isOpen"));
		CommonFunctions.highlight(ng_status);
		assertTrue(ng_status.getText().matches("The door is closed"));
		System.err.println("Initially: " + ng_status.getText());
		button.click();
		try {
			// confirm alert
			seleniumDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present - ignore
		} catch (WebDriverException ex) {
			System.err
					.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		ng_status = ngDriver.findElement(NgBy.binding("house.frontDoor.isOpen"));
		CommonFunctions.highlight(ng_status);
		System.err.println("Finally: " + ng_status.getText());
		assertThat(ng_status.getText(), matchesPattern(".+open"));
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void getPageContent(String pagename) {
		String baseUrl = CommonFunctions.getPageContent(pagename);
		ngDriver.navigate().to(baseUrl);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}
}
