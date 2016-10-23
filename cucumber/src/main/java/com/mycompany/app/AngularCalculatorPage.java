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

	private static final By valueBox = NgBy.model("first");
	private static final By button = NgBy.partialButtonText("Go"); // By.id("gobutton");
	private static final By result = NgBy.binding("latest");
	private static ProtractorDriver driver;

	public static ProtractorDriver getDriver() {
		return driver;
	}

	public static void setDriver(ProtractorDriver value) {
		driver = value;
	}

	public static void isPageDisplayed(String url) {
		driver.ngDriver.get(url);
		driver.waitForElementVisible(valueBox);
	}

	public static void enterValue(String model, String value) {
		driver.sendKeys(NgBy.model(model), value);
	}

	public static void evaluateResult() {
		driver.click(button);
	}

	public static String getDisplayedResult() throws InterruptedException {
		Thread.sleep(3000);
		driver.ngDriver.waitForAngular();
		driver.waitForElementVisible(result);
		driver.highlight(result);
		return driver.getText(result);
	}
}