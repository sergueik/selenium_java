package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.lang.RuntimeException;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTest {

	private static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	private static WebElement element = null;
	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;
	private static String baseURL = "http://blueimp.github.io/jQuery-File-Upload/basic.html";
	private static final Logger log = LogManager.getLogger(AppTest.class);

	private static final StringBuffer verificationErrors = new StringBuffer();

	private static Pattern pattern;
	private static Matcher matcher;

	private static String testFileName = "test.txt";
	private static String testFilePath = new File(testFileName).getAbsolutePath();

	@BeforeClass
	public static void setUp() {

		// driver = new FirefoxDriver();
		File driverFile = new File("C:/tools/phantomjs/bin/phantomjs.exe");
		System.setProperty("phantomjs.binary.path", driverFile.getAbsolutePath());
		driver = new PhantomJSDriver();

		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.close();
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@Before
	public void beforeTest() {
		driver.get(baseURL);
	}

	@After
	public void resetBrowser() {
		// load blank page
		driver.get("about:blank");
	}

	@Test
	public void testExecutePhantomJS() {

		element = driver.findElement(By.id("fileupload"));
		assertThat(element, notNullValue());

		assertTrue(element.getAttribute("multiple") != null);

		executePhantomJS(String.format(
				"var page = this; page.uploadFile('input[id=fileupload]', '%s' );",
				testFilePath));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
		validate();
	}

	@Test
	public void testSendKeys() {

		element = driver.findElement(By.id("fileupload"));
		assertThat(element, notNullValue());
		// highlight(element);

		assertTrue(element.getAttribute("multiple") != null);
		// This fixes the problem with hanging PhantomJS:
		executeScript("$('#fileupload').removeAttr('multiple');");
		element.sendKeys(testFilePath);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
		validate();
	}

	@Test
	public void testBrokenSendKeys() {
		element = driver.findElement(By.id("fileupload"));
		assertThat(element, notNullValue());
		try {
			element.sendKeys(testFilePath);
			Thread.sleep(2000);
		} catch (Exception e) {
			System.err
					.println("Full stack trace:\n" + ExceptionUtils.getFullStackTrace(e));
		}
		validate();
	}

	private static void validate() {
		element = driver.findElement(By.className("progress-bar"));
		assertThat(element.getAttribute("class"),
				containsString("progress-bar-success"));
		element = driver.findElement(By.id("files"));
		assertThat(element.getText(), containsString(testFileName));

	}

	private static String getScriptContent(String scriptName) throws Exception {
		try {
			final InputStream stream = AppTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception(scriptName);
		}
	}

	private static Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver); // a.k.a. (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private static Object executePhantomJS(String script, Object... arguments) {
		if (driver instanceof PhantomJSDriver) {
			return ((PhantomJSDriver) driver).executePhantomJS(script, arguments);
		} else {
			return null;
		}
	}
}
