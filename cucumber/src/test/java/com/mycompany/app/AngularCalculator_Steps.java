package com.mycompany.app;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import cucumber.api.java.After;
import cucumber.api.java.Before;

// import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.assertThat;

import com.mycompany.app.AngularCalculatorPage;

/*
 * Author : Serguei Kouzmine
 */

import com.mycompany.app.ProtractorDriver;

public class AngularCalculator_Steps {

	private ProtractorDriver driver;

	@Before("@Angular")
	public void setup() {
		driver = new ProtractorDriver();
		AngularCalculatorPage.setDriver(driver);
	}

	@After("@Angular")
	public void tearDown() {
		driver.quit();
	}

	@Given("^I open angular page url \"(.*)\"$")
	public void I_open_angular_page_url(String url) throws InterruptedException {
		AngularCalculatorPage.isPageDisplayed(url);
	}

	@When("^I enter \"([^\"]*)\" into \"([^\"]*)\"$")
	public void I_enter_value(String value, String model)
			throws InterruptedException {
		AngularCalculatorPage.enterValue(model, value);
	}

	@When("^I evaluate result$")
	public void I_evaluate_result() throws InterruptedException {
		AngularCalculatorPage.evaluateResult();
		Thread.sleep(1000);
	}

	@Then("^I should get \"([^\"]*)\"$")
	public void i_should_get(String value) throws Throwable {
		assertThat(AngularCalculatorPage.getDisplayedResult(), containsString(value));
	}

}
