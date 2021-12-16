package calculator;

import java.time.Month;
import java.util.List;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.text.WordUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CalculatorDateTest extends Calculator {

	@Before
	public void goToDate() {
		driver.findElementByName("Menu").click();
		driver.findElementByName("Date calculation Calculator").click();
		WebElement header = driver.findElementByAccessibilityId("Header");
		assertEquals("Date Calculator should be selected", "DATE CALCULATION",
				header.getText());
	}

	@Ignore
	@Test
	public void findDifference() {
		driver.findElementByAccessibilityId("DateDiff_FromDate").click();
		selectDate(26, 7, 2017);
		List<WebElement> elements = driver
				.findElementsByAccessibilityId("DateDiffAllUnitsResultLabel");
		if (!elements.isEmpty()) {
			System.out.println(elements.get(0).getText());
		}
		String difference = driver.findElement(By.xpath("(//Text)[last()]"))
				.getText();
		System.out.println(difference);
	}

	private void selectDate(int day, int month, int year) {
		driver.findElementByAccessibilityId("HeaderButton").click();
		driver.findElementByAccessibilityId("HeaderButton").click();
		selectYear(year);
		selectMonth(month);
		selectDay(day);
	}

	private void selectYear(int year) {
		String currView = driver.findElementByAccessibilityId("HeaderButton")
				.getText();
		String[] years = currView.split("-");
		Range<Integer> yearRange = Range.between(Integer.valueOf(years[0].trim()),
				Integer.valueOf(years[1].trim()));
		if (yearRange.contains(year)) {
			driver.findElementByName(String.valueOf(year)).click();
			return;
		} else if (yearRange.isBefore(year)) {
			driver.findElementByName("Next").click();
		} else if (yearRange.isAfter(year)) {
			driver.findElementByName("Previous").click();
		}
		selectYear(year);
	}

	private void selectMonth(int month) {
		String monthName = WordUtils
				.capitalize(Month.of(month).name().toLowerCase());
		System.out.println(monthName);
		driver.findElementByName(monthName).click();
	}

	private void selectDay(int day) {
		driver.findElementByName(String.valueOf(day)).click();
	}

}
