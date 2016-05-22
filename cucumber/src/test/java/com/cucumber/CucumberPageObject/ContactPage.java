package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends AbstractPageStepDefinition{

	WebDriver driver = getDriver();

  public ContactPage setNameField(String value) {
    driver.findElement(By.name("name_field")).sendKeys(value);
    return new ContactPage();
  }

  public ContactPage setAddressField(String value) {
    driver.findElement(By.name("address_field")).sendKeys(value);
    return new ContactPage();
  }

  public ContactPage setPostCodeField(String value) {
    driver.findElement(By.name("postcode_field")).sendKeys(value);
    return new ContactPage();
  }

  public ContactPage setEmailField(String value) {
    driver.findElement(By.name("email_field")).sendKeys(value);
    return new ContactPage();
  }

  public ContactConfirmPage submitForm() {
    driver.findElement(By.id("submit_message")).click();
    try {
      waitForDestinationPage("contact_confirm.html");
    } catch (InterruptedException e) { }
    return new ContactConfirmPage();
  }
}
