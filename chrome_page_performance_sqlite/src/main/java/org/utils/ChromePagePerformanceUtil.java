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

	private ArrayList<Map<String, String>> CreateDateMapFromJSON(String data) throws JSONException {
		// System.err.println("Data: " + data);
		ArrayList<Map<String, String>> hashes = new ArrayList<Map<String, String>>();
    

		Pattern pattern = Pattern.compile("(?:name|duration)");
		JSONArray hashesDataArray = new JSONArray(data);
		// System.err.println("# data rows : " + hashesDataArray.length());
		for (int row = 0; row < hashesDataArray.length(); row++) {
			// System.err.println("Data row: " + hashesDataArray.get(row));
			JSONObject resultObj = new JSONObject(
					hashesDataArray.get(row).toString());
			assertThat(resultObj, notNullValue());
			Iterator<String> dataKeys = resultObj.keys();
			Map<String, String> peformanceData = new HashMap<>();
			while (dataKeys.hasNext()) {
				String dataKey = dataKeys.next();
				// JSONArray resultDataArray = resultObj.getJSONArray(dataKey);
				// System.err.println(dataKey + " = " + resultObj.get(dataKey));
				Matcher matcher = pattern.matcher(dataKey);
				if (matcher.find()) {
					peformanceData.put(dataKey, resultObj.get(dataKey).toString());
				}
			}
			hashes.add(peformanceData);
		}
        
		assertTrue(hashes.size() > 0);
		System.err.println("Hash: " + hashes.size());
		for (Map<String, String> row : hashes) {
			Set<String> dataKeys = row.keySet();
			for (String dataKey : dataKeys) {
				System.err.println(dataKey + " = " + row.get(dataKey));
			}
		}

		return hashes;
	}

	private void setTimerNew(WebDriver driver) {
		String performanceScript = getScriptContent("performance_script.js");
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
