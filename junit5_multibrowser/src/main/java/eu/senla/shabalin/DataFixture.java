package eu.senla.shabalin;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DataFixture {
	protected static String correctEmail;
	public int scriptTimeout = 5;
	public int flexibleWait = 60;
	public int implicitWait = 1;
	public int pollingInterval = 500;
	protected static String correctPassword;
	protected static String incorrectPassword;
	protected static String baseUrl;
	protected static String authenticationFailedUrl = "http://automationpractice.com/index.php?controller=authentication";
	protected static String accountPage = "http://automationpractice.com/index.php?controller=my-account";
	private static final String hubUrl = "http://localhost:4444/wd/hub/";
	private static final Map<String, String> browserDrivers = new HashMap<>();
	protected static String osName = getOSName();
	static {
		browserDrivers.put("chrome", osName.equals("windows") ? "chromedriver.exe" : "chromedriver");
		browserDrivers.put("chromium", osName.equals("windows") ? null : "chromedriver");
		browserDrivers.put("firefox", osName.equals("windows") ? "geckodriver.exe" : "geckodriver");
		browserDrivers.put("edge", "MicrosoftWebDriver.exe");
	}

	protected RemoteWebDriver driver;

	@BeforeAll
	public static void beforeAllTest() {
		String propertyFileDirectory;
		if (System.getProperty("os.name").equals("Linux")) {
			propertyFileDirectory = "src/main/resources/testdata.properties";
		} else {
			propertyFileDirectory = "src\\main\\resources\\testdata.properties";
		}
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertyFileDirectory));
			correctEmail = properties.getProperty("correctEmail");
			correctPassword = properties.getProperty("correctPassword");
			incorrectPassword = properties.getProperty("incorrectPassword");
			baseUrl = properties.getProperty("baseUrl");
		} catch (IOException e) {
			System.err.println("Property file not found!");
			e.printStackTrace();
		}
//        Configuration.headless = true;
		Configuration.timeout = 8000;

		Configuration.remote = "http://localhost:4444/wd/hub/";
		Configuration.browserSize = "1920x1080";

	}

	public void setCapabilitiesByArguments(String browserName, String browserVersion) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browserName);
		// capabilities.setCapability("browserVersion", browserVersion);
		// capabilities.setCapability("enableVNC", true);
		Configuration.browser = browserName;
		Configuration.browserCapabilities = capabilities;
		try {
			if (browserName.equals("chrome") || browserName.equals("chromium")) {
				System.setProperty("webdriver.chrome.driver",
						osName.equals("windows") ? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
								: Paths.get(System.getProperty("user.home")).resolve("Downloads")
										.resolve("chromedriver").toAbsolutePath().toString());
			} else if (browserName.equals("firefox")) {

				// https://developer.mozilla.org/en-US/Firefox/Headless_mode
				// 3.5.3 and later
				System.setProperty("webdriver.gecko.driver",
						osName.equals("windows") ? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
								: Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("geckodriver")
										.toAbsolutePath().toString());
				System.setProperty("webdriver.firefox.bin",
						osName.equals("windows")
								? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe").getAbsolutePath()
								: "/usr/bin/firefox");
			}

			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
			driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, flexibleWait);
			wait.pollingEvery(Duration.ofMillis(pollingInterval));
			driver.navigate().to(baseUrl);
			wait.until(ExpectedConditions.titleContains("My Store"));
		} catch (MalformedURLException e) {
		}
	}

	static Stream<Arguments> browserArguments() {
		return Stream.of(arguments("chromium", "91.0")
		/* , arguments("chrome", "92.0") */
		/* , arguments("firefox", "90.0") */);
	}

	@AfterEach // (alwaysRun = true)
	public void afterTest() {
		if (driver != null) {
			try {
				driver.close();
				driver.quit();
			} catch (Exception e) {
			}
		}
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
}
