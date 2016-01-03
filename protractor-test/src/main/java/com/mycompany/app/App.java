package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Hashtable;
import java.lang.RuntimeException;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import org.openqa.selenium.Alert;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;


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

public class App {
	public static VideoRecorder recorder;
	private static WebDriver seleniumDriver;
	private static NgWebDriver ngDriver;
	public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";			
			static int implicit_wait_interval = 1;
	static int flexible_wait_interval = 5;
	static long wait_polling_interval = 500;
	static int width = 600;
	static int height = 400;
	static WebDriverWait wait;
	static Actions actions;
    static int flexibleWait = 5;
	static long pollingInterval = 500;


	private static final StringBuffer verificationErrors = new StringBuffer();

	@Before
public static void setUp() throws Exception {
		seleniumDriver = new FirefoxDriver();
		wait = new WebDriverWait(seleniumDriver, flexibleWait );
		wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);

		try{
			seleniumDriver.manage().window().setSize(new Dimension(width , height ));
			seleniumDriver.manage().timeouts()
				.pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(20, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		}  catch(Exception ex) {
			System.out.println(ex.toString());
		}
		actions = new Actions(seleniumDriver);		
		ngDriver = new NgWebDriver(seleniumDriver);
	ngDriver.navigate().to(baseUrl);
	Thread.sleep(10000);
	recorder = new VideoRecorder();

	recorder.startRecording(seleniumDriver);
        
	}


	private static String css_selector_of(WebElement element){
		String script =  "function get_css_selector_of(element) {\n" +
						"if (!(element instanceof Element))\n" +
						"return;\n" +
						"var path = [];\n" +
						"while (element.nodeType === Node.ELEMENT_NODE) {\n" +
						"var selector = element.nodeName.toLowerCase();\n" +
						"if (element.id) {\n" +
						"if (element.id.indexOf('-') > -1) {\n" +
						"selector += '[id = \"' + element.id + '\"]';\n" +
						"} else {\n" +
						"selector += '#' + element.id;\n" +
						"}\n" +
						"path.unshift(selector);\n" +
						"break;\n" +
						"} else {\n" +
						"var element_sibling = element;\n" +
						"var sibling_cnt = 1;\n" +
						"while (element_sibling = element_sibling.previousElementSibling) {\n" +
						"if (element_sibling.nodeName.toLowerCase() == selector)\n" +
						"sibling_cnt++;\n" +
						"}\n" +
						"if (sibling_cnt != 1)\n" +
						"selector += ':nth-of-type(' + sibling_cnt + ')';\n" +
						"}\n" +
						"path.unshift(selector);\n" +
						"element = element.parentNode;\n" +
						"}\n" +
						"return path.join(' > ');\n" +
						"} \n" +
						"return get_css_selector_of(arguments[0]);\n";
		return (String) execute_script(script, element);

	}

