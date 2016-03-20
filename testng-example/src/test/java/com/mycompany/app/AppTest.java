package com.mycompany.app;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.nio.charset.Charset;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

// import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.*;
import org.testng.annotations.*;

public class AppTest
{ 
  public RemoteWebDriver driver = null;
  public String seleniumHost = null;
  public String seleniumPort = null;
  public String seleniumBrowser = null;

  @BeforeMethod
  public void setupBeforeSuite( ITestContext context ) throws InterruptedException {
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();

    seleniumHost = context.getCurrentXmlTest().getParameter("selenium.host");
    seleniumPort = context.getCurrentXmlTest().getParameter("selenium.port");
    seleniumBrowser = context.getCurrentXmlTest().getParameter("selenium.browser");

    capabilities =   new DesiredCapabilities(seleniumBrowser, "", Platform.ANY);

    try {
      String hubUrl = "http://"+  seleniumHost  + ":" + seleniumPort   +  "/wd/hub";
      System.err.println(hubUrl); 
      driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
    } catch (MalformedURLException ex) { }

    try{
      driver.manage().window().setSize(new Dimension(600, 800));
      driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }  catch(Exception ex) {
      System.out.println(ex.toString());
    }
  }

  @AfterMethod
  public void cleanupSuite() {
    driver.close();
    driver.quit();
  }

  @Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 2, description="Finds a publication", dataProvider = "parseSearchResult")
  public void test1(String search_keyword, int expected) throws InterruptedException {

    driver.get("http://habrahabr.ru/search/?");
    WebDriverWait wait = new WebDriverWait(driver, 30);
    String search_input_name = null;
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inner_search_form")));
    search_input_name = "q";

    String search_input_xpath = String.format("//form[@id='inner_search_form']/input[@name='%s']", search_input_name);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(search_input_xpath)));
    WebElement element = driver.findElement(By.xpath(search_input_xpath));
    element.clear();
    element.sendKeys(search_keyword );
    element.sendKeys(Keys.RETURN);
    
    String pubsFoundCssSelector = "ul[class*='tabs-menu_habrahabr'] a[class*='tab-item tab-item_current']";
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pubsFoundCssSelector)));
    element = driver.findElement(By.cssSelector(pubsFoundCssSelector)); 
    Pattern pattern = Pattern.compile("(\\d+)");
    Matcher matcher = pattern.matcher(element.getText());
    int publicationsFound = 0;
    if (matcher.find()) {
      publicationsFound  = Integer.parseInt(matcher.group(1) ) ;
      System.err.println("Publication count " + publicationsFound );
    } else { 
      System.err.println("No publications");
    }
    assertTrue( publicationsFound >= expected );
  }

  @DataProvider(parallel = true)
  public Object[][] parseSearchResult() {
    return new Object[][]{
      {"junit", 100},
      {"testng", 30},
      {"spock", 10},
    };
  }

}