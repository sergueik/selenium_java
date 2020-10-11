package example;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseStepDefs {
	@cucumber.api.java.Before()
	public void before(Scenario scenario) {
		scenario.getId();
		System.err
				.println("Before scenario: " + scenario.getName().toString());

		System.err.println("Tags: " + scenario.getSourceTagNames());

	}

	@cucumber.api.java.After()
	public void after(Scenario scenario) {
		System.err
				.println("After scenario: " + scenario.getName().toString());
	}
}
