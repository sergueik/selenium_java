package com.mycompany.app;

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
  
  public static void main(String[] args) throws InterruptedException,java.io.IOException {

    System.out.println(System.getProperty("user.dir"));

    FirefoxProfile profile = new FirefoxProfile();
    DesiredCapabilities capabilities = new DesiredCapabilities();
    
    capabilities.setBrowserName("firefox");
    capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
    capabilities.setCapability(FirefoxDriver.PROFILE, profile);

    driver = new RemoteWebDriver(capabilities);
    driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(120, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, flexibleWait );
    wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);

    inject();

    driver.close();
    driver.quit();

  }

  public static void inject () throws InterruptedException,java.io.IOException  {
    // Go to URL
    String url = "https://github.com/" ;    
    driver.get(url);
    String jquery_probe_script = "return this.$ === undefined;";
    boolean needInjection = (Boolean)(executeScript(jquery_probe_script)); 
    boolean inject_local_script = false;
    if (needInjection) {
      if (inject_local_script) {  
        // not tested
        try {
          URI resource_url = App.class.getClassLoader().getResource("jquery.js").toURI();	;
          String jquery_script = resource_url.toString( /* Charsets.UTF_8 */);
          executeAsyncScript(jquery_script);
        } catch (URISyntaxException e) { // NOTE: multi-catch statement is not supported in -source 1.6
          throw new RuntimeException(e);
        }
      } else { 
        String jquery_loader_script =
          "var jq = document.createElement('script');"+
          "jq.src = 'http://code.jquery.com/jquery-latest.min.js';"+
          "document.getElementsByTagName('head')[0].appendChild(jq);";
        executeAsyncScript(jquery_loader_script);
        // TODO: getting TimeoutException
      }
    }
    needInjection = (Boolean)(executeScript(jquery_probe_script));
    System.err.println("needInjection : " + needInjection );
  }
  
  private static Object  executeAsyncScript( String script,Object ... args){
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeAsyncScript(script,args);
		}
		else {
			throw new RuntimeException("cannot execute script.");
		}
	}	
  
  private static Object executeScript(String script,Object ... args){
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script,args);
		}
		else {
			throw new RuntimeException("cannot execute script.");
		}
	}
  
}

