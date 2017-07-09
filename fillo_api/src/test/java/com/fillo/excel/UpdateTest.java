package com.fillo.excel;
import org.junit.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
public class UpdateTest {
	Fillo fillo = new Fillo();
	Connection connection = null;
	Recordset recordert = null;
	
	@Test
	public void updateExampleTest() throws FilloException{
		connection = fillo.getConnection(System.getProperty("user.dir")+"/TestData/StatesData.xlsx");
		String query = "update States set Capital='Hyderabad' where State='Andhra Pradesh'";
		connection.executeUpdate(query);
	}	
}
