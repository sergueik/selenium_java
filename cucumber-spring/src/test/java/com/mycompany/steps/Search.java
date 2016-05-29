package com.mycompany.steps;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import com.mycompany.pagetype.GooglePage;

// step definitions
public class Search {

  private @Autowired GooglePage _page;

  @Given("^I am on Google page$")
  public void I_am_on_Google_page() {
    _page.isPageDisplayed();
  }

  @When("^I search for \"(.*)\"$")
  public void I_search_for(String input) {
    _page.inputTextInSearchBox(input);
    _page.clickSearchButton();
  }

  @Then("^I verify the search results are displayed$")
  public void I_verify_the_search_results_are_displayed() throws InterruptedException {
    _page.isResultPageDispalyed();
  }
  
  @Then("^I should see \"([^\"]*)\"$")
  public void i_should_see(String arg1) throws Throwable {
    _page.isResultPageDispalyed();
    assertThat(_page.getBodyText(), containsString(arg1));
  }

}
