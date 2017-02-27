package com.mycompany.app;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;

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
import static org.junit.Assert.*;

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
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Ignore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class AppTest {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private static int flexibleWait = 5;
	private static int implicitWait = 1;
	private static long pollingInterval = 500;
	private static String baseURL = "about:blank";
	private static final String getCommand = "return document.swdpr_command === undefined ? '' : document.swdpr_command;";
	private static HashMap<String, String> data = new HashMap<String, String>();
	private static String osName;

	@BeforeClass
	public static void beforeSuiteMethod() throws Exception {

		getOsName();
		if (osName.toLowerCase().startsWith("windows")) {
			System.setProperty("webdriver.chrome.driver",
					"c:/java/selenium/chromedriver.exe");
			driver = new ChromeDriver();
			/*
			// IE 10 works, IE 11 does not
			File file = new File("c:/java/Selenium/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			
			capabilities.setCapability(
					InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			// https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/6511
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setBrowserName(
					DesiredCapabilities.internetExplorer().getBrowserName());
			
			driver = new InternetExplorerDriver(capabilities);
			*/
		} else if (osName.startsWith("Mac")) {
			driver = new SafariDriver();
			/*
			INFO: Launching Safari
			org.openqa.selenium.safari.SafariDriverCommandExecutor start
			INFO: Waiting for SafariDriver to connect
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Shutting down
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Stopping Safari
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Stopping server
			org.openqa.selenium.safari.SafariDriverServer stop
			INFO: Stopping server
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Shutdown complete
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Shutting down
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Stopping server
			org.openqa.selenium.safari.SafariDriverCommandExecutor stop
			INFO: Shutdown complete
			Ignored exception: java.lang.NullPointerException
			*/
		} else {
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@AfterClass
	public static void afterSuiteMethod() {
		try {
			driver.close();
			// driver.quit();
		} catch (Exception e) {
			System.err.println("Ignored exception: " + e.toString());
		}
	}

	@Before

	public void loadBaseURL() {
		driver.get(baseURL);
	}

	@After
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test
	public void testStatic() {
		// driver.get(getPageContent("ElementSearch.html"));
    // Unsupported URL protocol: file:/Users/sergueik/dev/selenium_java/swd_recorder/target/test-classes/ElementSearch.html
    driver.get("file:///Users/sergueik/dev/selenium_java/swd_recorder/target/test-classes/ElementSearch.html");
    // Unsupported URL protocol: file:///Users/sergueik/dev/selenium_java/swd_recorder/target/test-classes/ElementSearch.html
		WebElement element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
		highlight(element);
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
		HashMap<String, String> elementData = new HashMap<String, String>(); // empty
		String elementName = readVisualSearchResult(payload,
				Optional.of(elementData));
		Configuration config = new Configuration();
		BrowserConfiguration browserConfiguration = new BrowserConfiguration();
		browserConfiguration.name = "chrome";
		browserConfiguration.version = "54.0";
		browserConfiguration.driverVersion = "2.27";
		browserConfiguration.driverPath = "c:/java/selenium/chromedriver.exe";
		browserConfiguration.platform = getOsName();
		config.created = new Date();
		config.browserConfiguration = browserConfiguration;
		config.updated = new Date();
		HashMap<String, HashMap<String, String>> testData = new HashMap<String, HashMap<String, String>>();
		testData.put(elementName, elementData);
		config.elements = testData;

		YamlHelper.saveConfiguration(config);
	}

	public static String getOsName() {

		if (osName == null) {
			osName = System.getProperty("os.name");
			if (osName.startsWith("Windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	String readVisualSearchResult(String payload) {
		return readVisualSearchResult(payload,
				Optional.<HashMap<String, String>> empty());
	}

	private String readVisualSearchResult(final String payload,
			Optional<HashMap<String, String>> parameters) {
		// System.err.println("Processing payload: " + payload);
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
		ArrayList<String> scripts = new ArrayList<String>(
				Arrays.asList(getScriptContent("ElementSearch.js")));
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
			final InputStream stream = AppTest.class.getClassLoader()
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
			URI uri = AppTest.class.getClassLoader().getResource(pagename).toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static class YamlHelper {

		private static DumperOptions options = new DumperOptions();
		private static Yaml yaml = null;
		private static Date dateString;
		private static Calendar calendar;

		private static Configuration loadConfiguration(String fileName) {
			if (yaml == null) {
				options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				yaml = new Yaml(options);
			}
			Configuration config = null;
			try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
				config = yaml.loadAs(in, Configuration.class);
				// TODO: better method naming
				YamlHelper.saveConfiguration(config);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return config;
		}

		private static void saveConfiguration(Configuration config) {
			saveConfiguration(config, null);
		}

		@SuppressWarnings("deprecation")
		private static void saveConfiguration(Configuration config,
				String fileName) {
			if (yaml == null) {
				options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				yaml = new Yaml(options);
			}
			calendar = new GregorianCalendar();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT,
							Locale.US)).toPattern().replaceAll("\\byy\\b", "yyyy"));
			System.err.println("Testing date format: " + dateFormat.toPattern());

			try {
				config.updated = dateFormat
						.parse(dateFormat.format(calendar.getTime()));
			} catch (java.text.ParseException e) {
				System.err.println("Ignoring date parse exception: " + e.toString());
				config.updated = new Date();
			}
			if (fileName != null) {
				try {
					Writer out = new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF8");
					yaml.dump(config, out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println(yaml.dump(config));
			}
		}
	}
}
