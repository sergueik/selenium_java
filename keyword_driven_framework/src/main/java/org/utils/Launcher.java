package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Launcher {

	private static String suite = "TestCase.xls";
	private static int statusColumn = 6;

	public static void main(String[] args) throws IOException {

		FileInputStream file = new FileInputStream(getPropertyEnv("suite",
				String.format("%s\\Desktop\\%s", System.getenv("USERPROFILE"), suite)));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet indexSheet = workbook.getSheet("Index");
		Row indexRow;
		KeywordLibrary.loadProperties();

		for (int i = 1; i <= indexSheet.getLastRowNum(); i++) {
			indexRow = indexSheet.getRow(i);
			if (indexRow.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")) {
				System.out.println(
						"Reading suite: " + indexRow.getCell(0).getStringCellValue());
				Map<Integer, Map<String, String>> steps = readSteps(
						indexRow.getCell(0).getStringCellValue());

				for (int j = 0; j < steps.size(); j++) {
					Map<String, String> data = steps.get(j);
					String keyword = data.get("keyword");
					KeywordLibrary.callMethod(keyword, data);
					writeStatus(indexRow.getCell(0).getStringCellValue(), j + 1);
				}
			}
		}

		System.out.println("Done");
		workbook.close();

	}

	public static void writeStatus(String sheetName, int rowNumber)
			throws IOException {
		File file = new File(getPropertyEnv("suite",
				String.format("%s\\Desktop\\%s", System.getenv("USERPROFILE"), suite)));

		FileInputStream istream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(istream);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.createCell(statusColumn);
		cell.setCellValue(KeywordLibrary.status);

		FileOutputStream ostream = new FileOutputStream(file);
		workbook.write(ostream);
		ostream.close();
		workbook.close();
	}

	public static Map<Integer, Map<String, String>> readSteps(String sheetName)
			throws IOException {
		HashMap<String, String> data = new HashMap<>();
		Map<Integer, Map<String, String>> stepDataMap = new HashMap<>();
		FileInputStream file = new FileInputStream(getPropertyEnv("suite",
				String.format("%s\\Desktop\\%s", System.getenv("USERPROFILE"), suite)));

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet testcaseSheet = workbook.getSheet(sheetName);
		Row stepRow;
		Cell stepCell;
		for (int row = 1; row <= testcaseSheet.getLastRowNum(); row++) {
			System.err.println("Row: " + row);
			data = new HashMap<>();
			stepRow = testcaseSheet.getRow(row);
			data.put("keyword", stepRow.getCell(0).getStringCellValue());
			for (int col = 1; col < statusColumn; col++) {
				stepCell = stepRow.getCell(col);
				String cellValue = null;
				try {
					cellValue = safeCellToString(stepCell);
				} catch (NullPointerException|IllegalStateException e) {
					System.err.println("Exception (ignored): " + e.toString());
					cellValue = "";
				}
				System.err.println("Column[" + col + "] = " + cellValue);
				data.put(String.format("param%d", col), cellValue);
			}
			stepDataMap.put(row - 1, data);
		}
		workbook.close();
		return stepDataMap;
	}

	// Safe conversion of type Excel cell object to String value
	public static String safeCellToString(Cell cell) {
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
			throw new IllegalStateException("Can't evaluate formula cell");
		case HSSFCell.CELL_TYPE_BLANK: // 3
			result = "-";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // 4
			result = cell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 5
			throw new RuntimeException("Cell has an error");
		default:
			throw new IllegalStateException("Unsupported cell type: " + type);
		}
		return result.toString();
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
