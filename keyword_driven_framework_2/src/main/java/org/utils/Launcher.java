package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Launcher {

	private static String suite = "Test Suite.xls";

	public static void main(String[] args) throws IOException {

		FileInputStream file = new FileInputStream(String.format("%s\\Desktop\\%s",
				getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"), suite));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet indexSheet = workbook.getSheet("Index");
		Row indexRow;
		HashMap<Integer, ArrayList<String>> stepMap = null;
		List<String> stepList = new ArrayList<>();

		KeywordLibrary.loadProperties();

		for (int i = 1; i <= indexSheet.getLastRowNum(); i++) {
			indexRow = indexSheet.getRow(i);
			if (indexRow.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")) {
				stepMap = readSteps(indexRow.getCell(0).getStringCellValue());
				System.out.println(stepMap);

				for (int j = 0; j < stepMap.size(); j++) {
					stepList = stepMap.get(j);
					if (stepList.get(0).equals("openBrowser")) {
						KeywordLibrary.openBrowser();
						writeStatus(indexRow.getCell(0).getStringCellValue(), j + 1);
					} else {
						KeywordLibrary.callMethod(stepList.get(0), stepList.get(1),
								stepList.get(2), stepList.get(3));
						writeStatus(indexRow.getCell(0).getStringCellValue(), j + 1);
					}
				}
			}
		}

		System.out.println("Done");
		workbook.close();

	}

	public static void writeStatus(String sheetName, int rowNumber)
			throws IOException {
		File file = new File(String.format("%s\\Desktop\\%s",
				getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"), suite));

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
		HashMap<Integer, ArrayList<String>> map = new HashMap<>();
		FileInputStream file = new FileInputStream(String.format("%s\\Desktop\\%s",
				getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"), suite));

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet testcaseSheet = workbook.getSheet(sheetName);
		Row stepRow;
		Cell stepCell;
		for (int i = 1; i <= testcaseSheet.getLastRowNum(); i++) {
			ArrayList<String> stepList = new ArrayList<>();
			stepRow = testcaseSheet.getRow(i);
			for (int j = 0; j < 4; j++) {
				stepCell = stepRow.getCell(j);
				stepList.add(stepCell.getStringCellValue());
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
