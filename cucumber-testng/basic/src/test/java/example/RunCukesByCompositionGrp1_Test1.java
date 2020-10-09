package example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/test/resources/example/date_calculator1.feature", tags = "@calculator", plugin = "json:target/cucumber1.json")
public class RunCukesByCompositionGrp1_Test1 {

	@SuppressWarnings("rawtypes")
	private final Class testClass = this.getClass();

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
	@Test(groups = "examples-testng", description = "Example of invoke Cucumber through TestNGCucumberRunner")
	public void testRunCukes() {
		new TestNGCucumberRunner(testClass).runCukes();
	}
}
