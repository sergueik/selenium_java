package com.mycompany.app;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber-html",
		"json:target/cucumber-json-report.json" }, features = { "src/test/resources/feature" }, glue = { "com.mycompany.app" }, tags = {
		"@test", "~@ignore" }, dryRun = false)
public class Runner {

}
