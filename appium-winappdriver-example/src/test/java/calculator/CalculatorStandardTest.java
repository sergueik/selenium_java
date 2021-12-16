package calculator;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class CalculatorStandardTest extends Calculator {

	private String getResult() {
		WebElement result = driver
				.findElementByAccessibilityId("CalculatorResults");
		System.out.println(result.getText());
		return result.getText().replace("Display is ", "");
	}

	@Before
	public void emptyResult() {
		driver.findElementByName("Menu").click();
		driver.findElementByName("Standard Calculator").click();
		WebElement header = driver.findElementByAccessibilityId("Header");
		// TODO: ignoring case
		assertEquals("Standard Calculator should be selected", "Standard",
				header.getText());
	}

	@After
	public void clear() {
		driver.findElementByName("Clear").click();
		assertEquals("Should be 0", "0", getResult());
	}

	@Test
	public void addTest() {
		driver.findElementByName("One").click();
		driver.findElementByName("Plus").click();
		driver.findElementByName("Two").click();
		driver.findElementByName("Equals").click();
		assertEquals("Should be 3", "3", getResult());
	}

	@Test
	public void combination() {
		driver.findElementByName("Seven").click();
		driver.findElementByName("Multiply by").click();
		driver.findElementByName("Nine").click();
		driver.findElementByName("Plus").click();
		driver.findElementByName("One").click();
		driver.findElementByName("Equals").click();
		driver.findElementByName("Divide by").click();
		driver.findElementByName("Eight").click();
		driver.findElementByName("Equals").click();
		assertEquals("8", getResult());
	}

	@Test
	public void division() {
		driver.findElementByName("Eight").click();
		driver.findElementByName("Eight").click();
		driver.findElementByName("Divide by").click();
		driver.findElementByName("One").click();
		driver.findElementByName("One").click();
		driver.findElementByName("Equals").click();
		assertEquals("8", getResult());
	}

	@Test
	public void multiplication() {
		driver.findElementByName("Nine").click();
		driver.findElementByName("Multiply by").click();
		driver.findElementByName("Nine").click();
		driver.findElementByName("Equals").click();
		assertEquals("81", getResult());
	}

	@Test
	public void subtraction() {
		driver.findElementByName("Nine").click();
		driver.findElementByName("Minus").click();
		driver.findElementByName("One").click();
		driver.findElementByName("Equals").click();
		assertEquals("8", getResult());
	}

}
