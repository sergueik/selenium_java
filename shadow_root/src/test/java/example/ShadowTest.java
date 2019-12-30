package example;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.concurrent.TimeUnit;

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import example.ShadowDriver;

public class ShadowTest {

	private final static String baseUrl = "https://www.virustotal.com";
	// private static final String urlLocator = "a[data-route='url']";
	private static final String urlLocator = "*[data-route='url']";
	private boolean debug = Boolean
			.parseBoolean(getPropertyEnv("DEBUG", "false"));;
	protected static String osName = getOSName();
	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome",
				osName.equals("windows") ? "chromedriver.exe" : "chromedriver");
		browserDrivers.put("firefox",
				osName.equals("windows") ? "geckodriver.exe" : "driver");
		browserDrivers.put("edge", "MicrosoftWebDriver.exe");
	}

	private static ChromeDriver driver = null;
	private static ShadowDriver shadowDriver = null;
	private static String browser = getPropertyEnv("webdriver.driver", "chrome");
	// use -P profile to override
	private static final boolean headless = Boolean
			.parseBoolean(getPropertyEnv("HEADLESS", "false"));

	public static String getBrowser() {
		return browser;
	}

	public static void setBrowser(String browser) {
		ShadowTest.browser = browser;
	}

	@BeforeClass
	public static void injectShadowJS() {
		err.println("Launching " + browser);
		if (browser.equals("chrome")) {
		} // TODO: finish for other browser

		System
				.setProperty("webdriver.chrome.driver",
						Paths.get(System.getProperty("user.home"))
								.resolve("Downloads").resolve(osName.equals("windows")
										? "chromedriver.exe" : "chromedriver")
								.toAbsolutePath().toString());

		// https://peter.sh/experiments/chromium-command-line-switches/
		ChromeOptions options = new ChromeOptions();
		// options for headless
		if (headless) {
			for (String arg : (new String[] { "headless", "window-size=1200x800" })) {
				options.addArguments(arg);
			}
		}

		driver = new ChromeDriver(options);
		driver.navigate().to(baseUrl);
		shadowDriver = new ShadowDriver(driver);
	}

	@Before
	public void init() {

	}

	@Ignore
	@Test
	public void testApp() {

	}

	@Ignore
	@Test
	public void testJSInjection() {
		WebElement element = shadowDriver.findElement(urlLocator);
		err.println(element);
		// Assertions.assertEquals(new String(""),
		// shadowDriver.driver.getPageSource(),
		// "Message");
	}

	@Ignore
	@Test
	public void testGetAllObject() {
		List<WebElement> elements = shadowDriver.findElements(urlLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d %s elements:", elements.size(), urlLocator));
		// NOTE: default toString() is not be particularly useful
		elements.stream().forEach(err::println);
		elements.stream().map(o -> o.getTagName()).forEach(err::println);
		elements.stream()
				.map(o -> String.format("innerHTML: %s", o.getAttribute("innerHTML")))
				.forEach(err::println);
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Ignore
	@Test
	public void testAPICalls1() {
		WebElement element = shadowDriver.findElements(urlLocator).stream()
				.filter(o -> o.getTagName().matches("div")).collect(Collectors.toList())
				.get(0);

		WebElement element1 = shadowDriver.getNextSiblingElement(element);
		assertThat(element1, notNullValue());
		// TODO: examine the collection of elements returned earlier
	}

	@Ignore
	@Test
	public void testAPICalls2() {
		WebElement element = shadowDriver.findElements(urlLocator).stream()
				.filter(o -> o.getTagName().matches("div")).collect(Collectors.toList())
				.get(0);
		List<WebElement> elements = shadowDriver.findElements(element, "img");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
	}

	@Ignore
	@Test
	public void testAPICalls3() {
		WebElement element = null;

		for (String locator : Arrays
				.asList(new String[] { "#wrapperLink", "*[data-route='url']" })) {
			try {
				element = shadowDriver.findElement(locator);
			} catch (ElementNotVisibleException e) {
				err.println("Exception (ignored): " + e.toString());
			}
			if (element != null) {
				try {
					List<WebElement> elements = shadowDriver.getChildElements(element);
					assertThat(elements, notNullValue());
					assertThat(elements.size(), greaterThan(0));
					err.println(String.format("Located %d child elements in %s:",
							elements.size(), locator));
				} catch (JavascriptException e) {
					err.println("Exception (ignored): " + e.toString());
					// TODO:
					// javascript error: Illegal invocation
					// https://stackoverflow.com/questions/10743596/why-are-certain-function-calls-termed-illegal-invocations-in-javascript
				}
			}
		}
	}

	@Test
	public void testAPICalls41() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
		element = elements.get(0);
		elements.clear();
		try {
			elements = shadowDriver.getAllShadowElement(element, "span");
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));
			err.println(String.format("Located %d child elements", elements.size()));
		} catch (JavascriptException e) {
			err.println("Exception (ignored): " + e.toString());
			// TODO:
			// javascript error: Cannot read property 'querySelectorAll' of null
		}
	}

	@Ignore
	@Test
	public void testAPICalls4() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.findElements(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
		element = elements.get(0);
		elements.clear();
		try {
			elements = shadowDriver.getChildElements(element);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));
			err.println(String.format("Located %d child elements", elements.size()));
		} catch (JavascriptException e) {
			err.println("Exception (ignored): " + e.toString());
			// TODO:
			// javascript error: Illegal invocation
			// https://stackoverflow.com/questions/10743596/why-are-certain-function-calls-termed-illegal-invocations-in-javascript
		}
	}

	@Ignore
	@Test
	public void testAPICalls5() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.findElements(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	// TODO:
	@Ignore
	@Test
	public void testAPICalls6() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.getSiblingElements(element);
		// javascript error: object.siblings is not a function
		// https://www.w3schools.com/jquery/traversing_siblings.asp
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownAll() {
		driver.close();
	}

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}

}