	private static void highlight(WebElement element) throws InterruptedException {

		highlight(element, 100);
	}
	private static void highlight(WebElement element, long highlight_interval) throws InterruptedException {
		if (wait == null)         {
			wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		}
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));
		execute_script("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		execute_script("arguments[0].style.border=''", element);
	}

	private static String xpath_of(WebElement element){
		String script =  "function get_xpath_of(element) {\n" +
						" var elementTagName = element.tagName.toLowerCase();\n" +
						"     if (element.id != '') {\n" +
						"         return '//' + elementTagName + '[@id=\"' + element.id + '\"]';\n" +
						"     } else if (element.name && document.getElementsByName(element.name).length === 1) {\n" +
						"         return '//' + elementTagName + '[@name=\"' + element.name + '\"]';\n" +
						"     }\n" +
						"     if (element === document.body) {\n" +
						"         return '/html/' + elementTagName;\n" +
						"     }\n" +
						"     var sibling_count = 0;\n" +
						"     var siblings = element.parentNode.childNodes;\n" +
						"     siblings_length = siblings.length;\n" +
						"     for (cnt = 0; cnt < siblings_length; cnt++) {\n" +
						"         var sibling_element = siblings[cnt];\n" +
						"         if (sibling_element.nodeType !== 1) { // not ELEMENT_NODE\n" +
						"             continue;\n" +
						"         }\n" +
						"         if (sibling_element === element) {\n" +
						"             return sibling_count > 0 ? get_xpath_of(element.parentNode) + '/' + elementTagName + '[' + (sibling_count + 1) + ']' : get_xpath_of(element.parentNode) + '/' + elementTagName;\n" +
						"         }\n" +
						"         if (sibling_element.nodeType === 1 && sibling_element.tagName.toLowerCase() === elementTagName) {\n" +
						"             sibling_count++;\n" +
						"         }\n" +
						"     }\n" +
						"     return;\n" +
						" };\n" +
						" return get_xpath_of(arguments[0]);\n";
		return (String) execute_script(script, element);
	}
	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.JavascriptExecutor
	public static Object execute_script(String script,Object ... args){
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor=(JavascriptExecutor)seleniumDriver;
			return javascriptExecutor.executeScript(script,args);
		}
		else {
			throw new RuntimeException("Script execution is only available for WebDrivers that implement " + "the JavascriptExecutor interface.");
		}
	}

	private static List<WebElement> find_elements(String selector_type, String selector_value, WebElement parent){
		int flexible_wait_interval = 5;
		SearchContext finder;
		long wait_polling_interval = 500;
		String parent_css_selector = null;
		String parent_xpath = null;

		WebDriverWait wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
		List<WebElement> elements = null;
		Hashtable<String, Boolean> supported_selectors = new Hashtable<String, Boolean>();
		supported_selectors.put("css_selector", true);
		supported_selectors.put("xpath", true);

		if (selector_type == null || !supported_selectors.containsKey(selector_type) || !supported_selectors.get(selector_type)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = css_selector_of (parent );
			parent_xpath = xpath_of(parent );
			finder = parent;
		} else {
			finder = seleniumDriver;
		}

		if (selector_type == "css_selector") {
			String extended_css_selector = String.format("%s  %s", parent_css_selector, selector_value);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selector_value));
		}
		if (selector_type == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath, selector_value);

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(selector_value));
		}
		return elements;

	}

	private static List<WebElement> find_elements(String selector_type, String selector_value){
		return find_elements(selector_type, selector_value, null);
	}


	private static WebElement find_element(String selector_type, String selector_value){
		int flexible_wait_interval = 5;
		long wait_polling_interval = 500;
		WebDriverWait wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
		WebElement element = null;
		Hashtable<String, Boolean> supported_selectors = new Hashtable<String, Boolean>();
		supported_selectors.put("id", true);
		supported_selectors.put("css_selector", true);
		supported_selectors.put("xpath", true);
		supported_selectors.put("partial_link_text", false);
		supported_selectors.put("link_text", true);
		supported_selectors.put("classname", false);

		if (selector_type == null || !supported_selectors.containsKey(selector_type) || !supported_selectors.get(selector_type)) {
			return null;
		}
		if (selector_type == "id") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.id(selector_value));
		}
		if (selector_type == "classname") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.className(selector_value));
		}
		if (selector_type == "link_text") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.linkText(selector_value));
		}
		if (selector_type == "css_selector") {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.cssSelector(selector_value));
		}
		if (selector_type == "xpath") {

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector_value)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = seleniumDriver.findElement(By.xpath(selector_value));
		}
		return element;
	}

	public static void main(String[] args) throws Exception {
		setUp();
	// 	testVerifyText();
//	testAddition();
testAddCustomer();
	// 	testByModel();
		tearDown();


	}
	@Test
	public static void testVerifyText()  throws Exception   {
		String selector = null;
		WebElement element = null;
		try {
			assertEquals("Hotels", find_element("link_text", "Hotels").getText());

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			element =  find_element("link_text", "Hotels");
			highlight(element);
			selector = xpath_of(element);
			assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

		try {
			element =  find_element("xpath", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append(e.toString());
		}
		try {
			element =  find_element("link_text", "Hotels");
			highlight(element);
			selector = css_selector_of(element);
			assertEquals("div#HEAD > div > div:nth-of-type(2) > ul > li > span > a", selector);
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			element =  find_element("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {

			verificationErrors.append(e.toString());
		}

		try {
			element =  find_element("css_selector", "input#mainSearch");
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




		@Test
		public static  void testAddCustomer() throws Exception {
			ngDriver.findElement(NgBy.buttonText("Bank Manager Login")).click();
			ngDriver.findElement(NgBy.partialButtonText("Add Customer")).click();

			WebElement firstName  = ngDriver.findElement(NgBy.model("fName"));
			assertThat(firstName.getAttribute("placeholder"), equalTo("First Name"));
			firstName.sendKeys("John");

			WebElement lastName  = ngDriver.findElement(NgBy.model("lName"));
			assertThat(lastName.getAttribute("placeholder"), equalTo("Last Name"));
			lastName.sendKeys("Doe");

			WebElement postCode  = ngDriver.findElement(NgBy.model("postCd"));
			assertThat(postCode.getAttribute("placeholder"), equalTo("Post Code"));
			postCode.sendKeys("11011");

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
			actions.moveToElement(deleteCustomerButton.getWrappedElement()).clickAndHold().build().perform();
			Thread.sleep(100);
			actions.release().build().perform();
			// let the customers reload
			wait.until(ExpectedConditions.visibilityOf(ngDriver.findElement(NgBy.repeater("cust in Customers"))));
			Thread.sleep(1000);
			// TODO: assert the customers.count change
		}

	@After
	public static void tearDown()  throws Exception
	{
		recorder.stopRecording("Recording");
		ngDriver.close();
		seleniumDriver.quit();
	}
}
