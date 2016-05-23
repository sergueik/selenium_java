package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends AbstractPageStepDefinition{

	WebDriver driver = getDriver();
  private String pageUrl = "contact.html";

  public ContactPage(){
    super();
    try {
      waitForCurrentUrl(pageUrl);
    } catch (InterruptedException e) { }
  } 

  // method chaining 
  
  public ContactPage setNameField(String value) {
    driver.findElement(By.name("name_field")).sendKeys(value);
    return this;
  }

  public ContactPage setAddressField(String value) {
    driver.findElement(By.name("address_field")).sendKeys(value);
    return this;
  }

  public ContactPage setPostCodeField(String value) {
    driver.findElement(By.name("postcode_field")).sendKeys(value);
    return this;
  }

  public ContactPage setEmailField(String value) {
    driver.findElement(By.name("email_field")).sendKeys(value);
    return this;
  }

  public ContactConfirmPage submitForm() {
    driver.findElement(By.id("submit_message")).click();
    return new ContactConfirmPage();
  }
}
