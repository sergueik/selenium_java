package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class WriteExcel {

	public static void writeStatus(String sheetName, int rowNumber)
			throws IOException {
		File file = new File(String.format("%s\\Desktop\\Test Suite Copy.xls",
				getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei")));
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

}
