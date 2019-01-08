package example;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelectTest {

	private static Connection connection;
	private static Fillo fillo;
	private static Recordset recordset;
	private static String query;

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
	public void selectTest1() throws Exception {
		query = "Select * from Sheet1 where Country='India'";
		recordset = connection.executeQuery(query);

		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country"));
			System.err
					.println("Capital City:   " + recordset.getField("Capital City"));
		}
	}

	@Test
	public void selectTest2() throws Exception {
		query = "Select * from Sheet1 where ID BETWEEN '25' AND '40'";
		recordset = connection.executeQuery(query);

		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country"));
			System.err
					.println("Capital City:   " + recordset.getField("Capital City"));
			System.err.println();
		}
	}

	@Test
	public void selectTest3() throws Exception {
		query = "Select * from Sheet1 where country like '%IN%'";
		recordset = connection.executeQuery(query);

		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country"));
			System.err
					.println("Capital City:   " + recordset.getField("Capital City"));
			System.err.println();
		}
	}

	@Test
	public void selectTest4() throws Exception {
		query = "Select * from Sheet1 where country=\"Capital City\" ";
		recordset = connection.executeQuery(query);

		while (recordset.next()) {
			System.err.println("Country:        " + recordset.getField("Country"));
			System.err
					.println("Capital City:   " + recordset.getField("Capital City"));
			System.err.println();
		}
	}

}
