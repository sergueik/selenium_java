package org.cucumber.testRunners;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vimalselvam.cucumber.listener.ExtentProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.*;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features", glue = { "org.cucumber.stepDefinations" }, plugin = {
		"pretty", "html:target/cucumber-reports/cucumber-pretty",

		"json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/re-run.txt" })
public class TestRunnerWithJUnit {
	public static WebDriver driver;

	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sp369w\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

}
