package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Integration tests of AngularUI - Slider demo
 * https://htmlpreview.github.io/?https
 * ://github.com/angular-ui/ui-slider/master/demo/index.html
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgSliderTest {
	private static String fullStackTrace;
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	private static String baseUrl = "https://htmlpreview.github.io/?https://github.com/angular-ui/ui-slider/master/demo/index.html";

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

	@Before
	public void beforeEach() {
		ngDriver.navigate().to(baseUrl);
	}

	@Test
	public void testSliderKeyPress() throws Exception {
		if (isCIBuild) {
			return;
		}
		// suppressing the Angular interaction
		ngDriver.waitForAngular();
		// leads to org.openqa.selenium.WebDriverException: angular is not defined
		// in the
		List<WebElement> sliderElements = ngDriver.findElements(NgBy
				.model("demoVals.sliderExample1"));

		WebElement sliderContainerElement = sliderElements.get(0);
		assertThat(sliderContainerElement.getTagName(), equalTo("div"));
		highlight(sliderContainerElement);
		CommonFunctions.setHighlightTimeout(10);
		WebElement sliderElement = sliderContainerElement.findElement(By
				.className("ui-slider-handle"));
		for (int cnt = 0; cnt != 10; cnt++) {
			sliderElement.sendKeys(Keys.ARROW_RIGHT);
			highlight(sliderElement);
		}
		for (int cnt = 0; cnt != 10; cnt++) {
			sliderElement.sendKeys(Keys.ARROW_UP);
			highlight(sliderElement);
		}

		WebElement sliderValueElement = sliderElements.get(1);
		assertThat(sliderValueElement.getTagName(), equalTo("input"));
		actions.moveToElement(sliderValueElement).build().perform();
		CommonFunctions.setHighlightTimeout(100);
		highlight(sliderValueElement);
		System.err.println("Value = " + sliderValueElement.getAttribute("value"));
	}

	@Test
	public void testSliderMouseMove() throws Exception {
		if (isCIBuild) {
			return;
		}
		ngDriver.waitForAngular();
		List<WebElement> sliderElements = ngDriver.findElements(NgBy
				.model("demoVals.sliderExample1"));
		WebElement sliderContainerElement = sliderElements.get(0);
		assertThat(sliderContainerElement.getTagName(), equalTo("div"));
		Dimension dimension = sliderContainerElement.getSize();
		int width = dimension.width;
		// System.err.println("width = " + width);
		highlight(sliderContainerElement);
		WebElement sliderElement = sliderContainerElement.findElement(By
				.className("ui-slider-handle"));
		// actions.dragAndDropBy(sliderElement, 100, 0).build().perform();
		// has no effect
		for (int cnt = 0; cnt != 10; cnt++) {
			actions.moveToElement(sliderElement).clickAndHold();
			actions.moveByOffset(width / 20, 0);
			actions.release();
			actions.build().perform();
			Thread.sleep(100);
		}

		WebElement sliderValueElement = sliderElements.get(1);
		assertThat(sliderValueElement.getTagName(), equalTo("input"));
		actions.moveToElement(sliderValueElement).build().perform();
		highlight(sliderValueElement);
		System.err.println("Value = " + sliderValueElement.getAttribute("value"));
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) throws InterruptedException {
		CommonFunctions.highlight(element);
	}
}
