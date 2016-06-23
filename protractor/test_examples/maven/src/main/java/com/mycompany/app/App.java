package com.mycompany.app;

import org.apache.commons.lang.StringUtils;
import static java.lang.Boolean.*;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.RuntimeException;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.Platform;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

public class App {

	public static VideoRecorder recorder;
	private static WebDriver seleniumDriver;
	private static NgWebDriver ngDriver;
	public static String baseUrl2 = "http://www.way2automation.com/angularjs-protractor/banking";
	private static String baseUrl1 = "http://amitava82.github.io/angular-multiselect/";
	private static String baseUrl3 = "http://dalelotts.github.io/angular-bootstrap-datetimepicker/";

	static Alert alert;

	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 1200;
	static int height = 800;
	static WebDriverWait wait;
	static Actions actions;

	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setUp() throws Exception {
		final FirefoxProfile profile = new FirefoxProfile();
		profile.setEnableNativeEvents(false);
		// seleniumDriver = new FirefoxDriver(profile);
		System.setProperty("webdriver.chrome.driver",
				"c:/java/selenium/chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// LoggingPreferences logging_preferences = new LoggingPreferences();
		// logging_preferences.enable(LogType.BROWSER, Level.ALL);
		// capabilities.setCapability(CapabilityType.LOGGING_PREFS,
		// logging_preferences);
		// prefs.js:user_pref("extensions.logging.enabled", true);
		// user.js:user_pref("extensions.logging.enabled", true);
		seleniumDriver = new ChromeDriver(capabilities);

		seleniumDriver.manage().window().setSize(new Dimension(width , height ));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		actions = new Actions(seleniumDriver);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		ngDriver = new NgWebDriver(seleniumDriver);
		// Thread.sleep(10000);
		recorder = new VideoRecorder();
		recorder.startRecording(seleniumDriver);
	}

	private static String css_selector_of(WebElement element) {
		String script = "function get_css_selector_of(element) {\n"
				+ "if (!(element instanceof Element))\n"
				+ "return;\n"
				+ "var path = [];\n"
				+ "while (element.nodeType === Node.ELEMENT_NODE) {\n"
				+ "var selector = element.nodeName.toLowerCase();\n"
				+ "if (element.id) {\n"
				+ "if (element.id.indexOf('-') > -1) {\n"
				+ "selector += '[id = \"' + element.id + '\"]';\n"
				+ "} else {\n"
				+ "selector += '#' + element.id;\n"
				+ "}\n"
				+ "path.unshift(selector);\n"
				+ "break;\n"
				+ "} else {\n"
				+ "var element_sibling = element;\n"
				+ "var sibling_cnt = 1;\n"
				+ "while (element_sibling = element_sibling.previousElementSibling) {\n"
				+ "if (element_sibling.nodeName.toLowerCase() == selector)\n"
				+ "sibling_cnt++;\n" + "}\n" + "if (sibling_cnt != 1)\n"
				+ "selector += ':nth-of-type(' + sibling_cnt + ')';\n" + "}\n"
				+ "path.unshift(selector);\n" + "element = element.parentNode;\n"
				+ "}\n" + "return path.join(' > ');\n" + "} \n"
				+ "return get_css_selector_of(arguments[0]);\n";
		return (String) execute_script(script, element);

	}

	private static void highlight(WebElement element) throws InterruptedException {

		highlight(element, 500);
	}

	private static void highlight(WebElement element, long highlight_interval)
			throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		execute_script("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		execute_script("arguments[0].style.border=''", element);
	}

	private static String xpath_of(WebElement element) {
		String script = "function get_xpath_of(element) {\n"
				+ " var elementTagName = element.tagName.toLowerCase();\n"
				+ "     if (element.id != '') {\n"
				+ "         return '//' + elementTagName + '[@id=\"' + element.id + '\"]';\n"
				+ "     } else if (element.name && document.getElementsByName(element.name).length === 1) {\n"
				+ "         return '//' + elementTagName + '[@name=\"' + element.name + '\"]';\n"
				+ "     }\n"
				+ "     if (element === document.body) {\n"
				+ "         return '/html/' + elementTagName;\n"
				+ "     }\n"
				+ "     var sibling_count = 0;\n"
				+ "     var siblings = element.parentNode.childNodes;\n"
				+ "     siblings_length = siblings.length;\n"
				+ "     for (cnt = 0; cnt < siblings_length; cnt++) {\n"
				+ "         var sibling_element = siblings[cnt];\n"
				+ "         if (sibling_element.nodeType !== 1) { // not ELEMENT_NODE\n"
				+ "             continue;\n"
				+ "         }\n"
				+ "         if (sibling_element === element) {\n"
				+ "             return sibling_count > 0 ? get_xpath_of(element.parentNode) + '/' + elementTagName + '[' + (sibling_count + 1) + ']' : get_xpath_of(element.parentNode) + '/' + elementTagName;\n"
				+ "         }\n"
				+ "         if (sibling_element.nodeType === 1 && sibling_element.tagName.toLowerCase() === elementTagName) {\n"
				+ "             sibling_count++;\n" + "         }\n" + "     }\n"
				+ "     return;\n" + " };\n" + " return get_xpath_of(arguments[0]);\n";
		return (String) execute_script(script, element);
	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.JavascriptExecutor
	public static Object execute_script(String script, Object... args) {
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) seleniumDriver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException(
					"Script execution is only available for WebDrivers that implement "
							+ "the JavascriptExecutor interface.");
		}
	}

