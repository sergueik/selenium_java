package example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/test/resources/example/date_calculator2.feature", tags = "@calculator", plugin = "json:target/cucumber2.json")
public class RunCukesByFeatureAndCompositionTest2 {
	private TestNGCucumberRunner runner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		runner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "cucunberRunnerProvidedFeatures")
	public void getCucumberFeatureTest(CucumberFeatureWrapper cucumberFeature) {
		runner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] cucunberRunnerProvidedFeatures() {
		return runner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		runner.finish();
	}
}
