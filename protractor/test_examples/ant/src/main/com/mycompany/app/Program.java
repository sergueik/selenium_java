package com.mycompany.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.io.IOException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.openqa.selenium.firefox.FirefoxDriver;
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

import org.testng.*;
import org.testng.annotations.*;

public class Program {

	private static NgWebDriver ngDriver;
	private static WebDriver driver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite( ITestContext context ) throws InterruptedException {
	
        driver = new FirefoxDriver(); 
		driver.manage()
			.timeouts()
			.pageLoadTimeout(50, TimeUnit.SECONDS)
			.implicitlyWait(implicitWait, TimeUnit.SECONDS)
			.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait );
		wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
		actions = new Actions(driver);		
		ngDriver = new NgWebDriver(driver);

	}
	
    @Test      
    public void testAddition() throws InterruptedException{
		driver.get("http://juliemr.github.io/protractor-demo/");
		NgWebElement element = ngDriver.findElement(NgBy.model("first"));
		Assert.assertNotNull(element);
		highlight(element, 100);
		element.sendKeys("40");
		element = ngDriver.findElement(NgBy.model("second"));
		Assert.assertNotNull(element);
		highlight(element, 100);
		element.sendKeys("2");
		element = ngDriver.findElement(NgBy.options("value for (key, value) in operators"));
		element.click();
		element = ngDriver.findElement(NgBy.buttonText("Go!"));
		Assert.assertTrue(element.getText().indexOf("Go") == 0);
		element.click();
		element = ngDriver.findElement(NgBy.binding("latest" ));
		Assert.assertEquals("42", element.getText());
		highlight(element, 100);
	}
	
	@AfterSuite(alwaysRun = true,enabled =true) 
	public void cleanupSuite() {
			System.out.println("testClass1.cleanupSuite: after suite");
			driver.quit();
	    }

	public static void highlight(WebElement element, long highlightInterval ) throws InterruptedException {
		int flexibleWait = 5;
		long pollingInterval = 500;
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait );
		wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlightInterval);
		executeScript("arguments[0].style.border=''", element);
	}

	public static Object executeScript(String script,Object ... args){
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor=(JavascriptExecutor)driver;
			return javascriptExecutor.executeScript(script,args);
		}
		else {
			throw new RuntimeException("Script execution is only available for WebDrivers that implement " + "the JavascriptExecutor interface.");
		}
	}
	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element,  100);
	}

}       
