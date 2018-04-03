package com.github.sergueik.jprotractor.integration;

// import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Local file Integration tests of using Protractor Css Contrining Text selector 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgPlaceholderTextTest {

	// NOTE: non-Angular page
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static String fullStackTrace;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 1200;
	static int height = 800;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	public static String localFile;

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	// @Ignore
	@Test
	public void testNonAngularIgnoreSync() {
		if (isCIBuild) {
			return;
		}
		NgWebDriver ngDriver = new NgWebDriver(seleniumDriver, true);
		ngDriver.navigate()
				.to("http://www.seleniumeasy.com/test/input-form-demo.html");
		NgWebElement element = ngDriver.findElement(NgBy
				.cssContainingText("#contact_form > fieldset input", "E-Mail Address"));
		assertThat(element.getWrappedElement(), notNullValue());
		assertThat(element.getAttribute("data-bv-field"), is("email"));
		highlight(element);
		element.sendKeys("sample@gmail.com");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

}
