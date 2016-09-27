package com.mycompany.app;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.lang.RuntimeException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import static org.junit.Assert.*;

public class App {

	static WebDriver driver;
	static int implicit_wait_interval = 1;
	static int flexible_wait_interval = 5;
	static long wait_polling_interval = 500;
	static WebDriverWait wait;
	static Actions actions;
  static private Predicate<WebElement> hasClass;
  static private Predicate<WebElement> hasAttr;
  static private Predicate<WebElement> hasText;
  
	public static void main(String[] args) throws InterruptedException,
			java.io.IOException {

		String base_url = "https://datatables.net/examples/api/highlight.html";

		// Launch embedded driver
		driver = new FirefoxDriver();

		// Wait For Page To Load
		driver.manage().timeouts()
				.implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);

		// Go to base url
		driver.get(base_url);

		// Maximize Window
		driver.manage().window().maximize();

		String table_id = "example";
		WebElement table_element;
		wait = new WebDriverWait(driver, flexible_wait_interval);
		wait.pollingEvery(wait_polling_interval, TimeUnit.MILLISECONDS);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}

		table_element = driver.findElement(By.id(table_id));
		// NOTE: no leading slash in XPath
		List<WebElement> rows = table_element.findElements(By.xpath("tbody/tr"));
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rows.size());
		int row_num, cell_num, max_rows;
		max_rows = 3;
		row_num = 1;
		actions = new Actions(driver);
		String row_role_attribute = "row";
		for (WebElement row : rows) {
			if (row_num > max_rows) {
				break;
			}
			assertTrue(String.format("Unexpected title '%s'",
					row.getAttribute("role")),
					row.getAttribute("role").matches(row_role_attribute));
      // 
      hasText = e -> e.getText().matches("Software.*");
			List<WebElement> cells = row.findElements(By.xpath("td"));
      List<WebElement> webElementsWithText = cells.stream()
        .filter(hasText).collect(Collectors.toList());
      for (WebElement e: webElementsWithText) {
        
			assertTrue(String.format("Unexpected Text '%s'",
					e.getText()),
					e.getText().matches("Software.*"));
			 System.out.println(e.getText());
        
      }
			cell_num = 1;
			String checkColumn = cells.get(cell_num).getText();
			// System.out.println(checkColumn);
			if (checkColumn.matches("Software.*")) {
				// System.out.println("NUMBER OF COLUMNS=" + cells.size());
				cell_num = 1;
				for (WebElement cell : cells) {
					// Hover over cell
					actions.moveToElement(cell).build().perform();
					highlight(cell);
					System.out.println(String.format("row # %d, col # %d text='%s'",
							row_num, cell_num, cell.getText()));
					cell_num++;
				}
				row_num++;
			}
		}

		// Close browser window
		driver.close();

	}

	private static void highlight(WebElement element) throws InterruptedException {
		highlight(element, 100);
	}

	private static void highlight(WebElement element, long highlight) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].style.border='3px solid yellow'", new Object[]{ element });
		}
			Thread.sleep(highlight);
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].style.border=''", element);
		}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}


	private static String storeEval(String script) {
		String result = null;
		if (driver instanceof JavascriptExecutor) {
			System.out.println("running the script");
			result = (String) ((JavascriptExecutor) driver).executeScript(script, null);
		}
		return result;
	}
}
