package com.github.sergueik.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * based on focum discussion in https://automated-testing.info/t/ne-poluchaetsya-vybrat-option-v-kontekstnom-menyu-selenium-java/21298
*/

public class ContextMenuTest extends BaseTest {

	private static String baseURL = "http://the-internet.herokuapp.com/context_menu";

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);

	}

	@Test(enabled = true)
	public void selectControlReloadPageTest() {
		WebElement element = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
		actions.contextClick(element).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ENTER);
		actions.build().perform();
		sleep(1000);

	}

	@Test(enabled = true)
	public void selectControlSaveAsTest() {
		WebElement element = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
		actions.contextClick(element).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ENTER);

		actions.build().perform();
		sleep(1000);

	}

	@Test(enabled = true)
	public void selectDateTest2() {
		actions.keyDown(Keys.CONTROL).build().perform();
		WebElement element = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
		actions.moveToElement(element).contextClick().build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		sleep(1000);
	}

	@Test(enabled = false)
	public void selectContextViaScript() {
		WebElement element = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
		executeScript(getScriptContent("simulateRightMouseButtonClick.js"),
				element);
		sleep(1000);
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		sleep(1000);
		actions.sendKeys(Keys.SPACE).build().perform();
	}

}