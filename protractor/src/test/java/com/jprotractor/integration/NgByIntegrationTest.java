package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

@RunWith(Enclosed.class)
// @Category(Integration.class)
	public class NgByIntegrationTest {
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
	// set to true for Desktop, false for CI testing
	static boolean isDestopTesting = true; 

	@BeforeClass
	public static void setup() throws IOException {
		seleniumDriver = getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width , height ));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait );
		wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);		
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	private static WebDriver getSeleniumDriver() throws IOException {
		// For desktop browser testing, run a Selenium node and Selenium hub on port 4444	
		if (isDestopTesting ){
			DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "", Platform.ANY);
			FirefoxProfile profile = new ProfilesIni().getProfile("default");
			profile.setEnableNativeEvents(false);
			capabilities.setCapability("firefox_profile", profile);
			seleniumDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
			return seleniumDriver;
		} else {
		// for CI build
			return new PhantomJSDriver();
		}
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();		
	}
	
	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element,  100);
	}

	private static void highlight(WebElement element, long highlightInterval ) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlightInterval);
		executeScript("arguments[0].style.border=''", element);
	}

	private static void highlight(NgWebElement element, long highlightInterval) throws InterruptedException {
		highlight(element.getWrappedElement(), highlightInterval);
	}
	
	public static Object executeScript(String script,Object ... args){
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor=(JavascriptExecutor)seleniumDriver;
			return javascriptExecutor.executeScript(script,args);
		}
		else {
			throw new RuntimeException("Script execution is only available for WebDrivers that implement " + "the JavascriptExecutor interface.");
		}
	}

	public static class Way2AutomationTests {

		public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";
   		@Before
		public void beforeEach() throws InterruptedException{
			ngDriver.navigate().to(baseUrl);
		}

		@Test
		public void testCustomerLogin() throws Exception {
			NgWebElement element = ngDriver.findElement(NgBy.buttonText("Customer Login"));
			highlight(element);
			element.click();
			element = ngDriver.findElement(NgBy.input("custId"));
			assertThat(element.getAttribute("id"), equalTo("userSelect"));
			highlight(element);

			Enumeration<WebElement> customers = Collections.enumeration(element.findElements(NgBy.repeater("cust in Customers")));

			while (customers.hasMoreElements()){
				WebElement currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("Harry Potter") >= 0 ){
					System.err.println(currentCustomer.getText());
					currentCustomer.click();
				}
			}
			NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(login.isEnabled());	
			login.click();			
			// the {{user}} is composed from first and last name	
			assertTrue(ngDriver.findElement(NgBy.binding("user")).getText().matches("^(?:[^ ]+) +(?:[^ ]+)$"));
			assertThat(ngDriver.findElement(NgBy.binding("user")).getText(),containsString("Harry"));
			NgWebElement accountNumber = ngDriver.findElement(NgBy.binding("accountNo"));
			assertThat(accountNumber, notNullValue());
			assertTrue(accountNumber.getText().matches("^\\d+$"));
		}
		
		@Test
		public void testEvaluateTransactionDetails() throws Exception {
			// customer login
			ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
			// select customer/account with transactions
			assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"), equalTo("userSelect"));

			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElement(NgBy.model("custId")).findElements(NgBy.repeater("cust in Customers")));

			while (customers.hasMoreElements()){
				WebElement currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("Hermoine Granger") >= 0 ){
					System.err.println(currentCustomer.getText());
					currentCustomer.click();
				}
			}
			NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(login.isEnabled());
			login.click();
			Enumeration<WebElement> accounts = Collections.enumeration(ngDriver.findElements(NgBy.options("account for account in Accounts")));

			while (accounts.hasMoreElements()){
				WebElement currentAccount = accounts.nextElement();
				if (Integer.parseInt(currentAccount.getText()) == 1001){
					System.err.println(currentAccount.getText());
					currentAccount.click();
				}
			}
			// inspect transactions
			NgWebElement transactions_button = ngDriver.findElement(NgBy.partialButtonText("Transactions"));
						
			assertThat(transactions_button.getText(), equalTo("Transactions"));
			highlight(transactions_button);
			transactions_button.click();
			// wait until transactions are loaded
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("tx in transactions")).getWrappedElement()));
			Iterator<WebElement> transactions = ngDriver.findElements(NgBy.repeater("tx in transactions")).iterator();
			int cnt = 0 ;
			while (transactions.hasNext() && cnt++ < 5) {
				WebElement currentTransaction = (WebElement) transactions.next();
				NgWebElement ngCurrentTransaction = new NgWebElement(ngDriver, currentTransaction);
				assertTrue(ngCurrentTransaction.evaluate("tx.amount").toString().matches("^\\d+$"));
				assertTrue(ngCurrentTransaction.evaluate("tx.type").toString().matches("(?i:credit|debit)"));
				Object transaction_date = ngCurrentTransaction.evaluate("tx.date");
			}
		}
		
		@Test
		public void testOpenAccount() throws Exception {
			// bank manager login
			ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
			ngDriver.findElement(NgBy.partialButtonText("Open Account")).click();
			// wait for customers info get loaded
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("cust in Customers")).getWrappedElement()));
			NgWebElement selectCustomer = ngDriver.findElement(NgBy.model("custId"));
			assertThat(selectCustomer.getAttribute("id"),containsString("userSelect"));
			List<WebElement> customers = new NgWebElement(ngDriver,selectCustomer).findElements(NgBy.repeater("cust in Customers"));
			// pick random customer to log in
			WebElement customer = customers.get((int)(Math.random()*customers.size()));
			System.err.println(customer.getText());
			customer.click();
			NgWebElement ng_selectCurrencies = ngDriver.findElement(NgBy.model("currency"));
			// use core Selenium
			Select selectCurrencies = new Select(ng_selectCurrencies.getWrappedElement());
			List<WebElement> accountCurrencies = selectCurrencies.getOptions();
			// select "Dollars"
			selectCurrencies.selectByVisibleText("Dollar");
			// add the account
			WebElement submitButton = ngDriver.getWrappedDriver().findElement(By.xpath("/html/body//form/button[@type='submit']"));
			assertThat(submitButton.getText(),containsString("Process"));
			submitButton.click();
			try{
				alert = seleniumDriver.switchTo().alert();
				String alert_text = alert.getText();
				assertThat(alert_text,containsString("Account created successfully with account Number"));
				Pattern pattern = Pattern.compile("(\\d+)");
				Matcher matcher = pattern.matcher(alert_text);
				if (matcher.find()) {
					System.err.println("account id " + matcher.group(1) );
				}
				// confirm alert
				alert.accept();

			} catch (NoAlertPresentException ex){
				// Alert not present
				System.err.println(ex.getStackTrace());
				return;
			} catch(WebDriverException ex){
				// Alert not handled by PhantomJS
				// fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(ex);
				// System.err.println("Alert was not handled by PhantomJS: " + fullStackTrace);
				System.err.println("Alert was not handled by PhantomJS: " + ex.getStackTrace().toString());
				return;
			}

		}

		@Test
		public void testSortCustomerAccounts() throws Exception {
			// bank manager login
			ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
			ngDriver.findElement(NgBy.partialButtonText("Customers")).click();
			// wait for customers info get loaded
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("cust in Customers")).getWrappedElement()));
			WebElement sort_link = ngDriver.getWrappedDriver().findElement(By.cssSelector("a[ng-click*='sortType'][ng-click*= 'fName']"));
			assertThat(sort_link.getText(),containsString("First Name"));
			// sort the customers
			highlight(sort_link);
			sort_link.click();

			List<WebElement> customers = ngDriver.findElements(NgBy.repeater("cust in Customers"));
			// note the name of the last customer
			String last_customer_name = customers.get(customers.size() - 1).getText();
			// sort the customers in reverse
			highlight(sort_link);
			sort_link.click();
			WebElement first_customer = ngDriver.findElement(NgBy.repeater("cust in Customers"));
			assertThat(first_customer.getText(),containsString(last_customer_name));
		}

		@Test
		public void testListTransactions() throws Exception {
			// customer login
			ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
			// select customer/account with transactions
			assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"), equalTo("userSelect"));

			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElement(NgBy.model("custId")).findElements(NgBy.repeater("cust in Customers")));

			while (customers.hasMoreElements()){
				WebElement currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("Hermoine Granger") >= 0 ){
					System.err.println(currentCustomer.getText());
					currentCustomer.click();
				}
			}
			NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(login.isEnabled());
			login.click();
			Enumeration<WebElement> accounts = Collections.enumeration(ngDriver.findElements(NgBy.options("account for account in Accounts")));

			while (accounts.hasMoreElements()){
				WebElement currentAccount = accounts.nextElement();
				if (Integer.parseInt(currentAccount.getText()) == 1001){
					System.err.println(currentAccount.getText());
					currentAccount.click();
				}
			}
			// inspect transactions
			NgWebElement transactions = ngDriver.findElement(NgBy.partialButtonText("Transactions"));
			assertThat(transactions.getText(), equalTo("Transactions"));
			highlight(transactions);
			transactions.click();
			// wait until transactions are loaded
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("tx in transactions")).getWrappedElement()));
			Iterator<WebElement> transactionTypeColumns = ngDriver.findElements(NgBy.repeaterColumn("tx in transactions", "tx.type")).iterator();
			while (transactionTypeColumns.hasNext() ) {
				WebElement transactionTypeColumn = (WebElement) transactionTypeColumns.next();
				if (transactionTypeColumn.getText().isEmpty()){
					break;
				}
				if (transactionTypeColumn.getText().equalsIgnoreCase("Credit") ){
					highlight(transactionTypeColumn);
				}
			}
		}
		
		@Test
		public void testAddCustomer() throws Exception {
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
			Object[] addCustomerButtonElements = ngDriver.findElements(NgBy.partialButtonText("Add Customer")).toArray();
			WebElement addCustomerButtonElement = (WebElement) addCustomerButtonElements[1];
			addCustomerButtonElement.submit();
			try {
				alert = seleniumDriver.switchTo().alert();
			} catch (NoAlertPresentException ex){
				// Alert not present
				System.err.println(ex.getStackTrace());
				return;
			} catch(WebDriverException ex){
				// Alert not handled by PhantomJS
				System.err.println("Alert was not handled by PhantomJS: " + ex.getStackTrace());
				return;
			}
			String customer_added = "Customer added successfully with customer id :(\\d+)";
			
			Pattern pattern = Pattern.compile(customer_added);
			Matcher matcher = pattern.matcher(alert.getText());
			if (matcher.find()) {
				System.err.println("customer id " + matcher.group(1) );
			}
			// confirm alert
			alert.accept();
			
			// switch to "Customers" screen
			ngDriver.findElement(NgBy.partialButtonText("Customers")).click();
			Thread.sleep(1000);
			
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("cust in Customers"))));

			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElements(NgBy.repeater("cust in Customers")));
			
			WebElement currentCustomer = null;
			while (customers.hasMoreElements()){
				currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("John Doe") >= 0 ){
					System.err.println(currentCustomer.getText());					
					break;
				}
			}
			assertThat(currentCustomer, notNullValue());
			actions.moveToElement(currentCustomer).build().perform();

			highlight(currentCustomer);
			
			// delete the new customer
			NgWebElement deleteCustomerButton = new NgWebElement(ngDriver, currentCustomer).findElement(NgBy.buttonText("Delete"));
			assertThat(deleteCustomerButton, notNullValue());
			assertThat(deleteCustomerButton.getText(),containsString("Delete"));
			highlight(deleteCustomerButton,300);
			// .. in slow motion
			actions.moveToElement(deleteCustomerButton.getWrappedElement()).clickAndHold().build().perform();
			Thread.sleep(100);
			actions.release().build().perform();
			// let the customers reload
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("cust in Customers"))));
			Thread.sleep(1000);
			// TODO: assert the customers.count change
		}
		
		@Test
		public void testDepositAndWithdraw() throws Exception {
			// customer login
			ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
			
			// select customer with accounts
			assertThat(ngDriver.findElement(NgBy.input("custId")).getAttribute("id"), equalTo("userSelect"));
			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElement(NgBy.model("custId")).findElements(NgBy.repeater("cust in Customers")));
			while (customers.hasMoreElements()){
				WebElement currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("Harry Potter") >= 0 ){
					System.err.println(currentCustomer.getText());
					currentCustomer.click();
				}
			}
			
			NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(login.isEnabled());
			highlight(login);
			login.click();
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.options("account for account in Accounts")).getWrappedElement()));
			List<WebElement> accounts = ngDriver.findElements(NgBy.options("account for account in Accounts"));

			// pick random account 
			assertTrue(accounts.size() > 0 );
			int account_idx = 1 + (int)(Math.random() * (accounts.size() - 1)) ;
			String targetAccount = accounts.get(account_idx).getText();
			System.err.println(account_idx + " " + targetAccount);
			accounts.get(account_idx).click();
			int initialBalance = Integer.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());

			WebElement depositButton = ngDriver.findElements(NgBy.partialButtonText("Deposit")).get(0);
            assertTrue(depositButton.isDisplayed());
			depositButton.click();

			// deposit amount
            WebElement depositAmount = ngDriver.findElement(NgBy.model("amount"));
			highlight(depositAmount);
            depositAmount.sendKeys("100");
			
			// deposit the payment
			depositButton = ngDriver.findElements(NgBy.partialButtonText("Deposit")).get(1);
            assertTrue(depositButton.isDisplayed());
			depositButton.click();
			
			// inspect the message
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.binding("message")).getWrappedElement()));
            NgWebElement message = ngDriver.findElement(NgBy.binding("message"));
            assertThat(message.getText(),containsString("Deposit Successful"));
            highlight(message);

			// inspect the balance change
			int finalBalance = Integer.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());
			assertTrue(finalBalance == 100 + initialBalance);
			Thread.sleep(1000);
			// switch to "Home" screen
            ngDriver.findElement(NgBy.buttonText("Home")).click();
			// customer login
			ngDriver.findElement(NgBy.buttonText("Customer Login")).click();
			
			// find the same customer / account 			
			customers = Collections.enumeration(ngDriver.findElement(NgBy.model("custId")).findElements(NgBy.repeater("cust in Customers")));
			while (customers.hasMoreElements()){
				WebElement currentCustomer = customers.nextElement();
				if (currentCustomer.getText().indexOf("Harry Potter") >= 0 ){
					System.err.println(currentCustomer.getText());
					currentCustomer.click();
				}
			}

			ngDriver.findElement(NgBy.buttonText("Login")).click();			
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.options("account for account in Accounts")).getWrappedElement()));
			Enumeration<WebElement> accounts2 = Collections.enumeration(ngDriver.findElements(NgBy.options("account for account in Accounts")));
			while (accounts2.hasMoreElements()){
				WebElement currentAccount = accounts2.nextElement();
				if (currentAccount.getText().indexOf(targetAccount) >= 0 ){
					System.err.println(currentAccount.getText());
					currentAccount.click();
				}
			}
			
		    WebElement withdrawButton = ngDriver.findElement(NgBy.partialButtonText("Withdrawl"));
            assertTrue(withdrawButton.isDisplayed());
			withdrawButton.click();

			// withdraw a bigger amount then is on the account
            WebElement withdrawAmount = ngDriver.findElement(NgBy.model("amount"));
			highlight(withdrawAmount);
            withdrawAmount.sendKeys(String.format("%d", finalBalance + 10 ));
			withdrawButton =  ngDriver.findElement(NgBy.buttonText("Withdraw"));
			withdrawButton.click();
			
			// confirm the transaction failed
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.binding("message")).getWrappedElement()));
            message = ngDriver.findElement(NgBy.binding("message"));
            assertThat(message.getText(),containsString("Transaction Failed."));
            highlight(message);
			
            withdrawAmount.sendKeys(String.format("%d", finalBalance - 10 ));
			withdrawButton.click();
			// inspect the balance change
			finalBalance = Integer.parseInt(ngDriver.findElement(NgBy.binding("amount")).getText());
			assertTrue(finalBalance == 10 );
			
		}
	}
		public static class CalculatorTests{
	
        private static String baseUrl = "http://juliemr.github.io/protractor-demo/";
   		@Before
		public void beforeEach() {
			ngDriver.navigate().to(baseUrl);
		}

		@Test
		public void testAddition() throws Exception {
			NgWebElement element = ngDriver.findElement(NgBy.model("first"));
			assertThat(element,notNullValue());
			highlight(element, 100);
			element.sendKeys("40");
			element = ngDriver.findElement(NgBy.model("second"));
			assertThat(element,notNullValue());
			highlight(element, 100);
			element.sendKeys("2");
			element = ngDriver.findElement(NgBy.options("value for (key, value) in operators"));
			assertThat(element,notNullValue());
			element.click();
			element = ngDriver.findElement(NgBy.buttonText("Go!"));
			assertThat(element,notNullValue());
			assertThat(element.getText(),containsString("Go"));
			element.click();
			element = ngDriver.findElement(NgBy.binding("latest" ));
			assertThat(element, notNullValue());
			assertThat(element.getText(), equalTo("42"));
			highlight(element, 100);
		}
	}
}
