package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.BindException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

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

	@BeforeClass
	public static void setup() throws IOException {
		seleniumDriver = new PhantomJSDriver();
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
        seleniumDriver.quit();		
	}
	
	private static void highlight(WebElement element, long highlight_interval) throws InterruptedException {
		int flexible_wait_interval = 5;
		long wait_polling_interval = 500;
		if (wait == null)         {
			wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		}
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
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

	public static class CalculatorTests{

		public static String baseUrl = "http://juliemr.github.io/protractor-demo/";
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
			Thread.sleep(3000);
			element = ngDriver.findElement(NgBy.binding("latest" )); 
			assertThat(element, notNullValue());
			assertThat(element.getText(), equalTo("42"));
			highlight(element, 100);
		}
	}
	public static class Way2AutomationTests {

		public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";
   		@Before
		public void beforeEach() throws InterruptedException{
			ngDriver.navigate().to(baseUrl);
			Thread.sleep(3000);
		}

		@Test
		public void testCustomerLogin() throws Exception {
			NgWebElement element = ngDriver.findElement(NgBy.buttonText("Customer Login"));
			highlight(element, 100);
			element.click();
			element = ngDriver.findElement(NgBy.input("custId"));
			assertThat(element.getAttribute("id"), equalTo("userSelect"));
			List<WebElement> elements = ngDriver.findElements(NgBy.repeater("cust in Customers"));
			
			Enumeration<WebElement> elements_enumeration = Collections.enumeration(elements);

			while (elements_enumeration.hasMoreElements()){
				WebElement next_element = elements_enumeration.nextElement();
				if (next_element.getText().indexOf("Harry Potter") >= 0 ){
					System.out.println(next_element.getText());
					next_element.click();
				}
			}
			element =  ngDriver.findElement(NgBy.buttonText("Login"));
			assertTrue(element.isEnabled());
		}
	}
}