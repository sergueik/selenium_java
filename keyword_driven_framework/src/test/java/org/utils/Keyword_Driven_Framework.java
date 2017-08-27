package org.utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Keyword_Driven_Framework {
	public static int xTCRows, xTCCols;
	public static String[][] xTCdata;
	public static int xTSRows, xTSCols;
	public static String[][] xTSdata;
	public static String browser, vURL, vText, vGetText, vAttribute;
	public static String vTechnology, vCity, vZIP;
	public static String keyword, arg1, arg2, arg3;
	public static long vSleep;
	public static WebDriver driver;

	public static void main(String[] args) throws Exception {

		String eXPath;
		String xlPath;

		browser = "IE";
		vURL = "http://www.dice.com/";
		vTechnology = "Selenium";
		vCity = "Edison NJ";
		vZIP = "90001";
		vAttribute = "value";
		vSleep = 4000L;
		xlPath = String.format("%s/Desktop/TestAutomation.xls",
				System.getenv("USERPROFILE"));

		// Read the Test Steps XL sheet.
		xlTSRead(xlPath);
		xlTCRead(xlPath);
		for (int j = 1; j < xTCRows; j++) {
			if (xTCdata[j][4].equals("Y")) {
				for (int i = 1; i < xTSRows; i++) {
					if (xTCdata[j][1].equals(xTSdata[i][0])) {
						System.out.print(xTSdata[i][0]);
						keyword = xTSdata[i][4];
						arg1 = xTSdata[i][5];
						arg2 = xTSdata[i][6];
						arg3 = xTSdata[i][7];
						System.out
								.println(String.format("---%s````%s```` ````%s```` ````%s````",
										keyword, arg1, arg2, arg3));
						getIP();
						keyword_executor();
					}
				}
			}
		}
	}

	public static void navigate_to(WebDriver driver, String url) {
		driver.navigate().to(url);
	}

	public static void send_keys(WebDriver driver, String xpath, String text) {
		try {
			driver.findElement(By.xpath(xpath)).sendKeys(text);
		} catch (NoSuchElementException e) {
			System.out.println("SendKeys failed with exception: " + e);
		}
	}

	public static void click_element(WebDriver driver, String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
		} catch (NoSuchElementException e) {
			System.out.println("FindElement/Click failed with exception: " + e);
		}
	}

	public static void click_link(WebDriver driver, String text) {
		try {
			driver.findElement(By.linkText(text)).click();
		} catch (NoSuchElementException e) {
			System.out.println("FindElement/Click link failed with exception: " + e);
		}
	}

	public static String get_text(WebDriver driver, String xpath) {
		try {
			return driver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			System.out.println("FindElement/getText failed with exception: " + e);
			return "Fail";
		}
	}

	public static String verify_text(WebDriver driver, String xpath,
			String text) {
		try {
			String textOut = driver.findElement(By.xpath(xpath)).getText();
			if (textOut.equals(text)) {
				return "Pass";
			} else {
				return "Fail";
			}
		} catch (NoSuchElementException e) {
			System.out.println("FindElement/getText failed with exception: " + e);
			return "Fail";
		}
	}

	public static String element_present(WebDriver driver, String xpath) {
		try {
			if (driver.findElement(By.xpath(xpath)).isDisplayed()) {
				return "Pass";
			} else {
				return "Fail";
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element not found with exception: " + e);
			return "Fail";
		}
	}

	public static String link_present(WebDriver driver, String text) {
		try {
			if (driver.findElement(By.linkText(text)).isDisplayed()) {
				return "Pass";
			} else {
				return "Fail";
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element not found with exception: " + e);
			return "Fail";
		}
	}

	public static String get_attribute(WebDriver driver, String xpath,
			String attribute) {
		return driver.findElement(By.xpath(xpath)).getAttribute(attribute);
	}

	public static String verify_attribute(WebDriver driver, String xpath,
			String attribute, String text) {
		String textOut = driver.findElement(By.xpath(xpath))
				.getAttribute(attribute);
		if (textOut.equals(text)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public static void close_browser(WebDriver driver) {
		driver.close();
	}

	public static void wait_time(Long wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}

	public static void xlTSRead(String filePath) throws Exception {

		HSSFWorkbook myWB = new HSSFWorkbook(
				new FileInputStream(new File(filePath)));
		HSSFSheet mySheet = myWB.getSheetAt(2);
		xTSRows = mySheet.getLastRowNum() + 1;
		xTSCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows are " + xTSRows);
		System.out.println("Cols are " + xTSCols);
		xTSdata = new String[xTSRows][xTSCols];
		for (int i = 0; i < xTSRows; i++) {
			HSSFRow row = mySheet.getRow(i);
			for (int j = 0; j < xTSCols; j++) {
				HSSFCell cell = row.getCell(j);
				String value = cellToString(cell);
				xTSdata[i][j] = value;
			}
		}
	}

	public static void xlTCRead(String filePath) throws Exception {
		HSSFWorkbook myWB = new HSSFWorkbook(
				new FileInputStream(new File(filePath)));
		HSSFSheet mySheet = myWB.getSheetAt(1);
		xTCRows = mySheet.getLastRowNum() + 1;
		xTCCols = mySheet.getRow(0).getLastCellNum();
		// System.out.println("Number of rows is " + xTCRows);
		// System.out.println("Number of columns are " + xTCCols);
		xTCdata = new String[xTCRows][xTCCols];
		for (int i = 0; i < xTCRows; i++) {
			HSSFRow row = mySheet.getRow(i);
			for (int j = 0; j < xTCCols; j++) {
				HSSFCell cell = row.getCell(j);
				String value = cellToString(cell);
				xTCdata[i][j] = value;
			}
		}
	}

	public static String cellToString(HSSFCell cell) {
		int type = cell.getCellType();
		Object result;
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 0
			result = cell.getNumericCellValue();
			break;
		case HSSFCell.CELL_TYPE_STRING: // 1
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 2
			throw new RuntimeException("We can't evaluate formulas in Java");
		case HSSFCell.CELL_TYPE_BLANK: // 3
			result = "-";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // 4
			result = cell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 5
			throw new RuntimeException("The cell has an error");
		default:
			throw new RuntimeException(
					String.format("Cell type: %d is not suppored", type));
		}
		return result.toString();
	}

	// converts the symbols to variables defined in the class
	public static void getIP() {
		if (arg1.equals("vURL")) {
			arg1 = vURL;
		}
		if (arg1.equals("vTechnology")) {
			arg1 = vTechnology;
		}
		if (arg1.equals("vZIP")) {
			arg1 = vZIP;
		}
		if (arg1.equals("vCity")) {
			arg1 = vCity;
		}
		if (arg2.equals("vURL")) {
			arg2 = vURL;
		}
		if (arg2.equals("vTechnology")) {
			arg2 = vTechnology;
		}
		if (arg2.equals("vZIP")) {
			arg2 = vZIP;
		}
		if (arg2.equals("vCity")) {
			arg2 = vCity;
		}
		if (arg3.equals("vURL")) {
			arg3 = vURL;
		}
		if (arg3.equals("vTechnology")) {
			arg3 = vTechnology;
		}
		if (arg3.equals("vZIP")) {
			arg3 = vZIP;
		}
		if (arg3.equals("vCity")) {
			arg3 = vCity;
		}
	}

	public static void keyword_executor() throws Exception {

		if (keyword.equals("navigate_to")) {
			if (browser.equals("IE")) {
				driver = new InternetExplorerDriver();
			} else if (browser.equals("chrome")) {
				driver = new ChromeDriver();
			} else {
				driver = new FirefoxDriver();
			}
			navigate_to(driver, arg1);
		}
		if (keyword.equals("send_keys")) {
			send_keys(driver, arg1, arg2);
		}
		if (keyword.equals("click_element")) {
			click_element(driver, arg1);
		}
		if (keyword.equals("click_link")) {
			click_link(driver, arg1);
		}
		if (keyword.equals("get_text")) {
			get_text(driver, arg1);
		}
		if (keyword.equals("verify_text")) {
			System.out.println(
					"Text present " + arg2 + " is a " + verify_text(driver, arg1, arg2));
		}
		if (keyword.equals("get_attribute")) {
			get_attribute(driver, arg1, arg2);
		}
		if (keyword.equals("verify_attribute")) {
			verify_attribute(driver, arg1, arg2, arg3);
		}
		if (keyword.equals("close_browser")) {
			close_browser(driver);
		}
		if (keyword.equals("wait_time")) {
			wait_time(vSleep);
		}
		if (keyword.equals("element_present")) {
			System.out.println("Element " + arg1 + " is "
					+ (("Pass".equalsIgnoreCase(element_present(driver, arg1)))
							? "present" : "absent"));
		}
		if (keyword.equals("link_present")) {
			System.out.println("Link " + arg1 + " is "
					+ ((link_present(driver, arg1).equalsIgnoreCase("Pass")) ? "present"
							: "absent"));
		}
	}
}
