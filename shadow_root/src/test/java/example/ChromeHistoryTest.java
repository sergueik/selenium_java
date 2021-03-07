package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

// Explore Chrome History setting screen

public class ChromeHistoryTest {

	private static boolean isCIBuild = BaseTest.checkEnvironment();

	private static final boolean debug = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("DEBUG", "false"));

	private final String site = "www.wikipedia.org";
	private static final String url = "chrome://history/";
	private static WebDriver driver = null;
	private static ShadowDriver shadowDriver = null;
	private static String browser = BaseTest.getPropertyEnv("BROWSER",
			BaseTest.getPropertyEnv("webdriver.driver", "chrome"));

	private static final BrowserChecker browserChecker = new BrowserChecker(
			browser);

	private static final boolean headless = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("HEADLESS", "false"));

	private List<WebElement> elements = new ArrayList<>();
	private WebElement element = null;
	private WebElement element2 = null;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setup() {
		if (browser.equals("chrome")) {
			System.err
					.println("Launching " + (headless ? " headless " : "") + browser);
			String chromeDriverPath = null;
			if (isCIBuild) {
				WebDriverManager.chromedriver().setup();
				chromeDriverPath = WebDriverManager.chromedriver()
						.getDownloadedDriverPath();

			} else {
				chromeDriverPath = Paths.get(System.getProperty("user.home"))
						.resolve("Downloads")
						.resolve(System.getProperty("os.name").toLowerCase()
								.startsWith("windows") ? "chromedriver.exe" : "chromedriver")
						.toAbsolutePath().toString();
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			}
			if (debug)
				System.err.println("Chrome Driver Path: " + chromeDriverPath);

			// https://peter.sh/experiments/chromium-command-line-switches/
			ChromeOptions options = new ChromeOptions();

			if (headless) {
				for (String arg : (new String[] { "headless",
						"window-size=1200x800" })) {
					options.addArguments(arg);
				}
			}
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.binary", chromeDriverPath);

			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new ChromeDriver(capabilities);
			shadowDriver = new ShadowDriver(driver);
			shadowDriver.setDebug(debug);
		}
	}

	@Before
	public void init() {
		if ((browser.equals("chrome") && !isCIBuild)) {
			driver.navigate().to("about:blank");
		}
	}

	// download PDF have to be run first
	// https://stackoverflow.com/questions/20295578/difference-between-before-beforeclass-beforeeach-and-beforeall/20295618
	@Before
	public void browse() {

		Assume.assumeTrue(browserChecker.testingChrome());
		Assume.assumeFalse(isCIBuild);
		driver.navigate().to(String.format("https://%s", site));
	}

	@Test
	public void test1() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		element = driver.findElement(By.cssSelector("#history-app"));
		elements = shadowDriver.getAllShadowElement(element,
				"#main-container #content");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(1) HTML: " + element.getAttribute("innerHTML"));
		element2 = element.findElement(By.cssSelector("#history"));

		elements = shadowDriver.getAllShadowElement(element2,
				".history-cards history-item");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(2) HTML: " + element2.getAttribute("outerHTML"));
		elements = shadowDriver.getAllShadowElement(element2, "#main-container");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(3) HTML: " + element.getAttribute("outerHTML"));
		elements = element.findElements(By.cssSelector("#date-accessed"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		assertThat(element2.getText(), containsString("Today"));
		System.err.println("Element(4) text: " + element2.getText());
		elements = element.findElements(By.cssSelector("#title-and-domain"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(5) HTML: " + element2.getAttribute("outerHTML"));
		elements = element2.findElements(By.cssSelector("#domain"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2.getText(), containsString(site));
		System.err.println("Element(6) text: " + element2.getText());
	}

	// @Ignore
	// browses through Shadow roots, but a wrong pick
	@Test
	public void test2() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		element = driver.findElement(By.cssSelector("#history-app"));
		elements = shadowDriver.getAllShadowElement(element,
				"#main-container #content");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(1) HTML: " + element.getAttribute("innerHTML"));
		element2 = element.findElement(By.cssSelector("#history"));

		elements = shadowDriver.getAllShadowElement(element2, ".history-cards");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(2) HTML: " + element2.getAttribute("outerHTML"));
		elements = shadowDriver.getAllShadowElement(element2, "#items");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(3) HTML: " + element.getAttribute("outerHTML"));
		// nowhere to continue
	}

	// NOTE: cannot use Assume in @Begin or @After
	@After
	public void AfterMethod() {
		if ((browser.equals("chrome") && !isCIBuild)) {
			driver.get("about:blank");
		}
	}

	@AfterClass
	public static void tearDownAll() {
		if (driver != null) {
			driver.close();
		}
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

}
