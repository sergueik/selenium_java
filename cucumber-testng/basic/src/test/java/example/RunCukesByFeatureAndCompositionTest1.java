package example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import edu.emory.mathcs.backport.java.util.Arrays;
import cucumber.api.testng.CucumberFeatureWrapper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IAttributes;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.annotations.IDataProvidable;

// loop over all features "src/test/resources/example/date_calculator1.feature"
// @CucumberOptions(features = "src/test/resources/example", tags = "@calculator")
@CucumberOptions(features = "src/test/resources/example/date_calculator1.feature", tags = "@calculator")
public class RunCukesByFeatureAndCompositionTest1 {
	private final boolean debug = true;
	private TestNGCucumberRunner runner;

	// TODO: do we need strongly typed class in the constructor argument?
	private final Class<? extends RunCukesByFeatureAndCompositionTest1> testClass = this
			.getClass();

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		runner = new TestNGCucumberRunner(testClass);
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "cucumberFeatures")
	public void featureTest(CucumberFeatureWrapper cucumberFeature) {
		runner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	// https://www.codota.com/code/java/classes/org.testng.internal.annotations.IDataProvidable
	@BeforeMethod
	// NOTE: adding the final IDataProvidable testAnnotation argument leads to the
	// org.testng.TestNGException exception
	// Method handleTestMethodInformation requires 3 parameters but 0 were
	// supplied in the @Configuration annotation.
	public void handleTestMethodInformation(final ITestContext context,
			/* final IDataProvidable testAnnotation, */ final Method method) {
		final String suiteName = context.getCurrentXmlTest().getSuite().getName();
		final String methodName = method.getName();
		final String testName = context.getCurrentXmlTest().getName();
		System.err.println(String.format(
				"BeforeMethod:\tSuite: \"%s\"\tTest: \"%s\"\tMethod: \"%s\"", suiteName,
				testName, methodName));
		/*
		String dataProvider = ((IDataProvidable) testAnnotation).getDataProvider();
		System.err.println("Data Provider of the method: " + dataProvider);
		*/
		@SuppressWarnings("deprecation")
		final Map<String, String> parameters = (((TestRunner) context).getTest())
				.getParameters();
		final Set<String> keys = parameters.keySet();
		if (keys.size() == 0) {
			System.err.println("Method has no parameters");
		} else {
			System.err.println("Method  parameters:");
			for (String key : keys) {
				System.err.print("\t" + key + " = " + parameters.get(key));
			}
			System.err.println("");
		}
		final Set<String> attributeNames = ((IAttributes) context)
				.getAttributeNames();
		if (attributeNames.size() > 0) {
			for (String attributeName : attributeNames) {
				System.err.print("BeforeMethod Attribute: " + attributeName + " = "
						+ ((IAttributes) context).getAttribute(attributeName));
			}
		}
	}

	// NOTE: need strongly typed data provider?
	@DataProvider
	public Object[][] cucumberFeatures() {
		final Object[][] features = runner.provideFeatures();
		if (debug) {
			int cnt = 0;
			for (cnt = 0; cnt != features.length; cnt++) {
				@SuppressWarnings("unchecked")
				List<Object> feature = Arrays.asList(features[cnt]);
				System.err.println("feature: " + feature);
			}
		}
		return features;
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		runner.finish();
	}
}
