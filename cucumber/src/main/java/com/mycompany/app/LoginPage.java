package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private static WebElement element = null;
	private static WebDriverWait wait;
	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver value) {
		driver = value;
	}

	public static WebElement account_Btn() {
		element = driver.findElement(By.cssSelector("div#account"));
		return element;
	}

	public static WebElement login_Btn() {
		element = driver.findElement(By.id("login"));

		return element;
	}

	public static WebElement log_Input() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("log")));
		element = driver.findElement(By.id("log"));
		return element;
	}

	public static WebElement pwd_Input() {
		element = driver.findElement(By.id("pwd"));
		return element;
	}

	public static WebElement wrong_Response_Message() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("response")));
		element = driver.findElement(By.className("response"));
		return element;
	}

	public static boolean MessageShown(String message) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(
				wrong_Response_Message(), message));
		return true;
	}

	public static String getLoginErrorMsg() {
		System.out.println("----------------"
				+ wrong_Response_Message().getText());
		return wrong_Response_Message().getText();
	}

	public static WebElement account_Login_Remind_Msg() {
		element = driver.findElement(By.className("myaccount"));
		return element;
	}
}