package com.cucumber.testng.examples;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by amit.rawat on 06/01/16.
 */
@CucumberOptions(
        features = "src/test/resources/com.cucumber.testng.examples/date_calculator1.feature",
        tags = "@calculator",
        plugin = "json:target/cucumber2.json")
public class RunCukesByCompositionGrp1_Test2 {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("This is beforeSuite");
    }
    @BeforeClass
    public void beforeClass() {
        System.out.println("This is beforeClass");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("This is beforeMethod");
    }
    /**
     * Create one test method that will be invoked by TestNG and invoke the
     * Cucumber runner within that method.
     */
    @Test(groups = "examples-testng", description = "Example of using TestNGCucumberRunner to invoke Cucumber")
    public void runCukes() {
        new TestNGCucumberRunner(getClass()).runCukes();
    }
}
