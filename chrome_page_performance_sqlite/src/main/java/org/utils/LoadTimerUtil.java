package org.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoadTimerUtil {

	private static final String JAVASCRIPT = "var performance = window.performance;"
			+ "var timings = performance.timing;" + "return timings;";

	private Map<String, Double> timers;

	private static LoadTimerUtil ourInstance = new LoadTimerUtil();

	public static LoadTimerUtil getInstance() {
		return ourInstance;
	}

	private LoadTimerUtil() {
	}

	// Get a driver instance, go to endpoint, return load time
	public double getLoadTime(WebDriver driver, String endPoint) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endPoint);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	// Get a driver instance, do a click, return load time
	public double getLoadTime(WebDriver driver, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	// Get a driver instance, go to start point, do a click, return load time
	public double getLoadTime(WebDriver driver, String endPoint, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endPoint);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	// Open browser, go to start point, return load time
	public double getLoadTime(String endPoint) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endPoint);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	// Open browser, go to start point, do a click, return load time
	public double getLoadTime(String endPoint, By navigator) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.navigate().to(endPoint);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);

		setTimer(driver);

		return calculateLoadTime();
	}

	private void waitPageToLoad(WebDriver driver, WebDriverWait wait) {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		});
	}

	private void setTimer(WebDriver driver) {
		this.timers = CreateDateMap(
				((JavascriptExecutor) driver).executeScript(JAVASCRIPT).toString());
	}

	private double calculateLoadTime() {
		return timers.get("unloadEventStart");
	}

	private Map<String, Double> CreateDateMap(String str) {
		Map<String, Double> dict = new HashMap<String, Double>();
		Date currDate = new Date();

		str = str.substring(1, str.length() - 1);
		String[] pairs = str.split(",");

		for (String pair : pairs) {
			String[] values = pair.split("=");

			if (values[0].trim().toLowerCase().compareTo("tojson") != 0) {
				dict.put(values[0].trim(),
						((currDate.getTime() - Long.valueOf(values[1]))) / 1000.0);
			}
		}

		return dict;
	}
}
