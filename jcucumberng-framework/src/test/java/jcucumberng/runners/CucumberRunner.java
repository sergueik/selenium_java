package jcucumberng.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "src/test/resources/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng/steps/defs", "jcucumberng/steps/hooks" }, plugin = { "pretty",
				"html:target/cucumber-generated-reports/cucumber-report",
				"json:target/cucumber-generated-reports/cucumber-report.json",
				"junit:target/cucumber-generated-reports/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class CucumberRunner extends AbstractTestNGCucumberTests {
}

// For Eclipse IDE: Right-Click > Run As > TestNG Test

// TODO: Add testng.xml
// TODO: Support for IntelliJ
// TODO: Support for Jenkins
// TODO: Support for Selenium Grid
// TODO: Implement SLF4J
// TODO: Update README.md
