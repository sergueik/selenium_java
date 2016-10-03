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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.logging.Level;

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
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
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

// JSON
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.testng.*;
import org.testng.annotations.*;
import org.testng.internal.annotations.*;
import org.testng.internal.Attributes;

import java.lang.reflect.Method;

public class AppTest {

	public RemoteWebDriver driver = null;

	// for grid testing
	public String seleniumHost = null;
	public String seleniumPort = null;
	public String seleniumBrowser = null;

	public String baseUrl = "http://habrahabr.ru/search/?";

	public static final String TEST_ID_STR = "Row ID";
	public static final String TEST_EXPECTED_COUNT = "Expected minimum link count";
	public static final String TEST_DESC_STR = "Search keyword";

	private static long implicit_wait_interval = 3;
	private static int flexible_wait_interval = 5;
	private static int page_load_timeout_interval = 30;
	private static long wait_polling_interval = 500;
	private static long highlight_interval = 100;

	@BeforeClass(alwaysRun = true)
	public void setupBeforeClass(final ITestContext context)
			throws InterruptedException {

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		LoggingPreferences logging_preferences = new LoggingPreferences();
		logging_preferences.enable(LogType.BROWSER, Level.ALL);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS,
				logging_preferences);
		driver = new ChromeDriver(capabilities);
		/*
		 * FirefoxProfile profile = new FirefoxProfile();
		 * profile.setPreference("browser.download.folderList",2);
		 * profile.setPreference("browser.download.manager.showWhenStarting",false);
		 * profile.setPreference("browser.download.dir","c:\downloads");
		 * profile.setPreference
		 * ("browser.helperApps.neverAsk.saveToDisk","text/csv"); WebDriver driver =
		 * new FirefoxDriver(profile); //new RemoteWebDriver(new
		 * URL("http://localhost:4444/wd/hub"), capability);
		 */
		try {
			driver.manage().window().setSize(new Dimension(600, 800));
			driver.manage().timeouts()
					.pageLoadTimeout(page_load_timeout_interval, TimeUnit.SECONDS);
			driver.manage().timeouts()
					.implicitlyWait(implicit_wait_interval, TimeUnit.SECONDS);
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

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "searches publications for a keyword", dataProvider = "Excel 2003")
	public void test_with_Excel_2003(String search_keyword, double expected_count)
			throws InterruptedException {
		parseSearchResult(search_keyword, expected_count);
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "searches publications for a keyword", dataProvider = "OpenOffice Spreadsheet")
	public void test_with_OpenOffice_Spreadsheet(String search_keyword,
			double expected_count) throws InterruptedException {
		parseSearchResult(search_keyword, expected_count);
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "searches publications for a keyword", dataProvider = "Excel 2007")
	public void test_with_Excel_2007(String search_keyword, double expected_count)
			throws InterruptedException {
		parseSearchResult(search_keyword, expected_count);
	}

