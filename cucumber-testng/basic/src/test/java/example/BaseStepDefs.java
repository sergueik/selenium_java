package example;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseStepDefs {
	@Before()
	public void before(Scenario scenario) {
		scenario.getId();
		System.out
				.println("This is before Scenario: " + scenario.getName().toString());
	}

	@After
	public void after(Scenario scenario) {
		System.out
				.println("This is after Scenario: " + scenario.getName().toString());
	}
}
