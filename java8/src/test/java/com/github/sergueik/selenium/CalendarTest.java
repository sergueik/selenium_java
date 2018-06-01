package com.github.sergueik.selenium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * based on http://makeseleniumeasy.com/2017/09/18/how-to-handle-different-types-of-calendars-in-selenium-part-1/
*/

public class CalendarTest extends BaseTest {

	private static String baseURL = "https://www.makemytrip.com/";

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void selectDateTest() {
		// Locating departure date calendar
		WebElement calendarElement = driver.findElement(By.id("hp-widget__depart"));
		try {
			// NOTE: the site does not allow too far into the future
			// TODO: compute
			// NOTE: only works with the future dates
			selectDate(calendarElement, "2019", "February", "22", driver);
		} catch (ParseException e) {
		}
	}

	// utils
	public void selectDate(WebElement calendar, String year, String monthName,
			String day, WebDriver driver) throws ParseException {
		calendar.click();

		String yearElementXpath = "//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-year']";
		WebElement yearElement = driver.findElement(By.xpath(yearElementXpath));
		highlight(yearElement);
		String currentYear = yearElement.getText();

		// Advance via Next arrow till we reach desired year
		if (!currentYear.equals(year)) {
			do {
				WebElement nextYearElement = driver
						.findElement(By.xpath("(//span[text()='Next'])[1]"));
				highlight(nextYearElement);
				nextYearElement.click();
				yearElement = driver.findElement(By.xpath(yearElementXpath));
				currentYear = yearElement.getText();
			} while (!currentYear.equals(year));

		}
		flash(yearElement);


		
		String monthElementXpath = "(//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]";
		WebElement monthElement = driver.findElement(By.xpath(monthElementXpath));
		highlight(monthElement);
		String currentMonth = monthElement.getText().trim();
		if (!currentMonth.equalsIgnoreCase(monthName)) {
			do {
				WebElement nextMonthElement = driver
						.findElement(By.xpath("(//span[text()='Next'])[1]"));
				nextMonthElement.click();
				monthElement = driver.findElement(By.xpath(monthElementXpath));
				highlight(monthElement);
				currentMonth = monthElement.getText();
			} while (!currentMonth.equalsIgnoreCase(monthName));

		}
		flash(monthElement);
		
		// get java month number for desired month
		int month = getMonthJavaInt(monthName);

		// Filter dates that belong to desired month by the ui-datepicker
		// @data-month
		// attribute
		String dateElementXpath = String.format(
				"//div[@class='ui-datepicker-group ui-datepicker-group-first']//table/tbody[1]//td[(@class=' ' or @class=' ui-datepicker-week-end ' ) and @data-month = '%d']",
				month);

		List<WebElement> dateElements = driver
				.findElements(By.xpath(dateElementXpath));
		for (WebElement dayElement : dateElements) {
			if (dayElement.getText().trim().equals(day)) {
				flash(dayElement);
				dayElement.click();
				break;
			}
		}

	}

	public int getMonthJavaInt(String monthName) throws ParseException {
		Date date = new SimpleDateFormat("MMMM").parse(monthName);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

}