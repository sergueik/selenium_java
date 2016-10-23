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

import com.mycompany.app.ProtractorDriver;

public class AngularCalculatorPage {

  private final By _valueBox = NgBy.model("first");
  private final By _goButton = NgBy.partialButtonText("Go");  // By.id("gobutton");
  private final By _result = NgBy.binding("latest");

  public void isAngularPagePageDisplayed(String url) {
    ProtractorDriver.ngDriver.get(url);
    ProtractorDriver.waitForElementVisible(_valueBox);
  }

  public void enterValue(String model, String value) {
    ProtractorDriver.sendKeys(NgBy.model(model), value);
  }

  public void evaluateResult() {
    ProtractorDriver.click(_goButton);
  }

  public String getDisplayedResult() throws InterruptedException{
    Thread.sleep(3000);
    ProtractorDriver.ngDriver.waitForAngular();
    ProtractorDriver.waitForElementVisible(_result);
    ProtractorDriver.highlight(_result);
    return ProtractorDriver.getText(_result);
  }

}
