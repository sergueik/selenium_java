package org.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.testng.xml.*;

/**
 * The default parser used by ExcelTestNGRunner to parse an Excel file into
 * TestNG {@link org.testng.xml.XmlSuite XmlSuite}s. It parses each worksheet
 * into a separate suite and returns a list of all parsed <code>XmlSuite</code>s
 * 
 * <p>
 * This parser can be customized by using a custom {@link #parserMap}, which
 * specifies where to find the Suite and Test data. {@link ParserMapConstants}
 * is used to specify the keys of the map
 * 
 * @author <a href = "mailto:gaurav&#64;randomsync.net">Gaurav Gupta</a>
 * 
 */
public class ExcelSuiteParser implements IExcelFileParser {

	// default values
	public static final String SUITE_NAME_STR = "Suite Name";
	public static final String SUITE_PARAMS_STR = "Suite Parameters";
	public static final String TEST_ID_STR = "Id";
	public static final String TEST_NAME_STR = "Test Name";
	public static final String TEST_DESC_STR = "Test Description";
	public static final String TEST_PARAMS_STR = "Test Parameters";
	public static final String TEST_CONFIG_STR = "Test Configuration";

	// formatter to format cell data
	private DataFormatter formatter = new DataFormatter();

	// this map can be used to customize the location of Suite/Test data
	private Map<ParserMapConstants, int[]> parserMap;

	/**
	 * Creates a default parser with no custom map
	 */
	public ExcelSuiteParser() {
	}

	/**
	 * Creates a parser with specified map, which will be used to parse the
	 * Excel worksheets
	 * 
	 * @param parserMap
	 *			custom parser map to specify the location of suite/test data
	 */
	public ExcelSuiteParser(Map<ParserMapConstants, int[]> parserMap) {
		this.parserMap = parserMap;
	}

	/**
	 * Set the parser map for this parser. This can be used to customize the
	 * location of suite/test data that the parser retrieves from Excel files.
	 * 
	 * @param parserMap
	 *			Map that will be used to parse Excel file(s)
	 */
	public void setParserMap(Map<ParserMapConstants, int[]> parserMap) {
		this.parserMap = parserMap;
	}

