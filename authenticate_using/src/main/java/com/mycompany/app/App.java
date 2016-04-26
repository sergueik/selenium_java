package com.mycompany.app;
//  https://github.com/SeleniumHQ/selenium/blob/master/java/client/test/org/openqa/selenium/AuthenticatedPageLoadingTest.java

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.StringBuilder;

import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FilenameUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openqa.selenium.interactions.Actions;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import    org.openqa.selenium.Dimension;

import org.openqa.selenium.Platform;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.openqa.selenium.interactions.Actions;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Assert;
import static org.junit.Assert.*;

import org.openqa.selenium.Alert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;


public class App {

  static WebDriver driver;
  static WebDriverWait wait;
  static Alert alert;
  static int implicitWait = 10;
  static int flexibleWait = 5;
  static long pollingInterval = 500;

  
  static String username = "sergueik";
  static String password = "password";
  
  public static void main(String[] args) throws InterruptedException,java.io.IOException {

    System.out.println(System.getProperty("user.dir"));

    FirefoxProfile profile = new FirefoxProfile();
    DesiredCapabilities capabilities = new DesiredCapabilities();
    
    capabilities.setBrowserName("firefox");
    capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
    capabilities.setCapability(FirefoxDriver.PROFILE, profile);

    driver = new RemoteWebDriver(capabilities);
    driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, flexibleWait );
    wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);

    navigate();

    driver.close();
    driver.quit();

  }

  public static void navigate () throws InterruptedException,java.io.IOException  {

    // Go to URL
    // old style
    String url = "http://" + username + ":" + password + "@httpbin.org/basic-auth/" + username + "/" + password ;
    // new functionality
    url = "http://httpbin.org/basic-auth/" + username + "/" + password ;
    
    driver.get(url);
    alert = wait.until(alertIsPresent());
    Credentials credentials = new UserAndPassword(username, password);

    alert.authenticateUsing(credentials);

    // WebElement element = wait.until(presenceOfElementLocated(By.tagName("h1")));
    // assertEquals("authenticated", element.getText());    
  }
}

