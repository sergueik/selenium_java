package example;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static final String newFilePath = System.getProperty("user.dir")
			+ "/src/test/resources/TestData.new.xlsx";

	@BeforeClass
	public void beforeTest() throws FilloException {
		connection = fillo.getConnection(filePath);
	}

	@Test
	public void insertExampleTest() throws FilloException, IOException {
		List<String> tableNames = connection.getMetaData().getTableNames();
		for (String tableName : tableNames) {
			System.err.println("Table: " + tableName);
		}
		List<Object[]> result = new LinkedList<>();
		Map<String, String> columns = new HashMap<>();
		FileOutputStream fileOut = new FileOutputStream(newFilePath);
		XSSFWorkbook workBook = new XSSFWorkbook(filePath);

		XSSFSheet sheet = workBook.getSheetAt(0);

		Iterator<Row> rows = sheet.rowIterator();
		Iterator<org.apache.poi.ss.usermodel.Cell> cells;
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			XSSFCell cell;
			if (row.getRowNum() == 0) {
				cells = row.cellIterator();
				while (cells.hasNext()) {

					cell = (XSSFCell) cells.next();
					int columnIndex = cell.getColumnIndex();
					String columnHeader = cell.getStringCellValue();
					String columnName = CellReference
							.convertNumToColString(cell.getColumnIndex());
					columns.put(columnName, columnHeader);
					System.err
							.println(columnIndex + "[" + columnName + "]: " + columnHeader);
				}

				List<String> headers = Arrays
						.asList(new String[] { "Column1", "Column2", "Column3" });
				for (int col = 0; col < headers.size(); col++) {
					cell = row.createCell(col + 1);
					cell.setCellValue(headers.get(col));
					System.err
							.println("Writing " + row + " " + col + "  " + headers.get(col));
				}
				// java.io.IOException: Cannot write data, document seems to have been
				// closed already
				// workBook.close();

				// attempting to read and write the same file
				// org.apache.poi.EmptyFileException:
				// The supplied file was empty (zero bytes long)

				workBook.write(fileOut);
				workBook.close();
				fileOut.flush();
				fileOut.close();

			}
		}
		connection.close();
		connection = fillo.getConnection(filePath);
		// query = "insert into States(MethodName,Column1)
		// values('anotherMethod','1')";
		connection.executeUpdate(query);
		// when bad column name:
		// currently corrupting the "TestData.new.xlsx"
		// the data could not be loaded copletely because the maximum number of rows
		// per sheet was exceeded
		query = "insert into States(TestMethod,Column1,Column2,Column3) values('anotherMethod','1','1','1')";
		connection.executeUpdate(query);
	}
}
