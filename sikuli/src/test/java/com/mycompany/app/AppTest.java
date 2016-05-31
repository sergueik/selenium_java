package com.mycompany.app;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.io.IOException;

import java.awt.Toolkit;
import java.io.IOException;
import java.lang.RuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.Boolean.*;

import org.hamcrest.CoreMatchers.*;
import org.junit.Assert.*;

// import org.testng.Assert;
// import org.testng.ITestContext;
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

// https://www.youtube.com/watch?v=8OfnQEfzfmw
public class AppTest {

  private final StringBuffer verificationErrors = new StringBuffer();
  private Logger logger = Logger.getLogger("com.mycompany.app.AppTest");

  private WebDriver driver;
  private long implicit_wait_interval = 3;
  private int flexible_wait_interval = 5;
  private long wait_polling_interval = 500;
  private int sikuliTimeout = 5;
  private WebDriverWait wait;
  private Actions actions;
  private String pagename = "upload.htm";

  private Screen screen;
  private String openButton = AppTest.class.getClassLoader().getResource("open.png").getPath();
  private String textBox = AppTest.class.getClassLoader().getResource("filename.png").getPath();
  private org.sikuli.script.Pattern filenameTextBox = new org.sikuli.script.Pattern(textBox);
  private File tmpFile;

  @Before
  public void setUp() throws Exception  {
    driver = new FirefoxDriver();
    wait = new WebDriverWait(driver, flexible_wait_interval );
    wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
    driver.manage().timeouts().implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
    String baseUrl = getPageContent( pagename);
    driver.navigate().to(baseUrl);

    // TODO:
    // String imageDir = context.getCurrentXmlTest().getParameter("image.dir");
    // String uploadDir = context.getCurrentXmlTest().getParameter("upload.dir");

    screen = new Screen();
    screen.setAutoWaitTimeout(5);
    org.sikuli.script.Pattern filenameTextBox = new org.sikuli.script.Pattern(textBox);
  }

  @Test
  public void testUpload()  throws Exception   {
    
    tmpFile = File.createTempFile("foo", ".png");
    WebElement element  = driver.findElement(By.tagName("input"));
    element.click();
    try {
      screen.exists(openButton, sikuliTimeout);
      screen.type(filenameTextBox, tmpFile.getCanonicalPath() );
      System.out.println("Uploading: " + tmpFile.getCanonicalPath() );
			screen.click(openButton, 0);
		} catch (FindFailed e) {
      verificationErrors.append(e.toString());
		}
    try {
      element  = driver.findElement(By.tagName("input"));
      highlight(element);
      assertThat(element.getAttribute("value"),containsString( tmpFile.getName() ));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }

    if (verificationErrors.length() != 0) {
      throw new Exception(verificationErrors.toString());
    }
  }

  @After
  public void tearDown()  throws Exception
  {
    driver.quit();
  }

  private String getPageContent(String pagename) {
    try {
      URI uri = AppTest.class.getClassLoader().getResource(pagename).toURI();			
      System.err.println("Testing: " + uri.toString());
      return uri.toString();
    } catch (URISyntaxException e) { // NOTE: multi-catch statement is not supported in -source 1.6
      throw new RuntimeException(e);
    }
  }

  private void highlight(WebElement element) throws InterruptedException {
    highlight(element, 100);
  }

  private void highlight(WebElement element, long highlight_interval) throws InterruptedException {
    wait.until(ExpectedConditions.visibilityOf(element));
    execute_script("arguments[0].style.border='3px solid yellow'", element);
    Thread.sleep(highlight_interval);
    execute_script("arguments[0].style.border=''", element);
  }

  public Object execute_script(String script,Object ... args){
    if (driver instanceof JavascriptExecutor) {
      JavascriptExecutor javascriptExecutor=(JavascriptExecutor)driver;
      return javascriptExecutor.executeScript(script,args);
    }
    else {
      throw new RuntimeException("Script execution is only available for WebDrivers that implement " + "the JavascriptExecutor interface.");
    }
  }
}
