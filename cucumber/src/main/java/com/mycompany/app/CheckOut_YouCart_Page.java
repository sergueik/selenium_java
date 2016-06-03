package com.mycompany.app;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOut_YouCart_Page {
	private static WebElement element = null;
	
	public static WebElement CheckOut_Cart_Table(WebDriver driver){		
    return driver.findElement(By.className("checkout_cart"));
	}
	
	public static WebElement Product_Detail_Info(WebDriver driver, int row, int col){
		WebElement ShoppingCartTable = driver.findElement(By.className("checkout_cart"));
		List<WebElement> tableRows = ShoppingCartTable.findElements(By.tagName("tr"));
		List<WebElement> rowElements = tableRows.get(row).findElements(By.tagName("td"));
		element = rowElements.get(col);
		return element;
	}
	
	public static String getProductName(WebDriver driver){		
    int shoppingCart_productInfo_Row = 1;
    int shoppingCart_nameCol = 1;		
    return Product_Detail_Info(driver, shoppingCart_productInfo_Row, shoppingCart_nameCol).getText();
  }
	
	public static double getProductPrice(WebDriver driver){
		return Double.parseDouble(Product_Detail_Info(driver, 1, 1).getText().replace("$", ""));
	} 

  public static double getProductTotalPrice(WebDriver driver){
    return Double.parseDouble(Product_Detail_Info(driver, 1, 1).getText().replace("$", ""));
  } 	
	
  public static void continueCheckOut(WebDriver driver){
    Continue_Btn(driver).click();
  }
  public static WebElement Continue_Btn(WebDriver driver){
    return driver.findElement(By.className("step2"));
  }
}


