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
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
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

public class NgIgnoreSyncTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static String fullStackTrace;
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
	public void testNonAngular() throws Exception {

		ngDriver.navigate().to("http://www.google.com");
		// expecting exception about to be thrown is browser-specific 
		
		try { 
			long startTime = System.currentTimeMillis();
			ngDriver.waitForAngular();
			// exception: window.angular is undefined
			long estimatedTime = System.currentTimeMillis() - startTime;			
			System.err.println("waitForAngular: " + estimatedTime);
			NgWebElement element = ngDriver.findElement(By.cssSelector("input#gs_htif0"));
			element.getAttribute("id");
		} catch (TimeoutException exception) { 
			System.err.println("TimeoutException thrown:");
			fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(exception);
			System.err.println("Exception thrown: " + fullStackTrace);
			return;
		}		
	}
	
	@Test
	public void testNonAngularIgnoreSync() throws Exception {
			NgWebDriver ngDriver = new NgWebDriver(seleniumDriver, true);
			ngDriver.navigate().to("http://www.google.com");
			long startTime = System.currentTimeMillis();
			ngDriver.waitForAngular();
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.err.println("waitForAngular: " + estimatedTime);
			NgWebElement element = ngDriver.findElement(By.cssSelector("input#gs_htif0"));
		try { 
			element.getAttribute("id");
		} catch (TimeoutException exception) { 
			fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(exception);
			System.err.println("TimeoutException thrown: " + fullStackTrace);
			return;
		}
	}
	
	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();		
	}


}
