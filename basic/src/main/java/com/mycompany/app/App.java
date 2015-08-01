package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import java.awt.Toolkit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Hashtable;
import java.lang.RuntimeException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
private WebDriver driver;
String SiteURL = "http://www.smh.com.au/";

private final StringBuffer verificationErrors = new StringBuffer();

@Before
public void setUp() throws Exception {
	long implicit_wait_interval = 3;
	driver = new FirefoxDriver();
	driver.get(SiteURL);
	driver.manage().timeouts().implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
}

// http://www.adam-bien.com/roller/abien/entry/named_parameters_in_java_8
// does nothing exist prior to java 8 ?

private WebElement find_element(String selector_type, String selector_value){
	int flexible_wait_interval = 5;
	long wait_polling_interval = 500;
	WebDriverWait wait = new WebDriverWait(driver, flexible_wait_interval );
	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
	WebElement element = null;
	Hashtable<String, Boolean> supported_selectors = new Hashtable<String, Boolean>();
	supported_selectors.put("id", true);
	supported_selectors.put("css_selector", true);
	supported_selectors.put("xpath", true);
	supported_selectors.put("partial_link_text", false);
	supported_selectors.put("link_text", true);
	supported_selectors.put("classname", false);
	
	if (selector_type == null || !supported_selectors.containsKey(selector_type) || !supported_selectors.get(selector_type)){ 
			return null;
	}
	if (selector_type == "id") {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector_value)));
		} catch (RuntimeException timeoutException) {
// ignore
			return null;
		}
		element = driver.findElement(By.id(selector_value));
	}
	
		if (selector_type == "id") {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector_value)));
		} catch (RuntimeException timeoutException) {
// ignore
			return null;
		}
		element = driver.findElement(By.id(selector_value));
	}

	
		if (selector_type == "link_text") {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(selector_value)));
		} catch (RuntimeException timeoutException) {
// ignore
			return null;
		}
		element = driver.findElement(By.linkText(selector_value));
	}

	
		if (selector_type == "css_selector") {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector_value)));
		} catch (RuntimeException timeoutException) {
// ignore
			return null;
		}
		element = driver.findElement(By.cssSelector(selector_value));
	}

	
		if (selector_type == "xpath") {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector_value)));
// http://stackoverflow.com/questions/11736027/webdriver-wait-for-element
//By.linkText("View report")));
		} catch (RuntimeException timeoutException) {
// ignore
			return null;
		}
		element = driver.findElement(By.xpath(selector_value));
	}

	return element;
}

@Test
public void testVerifyText() throws Exception {
//-THIS SECTION SHOULD PASS AS TEXT IS CORRECT
	try {
		assertEquals("Copyright © 2013 Fairfax Media", driver.findElement(By.cssSelector("div.nN-footerLinks > cite")).getText());
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
	try {
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("Fairfax Media"));
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
	try {
		assertEquals("World", driver.findElement(By.linkText("World")).getText());
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
//-THIS SECTION SHOULD FAIL AS TEXT IS NOT CORRECT
	try {
		assertEquals("Copyright © 2013 FairfaXXX Media", driver.findElement(By.cssSelector("div.nN-footerLinks > cite")).getText());
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
	try {
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("FAIRLY OK Media"));
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
	try {
		assertEquals("World IS A WEIRD PLACE", driver.findElement(By.linkText("World")).getText());
	} catch (Error e) {
		verificationErrors.append(e.toString());
	}
}


@After
public void tearDown() throws Exception
{
	driver.quit();
}

}
