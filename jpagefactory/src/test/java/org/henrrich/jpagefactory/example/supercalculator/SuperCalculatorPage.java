package org.henrrich.jpagefactory.example.supercalculator;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by henrrich on 23/05/2016.
 */
public class SuperCalculatorPage {

	@FindBy(how = How.MODEL, using = "first")
	private WebElement firstNumber;

	// the @FindBy annotation below gives an example of defining different
	// locators for web and mobile channels, a simpler way is to do the same as
	// firstNumber input above
	@FindBy(howWeb = How.INPUT, usingWeb = "second", howMobile = How.XPATH, usingMobile = "//input[@ng-model='second']")
	private WebElement secondNumber;

	@FindBy(how = How.MODEL, using = "operator")
	private WebElement operatorSelect;

	@FindBy(how = How.ID, using = "gobutton")
	private WebElement goButton;

	@FindBy(how = How.BINDING, using = "latest")
	private WebElement latestResult;

	@FindAll({ @FindBy(how = How.REPEATER, using = "result in memory") })
	private List<WebElement> history;

	public void add(String first, String second) throws InterruptedException {
		firstNumber.clear();
		secondNumber.clear();

		firstNumber.sendKeys(first);
		secondNumber.sendKeys(second);

		goButton.click();

		TimeUnit.SECONDS.sleep(5);
	}

	public void times(String first, String second) throws InterruptedException {
		firstNumber.clear();
		secondNumber.clear();

		firstNumber.sendKeys(first);
		secondNumber.sendKeys(second);

		Select select = new Select(operatorSelect);
		select.selectByValue("MULTIPLICATION");

		goButton.click();
		TimeUnit.SECONDS.sleep(5);
	}

	public int getNumberOfHistoryEntries() {
		return history.size();
	}

	public String getLatestResult() {
		return latestResult.getText();
	}
}
