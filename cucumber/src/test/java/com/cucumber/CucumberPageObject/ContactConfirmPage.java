package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;

public class ContactConfirmPage extends AbstractPageStepDefinition {

  private String pageUrl = "contact_confirm.html";
  public ContactConfirmPage(){
    super();
      try {
      waitForCurrentUrl(pageUrl);
    } catch (InterruptedException e) { }
  } 

  public String getPageTitle()  {
    return driver.findElement(By.cssSelector(".content h1")).getText();
  }
}
