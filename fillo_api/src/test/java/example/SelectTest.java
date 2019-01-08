package example;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class SelectTest {

	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static Recordset recordset = null;
	private static String query = null;

	@Before
	public void beforeTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");

	}

	@Test
	public void basicSelectTest() throws FilloException {
		// Fetch everything
		query = "select * from States";
		recordset = connection.executeQuery(query);
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	@Test
	public void whereConditionSelectTest() throws FilloException {
		// Fetch data using select with compound where clause
		query = "select State, Capital, Sno, Region from States where Region='South' and Capital = 'Hyderabad'";
		recordset = connection.executeQuery(query);

		System.out
				.println("==========================================================");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	@Test
	public void whereConditionLikeSelectTest() throws FilloException {
		// Fetch data using select with like where clause
		query = "select * from States where State like 'T%'";
		recordset = connection.executeQuery(query);

		System.out
				.println("==========================================================");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	@Test
	public void chainedWhereClauseSelectTest() throws FilloException {
		query = "select * from States";
		// Fetch data using select with chained where clause
		recordset = connection.executeQuery(query).where("Region='South'")
				.where("State='Telangana'");

		System.out
				.println("==========================================================");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}
}
