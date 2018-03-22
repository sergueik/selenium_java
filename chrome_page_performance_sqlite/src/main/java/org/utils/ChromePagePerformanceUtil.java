package org.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

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

import static java.lang.Boolean.parseBoolean;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

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

public class ChromePagePerformanceUtil {

	private static String performanceTimerScript = String.format(
			"%s\nreturn window.timing.getTimes();",
			getScriptContent("performance_script.js"));
	private static String performanceNetworkScript = String.format(
			"%s\nreturn window.timing.getNetwork({stringify:true});",
			getScriptContent("performance_script.js"));

	private static final String simplePerformanceTimingsScript = "var performance = window.performancevar timings = performance.timing;"
			+ "return timings;";

	private Map<String, Double> pageElementTimers;

	public Map<String, Double> getPageElementTimers() {
		return pageElementTimers;
	}

	private Map<String, Double> pageEventTimers;

	public Map<String, Double> getPageEventTimers() {
		return pageEventTimers;
	}

	private boolean debug = false;

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	private int flexibleWait = 30;

	public int getFlexibleWait() {
		return flexibleWait;
	}

	public void setFlexibleWait(int flexibleWait) {
		this.flexibleWait = flexibleWait;
	}

	private static ChromePagePerformanceUtil ourInstance = new ChromePagePerformanceUtil();

	public static ChromePagePerformanceUtil getInstance() {
		return ourInstance;
	}

	private ChromePagePerformanceUtil() {
	}

	public double getLoadTime(WebDriver driver, String endUrl) {
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		driver.navigate().to(endUrl);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(WebDriver driver, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(WebDriver driver, String endUrl, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		driver.navigate().to(endUrl);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		setTimerNew(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(String endUrl) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
		driver.navigate().to(endUrl);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(String endUrl, By by) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, flexibleWait);

		driver.navigate().to(endUrl);
		if (by != null) {
			wait.until(ExpectedConditions.presenceOfElementLocated(by)).click();
		}
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	private void waitPageToLoad(WebDriver driver, WebDriverWait wait) {
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		});
	}

	private void setTimer(WebDriver driver) {
		String result = ((JavascriptExecutor) driver)
				.executeScript(
						performanceTimerScript /* simplePerformanceTimingsScript */)
				.toString();
		if (result == null) {
			throw new RuntimeException("result is null");
		}
		if (debug) {
			System.err.println("Processing result: " + result);
		}
		this.pageEventTimers = CreateDateMap(result);
	}

	private double calculateLoadTime() {
		return pageEventTimers.get("unloadEventStart");
	}

	// Example data:
	// payload = "[{redirectCount=0, encodedBodySize=64518, unloadEventEnd=0,
	// responseEnd=4247.699999992619, domainLookupEnd=2852.7999999932945,
	// unloadEventStart=0, domContentLoadedEventStart=4630.699999994249,
	// type=navigate, decodedBodySize=215670, duration=5709.000000002561,
	// redirectStart=0, connectEnd=3203.5000000032596, toJSON={},
	// requestStart=3205.499999996391, initiatorType=beacon}]";
	// TODO: use org.json
	public Map<String, Double> CreateDateMap(String payload) {
		Map<String, Double> eventData = new HashMap<>();
		Date currDate = new Date();

		payload = payload.substring(1, payload.length() - 1);
		String[] pairs = payload.split(",");

		for (String pair : pairs) {
			String[] values = pair.split("=");

			if (values[0].trim().toLowerCase().compareTo("tojson") != 0) {
				if (debug) {
					System.err.println("Collecting: " + pair);
				}
				eventData.put(values[0].trim(),
						((currDate.getTime() - Long.valueOf(values[1]))) / 1000.0);
			}
		}
		return eventData;
	}

	private Map<String, Double> CreateDateMapFromJSON(String payload)
			throws JSONException {

		if (debug) {
			System.err.println("payload: " + payload);
		}
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

	private void setTimerNew(WebDriver driver) {
		this.pageElementTimers = CreateDateMapFromJSON(((JavascriptExecutor) driver)
				.executeScript(performanceNetworkScript).toString());
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
}
