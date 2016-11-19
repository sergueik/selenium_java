package com.mycompany.app;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;
import java.io.InputStream;

import org.openqa.selenium.*;
import static org.openqa.selenium.By.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

import org.json.*;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

// NOTE: this project is not using jUnit annotations
// import static org.junit.Assert.assertThat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

// Java 8  part
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class AppTest {

	private FirefoxDriver driver;
	private WebDriverWait wait;
	private int flexibleWait = 5;
	static int implicit_wait_interval = 1;
	static int flexible_wait_interval = 5;
	static long wait_polling_interval = 500;
	private long pollingInterval = 500;
  private String baseUrl = "http://suvian.in/selenium";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
    // Address already in use: bind
    // temporarily using same project to two classes
		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseUrl);
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = false)
	public void Test0() {

    // Arrange
    driver.get("http://suvian.in/selenium/1.1link.html");
    try{
      Thread.sleep(1000);
    } catch (InterruptedException e) {}
    // Act

    // $x("//div[@class='intro-message']/h3[contains(text(), 'Link Successfully clicked')]")
    // $$(".container .row .intro-message h3 a")

		String cssSelector = ".container .row .intro-message h3 a";
    String xpath = "//div[1]/div/div/div/div/h3[2]/a";
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
				.cssSelector(cssSelector))));
    List<WebElement> elements = driver.findElements(By.xpath(xpath));
		assertTrue(elements.size() > 0);

    // Assert
    String linkText = "Click Here";
    WebElement element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
    // Act
    element.click();

    try{
      Thread.sleep(1000);
    } catch (InterruptedException e) {}
    linkText = "Link Successfully clicked";
    cssSelector = ".container .row .intro-message h3";
		String className = "intro-message";

    elements = driver.findElements(By.className(className));
    System.err.println("Text is: " + elements.get(0).getText());

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
			// TODO
		}

    try{
      (new WebDriverWait(driver, 5))
        .until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
            WebElement e = d.findElement(By.className("intro-message")) ;
            String t = e.getText();
              Boolean result = t.contains("Link Successfully clicked");
              System.err.println("in apply: Text = " + t + "\n result = " + result.toString());
              return result;
          }
        });
    } catch(Exception e) {
    }
    // http://stackoverflow.com/questions/12858972/how-can-i-ask-the-selenium-webdriver-to-wait-for-few-seconds-in-java
    // http://stackoverflow.com/questions/31102351/selenium-java-lambda-implementation-for-explicit-waits
    elements = driver.findElements(By.cssSelector(cssSelector));
    Stream<WebElement> elementsStream = elements.stream(); 			//convert list to stream
    elements = elementsStream.filter(o -> {
      System.err.println("in filter: Text = " + o.getText());
      return (Boolean) (o.getText().equalsIgnoreCase("Link Successfully clicked"));
      }).collect(Collectors.toList());	

		assertThat(elements.size(), equalTo(1));

    // Assert
    element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
    // Act
  	elements.forEach(System.out::println);			//output : spring node
	}

	@Test(enabled = true)
	public void Test1() {

    // Arrange
    driver.get("http://suvian.in/selenium/1.1link.html");

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
				.cssSelector(".container .row .intro-message h3 a"))));
    List<WebElement> elements = driver.findElements(By.xpath("//div[1]/div/div/div/div/h3[2]/a"));
		assertTrue(elements.size() > 0);

    // Act
    WebElement element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase("Click Here"), element.getText());
    element.click();

    // Wait page to load
    try{
      (new WebDriverWait(driver, 5))
        .until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
            return d.findElement(By.className("intro-message")).getText().contains("Link Successfully clicked");
          }
        });
    } catch(Exception e) {
    }

    // Assert
    elements = driver.findElements(By.cssSelector(".container .row .intro-message h3")).stream().filter(o ->o.getText().equalsIgnoreCase("Link Successfully clicked")).collect(Collectors.toList());	
		assertThat(elements.size(), equalTo(1));
    element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase("Link Successfully clicked"), element.getText());
  	elements.forEach(System.out::println);			// output :  ??
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexible_wait_interval);
		}
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);
    try{
      wait.until(ExpectedConditions.visibilityOf(element));
      if (driver instanceof JavascriptExecutor) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border='3px solid yellow'", element);
      }
        Thread.sleep(highlight_interval);
      if (driver instanceof JavascriptExecutor) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border=''", element);
      }
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

}
