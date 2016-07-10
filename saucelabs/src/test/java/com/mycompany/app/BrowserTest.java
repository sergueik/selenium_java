package com.mycompany.app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;

import java.net.URL;
import java.net.MalformedURLException;

import java.util.concurrent.TimeUnit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class BrowserTest {

	private String platform;
	private String browserName;
	private String browserVersion;

	private WebDriver driver;

	private static final String USERNAME = "********";
	private static final String ACCESS_KEY = "********";

	@Factory(dataProvider = "getBrowsers")
	public BrowserTest(String platform, String browserName, String browserVersion) {
		this.platform = platform;
		this.browserName = browserName;
		this.browserVersion = browserVersion;
	}

	@BeforeClass
	public void setUp() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("platform", platform);
		capability.setCapability("browserName", browserName);
		capability.setCapability("browserVersion", browserVersion);
		driver = new RemoteWebDriver(new URL(
				"http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub"),
				capability);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}

	@Test
	public void test() throws Exception {
		driver.get("http://www.amazon.com/");
		System.out.println("Page title is: " + driver.getTitle());
		assertEquals(
				"Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more",
				driver.getTitle());
		driver = new Augmenter().augment(driver);
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("Screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	@DataProvider(name = "getBrowsers")
	public static Object[][] createData1() {
		return new Object[][] { { Platform.WINDOWS.toString(), "chrome", "51" }, };
	}
}
