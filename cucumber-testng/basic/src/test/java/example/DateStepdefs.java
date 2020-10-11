package example;

import cucumber.api.Format;
import cucumber.api.Scenario;
import cucumber.api.Transform;
import cucumber.api.Transformer;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import example.DateCalculator;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateStepdefs {
	private String result;
	private DateCalculator calculator;
	private Scenario scenario;

	@Before
	public void beforescenario(Scenario scenario) {
		this.scenario = scenario;
	}

	@Given("^today is (?<date>[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2})$")
	public void today_is(@Format("yyyy-MM-dd") Date value) {
		calculator = new DateCalculator(value);
		scenario.write("<b>This is test message</b>");
	}

	// NOTE: attmpt to use cucumber expression leads to
	// cucumber.runtime.CucumberException
	@When("^I ask if (.*) is in the past$")
	public void I_ask_if_date_is_in_the_past(
			@Transform(DateMapper.class) Date date) {
		result = calculator.isDateInThePast(date);
	}

	public class DateMapper extends Transformer<Date> {
		@SuppressWarnings("deprecation")
		@Override
		public Date transform(String data) {
			System.err.println("Transformimg data: " + data);
			try {
				return new Date(Date.parse(data));
			} catch (cucumber.runtime.CucumberException e) {
				System.err.println("Exception (ignored) " + e.toString());
				return null;
			}
		}
	}

	// NOTE: the role based naming may be beneficial here
	// alternatively a regex group, and cucumber expressions allow that
	@Then("^the result should be (?<expectedresult>.+)$")
	public void the_result_should_be(String data) {
		assertEquals(data, result);
	}

}
