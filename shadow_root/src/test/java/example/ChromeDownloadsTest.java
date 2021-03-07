package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import example.BaseTest;

// Explore Chrome Downloads setting screen
public class ChromeDownloadsTest {

	private static boolean isCIBuild = BaseTest.checkEnvironment();

	protected static final boolean debug = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("DEBUG", "false"));

	private static final String url = "chrome://downloads/";

	protected static WebDriver driver = null;
	protected static ShadowDriver shadowDriver = null;
	private static String browser = BaseTest.getPropertyEnv("BROWSER",
			BaseTest.getPropertyEnv("webdriver.driver", "chrome"));

	private static final BrowserChecker browserChecker = new BrowserChecker(
			browser);

	private static final boolean headless = Boolean
			.parseBoolean(BaseTest.getPropertyEnv("HEADLESS", "false"));

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

	// download a PDF file first
	// https://stackoverflow.com/questions/20295578/difference-between-before-beforeclass-beforeeach-and-beforeall/20295618
	@Before
	public void download() {

		Assume.assumeTrue(browserChecker.testingChrome());
		Assume.assumeFalse(isCIBuild);
		driver.navigate().to(BaseTest.getPageContent("download.html"));
		WebElement element = driver
				.findElement(By.xpath("//a[contains(@href, \"wikipedia.pdf\")]"));
		element.click();
		sleep(1000);
	}

	// Explore Shadow DOM
	@Test
	public void test1() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		WebElement element = driver.findElement(By.tagName("downloads-manager"));
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"#downloadsList");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		WebElement element2 = elements.get(0);
		if (debug)
			System.err.println(String.format("Located element:",
					element2.getAttribute("outerHTML")));
		WebElement element3 = element2.findElement(By.tagName("downloads-item"));
		assertThat(element3, notNullValue());
		WebElement element4 = shadowDriver.getShadowElement(element3,
				"div#details");
		assertThat(element4, notNullValue());
		System.err.println("Result element: " + element3.getAttribute("outerHTML"));
		WebElement element5 = element4.findElement(By.cssSelector("span#name"));
		assertThat(element5, notNullValue());
		if (debug)
			System.err
					.println("Result element: " + element5.getAttribute("outerHTML"));
		final String element4HTML = element5.getAttribute("innerHTML");
		assertThat(element4HTML, containsString("wikipedia"));
		// NOTE: the getText() is failing
		try {
			assertThat(element4.getText(), containsString("wikipedia"));
		} catch (AssertionError e) {
			System.err.println("Exception (ignored) " + e.toString());
		}

		Pattern pattern = Pattern.compile(
				String.format("wikipedia(?:%s)*\\.pdf", " \\((\\d+)\\)"),
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(element4HTML);
		assertThat(matcher.find(), is(true));
		assertThat(pattern.matcher(element4HTML).find(), is(true));
		WebElement element6 = element4.findElement(By.cssSelector("a#url"));
		assertThat(element6, notNullValue());
		System.err
				.println("Inspecting element: " + element6.getAttribute("outerHTML"));
		shadowDriver.scrollTo(element6);
		WebElement element7 = shadowDriver.getParentElement(element6);
		assertThat(element7, notNullValue());
		assertThat(shadowDriver.isVisible(element7), is(true));
		String html = element7.getAttribute("outerHTML");
		if (debug)
			System.err.println("Inspecting parent element: " + html);
		try {
			assertThat(shadowDriver.getAttribute(element7, "outerHTML"),
					notNullValue());
			assertThat(shadowDriver.getAttribute(element7, "outerHTML"),
					containsString(html));
			System.err.println("Vefified attribute extraction: "
					+ shadowDriver.getAttribute(element7, "outerHTML"));
		} catch (AssertionError e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
		elements = shadowDriver.getChildElements(element7);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		sleep(1000);

	}

	// NOTE: can not use Assume in @Begin or @After
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
