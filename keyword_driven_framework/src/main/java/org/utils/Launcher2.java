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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Launcher2 {

	private static String suite = "TestCase.xls";
	private static HashMap<String, String> stepData = new HashMap<>();

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
				HashMap<Integer, ArrayList<String>> stepMap = readSteps(
						indexRow.getCell(0).getStringCellValue());
				System.out.println(stepMap);

				for (int j = 0; j < stepMap.size(); j++) {
					List<String> stepList = stepMap.get(j);
					String keyword = stepList.get(0);
					if (keyword.equals("	")) {
						// implicit
						KeywordLibrary.openBrowser(null);
					} else {
						// TODO: define methods with List<String> params
						KeywordLibrary.callMethod(keyword, stepData);
					}
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
		Cell cell = row.createCell(4);
		cell.setCellValue(KeywordLibrary.result);

		FileOutputStream ostream = new FileOutputStream(file);
		workbook.write(ostream);
		ostream.close();
		workbook.close();
	}

	public static HashMap<Integer, ArrayList<String>> readSteps(String sheetName)
			throws IOException {
		FileInputStream file = new FileInputStream(getPropertyEnv("suite",
				String.format("%s\\Desktop\\%s", System.getenv("USERPROFILE"), suite)));

		HashMap<Integer, ArrayList<String>> map = new HashMap<>();
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet testcaseSheet = workbook.getSheet(sheetName);
		Row stepRow;
		Cell stepCell;
		for (int i = 1; i <= testcaseSheet.getLastRowNum(); i++) {
			// System.out.println("Row: " + i);
			ArrayList<String> stepList = new ArrayList<>();
			stepRow = testcaseSheet.getRow(i);
			for (int j = 0; j < 4; j++) {
				// System.out.println("Col: " + j);
				stepCell = stepRow.getCell(j);
				String cellValue = null;
				try {
					cellValue = stepCell.getStringCellValue();
				} catch (NullPointerException e) {
					cellValue = "";
				}
				stepList.add(cellValue);
			}
			map.put(i - 1, stepList);
		}
		workbook.close();
		return map;
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
