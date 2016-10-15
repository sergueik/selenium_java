package com.xls.report.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.config.ExcelConfiguration;
import com.xls.report.utility.FileName;
import com.xls.report.utility.ReportData;

/**
 * @author - rahul.rathore
 */
public class ExcelReport {

	private static FileOutputStream _reportFile;
	private static XSSFWorkbook _book;

	public static String generateReport(String xmlFile) throws SAXException,
			IOException, ParserConfigurationException {
		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData
				.getTestMethodDetail(xmlFile);
		_book = ReportData.createExcelFile(data);
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
		return fileName;
	}

	public static String createOrUpdateReport(String srcExcel, String srcXml)
			throws SAXException, IOException, ParserConfigurationException {

		File createFile = new File(srcExcel);
		if (!createFile.exists()) {
			return generateReport(srcXml);
		}

		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData
				.getTestMethodDetail(srcXml);
		XSSFWorkbook book = ExcelConfiguration.getBook(srcExcel);
		for (String sheetName : data.keySet()) {
			if (ExcelConfiguration.isSheetPresent(book, sheetName)) {
				XSSFSheet xlSheet = ExcelConfiguration.getSheet(book, sheetName);
				HashMap<String, ArrayList<String>> sheetMap = (HashMap<String, ArrayList<String>>) data
						.get(sheetName);
				HashMap<String, ArrayList<String>> newSheetMap = ExcelConfiguration
						.appendExcelData(xlSheet, sheetMap);
				data.put(sheetName, newSheetMap);
			}
		}
		for (int i = 0; i < ExcelConfiguration.getTotalSheetCount(book); i++) {
			XSSFSheet xlSheet = book.getSheetAt(i);
			if (data.containsKey(xlSheet.getSheetName()))
				continue;
			HashMap<String, ArrayList<String>> sheetMap = ExcelConfiguration
					.appendExcelDataToMap(xlSheet);
			data.put(xlSheet.getSheetName(), sheetMap);
		}

		_book = ReportData.createExcelFile(data);
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
		return fileName;

	}

}
