package com.mycompany.app;

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
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

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

public class JqueryBarRatingTest {

	private WebDriver driver;
	private WebDriverWait wait;
	static Actions actions;
	private Predicate<WebElement> hasClass;
	private Predicate<WebElement> hasAttr;
	private Predicate<WebElement> hasText;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";
	private static final String browser = "firefox";
	private static String osName;

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		getOsName();
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();

			Map<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilepath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "target"
					+ System.getProperty("file.separator");
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("enableNetwork", "true");
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("allow-running-insecure-content");
			options.addArguments("allow-insecure-localhost");
			options.addArguments("enable-local-file-accesses");
			options.addArguments("disable-notifications");
			// options.addArguments("start-maximized");
			options.addArguments("browser.download.folderList=2");
			options.addArguments(
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
			options.addArguments("browser.download.dir=" + downloadFilepath);
			// options.addArguments("user-data-dir=/path/to/your/custom/profile");
			capabilities
					.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("firefox")) {

			System.setProperty("webdriver.gecko.driver",
					osName.toLowerCase().startsWith("windows")
							? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
							: "/tmp/geckodriver");
			System
					.setProperty("webdriver.firefox.bin",
							osName.toLowerCase().startsWith("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");
			// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// use legacy FirefoxDriver
			capabilities.setCapability("marionette", false);
			// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
			capabilities.setCapability("locationContextEnabled", false);
			capabilities.setCapability("acceptSslCerts", true);
			capabilities.setCapability("elementScrollBehavior", 1);
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setEnableNativeEvents(false);

			System.out.println(System.getProperty("user.dir"));
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			try {
				driver = new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				e.printStackTrace();
				throw new RuntimeException("Cannot initialize Firefox driver");
			}
		}
		actions = new Actions(driver);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseUrl);
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void test1() {
		// Arrange
		WebElement bar = wait.until((WebDriver d) -> {
			WebElement element = null;
			try {
				element = d.findElement(By.cssSelector(
						"section.section-examples div.examples div.box-example-square div.box-body div.br-theme-bars-square"));
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		});
		// Act
		// NOTE: relative xpath selector
		assertTrue(
				bar.findElements(By.xpath("//a[@data-rating-value]")).size() > 7);
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
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
			sleep(1000);
		});
		// Assert
	}

	@Test(enabled = true)
	public void test2() {
		// Arrange

		wait.until(ExpectedConditions.or(
				// NOTE: Boolean
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed")),
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed"))));

		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.examples div.box-example-reversed")));

		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		// Act
		ratings.keySet().stream().forEach(o -> {
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			// Assert
			WebElement comment = bar.findElement(By.xpath(
					".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]"));
			assertThat(comment.getText(), equalTo(o));
			System.err.println("Mouse over rating: " + o);
			sleep(1000);
		});
	}

	@Test(enabled = true)
	public void test3() {
		// Arrange
		// custom Expected Condition static Helper class
		WebElement bar = wait.until(Helper.oneOfElementsLocatedVisible(
				By.xpath(
						"//section[contains(@class, 'section-examples')]//div[contains(@class, 'box-example-movie')]//div[contains(@class, 'br-widget')]"),
				By.cssSelector(
						"section.section-examples div.box-example-movie div.br-widget")));
		// Act
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		ratings.keySet().stream().forEach(o -> {
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			r.click();
			// Assert
			WebElement comment = bar
					.findElement(By.xpath(".//*[contains(@class, 'br-current-rating')]"));
			assertThat(comment.getText(), equalTo(o));
			System.err.println("Mouse over rating: " + o);
			// NOTE: there is a hidden select sibling element. The selected option
			// does not appear to be updated
			sleep(1000);
			final String script = "var result = $(\"section.section-examples div.box-example-movie div.br-theme-bars-movie select#example-movie option[selected='selected']\");return result.val();";
			if (driver instanceof JavascriptExecutor) {
				Object result = ((JavascriptExecutor) driver).executeScript(script);
				System.err.println("Select option value: " + result.toString());
			}
		});
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

	public static class Helper {
		// for complex expected Condition, based on
		// http://stackoverflow.com/questions/14840884/wait-untilexpectedconditions-visibilityof-element1-or-element2

		public static ExpectedCondition<WebElement> oneOfElementsLocatedVisible(
				By... bys) {
			return new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					for (By by : Arrays.asList(bys)) {
						WebElement element;
						try {
							System.err.println("Locator : " + by.toString());
							element = driver.findElement(by);
						} catch (Exception e) {
							continue;
						}
						if (element.isDisplayed())
							return element;
					}
					return null;
				}
			};
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
