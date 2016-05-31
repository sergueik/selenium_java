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

import java.io.IOException;
import java.lang.RuntimeException;

import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.Boolean.*;

import org.hamcrest.CoreMatchers.*;
import org.junit.Assert.*;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.Pattern;

// https://www.youtube.com/watch?v=8OfnQEfzfmw
public class AppTest {

  private final StringBuffer verificationErrors = new StringBuffer();
  private Logger logger = Logger.getLogger("com.mycompany.app.AppTest");

  private WebDriver driver;
  private int sikuliTimeout = 5;
  private String pagename = "upload.htm";

  private Screen screen;
  private String openButtonImage = AppTest.class.getClassLoader().getResource("open.png").getPath();
  private String filenameTextBoxImage = AppTest.class.getClassLoader().getResource("filename.png").getPath();
  private Pattern filenameTextBox = new Pattern(filenameTextBoxImage );
  private File tmpFile;

  @Before
  public void setUp() throws Exception  {
    driver = new FirefoxDriver();
    driver.navigate().to(getPageContent( pagename));
    screen = new Screen();
    screen.setAutoWaitTimeout(5);
  }

  @Test
  public void testUpload()  throws Exception   {
    
    tmpFile = File.createTempFile("foo", ".png");
    WebElement element  = driver.findElement(By.tagName("input"));
    element.click();
    try {
      screen.exists(openButtonImage, sikuliTimeout);
      screen.type(filenameTextBox, tmpFile.getCanonicalPath() );
      System.out.println("Uploading: " + tmpFile.getCanonicalPath() );
      screen.click(openButtonImage, 0);
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
    WebDriverWait wait = new WebDriverWait(driver, 3 );
    wait.pollingEvery(500, TimeUnit.MILLISECONDS);
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
