package com.mycompany.app;

import java.awt.Toolkit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.RuntimeException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Boolean.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class App {

	private static WebDriver driver;
	static String baseUrl = "http://www.freetranslation.com/";
	static int flexible_wait_interval = 5;
	static long implicit_wait_interval = 3;
	static long wait_polling_interval = 500;
	static WebDriverWait wait;
	static Actions actions;
	static JavascriptExecutor javascriptExecutor;
	static String text = "good morning driver";

	// TODO: write the text to the file
	// Write-Host ('Translating: "{0}"' -f $text)
	// $text_file = [System.IO.Path]::Combine((Get-ScriptDirectory),
	// 'testfile.txt')
	// Write-Output $text | Out-File -FilePath $text_file -Encoding ascii

	static String localPath = "C:/developer/sergueik/powershell_selenium/powershell/testfile.txt";

	private static final StringBuffer verificationErrors = new StringBuffer();

	@Before
	public static void setUp() throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
				Platform.ANY);
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),
				capabilities);
		// TODO:
		// http://stackoverflow.com/questions/13204820/how-to-obtain-native-logger-in-selenium-webdriver
		// http://vrajasankar.blogspot.com/2012/04/logging-selenium-webdriver.html
		// https://www.nuget.org/packages/SeleniumLog﻿
		// driver has to be of type RemoteWebDriver
		// driver.setLogLevel(Level.ALL);
		javascriptExecutor = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);

		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);

	}

	public static void main(String[] args) throws Exception {
		setUp();
		testUpload();
		tearDown();
	}

	@Test
	public static void testUpload() throws Exception {

		// Wait for the page to load ( check that logo / title is updated )
		WebElement element = driver.findElement(By.cssSelector("a.brand"));
		assertThat(element.getAttribute("title"),
				containsString("Translate text, documents and websites for free"));

		// optional:
		// driver.manage().window().maximize();

		WebElement uploadButton = driver
				.findElement(By.cssSelector("div[id = 'upload-button']"));
		highlight(uploadButton, 1500);

		WebElement uploadElement = driver
				.findElement(By.cssSelector("input[class='ajaxupload-input']"));
		assertThat(uploadElement, notNullValue());
		assertEquals("file", uploadElement.getAttribute("type"));
		assertEquals("file", uploadElement.getAttribute("name"));
		// the uploadElement is not visible

		LocalFileDetector detector = new LocalFileDetector();
		File remoteFile = detector.getLocalFile(localPath);
		// https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/remote/LocalFileDetector.html
		// driver.setFileDetector(new LocalFileDetector());

		((RemoteWebElement) uploadElement).setFileDetector(detector);
		System.err.format("Uploading the file '%s'.\n",
				remoteFile.getAbsolutePath());

		// Populate upload input
		uploadElement.sendKeys(remoteFile.getAbsolutePath());
		// TODO : locate the progressbar
		// hard wait
		Thread.sleep(2000);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.className("gw-download-link")));
		} catch (RuntimeException timeoutException) {
			// continue - assertion follows
		}
		WebElement downloadLink = driver
				.findElement(By.className("gw-download-link"));
		assertEquals("Download", downloadLink.getText());

		WebElement downloaIndicator = driver.findElement(
				By.cssSelector("div [class='status-text'] img[class *= 'gw-icon']"));
		assertThat(downloaIndicator, notNullValue());
		// System.err.println(downloaIndicator.getAttribute("outerHTML"));
		highlight(downloaIndicator);
		String downloadHref = downloadLink.getAttribute("href");
		System.err.println("Reading '" + downloadHref + "'");
		sendGet(downloadHref);
		// assertEquals("file", downloadElement.getAttribute("name"));
	}

	@After
	public static void tearDown() throws Exception {
		driver.quit();
	}

	private static void highlight(WebElement element)
			throws InterruptedException {
		highlight(element, 100);
	}

	public static void highlight(WebElement element, int highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexible_wait_interval);
			wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		}
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			javascriptExecutor.executeScript(
					"arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight_interval);
			javascriptExecutor.executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

	// HTTP GET request FROM
	// http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	private static void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		connection.setRequestMethod("GET");

		// set request header
		String USER_AGENT = "Mozilla/5.0";
		connection.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = connection.getResponseCode();
		// TODO: Assert.
		System.err.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.err.println(response.toString());

	}
}
