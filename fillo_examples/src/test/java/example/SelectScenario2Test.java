package example;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelectScenario2Test {

	private static Connection connection;
	private static Fillo fillo;
	private static Recordset recordset;
	private static String query;
	private static int maxRow = 3;

	@BeforeClass
	public void beforeTests() throws Exception {
		fillo = new Fillo();
		connection = fillo.getConnection(System.getProperty("user.dir")
				+ "/src/test/resources/SelectExample.xlsx");
	}

	@AfterClass
	public void afterTests() throws Exception {
		recordset.close();
		connection.close();

	}

	@Test
	public void selectWhereBasicTest() throws Exception {
		query = "Select * from Sheet1 where Country='India'";
		recordset = connection.executeQuery(query);

		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country")
					+ "\n" + "Capital City:   " + recordset.getField("Capital City"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void selectWhereBetweenTest() throws Exception {
		query = "Select * from Sheet1 where ID BETWEEN '25' AND '40'";
		recordset = connection.executeQuery(query);
		int currentRow = 0;

		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country")
					+ "\n" + "Capital City:   " + recordset.getField("Capital City"));
			System.err.println();
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void selectCompoundWhereTest() throws Exception {
		query = "Select * from Sheet1 where ID BETWEEN '25' AND '40' and \"Capital City\" like 'C%'";
		recordset = connection.executeQuery(query);
		// .where("Country like 'C%'");
		// com.codoid.products.exception.FilloException: Invalid WHERE condition
		// .where("'Capital City' like 'B%'");

		int currentRow = 0;
		while (recordset.next()) {
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
			System.err.println("Country:        " + recordset.getField("Country")
					+ "\n" + "Capital City:   " + recordset.getField("Capital City"));
			System.err.println();
		}
	}

	@Test
	public void selectTest3() throws Exception {
		query = "Select * from Sheet1 where country like '%IN%'";
		recordset = connection.executeQuery(query);
		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country")
					+ "\n" + "Capital City:   " + recordset.getField("Capital City"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void selectTest4() throws Exception {
		query = "Select Country, \"Capital City\" from Sheet1 where country=\"Capital City\" limit 3 ";

		// com.codoid.products.exception.FilloException: Invalid Query -
		// SELECT COUNTRY, "CAPITAL CITY" LIMIT 3 FROM SHEET1 WHERE COUNTRY="CAPITAL
		// CITY"
		recordset = connection.executeQuery(query);

		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country")
					+ "\n" + "Capital City:   " + recordset.getField("Capital City"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

}
