package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.mycompany.app.ParallelTest;

@RunWith(ParallelTest.class)
public class AppTest {
  
  private String platform;
  private String browserName;
  private String browserVersion;
  private String hubUrl = "http://127.0.0.1:4444/wd/hub" ; 
  // "http://key:secret@hub.testingbot.com/wd/hub";
  private WebDriver driver;

  @Parameterized.Parameters
  public static LinkedList<String[]> getEnvironments() throws Exception {
    LinkedList<String[]> env = new LinkedList<String[]>();
    env.add(new String[]{Platform.ANY.toString(), "chrome", "2"});
    env.add(new String[]{Platform.ANY.toString(),"firefox","2"});
    //add more browsers here
    //env.add(new String[]{Platform.WINDOWS.toString(),"ie","1"});
    return env;
  }

  public AppTest(String platform, String browserName, String browserVersion) {
    this.platform = platform;
    this.browserName = browserName;
    this.browserVersion = browserVersion;
  }


  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platform", platform);
    capabilities.setCapability("browser", browserName);
    capabilities.setCapability("browserVersion", browserVersion);
    driver = new RemoteWebDriver( new URL(hubUrl), capabilities );
  }
  
  @Test
  public void testSimple() throws Exception {
    driver.get("http://www.google.com");
    String title = driver.getTitle();
    System.out.println("Page title is: " + title);
    WebElement element = driver.findElement(By.name("q"));
    element.sendKeys("TestingBot");
    element.submit();
    driver = new Augmenter().augment(driver);
    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(srcFile, new File("Screenshot.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }
}