	private static List<WebElement> find_elements(String selector_type,
			String selector_value, WebElement parent) {
		int flexible_wait_interval = 5;
		SearchContext finder;
		long wait_polling_interval = 500;
		String parent_css_selector = null;
		String parent_xpath = null;

		WebDriverWait wait = new WebDriverWait(seleniumDriver,
				flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		List<WebElement> elements = null;
		Hashtable<String, Boolean> supported_selectors = new Hashtable<String, Boolean>();
		supported_selectors.put("css_selector", true);
		supported_selectors.put("xpath", true);

		if (selector_type == null
				|| !supported_selectors.containsKey(selector_type)
				|| !supported_selectors.get(selector_type)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = css_selector_of(parent);
			parent_xpath = xpath_of(parent);
			finder = parent;
		} else {
			finder = seleniumDriver;
		}

		if (selector_type == "css_selector") {
			String extended_css_selector = String.format("%s  %s",
					parent_css_selector, selector_value);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selector_value));
		}
		if (selector_type == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath,
					selector_value);

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(selector_value));
		}
		return elements;
	}

	private static List<WebElement> find_elements(String selector_type,
			String selector_value) {
		return find_elements(selector_type, selector_value, null);
	}

	private static WebElement find_element(String selector_type,
			String selector_value) {
		WebElement element = null;
		Hashtable<String, Boolean> supported_selectors = new Hashtable<String, Boolean>();
		supported_selectors.put("id", true);
		supported_selectors.put("css_selector", true);
		supported_selectors.put("xpath", true);
		supported_selectors.put("partial_link_text", false);
		supported_selectors.put("link_text", true);
		supported_selectors.put("classname", false);

		if (selector_type == null
				|| !supported_selectors.containsKey(selector_type)
				|| !supported_selectors.get(selector_type)) {
			return null;
		}
		if (selector_type == "id") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.id(selector_value));
		}
		if (selector_type == "classname") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.className(selector_value));
		}
		if (selector_type == "link_text") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.linkText(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.linkText(selector_value));
		}
		if (selector_type == "css_selector") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.cssSelector(selector_value));
		}
		if (selector_type == "xpath") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.xpath(selector_value));
		}
		return element;
	}

	public static void main(String[] args) throws Exception {
		setUp();
		ngDriver.navigate().to(baseUrl3);
		testDatePickerDirectSelect();

		// testVerifyText();
		// testAddition();
		ngDriver.navigate().to(baseUrl1);

		testSelectAll();
		ngDriver.navigate().to(baseUrl1);

		testSelectOneByOne();
		// testAddCustomer();
		ngDriver.navigate().to(baseUrl2);

		testCustomerLogin();
		ngDriver.navigate().to(baseUrl2);

		testInvitateNewCustomerToOpenAccount();
		// testByModel();
		ngDriver.navigate().to(baseUrl2);

		testListTransactions();
		tearDown();
	}

	@Test
	public static void testDatePickerDirectSelect() throws Exception {
		NgWebElement ng_result;
		try {
			ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput"));
		} catch (WebDriverException e) {
			assertTrue(e.getMessage().contains(
					"no injector found for element argument to getTestability"));
			System.err.println(" Ignoring exception: " + e.getMessage());
		} catch (Exception e) {
			throw (e);
		}

		ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput",
				"[data-ng-app]"));
		actions.moveToElement(ng_result.getWrappedElement()).build().perform();

		assertThat(ng_result, notNullValue());
		assertTrue(ng_result.getAttribute("type").matches("text"));
		actions.moveToElement(ng_result.getWrappedElement()).build().perform();
		highlight(ng_result);
		NgWebElement ng_calendar = ngDriver.findElement(By
				.cssSelector(".input-group-addon"));
		assertThat(ng_calendar, notNullValue());
		highlight(ng_calendar);
		actions.moveToElement(ng_calendar.getWrappedElement()).click().build()
				.perform();
		// seleniumDriver.manage().window()
		// .setSize(new Dimension(datepicker_width, datepicker_heght));
		NgWebElement ng_dropdown = ngDriver.findElement(By
				.cssSelector("div.dropdown.open ul.dropdown-menu"));
		assertThat(ng_dropdown, notNullValue());
		highlight(ng_dropdown);
		actions.moveToElement(ng_dropdown.getWrappedElement()).build().perform();
		String monthDate = "12";
		WebElement dateElement = ng_dropdown.findElements(
				NgBy.cssContainingText("td.ng-binding", monthDate)).get(0);
		assertThat(dateElement, notNullValue());
		highlight(dateElement);
		System.err.println("Mondh Date: " + dateElement.getText());
		dateElement.click();
		ngDriver.waitForAngular();
		NgWebElement ng_element = ng_dropdown.findElement(NgBy.model(
				"data.dateDropDownInput", "[data-ng-app]"));
		assertThat(ng_element, notNullValue());
		highlight(ng_element);
		// System.err.println(ng_element.getAttribute("innerHTML"));
		List<WebElement> ng_dataDates = ng_dropdown.findElements(NgBy
				.repeater("dateObject in data.dates"));
		assertThat(ng_dataDates.size(), equalTo(24));
		// Thread.sleep(10000);

		String timeOfDay = "6:00 PM";
		WebElement ng_hour = ng_element.findElements(
				NgBy.cssContainingText("span.hour", timeOfDay)).get(0);
		assertThat(ng_hour, notNullValue());
		highlight(ng_hour);
		System.err.println("Hour of the day: " + ng_hour.getText());
		ng_hour.click();
		ngDriver.waitForAngular();
		String specificMinute = "6:35 PM";
		// reload,
		try {
			ng_element = ng_dropdown.findElement(NgBy.model("data.dateDropDownInput",
					"[data-ng-app]"));
			assertThat(ng_element, notNullValue());
			highlight(ng_element);
			// System.err.println("Dropdown: " + ng_element.getText());
		} catch (StaleElementReferenceException e) {
			// org.openqa.selenium.StaleElementReferenceException in Phantom JS
			// works fine with desktop browsers
			System.err.println("Enexpected exception");
			throw (e);
		}
		WebElement ng_minute;
		try {
			ng_minute = ng_element.findElements(
					NgBy.cssContainingText("span.minute", specificMinute)).get(0);
			assertThat(ng_minute, notNullValue());
			highlight(ng_minute);
			System.err.println("Time of the day: " + ng_minute.getText());
			ng_minute.click();
		} catch (StaleElementReferenceException e) {
			System.err.println("Ignoring StaleElementReferenceException");
		}
		try {
			ng_result = ngDriver.findElement(NgBy.model("data.dateDropDownInput",
					"[data-ng-app]"));
			highlight(ng_result);
			System.err.println("Selected Date/time : "
					+ ng_result.getAttribute("value"));
			// "Thu May 12 2016 18:35:00 GMT-0400 (Eastern Standard Time)"
			assertTrue(ng_result.getAttribute("value").matches(
					"\\w{3} \\w{3} \\d{1,2} \\d{4} 18:35:00 GMT[+-]\\d{4} \\(.+\\)"));
		} catch (StaleElementReferenceException e) {

			throw (e);
		}
	}

	// @Ignore
	@Test
	public static void testListTransactions() throws Exception {
		// customer login
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
		// select customer/account with transactions
		assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"),
				equalTo("userSelect"));

		Enumeration<WebElement> customers = Collections.enumeration(ngDriver
				.findElement(NgBy.model("custId")).findElements(
						NgBy.repeater("cust in Customers")));

		while (customers.hasMoreElements()) {
			WebElement currentCustomer = customers.nextElement();
			if (currentCustomer.getText().indexOf("Hermoine Granger") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		login.click();
		Enumeration<WebElement> accounts = Collections.enumeration(ngDriver
				.findElements(NgBy.options("account for account in Accounts")));

		while (accounts.hasMoreElements()) {
			WebElement currentAccount = accounts.nextElement();
			if (Integer.parseInt(currentAccount.getText()) == 1001) {
				System.err.println(currentAccount.getText());
				currentAccount.click();
			}
		}
		// inspect transactions
		NgWebElement transactions = ngDriver.findElement(NgBy
				.partialButtonText("Transactions"));
		assertThat(transactions.getText(), equalTo("Transactions"));
		highlight(transactions);
		transactions.click();
		// wait until transactions are loaded
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(
				NgBy.repeater("tx in transactions")).getWrappedElement()));
		Iterator<WebElement> transactionTypeColumns = ngDriver.findElements(
				NgBy.repeaterColumn("tx in transactions", "tx.type")).iterator();
		while (transactionTypeColumns.hasNext()) {
			WebElement transactionTypeColumn = (WebElement) transactionTypeColumns
					.next();
			if (transactionTypeColumn.getText().isEmpty()) {
				break;
			}
			if (transactionTypeColumn.getText().equalsIgnoreCase("Credit")) {
				highlight(transactionTypeColumn);
			}
		}
	}

	@Test
	public static void testInvitateNewCustomerToOpenAccount() throws Exception {
		// When I proceed to "Bank Manager Login"
		ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
		// And I proceed to "Add Customer"
		ngDriver.findElement(NgBy.partialButtonText("Add Customer")).click();

		NgWebElement ng_firstName = ngDriver.findElement(NgBy.model("fName"));
		assertThat(ng_firstName.getAttribute("placeholder"), equalTo("First Name"));
		ng_firstName.sendKeys("John");

		NgWebElement ng_lastName = ngDriver.findElement(NgBy.model("lName"));
		assertThat(ng_lastName.getAttribute("placeholder"), equalTo("Last Name"));
		ng_lastName.sendKeys("Doe");

		NgWebElement ng_postCode = ngDriver.findElement(NgBy.model("postCd"));
		assertThat(ng_postCode.getAttribute("placeholder"), equalTo("Post Code"));
		ng_postCode.sendKeys("11011");
		// And I add no accounts

		// NOTE: there are two 'Add Customer' buttons on this form
		WebElement ng_addCustomerButtonElement = ngDriver.findElements(
				NgBy.partialButtonText("Add Customer")).get(1);
		ng_addCustomerButtonElement.submit();
		try {
			alert = seleniumDriver.switchTo().alert();
			Pattern pattern = Pattern
					.compile("Customer added successfully with customer id :(\\d+)");
			Matcher matcher = pattern.matcher(alert.getText());
			if (matcher.find()) {
				System.err.println("New Customer id: " + matcher.group(1));
			}
			// confirm alert
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err.println(ex.getStackTrace());
			return;
		} catch (WebDriverException ex) {
			// Alert not handled by PhantomJS
			System.err.println("Alert was not handled by PhantomJS: "
					+ ex.getStackTrace());
			return;
		}

		// And I switch to "Home" screen
		NgWebElement ng_home = ngDriver.findElement(NgBy.buttonText("Home"));
		highlight(ng_home);
		ng_home.click();
		// And I proceed to "Customer Login"
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();

		// And I login as "John Doe"
		Enumeration<WebElement> customers = Collections.enumeration(ngDriver
				.findElement(NgBy.model("custId")).findElements(
						NgBy.repeater("cust in Customers")));
		while (customers.hasMoreElements()) {
			WebElement currentCustomer = customers.nextElement();
			if (currentCustomer.getText().indexOf("John Doe") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}
		actions
				.moveToElement(
						ngDriver.findElement(NgBy.model("custId")).getWrappedElement())
				.build().perform();

		NgWebElement ng_login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(ng_login.isEnabled());
		actions.moveToElement(ng_login.getWrappedElement()).build().perform();
		highlight(ng_login);
		ng_login.click();

		ngDriver.waitForAngular();
		// Then I am greeted as "John Doe"
		NgWebElement ng_user = ngDriver.findElement(NgBy.binding("user"));

		assertThat(ng_user.getText(), containsString("John"));
		assertThat(ng_user.getText(), containsString("Doe"));

		// And I am invited to open an account
		Object noAccount = ng_user.evaluate("noAccount");
		assertTrue(parseBoolean(noAccount.toString()));
		boolean hasAccounts = !(parseBoolean(noAccount.toString()));
		System.err.println("Has accounts: " + hasAccounts);
		WebElement message = seleniumDriver.findElement(By
				.cssSelector("span[ng-show='noAccount']"));
		actions.moveToElement(message).build().perform();
		assertTrue(message.isDisplayed());
		highlight(message);
		assertThat(message.getText(), containsString("Please open an account"));

		System.err.println(message.getText());

		// And I see no accounts
		NgWebElement accountNo = ngDriver.findElement(NgBy.binding("accountNo"));
		assertFalse(accountNo.isDisplayed());
		// And I cannot choose an account to view
		List<WebElement> accounts = ngDriver.findElements(NgBy
				.repeater("account for account in Accounts"));
		assertThat(accounts.size(), equalTo(0));

	}

	@Test
	public static void testSelectOneByOne() throws Exception {
		// Given we are using multiselect directive to pick cars
		NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
		assertThat(ng_directive, notNullValue());
		assertThat(ng_directive.getTagName(), equalTo("am-multiselect"));
		WebElement toggleSelect = ng_directive.findElement(NgBy
				.buttonText("Select Some Cars"));
		assertThat(toggleSelect, notNullValue());
		assertTrue(toggleSelect.isDisplayed());
		toggleSelect.click();

		// When selecting every car one car at a time
		// find how many cars there are
		List<WebElement> cars = ng_directive.findElements(NgBy
				.repeater("i in items"));
		// NOTE: not "c.name for c in cars"
		int rowNum = 0;
		for (rowNum = 0; rowNum != cars.size(); rowNum++) {
			// For every row, click on the car name
			NgWebElement ng_car = ngDriver.findElement(NgBy.repeaterElement(
					"i in items", rowNum, "i.label"));
			System.err.println("* " + ng_car.evaluate("i.label").toString());
			highlight(ng_car);
			ng_car.click();
		}
		// Then all cars got selected
		assertThat(ng_directive.findElements(NgBy.repeater("i in items")).size(),
				equalTo(rowNum));
		// TODO: verify the number of cars on the button
		WebElement button = ngDriver.findElement(By
				.cssSelector("am-multiselect > div > button"));
		assertTrue(button.getText().matches("There are (\\d+) car\\(s\\) selected"));
		System.err.println(button.getText());
	}

	@Test
	public static void testSelectAll() throws Exception {
		// Given we are using multiselect directive to pick cars
		NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
		assertThat(ng_directive, notNullValue());
		assertTrue(ng_directive.getTagName().equals("am-multiselect"));
		// When selecting cars
		WebElement toggleSelect = ng_directive.findElement(By
				.cssSelector("button[ng-click='toggleSelect()']"));
		assertThat(toggleSelect, notNullValue());
		assertTrue(toggleSelect.isDisplayed());
		toggleSelect.click();
		// When selected all cars using 'check all' link
		wait.until(ExpectedConditions.visibilityOf(ng_directive.findElement(By
				.cssSelector("button[ng-click='checkAll()']"))));
		WebElement checkAll = ng_directive.findElement(By
				.cssSelector("button[ng-click='checkAll()']"));
		assertThat(checkAll, notNullValue());
		assertTrue(checkAll.isDisplayed());
		highlight(checkAll);
		checkAll.click();
		// Then all cars were selected
		// NOTE: not "c.name for c in cars"
		List<WebElement> cars = ng_directive.findElements(NgBy
				.repeater("i in items"));
		int selectedCarCount = 0;
		for (WebElement car : cars) {
			assertTrue((boolean) (new NgWebElement(ngDriver, car)
					.evaluate("i.checked")));
			System.err.println("* " + car.getText());
			selectedCarCount++;
		}
		assertThat(cars.size(), equalTo(selectedCarCount));
	}

	@Test
	public static void testVerifyText() throws Exception {
		String selector = null;
		WebElement element = null;
		try {
			assertEquals("Hotels", find_element("link_text", "Hotels").getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			element = find_element("link_text", "Hotels");
			highlight(element);
			selector = xpath_of(element);
			assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

		try {
			element = find_element("xpath", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append(e.toString());
		}
		try {
			element = find_element("link_text", "Hotels");
			highlight(element);
			selector = css_selector_of(element);
			assertEquals("div#HEAD > div > div:nth-of-type(2) > ul > li > span > a",
					selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			element = find_element("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {

			verificationErrors.append(e.toString());
		}

		try {
			element = find_element("css_selector", "input#mainSearch");
			selector = css_selector_of(element);
			highlight(element);
			assertEquals("input#mainSearch", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	/*
	 * 
	 * Feature: Login Feature
	 * 
	 * @test Scenario Outline: Existing Customer Login Given I go to the home page
	 * When I continue as "Customer Login" And I choose myself "<FirstName>",
	 * "<LastName>" from existing customers And I log in Then I am greeted by
	 * "<FirstName>", "<LastName>" And I see balance on one of my accounts
	 * "<AccountNumbers>" And I can switch to any of my accounts
	 * "<AccountNumbers>" And I can not see any other accounts Examples: |
	 * AccountNumbers | FirstName | LastName | | 1004,1005,1006 | Harry | Potter |
	 */
	// @Ignore
	@Test
	public static void testCustomerLogin() throws Exception {
		// When I proceed to "Customer Login"
		NgWebElement element = ngDriver.findElement(NgBy
				.buttonText("Customer Login"));
		highlight(element);
		element.click();

		// And I choose my name from the list of existing customers
		element = ngDriver.findElement(NgBy.input("custId"));
		assertThat(element.getAttribute("id"), equalTo("userSelect"));
		highlight(element);

		Enumeration<WebElement> customers = Collections.enumeration(element
				.findElements(NgBy.repeater("cust in Customers")));

		while (customers.hasMoreElements()) {
			WebElement currentCustomer = customers.nextElement();
			if (currentCustomer.getText().indexOf("Harry Potter") >= 0) {
				System.err.println(currentCustomer.getText());
				actions.moveToElement(currentCustomer).build().perform();
				currentCustomer.click();
			}
		}
		// And I log in
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		actions.moveToElement(element.getWrappedElement()).build().perform();
		actions.moveToElement(login.getWrappedElement()).build().perform();
		login.click();

		// Then I am greeted by my first and last name
		String user = ngDriver.findElement(NgBy.binding("user")).getText();
		// NOTE: the binding is {{user}}. It is composed from first and last name
		// surrounded with whitespace
		// confirm the greeting looks like a person's name
		assertTrue(user.matches("^(?:[^ ]+) +(?:[^ ]+)$"));
		// The greeting contains my first name
		assertThat(user, containsString("Harry"));
		// And I see balance on one of my accounts
		NgWebElement ng_accountNo = ngDriver.findElement(NgBy.binding("accountNo"));
		actions.moveToElement(ng_accountNo.getWrappedElement()).build().perform();
		highlight(ng_accountNo);
		assertThat(ng_accountNo, notNullValue());
		// a valid account number
		String allCustomerAccountsAsString = "1004,1005,1006";
		String[] customerAccounts = allCustomerAccountsAsString.split(",");
		/*
		 * Pattern pattern = Pattern.compile("(" +
		 * StringUtils.join(customerAccounts, "|") + ")"); Matcher matcher =
		 * pattern.matcher(ng_accountNo.getText()); assertTrue(matcher.find());
		 * pattern = Pattern.compile("^(?!" + StringUtils.join(customerAccounts,
		 * "|") + ").*$"); matcher = pattern.matcher(ng_accountNo.getText());
		 * assertFalse(matcher.find());
		 */

		WebElement balance = ngDriver.findElement(NgBy.binding("amount"));
		// actions.moveToElement(balance.getWrappedElement()).build().perform();
		assertTrue(balance.getText().matches("^\\d+$"));
		highlight(balance);

		WebElement currency = ngDriver.findElement(NgBy.binding("currency"));
		assertTrue(currency.getText().matches("^(?:Dollar|Pound|Rupee)$"));
		highlight(currency);

		// And I can switch to any of my accounts
		ArrayList<String> avaliableAccounts = new ArrayList<String>();
		Enumeration<WebElement> options = Collections.enumeration(ngDriver
				.findElements(NgBy.options("account for account in Accounts")));
		while (options.hasMoreElements()) {
			String otherAccount = options.nextElement().getText();
			assertTrue(otherAccount.matches("^\\d+$"));
			// And I can not see any other accounts
			/*
			 * pattern = Pattern.compile("^(?!" + StringUtils.join(customerAccounts,
			 * "|") + ").*$"); matcher = pattern.matcher(otherAccount);
			 * assertFalse(matcher.find());
			 */
			avaliableAccounts.add(otherAccount);
		}
		assertTrue(avaliableAccounts.containsAll(new HashSet<String>(Arrays
				.asList(customerAccounts))));
	}

	@AfterClass
	public static void tearDown() throws Exception {
		recorder.stopRecording("Recording");
		ngDriver.close();
		seleniumDriver.quit();
	}
}
