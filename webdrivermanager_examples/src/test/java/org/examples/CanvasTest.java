package org.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class CanvasTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws InterruptedException {
		driver.get("http://szimek.github.io/signature_pad/");

		WebElement canvas = driver.findElement(By.tagName("canvas"));
		Action action = new Actions(driver).click(canvas)
				.moveToElement(canvas, 8, 8).clickAndHold(canvas).moveByOffset(120, 120)
				.moveByOffset(-120, 120).moveByOffset(-120, -120).moveByOffset(8, 8)
				.release(canvas).build();
		action.perform();

		Thread.sleep(5000);
	}

}
