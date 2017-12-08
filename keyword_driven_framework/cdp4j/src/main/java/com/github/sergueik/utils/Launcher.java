package com.github.sergueik.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * Standalone Launcher for Selenium WebDriver Keyword Driven Library
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class Launcher {

	private static String propertiesFileName = "application.properties";
	private static String defaultTestCase = "TestCase.xls";
	private static int statusColumn = 9;
	private static boolean debug = false;
	private static String testCase;

	public static void main(String[] args) throws IOException {

		Map<String, String> propertiesMap = PropertiesParser
				.getProperties(String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));
		statusColumn = Integer.parseInt(propertiesMap.get("statusColumn"));
		testCase = (propertiesMap.get("testCase") != null)
				? propertiesMap.get("testCase")
				: getPropertyEnv("testCase", String.format("%s\\Desktop\\%s",
						System.getenv("USERPROFILE"), defaultTestCase));
		FileInputStream file = new FileInputStream(testCase);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet indexSheet = workbook.getSheet("Index");
		KeywordLibrary.loadProperties();
		for (int row = 1; row <= indexSheet.getLastRowNum(); row++) {
			Row indexRow = indexSheet.getRow(row);
			if (safeCellToString(indexRow.getCell(1)).equalsIgnoreCase("Yes")
					&& !safeCellToString(indexRow.getCell(0)).isEmpty()) {
				if (debug) {
					System.err.println(
							"Reading suite: " + indexRow.getCell(0).getStringCellValue());
				}
				Map<Integer, Map<String, String>> steps = readSteps(
						indexRow.getCell(0).getStringCellValue());

				for (int step = 0; step < steps.size(); step++) {
					Map<String, String> data = steps.get(step);
					for (String param : new ArrayList<String>(data.keySet())) {
						if (data.get(param) == null) {
							data.remove(param);
						}
					}
					String keyword = data.get("keyword");
					KeywordLibrary.callMethod(keyword, data);
					writeStatus(indexRow.getCell(0).getStringCellValue(), step + 1);
				}
			}
		}
		if (debug) {
			System.err.println("Done");
		}
		workbook.close();

	}

	public static void writeStatus(String sheetName, int rowNumber)
			throws IOException {
		File file = new File(testCase);

		FileInputStream istream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(istream);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.createCell(statusColumn);
		cell.setCellValue(KeywordLibrary.getStatus());

		FileOutputStream ostream = new FileOutputStream(file);
		workbook.write(ostream);
		ostream.close();
		workbook.close();
	}

	// reads the spreadsheet into a hash of step keywords and parameters indexed
	// by column number and step number
	public static Map<Integer, Map<String, String>> readSteps(String sheetName)
			throws IOException {
		Map<String, String> data = new HashMap<>();
		Map<Integer, Map<String, String>> stepDataMap = new HashMap<>();
		FileInputStream file = new FileInputStream(testCase);

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet testcaseSheet = workbook.getSheet(sheetName);
		Row stepRow;
		Cell stepCell;
		for (int row = 1; row <= testcaseSheet.getLastRowNum(); row++) {
			if (debug) {
				System.err.println("Row: " + row);
			}
			data = new HashMap<>();
			stepRow = testcaseSheet.getRow(row);
			data.put("keyword", stepRow.getCell(0).getStringCellValue());
			for (int col = 1; col < statusColumn; col++) {
				stepCell = stepRow.getCell(col);
				String cellValue = null;
				try {
					cellValue = safeCellToString(stepCell);
				} catch (NullPointerException | IllegalStateException e) {
					System.err.println("Exception (ignored): " + e.toString());
					cellValue = "";
				}
				if (debug) {
					System.err.println("Column[" + col + "] = " + cellValue);
				}
				data.put(String.format("param%d", col), cellValue);
			}
			stepDataMap.put(row - 1, data);
		}
		workbook.close();
		return stepDataMap;
	}

	// Safe conversion of type Excel cell object to String value
	public static String safeCellToString(org.apache.poi.ss.usermodel.Cell cell) {

		if (cell == null) {
			return null;
		}
		CellType type = cell.getCellTypeEnum();
		Object result;
		switch (type) {
		case _NONE:
			result = null;
			break;
		case NUMERIC:
			result = cell.getNumericCellValue();
			break;
		case STRING:
			result = cell.getStringCellValue();
			break;
		case FORMULA:
			throw new IllegalStateException("The formula cell is not supported");
		case BLANK:
			result = null;
			break;
		case BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case ERROR:
			throw new RuntimeException("Cell has an error");
		default:
			throw new IllegalStateException(
					"Cell type: " + type + " is not supported");
		}
		return (result == null) ? null : result.toString();
	}

	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}
}