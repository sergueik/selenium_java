package com.fillo.excel;

import org.junit.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class SelectTest {

	private Fillo fillo = new Fillo();
	private Connection connection = null;
	private Recordset recordset = null;

	@Test
	public void dataFetchWithselectTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");
		String sqlQuery = "select * from States";
		recordset = connection.executeQuery(sqlQuery);
		System.err.println("test 1");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));

		String newQuery = "select * from States where Region='South'";
		recordset = connection.executeQuery(newQuery);

		System.out
				.println("==========================================================");
		// Displaying data using select with where cluase
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));

		String newQueryExcep = "select * from States";
		recordset = connection.executeQuery(newQueryExcep).where("Region='South'")
				.where("State='Telangana'");

		System.out
				.println("==========================================================");
		// Displaying data using select with where cluase
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}
}
