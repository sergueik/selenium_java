package example.base;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

	protected WebDriver driver;
	protected Logger logger = LoggerFactory.getLogger(BaseTest.class);

	@Parameters({ "browser", "environment", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, @Optional("grid") String environment,
			@Optional("LINUX") String platform, ITestContext ctx) {
		logger = LoggerFactory.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
		// driver = new BrowserDriverFactory(browser, log).createDriver();

		switch (environment) {
		case "local":
			driver = new BrowserDriverFactory(browser, logger).createDriver();
			break;
		case "grid":
			driver = new GridFactory(browser, platform, logger).createDriver();
			break;
		default:
			driver = new BrowserDriverFactory(browser, logger).createDriver();
			break;
		}
		driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		logger.info("Close driver");
		driver.quit();
	}
}
