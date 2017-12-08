package com.github.sergueik.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
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

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;

/**
 * Integration tests of Angular TODO List http://jaykanakiya.com/demos/angular-js-todolist/
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgTodoListIntegrationTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 800;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	private static String baseUrl = "http://jaykanakiya.com/demos/angular-js-todolist/";

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
	public void testCountItems() {
		List<WebElement> elements = ngDriver
				.findElements(NgBy.cssContainingText("span.todoName", "Angular-js"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), equalTo(2));

		elements.stream().forEach(element -> {
			actions.moveToElement(element);
			highlight(element);
			System.err.println(element.getText());
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

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