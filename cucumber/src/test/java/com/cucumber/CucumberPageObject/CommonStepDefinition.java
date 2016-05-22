package com.cucumber.CucumberPageObject;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonStepDefinition extends AbstractPageStepDefinition{
	WebDriver driver = getDriver();	
	LandingPage landingPage;
	
	/*@When("^I click on \"([^\"]*)\"$")
	public void i_click_on(String link) throws Throwable {
	   
		driver.findElement(By.id(link+"_link")).click();
	 
	}
*/
	@Then("^I close the Brower$")
	public void i_close_the_Brower() throws Throwable {
   driver.close();
   driver.quit();
	}

}
