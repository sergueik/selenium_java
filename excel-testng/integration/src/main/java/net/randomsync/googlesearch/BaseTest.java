package net.randomsync.googlesearch;

import java.io.File;

import static java.io.File.separator;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();
	protected String testUrl, browser, hubUrl;

	@BeforeClass
	public void setUpBeforeClass(ITestContext context) throws Exception {
		testUrl = context.getSuite().getParameter("testUrl");
		browser = context.getSuite().getParameter("browser");
		hubUrl = context.getSuite().getParameter("hubUrl");
	}

	@BeforeMethod
	public void setUp() throws Exception {
		// BasicConfigurator.configure();
		if (hubUrl == null || hubUrl.trim().isEmpty()) {
			// if no hubUrl specified, run the tests on localhost
			if (browser == null || browser.trim().isEmpty()) {
				// if no browser specified, use IE
				driver = new InternetExplorerDriver();
			} else {
				if (browser.trim().equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
				} else if (browser.trim().equalsIgnoreCase("chrome")) {
					driver = new ChromeDriver();
				} else {
					driver = new InternetExplorerDriver();
				}
			}
		} else {
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setBrowserName(browser);
			driver = new RemoteWebDriver(new URL(hubUrl), capability);
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context)
			throws Exception {
		Throwable t = result.getThrowable();
		if (t instanceof WebDriverException || t instanceof AssertionError) {
			// System.out.println("WebDriver or Assert Exception");
			// get filename
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			// concat prefix with current time and return

			String filename = result.getTestClass().getName() + "."
					+ result.getMethod().getMethodName() + "." + sf.format(cal.getTime())
					+ ".png";
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File(context.getOutputDirectory() + separator + filename));
			Reporter.setCurrentTestResult(result);
			Reporter.log("<a href=\"" + filename + "\">Screenshot</a>");
		}
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}

	}

}
