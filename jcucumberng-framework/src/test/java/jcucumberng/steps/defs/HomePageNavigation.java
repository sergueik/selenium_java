package jcucumberng.steps.defs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.PropsLoader;
import jcucumberng.api.Selenium;
import jcucumberng.steps.hooks.ScenarioHook;

public class HomePageNavigation {
	private static final Logger logger = LogManager.getLogger(HomePageNavigation.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public HomePageNavigation(ScenarioHook scenarioHook) {
		scenario = scenarioHook.getScenario();
		driver = scenarioHook.getDriver();
	}

	@Given("^I Am At The Home Page$")
	public void I_Am_At_The_Home_Page() throws Throwable {
		String baseUrl = PropsLoader.readConfig("base.url");
		logger.debug("Navigating to website: " + baseUrl);
		driver.get(baseUrl);
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Page Title '(.*)'$")
	public void I_Should_See_Page_Title(String pageTitle) {
		String windowTitle = driver.getTitle();
		logger.debug("Window Title: " + windowTitle);
		Assertions.assertThat(windowTitle).isEqualTo(pageTitle);
		Selenium.embedScreenshot(driver, scenario);
	}

}
