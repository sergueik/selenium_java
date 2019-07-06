package com.github.sergueik.jprotractor.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Local file Integration tests
 *
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgLocalFileIntegrationTest {
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
	public static String localFile;
	static StringBuilder sb;
	static Formatter formatter;
	private static String rootCauseMessage;

	@BeforeClass
	public static void setup() throws IOException {
		sb = new StringBuilder();
		formatter = new Formatter(sb, Locale.US);
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

	// @Ignore
	@Test
	public void testDatePickerDirectSelect() {
		getPageContent("ng_datepicker.htm");
		// can not test official page - it is not an Angular app.
		// http://dalelotts.github.io/angular-bootstrap-datetimepicker/
		// test aborted with exception
		// Caused by: org.openqa.selenium.WebDriverException: [ng:test] no injector
		// found for element argument to getTestability
		WebElement ng_result = ngDriver
				.findElement(NgBy.model("data.inputOnTimeSet"));
		assertThat(ng_result, notNullValue());
		assertTrue(ng_result.getAttribute("type").matches("text"));
		ng_result.clear();
		NgWebElement ng_calendar = ngDriver
				.findElement(By.cssSelector(".input-group-addon"));
		assertThat(ng_calendar, notNullValue());
		highlight(ng_calendar);
		actions.moveToElement(ng_calendar.getWrappedElement()).click().build()
				.perform();
		// need a little more room
		int datepicker_width = 900;
		int datepicker_heght = 800;
		seleniumDriver.manage().window()
				.setSize(new Dimension(datepicker_width, datepicker_heght));
		NgWebElement ng_dropdown = ngDriver
				.findElement(By.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		List<WebElement> ng_weekDates = ng_dropdown
				.findElements(NgBy.repeater("dateObject in week.dates"));
		assertTrue(ng_weekDates.size() > 27);
		String monthDate = "12";
		WebElement dateElement = ng_dropdown
				.findElements(NgBy.cssContainingText("td.ng-binding", monthDate))
				.get(0);
		assertThat(dateElement, notNullValue());
		highlight(dateElement);
		System.err.println("Mondh Date: " + dateElement.getText());
		dateElement.click();
		NgWebElement ng_element = ng_dropdown
				.findElement(NgBy.model("data.inputOnTimeSet"));
		assertThat(ng_element, notNullValue());
		List<WebElement> ng_dataDates = ng_element
				.findElements(NgBy.repeater("dateObject in data.dates"));
		assertThat(ng_dataDates.size(), equalTo(24));
		String timeOfDay = "6:00 PM";
		WebElement ng_hour = ng_element
				.findElements(NgBy.cssContainingText("span.hour", timeOfDay)).get(0);
		assertThat(ng_hour, notNullValue());
		highlight(ng_hour);
		System.err.println("Hour of the day: " + ng_hour.getText());
		ng_hour.click();
		ngDriver.waitForAngular();
		String specificMinute = "6:35 PM";
		// reload,
		try {
			ng_element = ng_dropdown.findElement(NgBy.model("data.inputOnTimeSet"));
			assertThat(ng_element, notNullValue());
			highlight(ng_element);
		} catch (StaleElementReferenceException e) {
			// org.openqa.selenium.StaleElementReferenceException in Phantom JS
			// works fine with desktop browsers
			if (isCIBuild) {
				System.err.println("Ignoring StaleElementReferenceException");
			} else {
				throw (e);
			}
		}
		WebElement ng_minute;
		try {
			ng_minute = ng_element
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
			ng_result = ngDriver.findElement(NgBy.model("data.inputOnTimeSet"));
			highlight(ng_result);
			System.err
					.println("Selected Date/time : " + ng_result.getAttribute("value"));
			// "Thu May 12 2016 18:35:00 GMT-0400 (Eastern Standard Time)"
			assertTrue(ng_result.getAttribute("value").matches(
					"\\w{3} \\w{3} \\d{1,2} \\d{4} 18:35:00 GMT[+-]\\d{4} \\(.+\\)"));
		} catch (StaleElementReferenceException e) {
			if (isCIBuild) {
				System.err.println("Ignoring StaleElementReferenceException");
			} else {
				throw (e);
			}
		}
	}

	// @Ignore
	@Test
	public void testDatePickerNavigation() {
		getPageContent("ng_datepicker.htm");
		WebElement ng_result = ngDriver
				.findElement(NgBy.model("data.inputOnTimeSet"));
		assertThat(ng_result, notNullValue());
		assertTrue(ng_result.getAttribute("type").matches("text"));
		ng_result.clear();
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
		NgWebElement ng_display = ngDriver
				.findElement(NgBy.binding("data.previousViewDate.display"));
		assertThat(ng_display, notNullValue());
		String dateDattern = "\\d{4}\\-(\\w{3})";
		assertTrue(ng_display.getText().matches(dateDattern));
		highlight(ng_display);
		System.err.println("Current month: " + ng_display.getText());

		Pattern pattern = Pattern.compile(dateDattern);
		Matcher matcher = pattern.matcher(ng_display.getText());
		List<String> months = new ArrayList<>(
				Arrays.asList(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Dec", "Jan" }));
		String followingMonth = null;
		if (matcher.find()) {
			followingMonth = months
					.get(months.indexOf(matcher.group(1).toString()) + 1);
		}

		WebElement ng_right = ng_display.findElement(By.xpath(".."))
				.findElement(By.className("right"));
		assertThat(ng_right, notNullValue());
		highlight(ng_right);
		ng_right.click();
		ng_display = ngDriver
				.findElement(NgBy.binding("data.previousViewDate.display"));
		highlight(ng_display);
		assertThat(ng_display.getText(), containsString(followingMonth));
		System.err.println("Following month: " + ng_display.getText());
	}

	// @Ignore
	@Test
	public void testEvaluate() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_service.htm");
		for (WebElement currentElement : ngDriver
				.findElements(NgBy.repeater("person in people"))) {
			if (currentElement.getText().isEmpty()) {
				break;
			}
			WebElement personName = new NgWebElement(ngDriver, currentElement)
					.findElement(NgBy.binding("person.Name"));
			assertThat(personName, notNullValue());
			Object personCountry = new NgWebElement(ngDriver, currentElement)
					.evaluate("person.Country");
			assertThat(personCountry, notNullValue());
			System.err.println(personName.getText() + " " + personCountry.toString());
			if (personName.getText().indexOf("Around the Horn") >= 0) {
				assertThat(personCountry.toString(), containsString("UK"));
				highlight(personName);
			}
		}
	}

	@Ignore
	@Test
	public void testUpload1() {
		if (!isCIBuild) {
			return;
		}
		// This example interacts with custom 'fileModel' directive
		getPageContent("ng_upload1.htm");
		WebElement file = ngDriver.findElement(
				By.cssSelector("div[ng-controller = 'myCtrl'] > input[type='file']"));
		assertThat(file, notNullValue());
		String localPath = "C:/developer/sergueik/powershell_selenium/powershell/testfile.txt";
		file.sendKeys(localPath);
		WebElement button = ngDriver.findElement(NgBy.buttonText("Upload"));
		button.click();

		NgWebElement ng_file = new NgWebElement(ngDriver, file);
		assertThat(ng_file, notNullValue());
		try {
			Object myFile = ng_file.evaluate("myFile");
			if (myFile instanceof Map) {
				@SuppressWarnings("rawtypes")
				Map<?, ?> map = (Map) (myFile);
				for (Map.Entry<?, ?> entry : map.entrySet()) {
					String key = entry.getKey().toString();
					Object value = entry.getValue();
					System.err.println(key + " = " + value.toString());
				}
			} else {
				System.err.println(myFile.toString());
			}
		} catch (Exception e) {
			rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
			System.err.println("Exception:\n" + rootCauseMessage);
		}
		try {
			String script = "var e = angular.element(arguments[0]); var f = e.scope().myFile; return f.name";
			Object result = CommonFunctions.executeScript(script, ng_file);
			assertThat(result, notNullValue());
			System.err.println("myFile.name = " + result);
		} catch (Exception e) {
			rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
			System.err.println("Exception:\n" + rootCauseMessage);
		}
	}

	// @Ignore
	@Test
	public void testUpload3() {
		if (isCIBuild) {
			return;
		}
		// TODO: abort the test on timeout
		// http://stackoverflow.com/questions/2275443/how-to-timeout-a-thread
		getPageContent("ng_upload3.htm");
		WebElement drop = ngDriver.findElement(NgBy.binding("dropText"));
		assertThat(drop, notNullValue());
		highlight(drop);
		WebElement fileToUpload = ngDriver.findElement(By.id("fileToUpload"));
		assertThat(fileToUpload, notNullValue());
		assertTrue(fileToUpload.getAttribute("type").equalsIgnoreCase("file"));
		String localPath = "C:/developer/sergueik/powershell_selenium/powershell/testfile.txt";
		System.err.println("sendKeys :" + localPath);
		fileToUpload.sendKeys(localPath);
		@SuppressWarnings("unused")
		NgWebElement ng_fileToUpload = new NgWebElement(ngDriver, fileToUpload);
		// String script = "angular.element(this).scope().setFiles(this)";
		// Object result = CommonFunctions.executeScript(script,ng_fileToUpload);
		// assertThat(result, notNullValue());
		wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(
				By.cssSelector("input[ type='button' ][ value='Upload' ]"))));
		// Element is not currently visible and may not be manipulated
		WebElement button = ngDriver.findElement(
				By.cssSelector("input[ type='button' ][ value='Upload' ]"));
		System.err.println("Click");
		button.click();
	}

	// @Ignore
	@Test
	public void testEvaluateEvenOdd() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_table_even_odd.htm");
		for (WebElement currentRow : ngDriver
				.findElements(NgBy.repeater("x in names"))) {
			for (WebElement currentCell : currentRow.findElements(By.tagName("td"))) {
				System.err.println(currentCell.getTagName() + " '"
						+ currentCell.getText() + "' " + currentCell.getAttribute("style"));
				boolean odd = ((Boolean) new NgWebElement(ngDriver, currentCell)
						.evaluate("$odd")).booleanValue();
				if (odd) {
				} else {
				}
			}
		}
	}

	// @Ignore
	@Test
	public void testFindRepeaterElement() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_table2.htm");
		int length = 0;
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(
				ngDriver.findElement(NgBy.binding("filtered.length")).getText());
		if (matcher.find()) {
			length = Integer.parseInt(matcher.group(1).toString());
		}
		for (int cnt = 0; cnt != length; cnt++) {
			for (WebElement currentCell : ngDriver.findElements(
					NgBy.repeaterElement("client in filtered ", cnt, "client.name"))) {
				actions.moveToElement(currentCell).build().perform();
				highlight(currentCell);
			}
		}

		List<WebElement> elements = ngDriver.findElements(
				NgBy.repeaterElement("client in filtered ", length, "client.name"));

		assertThat(elements.size(), equalTo(0));
	}

	// @Ignore
	@Test
	public void testFindElementByRepeaterColumn() {
		if (!isCIBuild) {
			return;
		}
		seleniumDriver.navigate()
				.to("http://www.w3schools.com/angular/customers.php");
		System.err.println("Customers:" + seleniumDriver.getPageSource());
		getPageContent("ng_service.htm");
		ngDriver.waitForAngular();
		List<WebElement> countries = new ArrayList<>(ngDriver.findElements(
				NgBy.repeaterColumn("person in people", "person.Country")));
		System.err.println("Found Countries.size() = " + countries.size());
		assertTrue(countries.size() > 0);
		int cnt = 0;
		for (WebElement country : countries) {
			System.err.format("%s %s\n", country.getText(),
					(country.getText().equalsIgnoreCase("Mexico")) ? " *" : "");
			highlight(country);
			if (country.getText().equalsIgnoreCase("Mexico")) {
				cnt = cnt + 1;
			}
		}
		System.err.println("Mexico found " + cnt + " times");
		assertTrue(cnt == 3);
	}

	@Ignore
	@Test
	public void testFindSelectedtOptionWithAlert() {

		getPageContent("ng_selected_option.htm");
		List<WebElement> elements = ngDriver
				.findElements(NgBy.selectedOption("countSelected"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}

		WebElement element = elements.get(0);
		ngDriver.waitForAngular();

		assertThat(element, notNullValue());
		assertTrue(element.isDisplayed());
		System.err.println("Selected option: " + element.getText() + "\n"
				+ element.getAttribute("outerHTML"));
		assertThat(element.getText(), containsString("One"));

		for (WebElement option : ngDriver.findElements(
				NgBy.options("count.id as count.name for count in countList"))) {
			System.err.println("Available option: " + option.getText());
			if (option.getText().isEmpty()) {
				break;
			}
			if (option.getText().equalsIgnoreCase("two")) {
				System.err.println("Selecting option: " + option.getText());
				option.click();
				if (!isCIBuild) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				if (!isCIBuild) {
					try {
						alert = seleniumDriver.switchTo().alert();
						String alert_text = alert.getText();
						System.err.println("Accepted alert: " + alert_text);
						alert.accept();
					} catch (NoAlertPresentException ex) {
						System.err.println(ex.getStackTrace());
						return;
					}
				}
			}
		}
		ngDriver.waitForAngular();
		elements = ngDriver.findElements(NgBy.selectedOption("countSelected"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}

		element = elements.get(0);
		assertThat(element, notNullValue());
		System.err.println("Selected option: " + element.getText() + "\n"
				+ element.getAttribute("outerHTML"));
		assertThat(element.getText(), containsString("Two"));
		WebElement countSelected = ngDriver
				.findElement(NgBy.binding("countSelected"));
		assertThat(countSelected, notNullValue());
		// System.err.println(countSelected.getText() );
		int valueOfCountSelected = Integer
				.parseInt(new NgWebElement(ngDriver, countSelected)
						.evaluate("countSelected").toString());
		System.err.println("countSelected = " + valueOfCountSelected);
		assertThat(valueOfCountSelected, equalTo(2));
		if (!isCIBuild) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	// @Ignore
	@Test
	public void testFindSelectedtOption() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_select_array.htm");
		List<WebElement> elements = ngDriver
				.findElements(NgBy.selectedOption("myChoice"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}
		WebElement element = elements.get(0);
		ngDriver.waitForAngular();

		assertThat(element, notNullValue());
		assertTrue(element.isDisplayed());
		assertThat(element.getText(), containsString("three"));
		System.err.println("Selected option: " + element.getText());
	}

	// @Ignore
	@Test
	public void testChangeSelectedtOption() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_select_array.htm");

		for (WebElement option : ngDriver
				.findElements(NgBy.repeater("option in options"))) {
			System.err.println("available option: " + option.getText());
			if (option.getText().isEmpty()) {
				break;
			}
			if (option.getText().equalsIgnoreCase("two")) {
				System.err.println("selecting option: " + option.getText());
				option.click();
			}
		}
		ngDriver.waitForAngular();
		List<WebElement> elements = ngDriver
				.findElements(NgBy.selectedOption("myChoice"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}
		WebElement element = elements.get(0);
		assertThat(element, notNullValue());
		System.err.println("selected option: " + element.getText());
		assertThat(element.getText(), containsString("two"));
	}

	// @Ignore
	@Test
	public void testChangeSelectedRepeaterOption() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_repeat_selected.htm");
		for (WebElement option : ngDriver
				.findElements(NgBy.repeater("fruit in Fruits"))) {
			System.err.println("available option: " + option.getText());
			if (option.getText().isEmpty()) {
				break;
			}
			if (option.getText().equalsIgnoreCase("Orange")) {
				System.err.println("selecting option: " + option.getText());
				option.click();
			}
		}
		ngDriver.waitForAngular();

		// System.err.println("findElements(NgBy.selectedRepeaterOption(...))");
		List<WebElement> elements = ngDriver
				.findElements(NgBy.selectedRepeaterOption("fruit in Fruits"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}
		WebElement element = elements.get(0);
		assertThat(element, notNullValue());
		System.err.println("selected option: " + element.getText());
		assertThat(element.getText(), containsString("Orange"));

		/*
		 * Object fruitSelected = new
		 * NgWebElement(ngDriver,element).evaluate("fruit.Selected");
		 * System.err.println("fruit.Selected = " + fruitSelected.toString()) ;
		 * assertTrue(Boolean.parseBoolean(fruitSelected.toString()));
		 */

		// System.err.println("findElement(NgBy.selectedRepeaterOption(...))");
		NgWebElement ngRootWevelement = new NgWebElement(ngDriver,
				ngDriver.findElement(By.cssSelector("div[ng-app='myApp']")));
		List<NgWebElement> ngElements = ngRootWevelement
				.findNgElements(NgBy.selectedRepeaterOption("fruit in Fruits"));
		try {
			assertThat(elements.size(), equalTo(1));
		} catch (AssertionError e) {
			if (isCIBuild) {
				System.err.println("Skipped processing exception " + e.toString());
				return;
			} else {
				throw e;
			}
		}

		NgWebElement ngElement = ngElements.get(0);
		assertThat(ngElement, notNullValue());
		assertThat(ngElement.getWrappedElement(), notNullValue());
		System.err.println("selected option: " + ngElement.getText());

		// System.err.println("findNgElements(NgBy.selectedRepeaterOption(...))");
		ngElement = ngDriver
				.findElement(NgBy.selectedRepeaterOption("fruit in Fruits"));
		assertThat(ngElement, notNullValue());
		assertThat(ngElement.getWrappedElement(), notNullValue());
		System.err.println("selected option: " + ngElement.getText());
	}

	@Ignore
	@Test
	public void testMultiSelect2() {
		getPageContent("ng_multi_select2.htm");
		WebElement button = ngDriver.findElement(By.cssSelector(
				"multiselect-dropdown button[data-ng-click='openDropdown()']"));
		assertThat(button, notNullValue());
		button.click();
		// selectedRepeaterOption("option in options") will not work with this
		// directive
		List<WebElement> elements = ngDriver
				.findElements(NgBy.binding("option.name"));
		assertTrue(elements.size() > 0);
		// find what is selected based on the bootstrap class attribute
		String expression = "\\-remove\\-";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = null;
		int count = 0;
		for (WebElement element : elements) {
			NgWebElement ngElement = new NgWebElement(ngDriver, element);
			String optionName = ngElement.evaluate("option.name").toString();
			// switch to core Selenium
			WebElement element2 = element.findElement(By.tagName("span"));
			assertThat(element2, notNullValue());
			String classAttribute = element2.getAttribute("class");
			// System.err.println( "option.name = " + optionName + " " +
			// classAttribute);
			matcher = pattern.matcher(classAttribute);
			if (matcher.find()) {

			} else {
				System.err.println("selected: " + optionName);
				highlight(element);
				count++;
			}
		}
		elements = ngDriver.findElements(
				NgBy.repeaterColumn("country in SelectedCountries", "country.name"));
		assertTrue(elements.size() == count);
		elements.stream()
				.forEach(element -> System.err.format("%s\n", element.getText()));
	}

	@Ignore
	@Test
	public void testMultiSelect() {
		getPageContent("ng_multi_select.htm");
		WebElement element = ngDriver.findElement(NgBy.model("selectedValues"));
		// use core Selenium
		Select selectObj = new Select(element);
		List<String> data = new ArrayList<>();
		for (WebElement option : selectObj.getOptions()) {
			if (option.getText().isEmpty()) {
				break;
			}
			if (Boolean.parseBoolean(option.getAttribute("selected"))) {
				System.err.println("Selected:	'" + option.getText() + "' value = "
						+ option.getAttribute("value"));
				// option.click();
				data.add(option.getAttribute("value"));
			} else {
				System.err.println("Not selected: '" + option.getText() + "' value = "
						+ option.getAttribute("value"));
			}
		}

		String selectedValues = ngDriver.findElement(NgBy.binding("selectedValues"))
				.getText();
		System.err.println("Inspecting: " + selectedValues);
		for (int cnt = 0; cnt != data.size(); cnt++) {
			String value = data.get(cnt);
			String expression = "\\\"" + value + "\\\"";
			Pattern pattern = Pattern.compile(expression);
			Matcher matcher = pattern.matcher(selectedValues);
			assertTrue(matcher.find());
			System.err.println("Found \"" + value + "\"");
		}

		// change selected options
		data.clear();
		for (WebElement option : selectObj.getOptions()) {
			if (option.getText().isEmpty()) {
				break;
			}
			System.err.println("changing selection of " + option.getText());
			actions.keyDown(Keys.CONTROL).click(option).keyUp(Keys.CONTROL).build()
					.perform();
			ngDriver.waitForAngular();
			if (Boolean.parseBoolean(option.getAttribute("selected"))) {
				data.add(option.getAttribute("value"));
			}
		}

		selectedValues = ngDriver.findElement(NgBy.binding("selectedValues"))
				.getText();
		System.err.println("Inspecting: " + selectedValues);
		for (int cnt = 0; cnt != data.size(); cnt++) {
			String value = data.get(cnt);
			String expression = "\\\"" + value + "\\\"";
			Pattern pattern = Pattern.compile(expression);
			Matcher matcher = pattern.matcher(selectedValues);
			assertTrue(matcher.find());
			System.err.println("Found \"" + value + "\"");
		}
	}

	// @Ignore
	@Test
	public void testFindElementByRepeaterWithBeginEnd() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_repeat_start_end.htm");
		List<WebElement> elements = ngDriver
				.findElements(NgBy.repeater("definition in definitions"));
		assertTrue(elements.get(0).isDisplayed());
		assertThat(elements.get(0).getText(), containsString("Foo"));
		System.err.println(elements.get(0).getText());
	}

	@Ignore
	@Test
	public void testFindElementByOptions() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_options_with_object.htm");
		List<WebElement> elements = ngDriver
				.findElements(NgBy.options("c.name for c in colors"));
		assertTrue(elements.size() == 5);
		assertThat(elements.get(0).getText(), containsString("black"));
		System.err.println(elements.get(0).getText());
		assertThat(elements.get(1).getText(), containsString("white"));
		System.err.println(elements.get(1).getText());
	}

	// @Ignore
	@Test
	public void testFindElementByModel() {
		// NOTE: works with Angular 1.2.13, fails with Angular 1.4.9
		getPageContent("ng_pattern_validate.htm");
		WebElement input = ngDriver.findElement(NgBy.model("myVal"));
		input.clear();
		WebElement valid = ngDriver.findElement(NgBy.binding("form.value.$valid"));
		assertThat(valid.getText(), containsString("false"));
		System.err.println(valid.getText()); // valid: false
		WebElement pattern = ngDriver
				.findElement(NgBy.binding("form.value.$error.pattern"));
		assertThat(pattern.getText(), containsString("false"));
		System.err.println(pattern.getText()); // pattern: false
		WebElement required = ngDriver
				.findElement(NgBy.binding("!!form.value.$error.required"));
		assertThat(required.getText(), containsString("true"));
		System.err.println(required.getText()); // required: true

		input.sendKeys("42");
		valid = ngDriver.findElement(NgBy.binding("form.value.$valid"));
		assertThat(valid.getText(), containsString("true"));
		System.err.println(valid.getText()); // valid: true
		pattern = ngDriver.findElement(NgBy.binding("form.value.$error.pattern"));
		assertThat(pattern.getText(), containsString("false"));
		System.err.println(pattern.getText()); // pattern: false
		required = ngDriver
				.findElement(NgBy.binding("!!form.value.$error.required"));
		assertThat(required.getText(), containsString("false"));
		System.err.println(required.getText()); // required: false
	}

	// @Ignore
	// @Test
	// public void testFindRepeaterElement() {
	// if (!isCIBuild) {
	// return;
	// }
	// getPageContent("ng_basic.htm");
	// WebElement element =
	// ngDriver.findElement(NgBy.repeaterElement("item in items",1,"item.b"));
	// System.err.println("item[row='1'][col='b'] = " + element.getText());
	// highlight(element);
	// List <WebElement>elements =
	// ngDriver.findElements(NgBy.repeaterElement("item in items",5,"item.a"));
	// assertThat(elements.size(), equalTo(0));
	// }
	// NOTE: failing in Linux VM: PhantomJS has crashed
	// @Ignore
	@Test
	public void testElementTextIsGenerated() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_load_json_data.htm");
		WebElement name = ngDriver.findElement(NgBy.model("name"));
		highlight(name);
		name.sendKeys("John");
		name.sendKeys(Keys.TAB);
		// NOTE: explicitly done by getAttribute.
		// ngDriver.waitForAngular();
		WebElement greeting = ngDriver.findElement(NgBy.model("greeting"));
		highlight(greeting);
		// JavascriptExecutor js = (JavascriptExecutor) ngDriver.getWrappedDriver();
		// String greeting_text = js.executeScript("return arguments[0].value",
		// greeting).toString();
		// assertTrue(greeting_text.length() > 0);
		String greeting_text = greeting.getAttribute("value");
		System.err.println(greeting_text);
		assertTrue(greeting_text.length() > 0);
	}

	// @Ignore
	@Test
	public void testDropDownWatch() {
		// NOTE: works with Angular 1.2.13, fails withAngular 1.4.9
		getPageContent("ng_dropdown_watch.htm");
		String optionsCountry = "country for country in countries";
		List<WebElement> elementsCountries = ngDriver
				.findElements(NgBy.options(optionsCountry));
		assertThat(elementsCountries.size(), equalTo(3));

		for (WebElement country : elementsCountries) {
			if (country.getAttribute("value").isEmpty()) {
				continue;
			}
			assertTrue(country.getAttribute("value").matches("^\\d+$"));
			assertTrue(country.getText().matches("(?i:China|United States)"));
			System.err.println("country = " + country.getText() + ", value: "
					+ country.getAttribute("value"));
		}
		String optionsState = "state for state in states";
		WebElement elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertFalse(elementState.isEnabled());

		Select selectCountries = new Select(
				ngDriver.findElement(NgBy.model("country")));
		selectCountries.selectByVisibleText("china");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		WebElement selectedOptionCountry = ngDriver
				.findElement(NgBy.selectedOption(optionsCountry));
		try {
			assertThat(selectedOptionCountry, notNullValue());
			assertThat(selectedOptionCountry.getText(), equalTo("china"));
		} catch (NullPointerException e) {
		}
		assertTrue(elementState.isEnabled());

		List<WebElement> elementsStates = ngDriver
				.findElements(NgBy.options(optionsState));
		assertThat(elementsStates.size(), equalTo(3));
		for (WebElement state : elementsStates) {
			if (state.getAttribute("value").isEmpty()) {
				continue;
			}
			assertTrue(state.getAttribute("value").matches("^\\d+$"));
			assertTrue(state.getText().matches("(?i:BeiJing|ShangHai)"));
			System.err.println("state = " + state.getText());
		}
		Select selectStates = new Select(ngDriver.findElement(NgBy.model("state")));
		for (WebElement option : selectStates.getOptions()) {
			if (option.getText().isEmpty()) {
				break;
			}
			System.err.println("option: '" + option.getText() + "', value : "
					+ option.getAttribute("value"));
			if (option.getText().equalsIgnoreCase("ShangHai")) {
				option.click();
			}
		}
		try {
			// TODO: fails:
			selectCountries.selectByVisibleText("ShangHai");
		} catch (NoSuchElementException e) {
		}

		List<WebElement> cities = ngDriver
				.findElements(NgBy.options("city for city in cities"));
		for (WebElement option : cities) {
			if (option.getText().isEmpty()) {
				break;
			}
			System.err.println("option: '" + option.getText() + "', value : "
					+ option.getAttribute("value"));
		}

		if (!isCIBuild) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	// @Ignore
	@Test
	public void testFindRepeaterRows() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_todo.htm");
		String todos_repeater = "todo in todoList.todos";
		List<WebElement> todos = ngDriver
				.findElements(NgBy.repeaterRows(todos_repeater, 1));
		assertTrue(todos.size() > 0);
		System.err.println("TODO: " + todos.get(0).getText());
		todos = ngDriver.findElements(NgBy.repeaterRows(todos_repeater, -1));
		assertThat(todos.size(), equalTo(0));
		List<WebElement> todos_check = ngDriver
				.findElements(NgBy.repeater(todos_repeater));
		todos = ngDriver.findElements(
				NgBy.repeaterRows(todos_repeater, todos_check.size() + 1));
		assertThat(todos.size(), equalTo(0));
	}

	// @Ignore
	@Test
	public void testOrderByColumnField() {
		// Given there is a sorted data grid
		getPageContent("ng_headers_sort_example1.htm");
		String[] headers = new String[] { "First Name", "Last Name", "Age" };

		@SuppressWarnings("unused")
		Hashtable<String, String> header_columns = new Hashtable<String, String>();

		// Without Protractor:
		// NOTE: not really needed
		// header_columns.put("First Name", "emp.firstName");
		// header_columns.put("Last Name", "emp.lastName");
		// header_columns.put("Age", "age");
		// String[] sortOrders = new String[]{"descending", "ascending"};
		for (String header : headers) {
			for (int cnt = 0; cnt != 2; cnt++) {
				// When grid is sorted by <header> in <sort order>
				WebElement headerelement = ngDriver
						.findElement(By.xpath("//th/a[contains(text(),'" + header + "')]"));
				headerelement.click();
				// Then Angular uses the column <binding> to sort rows in <sort order>
				// order
				List<WebElement> rows = ngDriver
						.findElements(NgBy.repeater("emp in data.employees"));
				WebElement row = rows.get(0);
				String field = row.getAttribute("ng-order-by");
				NgWebElement ng_row = new NgWebElement(ngDriver, row);
				boolean reverseSort = Boolean
						.parseBoolean(ng_row.evaluate("reverseSort").toString());
				// Without Protractor:
				// Then the data is sorted by <header> in <sort order>
				// String binding = header_columns.get(header);
				String binding = "emp." + ng_row.evaluate(field);
				System.err.println("Sorted by: " + field + ": " + binding
						+ ", Reverse:	" + reverseSort /* sortOrders[cnt] */);

				// Iterate over all rows, selected column
				for (WebElement emp : rows) {
					NgWebElement ng_emp = new NgWebElement(ngDriver, emp);
					try {
						WebElement empFieldElement = ng_emp
								.findElement(NgBy.binding(binding));
						assertThat(empFieldElement, notNullValue());
						System.err.println(empFieldElement.getText());
					} catch (Exception e) {

						rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
						System.err.println("Exception:\n" + rootCauseMessage);

					}
				}

				// Compare selected column of first and last row
				WebElement first_row_cell = ngDriver.findElement(
						NgBy.repeaterElement("emp in data.employees", 0, binding));
				WebElement last_row_cell = ngDriver.findElement(NgBy.repeaterElement(
						"emp in data.employees", rows.size() - 1, binding));
				System.err.println("First row cell: " + first_row_cell.getText());
				System.err.println("Last row cell: " + last_row_cell.getText());
				if (reverseSort) {
					System.err.println("Order: descending");
					System.err.println("Verified " + (first_row_cell.getText()
							.compareTo(last_row_cell.getText()) > 0));
				} else {
					System.err.println("Order: ascending");
					System.err.println("Verified " + (first_row_cell.getText()
							.compareTo(last_row_cell.getText()) < 0));
				}
			}
		}
	}

	// TODO: separate into class AngularUISelect.java
	@Ignore
	@Test
	public void testAngularUISelectHandleSelectedAndAvailable() {
		getPageContent("ng_ui_select_example1.htm");
		List<WebElement> selectedColors = ngDriver
				.findElements(NgBy.repeater("$item in $select.selected"));

		for (WebElement selectedColor : selectedColors) {
			NgWebElement ngSelectedColor = new NgWebElement(ngDriver, selectedColor);
			highlight(selectedColor);
			Object itemColor = ngSelectedColor.evaluate("$item");
			System.err.println("selected color: " + itemColor.toString());
		}
		WebElement search = ngDriver.findElement(NgBy.model("$select.search"));
		search.click();
		ngDriver.waitForAngular();
		List<WebElement> availableColors = ngDriver
				.findElements(By.cssSelector("div[role='option']"));
		for (WebElement availableColor : availableColors) {
			NgWebElement ngAvailableColor = new NgWebElement(ngDriver,
					availableColor);
			highlight(availableColor);
			int availableColorIndex = -1;
			try {
				availableColorIndex = Integer
						.parseInt(ngAvailableColor.evaluate("$index").toString());
			} catch (Exception e) {
			}
			System.err.println("available color:" + availableColor.getText()
					+ ",index = " + availableColorIndex);
		}
	}

	@Ignore
	@Test
	public void testAngularUISelectHandleSearch() {
		getPageContent("ng_ui_select_example1.htm");
		String searchText = "Ma";
		WebElement search = ngDriver
				.findElement(By.cssSelector("input[type='search']"));
		search.sendKeys(searchText);
		ngDriver.waitForAngular();
		List<WebElement> availableColors = ngDriver
				.findElements(By.cssSelector("div[role='option']"));
		for (WebElement availableColor : availableColors) {
			@SuppressWarnings("unused")
			NgWebElement ngAvailableColor = new NgWebElement(ngDriver,
					availableColor);
			highlight(availableColor);
			assertThat(availableColor.getText(), containsString(searchText));
			System.err.println(
					"search = " + searchText + ", found :" + availableColor.getText());
		}
	}

	// @Ignore
	@Test
	public void testAngularUISelectHandleDeselect() {
		getPageContent("ng_ui_select_example1.htm");
		List<WebElement> selectedColors = ngDriver
				.findElements(NgBy.repeater("$item in $select.selected"));
		while (selectedColors.size() != 0) {
			selectedColors = ngDriver
					.findElements(NgBy.repeater("$item in $select.selected"));
			for (WebElement selectedColor : selectedColors) {
				NgWebElement ngSelectedColor = new NgWebElement(ngDriver,
						selectedColor);
				Object itemColor = ngSelectedColor.evaluate("$item");
				System.err.println("deselecting color: " + itemColor.toString());
				WebElement ng_close = ngSelectedColor
						.findElement(By.cssSelector("span[class *='close']"));
				assertThat(ng_close, notNullValue());
				assertThat(ng_close.getAttribute("ng-click"), notNullValue());
				assertThat(ng_close.getAttribute("ng-click"),
						containsString("removeChoice"));
				highlight(ng_close);
				ng_close.click();
				ngDriver.waitForAngular();
			}
		}
		System.err.println("Nothing is selected");
	}

	// @Ignore
	@Test
	public void testFindAllBindings() {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_directive_binding.htm");

		WebElement container = ngDriver.getWrappedDriver()
				.findElement(By.cssSelector("body div"));
		assertThat(container, notNullValue());
		// will show class="ng-binding" added to each node
		System.err.println(container.getAttribute("innerHTML"));
		List<WebElement> names = ngDriver.findElements(NgBy.binding("name"));
		assertTrue(names.size() == 5);

		for (WebElement name : names) {
			// will show class="ng-binding" added to every node
			System.err.println(name.getAttribute("outerHTML"));
			System.err.println(getIdentity(name));
		}
	}

	// @Ignore
	@Test
	public void testHover() {
		getPageContent("ng_hover1.htm");
		// Hover over menu
		WebElement element = seleniumDriver.findElement(By.id("hover"));
		highlight(element);
		actions.moveToElement(element).build().perform();
		// Wait for the Angular 'hovering' property to get evaluated to true
		WebDriverWait wait2 = new WebDriverWait(seleniumDriver, 120);
		wait2.pollingEvery(50, TimeUnit.MILLISECONDS);
		wait2.until(new ExpectedCondition<Boolean>() {
			// NOTE: 'until' only prints "hovering: true" and never "hovering: false"
			@Override
			public Boolean apply(WebDriver d) {
				NgWebDriver ngD = new NgWebDriver(d);
				// org.openqa.selenium.WebDriverException: The WebDriver instance must
				// implement JavaScriptExecutor interface.
				NgWebElement ng_element = ngD.findElement(By.id("hover"));
				Object value = ng_element.evaluate("hovering");
				System.err.println("hovering: " + value.toString());
				return Boolean.parseBoolean(value.toString());
			}
		});

		actions.moveByOffset(0, 300).build().perform();
		// NOTE: cannot find div[ng-show='true'] nor div[show='true']
		// Wait for the tooltip div to get visible
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		actions.moveToElement(element).build().perform();
		try {
			wait.until(ExpectedConditions.visibilityOf(seleniumDriver
					.findElement(By.cssSelector("div[ng-show='hovering']"))));
		} catch (NoSuchElementException e) {
		}
	}

	// @Ignore
	@Test
	public void testDropDown() {
		if (!isCIBuild) {
			return;
		}
		// TODO: works with Angular 1.2.13, fails with Angular 1.4.9
		getPageContent("ng_dropdown.htm");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		String optionsCountry = "country for (country, states) in countries";
		List<WebElement> elementsCountries = ngDriver
				.findElements(NgBy.options(optionsCountry));
		assertThat(elementsCountries.size(), equalTo(4));
		for (WebElement country : elementsCountries) {
			if (country.getAttribute("value").isEmpty()) {
				continue;
			}
			assertTrue(
					country.getAttribute("value").matches("(?i:India|Australia|Usa)"));
			System.err.println("country = " + country.getAttribute("value"));
		}
		String optionsState = "state for (state,city) in states";
		WebElement elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertThat(elementState.getText().toLowerCase(Locale.getDefault()),
				containsString("select"));
		assertTrue(!elementState.isEnabled());

		WebElement element = ngDriver.findElement(NgBy.options(optionsCountry));
		assertTrue(element.isEnabled());
		Select selectCountries = new Select(
				ngDriver.findElement(NgBy.model("states")));

		selectCountries.selectByValue("Australia");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		WebElement selectedOptionCountry = ngDriver
				.findElement(NgBy.selectedOption(optionsCountry));
		try {
			assertThat(selectedOptionCountry, notNullValue());
		} catch (AssertionError e) {
			// TODO
		}
		elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertTrue(elementState.isEnabled());
		List<WebElement> elementsStates = ngDriver
				.findElements(NgBy.options(optionsState));
		assertThat(elementsStates.size(), equalTo(3));
		for (WebElement state : elementsStates) {
			if (state.getAttribute("value").isEmpty()) {
				continue;
			}
			assertTrue(
					state.getAttribute("value").matches("(?i:New South Wales|Victoria)"));
			System.err.println("state = " + state.getAttribute("value"));
		}
	}

	// @Ignore
	@Test
	public void testDragAndDrop() {
		// TODO: investigate the failure under TRAVIS
		assumeFalse(isCIBuild);
		getPageContent("ng_drag_and_drop1.htm");

		List<WebElement> ng_cars = ngDriver
				.findElements(NgBy.repeater("car in models.cars"));

		NgWebElement ng_basket = new NgWebElement(ngDriver,
				seleniumDriver.findElement(By.xpath("//*[@id='my-basket']")));
		highlight(ng_basket.getWrappedElement());
		for (WebElement ng_car : ng_cars) {
			if (ng_car.getText().isEmpty()) {
				break;
			}
			highlight(ng_car);
			actions.moveToElement(ng_car).build().perform();
			actions.clickAndHold(ng_car).moveToElement(ng_basket.getWrappedElement())
					.release().build().perform();
			ngDriver.waitForAngular();
			// System.err.println(basket.getAttribute("innerHTML"));
			List<WebElement> ng_cars_basket = ng_basket
					.findElements(NgBy.repeater("car in models.basket"));
			NgWebElement ng_car_basket = new NgWebElement(ngDriver,
					ng_cars_basket.get(ng_cars_basket.size() - 1));
			if (ng_car_basket.getText().isEmpty()) {
				break;
			}
			// {{ car.name }} - {{ car.modelYear }} ( {{ car.price | currency }} )
			System.err.format("%s - %s ( %s )\n", ng_car_basket.evaluate("car.name"),
					ng_car_basket.evaluate("car.modelYear"),
					ng_car_basket.evaluate("car.price | currency"));
		}
	}

	// @Ignore
	@Test
	public void testDragAndDropSimulate() {
		getPageContent("ng_drag_and_drop1.htm");

		WebElement basket = seleniumDriver
				.findElement(By.xpath("//*[@id='my-basket']"));
		highlight(basket);

		for (WebElement ng_car : ngDriver
				.findElements(NgBy.repeater("car in models.cars"))) {
			if (ng_car.getText().isEmpty()) {
				break;
			}
			highlight(ng_car);
			actions.moveToElement(ng_car).build().perform();
			// does not work
			dragAndDrop(ng_car, basket);
			ngDriver.waitForAngular();
		}
	}

	@Test
	public void testModaulWithAlertWait() {
		// NOTE: also available as http://fiddle.jshell.net/alexsuch/RLQhh/show/
		// see also:
		// https://stackoverflow.com/questions/27416044/simple-popup-by-using-angular-js/27417065#comment43276038_27416044
		getPageContent("ng_modal2.htm");
		WebElement showDialogButton = ngDriver
				.findElement(NgBy.buttonText("Open modal"));
		showDialogButton.click();

		WebElement titleElement = wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(NgBy.binding("title"))));

		assertThat(titleElement, notNullValue());
		assertTrue(titleElement.isDisplayed());
		highlight(titleElement);
		WebElement dialogElement = titleElement.findElement(By.xpath("../.."));
		System.err.println(
				"Dialog Element contents: " + dialogElement.getAttribute("outerHTML"));
		Map<String, String> formData = new HashMap<>();
		formData.put("email", "test_user@rambler.ru");
		formData.put("password", "secret");
		List<String> formElementIds = new ArrayList<>(
				Arrays.asList(new String[] { "email", "password" }));

		for (String formElementId : formElementIds) {

			List<WebElement> labelElements = dialogElement.findElements(By
					.cssSelector(String.format("form label[ for='%s']", formElementId)));
			assertThat(labelElements.size(), equalTo(1));
			highlight(labelElements.get(0));

			WebElement inputElement = dialogElement.findElement(
					By.cssSelector(String.format("form input#%s", formElementId)));
			assertThat(inputElement, notNullValue());
			highlight(inputElement);
			inputElement.sendKeys(formData.get(formElementId));
		}
		sleep(3000);
		// close the dialog
		WebElement submitButton = dialogElement
				.findElement(By.cssSelector("button[type='submit']")); //
		submitButton.click();

	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	// NOTE: a different implementation exists in CommonFunctions
	private static void getPageContent(String pagename) {
		String baseUrl = CommonFunctions.getPageContent(pagename);
		ngDriver.navigate().to(baseUrl);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

	private static String getIdentity(WebElement element) {
		String result = null;
		String script = "return angular.identity(angular.element(arguments[0])).html();";
		// returns too little HTML information in Java
		try {
			result = CommonFunctions.executeScript(script, element).toString();
		} catch (Exception e) {
		}
		return result;
	}

	private static void dragAndDrop(WebElement sourceElement,
			WebElement destinationElement) {
		String script = "simulateDragDrop.js";
		try {
			CommonFunctions.executeScript(CommonFunctions.getScriptContent(script),
					sourceElement, destinationElement);
		} catch (Exception e) {
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