	/**
	 * Parses the Excel file and returns a list of TestNG XmlSuites. If there
	 * are multiple worksheets, each worksheet is parsed into a separate
	 * XmlSuite
	 * 
	 * @param file
	 *			source Excel file
	 * @param loadClasses
	 *			whether to load test classes
	 * @return a List of TestNG XmlSuite with all tests from the Excel file
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	@Override
	public List<XmlSuite> getXmlSuites(File file, boolean loadClasses)
			throws InvalidFormatException, IOException /* ... */ {

		List<XmlSuite> suites = new ArrayList<XmlSuite>();

		FileInputStream fis = new FileInputStream(file);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(fis);
		} catch (IOException e) {
			throw (e);
		} catch (InvalidFormatException e) {
			throw (e);
		} catch (Exception e) {
		}

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			String name = getSuiteName(sheet);
			ExcelTestSuite suite = new ExcelTestSuite(name);
			suite.setSuiteParams(getSuiteParams(sheet));
			suite.setTestCases(parseExcelTestCases(sheet));
			suites.add(suite.getSuiteAsXmlSuite(loadClasses));
		}
		fis.close();
		return suites;
	}

	/**
	 * Parses the worksheet starting with header row and returns a list of test
	 * cases. Each row is considered as a test case with specific columns
	 * (specified by {@link #parserMap}) as test data. If there's an error
	 * parsing any row, it is skipped.
	 * 
	 * @return a list of test cases in the worksheet
	 */
	public List<ExcelTestCase> parseExcelTestCases(Sheet sheet) {

		List<ExcelTestCase> testCases = new ArrayList<ExcelTestCase>();

		// first get the location of Test Id Col
		int testIdCol = getTestIdCol(sheet);
		// then get the header row location
		int headerRow = getHeaderRow(sheet, testIdCol);
		// then get the rest of Test data colums
		int testNameCol = getColumnLocation(sheet, headerRow,
				ParserMapConstants.TEST_NAME_COL, TEST_NAME_STR);
		int testDescCol = getColumnLocation(sheet, headerRow,
				ParserMapConstants.TEST_DESC_COL, TEST_DESC_STR);
		int testParamCol = getColumnLocation(sheet, headerRow,
				ParserMapConstants.TEST_PARAM_COL, TEST_PARAMS_STR);
		int testConfigCol = getColumnLocation(sheet, headerRow,
				ParserMapConstants.TEST_CONFIG_COL, TEST_CONFIG_STR);

		/*
		 * parse the sheet starting from headerRow. Each row is a test case and
		 * needs to be added if test id is not blank
		 */
		Row row = null;
		for (int j = headerRow + 1; j <= sheet.getLastRowNum(); j++) {
			row = sheet.getRow(j);
			if (row != null) {
				try {
					if (!formatter.formatCellValue(row.getCell(testIdCol)).isEmpty()) {
						testCases.add(new ExcelTestCase(
								formatter.formatCellValue(row.getCell(testIdCol)), // id
								formatter.formatCellValue(row.getCell(testNameCol)), // name
								formatter.formatCellValue(row.getCell(testDescCol)), // desc
								formatter.formatCellValue(row.getCell(testParamCol)), // params
								formatter.formatCellValue(row.getCell(testConfigCol))// config
						));
					}
				} catch (Exception e) {
					// TODO add specific exception handlers
					System.err.println("Error parsing test case from row");
					e.printStackTrace();
					// skip the current row and continue
					continue;
				}
			}
		}
		return testCases;
	}

	/**
	 * Returns the name of the Suite from the worksheet. If unable to find the
	 * suite name (for example, if the location is invalid), an empty string is
	 * returned
	 * 
	 * <p>
	 * If a custom parser map is defined, the value is returned from the cell
	 * pointed at by the appropriate key. If a custom map is not defined or if
	 * it doesn't contain the appropriate key, it returns the value from the
	 * cell adjacent to a cell containing {@link #SUITE_NAME_STR}.
	 * 
	 * @param sheet
	 *			The worksheet to be parsed
	 * @return Name of the Suite or blank string if not found
	 */
	public String getSuiteName(Sheet sheet) {
		Row row;
		String suiteName = "";
		// if parser map is specified, get the suite name from specified cell
		if (parserMap != null
				&& parserMap.containsKey(ParserMapConstants.SUITE_NAME_CELL)) {
			int[] loc = parserMap.get(ParserMapConstants.SUITE_NAME_CELL);
			int rownum, colnum;
			try {
				rownum = loc[0];
				colnum = loc[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				// if location is not specified correctly, return blank string
				return "";
			}
			// get the suite name from specified cell
			row = sheet.getRow(rownum);
			if (row != null) {
				suiteName = formatter.formatCellValue(row.getCell(colnum));
			}
			// if suite name is null, return ""
			return suiteName != null ? suiteName : "";
		}
		// else get suite name from cell next to the one containing
		// SUITE_NAME_STR
		else {
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row != null) { // skip blank rows
					for (int j = 0; j <= row.getLastCellNum(); j++) {
						String cellValue = formatter.formatCellValue(row.getCell(j));
						if (SUITE_NAME_STR.equals(cellValue)) {
							suiteName = formatter.formatCellValue(row.getCell(j + 1));
							return suiteName;
						}
					}
				}
			} // end for
		}
		return suiteName;
	}

	/**
	 * Returns the Suite parameters from the worksheet as a Map. The suite
	 * parameters are required to be in a single cell in java {@link Properties}
	 * format. If the parameters are not found or are invalid, an empty map is
	 * returned.
	 * 
	 * <p>
	 * If a custom parser map is defined, the parameters map is returned from
	 * the cell pointed at by the appropriate key. If a custom map is not
	 * defined or if it doesn't contain the appropriate key, it returns the map
	 * from the cell adjacent to a cell containing {@link #SUITE_PARAMS_STR}.
	 * 
	 * @param sheet
	 *			The worksheet to be parsed
	 * @return A map containing the suite parameters
	 */
	public Map<String, String> getSuiteParams(Sheet sheet) {
		Row row;
		String rawParams = null;
		Map<String, String> params = new HashMap<String, String>();
		// if parser map is specified, get the parameters from specified cell
		if (parserMap != null
				&& parserMap.containsKey(ParserMapConstants.SUITE_PARAMS_CELL)) {
			int[] loc = parserMap.get(ParserMapConstants.SUITE_PARAMS_CELL);
			int rownum, colnum;
			try {
				rownum = loc[0];
				colnum = loc[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				// if location is not specified correctly, return empty map
				return params;
			}
			// else get the parameters from specified cell
			row = sheet.getRow(rownum);
			if (row != null) {
				rawParams = formatter.formatCellValue(row.getCell(colnum));
			}
		}
		// else get the parameters from cell next to the one containing
		// SUITE_PARAMS_STR
		else {
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row != null) { // skip blank rows
					for (int j = 0; j <= row.getLastCellNum(); j++) {
						String cellValue = formatter.formatCellValue(row.getCell(j));
						if (SUITE_PARAMS_STR.equals(cellValue)) {
							rawParams = formatter.formatCellValue(row.getCell(j + 1));
							break;
						}
					}
				}
				if (rawParams != null)
					break;
			}
		}
		if (rawParams != null) {
			Properties p = new Properties();
			try {
				p.load(new StringReader(rawParams));
			} catch (IOException ioe) {
				// if there are any issues with reading params, return empty map
				return params;
			}
			for (Enumeration<?> e = p.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				params.put(key, p.getProperty(key));
			}
		}
		return params;
	}

	/**
	 * Returns the header row location from the worksheet. Header row is the row
	 * that contains headers for test specifications and below which, all rows
	 * are required to be test case specifications.
	 * 
	 * <p>
	 * To find the header row, first the location of Test Id column should be
	 * determined using {@link #getTestIdCol(Sheet)}. The header row is the
	 * first row that contains {@link #TEST_ID_STR} in that column.
	 * 
	 * 
	 * @param sheet
	 *			The worksheet to be parsed
	 * @param testIdCol
	 *			The column containing the Test Id (0-based)
	 * @return the location of header row, or 0 if not found
	 */
	public int getHeaderRow(Sheet sheet, int testIdCol) {
		Row row;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row != null) { // skip blank rows
				String id = formatter.formatCellValue(row.getCell(testIdCol));
				if (TEST_ID_STR.equals(id)) {
					return i;
				}
			}
		}
		return 0;
	}

	/**
	 * Returns the location of Test Id column from the worksheet.
	 * 
	 * If a custom parser map is defined correctly, the value of
	 * {@link ParserMapConstants#TEST_ID_COL TEST_ID_COL} is returned. If not,
	 * it defaults to 0.
	 * 
	 * @param sheet
	 *			worksheet to be parsed
	 * @return the location of Test Id column, or 0 if not found
	 */
	public int getTestIdCol(Sheet sheet) {
		int testIdCol = 0; // default
		// if parser map is specified, try to get test id column from there
		if (parserMap != null
				&& parserMap.containsKey(ParserMapConstants.TEST_ID_COL)) {
			int[] loc = parserMap.get(ParserMapConstants.TEST_ID_COL);
			try {
				testIdCol = loc[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				// if location is not specified correctly, return 0
				return 0;
			}
		}
		// else if not specified, default is 0
		return testIdCol;
	}

	/**
	 * Returns the location of the column within the specified row. If parser
	 * map has the column location defined, it is used. Otherwise, the string is
	 * searched for within the row and the 1st matching cell is returned.
	 * 
	 * @param sheet
	 * @param rowLoc
	 * @param pMap
	 * @param str
	 * @return
	 */
	private int getColumnLocation(Sheet sheet, int rowLoc,
			ParserMapConstants pMap, String str) {
		Row row = sheet.getRow(rowLoc);
		if (parserMap != null && parserMap.containsKey(pMap)) {
			int[] loc = parserMap.get(pMap);
			try {
				return loc[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				// if location is not specified correctly, return 0
				return 0;
			}
		}
		if (row != null) {
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				if (str.equals(formatter.formatCellValue(row.getCell(j))))
					return j;
			}
		}
		return 0;
	}
}