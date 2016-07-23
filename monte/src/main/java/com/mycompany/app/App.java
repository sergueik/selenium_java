package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import java.awt.Toolkit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Hashtable;
import java.lang.RuntimeException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
	private static WebDriver driver;
	static String SiteURL = "http://www.tripadvisor.com/";

	static int implicit_wait_interval = 1;
	static int flexible_wait_interval = 5;
	static long wait_polling_interval = 500;
	static long highlight_interval = 100;

	static WebDriverWait wait;
	static Actions actions;
	static VideoRecorder recorder;
	private static final StringBuffer verificationErrors = new StringBuffer();

	@Before
	public static void setup() throws Exception {
		long implicit_wait_interval = 3;
		driver = new FirefoxDriver();
		driver.get(SiteURL);
		driver.manage().timeouts()
				.implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
		recorder = new VideoRecorder();
		recorder.startRecording(driver);
	}

	public static void main(String[] args) throws Exception {
		setup();
		testVerifyText();
		tearDown();
	}

	@Test
	public static void testVerifyText() throws Exception {
		String selector = null;
		WebElement element = null;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.linkText("Hotels")));
		} catch (RuntimeException timeoutException) {

		}
		element = driver.findElement(By.linkText("Hotels"));

		assertEquals("Hotels", element.getText());
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript(
				"arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		javascriptExecutor.executeScript("arguments[0].style.border=''", element);

		element.click();
	}

	@After
	public static void tearDown() throws Exception {
		recorder.stopRecording("Recording");
		driver.quit();
	}

}
