package com.mycompany.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.RuntimeException;
import static java.lang.Boolean.*;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class AppTest {

	private static WebDriver driver;
	private static WebDriver frame;
	private static WebDriverWait wait;
	private static Actions actions;
	private static WebElement element = null;
	private static Boolean debug = false;
	private static String selector = null;
	private static long implicitWait = 10;
	private static int flexibleWait = 30;
	private static long polling = 1000;
	private static long highlight = 100;
	private static long afterTest = 1000;
	private static String baseURL = "https://ya.ru/";
	private static String finalUrl = "https://www.yandex.ru/";
	private static String loginURL = "https://passport.yandex.ru";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static Map<String, String> env = System.getenv();
	private static String username = "";
	private static String password = "";
	private static Formatter formatter;
	private static StringBuilder loggingSb;
	private static String configurationFileName = "test.configuration";
	private static String propertiesFileName = "test.properties";

	@BeforeClass
	public static void setUp() {

		if (env.containsKey("DEBUG") && env.get("DEBUG").equals("true")) {
			debug = true;
		}
		TestConfigurationParser
				.getConfiguration(String.format("%s/src/test/resources/%s",
						System.getProperty("user.dir"), configurationFileName));
		HashMap<String, String> propertiesMap = PropertiesParser
				.getProperties(String.format("%s/src/test/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));
		username = propertiesMap.get("username");
		password = propertiesMap.get("password");
		loggingSb = new StringBuilder();
		formatter = new Formatter(loggingSb, Locale.US);
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(polling, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@Before
	public void beforeTest() {
		driver.get(baseURL);
		element = driver.findElement(By.cssSelector(
				"table.layout__table tr.layout__header div.personal div.b-inline"));
		highlight(element);
		element.click();
	}

	@After
	public void afterTest() {
	}

	@AfterClass
	public static void tearDown() {
		try {
			Thread.sleep(afterTest);
		} catch (InterruptedException e) {
		}
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(verificationErrors.toString());
		}
	}

	// @Ignore
	@Test
	public void getCookieTest() throws Exception {

		doLogin();
		Set<Cookie> cookies = driver.manage().getCookies();
		System.err.println("Cookies:");
		JSONArray cookieJSONArray = new JSONArray();
		for (Cookie cookie : cookies) {
			if (debug) {
				System.err.println(formatter
						.format(
								"Name: '%s'\n" + "Value: '%s'\n" + "Domain: '%s'\n"
										+ "Path: '%s'\n" + "Expiry: '%tc'\n" + "Secure: '%b'\n"
										+ "HttpOnly: '%b'\n" + "\n",
								cookie.getName(), cookie.getValue(), cookie.getDomain(),
								cookie.getPath(), cookie.getExpiry(), cookie.isSecure(),
								cookie.isHttpOnly())
						.toString());
			}
			JSONObject cookieJSONObject = new JSONObject(cookie);
			if (debug) {
				System.err.println(cookieJSONObject.toString());
			}
			cookieJSONArray.put(cookieJSONObject);
		}
		JSONObject cookiesJSONObject = new JSONObject();
		cookiesJSONObject.put("cookies", cookieJSONArray);
		if (debug) {
			System.err.println(cookiesJSONObject.toString());
		}
		doLogout();
	}

	@Ignore
	@Test
	public void useCookieTest() throws Exception {
		String loginUrl = doLogin();
		System.err.println("Getting the cookies");
		Set<Cookie> cookies = driver.manage().getCookies();
		System.err.println("Closing the browser");
		wait = null;
		System.err.println("re-open the browser, about to use the session cookies");
		driver.close();
		driver = new FirefoxDriver();
		// re-initialize wait object
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(polling, TimeUnit.MILLISECONDS);
		System.err.println("Navigating to " + loginUrl);
		driver.get(loginUrl);
		System.err.println("Loading cookies");
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		driver.navigate().refresh();

		System.err.println("Waiting for inbox");
		try {
			wait.until(ExpectedConditions.urlContains("#inbox"));
		} catch (TimeoutException | UnreachableBrowserException e) {
			verificationErrors.append(e.toString());
		}
		doLogout();
	}

	@Ignore
	@Test
	public void useExpiredCookieLaterTest() {
		/*
		 * public Cookie(java.lang.String name, java.lang.String value,
		 * java.lang.String domain, java.lang.String path, java.util.Date expiry,
		 * boolean isSecure, boolean isHttpOnly)
		 *
		 * Creates a cookie.
		 */
	}

	private String doLogin() {

		// And I enter the username
		element = driver.findElement(By.xpath("//form/div[1]/label/span/input"));
		highlight(element);
		element.clear();
		element.sendKeys(username);
		// Assert that input gets added to the background form
		element = driver
				.findElement(By.cssSelector("form.new-auth-form input[name='login']"));
		System.err.println("Username: " + element.getAttribute("value"));
		// And I enter the password
		element = driver.findElement(By.xpath("//form/div[2]/label/span/input"));
		highlight(element);
		element.clear();
		element.sendKeys(password);
		// Assert that input gets added to the background form
		element = driver
				.findElement(By.cssSelector("form.new-auth-form input[name='passwd']"));
		System.err.println("Password: " + element.getAttribute("value"));
		// Evaluate the landing page URL
		element = driver.findElement(
				By.cssSelector("form.new-auth-form span.new-auth-submit a.nb-button"));
		String login_href = element.getAttribute("href");
		System.err.println("Login href: " + login_href);
		Pattern pattern = Pattern
				.compile(String.format("%s/auth/\\?mode=qr&retpath=(.+)$", loginURL));
		Matcher matcher = pattern.matcher(login_href);
		String retpath = null;
		if (matcher.find()) {
			try {
				retpath = java.net.URLDecoder.decode(matcher.group(1).toString(),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore
			}
		}
		System.err.println("Login retpath: " + retpath);

		// And I click the login button
		element = driver.findElement(
				By.cssSelector("form.new-auth-form span.new-auth-submit button"));
		highlight(element);
		String currentUrl = driver.getCurrentUrl();
		element.click();

		// wait until browser is away from the login page
		System.err.println("Waiting to get away from " + loginURL);
		try {
			wait.until(
					ExpectedConditions.not(ExpectedConditions.urlContains(loginURL)));
		} catch (TimeoutException tex) {
			// verify if the page warns about the invalid credentials
			try {
				element = driver.findElement(
						By.cssSelector("div.layout-inner div.js-messages div.error-msg"));
				verificationErrors.append("Getting error message " + element.getText());
				System.err.println("Getting error message " + element.getText());
			} catch (NoSuchElementException elex) {
				// ignore
			}
			verificationErrors.append(tex.toString());
		}
		System.err.println("Waiting for " + retpath);

		// wait until browser is on the landing page
		wait.until(ExpectedConditions.urlContains(retpath));

		String currentURL = driver.getCurrentUrl();
		// System.out.println("Page url: " + currentURL);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("div.mail-App-Header div.mail-User")));
		} catch (TimeoutException | UnreachableBrowserException e) {
			verificationErrors.append(e.toString());
		}
		return retpath;
	}

	private void doLogout() {
		assertTrue(driver.getCurrentUrl().matches(".*#inbox"));

		// When I am logged on user
		element = driver.findElement(
				By.cssSelector("div.mail-App-Header div.mail-User div.mail-User-Name"));
		highlight(element);
		element.click();
		// And I am about to log off
		element = driver.findElement(By.cssSelector(
				"body.mail-Page-Body div.ui-dialog div._nb-popup-content div.b-user-dropdown-content-with-exit div.b-mail-dropdown__item a.ns-action"));
		highlight(element);
		element.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		// And I confirm I am going to log off
		element = driver.findElement(
				By.xpath("//div[5]/div[2]/table/tbody/tr/td/div[3]/div/a"));
		// Evaluate logout URL
		String logout_href = element.getAttribute("href");
		System.err.println("Logout href: " + logout_href);
		highlight(element);
		/*
		 * String retpath = null; Pattern pattern = Pattern
		 * .compile("https://passport.yandex.ru/passport?\\?mode=.+&retpath=(.+)$");
		 *
		 * Matcher matcher = pattern.matcher(logout_href); if (matcher.find()) { try
		 * { retpath = java.net.URLDecoder.decode(matcher.group(1).toString(),
		 * "UTF-8"); } catch (UnsupportedEncodingException e) { // ignore } } //
		 * NOTE: do not wait for retpath System.err.println("Logout relpath: " +
		 * retpath);
		 */
		String currentUrl = driver.getCurrentUrl();
		element.click();
		try {
			wait.until(
					ExpectedConditions.not(ExpectedConditions.urlContains(currentUrl)));
		} catch (TimeoutException e) {
			verificationErrors.append(e.toString());
		}

		try {
			wait.until(ExpectedConditions.urlContains(finalUrl));
		} catch (TimeoutException e) {
			// TODO
		}
	}

	private void highlight(WebElement element) {
		highlight(element, highlight);
	}

	private void highlight(WebElement element, long highlight) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// ignore
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

	private static class TestConfigurationParser {

		private static Scanner loadTestData(final String fileName) {
			Scanner scanner = null;
			System.err
					.println(String.format("Reading configuration file: '%s'", fileName));
			try {
				scanner = new Scanner(new File(fileName));
			} catch (FileNotFoundException e) {
				// fail(String.format("File '%s' was not found.", fileName));
				System.err.println(
						String.format("Configuration file was not found: '%s'", fileName));
				e.printStackTrace();
			}
			return scanner;
		}

		public static List<String[]> getConfiguration(final String fileName) {
			ArrayList<String[]> listOfData = new ArrayList<>();
			Scanner testData = loadTestData(fileName);
			while (testData.hasNext()) {
				String line = testData.next();
				String[] tokens = line.split("\\|");
				listOfData.add(tokens);
			}
			testData.close();
			return listOfData;
		}
	}

	private static class PropertiesParser {
		public static HashMap<String, String> getProperties(final String fileName) {
			Properties p = new Properties();
			HashMap<String, String> propertiesMap = new HashMap<String, String>();
			System.err
					.println(String.format("Reading properties file: '%s'", fileName));
			try {
				p.load(new FileInputStream(fileName));
				Enumeration e = p.propertyNames();

				for (; e.hasMoreElements();) {
					String key = e.nextElement().toString();
					String val = p.get(key).toString();
					System.out.println(String.format("Reading: '%s' = '%s'", key, val));
					propertiesMap.put(key, val);
				}

			} catch (FileNotFoundException e) {
				System.err.println(
						String.format("Properties file was not found: '%s'", fileName));
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println(
						String.format("Properties file is not readable: '%s'", fileName));
				e.printStackTrace();
			}
			return (propertiesMap);

		}
	}
}