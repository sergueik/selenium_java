package com.cucumber.testng.examples;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Created by amit.rawat on 21/12/15.
 */
public class BaseStepDefs {
    @Before()
    public void before(Scenario scenario) {
        scenario.getId();
        System.out.println("This is before Scenario: " + scenario.getName().toString());
    }

    @After
    public void after(Scenario scenario) {
        System.out.println("This is after Scenario: " + scenario.getName().toString());
    }
}
