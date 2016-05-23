package com.cucumber.CucumberPageObject;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonStepDefinition extends AbstractPageStepDefinition{
	WebDriver driver = getDriver();	
	LandingPage landingPage;
	
	@Then("^I close the Browser$")
	public void i_close_the_Browser() throws Throwable {
   driver.close();
   driver.quit();
	}

}
