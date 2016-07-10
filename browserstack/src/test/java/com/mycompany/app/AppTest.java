package com.mycompany.app;

import java.net.URL;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest {

	private String platform;
	private String browserName;
	private String browserVersion;
	private static final String USERNAME = "********";
	private static final String ACCESS_KEY = "********";

	@Factory(dataProvider = "getBrowsers")
	public AppTest(String platform, String browserName, String browserVersion) {
		this.platform = platform;
		this.browserName = browserName;
		this.browserVersion = browserVersion;
	}

	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("platform", platform);
		capability.setCapability("browser", browserName);
		capability.setCapability("browserVersion", browserVersion);
		driver = new RemoteWebDriver(new URL(
				"http://USERNAME:ACCESS_KEY@hub.browserstack.com/wd/hub"), capability);
	}

	@Test
	public void test() throws Exception {
		driver.get("http://www.google.com");
		System.out.println("Page title is: " + driver.getTitle());
		Assert.assertEquals("Google", driver.getTitle());
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Browser Stack");
		element.submit();
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	@DataProvider(name = "getBrowsers")
	public static Object[][] createData1() {
		return new Object[][] { { Platform.WINDOWS.toString(), "chrome", "51" },
				{ Platform.XP.toString(), "firefox", "43" }, };
	}
}
