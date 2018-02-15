package com.github.sergueik.selenium;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class DataTablesTest extends BaseTest {
	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.beforeMethod(method);
		// ?
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error(s) in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	//
	@Test(enabled = true)
	public void testf1() {
		// Arrange
		driver.get("https://datatables.net/examples/api/form.html");
		String table_id = "example";

		WebElement table_element;
		try {
			table_element = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		String text_input_css_selector = "input[id='row-5-age']";
		WebElement text_input_element = table_element
				.findElement(By.cssSelector(text_input_css_selector));
		actions.moveToElement(text_input_element).build().perform();
		String cell_text = "Software Developer";
		text_input_element.clear();
		// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/Keys.html
		text_input_element.sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE,
				Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE));
		sleep(3000);
		text_input_element.sendKeys(Keys.chord("20", org.openqa.selenium.Keys.TAB,
				cell_text, org.openqa.selenium.Keys.ENTER));
		sleep(3000);
	}

	@Test(enabled = true)
	public void test2() {

		// Go to base url
		driver.get("https://datatables.net/examples/api/highlight.html");

		// Maximize Window
		driver.manage().window().maximize();

		String table_id = "example";
		WebElement table_element;

		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		// http://stackoverflow.com/questions/6198947/how-to-get-text-from-each-cell-of-an-html-table
		String script = "var table_row_locator = 'div#example_wrapper table#example tbody tr';\n"
				+ "var rows = document.querySelectorAll(table_row_locator);\n"
				+ "var result = [];\n"
				+ "for (row_cnt = 0; row_cnt != rows.length; row_cnt++) {\n"
				+ "var row = rows[row_cnt];\n" + "if (row instanceof Element) {\n"
				+ "var cols = row.querySelectorAll('td');\n"
				+ "var check_col_num = 1;\n" + "var data_col_num = 0;\n"
				+ "if (cols[check_col_num].innerHTML.match(/^Software.*/ig)) {\n"
				+ "result.push(cols[data_col_num].innerHTML);\n" + "}\n" + "}\n" + "}\n"
				+ "return result.join();\n";

		System.out.println(
				String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

		// NOTE: Works in IDE, does not work with WebDriver
		script = String.format("(function() { %s })();", script);
		System.out.println(
				String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

		table_element = driver.findElement(By.id(table_id));
		// NOTE: no leading slash in XPath
		List<WebElement> rows = table_element.findElements(By.xpath("tbody/tr"));
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rows.size());
		int row_num, cell_num, max_rows;
		max_rows = 3;
		row_num = 1;
		String row_role_attribute = "row";
		for (WebElement row : rows) {
			if (row_num > max_rows) {
				break;
			}
			assertTrue(
					// String.format("Unexpected title '%s'", row.getAttribute("role")),
					row.getAttribute("role").matches(row_role_attribute));
			cell_num = 1;
			List<WebElement> cells = row.findElements(By.xpath("td"));
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
	}

	// TODO: move utils to BaseTest

	private String storeEval(String script) {
		String result = null;
		if (driver instanceof JavascriptExecutor) {
			System.out.println("running the script");
			result = (String) ((JavascriptExecutor) driver).executeScript(script);
		}
		return result;
	}
}
