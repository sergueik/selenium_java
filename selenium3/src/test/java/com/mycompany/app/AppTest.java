package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;

import java.lang.RuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
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
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonObject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

	private static WebDriver driver;
	private WebElement element = null;
	private String selector = null;
	private static Actions actions;
	private static String baseURL = "http://the-internet.herokuapp.com/drag_and_drop";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final String browser = "chrome";

	@BeforeClass
	public static void setUp() throws Exception {
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
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
			// alternatively one can add Geckodriver to system path
			System.setProperty("webdriver.gecko.driver",
					"c:/java/selenium/geckodriver.exe");
			// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// alternatively set "marionette" capability to false to use legacy
			// FirefoxDriver
			capabilities.setCapability("marionette", false);
			// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
			capabilities.setCapability("locationContextEnabled", false);
			capabilities.setCapability("acceptSslCerts", true);
			FirefoxProfile profile = new FirefoxProfile();
			JsonObject options = new JsonObject();
			JsonObject log = new JsonObject();

			log.addProperty("level", "trace");
			options.add("log", log);
			// geckodriver 0.11.0 introduces a new firefoxOptions dictionary that is
			// much like chromeOptions
			// https://github.com/mozilla/geckodriver/issues/228
			// Note: older versions of Selenium driver may crash with
			// Firefox option was set, but is not a FirefoxOption:
			// {"log":{"level":"trace"}}
			// capabilities.setCapability("moz:firefoxOptions", options);
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setEnableNativeEvents(false);

			System.out.println(System.getProperty("user.dir"));
			/* 
			// TODO: conditional
				try {
					profile.addExtension(new File(System.getProperty("user.dir"),
							"/resources/JSErrorCollector.xpi"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			*/
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			try {
				driver = new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				// Selenium 3.3.x
				// java.lang.ClassCastException:
				// org.openqa.selenium.remote.service.DriverCommandExecutor cannot be
				// cast to org.openqa.selenium.firefox.FirefoxDriver$LazyCommandExecutor
				e.printStackTrace();
				throw new RuntimeException("Cannot initialize Firefox driver");
			}
		}
		actions = new Actions(driver);
	}

	@Before
	public void beforeTest() throws Exception {
		driver.get(baseURL);
	}

	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(verificationErrors.toString());
		}
	}

	@Test
	public void verifyText() throws Exception {
		// new
		// Actions(driver).dragAndDrop(By.id("column-a"),By.id("column-b")).perform();
		actions.clickAndHold(driver.findElement(By.id("column-a")))
				.moveToElement(driver.findElement(By.id("column-b"))).release().build()
				.perform();
	}

}