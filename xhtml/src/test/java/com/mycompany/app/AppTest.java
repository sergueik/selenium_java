package com.mycompany.app;

import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.Boolean.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

	private static String baseUrl = "http://www.orcca.on.ca/~elena/useful/AboutMathML/MathML.xhtml";
	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private WebElement element = null;
	private String selector = null;
	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;
	private static long afterTest = 1000;
	private static final StringBuffer verificationErrors = new StringBuffer();
	private WebElement cell_element;

	@BeforeClass
	public static void setUp() throws Exception {

		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.manage().timeouts()
				.implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
		actions = new Actions(driver);

	}

	@Before
	public void beforeTest() throws InterruptedException {
		System.err.println(baseUrl);
		driver.get(baseUrl);
		cell_element = driver
				.findElement(By
						.cssSelector("table tr:nth-of-type(1) td:nth-of-type(1) p table tr:nth-of-type(2) td:nth-of-type(1)"));
		assertThat(cell_element, notNullValue());
		actions.moveToElement(cell_element).build().perform();
		highlight(cell_element);
	}

	@Test
	public void verifyXPath() throws Exception {
		WebElement element = null;
		String expression = "//*[local-name()='mrow']/*[local-name()='mrow']/*[local-name()='msup']/*[local-name()='mrow']";
		System.err.println("Try locate by modified xpath: " + expression);
		try {
			element = cell_element.findElement(By.xpath(expression));
		} catch (NoSuchElementException e) {
			System.err.println("Cannot locate by modified xpath: " + expression);
			verificationErrors.append(e.toString());
		}
		assertThat(element, notNullValue());
		System.err.println(element.getText());
		// ( exp x + 3 )
		// prints the whole <math> node.
		// System.err.println(cell_element.getAttribute("innerHTML"));
		try {
			highlight(element);
		} catch (org.openqa.selenium.WebDriverException e) {
			// arguments[0].style is undefined. Ignore
			/*
			 * System.err .println("Cannot apply style due to exception: " +
			 * e.toString()); verificationErrors.append(e.toString());
			 */
		}
	}

	@Test
	public void verifySimpleXPath() throws Exception {
		String expression = "//mrow";
		System.err.println("Try locate by xpath: " + expression);
		WebElement element = null;
		try {
			element = cell_element.findElement(By.xpath(expression));
		} catch (NoSuchElementException e) {
			System.err.println("Cannot locate by xpath: " + expression);
			// expect to not be able to find
		}
		assertThat(element, nullValue());
	}

	@Test
	public void verifyTagName() throws Exception {
		WebElement element = null;
		String expression = "mrow";
		System.err.println("Try locate by tagName: " + expression);
		try {
			element = cell_element.findElement(By.tagName(expression));
		} catch (NoSuchElementException e) {
			System.err.println("Cannot locate by tagName: " + expression);
			verificationErrors.append(e.toString());
		}
		assertThat(element, notNullValue());
		System.err.println(element.getText());
		// ( exp x + 3 ) ? ? 3 ? x 3 ? 2 ? y 2 ( x y + y x + x 3 )
	}

	@Test
	public void verifyCssSelector() throws Exception {
		WebElement element = null;
		String expression = "mrow mrow msup mrow";
		System.err.println("Try locate by cssSelector: " + expression);
		try {
			element = cell_element.findElement(By.cssSelector(expression));
		} catch (NoSuchElementException e) {
			System.err.println("Cannot locate by cssSelector: " + expression);
			verificationErrors.append(e.toString());
		}
		assertThat(element, notNullValue());
		System.err.println(element.getText());
		// ( exp x + 3 ) ? ? 3 ? x 3 ? 2 ? y 2 ( x y + y x + x 3 )
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(afterTest);
		driver.close();
		driver.quit();
		/*
		 * if (verificationErrors.length() != 0) { throw new
		 * Exception(verificationErrors.toString()); }
		 */
	}

	private void highlight(WebElement element) throws InterruptedException {
		highlight(element, highlight_interval);
	}

	private void highlight(WebElement element, long highlight_interval)
			throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		executeScript("arguments[0].style.border=''", element);
	}

	public Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver); // a.k.a. (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

}
