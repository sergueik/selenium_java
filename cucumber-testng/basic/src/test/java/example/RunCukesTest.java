package example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

// NOTE: annotation fragile against package rename 
// - eclipse unable to find the make changes to featue paths
@CucumberOptions(features = "src/test/resources/example/date_calculator1.feature", tags = "@calculator", format = {
		"pretty", "html:target/site/cucumber-pretty", "rerun:target/rerun.txt",
		"json:target/cucumber1.json" })
public class RunCukesTest extends AbstractTestNGCucumberTests {
}
