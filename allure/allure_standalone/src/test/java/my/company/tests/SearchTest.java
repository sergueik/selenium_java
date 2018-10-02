package my.company.tests;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import my.company.steps.WebDriverSteps;

/**
 * based on example from  https://automated-testing.info/t/maven-allure-directory-allure-results-not-found/21598/16 - in Russian
 */
public class SearchTest {

	private static WebDriverSteps steps;
	public static RemoteWebDriver driver;
	private static String osName = getOSName();

	private static final String downloadFilepath = String.format("%s\\Downloads",
			osName.equals("windows") ? System.getenv("USERPROFILE")
					: System.getenv("HOME"));

	@SuppressWarnings("deprecation")
	@BeforeAll
	public static void setUp() throws Exception {

		System.setProperty("webdriver.gecko.driver", osName.equals("windows")
				? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
				: /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
				Paths.get(System.getProperty("user.home")).resolve("Downloads")
						.resolve("geckodriver").toAbsolutePath().toString());
		System.setProperty("webdriver.firefox.bin",
				osName.equals("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");

		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
				Platform.ANY);

		// NOTE: setting legacy protocol to "true" leads to an error
		// Missing 'marionetteProtocol' field in handshake
		capabilities.setCapability("marionette", false);

		driver = new FirefoxDriver(capabilities);
		steps = new WebDriverSteps(driver);
	}

	@Test
	public void searchTest() throws Exception {
		steps.openMainPage();
		steps.search("Allure framework");
		steps.makeScreenShot();
		steps.quit();
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
