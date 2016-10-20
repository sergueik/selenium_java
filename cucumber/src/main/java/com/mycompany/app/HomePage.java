package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private static WebElement element = null;

	public static String pageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public static WebElement search_Input(WebDriver driver) {
		element = driver.findElement(By.className("search"));
		return element;
	}

	public static WebElement account_Btn(WebDriver driver) {
		// element = driver.findElement(By.id("account"));
    element = driver.findElement(By.cssSelector("div#account"));
		return element;
	}

	public static WebElement accountOut_Btn(WebDriver driver) {
		element = driver.findElement(By.id("account_logout"));
		return element;
	}

	public static WebElement buyNow_Btn(WebDriver driver) {
		element = driver.findElement(By.className("buynow"));
		return element;
	}

	public static WebElement SP_itemCounter_Link(WebDriver driver) {
		element = driver.findElement(By.className("count"));
		return element;
	}

	public static WebElement SP_checkOut_Icon(WebDriver driver) {
		element = driver.findElement(By.className("cart_icon"));
		return element;
	}

	public static void login(WebDriver driver, String userName, String pwd) {
		account_Btn(driver).click();
    // http://store.demoqa.com/products-page/your-account/
		LoginPage.log_Input(driver).sendKeys(userName);
		LoginPage.pwd_Input(driver).sendKeys(pwd);
		LoginPage.login_Btn(driver).click();
	}

	public static void logOut(WebDriver driver) {
		accountOut_Btn(driver).click();
	}

	public static void checkOut(WebDriver driver) {
		SP_checkOut_Icon(driver).click();
	}

	public static void clickBuyNow_btn(WebDriver driver)
			throws InterruptedException {
		Thread.sleep(2000);
		HomePage.buyNow_Btn(driver).click();
	}

	public static int getItemNumber(WebDriver driver) {
		return Integer.parseInt(HomePage.SP_itemCounter_Link(driver).getText());
	}
}
