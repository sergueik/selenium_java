package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HomePage {

	private static WebElement element = null;
	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver value) {
		driver = value;
	}

	public static String pageTitle() {
		return driver.getTitle();
	}

	public static WebElement search_Input() {
		element = driver.findElement(By.className("search"));
		return element;
	}

	public static WebElement account_Btn() {
		// element = driver.findElement(By.id("account"));
		element = driver.findElement(By.cssSelector("div#account"));
		assertThat(element, notNullValue());
		return element;
	}

	public static WebElement accountOut_Btn() {
		element = driver.findElement(By.id("account_logout"));
		return element;
	}

	public static WebElement buyNow_Btn() {
		element = driver.findElement(By.className("buynow"));
		return element;
	}

	public static WebElement SP_itemCounter_Link() {
		element = driver.findElement(By.className("count"));
		return element;
	}

	public static WebElement SP_checkOut_Icon() {
		element = driver.findElement(By.className("cart_icon"));
		return element;
	}

	public static void login( String userName, String pwd) {
		account_Btn().click();
		LoginPage.log_Input().sendKeys(userName);
		LoginPage.pwd_Input().sendKeys(pwd);
		LoginPage.login_Btn().click();
	}

	public static void logOut() {
		accountOut_Btn().click();
	}

	public static void checkOut() {
		SP_checkOut_Icon().click();
	}

	public static void clickBuyNow_btn()
			throws InterruptedException {
		Thread.sleep(2000);
		HomePage.buyNow_Btn().click();
	}

	public static int getItemNumber() {
		return Integer.parseInt(HomePage.SP_itemCounter_Link().getText());
	}
}