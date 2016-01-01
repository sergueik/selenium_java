package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

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

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

@RunWith(Enclosed.class)
// @Category(Integration.class)
	public class NgByIntegrationTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
    static int flexible_wait_interval = 5;
	static long wait_polling_interval = 500;
	// For desktop browser testing, run a Selenium node and Selenium hub on port 4444	
	@BeforeClass
	public static void setup() throws IOException {
		DesiredCapabilities capabilities =   new DesiredCapabilities("firefox", "", Platform.ANY);
		FirefoxProfile profile = new ProfilesIni().getProfile("default");
		capabilities.setCapability("firefox_profile", profile);
		seleniumDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
		wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);

		try{
			seleniumDriver.manage().window().setSize(new Dimension(600, 800));
			seleniumDriver.manage().timeouts()
				.pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(20, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		}  catch(Exception ex) {
			System.out.println(ex.toString());
		}
		
		ngDriver = new NgWebDriver(seleniumDriver);
		actions = new Actions(seleniumDriver);
	}

	// for CI build
	// @BeforeClass
	// public static void setup() throws IOException {
	//	seleniumDriver = new PhantomJSDriver();
	//	wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
	//	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
	//  actions = new Actions(seleniumDriver);
	//	ngDriver = new NgWebDriver(seleniumDriver);
	// }

	@AfterClass
	public static void teardown() {
		ngDriver.close();
        seleniumDriver.quit();		
	}
	
	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element,  100);
	}

	private static void highlight(WebElement element, long highlight_interval ) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		execute_script("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		execute_script("arguments[0].style.border=''", element);
	}

	private static void highlight(NgWebElement element, long highlight_interval) throws InterruptedException {
		highlight(element.getWrappedElement(), highlight_interval);
	}
	
	public static Object execute_script(String script,Object ... args){
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
			highlight(element, 100);
			element.click();
			element = ngDriver.findElement(NgBy.input("custId"));
			assertThat(element.getAttribute("id"), equalTo("userSelect"));
			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElements(NgBy.repeater("cust in Customers")));

			while (customers.hasMoreElements()){
				WebElement next_customer = customers.nextElement();
				if (next_customer.getText().indexOf("Harry Potter") >= 0 ){
					System.err.println(next_customer.getText());
					next_customer.click();
				}
			}
			NgWebElement login_element = ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(login_element.isEnabled());	
			login_element.click();			
			assertThat(ngDriver.findElement(NgBy.binding("user")).getText(),containsString("Harry"));
			
			NgWebElement account_number_element = ngDriver.findElement(NgBy.binding("accountNo"));
			assertThat(account_number_element, notNullValue());
			assertTrue(account_number_element.getText().matches("^\\d+$"));
		}
		
		@Test
		public void testAddCustomer() throws Exception {
			ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
			ngDriver.findElement(NgBy.partialButtonText("Add Customer")).click();

			NgWebElement firstNameElement  = ngDriver.findElement(NgBy.model("fName"));
			assertThat(firstNameElement.getAttribute("placeholder"), equalTo("First Name"));
			firstNameElement.sendKeys("John");

			NgWebElement lastNameElement  = ngDriver.findElement(NgBy.model("lName"));
			assertThat(lastNameElement.getAttribute("placeholder"), equalTo("Last Name"));
			lastNameElement.sendKeys("Doe");

			NgWebElement postCodeElement  = ngDriver.findElement(NgBy.model("postCd"));
			assertThat(postCodeElement.getAttribute("placeholder"), equalTo("Post Code"));
			postCodeElement.sendKeys("11011");

			// NOTE: there are two 'Add Customer' buttons on this form
			Object[] addCustomerButtonElements = ngDriver.findElements(NgBy.partialButtonText("Add Customer")).toArray();
			WebElement addCustomerButtonElement = (WebElement) addCustomerButtonElements[1];
			addCustomerButtonElement.submit();
			Alert alert =  seleniumDriver.switchTo().alert();
			String customer_added = "Customer added successfully with customer id :(\\d+)";
			
			Pattern pattern = Pattern.compile(customer_added);
            Matcher matcher = pattern.matcher(alert.getText());
			if (matcher.find()) {
				System.out.println("customer id " + matcher.group(1) );
			}
			// confirm alert
			alert.accept();
			
			// switch to "Customers" screen
			ngDriver.findElement(NgBy.partialButtonText("Customers")).click();
			Thread.sleep(1000);
			Enumeration<WebElement> customers = Collections.enumeration(ngDriver.findElements(NgBy.repeater("cust in Customers")));
			
			WebElement next_customer = null;
			while (customers.hasMoreElements()){
				next_customer = customers.nextElement();
				if (next_customer.getText().indexOf("John Doe") >= 0 ){
					System.err.println(next_customer.getText());					
				}
			}
			assertThat(next_customer, notNullValue());
			actions.moveToElement(next_customer).build().perform();

			highlight(next_customer);
			// unfinished - find and click 'Delete' column
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
