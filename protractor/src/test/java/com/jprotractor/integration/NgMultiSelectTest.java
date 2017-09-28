package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Tests of Native AngularJS multiselect directive
 * https://github.com/amitava82/angular-multiselect
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public class NgMultiSelectTest {
	private static String fullStackTrace;
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
	private static String baseUrl = "http://amitava82.github.io/angular-multiselect/";

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

	// @Ignore
	@Test
	public void testSelectCarsOneByOne() {

		// Given we are using multi select directive to pick cars
		NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
		assertThat(ng_directive, notNullValue());
		assertThat(ng_directive.getTagName(), equalTo("am-multiselect"));
		WebElement toggleSelect = ng_directive
				.findElement(NgBy.buttonText("Select Some Cars"));
		assertThat(toggleSelect, notNullValue());
		assertTrue(toggleSelect.isDisplayed());
		toggleSelect.click();

		// After selecting every car one car at a time
		List<WebElement> cars = ng_directive
				.findElements(NgBy.repeater("i in items"));
		// NOTE: not "c.name for c in cars" that one finds in the am-multiselect tag
		int selectedCarCount = 0;
		for (int rowNum = 0; rowNum != cars.size(); rowNum++) {
			NgWebElement ng_car = ngDriver
					.findElement(NgBy.repeaterElement("i in items", rowNum, "i.label"));
			System.err.println("* " + ng_car.evaluate("i.label").toString());
			CommonFunctions.highlight(ng_car);
			ng_car.click();
			selectedCarCount++;
		}
		// Until all cars are selected
		assertThat(ng_directive.findElements(NgBy.repeater("i in items")).size(),
				equalTo(selectedCarCount));
		// Then the button text shows the total number of cars selected
		WebElement button = ngDriver
				.findElement(By.cssSelector("am-multiselect > div > button"));
		String buttonTextPattern = "There are (\\d+) car\\(s\\) selected";
		String buttonText = button.getText();
		assertTrue(buttonText.matches(buttonTextPattern));
		System.err.println(buttonText);
		int carCount = 0;
		Pattern pattern = Pattern.compile(buttonTextPattern);
		Matcher matcher = pattern.matcher(buttonText);
		if (matcher.find()) {
			carCount = Integer.parseInt(matcher.group(1));
		}
		assertThat(carCount, equalTo(selectedCarCount));
	}

	// @Ignore
	@Test
	public void testSelectAll() {

		// Given we are using multiselect directive for selecting cars
		NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
		assertThat(ng_directive, notNullValue());
		assertTrue(ng_directive.getTagName().equals("am-multiselect"));
		WebElement toggleSelect = ng_directive
				.findElement(By.cssSelector("button[ng-click='toggleSelect()']"));
		assertThat(toggleSelect, notNullValue());
		assertTrue(toggleSelect.isDisplayed());
		toggleSelect.click();
		// When selected all cars via 'check all' link
		wait.until(ExpectedConditions.visibilityOf(ng_directive
				.findElement(By.cssSelector("button[ng-click='checkAll()']"))));
		WebElement checkAll = ng_directive
				.findElement(By.cssSelector("button[ng-click='checkAll()']"));
		assertThat(checkAll, notNullValue());
		assertTrue(checkAll.isDisplayed());
		CommonFunctions.highlight(checkAll);
		checkAll.click();
		// Then every car is selected
		List<WebElement> cars = ng_directive
				.findElements(NgBy.repeater("i in items"));
		int selectedCarCount = 0;
		for (WebElement car : cars) {
			assertTrue(
					(boolean) (new NgWebElement(ngDriver, car).evaluate("i.checked")));
			System.err.println("* " + car.getText());
			selectedCarCount++;
		}
		assertThat(cars.size(), equalTo(selectedCarCount));
		// And the button text shows the total number of cars selected
		WebElement button = ngDriver
				.findElement(By.cssSelector("am-multiselect > div > button"));
		String buttonTextPattern = "There are (\\d+) car\\(s\\) selected";
		String buttonText = button.getText();
		assertTrue(buttonText.matches(buttonTextPattern));
		System.err.println(buttonText);
		int carCount = 0;
		Pattern pattern = Pattern.compile(buttonTextPattern);
		Matcher matcher = pattern.matcher(buttonText);
		if (matcher.find()) {
			carCount = Integer.parseInt(matcher.group(1));
		}
		assertThat(carCount, equalTo(selectedCarCount));
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

}
