package example;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class SelectScenario1Test {

	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static Recordset recordset = null;
	private static String query = null;
	private static int maxRow = 3;

	@BeforeClass
	public void beforeTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/src/test/resources/StatesData.xlsx");

	}

	@Test
	public void basicSelectTest() throws FilloException {
		// Fetch everything
		query = "select * from States";
		recordset = connection.executeQuery(query);
		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void whereConditionSelectTest() throws FilloException {
		// Fetch data using select with compound where clause
		query = "select State, Capital, Sno, Region from States where Region='South' and Capital = 'Hyderabad'";
		recordset = connection.executeQuery(query);

		System.err
				.println("==========================================================");
		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void whereConditionLikeSelectTest() throws FilloException {
		// Fetch data using select with like where clause
		query = "select * from States where State like 'T%'";
		recordset = connection.executeQuery(query);

		System.err
				.println("==========================================================");
		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}

	@Test
	public void chainedWhereClauseSelectTest() throws FilloException {
		query = "select * from States";
		// Fetch data using select with chained where clause
		recordset = connection.executeQuery(query).where("Region='South'")
				.where("State='Telangana'");

		System.err
				.println("==========================================================");
		int currentRow = 0;
		while (recordset.next()) {
			System.err.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
			if (currentRow++ > maxRow) {
				System.err.println(String.format("Only showing first %d rows", maxRow));
				break;
			}
		}
	}
}
