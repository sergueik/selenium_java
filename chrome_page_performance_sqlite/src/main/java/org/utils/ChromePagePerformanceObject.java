package org.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class ChromePagePerformanceObject {

	private static String performanceTimerScript = String.format(
			"%s\nreturn window.timing.getTimes();",
			getScriptContent("performance_script.js"));
	private static String performanceNetworkScript = String.format(
			"%s\nreturn window.timing.getNetwork({stringify:true});",
			getScriptContent("performance_script.js"));

	private static final String simplePerformanceTimingsScript = "var performance = window.performancevar timings = performance.timing;"
			+ "return timings;";

	private WebDriver driver;
	private Map<String, Double> pageElementTimers;
	private Map<String, Double> pageEventTimers;
	private boolean debug = false;
	private WebDriverWait wait;
	private int flexibleWait = 30;

	public Map<String, Double> getPageElementTimers() {
		return pageElementTimers;
	}

	public Map<String, Double> getPageEventTimers() {
		return pageEventTimers;
	}

	public void setDebug(boolean value) {
		this.debug = value;
	}

	public int getFlexibleWait() {
		return flexibleWait;
	}

	public ChromePagePerformanceObject(WebDriver driver, String data,
			boolean javaScript) {
		this.driver = driver;
		wait = new WebDriverWait(driver, flexibleWait);

		if (javaScript) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(data);
		} else {
			driver.navigate().to(data);
		}
		waitPageToLoad(driver, wait);
		setTimer(driver);
	}

	public ChromePagePerformanceObject(WebDriver driver, By by) {
		this.driver = driver;
		if (by != null) {
			wait = new WebDriverWait(driver, flexibleWait);
			wait.until(ExpectedConditions.presenceOfElementLocated(by)).click();
			waitPageToLoad(driver, wait);
			setTimer(driver);
		}
	}

	public ChromePagePerformanceObject(WebDriver driver, String startUrl, By by) {
		this.driver = driver;
		wait = new WebDriverWait(driver, flexibleWait);
		if (startUrl != null) {
			driver.navigate().to(startUrl);
		}
		if (by != null) {
			wait.until(ExpectedConditions.presenceOfElementLocated(by)).click();
			waitPageToLoad(driver, wait);
			setTimer(driver);
			setTimerNew(driver);
		}
	}

	public ChromePagePerformanceObject(String endUrl) {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, flexibleWait);
		driver.navigate().to(endUrl);
		waitPageToLoad(this.driver, this.wait);
		setTimer(driver);
	}

	public ChromePagePerformanceObject(String startUrl, By by) {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, flexibleWait);
		if (startUrl != null) {
			driver.navigate().to(startUrl);
		}
		if (by != null) {
			this.wait.until(ExpectedConditions.presenceOfElementLocated(by)).click();
		}
		waitPageToLoad(driver, wait);
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

		this.pageEventTimers = CreateDateMap(((JavascriptExecutor) driver)
				.executeScript(performanceTimerScript).toString());
	}

	private void setTimerNew(WebDriver driver) {
		this.pageElementTimers = CreateDateMapFromJSON(((JavascriptExecutor) driver)
				.executeScript(performanceNetworkScript).toString());
	}

	public Map<String, Double> CreateDateMap(String payload) {
		Map<String, Double> eventData = new HashMap<>();
		Date currDate = new Date();

		payload = payload.substring(1, payload.length() - 1);
		String[] pairs = payload.split(",");

		for (String pair : pairs) {
			String[] values = pair.split("=");

			if (values[0].trim().toLowerCase().compareTo("tojson") != 0
					&& values[0].trim().toLowerCase().compareTo("initiatortype") != 0
					&& values[0].trim().toLowerCase().compareTo("type") != 0) {
				if (debug) {
					System.err.println("Collecting: " + pair);
				}
				try {
					eventData.put(values[0].trim(),
							((currDate.getTime() - Double.valueOf(values[1]))) / 1000.0);
				} catch (NumberFormatException e) {
					// ignore
					System.err.println(String.format("Exception (ignored) %s for %s = %s",
							e.toString(), values[0], values[1]));
				}
			}
		}
		return eventData;
	}

	private Map<String, Double> CreateDateMapFromJSON(String payload)
			throws JSONException {

		List<Map<String, String>> result = new ArrayList<>();
		// select columns to collect
		Pattern columnSelectionattern = Pattern.compile("(?:name|duration)");
		// ignore page events
		List<String> events = new ArrayList<>(Arrays.asList(new String[] {
				"first-contentful-paint", "first-paint", "intentmedia.all.end",
				"intentmedia.all.start", "intentmedia.core.fetch.page.request",
				"intentmedia.core.fetch.page.response", "intentmedia.core.init.end",
				"intentmedia.core.init.start", "intentmedia.core.newPage.end",
				"intentmedia.core.newPage.start", "intentmedia.core.scriptLoader.end",
				"intentmedia.core.scriptLoader.start",
				"intentmedia.sca.fetch.config.request",
				"intentmedia.sca.fetch.config.response" }));
		Pattern nameSelectionPattern = Pattern
				.compile(String.format("(?:%s)", String.join("|", events)));
		JSONArray jsonData = new JSONArray(payload);
		for (int row = 0; row < jsonData.length(); row++) {
			JSONObject jsonObj = new JSONObject(jsonData.get(row).toString());
			// assertThat(jsonObj, notNullValue());
			Iterator<String> dataKeys = jsonObj.keys();
			Map<String, String> dataRow = new HashMap<>();
			while (dataKeys.hasNext()) {
				String dataKey = dataKeys.next();
				if (columnSelectionattern.matcher(dataKey).find()) {
					dataRow.put(dataKey, jsonObj.get(dataKey).toString());
				}
			}
			// only collect page elements, skip events
			if (!nameSelectionPattern.matcher(dataRow.get("name")).find()) {
				result.add(dataRow);
			}
		}
		assertTrue(result.size() > 0);
		System.err.println(String.format("Added %d rows", result.size()));
		if (debug) {
			for (Map<String, String> resultRow : result) {
				Set<String> dataKeys = resultRow.keySet();
				for (String dataKey : dataKeys) {
					System.err.println(dataKey + " = " + resultRow.get(dataKey));
				}
			}
		}
		Map<String, Double> pageObjectTimers = new HashMap<>();

		for (Map<String, String> row : result) {
			try {
				pageObjectTimers.put(row.get("name"),
						java.lang.Double.parseDouble(row.get("duration")) / 1000.0);
			} catch (NumberFormatException e) {
				pageObjectTimers.put(row.get("name"), 0.0);
			}
		}

		if (debug) {
			Set<String> names = pageObjectTimers.keySet();
			for (String name : names) {
				System.err.println(name + " = " + pageObjectTimers.get(name));
			}
		}
		return pageObjectTimers;
	}

	public double getLoadTime() {
		return pageEventTimers.get("unloadEventStart");
	}

	public double connectEnd() {
		return pageEventTimers.get("connectEnd");
	}

	public double connectStart() {
		return pageEventTimers.get("connectStart");
	}

	public double domComplete() {
		return pageEventTimers.get("domComplete");
	}

	public double domContentLoadedEventEnd() {
		return pageEventTimers.get("domContentLoadedEventEnd");
	}

	public double domContentLoadedEventStart() {
		return pageEventTimers.get("domContentLoadedEventStart");
	}

	public double domInteractive() {
		return pageEventTimers.get("domInteractive");
	}

	public double domLoading() {
		return pageEventTimers.get("domLoading");
	}

	public double domainLookupEnd() {
		return pageEventTimers.get("domainLookupEnd");
	}

	public double domainLookupStart() {
		return pageEventTimers.get("domainLookupStart");
	}

	public double fetchStart() {
		return pageEventTimers.get("fetchStart");
	}

	public double loadEventEnd() {
		return pageEventTimers.get("loadEventEnd");
	}

	public double loadEventStart() {
		return pageEventTimers.get("loadEventStart");
	}

	public double navigationStart() {
		return pageEventTimers.get("navigationStart");
	}

	public double redirectEnd() {
		return pageEventTimers.get("redirectEnd");
	}

	public double redirectStart() {
		return pageEventTimers.get("redirectStart");
	}

	public double requestStart() {
		return pageEventTimers.get("requestStart");
	}

	public double responseEnd() {
		return pageEventTimers.get("responseEnd");
	}

	public double responseStart() {
		return pageEventTimers.get("responseStart");
	}

	public double secureConnectionStart() {
		return pageEventTimers.get("secureConnectionStart");
	}

	public double unloadEventEnd() {
		return pageEventTimers.get("unloadEventEnd");
	}

	public double unloadEventStart() {
		return pageEventTimers.get("unloadEventStart");
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = ChromePagePerformanceUtil.class
					.getClassLoader().getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	@Override
	public String toString() {
		return "org.utils.ChromePagePerformanceObject{" + "pageEventTimers="
				+ pageEventTimers.toString() + '}';
	}
}
