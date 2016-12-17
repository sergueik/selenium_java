package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class JqueryBarRatingTest {

	private FirefoxDriver driver;
	private WebDriverWait wait;
	static Actions actions;
	private Predicate<WebElement> hasClass;
	private Predicate<WebElement> hasAttr;
	private Predicate<WebElement> hasText;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
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

	@Test(enabled = true)
	public void test1() {
		// Arrange
		WebElement bar = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"section.section-examples div.examples div.row div.col div.box-example-square div.box-body div.br-theme-bars-square"))));

		// Act
		// NOTE: relative xpath selector
		assertTrue(bar
				.findElements(By.xpath("//a[@data-rating-value]")).size() > 7);
		// TODO: unique test
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		for (WebElement element : ratingElements) {
			System.err.println(
					"Rating element: " + element.getAttribute("data-rating-text"));
		}
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		//
		ratings.keySet().stream().forEach(o -> {
			System.err.println("Rating text: " + o);
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());

			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		});
		// Assert
	}

	@Test(enabled = true)
	public void test2() {
		// Arrange
		WebElement bar = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"div.examples div.row div.col div.box-example-reversed"))));
		// Act
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		ratings.keySet().stream().forEach(o -> {
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			// Assert
			WebElement comment = bar.findElement(By.xpath(".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]"));
      assertThat(comment.getText(), equalTo(o));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		});
		
		
		
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'", element);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

}
