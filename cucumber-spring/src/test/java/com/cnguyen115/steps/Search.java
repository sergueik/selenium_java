package com.cnguyen115.steps;

import org.springframework.beans.factory.annotation.Autowired;

import com.cnguyen115.pagetype.Homepage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Search {

    private @Autowired Homepage homepage;

    @Given("^I am on Google homepage$")
    public void I_am_on_Google_homepage() {
        homepage.isHomepageDisplayed();
    }

    @When("^I search for \"(.*)\"$")
    public void I_search_for(String input) {
        homepage.inputTextInSearchBox(input);
        homepage.clickSearchButton();
    }

    @Then("^I verify the search results are displayed$")
    public void I_verify_the_search_results_are_displayed() throws InterruptedException {
        homepage.isResultPageDispalyed();
        Thread.sleep(5000); // To see result page
    }
}
