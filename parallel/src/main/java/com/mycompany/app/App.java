package com.mycompany.app;
// example from 
// http://bharathautomation.blogspot.in/p/selenium-webdriver-faqs.html
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

      try {
        System.err.println("Thread: sleep 10 sec." );
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }    
        System.err.println("Thread: wake." );
			// With modal window, WebDriver appears to be hanging on [get current window handle]        
      String currentHandle = currentHandle = driver.getWindowHandle();
      System.err.println("Thread: Current Window handle" + currentHandle );
      while(true) {
        try {
					System.out.println("Thread: wait .5 sec");
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Thread: inspecting all Window handles" );        
        // when a modal window is created by Javascript window.showModalDialog
        // WebDriver appears to be hanging on [get current window handle], [get window handles]
        // Node console shows no Done: [get current window handle] or Done: [get window handles]
        // if the window is closed manually, and cleater again, the problem goes away
        windowHandles =  driver.getWindowHandles();
        if (windowHandles.size() > 1) {
					System.err.println("Found "  + (windowHandles.size() - 1 ) + " additional Windows");
					break;
        } else {
          System.out.println("Thread: no other Windows" );          
        }
      } 

      Iterator<String> windowHandleIterator =  windowHandles.iterator();
      while(windowHandleIterator.hasNext()) { 
        String handle = (String) windowHandleIterator.next();
				if (! handle.equals(currentHandle)){				
					System.out.println("Switch to " + handle);
					driver.switchTo().window(handle);
					// move, print attributes
					System.out.println("Switch to main window." );
					driver.switchTo().defaultContent();
				}
      }
      /*
      // the rest of example commented out
      String nextHandle = driver.getWindowHandle();
      System.out.println("nextHandle" + nextHandle);
      
      driver.findElement(By.xpath("//input[@type='button'][@value='Close']")).click();
     
      // Switch to main window
      for (String handle : driver.getWindowHandles()) {           
          driver.switchTo().window(handle);
      }
      // Accept alert
      driver.switchTo().alert().accept();
			*/
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

      new App();
      // non-modal windows are handled successfully.
			// driver.get("http://www.naukri.com/");
      driver.get("https://developer.mozilla.org/samples/domref/showModalDialog.html");
      driver.findElement(By.xpath("html/body/input")).click();
      System.out.println("main: sleeping 10 sec");
      
			Thread.sleep(20000);
      System.out.println("main: close");
      driver.close();
      driver.quit();

    }

}


