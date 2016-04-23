package com.mycompany.app;
// example from 
// # http://bharathautomation.blogspot.in/p/selenium-webdriver-faqs.html
// has errors 
// import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import static java.lang.Boolean.*;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Ignore;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
// import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

//-------------
import static org.junit.Assert.assertTrue;
import java.awt.Toolkit;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class App implements Runnable{
    public static WebDriver driver;
    private static Set<String> windowHandles; 
    Thread thread;   
    App() throws InterruptedException{       
        thread = new Thread(this,"test");
        thread.start();
    }

    public void run(){      
      
      System.err.println("run");
      while(true) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // WebDriver appears to be hanging on [get current window handle]
        String currentHandle = driver.getWindowHandle();
        System.out.println("Current Window handle" + currentHandle );
        // WebDriver appears to be hanging on getWindowHandles
        windowHandles =  driver.getWindowHandles();
        System.err.println( "handles = " + windowHandles.size());
        if (windowHandles.size() > 1) {
        System.err.println("Switch to new Window");
        break;
        }
      } 

      Iterator<String> windowHandleIterator =  windowHandles.iterator();
      while(windowHandleIterator.hasNext()) { 
        String handle = (String) windowHandleIterator.next();
        System.out.println("Switch to" + handle);
        driver.switchTo().window(handle);
      }
       
      String nextHandle = driver.getWindowHandle();
      System.out.println("nextHandle" + nextHandle);
      
      driver.findElement(By.xpath("//input[@type='button'][@value='Close']")).click();
     
      // Switch to main window
      for (String handle : driver.getWindowHandles()) {           
          driver.switchTo().window(handle);
      }
      // Accept alert
      driver.switchTo().alert().accept();
    }
    
    public static void main(String args[]) throws InterruptedException,MalformedURLException{
      // ProfilesIni p=new ProfilesIni();
      // WebDriver hangs on navigation with Firefox 40 / Selenium 2.44 
      // driver=new FirefoxDriver(p.getProfile("default"));
      // only works with Firefox 
      DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "", Platform.ANY);
      FirefoxProfile profile = new ProfilesIni().getProfile("default");
      profile.setEnableNativeEvents(false);
      capabilities.setCapability("firefox_profile", profile);

      /*
      System.setProperty("webdriver.chrome.driver", "c:/java/selenium/chromedriver.exe");
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      LoggingPreferences logging_preferences = new LoggingPreferences();
      logging_preferences.enable(LogType.BROWSER, Level.ALL);
      capabilities.setCapability(CapabilityType.LOGGING_PREFS, logging_preferences);
      //  prefs.js:user_pref("extensions.logging.enabled", true);
      //  user.js:user_pref("extensions.logging.enabled", true);
      driver = new ChromeDriver(capabilities);
      */
      driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);

      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      // driver=new ChromeDriver();
      // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driver.get("https://developer.mozilla.org/samples/domref/showModalDialog.html");
      new App();
      driver.findElement(By.xpath("html/body/input")).click();
      // not reached 
      windowHandles =  driver.getWindowHandles();

      Iterator<String> windowHandleIterator =  windowHandles.iterator();
      while(windowHandleIterator.hasNext()) { 
      String handle = (String) windowHandleIterator.next();
          System.out.println("window Handle:" + handle);
         //  driver.switchTo().window(handle);
      }

      driver.close();
      driver.quit();

    }

}


