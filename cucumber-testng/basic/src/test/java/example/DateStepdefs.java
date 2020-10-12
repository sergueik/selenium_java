package example;

import cucumber.api.DataTable;
import cucumber.api.Format;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.Transform;
import cucumber.api.Transformer;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import example.DateCalculator;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		scenario.write("Constructed date calculator for " + value.toString());
	}

	// NOTE: attmpt to use cucumber expression leads to
	// cucumber.runtime.CucumberException
	// java.lang.InstantiationException: example.DateStepdefs$DateMapper

	@When("^I ask if (.*) is in the past$")
	public void I_ask_if_date_is_in_the_past(
			/* @Transform(DateMapper.class) */ Date date) {
		scenario.write("Calling calculator with date: " + date.toString());
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
	@Then("^the result should be (?<expectedresult>yes|no)$")
	public void the_result_should_be(String data) {
		assertEquals(data, result);
	}

	@SuppressWarnings("deprecation")
	@Given("^today is the following date$")
	public void today_is_the_followng_date(DataTable inputDataTable) {
		List<List<String>> inputs = new ArrayList<>();
		List<String> input = new ArrayList<>();
		System.err.println("Reading input data table");
		try {
			// NOTE the presence of two methods: "asList" and " asLists"
			inputs = inputDataTable.asLists(String.class);
			System.err.println("Read input data " + inputs);
			input = inputDataTable.asList(String.class);
			System.err.println("Read input data " + input);
			System.err.println("Constructing date calculator for " + input.get(0));
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			System.err.println(String.format("Constructing date calculator for %s",
					format.parse(input.get(0))).toString());
			Date date = format.parse(input.get(0));
			// date = new Date(Date.parse(inputs.get(0).get(0)));
			calculator = new DateCalculator(date);
			scenario.write("Constructed date calculator for " + date.toString());
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString() + " " + e.getMessage());
		}
	}

}
