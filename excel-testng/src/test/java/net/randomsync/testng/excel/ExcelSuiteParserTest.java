package net.randomsync.testng.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for ExcelSuiteParser, test Excel file in src/test folder is required
 * 
 * @author <a href = "mailto:gaurav&#64;randomsync.net">Gaurav Gupta</a>
 * 
 */
public class ExcelSuiteParserTest {
	FileInputStream fis;
	Sheet sheet;

	@BeforeClass
	public void setUp() throws Exception {
		Workbook wb = null;
		URL url = this.getClass().getResource("/tests.xlsx");
		try {
			fis = new FileInputStream(url.getFile());
			//fis = new FileInputStream("src\\test\\tests.xlsx");
			wb = WorkbookFactory.create(fis);
		} catch (Exception e) {
			Assert.fail("Input Excel file not found");
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);

	}

	@Test(description = "Test getSuiteName without parser map")
	public void testGetSuiteName1() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		String name = parser.getSuiteName(sheet);
		Assert.assertEquals(name, "1.GoogleSearch.FF");
	}

	@Test(description = "Test getSuiteName with parser map - valid location")
	public void testGetSuiteName2() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_NAME_CELL, new int[] { 1, 2 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		String name = parser.getSuiteName(sheet);
		Assert.assertEquals(name, "1.GoogleSearch.FF");
	}

	@Test(description = "Test getSuiteName with parser map - invalid location")
	public void testGetSuiteName3() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_NAME_CELL, new int[] { 50, 2 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		String name = parser.getSuiteName(sheet);
		Assert.assertEquals(name, "");
	}

	@Test(description = "Test getSuiteName with parser map - incomplete map")
	public void testGetSuiteName4() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_NAME_CELL, new int[] { 50 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		String name = parser.getSuiteName(sheet);
		Assert.assertEquals(name, "");
	}

	@Test(description = "Test getSuiteParams without parser map")
	public void testGetSuiteParams1() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		Map<String, String> params = new HashMap<String, String>();
		params = parser.getSuiteParams(sheet);
		Assert.assertEquals(params.size(), 3);
		Assert.assertEquals(params.get("browser"), "firefox");
	}

	@Test(description = "Test getSuiteParams with parser map - valid location")
	public void testGetSuiteParams2() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_PARAMS_CELL, new int[] { 3, 2 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		Map<String, String> params = new HashMap<String, String>();
		params = parser.getSuiteParams(sheet);
		Assert.assertEquals(params.size(), 3);
		Assert.assertEquals(params.get("browser"), "firefox");
	}

	@Test(description = "Test getSuiteParams with parser map - invalid location")
	public void testGetSuiteParams3() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_PARAMS_CELL, new int[] { 50, 2 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		Map<String, String> params = new HashMap<String, String>();
		params = parser.getSuiteParams(sheet);
		Assert.assertEquals(params.size(), 0);
	}

	@Test(description = "Test getSuiteParams with parser map - incomplete map")
	public void testGetSuiteParams4() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_PARAMS_CELL, new int[] { 50 });

		ExcelSuiteParser parser = new ExcelSuiteParser(map);
		Map<String, String> params = new HashMap<String, String>();
		params = parser.getSuiteParams(sheet);
		Assert.assertEquals(params.size(), 0);
	}

	@Test(description = "Test getHeaderRow - valid test id col")
	public void testGetHeaderRow1() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		int headerRow = parser.getHeaderRow(sheet, 0);
		Assert.assertEquals(headerRow, 7);
	}

	@Test(description = "Test getHeaderRow - invalid test id col", expectedExceptions = IllegalArgumentException.class)
	public void testGetHeaderRow2() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		int headerRow = parser.getHeaderRow(sheet, -1);
		Assert.assertEquals(headerRow, 7);
	}

	@Test(description = "Test getHeaderRow - not matching test id str")
	public void testGetHeaderRow3() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		int headerRow = parser.getHeaderRow(sheet, 2);
		Assert.assertEquals(headerRow, 0);
	}

	@Test(description = "Test getTestIdCol without parser map")
	public void testGetTestIdCol1() {
		ExcelSuiteParser parser = new ExcelSuiteParser();
		int testIdCol = parser.getTestIdCol(sheet);
		Assert.assertEquals(testIdCol, 0);
	}

	@Test(description = "Test getTestIdCol with parser map but no key")
	public void testGetTestIdCol2() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.SUITE_PARAMS_CELL, new int[] { 50 });
		
		ExcelSuiteParser parser = new ExcelSuiteParser();
		parser.setParserMap(map);
		
		int testIdCol = parser.getTestIdCol(sheet);
		Assert.assertEquals(testIdCol, 0);
	}

	@Test(description = "Test getTestIdCol with parser map with valid key")
	public void testGetTestIdCol3() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.TEST_ID_COL, new int[] { 1 });
		
		ExcelSuiteParser parser = new ExcelSuiteParser();
		parser.setParserMap(map);
		
		int testIdCol = parser.getTestIdCol(sheet);
		Assert.assertEquals(testIdCol, 1);
	}

	@Test(description = "Test getTestIdCol with parser map with incomplete key")
	public void testGetTestIdCol4() {
		Map<ParserMapConstants, int[]> map = new HashMap<ParserMapConstants, int[]>();
		map.put(ParserMapConstants.TEST_ID_COL, new int[] { });
		
		ExcelSuiteParser parser = new ExcelSuiteParser();
		parser.setParserMap(map);
		
		int testIdCol = parser.getTestIdCol(sheet);
		Assert.assertEquals(testIdCol, 0);
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
