package com.mycompany.app;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.SnippetType;

@RunWith(Cucumber.class)

  // @formatter:off
@CucumberOptions(
	plugin = {
		"pretty",
		"html:target/cucumber-html",
		"json:target/cucumber-json-report.json"
	}, 
	features = {
		"src/test/resources/features"
	}, 
	glue = {
		"com.mycompany"
	}, 
	tags = {
		"@test",
		"~@ignore"
	}, 
	dryRun = false)
	// @formatter:on

public class CucumberTest {
}
