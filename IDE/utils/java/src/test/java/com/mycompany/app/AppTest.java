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

  // this test contains extra debugging in the ExpectedConditions anonimous class and filter
  // this test is somewat redundant, used for debugging
  // validate in Firebug
  // $x("xpath to validate")
  // $$("cssSelector to validate")
	@Test(enabled = false)
	public void Test0() {

    // Arrange
    driver.get("http://suvian.in/selenium/1.1link.html");
    try{
      Thread.sleep(1000);
    } catch (InterruptedException e) {}

		String cssSelector = ".container .row .intro-message h3 a";
    String xpath = "//div[1]/div/div/div/div/h3[2]/a";
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(cssSelector))));
    List<WebElement> elements = driver.findElements(By.xpath(xpath));
		assertTrue(elements.size() > 0);

    // Act
    String linkText = "Click Here";
    WebElement element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
    element.click();

    linkText = "Link Successfully clicked";
    cssSelector = ".container .row .intro-message h3";
		String className = "intro-message";

    elements = driver.findElements(By.className(className));
    System.err.println("Text is: " + elements.get(0).getText());

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}

    // Assert
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

    element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
	}

	@Test(enabled = true)
	public void Test1() {

    // Arrange
    driver.get("http://suvian.in/selenium/1.1link.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
    List<WebElement> elements = driver.findElements(By.xpath("//div[1]/div/div/div/div/h3[2]/a"));
    assertTrue(elements.size() > 0);

    // Act
    WebElement element = elements.get(0);
    highlight(element);
    assertTrue(element.getText().equalsIgnoreCase("Click Here"), element.getText());
    element.click();

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
    } catch (UnreachableBrowserException e) {
    }
    // Inspect enclosing element to confirm the page loaded
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


	@Test(enabled = false)
	public void Test2() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.2text_field.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.2text_field_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test3() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.3age_plceholder.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.3age_plceholder_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test4() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.4gender_dropdown.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.4gender_dropdown_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test5() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.5married_radio.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.5married_radio_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test6() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.6checkbox.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.6checkbox_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test7() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.7button.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.7button_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test8() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.8copyAndPasteText.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.8copyAndPasteText_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test9() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.9copyAndPasteTextAdvanced.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.9copyAndPasteTextAdvanced_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test10() {
    // Arrange
    driver.get("http://suvian.in/selenium/1.10selectElementFromDD.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("1.10selectElementFromDD_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test11() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.1alert.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.1alert_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test12() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.2browserPopUp.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.2browserPopUp_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test13() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.3frame.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.3frame_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test14() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.4mouseHover.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.4mouseHover_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test15() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.5resize.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.5resize_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test16() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.6liCount.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.6liCount_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test17() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.7waitUntil.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.7waitUntil_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test18() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.8progressBar.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.8progressBar_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test19() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.9greenColorBlock.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.9greenColorBlock_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test20() {
    // Arrange
    driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("2.10dragAndDrop_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test21() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.1fileupload.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.1fileupload_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test22() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.2dragAndDrop_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test23() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.3javaemail.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.3javaemail_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test24() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.4readWriteExcel.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.4readWriteExcel_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test25() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.5cricketScorecard.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.5cricketScorecard_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test26() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.6copyTextFromTextField.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.6copyTextFromTextField_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test27() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.7correspondingRadio_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test28() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.8screeshotToEmail.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.8screeshotToEmail_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test29() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.9FacebookTest.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.9FacebookTest_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
	}

	@Test(enabled = false)
	public void Test30() {
    // Arrange
    driver.get("http://suvian.in/selenium/3.10select1stFriday.html");

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

    // Act

    // Wait page to load
    try {
      wait.until(ExpectedConditions.urlContains("3.10select1stFriday_validate.html"));
      } catch (UnreachableBrowserException e) {
      }
    // Assert
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
