package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;

public class LandingPage extends AbstractPageStepDefinition {

	public ContactPage navigateToContactPage(String link) {
		driver.findElement(By.id(link + "_link")).click();
		return new ContactPage();
	}

	public LandingPage navigateToWebApp() {
		driver.navigate().to("http://www.thetestroom.com/webapp");
		return new LandingPage();
	}
}
