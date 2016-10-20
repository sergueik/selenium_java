package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	private static WebElement element = null;

	public static WebElement account_Btn(WebDriver driver) {
		element = driver.findElement(By.cssSelector("div#account"));
		return element;
	}

	public static WebElement login_Btn(WebDriver driver) {
		element = driver.findElement(By.id("login"));
    
		return element;
	}

	public static WebElement log_Input(WebDriver driver) {
		element = driver.findElement(By.id("log"));
		return element;
	}

	public static WebElement pwd_Input(WebDriver driver) {
		element = driver.findElement(By.id("pwd"));
		return element;
	}

	public static WebElement wrong_Response_Message(WebDriver driver) {
		element = driver.findElement(By.className("response"));
		return element;
	}

	public static String getLoginErrorMsg(WebDriver driver) {
		System.out.println("----------------"
				+ wrong_Response_Message(driver).getText());
		return wrong_Response_Message(driver).getText();
	}

	public static WebElement account_Login_Remind_Msg(WebDriver driver) {
		element = driver.findElement(By.className("myaccount"));
		return element;
	}
}
