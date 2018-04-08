package com.github.sergueik.selenium;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class DialogHTMLTest extends BaseTest {

	private static String baseURL = "https://codepen.io/keithjgrant/pen/eyMMVL";
	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseURL);
		wait.until(driver -> driver.getCurrentUrl()
				.matches(String.format("^%s.*", baseURL)));
		System.err.println("BeforeMethod: Current  URL: " + driver.getCurrentUrl());
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error(s) in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void testDialog() {
		// Arrange
		WebElement viewSwitchButton = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.id("view-switcher-button"))));
		assertThat(viewSwitchButton, notNullValue());

		flash(viewSwitchButton);
		viewSwitchButton.click();

		WebElement fullPageLinkButton = wait.until(ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//*[@id='full-page-link']"))));
		assertThat(viewSwitchButton, notNullValue());
		flash(fullPageLinkButton);
		fullPageLinkButton.click();
		String fullPageURL = "https://codepen.io/keithjgrant/full/eyMMVL/";
		wait.until(driver -> driver.getCurrentUrl()
				.matches(String.format("^%s.*", fullPageURL)));

		// Act

		WebElement frameElement = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("iframe#result")));
		assertThat(frameElement, notNullValue());
		String frameElementDocument = frameElement.getAttribute("outerHTML");

		int truncateSize = 800;
		System.err.println(
				"Switching to frame:\n" + ((frameElementDocument.length() > truncateSize
						? frameElementDocument.substring(0, truncateSize) + "..."
						: frameElementDocument).replaceAll("<", "\n<")));

		driver.switchTo().frame(frameElement);
		System.err.println("Inside the frame...");
		// Print the whole page
		WebElement body = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.tagName("body"))));
		assertThat(body, notNullValue());
		System.err.println("Page in hre frame: " + body.getAttribute("innerHTML"));

		// Assert
		// NOTE: can not find the element by id
		WebElement returnValueSpan = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//p[code]"))));
		assertThat(returnValueSpan, notNullValue());
		System.err.println(
				"Return value shown in: " + returnValueSpan.getAttribute("outerHTML"));

		WebElement openModalButton = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#open-modal"))));
		assertThat(openModalButton, notNullValue());
		flash(openModalButton);
		openModalButton.click();

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				String openAttribute = d.findElement(By.id("demo-modal"))
						.getAttribute("open");
				System.err.println("Dialog: open:" + openAttribute);
				Boolean result = Boolean.parseBoolean(openAttribute);
				return result;
			}
		});
		// Assert
		WebElement dialogModal = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.tagName("dialog"))));
		assertThat(dialogModal, notNullValue());
		System.err
				.println("Dialog element: " + dialogModal.getAttribute("outerHTML"));

		WebElement dialogButton = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#love-it"))));
		assertThat(dialogButton, notNullValue());
		System.err.println(
				"Clicking button with text: " + dialogButton.getAttribute("innerHTML"));
		flash(dialogButton);

		Map<String, String> textValues = new HashMap<>();
		textValues.put("I love it", "Love it");
		textValues.put("I like it", "Like it");
		/*
		 loveIt.addEventListener('click', function () {
		modal.close('Love it');
		});
		 */
		String checkText = textValues.get(dialogButton.getText());
		dialogButton.click();
		// TODO: flexible wait
		try {
			wait.until(
					(Function<? super WebDriver, Boolean>) (d -> !Boolean.parseBoolean(
							d.findElement(By.id("demo-modal")).getAttribute("open"))));
		} catch (UnreachableBrowserException e) {
		}

		// sleep(500);
		if (Boolean.parseBoolean(
				driver.findElement(By.id("demo-modal")).getAttribute("open"))) {
			//
		}

		// Assert
		// confirm the change of the page
		returnValueSpan = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//p[code][1]"))));
		assertThat(returnValueSpan, notNullValue());
		System.err.println(
				"Return value shown in: " + returnValueSpan.getAttribute("outerHTML"));
		System.err.println(returnValueSpan.getText());
		assertThat(returnValueSpan.getText(), containsString(checkText));
		//
		try {
			WebElement element = driver.findElement(By.id("return-value"));
			System.err.println("Span text: " + element.getText());
		} catch (Exception e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
		driver.switchTo().defaultContent();
	}

}
