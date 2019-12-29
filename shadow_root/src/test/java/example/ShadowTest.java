package example;

import static java.lang.System.err;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
/* 
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
*/
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import example.Shadow;

public class ShadowTest {

	private boolean debug = false;
	protected static String osName = getOSName();
	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome", osName.equals("windows") ? "chromedriver.exe" : "chromedriver");
		browserDrivers.put("firefox", osName.equals("windows") ? "geckodriver.exe" : "driver");
		browserDrivers.put("edge", "MicrosoftWebDriver.exe");
	}

	private static ChromeDriver driver = null;
	private static Shadow shadow = null;
	private static String browser = getPropertyEnv("webdriver.driver", "chrome");
	// use -P profile to override
	private static final boolean headless = Boolean.parseBoolean(getPropertyEnv("HEADLESS", "false"));

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

		System.setProperty("webdriver.chrome.driver",
				osName.equals("windows") ? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
						: Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("chromedriver")
								.toAbsolutePath().toString());

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// https://peter.sh/experiments/chromium-command-line-switches/
		ChromeOptions chromeOptions = new ChromeOptions();
		// options for headless
		if (headless) {
			for (String optionAgrument : (new String[] { "headless", "window-size=1200x800" })) {
				chromeOptions.addArguments(optionAgrument);
			}
		}

		driver = new ChromeDriver(chromeOptions);
		driver.navigate().to("https://www.virustotal.com");
		shadow = new Shadow(driver);
	}

	@Before
	public void init() {

	}

	@Test
	public void testApp() {

	}
	//	private static final String urlLocator = "a[data-route='url']";
	private static final String urlLocator = "*[data-route='url']";

	@Test
	public void testJSInjection() {
		WebElement element = shadow.findElement(urlLocator);
		System.out.println(element);
		// Assertions.assertEquals(new String(""), shadow.driver.getPageSource(),
		// "Message");
	}

	@Test
	public void testGetAllObject() {
		List<WebElement> element = shadow.findElements(urlLocator);
		System.out.println(element);
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
