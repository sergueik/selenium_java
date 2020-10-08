package com.cucumber.testng.examples;

import cucumber.api.Format;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Date;
import static org.testng.Assert.assertEquals;
/**
 * Created by amit.rawat on 21/12/15.
 */
public class DateStepdefs {
    private String result;
    private DateCalculator calculator;
    private Scenario scenario;

    @Before
    public void beforescenario(Scenario scenario){
        this.scenario = scenario;
    }

    @Given("^today is (.+)$")
    public void today_is(@Format("yyyy-MM-dd") Date date) {
        calculator = new DateCalculator(date);
        scenario.write("<b>This is test message</b>");
    }

    @When("^I ask if (.+) is in the past$")
    public void I_ask_if_date_is_in_the_past(Date date) {
        result = calculator.isDateInThePast(date);
    }

    @Then("^the result should be (.+)$")
    public void the_result_should_be(String expectedResult) {
        assertEquals(expectedResult, result);
    }
}
