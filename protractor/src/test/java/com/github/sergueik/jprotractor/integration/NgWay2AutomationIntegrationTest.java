package com.github.sergueik.jprotractor.integration;

import static java.lang.Boolean.parseBoolean;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

public class NgWay2AutomationIntegrationTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 700;
	static int height = 550;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(Duration.ofMillis(pollingInterval));
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
		CommonFunctions.setHighlightTimeout(1000);
	}

	@Before
	public void beforeEach() {
		// Given I am on Home page
		ngDriver.navigate().to(baseUrl);
	}

	@Ignore
	@Test
	public void testSiteLogint() {
		String login_url = "http://way2automation.com/way2auto_jquery/index.php";
		String username = System.getenv("TEST_USERNAME");
		String password = System.getenv("TEST_PASSWORD");
		WebDriver driver = ngDriver.getWrappedDriver();
		driver.navigate().to(login_url);
		// signup

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("load_box")));
		WebElement load_form = driver
				.findElement(By.cssSelector("div#load_box.popupbox form#load_form"));
		highlight(load_form);

		WebElement signup_link = load_form
				.findElement(By.cssSelector("a.fancybox[href='#login']"));
		actions.moveToElement(signup_link).build().perform();
		highlight(signup_link);
		signup_link.click();
		// enter username
		WebElement login_username = driver.findElement(By.cssSelector(
				"div#login.popupbox form#load_form input[name='username']"));
		highlight(login_username);
		login_username.sendKeys(username);
		// enter password
		WebElement login_password = driver.findElement(By.cssSelector(
				"div#login.popupbox form#load_form input[type='password'][name='password']"));
		highlight(login_password);
		login_password.sendKeys(password);
		// to try the method, can always invoke it before submiting the credentias
		// click "Login"
		actions
				.moveToElement(driver.findElement(By
						.cssSelector("div#login.popupbox form#load_form [value='Submit']")))
				.click().build().perform();
		// wait until the login popup box disappears with .Net delegate
		// .net / jave 8:
		// wait.Until(d =>
		// (d.FindElements(By.CssSelector("div#login.popupbox")).Count == 0));
		if (driver.findElement(By.cssSelector("div#login.popupbox"))
				.isDisplayed()) {
			System.err.println("Waiting while Login Popup Box is visible");
			CommonFunctions.waitWhileElementIsVisible(By.cssSelector(
					"div#login.popupbox input[type='hidden'][name='action']"));
		}
		System.err.println("Login Popup Box is not diplayed");
	}

	/*
	 * 
	 * Feature: Login Feature
	 * 
	 * @test Scenario: Customer Login Given I go to the home page When I continue
	 * as "Customer Login" Then I should be able to choose my name from the list
	 * of existing customers
	 * 
	 * @test Scenario: Bank Manager Login Given I go to the home page When I
	 * continue as "Bank Manager Login" Then I should see "Add Customer" button
	 * And Ishould see "Open Account" button And I should see "Customers" button
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
	public void testCustomerLogin() {
		if (isCIBuild) {
			return;
		}
		// When I proceed to "Customer Login"
		NgWebElement element = ngDriver
				.findElement(NgBy.buttonText("Customer Login"));
		highlight(element);
		element.click();

		// And I choose my name from the list of existing customers
		element = ngDriver.findElement(NgBy.input("custId"));
		assertThat(element.getAttribute("id"), equalTo("userSelect"));
		highlight(element);

		for (WebElement currentCustomer : element
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("Harry Potter") >= 0) {
				System.err.println(currentCustomer.getText());
				actions.moveToElement(currentCustomer).build().perform();
				currentCustomer.click();
			}
		}
		// And I log in
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
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
		highlight(ng_accountNo);
		assertThat(ng_accountNo, notNullValue());
		// a valid account number
		String allCustomerAccountsAsString = "1004,1005,1006";
		String[] customerAccounts = allCustomerAccountsAsString.split(",");
		Pattern pattern = Pattern
				.compile("(" + StringUtils.join(customerAccounts, "|") + ")");
		Matcher matcher = pattern.matcher(ng_accountNo.getText());
		assertTrue(matcher.find());
		pattern = Pattern
				.compile("^(?!" + StringUtils.join(customerAccounts, "|") + ").*$");
		matcher = pattern.matcher(ng_accountNo.getText());
		assertFalse(matcher.find());

		// alternative to java.util.regex
		List<String> oneAcountArray = new ArrayList<>();
		oneAcountArray.add(ng_accountNo.getText());
		assertTrue(CollectionUtils.containsAny(oneAcountArray,
				new ArrayList<>(Arrays.asList(customerAccounts))));
		oneAcountArray.clear();

		WebElement balance = ngDriver.findElement(NgBy.binding("amount"));
		assertTrue(balance.getText().matches("^\\d+$"));
		highlight(balance);

		WebElement currency = ngDriver.findElement(NgBy.binding("currency"));
		assertTrue(currency.getText().matches("^(?:Dollar|Pound|Rupee)$"));
		highlight(currency);

		// And I can switch to every account owned
		List<String> avaliableAccounts = new ArrayList<>();

		for (WebElement account : ngDriver
				.findElements(NgBy.options("account for account in Accounts"))) {
			String otherAccountId = account.getText();
			assertTrue(otherAccountId.matches("^\\d+$"));
			// And I can not see any other accounts
			pattern = Pattern
					.compile("^(?!" + StringUtils.join(customerAccounts, "|") + ").*$");
			matcher = pattern.matcher(otherAccountId);
			assertFalse(matcher.find());
			avaliableAccounts.add(otherAccountId);
		}

		// And I can find every my account
		assertTrue(avaliableAccounts
				.containsAll(new HashSet<String>(Arrays.asList(customerAccounts))));

		ngDriver.findElements(NgBy.options("account for account in Accounts"))
				.stream().forEach(currentAccount -> {
					System.err.println(currentAccount.getText());
					currentAccount.click();
				});

	}

	// @Ignore
	@Test
	public void testEvaluateTransactionDetails() throws InterruptedException {
		if (isCIBuild) {
			return;
		}
		// When I proceed to "Customer Login"
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
		// And I login as existing customer
		assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"),
				equalTo("userSelect"));

		// Select customer/account with transactions
		for (WebElement currentCustomer : ngDriver.findElement(NgBy.model("custId"))
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("Hermoine Granger") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		login.click();

		// NOTE: need to select account with transactions
		for (WebElement currentAccount : ngDriver
				.findElements(NgBy.options("account for account in Accounts"))) {

			if (Integer.parseInt(currentAccount.getText()) == 1001) {
				System.err.println(currentAccount.getText());
				currentAccount.click();
			}
		}

		// inspect transactions
		NgWebElement transactions_button = ngDriver
				.findElement(NgBy.partialButtonText("Transactions"));
		highlight(transactions_button);
		transactions_button.click();
		// wait until transactions are loaded
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("tx in transactions")).getWrappedElement()));

		// inspect max 5 transactions
		int cnt = 0;

		for (WebElement currentTransaction : ngDriver
				.findElements(NgBy.repeater("tx in transactions"))) {
			if (cnt++ >= 5) {
				break;
			}

			NgWebElement ngCurrentTransaction = new NgWebElement(ngDriver,
					currentTransaction);
			assertTrue(ngCurrentTransaction.evaluate("tx.amount").toString()
					.matches("^\\d+$"));
			assertTrue(ngCurrentTransaction.evaluate("tx.type").toString()
					.matches("(?i:credit|debit)"));
			Object transaction_date = ngCurrentTransaction.evaluate("tx.date");
		}
	}

	// @Ignore
	@Test
	public void testOpenAccount() throws InterruptedException {
		if (isCIBuild) {
			return;
		}
		// bank manager login
		ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
		ngDriver.findElement(NgBy.partialButtonText("Open Account")).click();
		// wait for customers info get loaded
		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("cust in Customers")).getWrappedElement()));
		NgWebElement selectCustomer = ngDriver.findElement(NgBy.model("custId"));
		assertThat(selectCustomer.getAttribute("id"), containsString("userSelect"));
		List<WebElement> customers = new NgWebElement(ngDriver, selectCustomer)
				.findElements(NgBy.repeater("cust in Customers"));
		// pick random customer to log in
		int random_customer_index = 1
				+ (int) (Math.random() * (customers.size() - 1));
		WebElement customer = customers.get(random_customer_index);
		String customerName = customer.getText();
		System.err.println(customerName);

		customer.click();
		NgWebElement ng_selectCurrencies = ngDriver
				.findElement(NgBy.model("currency"));

		actions.moveToElement(ng_selectCurrencies.getWrappedElement()).build()
				.perform();
		highlight(ng_selectCurrencies.getWrappedElement());

		// use core Selenium
		Select selectCurrencies = new Select(
				ng_selectCurrencies.getWrappedElement());
		List<WebElement> accountCurrencies = selectCurrencies.getOptions();
		// select "Dollars"
		selectCurrencies.selectByVisibleText("Dollar");
		// add the account
		WebElement submitButton = ngDriver.getWrappedDriver()
				.findElement(By.xpath("/html/body//form/button[@type='submit']"));
		assertThat(submitButton.getText(), containsString("Process"));
		actions.moveToElement(submitButton).build().perform();
		highlight(submitButton);
		submitButton.click();
		String newAccount = null;
		try {
			alert = seleniumDriver.switchTo().alert();
			String alert_text = alert.getText();
			assertThat(alert_text,
					containsString("Account created successfully with account Number"));
			Pattern pattern = Pattern.compile("(\\d+)");
			Matcher matcher = pattern.matcher(alert_text);
			if (matcher.find()) {
				newAccount = matcher.group(1);
				System.err.println("New account id " + newAccount);
			}
			// confirm the alert
			alert.accept();

		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err.println("NoAlertPresentException: " + ex.getStackTrace());
			// observed in Chrome. Ignore
			// return;
		} catch (WebDriverException ex) {
			// fullStackTrace =
			// org.apache.commons.lang3.exception.ExceptionUtils.getFullStackTrace(ex);
			// System.err.println("Alert was not handled by PhantomJS: " +
			// fullStackTrace);
			System.err.println("Alert was not handled by PhantomJS: "
					+ ex.getStackTrace().toString());
			return;
		}

		// And I switch to "Home" screen
		NgWebElement ng_home = ngDriver.findElement(NgBy.buttonText("Home"));
		highlight(ng_home);
		ng_home.click();
		// And I proceed to "Bank Manager Login"
		ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
		ngDriver.findElement(NgBy.partialButtonText("Customers")).click();
		// wait for customers info get loaded
		ngDriver.waitForAngular();
		// I can find the Customers Account I just Added
		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("cust in Customers")).getWrappedElement()));

		// find the customer by name
		for (WebElement currentCustomer : ngDriver
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf(customerName) >= 0) {
				// System.err.println("Current customer: " + currentCustomer.getText());
				highlight(currentCustomer);
				NgWebElement ng_currentCustomer = new NgWebElement(ngDriver,
						currentCustomer);
				// find the account we just added
				for (WebElement currentAccount : ng_currentCustomer
						.findElements(NgBy.repeater("account in cust.accountNo"))) {
					if (currentAccount.getText().indexOf(newAccount) >= 0) {
						highlight(currentAccount);
					}
				}
			}
		}
	}

	// @Ignore
	@Test
	public void testSortCustomerAccounts() throws InterruptedException {
		if (isCIBuild) {
			return;
		}
		// bank manager login
		ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
		ngDriver.findElement(NgBy.partialButtonText("Customers")).click();
		// wait for customers info get loaded
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("cust in Customers")).getWrappedElement()));
		WebElement sort_link = ngDriver.getWrappedDriver().findElement(
				By.cssSelector("a[ng-click*='sortType'][ng-click*= 'fName']"));
		assertThat(sort_link.getText(), containsString("First Name"));
		// sort the customers
		highlight(sort_link);
		sort_link.click();

		List<WebElement> customers = ngDriver
				.findElements(NgBy.repeater("cust in Customers"));
		// note the name of the last customer
		String last_customer_name = customers.get(customers.size() - 1).getText();
		// sort the customers in reverse
		highlight(sort_link);
		sort_link.click();
		// FindElement is equivalent to FindElements...get(0)
		WebElement first_customer = ngDriver
				.findElement(NgBy.repeater("cust in Customers"));
		assertThat(first_customer.getText(), containsString(last_customer_name));
	}

	// @Ignore
	@Test
	public void testListTransactions() {
		if (isCIBuild) {
			return;
		}
		// customer login
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
		// select customer/account with transactions
		assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"),
				equalTo("userSelect"));

		for (WebElement currentCustomer : ngDriver.findElement(NgBy.model("custId"))
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("Hermoine Granger") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		login.click();

		for (WebElement currentAccount : ngDriver
				.findElements(NgBy.options("account for account in Accounts"))) {
			if (Integer.parseInt(currentAccount.getText()) == 1001) {
				System.err.println(currentAccount.getText());
				currentAccount.click();
			}
		}
		// inspect transactions
		NgWebElement transactions = ngDriver
				.findElement(NgBy.partialButtonText("Transactions"));
		assertThat(transactions.getText(), equalTo("Transactions"));
		highlight(transactions);
		transactions.click();
		// wait until transactions are loaded
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("tx in transactions")).getWrappedElement()));

		// perform some Credit transaction-specific checks
		for (WebElement transactionTypeColumn : ngDriver
				.findElements(NgBy.repeaterColumn("tx in transactions", "tx.type"))) {
			if (transactionTypeColumn.getText().isEmpty()) {
				break;
			}
			if (transactionTypeColumn.getText().equalsIgnoreCase("Credit")) {
				highlight(transactionTypeColumn);
			}
		}
	}

	// @Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void testAddCustomer() {
		if (isCIBuild) {
			return;
		}
		ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
		ngDriver.findElement(NgBy.partialButtonText("Add Customer")).click();

		NgWebElement firstName = ngDriver.findElement(NgBy.model("fName"));
		assertThat(firstName.getAttribute("placeholder"), equalTo("First Name"));
		firstName.sendKeys("John");

		NgWebElement lastName = ngDriver.findElement(NgBy.model("lName"));
		assertThat(lastName.getAttribute("placeholder"), equalTo("Last Name"));
		lastName.sendKeys("Doe");

		NgWebElement postCode = ngDriver.findElement(NgBy.model("postCd"));
		assertThat(postCode.getAttribute("placeholder"), equalTo("Post Code"));
		postCode.sendKeys("11011");

		// NOTE: there are two 'Add Customer' buttons on this form
		Object[] addCustomerButtonElements = ngDriver
				.findElements(NgBy.partialButtonText("Add Customer")).toArray();
		WebElement addCustomerButtonElement = (WebElement) addCustomerButtonElements[1];
		addCustomerButtonElement.submit();
		try {
			alert = seleniumDriver.switchTo().alert();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err.println(ex.getStackTrace());
			return;
		} catch (WebDriverException ex) {
			// Alert not handled by PhantomJS
			System.err
					.println("Alert was not handled by PhantomJS: " + ex.getStackTrace());
			return;
		}

		Pattern pattern = Pattern
				.compile("Customer added successfully with customer id :(\\d+)");
		Matcher matcher = pattern.matcher(alert.getText());
		if (matcher.find()) {
			System.err.println("New Customer id: " + matcher.group(1));
		}

		// Actually add the customer
		alert.accept();

		// switch to "Customers" screen
		ngDriver.findElement(NgBy.partialButtonText("Customers")).click();

		// block and wait the customers list to get populated
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.repeater("cust in Customers"))));

		for (WebElement firstNameElement : ngDriver
				.findElements(NgBy.repeaterColumn("cust in Customers", "cust.fName"))) {
			actions.moveToElement(firstNameElement).build().perform();
			highlight(firstNameElement);
			System.err
					.println("Customer's First Name: " + firstNameElement.getText());
			// no need for explicit findElements
			Object objLastName = new NgWebElement(ngDriver, firstNameElement)
					.evaluate("cust.lName");
			assertThat(objLastName, notNullValue());
			System.err.println("Customer's Last Name: " + objLastName.toString());
			// fName, lName, accountNo, postCD, id, date
			List<Long> accounts = (List<Long>) new NgWebElement(ngDriver,
					firstNameElement).evaluate("cust.accountNo");
			System.err.println(accounts == null ? "No accounts"
					: "Account No: " + Arrays.toString(accounts.toArray()));
		}

		NgWebElement ng_deleteCustomer = null;
		for (WebElement currentCustomer : ngDriver
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("John Doe") >= 0) {
				ng_deleteCustomer = new NgWebElement(ngDriver, currentCustomer);
				break;
			}
		}
		assertThat(ng_deleteCustomer.getWrappedElement(), notNullValue());
		actions.moveToElement(ng_deleteCustomer.getWrappedElement()).build()
				.perform();

		// delete the customer we added
		highlight(ng_deleteCustomer);
		NgWebElement deleteCustomerButton = ng_deleteCustomer
				.findElement(NgBy.buttonText("Delete"));
		assertThat(deleteCustomerButton, notNullValue());
		assertThat(deleteCustomerButton.getText(), containsString("Delete"));
		highlight(deleteCustomerButton);
		// .. in slow motion
		actions.moveToElement(deleteCustomerButton.getWrappedElement())
				.clickAndHold().build().perform();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		actions.release().build().perform();
		// let the customers reload
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.repeater("cust in Customers"))));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		// TODO: assert the customers.count change
	}

	// @Ignore
	@Test
	public void testInvitateNewCustomerToOpenAccount() {
		if (isCIBuild) {
			return;
		}
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
		WebElement ng_addCustomerButtonElement = ngDriver
				.findElements(NgBy.partialButtonText("Add Customer")).get(1);
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
			System.err
					.println("Alert was not handled by PhantomJS: " + ex.getStackTrace());
			return;
		}

		// And I switch to "Home" screen
		NgWebElement ng_home = ngDriver.findElement(NgBy.buttonText("Home"));
		highlight(ng_home);
		ng_home.click();
		// And I proceed to "Customer Login"
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();

		// And I login as "John Doe"

		for (WebElement currentCustomer : ngDriver.findElement(NgBy.model("custId"))
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("John Doe") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}

		NgWebElement ng_login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(ng_login.isEnabled());
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
		WebElement message = seleniumDriver
				.findElement(By.cssSelector("span[ng-show='noAccount']"));
		assertTrue(message.isDisplayed());
		highlight(message);
		assertThat(message.getText(), containsString("Please open an account"));

		System.err.println(message.getText());

		// And I see no accounts
		NgWebElement accountNo = ngDriver.findElement(NgBy.binding("accountNo"));
		assertFalse(accountNo.isDisplayed());
		// And I cannot choose an account to view
		List<WebElement> accounts = ngDriver
				.findElements(NgBy.repeater("account for account in Accounts"));
		assertThat(accounts.size(), equalTo(0));

	}

	// @Ignore
	@Test
	public void testDepositAndWithdraw() {
		if (isCIBuild) {
			return;
		}
		// customer login
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();

		// select customer with accounts
		assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"),
				equalTo("userSelect"));

		for (WebElement currentCustomer : ngDriver.findElement(NgBy.model("custId"))
				.findElements(NgBy.repeater("cust in Customers"))) {

			if (currentCustomer.getText().indexOf("Harry Potter") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}

		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		highlight(login);
		login.click();
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.options("account for account in Accounts"))
						.getWrappedElement()));
		List<WebElement> accounts = ngDriver
				.findElements(NgBy.options("account for account in Accounts"));

		// pick a random account
		assertTrue(accounts.size() > 0);
		int account_idx = 1 + (int) (Math.random() * (accounts.size() - 1));
		String targetAccount = accounts.get(account_idx).getText();
		System.err.println(account_idx + " " + targetAccount);
		accounts.get(account_idx).click();
		int initialBalance = Integer
				.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());

		WebElement depositButton = ngDriver
				.findElements(NgBy.partialButtonText("Deposit")).get(0);
		assertTrue(depositButton.isDisplayed());
		depositButton.click();

		// deposit amount
		WebElement depositAmount = ngDriver.findElement(NgBy.model("amount"));
		highlight(depositAmount);
		depositAmount.sendKeys("100");

		// deposit the payment
		depositButton = ngDriver.findElements(NgBy.partialButtonText("Deposit"))
				.get(1);
		assertTrue(depositButton.isDisplayed());
		depositButton.click();

		// Then we see the confirmation message
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.binding("message")).getWrappedElement()));
		NgWebElement message = ngDriver.findElement(NgBy.binding("message"));
		assertThat(message.getText(), containsString("Deposit Successful"));
		highlight(message);

		// And the balance changes
		int finalBalance = Integer
				.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());
		assertTrue(finalBalance == 100 + initialBalance);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		// switch to "Home" screen
		ngDriver.findElement(NgBy.buttonText("Home")).click();
		// customer login
		ngDriver.findElement(NgBy.buttonText("Customer Login")).click();

		// find the same customer / account

		for (WebElement currentCustomer : ngDriver.findElement(NgBy.model("custId"))
				.findElements(NgBy.repeater("cust in Customers"))) {
			if (currentCustomer.getText().indexOf("Harry Potter") >= 0) {
				System.err.println(currentCustomer.getText());
				currentCustomer.click();
			}
		}

		ngDriver.findElement(NgBy.buttonText("Login")).click();
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.options("account for account in Accounts"))
						.getWrappedElement()));

		for (WebElement currentAccount : ngDriver
				.findElements(NgBy.options("account for account in Accounts"))) {
			if (currentAccount.getText().indexOf(targetAccount) >= 0) {
				System.err.println(currentAccount.getText());
				currentAccount.click();
			}
		}

		WebElement withdrawButton = ngDriver
				.findElement(NgBy.partialButtonText("Withdrawl"));
		assertTrue(withdrawButton.isDisplayed());
		withdrawButton.click();

		// When we withdraw a amount bigger then the balance on the account
		WebElement withdrawAmount = ngDriver.findElement(NgBy.model("amount"));
		highlight(withdrawAmount);
		withdrawAmount.sendKeys(String.format("%d", finalBalance + 10));
		withdrawButton = ngDriver.findElement(NgBy.buttonText("Withdraw"));
		withdrawButton.click();

		// We see warning that transaction failed
		wait.until(ExpectedConditions.visibilityOf(
				ngDriver.findElement(NgBy.binding("message")).getWrappedElement()));
		message = ngDriver.findElement(NgBy.binding("message"));
		assertThat(message.getText(), containsString("Transaction Failed."));
		highlight(message);

		withdrawAmount.sendKeys(String.format("%d", finalBalance - 10));
		withdrawButton.click();
		// And the balance does not change
		finalBalance = Integer
				.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());
		assertTrue(finalBalance == 10);
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
