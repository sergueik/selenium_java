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
import static org.hamcrest.CoreMatchers.hasItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KeyMasterTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseURL = "about:blank";
	private final String getCommand = "return document.swdpr_command === undefined ? '' : document.swdpr_command;";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		// NOTE: CTRL-right click via actions does not work well with Firefox
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
	public void afterSuiteMethod() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.err.println("Ignored exception: " + e.toString());
			// WARNING: Process refused to die after 10 seconds, 
			// and couldn't taskkill it
			// java.lang.NullPointerException: Unable to find executable for: taskkill
			// when run from Powershell
		}
	}

	@BeforeMethod
	public void loadBaseURL() {
		driver.get(baseURL);
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = false, priority = 1)
	public void testStatic() {
		driver.get(getPageContent("keymaster.html"));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		highlight(element);
		actions.moveToElement(element).sendKeys("o").build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Assert
		confirmAlertIsPresent();
	}

	@Test(enabled = false, priority = 2)
	public void testBlankPageKeyMasterInjection() {
		driver.get(getPageContent("blankpage.html"));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.<String> empty());
		highlight(element);
		actions.moveToElement(element).sendKeys("o").build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Assert
		confirmAlertIsPresent();
	}

	@Test(enabled = false, priority = 3)
	public void testBlankPageElementSearch() {
		driver.get(getPageContent("blankpage.html"));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	@Test(enabled = false, priority = 4)
	public void testWebPageElementSearch() {
		driver.get("https://www.ryanair.com/ie/en/");
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"#home div.specialofferswidget h3 > span:nth-child(1)"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		// Assert
		// header.sendKeys("o");
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		completeVisualSearch("element name");

		// Assert
		String payload = (String) executeScript(getCommand);
		assertFalse(payload.isEmpty());
		String result = readVisualSearchResult(payload);
	}

	@Test(enabled = false, priority = 5)
	public void testWebPageVanillaKeyMasterInjection() {
		driver.get("https://www.ryanair.com/ie/en/");
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"#home div.specialofferswidget h3 > span:nth-child(1)"))));
		injectKeyMaster(Optional.<String> empty());
		highlight(element);
		// Assert
		actions.moveToElement(element).sendKeys("o").build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Assert
		confirmAlertIsPresent();
	}

	// This test is failing
	@Test(enabled = false, priority = 6)
	public void testWebPageKeyMasterElementSearchInjection() {
		driver.get("https://www.ryanair.com/ie/en/");
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"#home div.specialofferswidget h3 > span:nth-child(1)"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		// Act
		actions.moveToElement(element).sendKeys("o").build().perform();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		// Assert
		confirmAlertIsPresent();
		// Act
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		String payload = (String) executeScript(getCommand);
		assertFalse(payload.isEmpty());
		String result = readVisualSearchResult(payload);
	}

	@Test(enabled = false, priority = 7)
	public void testBlankPageKeyMasterElementSearchInjection() {
		driver.get(getPageContent("blankpage.html"));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		// Act
		actions.moveToElement(element).sendKeys("o").build().perform();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// Assert
		confirmAlertIsPresent();
		// Act
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		// Assert
		String payload = (String) executeScript(getCommand);
		assertFalse(payload.isEmpty());
		String result = readVisualSearchResult(payload);
	}

	@Test(enabled = true, priority = 8)
	public void testElementSearchResult() {
		driver.get(getPageContent("blankpage.html"));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));
		highlight(element);
		String payload = (String) executeScript(getCommand);
		assertTrue(payload.isEmpty());
		// Act
		actions.keyDown(Keys.CONTROL).build().perform();
		actions.moveToElement(element).contextClick().build().perform();
		actions.keyUp(Keys.CONTROL).build().perform();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		// Assert, Act
		completeVisualSearch("element name");
		// Assert
		payload = (String) executeScript(getCommand);
		assertFalse(payload.isEmpty());
		HashMap<String, String> data = new HashMap<String, String>();
		String result = readVisualSearchResult(payload, Optional.of(data));
		for (String key : data.keySet()) {
			System.err.println(key + ": " + data.get(key));
		}
		injectKeyMaster(Optional.of(getScriptContent("ElementSearch.js")));

		closeVisualSearch();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}

	}

	String readVisualSearchResult(String payload) {
		return readVisualSearchResult(payload,
				Optional.<HashMap<String, String>> empty());
	}

	private String readVisualSearchResult(final String payload,
			Optional<HashMap<String, String>> parameters) {
		System.err.println("Processing payload: " + payload);
		Boolean collectResults = parameters.isPresent();
		HashMap<String, String> collector = (collectResults) ? parameters.get()
				: new HashMap<String, String>();
		try {
			JSONObject payloadObj = new JSONObject(payload);
			Iterator<String> payloadKeyIterator = payloadObj.keys();
			while (payloadKeyIterator.hasNext()) {

				String itemKey = payloadKeyIterator.next();
				String itemVal = payloadObj.getString(itemKey);
				collector.put(itemKey, itemVal);
				/*
				 * JSONArray dataArray = resultObj.getJSONArray(key); for (int cnt = 0;
				 * cnt < dataArray.length(); cnt++) { System.err.println(key + " " +
				 * dataArray.get(cnt)); }
				 */
			}
		} catch (JSONException e) {

		}
		assertTrue(collector.containsKey("ElementId"));
		// NOTE: elementCodeName will not be set if
		// user clicked the SWD Table Close Button
		// ElementId is always set
		return collector.get("ElementCodeName");
	}

	private void completeVisualSearch(String elementCodeName) {
		WebElement swdControl = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("SWDTable"))));
		assertThat(swdControl, notNullValue());

		// System.err.println("Swd Control:" +
		// swdControl.getAttribute("innerHTML"));
		WebElement swdCodeID = wait.until(ExpectedConditions
				.visibilityOf(swdControl.findElement(By.id("SwdPR_PopUp_CodeIDText"))));
		assertThat(swdCodeID, notNullValue());
		swdCodeID.sendKeys(elementCodeName);
		WebElement swdAddElementButton = wait
				.until(ExpectedConditions.visibilityOf(swdControl.findElement(
						By.xpath("//input[@type='button'][@value='Add element']"))));
		assertThat(swdAddElementButton, notNullValue());
		highlight(swdAddElementButton);
		swdAddElementButton.click();
	}

	private void closeVisualSearch() {
		WebElement swdControl = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("SWDTable"))));
		assertThat(swdControl, notNullValue());

		WebElement swdCloseButton = wait.until(ExpectedConditions.visibilityOf(
				swdControl.findElement(By.id("SwdPR_PopUp_CloseButton"))));
		assertThat(swdCloseButton, notNullValue());
		highlight(swdCloseButton);
		highlight(swdCloseButton);
		swdCloseButton.click();
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
				getScriptContent("keymaster.js"),
				"key('o, enter, left', function(event, handler){ window.alert('o, enter or left pressed on target = ' + event.target.toString() +  ' srcElement = ' + event.srcElement.toString() + ' !');});"));
		if (script.isPresent()) {
			scripts.add(script.get());
		}
		for (String s : scripts) {
			if (s != null)
				executeScript(s);
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
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight_interval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			System.err.println("Ignored: " + e.toString());
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
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: parameter
	private void confirmAlertIsPresent() {
		try {
			alert = driver.switchTo().alert();
			String alert_text = alert.getText();
			System.err.println("Accepted alert: " + alert_text);
			// Accepted alert: o, enter or left pressed on
			// target = [object HTMLBodyElement]
			// srcElement = [object HTMLBodyElement] !

			// confirm alert
			alert.accept();
		} catch (NoAlertPresentException e) {
			throw new RuntimeException("Alert was not present.");

		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}
}
