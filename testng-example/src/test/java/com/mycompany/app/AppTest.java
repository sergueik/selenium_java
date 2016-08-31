package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.io.File;
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

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

// import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

// custom testng data providers

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

// OLE2 Office Documents needs HSSF

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// Office 2007+ XML needs XSSF
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

// OpenOffice  
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

// http://testngtricks.blogspot.com/2013/05/how-to-provide-data-to-dataproviders.html

import org.testng.*;
import org.testng.annotations.*;
import org.testng.internal.annotations.*;
import org.testng.internal.Attributes;
import java.lang.reflect.Method;
// https://groups.google.com/forum/#!topic/testng-users/J437qa5PSx8


public class AppTest
{  
  public RemoteWebDriver driver = null;
  public String seleniumHost = null;
  public String seleniumPort = null;
  public String seleniumBrowser = null;
  public String baseUrl = "http://habrahabr.ru/search/?";  

  @BeforeMethod
  public void setupBeforeSuite( ITestContext context ) throws InterruptedException {
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();

    seleniumHost = context.getCurrentXmlTest().getParameter("selenium.host");
    seleniumPort = context.getCurrentXmlTest().getParameter("selenium.port");
    seleniumBrowser = context.getCurrentXmlTest().getParameter("selenium.browser");

    capabilities =   new DesiredCapabilities(seleniumBrowser, "", Platform.ANY);

    try {
      String hubUrl = "http://"+  seleniumHost  + ":" + seleniumPort   +  "/wd/hub";
      driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
    } catch (MalformedURLException ex) { }

    try{
      driver.manage().window().setSize(new Dimension(600, 800));
      driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }  catch(Exception ex) {
      System.err.println(ex.toString());
    }
  }  
  
  @BeforeMethod
  // NOTE: cannot change signature of the method to include annotation:
  // org.testng.TestNGException:
  // Method handleTestMethodName requires 3 parameters but 0 were supplied in the @Configuration annotation.
  public void handleTestMethodInformation(final Method method, final ITestContext context /*, IDataProviderAnnotation annotation */){
    String suiteName = context.getCurrentXmlTest().getSuite().getName();
    System.err.println("Suite: " + suiteName);
    String testName = context.getCurrentXmlTest().getName();
    System.err.println("Test: " + testName);
    String methodName = method.getName();  
    System.err.println("Method: " + methodName);
    // String dataProvider = ((IDataProvidable)annotation).getDataProvider();
    // System.err.println("Data Provider: " + dataProvider);
    Map<String, String> parameters = (((TestRunner) context).getTest()).getParameters();
    Set<String> keys =  parameters.keySet();
    for (String key : keys) {
      System.out.print("Parameter: " + key + " = ");
      System.out.println(parameters.get(key));
    }
    Set<java.lang.String> attributeNames = ((IAttributes)context).getAttributeNames();
    if (attributeNames.size() > 0 ) {
      for (String attributeName : attributeNames) {
        System.out.print("Attribute: " + attributeName + " = ");
        System.out.println(((IAttributes)context).getAttribute(attributeName));
      }      
    }
  }
  
