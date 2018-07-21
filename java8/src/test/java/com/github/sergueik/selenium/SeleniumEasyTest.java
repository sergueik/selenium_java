package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class SeleniumEasyTest extends BaseTest {

	private static String baseURL = "http://www.seleniumeasy.com/test/javascript-alert-box-demo.html";
	private static final StringBuffer verificationErrors = new StringBuffer();

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseURL);
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches(String.format("^%s.*", baseURL));
		wait.until(urlChange);
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
	public void alertConfirmTest() {
		// wrong selector
		String buttonSelector = String.format(
				"//*[@id='easycont']//div[@class='panel-heading'][contains(normalize-space(.), '%s')]/..//button",
				"Java Script Confirm Box");
		try {
			WebElement buttonElement = wait
					.until(new ExpectedCondition<WebElement>() {
						Predicate<WebElement> textCheck = _element -> {
							String _text = _element.getText();
							System.err.println("in filter: Text = " + _text);
							return (Boolean) (_text.contains("Click me!"));
						};

						@Override
						public WebElement apply(WebDriver d) {
							System.err.println("Locating " + buttonSelector);
							Optional<WebElement> e = d.findElements(By.xpath(buttonSelector))
									.stream().filter(textCheck).findFirst();
							return (e.isPresent()) ? e.get() : (WebElement) null;
						}
					});

			System.err
					.println("Acting on: " + buttonElement.getAttribute("outerHTML"));
			highlight(buttonElement);
			flash(buttonElement);
			buttonElement.click();
			sleep(1000);
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append("Exception: " + e.toString());
		}
		try {
			// dismiss alert
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException ex) {
			// Alert not present - ignore
		} catch (WebDriverException ex) {
			System.err
					.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		assertThat(driver.findElement(By.id("confirm-demo")).getText(),
				is(equalTo("You pressed Cancel!")));
	}

	// https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
	@Test(enabled = true)
	public void alertWaitPresentTest() {

		WebElement buttonElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("button[onclick='myConfirmFunction()']")));
		System.err.println("Acting on: " + buttonElement.getAttribute("innerHTML"));
		highlight(buttonElement);
		flash(buttonElement);
		buttonElement.click();
		alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.dismiss();
		assertThat(driver.findElement(By.id("confirm-demo")).getText(),
				is(equalTo("You pressed Cancel!")));
	}

	@Test(enabled = true)
	public void alertTest() {
		WebElement buttonElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
						"//*[@id='easycont']//div[@class='panel-heading'][contains(normalize-space(.), '%s')]/..//button[contains(normalize-space(.), '%s')]",
						"Java Script Alert Box", "Click me!"))));
		System.err.println("Acting on: " + buttonElement.getAttribute("outerHTML"));
		highlight(buttonElement);
		flash(buttonElement);
		buttonElement.click();
		sleep(1000);
		try {
			// confirm alert
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present - ignore
		} catch (WebDriverException ex) {
			System.err
					.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
	}

	@Test(enabled = true)
	public void alertPromptTest() {
		String buttonText = "Click for Prompt Box";
		String buttonSelector = String.format(
				"//*[@id='easycont']//div[@class='panel-heading'][contains(normalize-space(.), '%s')]/..//button[contains(normalize-space(.), '%s')]",
				"Java Script Alert Box", buttonText);
		try {
			WebElement buttonElement = wait
					.until(new ExpectedCondition<WebElement>() {
						Predicate<WebElement> textCheck = _element -> {
							String _text = _element.getText();
							System.err.format("in filter: Text: \"%s\" expecting : \"%s\"\n",
									_text, buttonText);
							return (Boolean) (_text.contains(buttonText));
						};

						@Override
						public WebElement apply(WebDriver d) {
							System.err.println("Locating " + buttonSelector);
							Optional<WebElement> e = d.findElements(By.xpath(buttonSelector))
									.stream().filter(textCheck).findFirst();
							return (e.isPresent()) ? e.get() : (WebElement) null;
						}
					});

			System.err
					.println("Acting on: " + buttonElement.getAttribute("outerHTML"));
			highlight(buttonElement);
			flash(buttonElement);
			buttonElement.click();
			sleep(1000);
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append("Exception: " + e.toString());
		}
		try {
			// fill the data in the alert
			Alert alert = driver.switchTo().alert();
			System.err.println("In alert " + alert.toString());
			alert.sendKeys("Harry Potter");
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present - ignore
		} catch (WebDriverException ex) {
			System.err
					.println("Alert was not handled : " + ex.getStackTrace().toString());
			return;
		}
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.id("prompt-demo")));
		assertThat(
				wait.until(ExpectedConditions
						.visibilityOf(driver.findElement(By.id("prompt-demo")))).getText(),
				is(equalTo("You have entered 'Harry Potter' !")));
	}

}
