
package org.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChromePagePerformanceObject {

	private static final String JAVASCRIPT = "var performance = window.performance;"
			+ "var timings = performance.timing;" + "return timings;";

	private WebDriver driver;
	private Map<String, Double> timers;
	private WebDriverWait wait;

	public ChromePagePerformanceObject(WebDriver driver, String data, boolean javaScript) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);

		if (javaScript) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(data);
		} else {
			driver.navigate().to(data);
		}
		waitPageToLoad(this.driver, this.wait);
		setTimer(driver);
	}

	public ChromePagePerformanceObject(WebDriver driver, By navigator) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);

		this.wait.until(ExpectedConditions.presenceOfElementLocated(navigator))
				.click();

		waitPageToLoad(this.driver, this.wait);

		setTimer(driver);
	}

	public ChromePagePerformanceObject(WebDriver driver, String startUrl, By navigator) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		driver.navigate().to(startUrl);
		this.wait.until(ExpectedConditions.presenceOfElementLocated(navigator))
				.click();
		waitPageToLoad(this.driver, this.wait);
		setTimer(driver);
	}

	public ChromePagePerformanceObject(String endUrl) {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endUrl);
		waitPageToLoad(this.driver, this.wait);
		setTimer(driver);
	}

	public ChromePagePerformanceObject(String startUrl, By navigator) {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 30);
		driver.navigate().to(startUrl);
		this.wait.until(ExpectedConditions.presenceOfElementLocated(navigator))
				.click();
		waitPageToLoad(this.driver, this.wait);
		setTimer(driver);
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

	private Map<String, Double> CreateDateMap(String data) {
		Map<String, Double> dict = new HashMap<>();
		Date currDate = new Date();
		data = data.substring(1, data.length() - 1);
		String[] pairs = data.split(",");

		for (String pair : pairs) {
			String[] values = pair.split("=");

			if (values[0].trim().toLowerCase().compareTo("tojson") != 0) {
				dict.put(values[0].trim(),
						((currDate.getTime() - Long.valueOf(values[1]))) / 1000.0);
			}
		}

		return dict;
	}

	public double getLoadTime() {
		return timers.get("unloadEventStart");
	}

	public double connectEnd() {
		return timers.get("connectEnd");
	}

	public double connectStart() {
		return timers.get("connectStart");
	}

	public double domComplete() {
		return timers.get("domComplete");
	}

	public double domContentLoadedEventEnd() {
		return timers.get("domContentLoadedEventEnd");
	}

	public double domContentLoadedEventStart() {
		return timers.get("domContentLoadedEventStart");
	}

	public double domInteractive() {
		return timers.get("domInteractive");
	}

	public double domLoading() {
		return timers.get("domLoading");
	}

	public double domainLookupEnd() {
		return timers.get("domainLookupEnd");
	}

	public double domainLookupStart() {
		return timers.get("domainLookupStart");
	}

	public double fetchStart() {
		return timers.get("fetchStart");
	}

	public double loadEventEnd() {
		return timers.get("loadEventEnd");
	}

	public double loadEventStart() {
		return timers.get("loadEventStart");
	}

	public double navigationStart() {
		return timers.get("navigationStart");
	}

	public double redirectEnd() {
		return timers.get("redirectEnd");
	}

	public double redirectStart() {
		return timers.get("redirectStart");
	}

	public double requestStart() {
		return timers.get("requestStart");
	}

	public double responseEnd() {
		return timers.get("responseEnd");
	}

	public double responseStart() {
		return timers.get("responseStart");
	}

	public double secureConnectionStart() {
		return timers.get("secureConnectionStart");
	}

	public double unloadEventEnd() {
		return timers.get("unloadEventEnd");
	}

	public double unloadEventStart() {
		return timers.get("unloadEventStart");
	}

	@Override
	public String toString() {
		return "org.utils.ChromePagePerformanceObject{" + "timers=" + timers.toString() + '}';
	}
}
