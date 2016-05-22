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
  
  public void waitForDestinationPage(final String destinationPage) throws InterruptedException {
    // Wait for the destination page to load
   (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        // System.err.println( "url: " + d.getCurrentUrl());
        return (d.getCurrentUrl().contains((CharSequence )destinationPage));
      }
   });
    
  }  
}
