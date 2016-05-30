package com.mycompany.steps;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

// import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.assertThat;

import com.mycompany.pagetype.AngularPage;

// step definitions
public class AngularSearch {

  private @Autowired AngularPage _page;

  @Given("^I open angular page url \"(.*)\"$")
  public void I_open_angular_page_url(String url) throws InterruptedException {
    _page.isAngularPagePageDisplayed(url);
  }
  
  @When("^I enter \"([^\"]*)\" into \"([^\"]*)\"$")
  public void I_enter_value(String value, String model) throws InterruptedException {
    _page.enterValue(model, value) ; 
  }
  
  @When("^I evaluate result$")
  public void I_evaluate_result() throws InterruptedException {
    _page.evaluateResult() ; 
    Thread.sleep(1000);
  }
  
  @Then("^I should get \"([^\"]*)\"$")
  public void i_should_get(String value) throws Throwable {
    assertThat(_page.getDisplayedResult(), containsString(value));
  }

}
