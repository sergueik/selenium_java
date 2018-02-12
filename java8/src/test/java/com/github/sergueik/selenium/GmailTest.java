package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class GmailTest extends BaseTest {

	private String baseURL = "https://www.google.com/gmail/about/#";

	private By signInLink = By.xpath("//*[@data-g-label='Sign in']");
	// By.xpath("/html/body/nav/div/a[2]");
	private By identifier = By.id("identifierId");
	private By identifierNextButton = By
			.xpath("//*[@id='identifierNext']/content/span[contains(text(),'Next')]");
	private By passwordInput = By.xpath("//*[@id='password']//input");
	private By passwordNextButton = By
			.xpath("//*[@id='passwordNext']/content/span[contains(text(),'Next')]");
	private By profileImage = By.xpath(
			"//a[contains(@href,'https://accounts.google.com/SignOutOptions')]");
	// By.xpath("//*[@id='gb']/div[1]/div[1]/div[2]/div[4]/div[1]/a/span");
	private By signOutButton = By.xpath(
			"//div[@aria-label='Account Information']//a[contains(text(), 'Sign out')][contains(@href, 'https://accounts.google.com/Logout?')]");
	// By.xpath(".//*[@id='gb_71']")

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
	}

	@BeforeMethod
	public void beforeMethod() {
		System.err.println("Navigate to URL: " + baseURL);
		driver.get(baseURL);
		// Wait for page url to change
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches(String.format("^%s.*", baseURL));
		wait.until(urlChange);
		System.err.println("Current  URL: " + driver.getCurrentUrl());
	}

	@Test(priority = 1, enabled = true)
	public void invalidUsernameTest() throws InterruptedException, IOException {

		// click on Sign in link
		driver.findElement(signInLink).click();

		// enter a non-existing account
		enterData(identifier, "InvalidUser_UVW");

		// Click on next button
		clickNextButton(identifierNextButton);

		// Inspect error messages
		List<WebElement> errMsg = waitForElements(By.xpath(
				"//*[contains (text(), \"Couldn't find your Google Account\")]"));
		assertTrue(errMsg.size() > 0);
	}

	@Test(priority = 2, enabled = true)
	public void invalidPasswordTest() {

		driver.get("https://www.google.com/gmail/about/#");

		// Sign in
		driver.findElement(signInLink).click();
		sleep(1000);

		// Enter existing email id
		enterData(identifier, "automationnewuser24@gmail.com");

		// Click on next button
		clickNextButton(identifierNextButton);

		// Enter the password
		enterData(passwordInput, "InvalidPwd");

		// Click on next button
		clickNextButton(passwordNextButton);

		// Inspect error messages
		List<WebElement> errMsg = waitForElements(
				By.xpath("//*[contains (text(),'Wrong password')]"));
		assertTrue(errMsg.size() > 0);
	}

	@Test(priority = 3, enabled = false)
	public void loginAfterTest() throws InterruptedException, IOException {

		// Click on Sign in Link
		System.err.println("Click on Sign in Link");
		driver.findElement(signInLink).click();

		// Wait for page url to change
		System.err.println("Wait for page url to change");
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/signin.*");
		wait.until(urlChange);

		// Enter the email id
		System.err.println("Enter the email id");
		enterData(identifier, "automationnewuser24@gmail.com");

		// Click on next button
		System.err.println("Click on next button");
		clickNextButton(identifierNextButton);

		// Enter the password
		System.err.println("Enter the password");
		enterData(passwordInput, "automationnewuser2410");

		// Click on next button
		System.err.println("Click on next button");
		clickNextButton(passwordNextButton);

		// Wait for page url to change
		urlChange = driver -> {
			String url = driver.getCurrentUrl();
			System.err.println("The url is: " + url);
			return (Boolean) url.matches("^https://mail.google.com/mail.*");
		};
		wait.until(urlChange);

		// Click on profile image
		System.err.println("Click on profile image");
		wait.until((WebDriver driver) -> {
			WebElement element = null;
			try {
				element = driver.findElement(profileImage);
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		}).click();

		// Sign out
		System.err.println("Sign out");
		highlight(driver.findElement(signOutButton), 100);
		driver.findElement(signOutButton).click();

		try {
			alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err.println("NoAlertPresentException: " + ex.getStackTrace());
		} catch (WebDriverException ex) {
			System.err.println("Alert was not handled by PhantomJS: "
					+ ex.getStackTrace().toString());
		}

		// Wait for page url to change
		System.err.println("Wait for page url to change");
		urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/signin.*");
		wait.until(urlChange);
		// Click on Choose user Link
		sleep(100);
		/*
		List<WebElement> x = driver.findElements(By.cssSelector("svg"));
		for (WebElement y : x) {      
		  System.err.println(y.getAttribute("outerHTML"));
		}
		*/
		System.err.println("Click on users list link");
		WebElement svgSelector =
				// driver.findElement(By.cssSelector("div[role='button']
				// svg")).findElement(By.xpath(".."));
				driver
						.findElements(
								By.cssSelector("div[role='button'] svg[width='24px']"))
						.get(0).findElement(By.xpath(".."));
		// System.err.println(svgSelector.findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).findElement(By.xpath("..")).getAttribute("outerHTML"));
		/*
		<?xml version="1.0"?>
		<div>
		<h1 class="sfYUmb" data-a11y-title-piece="" id="headingText" jsname="z6sL2b">Hi auto</h1>
		<div class="FgbZLd r5i3od">
		<img jsname="XpilHb" alt="" src="https://lh3.googleusercontent.com/-wFVXorRrucg/AAAAAAAAAAI/AAAAAAAAAAA/AMp5VUqt1um4XARGfeTINdbjBR3yAa_CpQ/mo/photo.jpg?sz=64" class="iarmfc"/>
		<div id="profileIdentifier" class="RRP0oc ilEhd" data-a11y-title-piece="">automationnewuser24@gmail.com</div>
		<div jscontroller="hgUmTc" jsaction="JIbuQc:BV9TTc(af8ijd)">
		  <div role="button" class="mUbCce fKz7Od YYBxpf KEavsb" jscontroller="VXdfxd" jsaction="click:cOuCgd; mousedown:UX7yZ; mouseup:lbsD7e; mouseenter:tfO1Yc; mouseleave:JywGue;touchstart:p6p2H; touchmove:FwuNnf; touchend:yfqBxc(preventMouseEvents=true|preventDefault=true); touchcancel:JMtRjd;focus:AHmuwe; blur:O22p3e; contextmenu:mg9Pef;" jsshadow="" jsname="af8ijd" aria-label="Switch account" aria-disabled="false" tabindex="0">
		    <div class="VTBa7b MbhUzd" jsname="ksKsZd"/>
		    <content class="xjKiLb">
		      <span style="top: -12px">
		        <span>
		          <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" width="24px" height="24px" viewBox="0 0 24 24" fill="#000000">
		            <path d="M7.41 7.84L12 12.42l4.59-4.58L18 9.25l-6 6-6-6z"/>
		            <path d="M0-.75h24v24H0z" fill="none"/>
		          </svg>
		        </span>
		      </span>
		    </content>
		  </div>
		</div>
		</div>
		</div>
		
		*/
		highlight(svgSelector, 100);
		svgSelector.click();
		// Wait for page url to change
		System.err.println("Wait for page url to change");
		urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/ServiceLogin/signinchooser.*");
		wait.until(urlChange);
		// TODO: examine cookies
		sleep(1000);
	}

	// the gmail of showing the identity confirmation page the test is not ready to handle
	@Test(priority = 4, enabled = false)
	public void loginTest() throws InterruptedException, IOException {

		// Click on Sign in Link
		driver.findElement(signInLink).click();

		// origin:
		// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection

		// Wait for page url to change
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/signin.*");
		wait.until(urlChange);
		// TODO: examine it landed on https://accounts.google.com/AccountChooser

		// Enter the email id
		enterData(identifier, "automationnewuser24@gmail.com");
		/*
		File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		FileUtils.copyFile(screenshotFile, new File("c:\\temp\\UserID.jpg"));
		*/

		// Click on next button
		clickNextButton(identifierNextButton);

		// Enter the password
		enterData(passwordInput, "automationnewuser2410");

		// Click on next button
		clickNextButton(passwordNextButton);

		// Wait for page url to change
		/*
		urlChange = driver -> {
			String url = driver.getCurrentUrl();
			System.err.println("The url is: " + url);
			return (Boolean) url.matches("^https://mail.google.com/mail.*");
		};
		wait.until(urlChange);
		 */

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if mail page is loaded...");
				// NOTE: in a realistic project this method
				// will belong to the page object
				// e.g.
				// https://github.com/pawnu/SeleniumQA/blob/master/EcommerceProject/RunTest.java
				return checkPage();
			}
		});

		// Wait until form is redndered, old semantics
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				System.err.println("Wait for form to finish rendering");
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				Boolean active = (Boolean) js
						.executeScript("return document.readyState == 'complete'");
				if (active) {
					System.err.println("Done");
				}
				return active;
			}
		});

		System.err.println("Click on profile image");
		// Click on profile image
		wait.until((WebDriver driver) -> {
			WebElement element = null;
			try {
				element = driver.findElement(profileImage);
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		}).click();

		// Wait until form is redndered, lambda semantics
		wait.until((WebDriver driver) -> {
			System.err.println("Wait for form to finish rendering");
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			Boolean active = (Boolean) js
					.executeScript("return document.readyState == 'complete'");
			if (active) {
				System.err.println("Done");
			}
			return active;
		});

		// Sign out
		System.err.println("Sign out");
		highlight(driver.findElement(signOutButton), 100);
		driver.findElement(signOutButton).click();

		try {
			alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err
					.println("NoAlertPresentException (ignored): " + ex.getStackTrace());
			return;
		} catch (WebDriverException ex) {
			System.err.println("Alert was not handled by PhantomJS: "
					+ ex.getStackTrace().toString());
			return;
		}
	}

	private void clickNextButton(By locator) {
		wait.until((WebDriver driver) -> {
			WebElement element = null;
			try {
				element = driver.findElement(locator);
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		}).click();
	}

	private Boolean checkPage() {
		return driver.getCurrentUrl().matches("^https://mail.google.com/mail.*");
	}

	private void enterData(By locator, String data) {
		wait.until((WebDriver driver) -> {
			WebElement element = null;
			try {
				element = driver.findElement(locator);
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		}).sendKeys(data);

	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private List<WebElement> waitForElements(By locator) {

		return wait.until((WebDriver d) -> {
			List<WebElement> elements = new ArrayList<>();
			try {
				elements = d.findElements(locator);
			} catch (Exception e) {
				return null;
			}
			return (elements.size() > 0) ? elements : null;
		});

	}

	@AfterMethod
	public void resetBrowser() {
		if (driver != null) {
			driver.get("about:blank");
		}
	}

}
