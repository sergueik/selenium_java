package com.github.sergueik.selenium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

		// NOTE: not optimal: Calendar element: input[id="hp-widget__depart"]
		// Do we need input#hp-widget__depart ? Probably selected so because of the
		// dash
		highlight(calendarElement, 1000);
		try {
			// NOTE: the site does not allow too far into the future
			// TODO: compute
			// NOTE: only works with the future dates
			selectDate(calendarElement, "2019", "February", "22", driver);
		} catch (ParseException e) {
		}
	}

	@Test(enabled = false)
	public void selectCssToXPathTest() {
		// Locating departure date calendar
		WebElement calendarElement = driver.findElement(By.id("hp-widget__depart"));
		int month = 4;
		List<String> xpaths = Arrays.asList(new String[] {
				"//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-year']",
				"(//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]",
				String.format(
						"//div[@class='ui-datepicker-group ui-datepicker-group-first']//table/tbody[1]//td[(@class=' ' or @class=' ui-datepicker-week-end ' ) and @data-month = '%d']",
						month),
				String.format(
						"//div[@class='ui-datepicker-group ui-datepicker-group-first']//table/tbody[1]//td[@data-month = '%d']",
						month) });
		xpaths.stream().forEach(
				o -> System.err.println(String.format("%s => %s", o, xPathToCSS(o))));
	}

	// utils
	public void selectDate(WebElement calendar, String year, String monthName,
			String day, WebDriver driver) throws ParseException {
		calendar.click();

		/* String yearElementXpath = "//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-year']";
		WebElement yearElement = driver.findElement(By.xpath(yearElementXpath));
		*/
		String yearElementSelector = "div.ui-datepicker-title span.ui-datepicker-year";
		WebElement yearElement = driver
				.findElement(By.cssSelector(yearElementSelector));
		highlight(yearElement);
		String currentYear = yearElement.getText();

		// Advance via Next arrow till we see desired year
		if (!currentYear.equals(year)) {
			do {
				// WebElement nextYearElement = driver
				// .findElement(By.xpath("(//span[text()='Next'])[1]"));
				WebElement nextYearElement = driver
						.findElement(By.cssSelector("a.ui-datepicker-next"));

				highlight(nextYearElement);
				nextYearElement.click();
				yearElement = driver.findElement(By.cssSelector(yearElementSelector));
				currentYear = yearElement.getText();
			} while (!currentYear.equals(year));

		}
		flash(yearElement);
		/*
				String monthElementXpath = "(//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]";
				WebElement monthElement = driver.findElement(By.xpath(monthElementXpath));
				*/
		String monthElementSelector = "div.ui-datepicker-title span.ui-datepicker-month:nth-of-type(1)";
		WebElement monthElement = driver
				.findElement(By.cssSelector(monthElementSelector));
		highlight(monthElement);
		String currentMonth = monthElement.getText().trim();
		if (!currentMonth.equalsIgnoreCase(monthName)) {
			do {
				WebElement nextMonthElement = driver
						.findElement(By.cssSelector("a.ui-datepicker-next"));
				nextMonthElement.click();
				monthElement = driver.findElement(By.cssSelector(monthElementSelector));
				highlight(monthElement);
				currentMonth = monthElement.getText();
			} while (!currentMonth.equalsIgnoreCase(monthName));

		}
		flash(monthElement);

		// get java month number for desired month
		int month = getMonthJavaInt(monthName);

		// Filter selectable dates that belong to desired month by the presence of 
		// @ui-datepicker and @data-month attributes

		/*
		String dateElementXpath = "//div[@class='ui-datepicker-group ui-datepicker-group-first']//table/tbody[1]//"
				+ String.format(
						"td[(@class=' ' or @class=' ui-datepicker-week-end ' ) and @data-month = '%d']",
						month);
		List<WebElement> dateElements = driver
				.findElements(By.xpath(dateElementXpath));
				*/
		String dateElementSelector = String.format(
				"table.ui-datepicker-calendar tbody td[data-handler='selectDay'][data-month='%d'] a",
				month);
		List<WebElement> dateElements = driver
				.findElements(By.cssSelector(dateElementSelector));
		for (WebElement dayElement : dateElements) {
			// highlight(dayElement);
			System.err
					.println("Day element: " + dayElement.getAttribute("innerHTML"));
			// if (dayElement.getText().trim().equals(day)) {
			if (dayElement.getAttribute("innerHTML").trim().equals(day)) {
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