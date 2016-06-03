package se.iths.CucumberProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOut_Info_Final_Page {

	private static WebElement element = null;
	
	public static String pageTitle(WebDriver driver){
		return driver.getTitle();
	}	
	
	public static WebElement pageEmptyMsg(WebDriver driver){
		element = driver.findElement(By.className("entry-content"));
		return element;
	}
	public static String getEmptyPageMsg(WebDriver driver){
		return CheckOut_Info_Final_Page.pageEmptyMsg(driver).getText();
	}
}
