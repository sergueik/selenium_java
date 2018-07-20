package jcucumberng.project.stepdefs;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.framework.api.Selenium;
import jcucumberng.project.domain.Transaction;
import jcucumberng.project.hooks.ScenarioHook;

public class NetIncomeProjectorSteps {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetIncomeProjectorSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@When("I Enter My Start Balance: {word}")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		Selenium.enterText(driver, startBalance, "start.balance.txt");
		LOGGER.debug("Start Balance=" + startBalance);
		this.scrollToDivBox(0);
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(DataTable table) throws Throwable {
		List<Transaction> txns = table.asList(Transaction.class);
		this.enterTransaction(txns, "income.add.btn", "income.name.txt", "income.amount.txt", "income.freq.select");
		this.scrollToDivBox(1);
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable table) throws Throwable {
		List<Transaction> txns = table.asList(Transaction.class);
		this.enterTransaction(txns, "expense.add.btn", "expense.name.txt", "expense.amount.txt", "expense.freq.select");
		this.scrollToDivBox(2);
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) throws Throwable {
		WebElement netPerMonthTd = driver.findElement(Selenium.by("net.per.month.td"));
		String netPerMonthText = netPerMonthTd.getText();
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);
		LOGGER.debug("Net Per Month=" + netPerMonthText);
		Selenium.scrollToElement(driver, netPerMonthTd);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) throws Throwable {
		WebElement netPerYearTd = driver.findElement(Selenium.by("net.per.year.td"));
		String netPerYearText = netPerYearTd.getText();
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);
		LOGGER.debug("Net Per Year=" + netPerYearText);
		Selenium.scrollToElement(driver, netPerYearTd);
	}

	private void enterTransaction(List<Transaction> txns, String addBtnKey, String nameFldKey, String amtFldKey,
			String freqSelKey) throws Throwable {
		// Click Add button
		for (int ctr = 0; ctr < txns.size() - 1; ctr++) {
			Selenium.clickElement(driver, addBtnKey);
		}
		// Enter details
		List<WebElement> nameFields = driver.findElements(Selenium.by(nameFldKey));
		List<WebElement> amtFields = driver.findElements(Selenium.by(amtFldKey));
		List<Select> freqSelects = Selenium.getSelectElements(driver, freqSelKey);
		for (int ctr = 0; ctr < txns.size(); ctr++) {
			Selenium.enterText(driver, txns.get(ctr).getName(), nameFields.get(ctr));
			Selenium.enterText(driver, txns.get(ctr).getAmount(), amtFields.get(ctr));
			freqSelects.get(ctr).selectByVisibleText(txns.get(ctr).getFrequency());
			LOGGER.debug(txns.get(ctr).toString());
		}
	}

	private void scrollToDivBox(int index) throws Throwable {
		List<WebElement> divBoxes = driver
				.findElements(new ByChained(Selenium.by("page.div.span7"), Selenium.by("page.div.box")));
		Selenium.scrollToElement(driver, divBoxes.get(index));
	}

}
