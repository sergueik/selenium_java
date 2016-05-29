package com.mycompany.pagetype;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.stereotype.Component;

import com.mycompany.api.BrowserDriver;

@Component
public class GooglePage {

  private final By _searchBox = By.name("q") ; 
  // By.id("lst-ib");
  private final By _searchButton = By.xpath("//button[@class='lsb']");
  private final By _resultStats = By.id("resultStats");

  public void isPageDisplayed(String url) {
    System.err.println(url);
    BrowserDriver.driver.get(url);  // no country redirect
    BrowserDriver.waitForElementVisible(_searchBox);
  }

  public void inputTextInSearchBox(String text) {
    WebElement queryText = BrowserDriver.driver.findElement(_searchBox);
    queryText.sendKeys(text);
  }

  public void clickSearchButton() {
    // NOTE: phantomjs -   Scenario: Verify user is able to search: Timed out after 5 seconds waiting for visibility of element located by By.xpath: //button[@class='lsb'](..)
    // WebElement queryText = BrowserDriver.driver.findElement(_searchBox);
    // queryText.sendKeys(Keys.RETURN);

    BrowserDriver.waitForElementVisible(_searchButton);
    BrowserDriver.driver.findElement(_searchButton).click();
  }

  public void isResultPageDispalyed() {
    BrowserDriver.waitForElementVisible(_resultStats, 5);
  }

  public String getBodyText() {
    return BrowserDriver.getBodyText();
  }
}
