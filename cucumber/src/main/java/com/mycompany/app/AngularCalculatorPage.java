package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

public class AngularCalculatorPage {

	private final By valueBox = NgBy.model("first");
	private final By button = NgBy.partialButtonText("Go"); // By.id("gobutton");
	private final By result = NgBy.binding("latest");
	private ProtractorDriver driver;

	public ProtractorDriver getDriver() {
		return driver;
	}

	public void setDriver(ProtractorDriver value) {
		driver = value;
	}

	public void isPageDisplayed(String url) {
		driver.ngDriver.get(url);
		driver.waitForElementVisible(valueBox);
	}

	public void enterValue(String model, String value) {
		driver.sendKeys(NgBy.model(model), value);
	}

	public void evaluateResult() {
		driver.click(button);
	}

	public String getDisplayedResult() throws InterruptedException {
		Thread.sleep(3000);
		driver.ngDriver.waitForAngular();
		driver.waitForElementVisible(result);
		driver.highlight(result);
		return driver.getText(result);
	}
}