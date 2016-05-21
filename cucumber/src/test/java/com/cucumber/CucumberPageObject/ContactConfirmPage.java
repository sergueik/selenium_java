package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;

public class ContactConfirmPage extends AbstractPageStepDefinition {

	public String getPageTitle()  {
		return driver.findElement(By.cssSelector(".content h1")).getText();
	}
}
