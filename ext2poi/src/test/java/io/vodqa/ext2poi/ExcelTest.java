package io.vodqa.ext2poi;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 20-May-17.
 */
public class ExcelTest {

	private String wbName = "testWorkbook.xlsx";
	private String sheetName1 = "Sheet1";
	private String sheetName2 = "Sheet2";
	private String sheetName3 = "Sheet3";
	private String workbookFilePath = System.getProperty("user.dir")
			+ "/src/test/resources/" + wbName;

	@Test(enabled = false)
	public void test1_getColumnValuesList() {
		List<String> list = Excel.Read.getListOfColumnValues(workbookFilePath,
				sheetName1, "ColHeader1", false);

		for (int i = 0; i < list.size(); i++) {
			System.out.println("Value " + i + ": " + list.get(i).toString());
			System.out.println(
					"Data type " + i + ": " + list.get(i).getClass().getTypeName());
		}
	}

	@Test(enabled = false)
	public void test2_getCellValue() {
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader1");
		Assert.assertEquals(cellValue, "String 1");

		cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader2");
		Assert.assertEquals(cellValue, "String 2");

		cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader3");
		Assert.assertEquals(cellValue, "String 3");

		cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader4");
		Assert.assertEquals(cellValue, "String 4");

		cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader5");
		Assert.assertEquals(cellValue, "String 5");

		// cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
		// "ColHeader6");
		// System.out.println("Cell value: " + cellValue);

		cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 5,
				"ColHeader7");
		Assert.assertEquals(cellValue, "String 7");

	}

	@Test(enabled = false)
	public void test3_getCellValue() {
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 3,
				"ColHeader2");
		String cellValue2 = Excel.Read.getCellValue(workbookFilePath, sheetName1,
				"2", "ColHeader2");
		Assert.assertEquals(cellValue, cellValue2);
	}

	@Test(enabled = false)
	public void test4_getCellValue() {
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1,
				"AA7");
		Assert.assertEquals(cellValue, "test4_getCellValue");
	}

	@Test(enabled = false)
	public void test5_getCellValue() {
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1, 10,
				10);
		Assert.assertEquals(cellValue, "test5_getCellValue");
	}

	@Test(enabled = false)
	public void test6_getCellValue() {
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1,
				"2", 10);
		Assert.assertEquals(cellValue, "test6_getCellValue");
	}

	@Test(enabled = false)
	public void test7_setDateFormat() {
		Excel.Utils.setDateFormat("dd-MM-yyyy");
		String cellValue = Excel.Read.getCellValue(workbookFilePath, sheetName1,
				"5", "ColHeader2");
		Assert.assertEquals(cellValue, "13-05-1987");
	}

	@Test(enabled = false)
	public void test8_getColValuesList() {
		List<String> list = Excel.Read.getListOfColumnValues(workbookFilePath,
				sheetName1, 2, false);

		for (int i = 0; i < list.size(); i++) {
			System.out.println("Value " + i + ": " + list.get(i).toString());
		}
	}

	@Test(enabled = false)
	public void test9_getColValuesList() {
		List<String> list = Excel.Read.getListOfColumnValues(workbookFilePath,
				sheetName1, 3, 2, true);

		for (int i = 0; i < list.size(); i++) {
			System.out.println("Value " + i + ": " + list.get(i).toString());
		}
	}

	// NPE
	@Test(enabled = false)
	public void test10_getRowValuesList() {
		List<String> list = Excel.Read.getListOfRowValues(workbookFilePath,
				sheetName1, 2, 0, 4, false);
		for (String value : list) {
			System.out.println("Value: " + value);
		}
	}

	@Test
	public void test11_writeToCell() {
		Excel.Write.writeStringValueToCell(workbookFilePath, "Sheet2", "B5",
				"Test");
	}

}
