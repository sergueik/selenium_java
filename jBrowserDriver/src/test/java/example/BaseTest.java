package example;

import static java.lang.System.err;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

@SuppressWarnings("deprecation")
public class BaseTest {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static WebElement element = null;
	protected static String selector = null;
	private static long implicit_wait_interval = 3;

	private static int flexible_wait_interval = 5;
	private static long wait_polling_interval = 500;
	private static long afterTest_interval = 1000;
	private static long highlight_interval = 100;
	private static String cssSelectorOfElementFinderScript;
	private static String cssSelectorOfElementAlternativeFinderScript;
	private static String xpathOfElementFinderScript;
	protected static final StringBuffer verificationErrors = new StringBuffer();
	private static StringBuilder loggingSb;
	private static String baseURL = "about:blank";

	@BeforeClass
	public static void setUp() throws Exception {
		loggingSb = new StringBuilder();
		driver = new JBrowserDriver(
				Settings.builder().timezone(Timezone.AMERICA_NEWYORK).build());
		// driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicit_wait_interval,
				TimeUnit.SECONDS);
		cssSelectorOfElementFinderScript = getScriptContent(
				"cssSelectorOfElement.js");
		cssSelectorOfElementAlternativeFinderScript = getScriptContent(
				"cssSelectorOfElementAlternative.js");
		xpathOfElementFinderScript = getScriptContent("xpathOfElement.js");
		driver.get(baseURL);
	}

	@Before
	public void beforeTest() {
	}

	@After
	public void afterTest() {
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Thread.sleep(afterTest_interval);
		if (driver instanceof JBrowserDriver) {
			// jbrowserDriver does not support quit() yet
			// https://github.com/MachinePublishers/jBrowserDriver/issues/213
			// driver.close();
			driver.quit();
		} else {
			driver.close();
			driver.quit();
		}
		if (verificationErrors.length() != 0) {
			throw new Exception(
					verificationErrors.toString());
		}
	}

	protected String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(cssSelectorOfElementFinderScript, element);
	}

	protected String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(cssSelectorOfElementAlternativeFinderScript,
				element);
	}

	protected String xpathOfElement(WebElement element) {
		return (String) executeScript(xpathOfElementFinderScript, element);
	}

	// wraps the core Selenium find methods with a wait, combines xpath
	protected List<WebElement> findElements(String strategy, String argument,
			WebElement parent) {
		SearchContext finder;
		String parent_css_selector = null;
		String parent_xpath = null;

		List<WebElement> elements = null;
		Hashtable<String, Boolean> supportedSelectorStrategies = new Hashtable<>();
		supportedSelectorStrategies.put("css_selector", true);
		supportedSelectorStrategies.put("xpath", true);

		if (strategy == null || !supportedSelectorStrategies.containsKey(strategy)
				|| !supportedSelectorStrategies.get(strategy)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = cssSelectorOfElement(parent);
			parent_xpath = xpathOfElement(parent);
			finder = parent;
		} else {
			finder = driver;
		}

		if (strategy == "css_selector") {
			String extended_css_selector = String.format("%s  %s",
					parent_css_selector, argument);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(argument));
		}
		if (strategy == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath, argument);

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(argument));
		}
		return elements;
	}

	protected List<WebElement> findElements(String strategy, String argument) {
		return findElements(strategy, argument, null);
	}

	// wraps the core Selenium find methods with a wait
	// Error in cssSelectorOfElementWithIdTest() : java.lang.NoClassDefFoundError: org/openqa/selenium/internal/WrapsDriver
	// https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/internal/WrapsDriver.html
	protected WebElement findElement(String strategy, String argument) {
		WebElement element = null;
		Hashtable<String, Boolean> supportedSelectorStrategies = new Hashtable<>();
		supportedSelectorStrategies.put("id", true);
		supportedSelectorStrategies.put("css_selector", true);
		supportedSelectorStrategies.put("xpath", true);
		supportedSelectorStrategies.put("partial_link_text", false);
		supportedSelectorStrategies.put("link_text", true);
		supportedSelectorStrategies.put("classname", false);

		if (strategy == null || !supportedSelectorStrategies.containsKey(strategy)
				|| !supportedSelectorStrategies.get(strategy)) {
			return null;
		}
		if (strategy == "id") {
			try {
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.id(argument)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.id(argument));
		}
		if (strategy == "classname") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.className(argument)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.className(argument));
		}
		if (strategy == "link_text") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.linkText(argument)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.linkText(argument));
		}
		if (strategy == "css_selector") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(argument)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.cssSelector(argument));
		}
		if (strategy == "xpath") {

			try {
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath(argument)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.xpath(argument));
		}
		return element;
	}

	protected void highlight(WebElement element) {
		highlight(element, highlight_interval, "yellow");
	}

	protected void highlight(WebElement element, long highlightInterval,
			String color) {
		System.err.println("Color: " + color);
		if (wait == null) {
			wait = new WebDriverWait(driver, flexible_wait_interval);
		}
		// Selenium Driver version sensitive code: 3.13.0 vs. 3.8.0 and older
		// https://stackoverflow.com/questions/49687699/how-to-remove-deprecation-warning-on-timeout-and-polling-in-selenium-java-client
		// wait.pollingEvery(Duration.ofMillis(wait_polling_interval));
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);

		// wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript(String.format("arguments[0].style.border='3px %s'", color),
					element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Exception (ignored): " + e.toString());
		}
	}

	protected Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = BaseTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected void fastSetText(String selector, String text) {
		String script = getScriptContent("setValueWithSelector.js");
		try {
			executeScript(script, selector, text);
		} catch (Exception e) {
			err.println("Ignored: " + e.toString());
		}
	}

	protected void fastSetText(WebElement element, String text) {
		String script = getScriptContent("setValue.js");
		try {
			executeScript(script, element, text);
		} catch (Exception e) {
			err.println("Ignored: " + e.toString());
		}
	}

}
