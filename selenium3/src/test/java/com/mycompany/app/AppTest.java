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
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.JsonObject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

	private static WebDriver driver;
	private WebElement element = null;
	private String selector = null;
	private static String baseURL = "http://www.tripadvisor.com/";

	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setUp() throws Exception {
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
		capabilities.setCapability("moz:firefoxOptions", options);
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(true);
		profile.setEnableNativeEvents(false);

		System.out.println(System.getProperty("user.dir"));

		try {
			profile.addExtension(new File(System.getProperty("user.dir"),
					"/resources/JSErrorCollector.xpi"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		try {
			driver = new FirefoxDriver(capabilities);
		} catch (WebDriverException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot initialize Firefox driver");
		}
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
	}

}
