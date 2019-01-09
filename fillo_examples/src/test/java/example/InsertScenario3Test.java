package example;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InsertScenario3Test {
	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static String query = null;
	private static final String filePath = System.getProperty("user.dir")
			+ "/src/test/resources/TestData.xlsx";
	private static final String saveFilePath = System.getProperty("user.dir")
			+ "/src/test/resources/TestData.BACKUP.xlsx";
	private static List<String> columnHeaders = new ArrayList<>();
	private static String newColumn = null;

	@BeforeClass
	public void beforeTest() throws FilloException {
		columnHeaders = readColumnHeaders();
		newColumn = String.format("Column%d", columnHeaders.size());
		appendColumnHeader(newColumn);
		columnHeaders = readColumnHeaders();
		System.err.println("Appnded new column before query test");
	}

	private List<String> readColumnHeaders() {

		Map<String, String> columns = new HashMap<>();
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(filePath);
		} catch (IOException e) {
		}
		if (workBook == null) {
			return new ArrayList<>();
		}
		XSSFSheet sheet = workBook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				Iterator<org.apache.poi.ss.usermodel.Cell> cells = row.cellIterator();
				while (cells.hasNext()) {

					XSSFCell cell = (XSSFCell) cells.next();
					int columnIndex = cell.getColumnIndex();
					String columnHeader = cell.getStringCellValue();
					String columnName = CellReference
							.convertNumToColString(cell.getColumnIndex());
					columns.put(columnName, columnHeader);
					System.err
							.println(columnIndex + "[" + columnName + "]: " + columnHeader);
				}
			}
		}
		try {
			workBook.close();
			return new ArrayList<String>(columns.keySet());
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

	private void appendColumnHeader(String columnHeader) {
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(filePath);
		} catch (IOException e) {
		}
		if (workBook == null) {
			return;
		}
		XSSFSheet sheet = workBook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		Iterator<org.apache.poi.ss.usermodel.Cell> cells;
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				XSSFCell cell = row.createCell(columnHeaders.size());
				cell.setCellValue(columnHeader);
				System.err.println("Adding column # " + (columnHeaders.size())
						+ " with the name: " + columnHeader);
				cells = row.cellIterator();
			}
		}
		try {
			// TODO: saving properly, without dummy temp file

			FileOutputStream fileOut = new FileOutputStream(saveFilePath);
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			workBook.close();
		} catch (IOException e) {
		}
		File file = new File(saveFilePath);

		if (file.delete()) {
			System.out.println("File " + saveFilePath + " deleted successfully");
		} else {
			System.out.println("Failed to delete the file " + saveFilePath);
		}
	}

	@Test
	public void insertExampleTest() throws FilloException {

		connection = fillo.getConnection(filePath);
		List<String> tableNames = connection.getMetaData().getTableNames();
		for (String tableName : tableNames) {
			System.err.println("Table: " + tableName);
		}
		query = String.format("update States set %s='true' where TestMethod='someTest'",
				newColumn);
		System.err.println("Runnin	query: " + query);
		connection.executeUpdate(query);
		// When column name is "bad":
		// fillo may be corrupting the spreadsheet:
		// attempt to open the resulting file "TestData.new.xlsx" shows dialog:
		// the data could not be loaded completely because
		// the maximum number of rows per sheet was exceeded
	}
}
