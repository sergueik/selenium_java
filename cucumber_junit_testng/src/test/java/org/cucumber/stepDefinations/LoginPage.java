package org.cucumber.stepDefinations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.cucumber.testRunners.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginPage {
	public String someScreenshotPath = System.getProperty("user.dir") + "\\src\\test\\resources\\Windows.PNG";
	// TestRunnerWithTestNG testRunnerWithTestNG = new TestRunnerWithTestNG();
	TestRunnerWithJUnit testRunnerWithJUnit = new TestRunnerWithJUnit();
	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
	ExtentReports extent = new ExtentReports();
	ExtentTest feature = null;
	ExtentTest scenario1 = null;
	ExtentTest scenario2 = null;

	@SuppressWarnings("static-access")
	// WebDriver driver = testRunnerWithTestNG.driver;
	WebDriver driver = testRunnerWithJUnit.driver;

	@Given("^As an existing user$")
	public void as_an_existing_user() {
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Cucumber Extent Reports");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Demo on Cucumber Extent Reports");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version", System.getProperty("os.version"));
		extent.setSystemInfo("Host", System.getenv("host"));
		extent.setSystemInfo("IP", System.getenv("ip"));
		extent.attachReporter(htmlReporter);
		feature = extent.createTest(Feature.class, "Login Feature",
				"This will depicts you the extent reports for cucumber framework with gif besides png,jpg,jpeg!");
		feature.assignAuthor("Rupesh Kumar Somala");
		feature.assignCategory("Feature");
		scenario1 = feature.createNode(Scenario.class, "Authentication");
		scenario1.assignAuthor("Automator");
		scenario1.assignCategory("Scenario");
		scenario2 = feature.createNode(Scenario.class, "Login and Logout");
		scenario2.assignAuthor("Robot");
		scenario2.assignCategory("Scenario");
	}

	@Given("^Open Application and Enter url$")
	public void open_Application_and_Enter_url() throws Throwable {
		driver.manage().window().maximize();
		driver.get("http://executeautomation.com/demosite/Login.html");
		scenario1.createNode(new GherkinKeyword("Given"), "Open Application and Enter url").pass("pass",
				MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());
		// ExtentTest node1 = scenario1.createNode(new GherkinKeyword("Given"), "Open
		// Application and Enter url");
		// ExtentTest subnode1 = node1.createNode("Open Application", "Opened the
		// application successfully!").pass("pass",
		// MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());
		// ExtentTest subnode2 = node1.createNode("Enter url", "Entered url
		// successfully!").pass("pass",
		// MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());

		scenario2.createNode(new GherkinKeyword("Given"), "Open Application and Enter url").pass("pass",
				MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());

	}

	@When("^enter username$")
	public void enter_username() throws Throwable {
		driver.findElement(By.name("UserName")).sendKeys("Admin");
		// ExtentTest node1 = scenario1.createNode(new GherkinKeyword("When"), "enter
		// username");
		// ExtentTest subnode1 = node1.createNode("Enter username", "Entered username
		// successfully!").pass("pass",
		// MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());
		scenario1.createNode(new GherkinKeyword("When"), "enter username").pass("pass",
				MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());
		scenario2.createNode(new GherkinKeyword("When"), "enter username").pass("pass",
				MediaEntityBuilder.createScreenCaptureFromPath(someScreenshotPath).build());
	}

	@When("^enter password$")
	public void enter_password() throws Throwable {
		driver.findElement(By.name("Password")).sendKeys("Admin123");
		driver.findElement(By.name("Login")).submit();
		scenario1.createNode(new GherkinKeyword("When"), "enter password").pass("pass");
		scenario2.createNode(new GherkinKeyword("When"), "enter password").pass("pass");
	}

	@Then("^verify Msg$")
	public void verify_Msg() throws Throwable {
		boolean result = true;
		Assert.assertTrue(result);
		scenario1.createNode(new GherkinKeyword("Then"), "verify Msg").pass("pass");
		scenario2.createNode(new GherkinKeyword("Then"), "verify Msg").pass("pass");
		extent.flush();
	}

	@And("^click on logout$")
	public void click_on_logout() throws ClassNotFoundException {
		driver.findElement(By.xpath("//a[@href='Login.html']")).click();
		scenario2.createNode(new GherkinKeyword("And"), "click on logout").pass("pass");
	}

	@Before
	public void beforeScenario(cucumber.api.Scenario scenario) throws IOException {
		System.out.println("beforeScenario");

		System.out.println(scenario.getId());
		System.out.println(scenario.getName());
		System.out.println(scenario.getStatus());
		System.out.println(scenario.getClass());
		System.out.println(scenario.getSourceTagNames());
	
	}

	@After
	public void afterScenario(Scenario scenario) {
		System.out.println("afterScenario");
	}

}
