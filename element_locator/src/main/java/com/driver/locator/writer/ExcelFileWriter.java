package com.driver.locator.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.driver.locator.model.LocatorModel;
import com.driver.locator.utility.ResourceHelper;

public class ExcelFileWriter implements FileWrite {

	private static final String SHEET_NAME = "Locator";

	private XSSFWorkbook getWorkBook()
			throws InvalidFormatException, IOException {
		return new XSSFWorkbook();
	}

	@Override
	public boolean writeToFile(String fileName, List<LocatorModel> dData) {
		int i = 0;
		try (XSSFWorkbook book = getWorkBook()) {
			XSSFSheet sheet = book.createSheet(SHEET_NAME);
			for (LocatorModel model : dData) {
				XSSFRow row = sheet.createRow(i++);
				row.createCell(0).setCellValue(model.getLocatorType());
				row.createCell(1).setCellValue(model.getLocatorValue());
			}
			book.write(new FileOutputStream(new File(
					ResourceHelper.getResourcePath("excel/") + fileName + ".xlsx")));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
