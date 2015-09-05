package com.mycompany.app;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.lang.RuntimeException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import static org.junit.Assert.*;

public class App
{

static Actions actions;
static int implicit_wait_interval = 1;
static int flexible_wait_interval = 5;
static long wait_polling_interval = 500;
static WebDriverWait wait;
static WebDriver driver;

public static void main(String[] args) throws InterruptedException,java.io.IOException {
	String base_url = "https://datatables.net/examples/api/form.html";

	// Launch embedded driver
	driver = new FirefoxDriver();

	// Wait For Page To Load
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	// Go to base url
	driver.get(base_url);

	// Maximize Window
	driver.manage().window().maximize();

	String table_id="example";
	WebElement table_element;

	wait = new WebDriverWait(driver, flexible_wait_interval );
	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);

	try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
	} catch (RuntimeException timeoutException) {
		return;
	}

	table_element =  driver.findElement(By.id(table_id));

	String text_input_css_selector = "input[id='row-5-age']";
	WebElement text_input_element;
	text_input_element = table_element.findElement(By.cssSelector(text_input_css_selector));
	actions = new Actions(driver);
	actions.moveToElement(text_input_element).build().perform();
	String cell_text = "Software Developer";

	text_input_element.clear();
	// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/Keys.html
	text_input_element.sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE ));
	Thread.sleep(5000);
	text_input_element.sendKeys(Keys.chord("20", org.openqa.selenium.Keys.TAB, cell_text, org.openqa.selenium.Keys.ENTER));
	Thread.sleep(5000);

	// Close browser window
	driver.close();

}
}
