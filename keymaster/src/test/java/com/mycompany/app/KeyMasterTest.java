package com.mycompany.app;

import java.io.InputStream;
import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class KeyMasterTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseURL = "about:blank";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		// NOTE: actions CTRL-right click does not work well with Firefox
		// driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"c:/java/selenium/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseURL);
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = true, priority = 1)
	public void testStatic() {
		driver.get(getPageContent("keymaster.html"));
		WebElement header = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		highlight(header);
		actions.moveToElement(header).sendKeys("o").build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Assert
		try {
			// confirm alert
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			throw new RuntimeException("Alert was not present.");
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	@Test(enabled = true, priority = 2)
	public void testDynamic() {
		driver.get(getPageContent("blankpage.html"));
		WebElement header = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.<String> empty());
		highlight(header);
		actions.moveToElement(header).sendKeys("o").build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Assert
		try {
			// confirm alert
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			throw new RuntimeException("Alert was not present.");

		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	@Test(enabled = true, priority = 3)
	public void testElementSearch() {
		driver.get(getPageContent("blankpage.html"));
		WebElement header = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(header);
		// header.sendKeys("o");
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(header).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	@Test(enabled = true, priority = 4)
	public void testElementSearch2() {
		driver.get("https://www.ryanair.com/ie/en/");
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"#home div.specialofferswidget h3 > span:nth-child(1)"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		// header.sendKeys("o");
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'", element);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private void injectKeyMaster(Optional<String> script) {

		ArrayList<String> scripts = new ArrayList<String>(Arrays.asList(
				getScriptContent("keymaster.js"), script.isPresent() ? script.get()
						: "key('o, enter, left', function(){ window.alert('o, enter or left pressed!');});"));
		for (String s : scripts) {
			executeScript(s);
		}
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = KeyMasterTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = KeyMasterTest.class.getClassLoader().getResource(pagename)
					.toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}
}
