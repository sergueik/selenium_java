package com.fillo.excel;

import org.junit.Test;
import org.junit.Ignore;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class InsertTest {
	Fillo fillo = new Fillo();
	Connection connection = null;

	@Ignore
	@Test
	public void insertExampleTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");
		String query = "insert into States(Sno,State,Capital,Region) values(8,'Rajastan','Jaipur','North')";
		connection.executeUpdate(query);
	}
}
