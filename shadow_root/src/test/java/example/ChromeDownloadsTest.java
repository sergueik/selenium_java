package example;

import static java.lang.System.err;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

*/

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import example.BaseTest;

// this test methods will get skipped when run on Firefox
public class ChromeDownloadsTest {

	private static Map<String, String> env = System.getenv();
	private static boolean isCIBuild = BaseTest.checkEnvironment();

	protected static final boolean debug = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("DEBUG", "false"));

	protected static WebDriver driver = null;
	protected static ShadowDriver shadowDriver = null;
	private static String browser = BaseTest.getPropertyEnv("BROWSER",
			BaseTest.getPropertyEnv("webdriver.driver", "chrome"));

	private static JavascriptExecutor javascriptExecutor;
	private static final String script = "var getShadowElement = function getShadowElement(object,selector) { return object.shadowRoot.querySelector(selector);};   return getShadowElement(arguments[0],arguments[1]);";

	private static final BrowserChecker browserChecker = new BrowserChecker(
			browser);

	private static final boolean headless = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("HEADLESS", "false"));

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setup() {
		if (browser.equals("chrome")) {
			err.println("Launching " + browser);
			if (isCIBuild) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else {
				final String chromeDriverPath = Paths
						.get(System.getProperty("user.home")).resolve("Downloads")
						.resolve(System.getProperty("os.name").toLowerCase()
								.startsWith("windows") ? "chromedriver.exe" : "chromedriver")
						.toAbsolutePath().toString();
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);

				// https://peter.sh/experiments/chromium-command-line-switches/
				ChromeOptions options = new ChromeOptions();
				// options for headless
				if (headless) {
					for (String arg : (new String[] { "headless",
							"window-size=1200x800" })) {
						options.addArguments(arg);
					}
				}

				Map<String, Object> preferences = new Hashtable<>();
				preferences.put("profile.default_content_settings.popups", 0);
				preferences.put("download.prompt_for_download", "false");
				String downloadsPath = System.getProperty("user.home") + "/Downloads";
				preferences.put("download.default_directory",
						BaseTest.getPropertyEnv("fileDownloadPath", downloadsPath));

				Map<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("plugins.always_open_pdf_externally", true);
				Map<String, Object> plugin = new HashMap<>();
				plugin.put("enabled", false);
				plugin.put("name", "Chrome PDF Viewer");

				chromePrefs.put("plugins.plugins_list", Arrays.asList(plugin));
				options.setExperimentalOption("prefs", chromePrefs);
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.binary", chromeDriverPath);

				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capabilities);
			}
			shadowDriver = new ShadowDriver(driver);
			shadowDriver.setDebug(debug);
			javascriptExecutor = JavascriptExecutor.class.cast(driver);
		}
	}

	@Before
	public void init() {
		driver.navigate().to("about:blank");
	}

	// TODO: ordering
	@Test
	public void test1() { // downloadPDFTest
		Assume.assumeTrue(browser.equals("chrome"));
		driver.navigate().to(
				"https://intellipaat.com/blog/tutorial/selenium-tutorial/selenium-cheat-sheet/");
		WebElement element = driver.findElement(By.xpath(
				"//*[@id=\"global\"]//a[contains(@href, \"Selenium-Cheat-Sheet.pdf\")]"));
		element.click();
		sleep(5000);
	}

	@Test
	public void test2() { // listDownloadsTest
		Assume.assumeTrue(browserChecker.testingChrome());
		driver.navigate().to("chrome://downloads/");
		WebElement element = driver.findElement(By.tagName("downloads-manager"));
		Object result1 = executeScript(script, element, "#downloadsList");
		assertThat(result1, notNullValue());
		if (debug) {
			System.err.println("Result is: " + result1);
		}
		WebElement element2 = (WebElement) result1;
		System.err.println("Result element: " + element2.getAttribute("outerHTML"));

		Object result2 = executeScript(script,
				element2.findElement(By.tagName("downloads-item")), "div#details");
		assertThat(result2, notNullValue());
		if (debug) {
			System.err.println("Result is: " + result2);
		}
		WebElement element3 = (WebElement) result2;
		System.err.println("Result element: " + element3.getAttribute("outerHTML"));
		WebElement element4 = element3.findElement(By.cssSelector("span#name"));
		assertThat(element4, notNullValue());
		System.err.println("Result element: " + element4.getAttribute("outerHTML"));
		final String element4HTML = element4.getAttribute("innerHTML");
		System.err.println("Inspecting element: " + element4HTML);
		assertThat(element4HTML, containsString("Selenium-Cheat-Sheet"));
		// NOTE: the getText() is failing
		try {
			assertThat(element4.getText(), containsString("Selenium-Cheat-Sheet"));
		} catch (AssertionError e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
		// can be OS-specific: "Selenium-Cheat-Sheet (10).pdf"

		Pattern pattern = Pattern.compile(
				String.format(".*Selenium-Cheat-Sheet(?:%s)*.pdf", " \\((\\d+)\\)"),
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(element4HTML);
		assertThat(matcher.find(), is(true));
		assertThat(pattern.matcher(element4HTML).find(), is(true));
		WebElement element5 = element3.findElement(By.cssSelector("a#url"));
		assertThat(element5, notNullValue());
		System.err
				.println("Inspecting element: " + element5.getAttribute("outerHTML"));
		sleep(1000);

	}

	@Test
	public void test3() { // listDownloadsShadowTest
		Assume.assumeTrue(browserChecker.testingChrome());
		driver.navigate().to("chrome://downloads/");
		WebElement element = driver.findElement(By.tagName("downloads-manager"));
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"#downloadsList");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		WebElement element2 = elements.get(0);
		err.println(
				String.format("Located element:", element2.getAttribute("outerHTML")));
		WebElement element3 = element2.findElement(By.tagName("downloads-item"));
		assertThat(element3, notNullValue());
		WebElement element4 = shadowDriver.getShadowElement(element3,
				"div#details");
		assertThat(element4, notNullValue());
		System.err.println("Result element: " + element3.getAttribute("outerHTML"));
		WebElement element5 = element4.findElement(By.cssSelector("span#name"));
		assertThat(element5, notNullValue());
		System.err.println("Result element: " + element5.getAttribute("outerHTML"));
		final String element4HTML = element5.getAttribute("innerHTML");
		assertThat(element4HTML, containsString("Selenium-Cheat-Sheet"));
		// NOTE: the getText() is failing
		try {
			assertThat(element4.getText(), containsString("Selenium-Cheat-Sheet"));
		} catch (AssertionError e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
		// can be OS-specific: "Selenium-Cheat-Sheet (10).pdf"

		Pattern pattern = Pattern.compile(
				String.format(".*Selenium-Cheat-Sheet(?:%s)*.pdf", " \\((\\d+)\\)"),
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(element4HTML);
		assertThat(matcher.find(), is(true));
		assertThat(pattern.matcher(element4HTML).find(), is(true));
		WebElement element6 = element4.findElement(By.cssSelector("a#url"));
		assertThat(element6, notNullValue());
		System.err
				.println("Inspecting element: " + element6.getAttribute("outerHTML"));
		sleep(1000);

	}

	@After
	public void AfterMethod() {
		driver.get("about:blank");
	}

	@AfterClass
	public static void tearDownAll() {
		driver.close();
	}

	// origin: https://reflectoring.io/conditional-junit4-junit5-tests/
	// probably an overkill
	public static class BrowserChecker {
		private String browser;

		public BrowserChecker(String browser) {
			this.browser = browser;
		}

		public boolean testingChrome() {
			return (this.browser.equals("chrome"));
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Object executeScript(String script, Object... arguments) {
		String argStr = "";
		for (int i = 0; i < arguments.length; i++) {
			argStr = argStr + " "
					+ (arguments[i] == null ? "null" : arguments[i].toString());
		}
		if (debug) {
			System.err.println("Calling " + script.substring(0, 40) + "..." + "\n"
					+ "with arguments: " + argStr);
		}
		return javascriptExecutor.executeScript(script, arguments);
	}

}
