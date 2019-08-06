package com.github.sergueik.selenium;

import static java.lang.System.err;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// https://toster.ru/q/653249?e=7897302#comment_1962398
// https://stackoverflow.com/questions/29916054/change-user-agent-for-selenium-driver

public class ChromiumCdpTest {

	private static ChromiumDriver driver;
	// currently unused
	@SuppressWarnings("unused")
	private static Actions actions;
	private static String baseURL = "https://www.whoishostingthis.com/tools/user-agent/";
	private static Gson gson = new Gson();

	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		// NOTE: protected constructor method is not visible
		// driver = new ChromiumDriver((CommandExecutor) null, new
		// ImmutableCapabilities(),
		// null);
		driver = new ChromeDriver();
		actions = new Actions(driver);
	}

	@Before
	public void beforeTest() throws Exception {
		driver.get(baseURL);
	}

	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@SuppressWarnings("serial")
	@Test
	public void setUserAgentOverrideTest() {
		// Arrange
		By locator = By.cssSelector("div.info-box.user-agent");
		WebElement element = driver.findElement(locator);
		assertThat(element.getAttribute("innerText"), containsString("Mozilla"));

		try {
			driver.executeCdpCommand("Network.setUserAgentOverride",
					new HashMap<String, Object>() {
						{
							put("userAgent", "python 2.7");
							put("platform", "Windows");
						}
					});
		} catch (WebDriverException e) {
			System.err.println("Exception (ignored): " + e.toString());
			// org.openqa.selenium.WebDriverException: unknown error: unhandled
			// inspector error : {"code":-32601,"message":"'setUserAgentOverride'
			// wasn't found"}
		}
		driver.navigate().refresh();
		sleep(1000);

		element = driver.findElement(locator);
		assertThat(element.isDisplayed(), is(true));
		assertThat(element.getAttribute("innerText"), is("python 2.7"));
	}

	@Test
	public void getCookiesTest() {

		try {
			Map<String, Object> result = driver.executeCdpCommand("Page.getCookies",
					new HashMap<String, Object>());
			err.println("Cookies: " + result.get("cookies").toString());
			try {
				List<Map<String, Object>> cookies = gson
						.fromJson(result.get("cookies").toString(), ArrayList.class);

			} catch (JsonSyntaxException e) {
				err.println("Exception (ignored): " + e.toString());
			}
			try {
				ArrayList<Map<String, Object>> cookies = (ArrayList<Map<String, Object>>) result
						.get("cookies");
				cookies.stream().limit(3).map(o -> o.keySet())
						.forEach(System.err::println);
				Set<String> cookieKeys = new HashSet<>();
				cookieKeys.add("domain");
				cookieKeys.add("expires");
				cookieKeys.add("httpOnly");
				cookieKeys.add("name");
				cookieKeys.add("path");
				cookieKeys.add("secure");
				cookieKeys.add("session");
				cookieKeys.add("size");
				cookieKeys.add("value");

				assertTrue(cookies.get(0).keySet().containsAll(cookieKeys));

			} catch (Exception e) {
				err.println("Exception (ignored): " + e.toString());
			}

		} catch (WebDriverException e) {
			err.println("Exception (ignored): " + e.toString());
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}