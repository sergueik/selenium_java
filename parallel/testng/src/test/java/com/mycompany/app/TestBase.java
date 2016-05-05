package com.mycompany.app;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

  protected ThreadLocal<RemoteWebDriver> threadDriver = null;

  @BeforeMethod
  public void setUp() throws MalformedURLException {
    threadDriver = new ThreadLocal<RemoteWebDriver>();
    String browserName = "firefox";
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platform", Platform.ANY.toString());
    capabilities.setCapability("browser", browserName);
    capabilities.setBrowserName(browserName);
    RemoteWebDriver driver = new RemoteWebDriver( new URL("http://localhost:4444/wd/hub"), capabilities );
    threadDriver.set(driver);
  }

  public WebDriver getDriver() {
    return threadDriver.get();
  }

  @AfterMethod
  public void closeBrowser() {
    getDriver().quit();
  }
}