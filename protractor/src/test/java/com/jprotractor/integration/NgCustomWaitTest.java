package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.jprotractor.NgWebDriver;

/**
 * Alternative waitForAngular tests based on example from https://github.com/ansiona/ProtractorDemoTest/blob/master/src/main/java/protractordemotest/pages/BasePage.java
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgCustomWaitTest {
	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static boolean isCIBuild = false;

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		ngDriver = new NgWebDriver(seleniumDriver);
	}

	@Test
	public void test1() {
		ngDriver.navigate().to("https://angular-ui.github.io/");
		waitForAngularJS();
	}

	@Test
	public void test2() {
		ngDriver.navigate().to("https://www.yahoo.com/");
		waitForAngularJS();
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	public void waitForAngularJS() {

		try {
			executeScript(
					"waitForAngular = function() { window.angularFinished = false; angular.element(document.querySelector('body')).injector().get('$browser').notifyWhenNoOutstandingRequests(function() { window.angularFinished = true});};waitForAngular();");
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					// NullPointerException
					Object angularFinished = executeScript(
							"return window.angularFinished;");
					return angularFinished != null
							&& java.lang.Boolean.parseBoolean(angularFinished.toString());
				}
			});
		} catch (TimeoutException e) {
			System.out.println("Exception (ignored): " + e.toString());
		} catch (WebDriverException e) {
			System.out.println("Exception (ignored): " + e.toString());
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Unexpected exception: " + ExceptionUtils.getFullStackTrace(e)));
		}
	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.JavascriptExecutor
	public static Object executeScript(String script, Object... args) {
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) seleniumDriver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}
}
