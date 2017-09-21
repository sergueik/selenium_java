package org.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class MainClass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		FileInputStream file = new FileInputStream(
				"C:\\Users\\ashokkumar.r.khape\\Desktop\\Test Suite Copy.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet indexSheet = workbook.getSheet("Index");
		Row indexRow;
		HashMap<Integer, ArrayList<String>> stepMap = null;
		List<String> stepList = new ArrayList<String>();

		KeywordLibrary.loadProperties();

		for (int i = 1; i <= indexSheet.getLastRowNum(); i++) {
			indexRow = indexSheet.getRow(i);
			if (indexRow.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")) {
				stepMap = ReadExcel.readSteps(indexRow.getCell(0).getStringCellValue());
				System.out.println(stepMap);

				for (int j = 0; j < stepMap.size(); j++) {
					stepList = stepMap.get(j);
					if (stepList.get(0).equals("openBrowser")) {
						KeywordLibrary.openBrowser();
						WriteExcel.writeStatus(indexRow.getCell(0).getStringCellValue(),
								j + 1);
					} else {
						KeywordLibrary.callMethod(stepList.get(0), stepList.get(1),
								stepList.get(2), stepList.get(3));
						WriteExcel.writeStatus(indexRow.getCell(0).getStringCellValue(),
								j + 1);
					}
				}
			}
		}

		System.out.println("Done");

		workbook.close();

	}

}
