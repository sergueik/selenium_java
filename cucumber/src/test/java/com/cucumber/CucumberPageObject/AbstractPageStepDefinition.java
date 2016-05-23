package com.cucumber.CucumberPageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPageStepDefinition {
  
	protected static WebDriver driver;	
	protected WebDriver getDriver(){
		if (driver == null){
			System.setProperty("webdriver.chrome.driver", "C://java/selenium/chromedriver.exe");
			driver = new ChromeDriver();
		}
		return driver;
	}
  
  // Wait for the current url to get changed
  public void waitForCurrentUrl(final String expectedUrl) throws InterruptedException {
   (new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        System.err.println( "Expecting: " +  expectedUrl + "\n Current url: " + d.getCurrentUrl());
        return (d.getCurrentUrl().contains((CharSequence ) expectedUrl));
      }
   });
    
  }  
}
