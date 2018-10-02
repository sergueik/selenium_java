package my.company.tests;

// import io.github.bonigarcia.wdm.ChromeDriverManager;

import java.io.File;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import my.company.steps.WebDriverSteps;

/**
 * @author Dmitry Baev charlie@yandex-team.ru Date: 28.10.13
 */
public class SearchTest {

	private WebDriverSteps steps;
  private static String osName = getOSName();

	@Before
	public void setUp() throws Exception {
    
    			System.setProperty("webdriver.gecko.driver", osName.equals("windows")
					? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
					: /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
					Paths.get(System.getProperty("user.home")).resolve("Downloads")
							.resolve("geckodriver").toAbsolutePath().toString());
			System
					.setProperty("webdriver.firefox.bin",
							osName.equals("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");
			// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities

		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
				Platform.ANY);


			capabilities.setCapability("marionette", false);

        WebDriver seleniumDriver = new FirefoxDriver(capabilities);
		// new ChromeDriver()
		// ChromeDriverManager.getInstance().setup();
		// java.lang.NoClassDefFoundError:
		// org/openqa/selenium/remote/service/DriverService$Builder
		steps = new WebDriverSteps(seleniumDriver);
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
