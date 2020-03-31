package example;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParallelTest {

	private static final String USERNAME = "********";
	private static final String ACCESS_KEY = "********";
	private static final boolean mockup = true;
	private Long id = (long) -1;
	private WebDriver driver;

	@Parameters(value = { "browser", "version", "platform" })
	@BeforeClass
	public void setUp(String browser, String version, String platform)
			throws MalformedURLException, InterruptedException, WebDriverException {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("platform", platform);
		capability.setCapability("browserName", browser);
		capability.setCapability("browserVersion", version);
		capability.setCapability("project", "P1");
		capability.setCapability("build", "1.0");

		if (mockup) {
			id = Thread.currentThread().getId();
			System.err.println(
					String.format("Creating browser driver for %s %s %s on thread %d ",
							browser, version, platform, id));
		} else {
			driver = new RemoteWebDriver(new URL("http://" + USERNAME + ":"
					+ ACCESS_KEY + "@hub.browserstack.com/wd/hub"), capability);
		}
	}

	@Test
	public void test() throws Exception {
		if (mockup) {
			id = Thread.currentThread().getId();
			System.out.println("running test on thread: " + id);
		} else {
			driver.get("http://www.google.com");
			System.out.println("Page title is: " + driver.getTitle());
			Assert.assertEquals("Google", driver.getTitle());
			WebElement element = driver.findElement(By.name("q"));
			element.sendKeys("Browser Stack");
			element.submit();
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		if (driver != null) {
			driver.quit();
		}
	}
}
