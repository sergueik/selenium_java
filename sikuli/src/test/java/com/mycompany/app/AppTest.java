package com.mycompany.app;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;

import java.io.IOException;
import java.io.IOException;
import java.lang.RuntimeException;

import java.lang.Boolean.*;

import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

import org.hamcrest.CoreMatchers.*;
import org.junit.Assert.*;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.Pattern;

// https://www.youtube.com/watch?v=8OfnQEfzfmw
public class AppTest {

	// private Logger logger = Logger.getLogger(this.getClass().getName());

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Actions actions;
	private static Screen screen;

	private static int flexibleWait = 5;
	private static int implicitWait = 1;
	private static long pollingInterval = 500;
	private static int sikuliTimeout = 5;
	private File tmpFile = null;

	@BeforeClass
	public static void setUp() {
		// driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"c:/java/selenium/chromedriver.exe");
		driver = new ChromeDriver();
		screen = new Screen();
		screen.setAutoWaitTimeout(sikuliTimeout);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@Before
	public void loadPage() {
		driver.get("about:blank");
	}

	@Test
	public void testUpload() {
		String openButtonImage = AppTest.class.getClassLoader()
				.getResource("open_1920x1080.png").getPath();
		String filenameTextBoxImage = AppTest.class.getClassLoader()
				.getResource("filename_1920x1080.png").getPath();
		Pattern filenameTextBox = new Pattern(filenameTextBoxImage);
		driver.navigate().to(getPageContent("upload.html"));
		try {
			tmpFile = File.createTempFile("foo", ".png");
			WebElement element = driver.findElement(By.tagName("input"));
			element.click();
			try {
				screen.exists(openButtonImage, sikuliTimeout);
				screen.type(filenameTextBox, tmpFile.getCanonicalPath());
				System.out.println("Uploading: " + tmpFile.getCanonicalPath());
				screen.click(openButtonImage, 0);
			} catch (FindFailed e) {
				verificationErrors.append(e.toString());
			}
			try {
				element = driver.findElement(By.tagName("input"));
				highlight(element);
				assertThat(element.getAttribute("value"),
						containsString(tmpFile.getName()));
			} catch (Error e) {
				verificationErrors.append(e.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(verificationErrors.toString());
		}
	}

	private String getPageContent(String pagename) {
		try {
			URI uri = AppTest.class.getClassLoader().getResource(pagename).toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
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

	public Object execute_script(String script, Object... args) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException(
					"Script execution is only available for WebDrivers that implement "
							+ "the JavascriptExecutor interface.");
		}
	}
}
