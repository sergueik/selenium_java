package example.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.testng.ITestContext;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

import org.openqa.selenium.devtools.v94.browser.Browser;
import org.openqa.selenium.devtools.v94.browser.Browser.GetVersionResponse;
import org.openqa.selenium.devtools.v94.log.Log;
import org.openqa.selenium.devtools.v94.log.model.LogEntry;

public class BaseTest {

	protected WebDriver driver;
	protected DevTools devTools;
	protected Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private Map<String, Object> results = new HashMap<>();

	@Parameters({ "browser", "environment", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser,
			@Optional("grid") String environment, @Optional("LINUX") String platform,
			ITestContext ctx) {
		logger = LoggerFactory
				.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
		// driver = new BrowserDriverFactory(browser, log).createDriver();

		switch (environment) {
		case "local":
			results = new BrowserDriverFactory(browser, logger).createDriver();
			driver = (WebDriver) results.get("driver");
			devTools = (DevTools) results.get("devtools");
			if (devTools == null) {
				logger.info(
						"Failed to get devtools. java.lang.NullPointerException to follow");
			}
			break;
		case "grid":
			results = new GridFactory(browser, platform, logger).createDriver();
			driver = (WebDriver) results.get("driver");
			devTools = (DevTools) results.get("devtools");
			break;
		default:
			results = new BrowserDriverFactory(browser, logger).createDriver();
			driver = (WebDriver) results.get("driver");
			devTools = (DevTools) results.get("devtools");
			break;
		}
		devTools.createSessionIfThereIsNotOne();
		devTools.addListener(Log.entryAdded(), System.err::println);
		// TODO:
		// devTools.addListener(Log.entryAdded(), logger::info);

		GetVersionResponse response = devTools.send(Browser.getVersion());
		response.getUserAgent();
		logger.info("Browser Version : " + response.getProduct() + "\t"
				+ "Browser User Agent : " + response.getUserAgent() + "\t"
				+ "Browser Protocol Version : " + response.getProtocolVersion() + "\t"
				+ "Browser JS Version : " + response.getJsVersion());
		// driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		logger.info("Close driver");
		driver.quit();
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
