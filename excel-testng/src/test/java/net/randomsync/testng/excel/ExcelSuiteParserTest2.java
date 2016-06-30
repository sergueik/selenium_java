package net.randomsync.testng.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

/**
 * Tests for ExcelSuiteParser, test Excel file in src/test folder is required
 * 
 * @author <a href = "mailto:gaurav&#64;randomsync.net">Gaurav Gupta</a>
 * 
 */
public class ExcelSuiteParserTest2 {
	FileInputStream fis;
	Workbook wb;
	Sheet sheet;
	URL url = null;

	@BeforeClass
	public void setUp() throws Exception {
		url = this.getClass().getResource("/tests.xlsx");
		try {
			fis = new FileInputStream(url.getFile());
			wb = WorkbookFactory.create(fis);
		} catch (Exception e) {
			Assert.fail("Input Excel file not found");
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);

	}

	@Test(description = "Test parseExcelTestCases - default parser")
	public void testParseExcelTestCases1() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		List<ExcelTestCase> testcases = parser.parseExcelTestCases(sheet);
		Assert.assertEquals(testcases.size(), 2);
		Assert.assertEquals(testcases.get(0).name, "GoogleSearch");
	}

	@Test(description = "Test parseExcelTestCases - custom parser map")
	public void testParseExcelTestCases2() {
		Sheet sheet2 = wb.getSheetAt(1);
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.TEST_ID_COL, new int[]{1});

		ExcelSuiteParser parser = new ExcelSuiteParser();
		parser.setParserMap(map);
		
		List<ExcelTestCase> testcases = parser.parseExcelTestCases(sheet2);
		Assert.assertEquals(testcases.size(), 8);
		Assert.assertEquals(testcases.get(0).name, "GoogleSearch");
		
	}

	@Test(description = "Test getXmlSuites - nonexistent file", expectedExceptions = IOException.class)
	public void testGetXmlSuites1() throws InvalidFormatException, IOException {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		parser.getXmlSuites(new File("nonexistentfile.xls"), false);
	}

	@Test(description = "Test getXmlSuites - invalid file format", expectedExceptions = IllegalArgumentException.class)
	public void testGetXmlSuites2() throws InvalidFormatException, IOException {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		URL url = this.getClass().getResource("/testng.xml");
		parser.getXmlSuites(new File(url.getFile()), false);
	}

	@Test(description = "Test getXmlSuites - invalid file ", expectedExceptions = IllegalArgumentException.class)
	public void testGetXmlSuites3() throws InvalidFormatException, IOException {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		URL url = this.getClass().getResource("/testng.xml");
		parser.getXmlSuites(new File(url.getFile()), false);
	}

	@Test(description = "Test getXmlSuites")
	public void testGetXmlSuites4() throws InvalidFormatException, IOException {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		List<XmlSuite> suites = parser.getXmlSuites(new File(url.getFile()), false);
		Assert.assertEquals(suites.size(), 3);
		Assert.assertEquals(suites.get(0).getName(),"1.GoogleSearch.FF");
		Assert.assertEquals(suites.get(1).getName(),"2.GoogleSearch.IE");
	}

	@AfterClass
	public void tearDown() {
		try {
			fis.close();
		} catch (IOException e) {
			System.out
					.println("IOException when trying to close file input stream");
		}
	}

}
