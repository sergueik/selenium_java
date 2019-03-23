package org.cucumber.testRunners;

import java.util.List;

import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/Features/Login.feature" }, glue = {
		"org.cucumber.stepDefinations" }, plugin = { "pretty" })
public class GetToKnowAboutCurrentBDD {

	

	

}
