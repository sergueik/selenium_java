package com.mycompany.app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
//  TODO:
// import org.openqa.selenium.phantomjs.PhantomJSDriver;
// import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

public class ProtractorDriver {

	public static WebDriver driver;
	public static NgWebDriver ngDriver;
	private static WebDriverWait wait;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	private static Actions actions;
	private static final Logger log = LogManager
			.getLogger(ProtractorDriver.class);
	private static int highlightInterval = 100;

	@Before
	public void init() throws MalformedURLException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		System.setProperty("webdriver.chrome.driver",
				"c:/java/selenium/chromedriver.exe");

		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");

		driver = new ChromeDriver(capabilities);
		ngDriver = new NgWebDriver(driver);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@After
	public void tearDown(Scenario scenario) {
		if (driver != null) {
			driver.quit();
		}
	}

	public static void waitForElementVisible(By locator) {
		log.info("Waiting for element visible for locator: {}", locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementVisible(By locator, long timeout) {
		log.info("Waiting for element visible for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator) {
		log.info("Waiting for element present  for locator: {}", locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForElementPresent(By locator, long timeout) {
		log.info("Waiting for element present for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForPageLoad() {
		log.info("Wait for page load via JS...");
		String state = "";
		int counter = 0;

		do {
			try {
				state = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
			log.info(("Browser state is: " + state));
		} while (!state.equalsIgnoreCase("complete") && counter < 20);

	}

	public static boolean isAttributePresent(By locator, String attribute) {
		log.info("Is Attribute Present for locator: {}, attribute: {}", locator,
				attribute);
		return ngDriver.findElement(locator).getAttribute(attribute) != null;
	}

	public static void selectDropdownByIndex(By locator, int index) {
		log.info("Select Dropdown for locator: {} and index: {}", locator, index);
		try {
			Select select = new Select(ngDriver.findElement(locator));
			select.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clickJS(By locator) {
		log.info("Clicking on locator via JS: {}", locator);
		wait.until(ExpectedConditions.elementToBeClickable(ngDriver
				.findElement(locator)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				ngDriver.findElement(locator).getWrappedElement());
	}

	public static void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", ngDriver.findElement(locator)
						.getWrappedElement());
	}

	public static void mouseOver(By locator) {
		log.info("Mouse over: {}", locator);
		actions.moveToElement(ngDriver.findElement(locator).getWrappedElement())
				.build().perform();
	}

	public static void click(By locator) {
		log.info("Clicking: {}", locator);
		ngDriver.findElement(locator).click();
	}

	public static void clear(By locator) {
		log.info("Clearing input: {}", locator);
		ngDriver.findElement(locator).clear();
	}

	public static void sendKeys(By locator, String text) {
		log.info("Typing \"{}\" into locator: {}", text, locator);
		ngDriver.findElement(locator).sendKeys(text);
	}

	public static String getText(By locator) {
		String text = ngDriver.findElement(locator).getText();
		log.info("The string at {} is: {}", locator, text);
		return text;
	}

	public static String getAttributeValue(By locator, String attribute) {
		String value = ngDriver.findElement(locator).getAttribute(attribute);
		log.info("The attribute \"{}\" value of {} is: {}", attribute, locator,
				value);
		return value;
	}

	public static boolean isElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return true;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return false;
		}
	}

	public static boolean isElementNotVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		}
	}

	public static String getBodyText() {
		log.info("Getting boby text");
		return ngDriver.findElement(By.tagName("body")).getText();
	}

	public static void highlight(By locator) throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = ngDriver.findElement(locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlightInterval);
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''",
				element);
	}
}