	@Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "searches publications for a keyword", dataProvider = "JSON")
	public void test_with_JSON(String search_keyword, double expected_count)
			throws InterruptedException {
		parseSearchResult(search_keyword, expected_count);
	}

	@AfterClass(alwaysRun = true)
	public void cleanupSuite() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

	private void parseSearchResult(String search_keyword, double expected_count)
			throws InterruptedException {
		driver.get(baseUrl);

		System.err.println(String.format(
				"Search keyword:'%s'\tExpected minimum link count:%d", search_keyword,
				(int) expected_count));

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

	// static disconnected data provider
	@DataProvider(parallel = true)
	public Object[][] dataProviderInline() {
		return new Object[][] { { "junit", 100.0 }, { "testng", 30.0 },
				{ "spock", 10.0 }, };
	}

	@DataProvider(parallel = false, name = "Excel 2007")
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
		List<Object[]> testData = new ArrayList<Object[]>();
		Object[] testDataRow = {};

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
			String search_keyword = "";
			double expected_count = 0;
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
						search_keyword = cell.getStringCellValue();
					}
					if (cellColumn.equals("C")) {
						assertEquals(cell.getCellType(), XSSFCell.CELL_TYPE_NUMERIC);
						expected_count = cell.getNumericCellValue();
					}
				}
				System.err.println(String.format(
						"Row ID:%d\tSearch keyword:'%s'\tExpected minimum link count:%d",
						id, search_keyword, (int) expected_count));
				testDataRow = new Object[] { search_keyword, expected_count };
				testData.add(testDataRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

	@DataProvider(parallel = false, name = "Excel 2003")
	public Object[][] createData_from_Excel2003() {

		List<Object[]> testData = new ArrayList<Object[]>();
		Object[] testDataRow = {};

		String filename = "data_2003.xls";
		try {
			InputStream ExcelFileToRead = new FileInputStream(filename);
			HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

			// HSSFSheet sheet = wb.getSheetAt(0);
			String sheetName = "Employee Data";
			HSSFSheet sheet = wb.getSheet(sheetName);
			HSSFRow row;
			HSSFCell cell;

			String search_keyword = "";
			double expected_count = 0;

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
							expected_count = cell.getNumericCellValue();
						} else {
							expected_count = 0;
						}
					}
					if (cell.getColumnIndex() == 1) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							search_keyword = cell.getStringCellValue();
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							search_keyword = Double.toString(cell.getNumericCellValue());
						} else {
							// TODO: Boolean, Formula, Errors
							search_keyword = "";
						}
					}
				}
				testDataRow = new Object[] { search_keyword, expected_count };
				testData.add(testDataRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

	@DataProvider(parallel = false, name = "OpenOffice Spreadsheet")
	public Object[][] createData_from_OpenOfficeSpreadsheet() {

		HashMap<String, String> columns = new HashMap<String, String>();
		List<Object[]> testData = new ArrayList<Object[]>();
		Object[] testDataRow = {};

		String filename = "data.ods";
		Sheet sheet;

		String search_keyword = "";
		double expected_count = 0;
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
						expected_count = Double.valueOf(cell.getValue().toString());
					}
					if (columns.get(cellName).equals("SEARCH")) {
						assertEquals(cell.getValueType(), ODValueType.STRING);
						search_keyword = cell.getTextValue();
					}
					if (columns.get(cellName).equals("ID")) {
						System.err.println("Column: " + columns.get(cellName));
						assertEquals(cell.getValueType(), ODValueType.FLOAT);
						id = Integer.decode(cell.getValue().toString());
					}
				}

				System.err.println(String.format(
						"Row ID:%d\tSearch term:'%s'\tExpected minimum link count:%d", id,
						search_keyword, (int) expected_count));
				testDataRow = new Object[] { search_keyword, expected_count };
				testData.add(testDataRow);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

	@DataProvider(parallel = false, name = "JSON")
	public Object[][] createData_from_JSON(final ITestContext context,
			final Method method) throws org.json.JSONException {

		String filename = "data.json";
		JSONObject allTestData = new JSONObject();
		List<Object[]> testData = new ArrayList<Object[]>();
		ArrayList<String> hashes = new ArrayList<String>();
		String search_keyword = "";
		double expected_count = 0;

		JSONArray rows = new JSONArray();

		try {
			allTestData = new JSONObject(readFile(filename, Charset.forName("UTF-8")));
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String testName = "test";

		assertTrue(allTestData.has(testName));
		String dataString = allTestData.getString(testName);

		try {
			rows = new JSONArray(dataString);
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < rows.length(); i++) {
			String entry = rows.getString(i); // possible
			// org.json.JSONException
			hashes.add(entry);
		}
		assertTrue(hashes.size() > 0);
		for (String entry : hashes) {
			JSONObject entryObj = new JSONObject();
			try {
				entryObj = new JSONObject(entry);
			} catch (org.json.JSONException e) {
				e.printStackTrace();
			}
			Iterator<String> entryKeyIterator = entryObj.keys();

			while (entryKeyIterator.hasNext()) {
				String entryKey = entryKeyIterator.next();
				String entryData = entryObj.get(entryKey).toString();
				// System.err.println(entryKey + " = " + entryData);
				switch (entryKey) {
				case "keyword":
					search_keyword = entryData;
					break;
				case "count":
					expected_count = Double.valueOf(entryData);
					break;
				}
			}
			testData.add(new Object[] { search_keyword, expected_count });
		}
		Object[][] testDataArray = new Object[testData.size()][];
		testData.toArray(testDataArray);
		return testDataArray;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}