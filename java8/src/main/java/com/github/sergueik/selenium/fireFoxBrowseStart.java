package com.github.sergueik.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;

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
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class fireFoxBrowseStart {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Actions actions;
	private static Predicate<WebElement> hasClass;
	private static Predicate<WebElement> hasAttr;
	private static Predicate<WebElement> hasText;
	private static int flexibleWait = 5;
	private static int implicitWait = 1;
	private static long pollingInterval = 500;
	private static String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";
	private static String osName;

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	public static void main(String[] args) throws Exception {
		getOsName();
		System.setProperty("webdriver.gecko.driver",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
						: "/tmp/geckodriver");
		System.setProperty("webdriver.firefox.bin",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
		resetBrowser();
		loadPage();
		test();
		afterSuiteMethod();
	}

	public static void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	public static void loadPage() {
		driver.get(baseUrl);
	}

	public static void resetBrowser() {
		driver.get("about:blank");
	}

	public static void test() {
		// Arrange
		// Act
		WebElement bar = driver.findElement(By.cssSelector(
				"section.section-examples div.examples div.box-example-square div.box-body div.br-theme-bars-square"));
		assertTrue(
				bar.findElements(By.xpath("//a[@data-rating-value]")).size() > 7);
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]")); // NOTE: relative
																															// xpath selector
		assertTrue(ratingElements.size() > 0);
		// TODO: test that result set elements are unique ?
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		//
		ratings.keySet().stream().forEach(o -> {
			System.err.println("Mouse over rating: " + o);
			WebElement r = ratings.get(o);
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		});
		// Assert
	}

	private static void highlight(WebElement element) {
		highlight(element, 100);
	}

	private static void highlight(WebElement element, long highlight_interval) {
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
}