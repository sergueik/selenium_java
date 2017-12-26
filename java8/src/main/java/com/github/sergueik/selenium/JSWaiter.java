package com.github.sergueik.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on: https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection

public class JSWaiter {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor javascriptExecutor;

	private Object executeScript(String script, Object... arguments) {
		return this.javascriptExecutor.executeScript(script, arguments);
	}

	// Get the driver
	public void setDriver(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, 10);
		if (driver instanceof JavascriptExecutor) {
			this.javascriptExecutor = JavascriptExecutor.class.cast(driver); // a.k.a.
																																				// (JavascriptExecutor)
																																				// driver;
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	public void waitForJQueryLoad() {

		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
				.executeScript("return jQuery.active") == 0);

		// Get JQuery is Ready
		if (!((Boolean) executeScript("return jQuery.active==0"))) {
			System.out.println("JQuery is NOT Ready!");
			// Wait for jQuery to load
			this.wait.until(jQueryLoad);
		} else {
			System.out.println("JQuery is Ready!");
		}
	}

	public void waitForAngularLoad() {

		ExpectedCondition<Boolean> angularLoad = driver -> Boolean
				.valueOf(((JavascriptExecutor) driver)
						.executeScript(
								"return angular.element(document).injector().get('$http').pendingRequests.length === 0")
						.toString());

		boolean angularReady = Boolean.valueOf(executeScript(
				"return angular.element(document).injector().get('$http').pendingRequests.length === 0")
						.toString());

		if (!angularReady) {
			System.out.println("ANGULAR is NOT Ready!");
			this.wait.until(angularLoad);
		} else {
			System.out.println("ANGULAR is Ready!");
		}
	}

	public void waitUntilJSReady() {
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString()
				.equals("complete");

		boolean jsReady = (Boolean) executeScript("return document.readyState")
				.toString().equals("complete");

		if (!jsReady) {
			System.out.println("JS in NOT Ready!");
			wait.until(jsLoad);
		} else {
			System.out.println("JS is Ready!");
		}
	}

	public void waitUntilJQueryReady() {
		// First check that JQuery is defined on the page. If it is, then wait AJAX
		Boolean jQueryDefined = (Boolean) executeScript(
				"return typeof jQuery != 'undefined'");
		if (jQueryDefined == true) {
			sleep(20);

			waitForJQueryLoad();
			waitUntilJSReady();

			sleep(20);
		} else {
			System.out.println("jQuery is not defined on this site!");
		}
	}

	// Wait Until Angular and JS Ready
	public void waitUntilAngularReady() {
		Boolean angularUnDefined = (Boolean) executeScript(
				"return window.angular === undefined");
		if (!angularUnDefined) {
			Boolean angularInjectorUnDefined = (Boolean) executeScript(
					"return angular.element(document).injector() === undefined");
			if (!angularInjectorUnDefined) {
				// Pre Wait for stability (Optional)
				sleep(20);

				// Wait Angular Load
				waitForAngularLoad();

				// Wait JS Load
				waitUntilJSReady();

				// Post Wait for stability (Optional)
				sleep(20);
			} else {
				System.out.println("Angular injector is not defined on this site!");
			}
		} else {
			System.out.println("Angular is not defined on this site!");
		}
	}

	// Wait Until JQuery Angular and JS is ready
	public void waitJQueryAngular() {
		waitUntilJQueryReady();
		waitUntilAngularReady();
	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}