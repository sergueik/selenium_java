package my.company.tests;

// import io.github.bonigarcia.wdm.ChromeDriverManager;
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

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
				Platform.ANY);
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
}
