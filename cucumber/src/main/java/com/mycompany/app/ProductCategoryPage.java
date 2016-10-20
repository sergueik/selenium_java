package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductCategoryPage {

	private static WebElement element = null;

	public static WebElement item_Name(WebDriver driver) {
		element = driver.findElement(By.className("prodtitle"));
		return element;
	}

	public static WebElement Item_Price(WebDriver driver) {
		WebElement form = driver.findElement(By.className("product_form_ajax"));
		WebElement element = form
				.findElement(By
						.xpath("//*[@id='single_product_page_container']/div[1]/div[2]/form/div[1]/p[2]/span"));
		return element;
	}
}
