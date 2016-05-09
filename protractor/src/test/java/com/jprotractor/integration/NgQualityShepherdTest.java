package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
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

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * Purpose: implement Java equivalents of the Protractor test spec
 * https://github.com/qualityshepherd/protractor_example/specs/friendSpec.js
 * https://github.com/qualityshepherd/protractor_example/pages/friendPage.js
 * to practice difference between Java and Jasmine Test layout
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NgQualityShepherdTest {

  private static String fullStackTrace;
  private static NgWebDriver ngDriver;
  private static WebDriver seleniumDriver;
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
  private static String baseUrl = "http://qualityshepherd.com/angular/friends/";

  @BeforeClass
  public static void setup() throws IOException{
    isCIBuild = CommonFunctions.checkEnvironment();
    seleniumDriver = CommonFunctions.getSeleniumDriver();
    seleniumDriver.manage().window().setSize(new Dimension(width , height ));
    seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(seleniumDriver, flexibleWait );
    wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
    actions = new Actions(seleniumDriver);		
    ngDriver = new NgWebDriver(seleniumDriver);
  }

  @Before
  public void beforeEach() {
    ngDriver.navigate().to(baseUrl);
  }

  @Test
  public void testAddFriend() throws Exception {
    String friendName = "John Doe";
    int friendCount = ngDriver.findElements(NgBy.repeater("row in rows")).size();
    NgWebElement addnameBox = ngDriver.findElement(NgBy.model("addName"));
    assertThat(addnameBox,notNullValue());
    highlight(addnameBox, 100);
    addnameBox.sendKeys(friendName);
    NgWebElement addButton = ngDriver.findElement(NgBy.buttonText("+ add"));
    assertThat(addButton,notNullValue());
    highlight(addButton, 100);
    addButton.click();
    ngDriver.waitForAngular();
    assertThat(ngDriver.findElements(NgBy.repeater("row in rows")).size() - friendCount , equalTo(1));
    WebElement addedFriendElement = ngDriver.findElements(NgBy.cssContainingText("td.ng-binding",friendName)).get(0);
    assertThat(addedFriendElement,notNullValue());
    highlight(addedFriendElement, 100);
    System.err.println("Added friend name: " + addedFriendElement.getText());
  }

  @Test
  public void testSearchAndDeleteFriend() throws Exception {

    List<WebElement> names = ngDriver.findElements(NgBy.repeaterColumn("row in rows", "row"));
    String nameString = names.get(0).getText();
    assertFalse( nameString.isEmpty() );
    System.err.println("Search name: " + nameString);
    NgWebElement searchBox  = ngDriver.findElement(NgBy.model("search"));
    assertThat(searchBox,notNullValue());
    searchBox.sendKeys(nameString);
    Iterator<WebElement> elements = ngDriver.findElements(NgBy.repeater("row in rows")).iterator();
    while (elements.hasNext() ) {
      WebElement currentElement = (WebElement) elements.next();
      WebElement personName = new NgWebElement(ngDriver,currentElement).findElement(NgBy.binding("row"));
      System.err.println("Found name: " + personName.getText());
      if (personName.getText().indexOf(nameString) >= 0 ){
        WebElement deleteButton = currentElement.findElement(By.cssSelector("i.icon-trash"));
        highlight(deleteButton);
        deleteButton.click();
        ngDriver.waitForAngular();
      }
    }
    WebElement clearSearchBox = searchBox.findElement(By.xpath("..")).findElement(By.cssSelector("i.icon-remove"));
    assertThat(clearSearchBox,notNullValue());
    System.err.println("Clear Search");
    clearSearchBox.click();
    ngDriver.waitForAngular();
    elements = ngDriver.findElements(NgBy.repeater("row in rows")).iterator();
    while (elements.hasNext() ) {
      WebElement currentElement = (WebElement) elements.next();
      String currentName =  new NgWebElement(ngDriver, currentElement).evaluate("row").toString();
      System.err.println("Found name: " + currentName);
      assertTrue(currentName.indexOf(nameString) == -1);
    }
  }

  @Ignore
  @Test
  public void testRemoveAllFriends() throws Exception {
     ngDriver.waitForAngular();
     List <WebElement> elements = ngDriver.findElements(NgBy.repeater("row in rows"));
     assertTrue( elements.size() != 0 );
  }

  @AfterClass
  public static void teardown() {
    ngDriver.close();
    seleniumDriver.quit();		
  }

  private static void highlight(WebElement element) throws InterruptedException {
    highlight(element,  100);
  }

  private static void highlight(WebElement element, long highlightInterval ) throws InterruptedException {
    CommonFunctions.highlight(element, highlightInterval);
  }
}


