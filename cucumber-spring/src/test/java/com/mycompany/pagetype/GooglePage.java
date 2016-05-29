package com.mycompany.pagetype;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.mycompany.api.BrowserDriver;

@Component
public class GooglePage {

  private final By searchBox = By.id("lst-ib");
  private final By searchButton = By.xpath("//button[@class='lsb']");
  private final By resultStats = By.id("resultStats");

  public void isPageDisplayed() {
    BrowserDriver.driver.get("http://www.google.com/ncr");  // no country redirect
    BrowserDriver.waitForElementVisible(searchBox);
  }

  public void inputTextInSearchBox(String text) {
    BrowserDriver.driver.findElement(searchBox).sendKeys(text);
  }

  public void clickSearchButton() {
    BrowserDriver.waitForElementVisible(searchButton);
    BrowserDriver.driver.findElement(searchButton).click();
  }

  public void isResultPageDispalyed() {
    BrowserDriver.waitForElementVisible(resultStats, 5);
  }

  public String getBodyText() {
    return BrowserDriver.getBodyText();
  }
}
