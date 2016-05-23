package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;
// import static com.jcabi.matchers.RegexMatchers;
import static com.jcabi.matchers.RegexMatchers.*;
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

public class NgButtonTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static boolean state = false;
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
	public void testButtonNgIf() throws Exception {
		//if (isCIBuild) { // Alert not handled by PhantomJS
		//	return;
		//}
		getPageContent("ng_watch_ng_if.htm");		
		
		WebElement button = seleniumDriver.findElement(By.cssSelector("button.btn"));
		Thread.sleep(1000);
		highlight(button);
        NgWebElement ng_button = new NgWebElement(ngDriver, button);
		assertThat(ng_button, notNullValue());
		assertThat(ng_button.getAttribute("ng-click"), equalTo("house.frontDoor.open()"));
		assertThat(ng_button.getText(), equalTo("Open Door"));		 
		ngDriver.waitForAngular();
		try {		    
			state = ((Boolean) ng_button.evaluate("!house.frontDoor.isOpen")).booleanValue(); 
		} catch(WebDriverException ex) { 
		    // [JavaScript Error: "e is null"] ?
			System.err.println("evaluate was not handled : " + ex.getStackTrace().toString());
		} catch(Exception ex) { 
		System.err.println("evaluate was not handled : " + ex.getStackTrace().toString());
		}
		assertTrue(state);
		button.click();
		try{
			// confirm alert
			seleniumDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex){
			// Alert not present - ignore			
		} catch(WebDriverException ex){			
			System.err.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		button = seleniumDriver.findElement(By.cssSelector("button.btn"));
		highlight(button);
        ng_button = new NgWebElement(ngDriver, button);
		assertThat(ng_button, notNullValue());
		assertThat(ng_button.getAttribute("ng-click"), equalTo("house.frontDoor.close()"));
		assertThat(ng_button.getText(), equalTo("Close Door"));		
		try {			
			state = ((Boolean) ng_button.evaluate("house.frontDoor.isOpen")).booleanValue(); 
		} catch(WebDriverException ex) { 
		    // [JavaScript Error: "e is null"] ?
			System.err.println("evaluate was not handled : " + ex.getStackTrace().toString());
		} catch(Exception ex) { 
		System.err.println("evaluate was not handled : " + ex.getStackTrace().toString());
		}
		assertTrue(state);
	}		
	@Test
	public void testButtonStateText() throws Exception {
		//if (isCIBuild) { // Alert not handled by PhantomJS
		//	return;
		//}
		getPageContent("ng_watch_ng_if.htm");				
		WebElement button = seleniumDriver.findElement(By.cssSelector("button.btn"));
		Thread.sleep(1000);
		highlight(button);
		ngDriver.waitForAngular();
		NgWebElement ng_status = ngDriver.findElement(NgBy.binding("house.frontDoor.isOpen"));
		highlight(ng_status);
		System.err.println("Initially: " + ng_status.getText());
		assertTrue(ng_status.getText().matches("The door is closed"));
		button.click();
		try{
			// confirm alert
			seleniumDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex){
			// Alert not present - ignore			
		} catch(WebDriverException ex){			
			System.err.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		ngDriver.waitForAngular();
		ng_status = ngDriver.findElement(NgBy.binding("house.frontDoor.isOpen"));
		highlight(ng_status);
		System.err.println("Finally: " +  ng_status.getText());
		assertThat(ng_status.getText(), matchesPattern(".+open"));
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
	  CommonFunctions.highlight(element);
  }
	
	private static String getIdentity(WebElement element ) throws InterruptedException {
		String script = "return angular.identity(angular.element(arguments[0])).html();";
		// returns too little HTML information in Java 
		return CommonFunctions.executeScript(script, element).toString();
	}

}
