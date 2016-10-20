package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Slide_Item_Page {
	private static WebElement element = null;

	public static WebElement item_Price(WebDriver driver) {
		WebElement form = driver.findElement(By.className("product_form_ajax"));
		return form
				.findElement(By
						.xpath("//*[@id='single_product_page_container']/div[1]/div[2]/form/div[1]/p[2]/span"));
	}

	public static WebElement product_title(WebDriver driver) {
		return driver.findElement(By.className("prodtitle"));
	}

	public static WebElement wpsc_Buy_Btn(WebDriver driver) {
		return driver.findElement(By.className("wpsc_buy_button"));
	}

	public static WebElement popup_CheckOut_Btn(WebDriver driver) {
		String str1 = "go_to_checkout";
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			if (driver.findElement(By.className(str1)) != null) {
				element = driver.findElement(By.className(str1));
			}
		}
		return element;
	}

	public static WebElement popup_ContinueShopping_Btn(WebDriver driver) {
		String str1 = "continue_shopping";
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			if (driver.findElement(By.className(str1)) != null) {
				element = driver.findElement(By.className(str1));
			}
		}
		return element;
	}

	// add one item in the shoppingcart
	public static void addOneItemInShoppingCart(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wpsc_Buy_Btn(driver).click();
	}
}
