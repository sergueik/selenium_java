package example;

/**
 * Selected test scenarios for Selenium jBrowserDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuppressWarnings("deprecation")
public class JBrowserDriverTest extends BaseTest {

	private static String selector = null;

	private static String baseURL = "https://datatables.net/examples/api/form.html";
	private static String testFileName = "test.txt";
	private static String testFilePath = new File(testFileName).getAbsolutePath().replaceAll("\\\\", "/");
	private static final Logger log = LogManager.getLogger(JBrowserDriverTest.class);

	@Before
	public void beforeTest() {
		// System.err.println("in beforeTest");
		driver.get(baseURL);
	}

	@After
	public void afterTest() {
	}

	@Test
	public void test3() {

		// Go to base url
		driver.get("https://datatables.net/examples/api/highlight.html");

		// Maximize Window
		driver.manage().window().maximize();

		String table_id = "example";
		WebElement table_element;

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		// http://stackoverflow.com/questions/6198947/how-to-get-text-from-each-cell-of-an-html-table
		String script = "var table_row_locator = 'div#example_wrapper table#example tbody tr';\n"
				+ "var rows = document.querySelectorAll(table_row_locator);\n" + "var result = [];\n"
				+ "for (row_cnt = 0; row_cnt != rows.length; row_cnt++) {\n" + "var row = rows[row_cnt];\n"
				+ "if (row instanceof Element) {\n" + "var cols = row.querySelectorAll('td');\n"
				+ "var check_col_num = 1;\n" + "var data_col_num = 0;\n"
				+ "if (cols[check_col_num].innerHTML.match(/^Software.*/ig)) {\n"
				+ "result.push(cols[data_col_num].innerHTML);\n" + "}\n" + "}\n" + "}\n" + "return result.join();\n";

		System.out.println(String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

		// NOTE: Works in IDE, does not work with WebDriver
		script = String.format("(function() { %s })();", script);
		System.out.println(String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

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
					// actions.moveToElement(cell).build().perform();
					highlight(cell);
					System.out
							.println(String.format("row # %d, col # %d text='%s'", row_num, cell_num, cell.getText()));
					cell_num++;
				}
				row_num++;
			}
		}
	}

	@Ignore
	@Test
	public void test1() {
		// Arrange
		WebElement table_element = null;
		String table_id = "example";

		table_element = findElement("id", table_id);
		assertThat(table_element, notNullValue());
		try {
			table_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		assertThat(table_element, notNullValue());
		String text_input_css_selector = "input[id='row-5-age']";
		WebElement text_input_element = findElements("css_selector", text_input_css_selector, table_element).get(0);
		// table_element.findElement(By.cssSelector(text_input_css_selector));
		String cell_text = "Software Developer";
		text_input_element.clear();
		// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/Keys.html
		text_input_element.sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
				Keys.BACK_SPACE, Keys.BACK_SPACE));
		sleep(3000);
		text_input_element
				.sendKeys(Keys.chord("20", org.openqa.selenium.Keys.TAB, cell_text, org.openqa.selenium.Keys.ENTER));
		sleep(3000);

	}

	@Ignore
	@Test
	public void test2() {
		// Arrange
		WebElement table_element = null;
		String table_id = "example";

		try {
			System.err.println("Using wait " + wait.hashCode());

			table_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			System.err.println("Exception in wait by id: " + table_id);

			return;
			// NOTE: quits the test
		}
		assertThat(table_element, notNullValue());
		table_element = findElement("id", table_id);
		assertThat(table_element, notNullValue());
		String text_input_css_selector = "input[id='row-5-age']";
		WebElement text_input_element = findElements("css_selector", text_input_css_selector, table_element).get(0);
		// table_element.findElement(By.cssSelector(text_input_css_selector));
		String cell_text = "Software Developer";
		text_input_element.clear();
		// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/Keys.html
		text_input_element.sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
				Keys.BACK_SPACE, Keys.BACK_SPACE));
		sleep(3000);
		text_input_element
				.sendKeys(Keys.chord("20", org.openqa.selenium.Keys.TAB, cell_text, org.openqa.selenium.Keys.ENTER));
		sleep(3000);

	}

}
