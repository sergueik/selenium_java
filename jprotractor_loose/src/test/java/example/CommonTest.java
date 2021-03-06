package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Integration tests of Calculator http://juliemr.github.io/protractor-demo/
 * without actually using NgWebDriver class at Java level
 * but not using
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 * 
 */

public class CommonTest {

	protected static WebDriver driver;
	private static String osName = getOSName();
	protected static WebDriverWait wait;
	protected static Actions actions;
	protected static Alert alert;
	protected boolean debug = false;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	protected static String baseUrl = "about:blank"; // "http://juliemr.github.io/protractor-demo/";
	protected static final boolean headless = Boolean
			.parseBoolean(System.getenv("HEADLESS"));

	protected static List<WebElement> elements = new ArrayList<>();
	protected static WebElement element = null;
	protected static String script = null;
	protected static int cnt;

	@BeforeClass
	public static void setup() throws IOException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		// options for headless
		if (headless) {
			for (String optionAgrument : (new String[] { "headless",
					"window-size=1200x800" })) {
				options.addArguments(optionAgrument);
			}
		}
		System
				.setProperty("webdriver.chrome.driver",
						Paths.get(System.getProperty("user.home"))
								.resolve("Downloads").resolve(osName.equals("windows")
										? "chromedriver.exe" : "chromedriver")
								.toAbsolutePath().toString());

		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Before
	public void beforeEach() {
		driver.navigate().to(baseUrl);
	}

	// http://www.javawithus.com/tutorial/using-ellipsis-to-accept-variable-number-of-arguments
	public Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	protected Object executeAsyncScript(String script, Object... args) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeAsyncScript(script, args);
		} else {
			throw new RuntimeException("cannot execute script.");
		}
	}

	public Object executeScript(WebDriver driver, String script,
			Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public void highlight(WebElement element) {
		highlight(element, 100, "solid yellow");
	}

	public void highlight(WebElement element, long highlightInterval) {
		highlight(element, highlightInterval, "solid yellow");
	}

	public void highlight(WebElement element, long highlightInterval,
			String color) {
		// err.println("Color: " + color);
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		// Selenium Driver version sensitive code: 3.13.0 vs. 3.8.0 and older
		// https://stackoverflow.com/questions/49687699/how-to-remove-deprecation-warning-on-timeout-and-polling-in-selenium-java-client
		wait.pollingEvery(Duration.ofMillis((int) pollingInterval));

		// wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			String current_value = (String) executeScript(String.format(
					"const element = arguments[0]; current_value = element.style.border; element.style.border='3px %s' ; return current_value;",
					color), element);
			// TODO: pass argument
			if (debug)
				System.err.println("Current value :" + current_value);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=arguments[1]", element,
					current_value);
		} catch (InterruptedException e) {
			// err.println("Exception (ignored): " + e.toString());
		}
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = CommonTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static void getLocalPage(String pagename) {
		try {
			URI uri = CommonTest.class.getClassLoader().getResource(pagename).toURI();
			System.err.println("Testing: " + uri.toString());
			String baseUrl = uri.toString();
			driver.navigate().to(baseUrl);
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}
}