package com.xls.report.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author - rahul.rathore
 */
public class ExcelConfiguration {

	public static XSSFRow CreateHeader(XSSFWorkbook book, XSSFSheet sheet,
			String[] aHeader) {
		XSSFRow row = sheet.createRow(0);
		XSSFCell[] headerCell = new XSSFCell[aHeader.length];
		XSSFCellStyle[] sHeaderStyle = new XSSFCellStyle[aHeader.length];

		for (int i = 0; i < headerCell.length; i++) {
			headerCell[i] = row.createCell(i);

			sHeaderStyle[i] = book.createCellStyle();
			sHeaderStyle[i].setAlignment(HorizontalAlignment.CENTER);
			sHeaderStyle[i].setBorderBottom(BorderStyle.THIN);
			sHeaderStyle[i].setBorderLeft(BorderStyle.THIN);
			sHeaderStyle[i].setBorderRight(BorderStyle.THIN);
			sHeaderStyle[i].setBorderTop(BorderStyle.THIN);
			sHeaderStyle[i].setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			sHeaderStyle[i].setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sHeaderStyle[i].setLocked(true);

			headerCell[i].setCellStyle(sHeaderStyle[i]);
			headerCell[i].setCellValue(aHeader[i]);
		}
		return row;
	}

	public static boolean isFilePresent(String... aFileName) {
		try {
			for (String name : aFileName) {
				File file = new File(name);
				if (!file.exists())
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static XSSFWorkbook getBook(String aFileName)
			throws FileNotFoundException, IOException {
		return new XSSFWorkbook(new FileInputStream(new File(aFileName)));
	}

	public static XSSFSheet getSheet(XSSFWorkbook aBook, String bSheetName) {
		return aBook.getSheet(bSheetName);
	}

	public static boolean isSheetPresent(XSSFWorkbook book, String aSheetName) {
		if (book.getSheet(aSheetName) == null)
			return false;
		else
			return true;
	}

	public static int getTotalSheetCount(XSSFWorkbook book) {
		int index = 0;
		while (true) {
			try {
				if (book.getSheetAt(index) == null)
					break;
				index++;
			} catch (Exception e) {
				break;
			}

		}
		return index;
	}

	public static HashMap<String, ArrayList<String>> appendExcelData(
			XSSFSheet xlSheet, HashMap<String, ArrayList<String>> sheetMap) {
		XSSFRow row = null;
		int i = xlSheet.getFirstRowNum() + 1;
		for (; i <= xlSheet.getLastRowNum(); i++) {
			row = xlSheet.getRow(i);
			if (sheetMap.get(row.getCell(Configuration.testNameIndex)
					.getStringCellValue()) == null) {
				ArrayList<String> newData = addDataToMap(row, new ArrayList<String>());
				sheetMap.put(row.getCell(Configuration.testNameIndex)
						.getStringCellValue(), newData);
			}
		}
		return sheetMap;
	}

	private static ArrayList<String> addDataToMap(XSSFRow row,
			ArrayList<String> arrayList) {
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			try {
				arrayList.remove(i);
			} catch (Exception e) {
				// ignored
			}
			arrayList.add(i, row.getCell(i).getStringCellValue());
		}
		return arrayList;
	}

	public static HashMap<String, ArrayList<String>> appendExcelDataToMap(
			XSSFSheet xlSheet) {
		XSSFRow row = null;
		Map<String, ArrayList<String>> sheetMap = new HashMap<String, ArrayList<String>>();
		int i = xlSheet.getFirstRowNum() + 1;
		for (; i <= xlSheet.getLastRowNum(); i++) {
			row = xlSheet.getRow(i);
			ArrayList<String> newData = addDataToMap(row, new ArrayList<String>());
			sheetMap.put(row.getCell(Configuration.testNameIndex)
					.getStringCellValue(), newData);
		}
		return (HashMap<String, ArrayList<String>>) sheetMap;
	}

	public static String transformExpMessage(String aMesg) {
		if (aMesg.indexOf(Configuration.transformKeyword) != -1) {
			aMesg = aMesg
					.substring(0, aMesg.indexOf(Configuration.transformKeyword));
			if (aMesg.indexOf(Configuration.transformKeywordTwo) != -1) {
				return aMesg.substring(0,
						aMesg.indexOf(Configuration.transformKeywordTwo));
			}
			return aMesg;
		}
		return aMesg;
	}

}
