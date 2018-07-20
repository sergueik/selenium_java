package jcucumberng.project.hooks;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.AfterStep;
import jcucumberng.framework.api.Selenium;

public class StepHook {
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public StepHook(ScenarioHook scenarioHook) {
		scenario = scenarioHook.getScenario();
		driver = scenarioHook.getDriver();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		Selenium.embedScreenshot(driver, scenario);
	}

}
