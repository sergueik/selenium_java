package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Integration tests of Calculator http://juliemr.github.io/protractor-demo/
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgCalculatorTest {

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
	private static String baseUrl = "http://juliemr.github.io/protractor-demo/";

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
	public void testAddition() {
		NgWebElement element = ngDriver.findElement(NgBy.model("first"));
		assertThat(element, notNullValue());
		highlight(element);
		element.sendKeys("40");
		element = ngDriver.findElement(NgBy.model("second"));
		assertThat(element, notNullValue());
		highlight(element);
		element.sendKeys("2");
		element = ngDriver
				.findElement(NgBy.options("value for (key, value) in operators"));
		assertThat(element, notNullValue());
		element.click();
		element = ngDriver.findElement(NgBy.buttonText("Go!"));
		assertThat(element, notNullValue());
		assertThat(element.getText(), containsString("Go"));
		element.click();
		element = ngDriver.findElement(NgBy.binding("latest"));
		assertThat(element, notNullValue());
		assertThat(element.getText(), equalTo("42"));
		highlight(element);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

}