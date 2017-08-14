package org.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Boolean.parseBoolean;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.json.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChromePagePerformanceUtil {

	private static final String JAVASCRIPT = "var performance = window.performance;"
			+ "var timings = performance.timing;" + "return timings;";

	private Map<String, Double> timers;
	private ArrayList<Map<String, String>> timersNew;

	private static ChromePagePerformanceUtil ourInstance = new ChromePagePerformanceUtil();

	public static ChromePagePerformanceUtil getInstance() {
		return ourInstance;
	}

	private ChromePagePerformanceUtil() {
	}

	public double getLoadTime(WebDriver driver, String endUrl) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endUrl);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(WebDriver driver, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(WebDriver driver, String endUrl, By navigator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endUrl);
		wait.until(ExpectedConditions.presenceOfElementLocated(navigator)).click();
		waitPageToLoad(driver, wait);
		setTimer(driver);
		setTimerNew(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(String endUrl) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().to(endUrl);
		waitPageToLoad(driver, wait);
		setTimer(driver);
		return calculateLoadTime();
	}

	public double getLoadTime(String endUrl, By navigator) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.navigate().to(endUrl);
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

	// TODO: use org.json
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

	private ArrayList<Map<String, String>> CreateDateMapFromJSON(String jsonData)
			throws JSONException {

		ArrayList<Map<String, String>> resultDataSet = new ArrayList<Map<String, String>>();
		// columns to collect
		Pattern pattern = Pattern.compile("(?:name|duration)");
		JSONArray dataRows = new JSONArray(jsonData);
		for (int row = 0; row < dataRows.length(); row++) {
			JSONObject jsonObj = new JSONObject(dataRows.get(row).toString());
			assertThat(jsonObj, notNullValue());
			Iterator<String> dataKeys = jsonObj.keys();
			Map<String, String> resultRow = new HashMap<>();
			while (dataKeys.hasNext()) {
				String dataKey = dataKeys.next();
				// Matcher matcher = pattern.matcher(dataKey);
				if (pattern.matcher(dataKey).find()) {
					// only collect certain columns
					resultRow.put(dataKey, jsonObj.get(dataKey).toString());
				}
			}
			resultDataSet.add(resultRow);
		}

		assertTrue(resultDataSet.size() > 0);
		System.err.println(String.format("Addd %d rows", resultDataSet.size()));
		for (Map<String, String> resultRow : resultDataSet) {
			Set<String> dataKeys = resultRow.keySet();
			for (String dataKey : dataKeys) {
				System.err.println(dataKey + " = " + resultRow.get(dataKey));
			}
		}

		return resultDataSet;
	}

	private void setTimerNew(WebDriver driver) {
		String performanceScript = getScriptContent("performance_script.js");

		// NOTE: the following does not work (works in c#):
		/*
		// cannot cast from String to List
		@SuppressWarnings("unchecked")
		List<Object> rawData = (List<Object>) ((JavascriptExecutor) driver)
				.executeScript(performanceScript);
		
		for (Object rawRow : rawData) {
			@SuppressWarnings("unchecked")
			Map<String, String> resultRow = (Map<String, String>) rawRow;
		}
		*/
		this.timersNew = CreateDateMapFromJSON(((JavascriptExecutor) driver)
				.executeScript(performanceScript).toString());
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
