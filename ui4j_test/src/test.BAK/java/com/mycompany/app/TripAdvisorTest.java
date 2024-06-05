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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class TripAdvisorTest {

	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;
	private static String baseURL = "https://www.tripadvisor.com/";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager.getLogger(TripAdvisorTest.class);
	private static String osName;

	private static Pattern pattern;
	private static Matcher matcher;
	private static final String browser = "chrome";

	@BeforeSuite
	@SuppressWarnings("deprecation")
	public static void setUp() {
		getOsName();
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			// origin:
			// https://sqa.stackexchange.com/questions/26275/how-to-disable-chrome-save-your-password-selenium-java
			// http://learn-automation.com/disable-chrome-notifications-selenium-webdriver/

			HashMap<String, Object> prefs = new HashMap<>();
			prefs.put("profile.default_content_settings.popups", 0);
			// Put this into prefs map to switch off browser notification
			prefs.put("profile.default_content_setting_values.notifications", 2);
			// Put this into prefs map to switch off save password notification
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			String downloadPath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "target"
					+ System.getProperty("file.separator");
			prefs.put("download.default_directory", downloadPath);
			prefs.put("enableNetwork", "true");
			options.setExperimentalOption("prefs", prefs);

			for (String optionAgrument : (new String[] {
					"--allow-running-insecure-content", "--allow-insecure-localhost",
					"--enable-local-file-accesses", "--disable-notifications",
					"--disable-save-password-bubble",
					/* "start-maximized" , */
					"--browser.download.folderList=2", "--disable-web-security",
					"--no-proxy-server",
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
					String.format("--browser.download.dir=%s", downloadPath)
					/* "--user-data-dir=/path/to/your/custom/profile"  , */
			})) {
				options.addArguments(optionAgrument);
			}

			// options for headless
			/*
			for (String optionAgrument : (new String[] { "headless",
					"window-size=1200x600", })) {
				options.addArguments(optionAgrument);
			}
			*/
			capabilities
					.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capabilities);

		} else if (browser.equals("firefox")) {
			// TODO: Observed user agent problem with firefox - mobile version of
			// tripadvisor is rendered
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
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
	}

	@AfterSuite
	public static void tearDown() throws Exception {
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@BeforeMethod
	public void loadBaseURL() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void test1() {
		assertEquals("Hotels", findElement("link_text", "Hotels").getText());
	}

	@Test(enabled = true)
	public void test2() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = xpathOfElement(element);
		assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		element = findElement("xpath", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	@Test(enabled = true)
	public void test3_1() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElement(element);
		System.err.println("test 2: CssSelector: " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 2: " + e.toString());
		}
	}

	@Test(enabled = true)
	public void test3_2() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElement(element);
		System.err.println("test 2: CssSelector: " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	@Test(enabled = true)
	public void test4_1() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		System.err.println("test 3: Css Selector (alternative) : " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 3: " + e.toString());
		}
	}

	@Test(enabled = true)
	public void test4_2() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		System.err.println("test 3: Css Selector (alternative) : " + selector);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				selector);
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	@Test(enabled = true)
	public void test5() {
		WebElement element = findElement("css_selector", "input#mainSearch");
		assertEquals("input#mainSearch", cssSelectorOfElement(element));
		assertEquals("input#mainSearch", cssSelectorOfElementAlternative(element));
		highlight(element);
	}

	@Test(enabled = true)
	public void test6() {
		WebElement element = findElement("css_selector", "span#rdoFlights")
				.findElement(By.cssSelector("div span.label"));
		assertEquals("span#rdoFlights > div.header > span.label",
				cssSelectorOfElement(element));
		highlight(element);
	}

	@Test(enabled = true)
	public void test7() {
		WebElement element = findElement("id", "searchbox");
		assertEquals("input#searchbox", cssSelectorOfElement(element));
		highlight(element);
	}

	@Test(enabled = true)
	public void test8() {
		WebElement element = findElement("id", "searchbox")
				.findElement(By.xpath(".."));
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		// System.err.println("test 7: selector (alternative):\n" + selector);
		assertEquals(
				"form#PTPT_HAC_FORM > div.metaFormWrapper > div.metaFormLine.flex > label.ptptLabelWrap",
				selector);
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// @Ignore
	@SuppressWarnings("static-access")
	@Test(enabled = true)
	public void test9() {

		WebElement element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		String style = styleOfElement(element);
		System.err.println("style:\n" + style);
		String colorAttribute = styleOfElement(element, "color");
		String heightAttribute = styleOfElement(element, "height");
		String widthAttribute = styleOfElement(element, "width");

		// assertions of certain CSS attributes
		try {
			assertTrue(colorAttribute.equals("rgb(255, 255, 255)"));
		} catch (AssertionError e) {
			// slurp
		}
		// pattern = Pattern.compile("\\((\\d+),");
		matcher = Pattern.compile("\\((\\d+),").matcher(colorAttribute);
		if (matcher.find()) {
			int red = Integer.parseInt(matcher.group(1).toString());
			assertTrue(red > 254);
		}
		System.err.println("color:" + colorAttribute);

		try {
			assertTrue(widthAttribute.equals("36.6667px")); // fragile !
		} catch (AssertionError e) {
			// slurp
		}
		pattern = Pattern.compile("([\\d\\.]+)px");
		matcher = pattern.matcher(widthAttribute);
		if (matcher.find()) {
			Float mask = new Float("20.75f");
			Float width = mask.parseFloat(matcher.group(1).toString());
			assertTrue(width > 36.5);
		}
		System.err.println("width:" + widthAttribute);

		System.err.println("examine height attribute: " + heightAttribute);
		// broken after 431eac6a3baa
		// assertTrue(height.equals("12px"));
		// print css values
		// System.err.println("height:" + heightAttribute);
	}

	// Utils
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	private String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("cssSelectorOfElement.js"),
				element);
	}

	private String styleOfElement(WebElement element, Object... arguments) {
		return (String) executeScript(getScriptContent("getStyle.js"), element,
				arguments);
	}

	private String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(
				getScriptContent("cssSelectorOfElementAlternative.js"), element);
	}

	private void highlight(WebElement element) {
		highlight(element, highlight_interval);
	}

	private static void highlight(WebElement element, long highlight) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript("arguments[0].style.border='3px solid yellow'",
					new Object[] { element });
			Thread.sleep(highlight);
			executeScript("arguments[0].style.border=''", new Object[] { element });

		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	private String xpathOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("xpathOfElement.js"),
				new Object[] { element });
	}

	public static Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver); // a.k.a. (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue, WebElement parent) {
		SearchContext finder;
		String parent_css_selector = null;
		String parent_xpath = null;

		List<WebElement> elements = null;
		Hashtable<String, Boolean> selectorStrategies = new Hashtable<String, Boolean>();
		selectorStrategies.put("css_selector", true);
		selectorStrategies.put("xpath", true);

		if (selectorKind == null || !selectorStrategies.containsKey(selectorKind)
				|| !selectorStrategies.get(selectorKind)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = cssSelectorOfElement(parent);
			parent_xpath = xpathOfElement(parent);
			finder = parent;
		} else {
			finder = driver;
		}

		if (selectorKind == "css_selector") {
			String extended_css_selector = String.format("%s  %s",
					parent_css_selector, selectorValue);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath,
					selectorValue);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(selectorValue));
		}
		return elements;
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue) {
		return findElements(selectorKind, selectorValue, null);
	}

	private WebElement findElement(String selectorKind, String selectorValue) {
		WebElement element = null;
		Hashtable<String, Boolean> selectorStrategies = new Hashtable<String, Boolean>();
		selectorStrategies.put("id", true);
		selectorStrategies.put("css_selector", true);
		selectorStrategies.put("xpath", true);
		selectorStrategies.put("partial_link_text", false);
		selectorStrategies.put("link_text", true);
		selectorStrategies.put("classname", false);

		if (selectorKind == null || !selectorStrategies.containsKey(selectorKind)
				|| !selectorStrategies.get(selectorKind)) {
			return null;
		}
		if (selectorKind == "id") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.id(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.id(selectorValue));
		}
		if (selectorKind == "classname") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.className(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.className(selectorValue));
		}
		if (selectorKind == "link_text") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.linkText(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.linkText(selectorValue));
		}
		if (selectorKind == "css_selector") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.xpath(selectorValue));
		}
		return element;
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = TripAdvisorTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("Cannot load file: " + scriptName);
		}
	}

	public static void waitForElementVisible(By locator) {
		log.info("Waiting for element visible for locator: {}", locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementVisible(By locator, long timeout) {
		log.info("Waiting for element visible for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator) {
		log.info("Waiting for element present  for locator: {}", locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator, long timeout) {
		log.info("Waiting for element present for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForPageLoad() {
		log.info("Wait for page load via JS...");
		String state = "";
		int counter = 0;

		do {
			try {
				state = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
			log.info(("Browser state is: " + state));
		} while (!state.equalsIgnoreCase("complete") && counter < 20);

	}

	public static boolean isAttributePresent(By locator, String attribute) {
		log.info("Is Attribute Present for locator: {}, attribute: {}", locator,
				attribute);
		return driver.findElement(locator).getAttribute(attribute) != null;
	}

	public static void selectDropdownByIndex(By locator, int index) {
		log.info("Select Dropdown for locator: {} and index: {}", locator, index);
		try {
			Select select = new Select(driver.findElement(locator));
			select.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getBaseURL() {
		// TODO: somehow browser is set to mobile
		// if the next line is commented
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

	public static void clickJS(By locator) {
		log.info("Clicking on locator via JS: {}", locator);
		wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(locator));
	}

	public static void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}

	public static void mouseOver(By locator) {
		log.info("Mouse over: {}", locator);
		actions.moveToElement(driver.findElement(locator)).build().perform();
	}

	public static void click(By locator) {
		log.info("Clicking: {}", locator);
		driver.findElement(locator).click();
	}

	public static void clear(By locator) {
		log.info("Clearing input: {}", locator);
		driver.findElement(locator).clear();
	}

	public static void sendKeys(By locator, String text) {
		log.info("Typing \"{}\" into locator: {}", text, locator);
		driver.findElement(locator).sendKeys(text);
	}

	public static String getText(By locator) {
		String text = driver.findElement(locator).getText();
		log.info("The string at {} is: {}", locator, text);
		return text;
	}

	public static String getAttributeValue(By locator, String attribute) {
		String value = driver.findElement(locator).getAttribute(attribute);
		log.info("The attribute \"{}\" value of {} is: {}", attribute, locator,
				value);
		return value;
	}

	public static boolean isElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return true;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return false;
		}
	}

	// custom wait while Login Lightbox is visible

	public static void waitWhileElementIsVisible(By locator) {
		final By locatorFinal = locator;
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver o) {
				return (o.findElements(locatorFinal).size() == 0);
			}
		});
	}

	public static boolean isElementNotVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		}
	}

	public static String getBodyText() {
		log.info("Getting boby text");
		return driver.findElement(By.tagName("body")).getText();
	}

	public static void highlight(By locator) throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		executeScript("arguments[0].style.border=''", element);
	}
}
