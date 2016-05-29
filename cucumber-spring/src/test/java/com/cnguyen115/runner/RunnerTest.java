package com.cnguyen115.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-html", "json:target/cucumber-json-report.json"}, 
        features = {"src/test/resources/feature"}, glue = {"com.cnguyen115"},
        tags = {"@test", "~@ignore"})

public class RunnerTest {
}
