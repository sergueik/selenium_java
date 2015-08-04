package com.mycompany.app;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;


import java.lang.RuntimeException;


import java.util.concurrent.TimeUnit;


import org.junit.Assert;

import static org.junit.Assert.*;


public class App
{


static WebDriver driver;
static int implicit_wait_interval = 1;
static int flexible_wait_interval = 5;
static long wait_polling_interval = 500;
static WebDriverWait wait;
static Actions actions;

public static void main(String[] args) throws InterruptedException,java.io.IOException {

	String base_url = "https://datatables.net/examples/api/highlight.html";

	// Launch embedded driver
	driver = new FirefoxDriver();

	// Wait For Page To Load
	driver.manage().timeouts().implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);

	// Go to base url
	driver.get(base_url);

	// Maximize Window
	driver.manage().window().maximize();

	String table_id = "example";
	WebElement table_element;
	wait = new WebDriverWait(driver, flexible_wait_interval );
	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);

	try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
	} catch (RuntimeException timeoutException) {
		return;
	}

	table_element = driver.findElement(By.id(table_id));
	// NOTE: no leading slash in XPath
	List<WebElement> table_row_collection = table_element.findElements(By.xpath("tbody/tr"));
	System.out.println("NUMBER OF ROWS IN THIS TABLE = " + table_row_collection.size());
	int row_num,cell_num;
	row_num = 1;
	actions = new Actions(driver);
	String row_role_attribute = "row";
	for(WebElement table_row : table_row_collection)
	{

		assertTrue(String.format("Unexpected title '%s'", table_row.getAttribute("role")), table_row.getAttribute("role").matches(row_role_attribute) );
		List<WebElement> table_cell_collection = table_row.findElements(By.xpath("td"));
		System.out.println("NUMBER OF COLUMNS=" + table_cell_collection.size());
		cell_num = 1;
		for(WebElement table_cell : table_cell_collection)
		{
			// Hover over cell
			actions.moveToElement(table_cell).build().perform();
			highlight(table_cell);
			System.out.println(String.format("row # %d, col # %d text='%s'",row_num, cell_num, table_cell.getText()));
			cell_num++;
		}
		row_num++;
	}

	// Close browser window
	driver.close();

}

private static void highlight(WebElement element) throws InterruptedException {

	highlight(element, 100);
}
private static void highlight(WebElement element, long highlight_interval) throws InterruptedException {
	if (wait!= null)         {
		wait = new WebDriverWait(driver, flexible_wait_interval );
	}
	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
	wait.until(ExpectedConditions.visibilityOf(element));
	if (driver instanceof JavascriptExecutor) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid yellow'", element);
	}
	Thread.sleep(highlight_interval);
	if (driver instanceof JavascriptExecutor) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", element);
	}
}

}

