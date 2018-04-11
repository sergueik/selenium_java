package jcucumberng.steps.defs;

import java.util.List;

import com.paulhammant.ngwebdriver.ByAngular;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import jcucumberng.steps.hooks.ScenarioHook;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

public class NetIncomeProjector {
	private static final Logger logger = LogManager.getLogger(NetIncomeProjector.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public NetIncomeProjector(ScenarioHook scenarioHook) {
		scenario = scenarioHook.getScenario();
		driver = scenarioHook.getDriver();
	}

	@When("^I Enter My Start Balance: (.*)$")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		Selenium.enterText(driver, "start.balance", startBalance);
		Selenium.embedScreenshot(driver, scenario);
	}

	@When("^I Enter My Regular Income Sources$")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) throws Throwable {
		List<Income> incomes = dataTable.asList(Income.class);
		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, "income.add.btn");
		}
		List<WebElement> incomeNameTextFields = driver.findElements(Selenium.getBy("income.name.txt"));
		List<WebElement> incomeAmountTextFields = driver.findElements(Selenium.getBy("income.amount.txt"));
		List<Select> incomeFreqDropMenus = Selenium.getSelectElements(driver, "income.freq.drop");
		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, incomeNameTextFields.get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, incomeAmountTextFields.get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, incomeFreqDropMenus.get(ctr), incomes.get(ctr).getFrequency());
		}
		Selenium.embedScreenshot(driver, scenario);
	}

	@When("^I Enter My Regular Expenses$")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) throws Throwable {
		List<Expense> expenses = dataTable.asList(Expense.class);
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, "expense.add.btn");
		}
		List<WebElement> expenseNameTextFields = driver.findElements(Selenium.getBy("expense.name.txt"));
		List<WebElement> expenseAmountTextFields = driver.findElements(Selenium.getBy("expense.amount.txt"));
		List<Select> expenseFreqDropMenus = Selenium.getSelectElements(driver, "expense.freq.drop");
		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, expenseNameTextFields.get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, expenseAmountTextFields.get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, expenseFreqDropMenus.get(ctr), expenses.get(ctr).getFrequency());
		}
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Net Income Per Month: (.*)$")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) {
		WebElement netPerMonthTd = driver.findElement(ByAngular.binding("roundDown(monthlyNet())"));
		String netPerMonthText = netPerMonthTd.getText();
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);
		logger.debug("Net Per Month: " + netPerMonthText);
		Selenium.scrollVertical(driver, 500);
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Net Income Per Year: (.*)$")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) {
		WebElement netPerYearTd = driver
				.findElement(ByAngular.binding("roundDown(monthlyNet()*12)+tallyTransactions()"));
		String netPerYearText = netPerYearTd.getText();
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);
		logger.debug("Net Per Year: " + netPerYearText);
		Selenium.scrollVertical(driver, 500);
		Selenium.embedScreenshot(driver, scenario);
	}

}
