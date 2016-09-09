package com.mycompany.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.nio.charset.Charset;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// testng data providers

// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

// OLE2 Office Documents
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// Office 2007+ XML
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// OpenOffice
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Table;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Range;

// http://testngtricks.blogspot.com/2013/05/how-to-provide-data-to-dataproviders.html

import org.testng.*;
import org.testng.annotations.*;
import org.testng.internal.annotations.*;
import org.testng.internal.Attributes;

import java.lang.reflect.Method;

// https://groups.google.com/forum/#!topic/testng-users/J437qa5PSx8

public class AppTest {

	public RemoteWebDriver driver = null;
	public String seleniumHost = null;
	public String seleniumPort = null;
	public String seleniumBrowser = null;
	public String baseUrl = "http://habrahabr.ru/search/?";

	@BeforeClass(alwaysRun = true)
	public void setupBeforeClass(final ITestContext context)
			throws InterruptedException {

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		seleniumHost = context.getCurrentXmlTest().getParameter("selenium.host");
		seleniumPort = context.getCurrentXmlTest().getParameter("selenium.port");
		seleniumBrowser = context.getCurrentXmlTest().getParameter(
				"selenium.browser");

		capabilities = new DesiredCapabilities(seleniumBrowser, "", Platform.ANY);

		try {
			String hubUrl = "http://" + seleniumHost + ":" + seleniumPort + "/wd/hub";
			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
		} catch (MalformedURLException ex) {
		}

		try {
			driver.manage().window().setSize(new Dimension(600, 800));
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
	}

	// NOTE: cannot change signature of the method to include annotation:
	// handleTestMethodInformation(final ITestContext context, final Method
	// method, IDataProviderAnnotation annotation )
	// runtime TestNGException:
	// Method handleTestMethodInformation requires 3 parameters but 0 were
	// supplied in the @Configuration annotation.
	@BeforeMethod
	public void handleTestMethodInformation(final ITestContext context,
			final Method method) {
		String suiteName = context.getCurrentXmlTest().getSuite().getName();
		System.err.println("BeforeMethod Suite: " + suiteName);
		String testName = context.getCurrentXmlTest().getName();
		System.err.println("BeforeMethod Test: " + testName);
		String methodName = method.getName();
		System.err.println("BeforeMethod Method: " + methodName);
		// String dataProvider = ((IDataProvidable)annotation).getDataProvider();
		// System.err.println("Data Provider: " + dataProvider);
		Map<String, String> parameters = (((TestRunner) context).getTest())
				.getParameters();
		Set<String> keys = parameters.keySet();
		for (String key : keys) {
			System.out.println("BeforeMethod Parameter: " + key + " = "
					+ parameters.get(key));
		}
		Set<java.lang.String> attributeNames = ((IAttributes) context)
				.getAttributeNames();
		if (attributeNames.size() > 0) {
			for (String attributeName : attributeNames) {
				System.out.print("BeforeMethod Attribute: " + attributeName + " = "
						+ ((IAttributes) context).getAttribute(attributeName));
			}
		}
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "Excel_2003")
	public void test_with_Excel2003(String search_keyword, double expected)
			throws InterruptedException {
		parseSearchResult(search_keyword, expected);
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "OpenOfficeSpreadsheet")
	public void test_with_OpenOfficeSpreadsheet(String search_keyword,
			double expected) throws InterruptedException {
		parseSearchResult(search_keyword, expected);
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "Excel2007")
	public void test_with_Excel2007(String search_keyword, double expected)
			throws InterruptedException {
		parseSearchResult(search_keyword, expected);
	}

	@AfterClass(alwaysRun = true)
	public void cleanupSuite() {
		driver.close();
		driver.quit();
	}

	@DataProvider(parallel = true)
	public Object[][] dataProviderInline() {
		return new Object[][] { { "junit", 100.0 }, { "testng", 30.0 },
				{ "spock", 10.0 }, };
	}

	private void parseSearchResult(String search_keyword, double expected_count)
			throws InterruptedException {
		driver.get(baseUrl);

		System.err.println(String.format("SEARCH:'%s'\tEXECTED COUNT:%d",
				search_keyword, (int) expected_count));

		WebDriverWait wait = new WebDriverWait(driver, 30);
		String search_input_name = null;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("inner_search_form")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("search-field__input")));
		search_input_name = "q";
		String search_input_xpath = String
				.format(
						"//form[@id='inner_search_form']/div[@class='search-field__wrap']/input[@name='%s']",
						search_input_name);
		wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(search_input_xpath)));
		WebElement element = driver.findElement(By.xpath(search_input_xpath));
		element.clear();
		element.sendKeys(search_keyword);
		element.sendKeys(Keys.RETURN);

		String pubsFoundCssSelector = "ul[class*='tabs-menu_habrahabr'] a[class*='tab-item tab-item_current']";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(pubsFoundCssSelector)));
		element = driver.findElement(By.cssSelector(pubsFoundCssSelector));
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(element.getText());
		int publicationsFound = 0;
		if (matcher.find()) {
			publicationsFound = Integer.parseInt(matcher.group(1));
			System.err.println("Publication count " + publicationsFound);
		} else {
			System.err.println("No publications");
		}
		assertTrue(publicationsFound >= expected_count);
	}

	// http://stackoverflow.com/questions/666477/possible-to-pass-parameters-to-testng-dataprovider
	@DataProvider(parallel = false, name = "Excel2007")
	public Object[][] createData_from_Excel2007(final ITestContext context,
			final Method method) {

		String suiteName = context.getCurrentXmlTest().getSuite().getName();
		System.err.println("Data Provider Caller Suite: " + suiteName);
		String testName = context.getCurrentXmlTest().getName();
		System.err.println("Data Provider Caller Test: " + testName);
		String methodName = method.getName();

		System.out.println("Data Provider Caller Method: " + method.getName());
		// String testParam =
		// context.getCurrentXmlTest().getParameter("test_param");

		@SuppressWarnings("deprecation")
		Map<String, String> parameters = (((TestRunner) context).getTest())
				.getParameters();
		Set<String> keys = parameters.keySet();
		for (String key : keys) {
			System.out.println("Data Provider Caller Parameter: " + key + " = "
					+ parameters.get(key));
		}

		HashMap<String, String> columns = new HashMap<String, String>();
		List<Object[]> data = new ArrayList<Object[]>();
		Object[] dataRow = {};

		String filename = "data_2007.xlsx";
		try {

			String sheetName = "Employee Data";
			XSSFWorkbook wb = new XSSFWorkbook(filename);
			XSSFSheet sheet = wb.getSheet(sheetName);
			// XSSFSheet sheet = wb.getSheetAt(0);
			// sheet.getSheetName();
			XSSFRow row;
			XSSFCell cell;
			int cellIndex = 0;
			String cellColumn = "";
			String search = "";
			double count = 0;
			int id = 0;
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();

				if (row.getRowNum() == 0) {
					// skip the header
					Iterator cells = row.cellIterator();
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						String dataHeader = cell.getStringCellValue();
						cellIndex = cell.getColumnIndex();
						cellColumn = CellReference.convertNumToColString(cellIndex);
						System.err.println(cellIndex + " = " + cellColumn + " "
								+ dataHeader);
						columns.put(cellColumn, dataHeader);
					}
					continue;
				}
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					cellColumn = CellReference.convertNumToColString(cell
							.getColumnIndex());
					if (columns.get(cellColumn).equals("ID")) {
						assertEquals(cell.getCellType(), XSSFCell.CELL_TYPE_NUMERIC);
						id = (int) cell.getNumericCellValue();
					}
					if (cellColumn.equals("B")) {
						assertEquals(cell.getCellType(), XSSFCell.CELL_TYPE_STRING);
						search = cell.getStringCellValue();
					}
					if (cellColumn.equals("C")) {
						assertEquals(cell.getCellType(), XSSFCell.CELL_TYPE_NUMERIC);
						count = cell.getNumericCellValue();
					}
				}
				System.err.println(String.format("Row ID:%d\tSEARCH:'%s'\tCOUNT:%d",
						id, search, (int) count));
				dataRow = new Object[] { search, count };
				data.add(dataRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object[][] dataArray = new Object[data.size()][];
		data.toArray(dataArray);
		return dataArray;
	}

	@DataProvider(parallel = false, name = "Excel2003")
	public Object[][] createData_from_Excel2003() {

		List<Object[]> data = new ArrayList<Object[]>();
		Object[] dataRow = {};

		String filename = "data_2003.xls";
		try {
			InputStream ExcelFileToRead = new FileInputStream(filename);
			HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

			// HSSFSheet sheet = wb.getSheetAt(0);
			String sheetName = "Employee Data";
			HSSFSheet sheet = wb.getSheet(sheetName);
			HSSFRow row;
			HSSFCell cell;

			String name = "";
			double count = 0;

			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				if (row.getRowNum() == 0) { // ignore the header
					continue;
				}
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					if (cell.getColumnIndex() == 2) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							count = cell.getNumericCellValue();
						} else {
							count = 0;
						}
					}
					if (cell.getColumnIndex() == 1) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							name = cell.getStringCellValue();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							name = Double.toString(cell.getNumericCellValue());
						} else {
							// TODO: Boolean, Formula, Errors
							name = "";
						}
					}
				}
				dataRow = new Object[] { name, count };
				data.add(dataRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object[][] dataArray = new Object[data.size()][];
		data.toArray(dataArray);
		return dataArray;
	}

	@DataProvider(parallel = false, name = "OpenOfficeSpreadsheet")
	public Object[][] createData_from_OpenOfficeSpreadsheet() {

		HashMap<String, String> columns = new HashMap<String, String>();
		List<Object[]> data = new ArrayList<Object[]>();
		Object[] dataRow = {};

		String filename = "data.ods";
		Sheet sheet;

		String search = "";
		double count = 0;
		int id = 0;

		try {
			File file = new File(filename);
			String sheetName = "Employee Data";
			sheet = SpreadSheet.createFromFile(file).getSheet(sheetName);
			// sheet = SpreadSheet.createFromFile(file).getFirstSheet();
			System.err.println("Sheet name: " + sheet.getName());
			int nColCount = sheet.getColumnCount();
			int nRowCount = sheet.getRowCount();
			Cell cell = null;
			for (int nColIndex = 0; nColIndex < nColCount; nColIndex++) {
				String header = sheet.getImmutableCellAt(nColIndex, 0).getValue()
						.toString();
				if (StringUtils.isBlank(header)) {
					break;
				}
				String column = CellReference.convertNumToColString(nColIndex);
				System.err.println(nColIndex + " = " + column + " " + header);
				columns.put(column, header);
			}
			// often there may be no ranges defined
			Set<String> rangeeNames = sheet.getRangesNames();
			Iterator rangeNamesIterator = rangeeNames.iterator();

			while (rangeNamesIterator.hasNext()) {
				System.err.println("Range = " + rangeNamesIterator.next());
			}
			// isCellBlank has protected access in Table
			for (int nRowIndex = 1; nRowIndex < nRowCount
					&& StringUtils.isNotBlank(sheet.getImmutableCellAt(0, nRowIndex)
							.getValue().toString()); nRowIndex++) {
				for (int nColIndex = 0; nColIndex < nColCount
						&& StringUtils
								.isNotBlank(sheet.getImmutableCellAt(nColIndex, nRowIndex)
										.getValue().toString()); nColIndex++) {
					cell = sheet.getImmutableCellAt(nColIndex, nRowIndex);
					String cellName = CellReference.convertNumToColString(nColIndex);

					if (columns.get(cellName).equals("COUNT")) {
						assertEquals(cell.getValueType(), ODValueType.FLOAT);
						count = Double.valueOf(cell.getValue().toString());
					}
					if (columns.get(cellName).equals("SEARCH")) {
						assertEquals(cell.getValueType(), ODValueType.STRING);
						search = cell.getTextValue();
					}
					if (columns.get(cellName).equals("ID")) {
						System.err.println("Column: " + columns.get(cellName));
						assertEquals(cell.getValueType(), ODValueType.FLOAT);
						id = Integer.decode(cell.getValue().toString());
					}
				}

				System.err.println(String.format("Row ID:%d\tSEARCH:'%s'\tCOUNT:%d",
						id, search, (int) count));
				dataRow = new Object[] { search, count };
				data.add(dataRow);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		Object[][] dataArray = new Object[data.size()][];
		data.toArray(dataArray);
		return dataArray;
	}

}