  @Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "dataProviderExcel2003"  )
  public void test1(String search_keyword, double expected) throws InterruptedException {
    parseSearchResult(search_keyword, expected);  
  }

  @Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "dataProviderOpenOfficeSpreadsheet" )
  public void test2(String search_keyword, double expected) throws InterruptedException {
    parseSearchResult(search_keyword, expected);
  }

  @Test(singleThreaded = false, threadPoolSize = 1, invocationCount = 1, description = "Finds a publication", dataProvider = "dataProviderExcel2007" )
  public void test3(String search_keyword, double expected) throws InterruptedException {  
    parseSearchResult(search_keyword, expected);
  }

  @AfterMethod
  public void cleanupSuite() {
    driver.close();
    driver.quit();
  }

  @DataProvider(parallel = true)
  public Object[][] dataProviderInline() {
    return new Object[][]{
      {"junit", 100.0},
      {"testng", 30.0},
      {"spock", 10.0},
    };
  }
  
  private void parseSearchResult(String search_keyword, double expected) throws InterruptedException {
    driver.get(baseUrl);
    WebDriverWait wait = new WebDriverWait(driver, 30);
    String search_input_name = null;
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inner_search_form")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-field__input")));
    search_input_name = "q";
    String search_input_xpath = String.format("//form[@id='inner_search_form']/div[@class='search-field__wrap']/input[@name='%s']", search_input_name);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(search_input_xpath)));
    WebElement element = driver.findElement(By.xpath(search_input_xpath));
    element.clear();
    element.sendKeys(search_keyword );
    element.sendKeys(Keys.RETURN);
  
    String pubsFoundCssSelector = "ul[class*='tabs-menu_habrahabr'] a[class*='tab-item tab-item_current']";
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pubsFoundCssSelector)));
    element = driver.findElement(By.cssSelector(pubsFoundCssSelector));  
    Pattern pattern = Pattern.compile("(\\d+)");
    Matcher matcher = pattern.matcher(element.getText());
    int publicationsFound = 0;
    if (matcher.find()) {
      publicationsFound  = Integer.parseInt(matcher.group(1) ) ;
      System.err.println("Publication count " + publicationsFound );
    } else {  
      System.err.println("No publications");
    }
    assertTrue( publicationsFound >= expected );
  }


  /*
   * Reads test data {<SEARCH>,<COUNT>} from Excel 2007 sheet with the following columns (ID is ignored):
   * ID(0) SEARCH(1) COUNT(2)
   * 1.0   junit(1)  100.0
   */
  
  @DataProvider(parallel = false)
  public Object[][] dataProviderExcel2007() {
  
    List<Object[]> data = new ArrayList<Object[]>();
    Object[] dataRow = { };
  
    String filename = "data_2007.xlsx";  
    try{

      XSSFWorkbook wb = new XSSFWorkbook(filename );
      XSSFSheet sheet = wb.getSheetAt(0);
      XSSFRow row;
      XSSFCell cell;
      String name = "";
      double count = 0;

      Iterator rows = sheet.rowIterator();  
      while (rows.hasNext()) {
        row = (XSSFRow) rows.next();
        if (row.getRowNum() == 0){ // skip the header
          continue;
        }
        Iterator cells = row.cellIterator();
        while (cells.hasNext()) {
          cell = (XSSFCell) cells.next();
          if (cell.getColumnIndex() == 2) {  
            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
              count = cell.getNumericCellValue();
            } else {
              count = 0;
            }
          }
          if (cell.getColumnIndex() == 1) {  
            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
              name = cell.getStringCellValue();
            } else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
              name = Double.toString(cell.getNumericCellValue());
            } else {
              // TODO: Boolean, Formula, Errors
              name = "";
            }
          }
        }
        dataRow = new Object[]{name, count} ;
        data.add(dataRow);
      }
    }  catch (Exception e) {
      e.printStackTrace();
    }
    Object[][] dataArray = new Object[ data.size() ][];
    data.toArray( dataArray );
    return dataArray;
  }

  /*
   * Reads test data {<SEARCH>,<COUNT>} from Excel 2003 OLE binary file with the following columns (ID is ignored):
   * ID(0) SEARCH(1) COUNT(2)
   * 1.0   junit(1)  100.0
   */
  @DataProvider(parallel = false , name = "dataProviderExcel2003" )
  public Object[][] dataProviderExcel2003() {
  
    List<Object[]> data = new ArrayList<Object[]>();
    Object[] dataRow = { };
  
    String filename = "data_2003.xls";  
    try{
      InputStream ExcelFileToRead = new FileInputStream(filename );
      HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row;
      HSSFCell cell;

      String name = "";
      double count = 0;

      Iterator rows = sheet.rowIterator();  
      while (rows.hasNext()) {
        row = (HSSFRow) rows.next();
        if (row.getRowNum() == 0){ // skip the header
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
            } else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
              name = Double.toString(cell.getNumericCellValue());
            } else {
              // TODO: Boolean, Formula, Errors
              name = "";
            }
          }
        }
        dataRow = new Object[]{name, count} ;
        data.add(dataRow);
      }
    }  catch (Exception e) {
      e.printStackTrace();
    }
    Object[][] dataArray = new Object[ data.size() ][];
    data.toArray( dataArray );
    return dataArray;
  }
  
  /*
   * Reads test data {<SEARCH>,<COUNT>} from OpenoOffice spreadsheet with the following columns (ID is ignored):
   * ID(0) SEARCH(1) COUNT(2)
   * 1.0   junit(1)  100.0
   */
  
  @DataProvider(parallel = false)
  public Object[][] dataProviderOpenOfficeSpreadsheet() {
  
    List<Object[]> data = new ArrayList<Object[]>();
    Object[] dataRow = { };
  
    String filename = "data.ods";  
    Sheet sheet;

    String name = "";
    double count = 0;
  
    try{
      File file = new File(filename);
      sheet = SpreadSheet.createFromFile(file).getSheet(0); // can pass sheet name as string  
      int nColCount = sheet.getColumnCount();
      int nRowCount = sheet.getRowCount();
      MutableCell cell = null;
      for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++ ) {
        if (sheet.getCellAt(0, nRowIndex).getValue() == null || sheet.getCellAt(0, nRowIndex).getValue() == "") {
          break ;
        }
        name = sheet.getCellAt(1, nRowIndex).getValue().toString() ;
        count = Double.valueOf(sheet.getCellAt(2, nRowIndex).getValue().toString() );
        dataRow = new Object[]{name, count} ;
        data.add(dataRow);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Object[][] dataArray = new Object[ data.size() ][];
    data.toArray( dataArray );
    return dataArray;
  }
  
}
