package org.cucumber.testRunners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.vimalselvam.cucumber.listener.ExtentProperties;
//import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberTagStatement;
import gherkin.formatter.model.Tag;

@CucumberOptions(features = "src/test/resources/Features/Login.feature", glue = {
		"org.cucumber.stepDefinations" }, plugin = { /*
														 * "com.cucumber.listener.ExtentCucumberFormatter:output/report.html",
														 */ "pretty",
				/* "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", */
				"html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/CucumberTestReport.json",
				"rerun:target/cucumber-reports/re-run.txt" })
public class TestRunnerWithTestNG {
	public static WebDriver driver;
	private TestNGCucumberRunner testRunner;
	List<CucumberFeature> features;
	List<String> featuresList = new ArrayList<String>();
	// HashMap<String, List<Tag>> featuresTagsList = new HashMap<String,
	// List<Tag>>();
	// HashMap<String, List<CucumberTagStatement>> featureElementList = new
	// HashMap<String, List<CucumberTagStatement>>();

	List<String> scenariosList = new ArrayList<String>();
	List<String> scenarioOutlinesList = new ArrayList<String>();

	@BeforeSuite
	public void setUP() {
		// ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		// extentProperties.setReportPath("cucumberExtentReport.html");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sp369w\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		testRunner = new TestNGCucumberRunner(TestRunnerWithTestNG.class);

		features = testRunner.getFeatures();
		for (CucumberFeature f : features) {
			featuresList.add(f.getGherkinFeature().getName());
			// featuresTagsList.put(f.getGherkinFeature().getName(),
			// f.getGherkinFeature().getTags());
			// featureElementList.put(f.getGherkinFeature().getName(),
			// f.getFeatureElements());
			List<CucumberTagStatement> featureElementsList = f.getFeatureElements();
			for (CucumberTagStatement t : featureElementsList) {
				if (t.getVisualName().contains("Scenario:")) {
					scenariosList.add(t.getVisualName().replace("Scenario:", "").trim());
					System.out.println(t.getGherkinModel());
				}
				if (t.getVisualName().contains("Scenario Outline:")) {
					scenarioOutlinesList.add(t.getVisualName().replace("Scenario Outline:", "").trim());
				}
			}
		}

	}

	@Test(dataProvider = "features")
	public void login(CucumberFeatureWrapper cFeature) {
		testRunner.runCucumber(cFeature.getCucumberFeature());
	}

	@DataProvider(name = "features")
	public Object[][] getFeatures() {
		return testRunner.provideFeatures();
	}

	@AfterSuite
	public void tearDown() {
		// Reporter.loadXMLConfig(new File("extent-config.xml"));
		// Reporter.setSystemInfo("user", System.getProperty("user.name"));
		// Reporter.setSystemInfo("os", System.getProperty("os.name"));
		// Reporter.setSystemInfo("ip", System.getProperty("ip"));
		// Reporter.setTestRunnerOutput("Sample test runner output message");
		testRunner.finish();
		driver.quit();
	}

}
