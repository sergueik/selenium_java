package jcucumberng.project.stepdefs;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.framework.api.ConfigLoader;
import jcucumberng.project.hooks.ScenarioHook;

public class HomePageNavigationSteps {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageNavigationSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public HomePageNavigationSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@Given("I Am At The Home Page")
	public void I_Am_At_The_Home_Page() throws Throwable {
		String baseUrl = ConfigLoader.projectConf("base.url");
		driver.get(baseUrl);
		LOGGER.debug("Base URL=" + baseUrl);
	}

	@Then("I Should See Page Title: {string}")
	public void I_Should_See_Page_Title(String pageTitle) throws Throwable {
		String windowTitle = driver.getTitle();
		Assertions.assertThat(windowTitle).isEqualTo(pageTitle);
		LOGGER.debug("Window Title=" + windowTitle);
	}

}
