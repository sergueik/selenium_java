package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import static java.lang.Boolean.*;
import static java.lang.Float.*;
import java.lang.Float;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.CoreMatchers.matchesPattern;
// https://stackoverflow.com/questions/8505153/assert-regex-matches-in-junit
// https://piotrga.wordpress.com/2009/03/27/hamcrest-regex-matcher/

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class PlunkerTest extends BaseTest {

	private static String baseURL = "https://embed.plnkr.co/";
	private static String projectId = "ggL5oAvGgucDA5HCYk7K";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(PlunkerTest.class);
	private static String osName;

	private static Pattern pattern;
	private static Matcher matcher;
	private static Set<String> windowHandles;

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	@Override
	@AfterClass
	public void afterClass() throws Exception {
		try {
			driver.close();
		} catch (NoSuchWindowException e) {

		}
		driver.quit();
		driver = null;
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@BeforeMethod
	public void loadBaseURL() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void testEditorPreview() {
		driver.get(String.format("https://plnkr.co/edit/%s/?p=info", projectId));

		List<WebElement> iframes = driver.findElements(By.cssSelector("iframe"));
		HashMap<String, Object> iframesMap = new HashMap<>();
		for (WebElement iframe : iframes) {
			String key = String.format("id: \'%s\", name: \"%s\"",
					iframe.getAttribute("id"), iframe.getAttribute("name"));
			System.err.println(String.format("Found iframe %s", key));
			iframesMap.put(key, iframe);
		}

		WebElement runButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("body > nav button i.icon-play")));
		assertThat(runButton, notNullValue());
		highlight(runButton);
		runButton.click();
		iframes = driver.findElements(By.cssSelector("iframe"));
		for (WebElement iframe : iframes) {
			String key = String.format("id: \'%s\", name: \"%s\"",
					iframe.getAttribute("id"), iframe.getAttribute("name"));
			if (!iframesMap.containsKey(key)) {
				System.err.println(String.format("Found new iframe %s", key));
			}
		}
		WebElement previewIframe = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("iframe[name='plunkerPreviewTarget']")));
		assertThat(previewIframe, notNullValue());
		// System.err.println(driver.getCurrentUrl());
		assertTrue(driver.getCurrentUrl().matches("^.*\\?p=preview$"));
		// driver.findElement(By.linkText("urlLink")).sendKeys(Keys.chord(Keys.CONTROL,Keys.RETURN));
	}

	@Test(enabled = true)
	public void testEmbed() {
		driver.get("https://embed.plnkr.co/" + projectId);
		WebElement closeButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
						"body div.plunker-ide-workspace button.plunker-ops-close")));
		highlight(closeButton);
		closeButton.click();
		// button.plunker-sidebar-selector.plunker-selector-tree is better handled
		// via Protractor
	}

	@Test(enabled = true)
	public void testFullScreeen() {
		projectId = "WFJYrM";
		driver.get(String.format("https://plnkr.co/edit/%s/?p=info", projectId));
		WebElement runButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("body > nav button i.icon-play")));
		assertThat(runButton, notNullValue());
		highlight(runButton);
		runButton.click();
		WebElement previewIframe = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("iframe[name='plunkerPreviewTarget']")));
		assertThat(previewIframe, notNullValue());
		WebDriver iframe = driver.switchTo().frame(previewIframe);
		sleep(500);
		// System.err.println(iframe.getPageSource());
		// the fullscreen button is not in the preview frame
		driver.switchTo().defaultContent();
		WebElement fullScreenButton = null;
		try {
			fullScreenButton = wait.until(new ExpectedCondition<WebElement>() {

				@Override
				public WebElement apply(WebDriver d) {
					Optional<WebElement> e = d
							.findElements(By.cssSelector("button#expand-preview")).stream()
							.filter(o -> {
								String t = o.getAttribute("title");
								return (Boolean) (t
										.contains("Launch the preview in a separate window"));
							}).findFirst();
					return (e.isPresent()) ? e.get() : (WebElement) null;
				}
			});
		} catch (

		Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		assertThat(fullScreenButton, notNullValue());
		// confirm it opens in a new tab.
		// fullScreenButton.click();
		// alternatively, enforce open in a new tab:
		String openinLinkNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
		fullScreenButton.sendKeys(openinLinkNewTab);
		sleep(500);
		// confirm it opens in a new tab.
		String currentHandle = null;
		try {
			currentHandle = driver.getWindowHandle();
			// System.err.println("Thread: Current Window handle: " + currentHandle);
		} catch (NoSuchWindowException e) {

		}
		windowHandles = driver.getWindowHandles();
		assertThat(windowHandles.size(), equalTo(2));

		Iterator<String> windowHandleIterator = windowHandles.iterator();
		while (windowHandleIterator.hasNext()) {
			String handle = windowHandleIterator.next();
			if (!handle.equals(currentHandle)) {
				driver.switchTo().window(handle);
				assertThat(getBaseURL(), equalTo("https://run.plnkr.co"));
				// System.err.println(getBaseURL());
				driver.switchTo().defaultContent();
			}
		}
	}

	// Failed tests: afterMethod(com.mycompany.app.PlunkerTest): no such window:
	// target window already closed(..)

	@Test(enabled = false)
	public void testFullScreeenInNewWindow() {
		projectId = "WFJYrM";
		driver.get(String.format("https://plnkr.co/edit/%s/?p=info", projectId));
		WebElement runButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("body > nav button i.icon-play")));
		assertThat(runButton, notNullValue());
		highlight(runButton);
		runButton.click();
		WebElement previewIframe = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("iframe[name='plunkerPreviewTarget']")));
		assertThat(previewIframe, notNullValue());
		WebDriver iframe = driver.switchTo().frame(previewIframe);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		// System.err.println(iframe.getPageSource());
		// the fullscreen button is not in the preview frame
		driver.switchTo().defaultContent();
		WebElement fullScreenButton = null;
		try {
			fullScreenButton = wait.until(new ExpectedCondition<WebElement>() {

				@Override
				public WebElement apply(WebDriver d) {
					Optional<WebElement> e = d
							.findElements(By.cssSelector("button#expand-preview")).stream()
							.filter(o -> {
								String t = o.getAttribute("title");
								return (Boolean) (t
										.contains("Launch the preview in a separate window"));
							}).findFirst();
					return (e.isPresent()) ? e.get() : (WebElement) null;
				}
			});
		} catch (

		Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		assertThat(fullScreenButton, notNullValue());
		String currentHandle = null;
		try {
			currentHandle = driver.getWindowHandle();
			// System.err.println("Thread: Current Window handle: " + currentHandle);
		} catch (NoSuchWindowException e) {

		}
		// open fullscreen view in a new browser window.
		String openinLinkNewBrowserWindow = Keys.chord(Keys.SHIFT, Keys.RETURN);
		fullScreenButton.sendKeys(openinLinkNewBrowserWindow);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		// confirm it opens in a new browser window.
		windowHandles = driver.getWindowHandles();
		assertThat(windowHandles.size(), equalTo(2));

		Iterator<String> windowHandleIterator = windowHandles.iterator();
		while (windowHandleIterator.hasNext()) {
			String handle = windowHandleIterator.next();
			if (!handle.equals(currentHandle)) {
				driver.switchTo().window(handle);
				assertThat(getBaseURL(), equalTo("https://run.plnkr.co"));
				// System.err.println(getBaseURL());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				driver.close();
				// System.err.println("After close");
				try {
					driver.switchTo().defaultContent();
					System.err.println("After defaultcontext");
				} catch (NoSuchWindowException e) {
				}
			}
		}
	}

	public String getBaseURL() {
		System.err.println("Get base URL: " + driver.getCurrentUrl());
		log.info("Get base URL: {}", driver.getCurrentUrl());
		String currentURL = driver.getCurrentUrl();
		String protocol = null;
		String domain = null;

		try {
			URL url = new URL(currentURL);
			protocol = url.getProtocol();
			domain = url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// System.err.println("Returning: " + protocol + "://" + domain);
		return protocol + "://" + domain;
	}

	public void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}

	public void highlight(By locator, long highlight_interval) {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		try {
			Thread.sleep(highlight_interval);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
		executeScript("arguments[0].style.border=''", element);
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	public void highlight(WebElement element, long highlight_interval) {
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

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
