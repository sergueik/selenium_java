package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Integration tests of Calculator http://juliemr.github.io/protractor-demo/
 * without actually using NgWebDriver class at Java level
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 * 
 */

public class StandaloneIntegrationTest {

	// private static NgWebDriver ngDriver;
	private static WebDriver driver;
	private static String osName = getOSName();
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	private boolean debug = false;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	private static String baseUrl = "http://juliemr.github.io/protractor-demo/";
	private static final boolean headless = Boolean
			.parseBoolean(System.getenv("HEADLESS"));

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

	private static List<WebElement> elements = new ArrayList();
	private static WebElement element = null;
	private static String script = null;

	@SuppressWarnings("unchecked")
	@Test
	public void testAddition() {
		for (int cnt = 0; cnt != 3; cnt++) {
			script = getScriptContent("model.js");
			elements = (List<WebElement>) executeScript(script, null, "first", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element, notNullValue());
			highlight(element);
			element.clear();
			element.sendKeys("40");
			elements = (List<WebElement>) executeScript(script, null, "second", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element, notNullValue());
			element.clear();
			element.sendKeys("2");
			script = getScriptContent("options.js");
			elements = (List<WebElement>) executeScript(script, null,
					"value for (key, value) in operators", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			element.click();
			script = getScriptContent("options.js");
			elements = (List<WebElement>) executeScript(script, null,
					"value for (key, value) in operators", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			element.click();
			script = getScriptContent("buttonText.js");
			elements = (List<WebElement>) executeScript(script, null, "Go!");
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element.getText(), containsString("Go"));
			element.click();
			sleep(3000);

			script = getScriptContent("waitForAngular.js");
			try {
				executeAsyncScript(script, null, null); // does not work
				// sleep(1000);
			} catch (ScriptTimeoutException e) {
			}
		}

		script = getScriptContent("repeater.js");
		elements = (List<WebElement>) executeScript(script, null,
				"result in memory", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(2));
		element = elements.get(0);
		System.err.println(element.getAttribute("outerHTML"));

		script = getScriptContent("binding.js");
		elements = (List<WebElement>) executeScript(script, null, "latest", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		sleep(10000);
		assertThat(element.getText(), equalTo("42"));
		highlight(element);
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

	private Object executeAsyncScript(String script, Object... args) {
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
			final InputStream stream = StandaloneIntegrationTest.class
					.getClassLoader().getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

}