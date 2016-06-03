package com.mycompany.app;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOut_Info_Page {

  private static WebElement element = null;

  public static WebElement UserName_Input(WebDriver driver){
    WebElement form = driver.findElement(By.id("ajax_loginform"));		
    return form.findElement(By.id("log"));
  }	

  public static WebElement Password_Input(WebDriver driver){
    WebElement form = driver.findElement(By.id("ajax_loginform"));		
    return form.findElement(By.id("pwd"));
  }

  public static WebElement Login_Btn(WebDriver driver){
    return driver.findElement(By.id("login"));		
  }

  public static WebElement CheckOut_Tab1_Detail_Info(WebDriver driver, String tabName, int row, int col){
    WebElement checkTab1 = driver.findElement(By.className(tabName));	
    List<WebElement> tableRows = checkTab1.findElements(By.tagName("tr"));
    List<WebElement> rowElements = tableRows.get(row).findElements(By.tagName("td"));
    element = rowElements.get(col);
    return element;
  }

  public static WebElement Purchase_Btn(WebDriver driver){
    return driver.findElement(By.className("wpsc_make_purchase"));		
  }

  public static void login(WebDriver driver, String userName, String pwd){
    UserName_Input(driver).sendKeys(userName);
    Password_Input(driver).sendKeys(pwd);
    Login_Btn(driver).submit();
  }

  public static void clickPurchase(WebDriver driver){
    Purchase_Btn(driver).submit();
  }
		
}
