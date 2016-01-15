package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	public class NgLocalFileTest {
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
	static boolean isCIBuild =  false;
	public static String localFile;

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width , height ));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait );
		wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);		
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	private static String getScriptContent(String filename) {
		return CommonFunctions.	getScriptContent( filename) ;
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
		CommonFunctions.highlight(element, highlightInterval);
	}

	private static void highlight(NgWebElement element) throws InterruptedException {
		highlight(element,  100);
	}

	private static void highlight(NgWebElement element, long highlightInterval ) throws InterruptedException {
		CommonFunctions.highlight(element, highlightInterval);
	}

	
	@Test
	public void testEvaluate() throws Exception {
		if (!isCIBuild) {
			return;
		}			
		localFile = "ng_service_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));
		Thread.sleep(500);
		Enumeration<WebElement> elements = Collections.enumeration(ngDriver.findElements(NgBy.repeater("person in people")));
		while (elements.hasMoreElements()){
			WebElement currentElement = elements.nextElement();
			if (currentElement.getText().isEmpty()){
				break;
			}
			WebElement personName = new NgWebElement(ngDriver,currentElement).findElement(NgBy.binding("person.Name"));
			assertThat(personName, notNullValue());
			Object personCountry = new NgWebElement(ngDriver,currentElement).evaluate("person.Country");
			assertThat(personCountry, notNullValue());
			System.err.println(personName.getText() + " " + personCountry.toString());
			if (personName.getText().indexOf("Around the Horn") >= 0 ){
				assertThat(personCountry.toString(),containsString("UK"));	
				highlight(personName);
			}
		}
	}

	@Test
	public void testFindElementByRepeaterColumn() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "ng_service_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));
		Thread.sleep(500);
		Iterator<WebElement> countryColumns = ngDriver.findElements(NgBy.repeaterColumn("person in people", "person.Country")).iterator();
		Integer cnt = 0;
		while (countryColumns.hasNext() ) {
			WebElement countryColumn = (WebElement) countryColumns.next();
			System.err.println(countryColumn.getText() );
			if (countryColumn.getText().equalsIgnoreCase("Mexico") ){
				highlight(countryColumn);
				cnt = cnt + 1;
			}
		}
		assertTrue(cnt == 3);	
		System.err.println("Mexico = " + cnt.toString() );
	}		

	/*
	@Test
	public void testFindSelectedtOption() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "bind_select_option_data_from_array_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));
		Thread.sleep(500);
		// Some versions of PhantomJS have trouble finding the selectedOption in
		// <option ng-repeat="option in options" value="3" ng-selected="option.value == myChoice" class="ng-scope ng-binding" selected="selected">three</option>
		WebElement element = ngDriver.findElement(NgBy.selectedOption("myChoice"));
		Thread.sleep(500);
		assertThat(element, notNullValue());
		assertTrue(element.isDisplayed());
		assertThat(element.getText(),containsString("three"));		
		System.err.println(element.getText() );
	}
	*/

	/*		
	@Test
	public void testChangeSelectedtOption() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "bind_select_option_data_from_array_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));
		Thread.sleep(500);
		Iterator<WebElement> options = ngDriver.findElements(NgBy.repeater("option in options")).iterator();
		while (options.hasNext() ) {
			WebElement option = (WebElement)  options.next();
			System.err.println("option = " + option.getText() );
			if (option.getText().isEmpty()){
				break;
			}
			if (option.getText().equalsIgnoreCase("two") ){
                    option.click();
                }
            }
		Thread.sleep(500);
		// Some versions of PhantomJS have trouble finding the selectedOption in
		// <option ng-repeat="option in options" value="3" ng-selected="option.value == myChoice" class="ng-scope ng-binding" selected="selected">three</option>
		NgWebElement element = ngDriver.findElement(NgBy.selectedOption("myChoice"));
		assertThat(element, notNullValue());
		System.err.println("selectedOption = " + element.getText() );
		assertThat(element.getText(),containsString("two"));		
	}
	*/

	@Test
	public void testFindElementByRepeaterWithBeginEnd() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "ng_repeat_start_and_ng_repeat_end_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));			
		Thread.sleep(500);
		List<WebElement> elements = ngDriver.findElements(NgBy.repeater("definition in definitions"));
		assertTrue(elements.get(0).isDisplayed());
		assertThat(elements.get(0).getText(),containsString("Foo"));
		System.err.println(elements.get(0).getText() );
	}
	
	@Test
	public void testFindElementByOptions() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "ng_options_with_object_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));			
		Thread.sleep(500);
		List<WebElement> elements = ngDriver.findElements(NgBy.options("c.name for c in colors"));
		assertTrue(elements.size() == 5);
		assertThat(elements.get(0).getText(),containsString("black"));
		System.err.println(elements.get(0).getText() );
		assertThat(elements.get(1).getText(),containsString("white"));
		System.err.println(elements.get(1).getText() );
	}
	
	@Test
	public void testFindElementByModel() throws Exception {
		if (!isCIBuild) {
			return;
		}
		localFile = "use_ng_pattern_to_validate_example.htm";
		ngDriver.navigate().to(getScriptContent(localFile));			
		Thread.sleep(500);
		WebElement input = ngDriver.findElement(NgBy.model("myVal"));
		input.clear();
		WebElement valid = ngDriver.findElement(NgBy.binding("form.value.$valid"));
		assertThat(valid.getText(),containsString("false"));
		System.err.println( valid.getText()); // valid: false
		WebElement pattern = ngDriver.findElement(NgBy.binding("form.value.$error.pattern"));
		assertThat(pattern.getText(),containsString("false"));
		System.err.println(pattern.getText()); // pattern: false
		WebElement required = ngDriver.findElement(NgBy.binding("!!form.value.$error.required"));
		assertThat(required.getText(),containsString("true"));
		System.err.println(required.getText()); // required: true

		input.sendKeys("42");
		valid = ngDriver.findElement(NgBy.binding("form.value.$valid"));
		assertThat(valid.getText(),containsString("true"));
		System.err.println(valid.getText()); // valid: true
		pattern = ngDriver.findElement(NgBy.binding("form.value.$error.pattern"));
		assertThat(pattern.getText(),containsString("false"));
		System.err.println(pattern.getText()); // pattern: false
		required = ngDriver.findElement(NgBy.binding("!!form.value.$error.required"));
		assertThat(required.getText(),containsString("false"));
		System.err.println(required.getText()); // required: false
	}

	@Test
	public void testfindRepeaterElement() throws Exception {
		if (!isCIBuild) {
			return;
		}			
		localFile = "ng_basic.htm";
		ngDriver.navigate().to(getScriptContent(localFile));
		Thread.sleep(500);
		WebElement element = ngDriver.findElement(NgBy.repeaterElement("item in items",1,"item.b"));
		System.err.println("item[row='1'][col='b'] = " + element.getText());
		highlight(element);	
		List <WebElement>elements = ngDriver.findElements(NgBy.repeaterElement("item in items",5,"item.a"));
		assertThat(elements.size(), equalTo(0));
	}
}
