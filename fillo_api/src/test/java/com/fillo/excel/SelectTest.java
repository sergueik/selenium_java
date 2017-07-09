package com.fillo.excel;

import org.junit.Test;
import org.junit.Ignore;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class SelectTest {

	private Fillo fillo = new Fillo();
	private Connection connection = null;
	private Recordset recordset = null;

	@Ignore
	@Test
	public void basicSelectTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");
		// Fetch everything
		String sqlQuery = "select * from States";
		recordset = connection.executeQuery(sqlQuery);
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	// @Ignore
	@Test
	public void whereConditionSelectTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");
		// Fetch data using select with compound where clause
		String newQuery = "select State, Capital, Sno, Region from States where Region='South' and Capital = 'Hyderabad'";
		recordset = connection.executeQuery(newQuery);

		System.out
				.println("==========================================================");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	// @Ignore
	@Test
	public void whereConditionLikeSelectTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");
		// Fetch data using select with like where clause
		String newQuery = "select * from States where State like 'T%'";
		recordset = connection.executeQuery(newQuery);

		System.out
				.println("==========================================================");
		while (recordset.next())
			System.out.println("Sno is: " + recordset.getField("Sno")
					+ " -- State Name is: " + recordset.getField("State")
					+ " -- Capital Name is: " + recordset.getField("Capital")
					+ " -- Region is: " + recordset.getField("Region"));
	}

	// @Ignore
	@Test
	public void chainedWhereClauseSelectTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");

		String newQueryExcep = "select * from States";
		// Fetch data using select with chained where clause
		recordset = connection.executeQuery(newQueryExcep).where("Region='South'")
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
