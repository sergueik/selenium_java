package com.jprotractor.integration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Local file Integration tests of using Protractor driver with non-Angular pages
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgIgnoreSyncTest {
  private static NgWebDriver ngDriver;
  private static WebDriver seleniumDriver;
  private static String fullStackTrace;
  static WebDriverWait wait;
  static Actions actions;
  static Alert alert;
  static int implicitWait = 10;
  static int flexibleWait = 5;
  static long pollingInterval = 500;
  static int width = 600;
  static int height = 400;
  // set to true for Desktop, false for headless browser testing
  static boolean isCIBuild =  false;
  public static String localFile;

  @BeforeClass
  public static void setup() throws IOException {
    isCIBuild = CommonFunctions.checkEnvironment();
    seleniumDriver = CommonFunctions.getSeleniumDriver();
    seleniumDriver.manage().window().setSize(new Dimension(width , height ));
    seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(seleniumDriver, flexibleWait );
    wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
    actions = new Actions(seleniumDriver);		
    ngDriver = new NgWebDriver(seleniumDriver);
  }
  
  // @Ignore
  @Test
  public void testNonAngular() {
    if (isCIBuild) {
      return;	
    }
    ngDriver.navigate().to("http://www.google.com");
    // expecting exception about to be thrown is browser-specific 
    
    try { 
      long startTime = System.currentTimeMillis();
      ngDriver.waitForAngular();
      // exception: window.angular is undefined
      long estimatedTime = System.currentTimeMillis() - startTime;			
      System.err.println("waitForAngular: " + estimatedTime);
      NgWebElement element = ngDriver.findElement(By.cssSelector("input#gs_htif0"));
      element.getAttribute("id");
    } catch (TimeoutException exception) { 
      System.err.println("TimeoutException thrown:");
      fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(exception);
      System.err.println("Exception thrown: " + fullStackTrace);
      return;
    }		
  }
  // @Ignore
  @Test
  public void testNonAngularIgnoreSync() {
    if (isCIBuild) {
      return;	
    }
    NgWebDriver ngDriver = new NgWebDriver(seleniumDriver, true);
    ngDriver.navigate().to("http://www.google.com");
    long startTime = System.currentTimeMillis();
    ngDriver.waitForAngular();
    long estimatedTime = System.currentTimeMillis() - startTime;
    System.err.println("waitForAngular: " + estimatedTime);
    NgWebElement element = ngDriver.findElement(By.cssSelector("input#gs_htif0"));
    try { 
      element.getAttribute("id");
    } catch (TimeoutException exception) { 
      fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(exception);
      System.err.println("TimeoutException thrown: " + fullStackTrace);
      return;
    }
  }

  @AfterClass
  public static void teardown() {
    ngDriver.close();
    seleniumDriver.quit();		
  }
}
