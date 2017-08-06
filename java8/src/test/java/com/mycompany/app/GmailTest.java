package com.mycompany.app;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.lang.reflect.Method;

public class GmailTest {

	private Alert alert;
	private WebDriver driver;
	private static Actions actions;
	private WebDriverWait wait;
	private int flexibleWait = 120;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseUrl = "https://www.google.com/gmail/about/#";
	private static final String browser = "chrome";
	private static String osName;
	private static TakesScreenshot screenshot;

	private By signInLink = By.xpath("//*[@data-g-label='Sign in']");
	// By.xpath("/html/body/nav/div/a[2]");
	private By identifier = By.cssSelector("#identifierId");
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

	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		getOsName();
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilepath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "target"
					+ System.getProperty("file.separator");
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("enableNetwork", "true");
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("allow-running-insecure-content");
			options.addArguments("allow-insecure-localhost");
			options.addArguments("enable-local-file-accesses");
			options.addArguments("disable-notifications");
			// options.addArguments("start-maximized");
			options.addArguments("browser.download.folderList=2");
			options.addArguments(
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
			options.addArguments("browser.download.dir=" + downloadFilepath);
			// options.addArguments("user-data-dir=/path/to/your/custom/profile");
			capabilities
					.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("firefox")) {

			System.setProperty("webdriver.gecko.driver",
					osName.toLowerCase().startsWith("windows")
							? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
							: "/tmp/geckodriver");
			System
					.setProperty("webdriver.firefox.bin",
							osName.toLowerCase().startsWith("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");
			// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// use legacy FirefoxDriver
			capabilities.setCapability("marionette", false);
			// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
			capabilities.setCapability("locationContextEnabled", false);
			capabilities.setCapability("acceptSslCerts", true);
			capabilities.setCapability("elementScrollBehavior", 1);
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setEnableNativeEvents(false);

			System.out.println(System.getProperty("user.dir"));
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			try {
				driver = new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				e.printStackTrace();
				throw new RuntimeException("Cannot initialize Firefox driver");
			}
		}
		actions = new Actions(driver);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		screenshot = ((TakesScreenshot) driver);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage(Method method) {
		driver.get(baseUrl);
		// Use the test name to create a specific screenshot folder path for every
		// test.
		String testName = method.getName();
		System.out.println("Test Name: " + testName + "\n");
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(priority = 1, enabled = false)
	public void invalidUsernameTest() throws InterruptedException, IOException {

		// click on Sign in link
		driver.findElement(signInLink).click();

		enterData(identifier, "InvalidUser_UVW");

		// click on next button
		clickNextButton(identifierNextButton);

		// Inspect error messages
		List<WebElement> errMsg = waitForElements(By.xpath(
				"//*[contains (text(), \"Couldn't find your Google Account\")]"));
		assertTrue(errMsg.size() > 0);
	}

	@Test(priority = 2, enabled = false)
	public void invalidPasswordTest() {

		driver.get("https://www.google.com/gmail/about/#");

		// Sign in
		driver.findElement(signInLink).click();
		sleep(1000);

		// Enter the email id
		enterData(identifier, "automationnewuser24@gmail.com");

		// click on next button
		clickNextButton(identifierNextButton);

		// Enter the password
		enterData(passwordInput, "InvalidPwd");

		// click on next button
		clickNextButton(passwordNextButton);

		// Inspect error messages
		List<WebElement> errMsg = waitForElements(
				By.xpath("//*[contains (text(),'Wrong password. Try again.')]"));
		assertTrue(errMsg.size() > 0);
	}

	@Test(priority = 3, enabled = false)
	public void loginTest() throws InterruptedException, IOException {

		// Click on Sign in Link
		driver.findElement(signInLink).click();

		// origin:
		// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection

		// Wait and track the for page url to change
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/signin.*");
		wait.until(urlChange);

		// Enter the email id
		enterData(identifier, "automationnewuser24@gmail.com");
		/*
		File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		FileUtils.copyFile(screenshotFile, new File("c:\\temp\\UserID.jpg"));
		*/

		// click on next button
		clickNextButton(identifierNextButton);

		// Enter the password
		enterData(passwordInput, "automationnewuser2410");

		// Click on next button
		clickNextButton(passwordNextButton);

		// Wait and track the for page url to change
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

		// Sign out
		highlight(driver.findElement(signOutButton), 100);
		driver.findElement(signOutButton).click();

		try {
			alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			System.err.println("NoAlertPresentException: " + ex.getStackTrace());
			return;
		} catch (WebDriverException ex) {
			System.err.println("Alert was not handled by PhantomJS: "
					+ ex.getStackTrace().toString());
			return;
		}
	}

	@Test(priority = 4, enabled = true)
	public void loginAfterTest() throws InterruptedException, IOException {

		// Click on Sign in Link
		driver.findElement(signInLink).click();

		// Wait and track the for page url to change
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/signin.*");
		wait.until(urlChange);

		// Enter the email id
		enterData(identifier, "automationnewuser24@gmail.com");

		// click on next button
		clickNextButton(identifierNextButton);

    		// Enter the password
		enterData(passwordInput, "automationnewuser2410");

		// Click on next button
		clickNextButton(passwordNextButton);

		// Wait and track the for page url to change
		urlChange = driver -> {
			String url = driver.getCurrentUrl();
			System.err.println("The url is: " + url);
			return (Boolean) url.matches("^https://mail.google.com/mail.*");
		};
		wait.until(urlChange);

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

		// Sign out
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

		// Wait and track the for page url to change
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
		WebElement svgSelector =
		// driver.findElement(By.cssSelector("div[role='button'] svg")).findElement(By.xpath(".."));
		driver.findElements(By.cssSelector("div[role='button'] svg[width='24px']")).get(0).findElement(By.xpath(".."));
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
		// Wait and track the for page url to change
		urlChange = driver -> driver.getCurrentUrl()
				.matches("^https://accounts.google.com/ServiceLogin/signinchooser.*");
		wait.until(urlChange);

		sleep(1000);
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

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}
}
