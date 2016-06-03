package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlertPopupPage {

	private static WebElement element = null;

	public static WebElement Alert_Meg(WebDriver driver){
		element = driver.findElement(By.id("fancy_notification_content"));
		return element;
	}
	
	public static WebElement popup_Btn(String btnLabel, WebDriver driver){		
    for(String winHandle :driver.getWindowHandles()){
      driver.switchTo().window(winHandle);
      if(driver.findElement(By.className(btnLabel)) != null){
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        element = driver.findElement(By.className(btnLabel));
      }
    }
    return element;
	}
}
