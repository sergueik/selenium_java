package example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.CucumberFeatureWrapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/test/resources/example/date_calculator1.feature", tags = "@calculator", plugin = "json:target/cucumber1.json")
public class RunCukesByFeatureAndCompositionTest1 {
	private TestNGCucumberRunner runner;

	// TODO: do we need strongly typed class in the constructor argument?
	private final Class<? extends RunCukesByFeatureAndCompositionTest1> testClass = this
			.getClass();

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		runner = new TestNGCucumberRunner(testClass);
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "cucunberRunnerProvidedFeatures")
	public void featureTest(CucumberFeatureWrapper cucumberFeature) {
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
