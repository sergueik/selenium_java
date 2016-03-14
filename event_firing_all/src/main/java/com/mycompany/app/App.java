package com.mycompany.app;

import static org.junit.Assert.assertTrue;
import java.awt.Toolkit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Hashtable;
import java.lang.RuntimeException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.mycompany.app.EventListenerType1;

public class App {

  private static WebDriver firefoxDriver;
  private static EventFiringWebDriver driver;
  private static EventListenerType1 listener;
  static String SiteURL = "http://www.tripadvisor.com/";
  static int implicit_wait_interval = 1;
  static int flexible_wait_interval = 5;
  static long wait_polling_interval = 500;
  static WebDriverWait wait;
  static Actions actions;

  private static final StringBuffer verificationErrors = new StringBuffer();

  @Before
  public static void setUp() throws Exception  {
    long implicit_wait_interval = 3;
    firefoxDriver = new FirefoxDriver();
    driver = new EventFiringWebDriver(firefoxDriver);
    listener = new EventListenerType1();
    driver.register(listener);
    driver.manage().timeouts().implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);

  }
  @Test
  public static void testVerifyText() { 
    driver.get(SiteURL);
    driver.findElement(By.className("nosuchclassName"));
  }

  public static void main(String[] args) throws Exception {
    setUp();
    testVerifyText();
    tearDown();
  }

  @After
  public static void tearDown() throws Exception
  {
    driver.unregister(listener);
    firefoxDriver.close();
    firefoxDriver.quit();
  }

}
