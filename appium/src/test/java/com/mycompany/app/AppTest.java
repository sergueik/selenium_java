package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.spi.DirStateFactory.Result;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.*;
import static org.openqa.selenium.By.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class AppTest {

	private AppiumDriver<WebElement> driver;

	private WebDriverWait wait;
	static Actions actions;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private int screenHeight = 800;
	private int screenWidth = 480;
	private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
				"Nexus_4_API_22");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
		driver = new AndroidDriver<WebElement>(
				new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
		try {
			Dimension dimensions = driver.manage().window().getSize();
			// unknown error: operation is unsupported on Android
			// keep code for emulation tests
			screenWidth = dimensions.getWidth();
			screenHeight = dimensions.getHeight();
		} catch (WebDriverException e) {

		}
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseUrl);
	}

	@AfterMethod
	public void resetBrowser() {
		// load blank page
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void Test1() {
		// Arrange
		WebElement bar = wait.until((WebDriver d) -> {
			WebElement element = null;
			try {
				element = d.findElement(By.cssSelector(
						"section.section-examples div.examples div.box-example-square div.box-body div.br-theme-bars-square"));
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		});
		assertThat(bar, notNullValue());
		actions.moveToElement(bar).build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		assertTrue(bar.isDisplayed());
		// NOTE:
		// http://stackoverflow.com/questions/22703159/selenium-movebyoffset-doesnt-do-anything
		Action moveM = actions.moveByOffset(0, -100).build();
		moveM.perform();
		/*
		 * try { Rectangle rect = bar.getRect(); System.err.println(String.format(
		 * "Rectangle: %d , %d", rect.x, rect.y)); } catch (Exception e) { // error:
		 * Could not proxy command to remote server. // Original error: 404 -
		 * unknown command: // session/.../element/.../rect // it also appear to
		 * hang the Appium, even inside the try / catch block System.err.println(
		 * "Exception: " + e.toString()); }
		 */
		Point point = bar.getLocation();
		System.err
				.println(String.format("Element Location: %d , %d", point.x, point.y));
		int bottom = point.y;
		int cnt = 0;
		// NOTE: standalone apk just for swipe:
		// http://www.software-testing-tutorials-automation.com/2015/11/appium-how-to-swipe-vertical-and.html
		while (bottom > screenHeight / 2) {
			driver.findElementByTagName("body").sendKeys(Keys.DOWN);
			try {
				if (driver instanceof JavascriptExecutor) {
					Long result = (Long) ((JavascriptExecutor) driver).executeScript(
							"return arguments[0].getBoundingClientRect()[\"bottom\"]", bar);
					bottom = result.intValue();
					System.err.println(String.format("Bottom: %d, cnt: %d", bottom, cnt));
				}
				cnt++;
			} catch (Exception e) {
				System.err.println("Exception: " + e.toString());
			}
			// Assert
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);

		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		ratings.keySet().stream().forEach(o -> {
			System.err.println("Mouse over rating: " + o);
			WebElement r = ratings.get(o);
			// hover
			actions.moveToElement(r).build().perform();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		});
		// TODO: Assert: attribute change
	}

	@Test(enabled = true)
	public void Test2() {
		// Arrange
		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.examples div.box-example-reversed")));
		assertThat(bar, notNullValue());
		WebElement comment = bar.findElement(By.xpath(
				".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.err.println(
				String.format("About to scroll to text: '%s'", comment.getText()));
		try {
			driver.scrollTo(comment.getText());
		} catch (WebDriverException e) {
			System.err.println("Exception: " + e.getMessage() );
			// unknown error: Unsupported locator strategy: -android uiautomator
			System.err.println("Exception: " + e.toString());
		}

		HashMap<String, Object> scrollObject = new HashMap<String, Object>();
		scrollObject.put("direction", "down");
		try {
			System.err.println("About to scroll down with Javascriptt");
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript("mobile: scroll",
						scrollObject);
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		/*
		 * http://stackoverflow.com/questions/23339742/how-to-perform-swipe-using-
		 * appium-in-java-for-android-native-app
		 */

		try {
			driver.swipe(100, 200, 100, 600, 1000);
		} catch (WebDriverException e) {
			System.err.println("Exception: " + e.getMessage() );
			// Not yet implemented.
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	public static void swipeUpElement(AppiumDriver<WebElement> driver,
			WebElement element, int duration) {
		int bottomY = element.getLocation().getY() - 200;
		driver.swipe(element.getLocation().getX(), element.getLocation().getY(),
				element.getLocation().getX(), bottomY, duration);
	}

}