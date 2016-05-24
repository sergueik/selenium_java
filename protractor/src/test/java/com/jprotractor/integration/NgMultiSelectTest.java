package com.jprotractor.integration;

import org.apache.commons.lang.exception.ExceptionUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
 * Tests of Native AngularJS multiselect directive https://github.com/amitava82/angular-multiselect
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public class NgMultiSelectTest {
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
  private static String baseUrl = "http://amitava82.github.io/angular-multiselect/";

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
	public void testSelectOneByOne() throws Exception {
    NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
    assertThat(ng_directive, notNullValue());
    WebElement toggleSelect = ngDriver.findElement(NgBy.buttonText("Select Some Cars"));
    assertThat(toggleSelect, notNullValue());
    assertTrue(toggleSelect.isDisplayed());
    toggleSelect.click();    
    // NOTE: not "c.name for c in cars"
    List <WebElement> cars = ng_directive.findElements(NgBy.repeater("i in items")); 
    int cnt = 0;
    for (WebElement car: cars) {
      NgWebElement ng_car = new NgWebElement(ngDriver,car);
      assertThat(car, notNullValue());
      NgWebElement ng_carLabel = ng_car.findElement(NgBy.binding("i.label"));
      if (ng_carLabel.getText().matches("(?i:Audi|BMW|Honda)")){
        System.err.println( "* " + ng_car.evaluate("i.label").toString());
        highlight(car);
        car.click();
        cnt ++;
      }
    }
    cars = ng_directive.findElements(NgBy.repeater("i in items")); 
    assertThat(cars.size(), equalTo(cnt));
  }
  
	@Test
	public void testSelectAll() throws Exception {
    
    NgWebElement ng_directive = ngDriver.findElement(NgBy.model("selectedCar"));
    assertThat(ng_directive, notNullValue());
    WebElement toggleSelect = ng_directive.findElement(By.cssSelector("button[ng-click='toggleSelect()']"));
    assertThat(toggleSelect, notNullValue());
    assertTrue(toggleSelect.isDisplayed());
    toggleSelect.click();    
    wait.until(ExpectedConditions.visibilityOf(ng_directive.findElement(By.cssSelector("button[ng-click='checkAll()']"))));
    WebElement checkAll = ng_directive.findElement(By.cssSelector("button[ng-click='checkAll()']"));
    assertThat(checkAll, notNullValue());
    assertTrue(checkAll.isDisplayed());
    checkAll.click();
    // NOTE: not "c.name for c in cars"
    List <WebElement> cars = ng_directive.findElements(NgBy.repeater("i in items")); 
    int cnt = 0;
    for (WebElement car: cars) {
      assertTrue(car.getText().matches("(?i:Audi|BMW|Honda)"));
      Object ng_checked = new NgWebElement(ngDriver, car).evaluate("i.checked");
      assertTrue((boolean ) ng_checked );
      System.err.println( "* " + car.getText());
      cnt ++;
    }
    assertThat(cars.size(), equalTo(cnt));
 	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();		
	}

  private static void highlight(WebElement element) throws InterruptedException {
	  CommonFunctions.highlight(element);
  }

}


