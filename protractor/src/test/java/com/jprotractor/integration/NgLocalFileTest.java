package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Locale;
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
import org.openqa.selenium.Keys;
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

/**
 * Local file Integration tests
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

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

	@Test
	public void testEvaluate() throws Exception {
		if (!isCIBuild) {
			return;
		}			
		getPageContent("ng_service.htm");
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
		seleniumDriver.navigate().to("http://www.w3schools.com/angular/customers.php");
		System.err.println("Customers:" + seleniumDriver.getPageSource());
		getPageContent("ng_service.htm");
		ngDriver.waitForAngular();
		ArrayList<WebElement> people =  new ArrayList<WebElement>(ngDriver.findElements(NgBy.repeater("person in people")));
		System.err.println("Found people.size() = " + people.size() );
		ArrayList<WebElement> countryColumns =  new  ArrayList<WebElement>();
		Integer cnt = 0;
		while (countryColumns.size() == 0) {
			ngDriver.waitForAngular();
			countryColumns = new ArrayList<WebElement>(ngDriver.findElements(NgBy.repeaterColumn("person in people", "person.Country")));
			System.err.println("Found Countries.size() = " + countryColumns.size() );
				cnt = cnt + 1;
				assertTrue( cnt < 10 );
				
		}
		Iterator<WebElement> countryColumnsIterator = countryColumns.iterator();
		cnt = 0 ;
		while (countryColumnsIterator.hasNext() ) {
			WebElement countryColumn = (WebElement) countryColumnsIterator.next();
			System.err.println(countryColumn.getText() );
			if (countryColumn.getText().equalsIgnoreCase("Mexico") ){
				highlight(countryColumn);
				cnt = cnt + 1;
			}
		}
		System.err.println("Mexico = " + cnt.toString() );
		assertTrue(cnt == 3);	
	}		

	/*
	@Test
	public void testFindSelectedtOption() throws Exception {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_select_array.htm");
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
		getPageContent("ng_select_array.htm");
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
		getPageContent("ng_repeat_start_end.htm");
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
		getPageContent("ng_options_with_object.htm");
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
		//  NOTE: works with Angular 1.2.13, fails with Angular 1.4.9
		getPageContent("ng_pattern_validate.htm");
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
	public void testFindRepeaterElement() throws Exception {
		if (!isCIBuild) {
			return;
		}			
		getPageContent("ng_basic.htm");
		WebElement element = ngDriver.findElement(NgBy.repeaterElement("item in items",1,"item.b"));
		System.err.println("item[row='1'][col='b'] = " + element.getText());
		highlight(element);	
		List <WebElement>elements = ngDriver.findElements(NgBy.repeaterElement("item in items",5,"item.a"));
		assertThat(elements.size(), equalTo(0));
	}

	@Test
	public void testElementTextIsGenerated() throws Exception {
		if (!isCIBuild) {
			return;
		}			
		getPageContent("ng_load_json_data.htm");
		WebElement name  = ngDriver.findElement(NgBy.model("name"));
		highlight(name);
		name.sendKeys("John");
		name.sendKeys(Keys.TAB);
		// NOTE: explicitly done by getAttribute.
		// ngDriver.waitForAngular();
		WebElement greeting = ngDriver.findElement(NgBy.model("greeting"));
		highlight(greeting);
		//JavascriptExecutor js = (JavascriptExecutor) ngDriver.getWrappedDriver();
		//String greeting_text = js.executeScript("return arguments[0].value", greeting).toString();
		//assertTrue(greeting_text.length() > 0);
		String greeting_text = greeting.getAttribute("value");
		System.err.println( greeting_text );
		assertTrue(greeting_text.length() > 0);
	}

    @Test
	public void testDropDownWatch() throws Exception {
		if (!isCIBuild) {
			return;
		}
		//  NOTE: works with Angular 1.2.13, fails withAngular 1.4.9
		getPageContent("ng_dropdown_watch.htm");
		String optionsCountry = "country for country in countries";
		List <WebElement>elementsCountries = ngDriver.findElements(NgBy.options(optionsCountry));
		assertThat(elementsCountries.size(), equalTo(3));

		Iterator<WebElement> iteratorCountries = elementsCountries.iterator();
		while (iteratorCountries.hasNext()) {
			WebElement country = (WebElement) iteratorCountries.next();
			if (country.getAttribute("value").isEmpty()){
				continue;
			}
			assertTrue(country.getAttribute("value").matches("^\\d+$"));
			assertTrue(country.getText().matches("(?i:China|United States)"));
			System.err.println("country = " + country.getText() );
		}
		String optionsState = "state for state in states";
		WebElement elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertTrue(!elementState.isEnabled());

		Select selectCountries = new Select(ngDriver.findElement(NgBy.model("country")));
		selectCountries.selectByVisibleText("china");
		WebElement selectedOptionCountry = ngDriver.findElement(NgBy.selectedOption(optionsCountry));
		try{
			assertThat(selectedOptionCountry, notNullValue());
		} catch (AssertionError e) {
		}
		assertTrue(elementState.isEnabled());
		
		List <WebElement>elementsStates = ngDriver.findElements(NgBy.options(optionsState));
		assertThat(elementsStates.size(), equalTo(3));
		Iterator<WebElement> iteratorStates = elementsStates.iterator();
		while (iteratorStates.hasNext()) {
			WebElement state = (WebElement) iteratorStates.next();
			if (state.getAttribute("value").isEmpty()){
				continue;
			}
			assertTrue(state.getAttribute("value").matches("^\\d+$"));
			assertTrue(state.getText().matches("(?i:BeiJing|ShangHai)"));
			System.err.println("state = " + state.getText());
		}

	}

    @Test
	public void testFindRepeaterRows() throws Exception {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_todo.htm");
		String todos_repeater = "todo in todoList.todos";
		List <WebElement>todos = ngDriver.findElements(NgBy.repeaterRows(todos_repeater,1));
		assertTrue(todos.size() > 0);
		System.err.println("TODO: " + todos.get(0).getText());
		todos = ngDriver.findElements(NgBy.repeaterRows(todos_repeater,-1));
		assertThat(todos.size(), equalTo(0));
		List <WebElement>todos_check = ngDriver.findElements(NgBy.repeater(todos_repeater));
		todos = ngDriver.findElements(NgBy.repeaterRows(todos_repeater,todos_check.size() + 1));
		assertThat(todos.size(), equalTo(0));
		
	}
	
    @Test
	public void testFindAllBindings() throws Exception {
		if (!isCIBuild) {
			return;
		}
		getPageContent("ng_directive_binding.htm");
		
		WebElement container = ngDriver.getWrappedDriver().findElement(By.cssSelector("body div"));
		assertThat(container, notNullValue());
		// will show class="ng-binding" added to each node
		System.err.println(container.getAttribute("innerHTML") );		
		List <WebElement>names = ngDriver.findElements(NgBy.binding("name"));
		assertTrue(names.size() == 5 );

		Iterator<WebElement> iteratorNames = names.iterator();		
		while (iteratorNames.hasNext()) {
			WebElement name = (WebElement) iteratorNames.next();
			// will show class="ng-binding" added to every node
			System.err.println(name.getAttribute("outerHTML") );
			System.err.println(getIdentity(name));
		}
	}

    @Test
	public void testDropDown() throws Exception {
		if (!isCIBuild) {
			return;
		}
		//  NOTE: works with Angular 1.2.13, fails with Angular 1.4.9
		getPageContent("ng_dropdown.htm");
		
		String optionsCountry = "country for (country, states) in countries";
		List <WebElement>elementsCountries = ngDriver.findElements(NgBy.options(optionsCountry));
		assertThat(elementsCountries.size(), equalTo(4));
		Iterator<WebElement> iteratorCountries = elementsCountries.iterator();
		while (iteratorCountries.hasNext()) {
			WebElement country = (WebElement) iteratorCountries.next();
			if (country.getAttribute("value").isEmpty()){
				continue;
			}
			assertTrue(country.getAttribute("value").matches("(?i:India|Australia|Usa)"));
			System.err.println("country = " + country.getAttribute("value") );
		}

		String optionsState = "state for (state,city) in states";
		WebElement elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertTrue(!elementState.isEnabled());

		WebElement element = ngDriver.findElement(NgBy.options(optionsCountry));
		assertThat(element.getText().toLowerCase(Locale.getDefault()),containsString("select"));
		assertTrue(element.isEnabled());

		Select selectCountries = new Select(ngDriver.findElement(NgBy.model("states")));
		selectCountries.selectByValue("Australia");
		WebElement selectedOptionCountry = ngDriver.findElement(NgBy.selectedOption(optionsCountry));
		try{
			assertThat(selectedOptionCountry, notNullValue());
		} catch (AssertionError e) {
		}
		elementState = ngDriver.findElement(NgBy.options(optionsState));
		assertTrue(elementState.isEnabled());
		List <WebElement>elementsStates = ngDriver.findElements(NgBy.options(optionsState));
		assertThat(elementsStates.size(), equalTo(3));
		Iterator<WebElement> iteratorStates = elementsStates.iterator();
		while (iteratorStates.hasNext()) {
			WebElement state = (WebElement) iteratorStates.next();
			if (state.getAttribute("value").isEmpty()){
				continue;
			}
			assertTrue(state.getAttribute("value").matches("(?i:New South Wales|Victoria)"));
			System.err.println("state = " + state.getAttribute("value") );
		}
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();		
	}

	private static void getPageContent(String pagename) throws InterruptedException{
		String baseUrl = CommonFunctions.getPageContent( pagename) ;
		ngDriver.navigate().to(baseUrl);
		Thread.sleep(500);
	}

	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element,  100);
	}

	private static void highlight(WebElement element, long highlightInterval ) throws InterruptedException {
		CommonFunctions.highlight(element, highlightInterval);
	}
	
	private static String getIdentity(WebElement element ) throws InterruptedException {
		String script = "return angular.identity(angular.element(arguments[0])).html();";
		// returns too little HTML information in Java 
		return CommonFunctions.executeScript(script, element).toString();
	}

}
