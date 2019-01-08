package example;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateTest {
	private static Connection connection;
	private static Fillo fillo;
	private static Recordset recordset;
	private static String query;

	@BeforeClass
	public void beforeTests() throws Exception {
		fillo = new Fillo();
		connection = fillo.getConnection(System.getProperty("user.dir")
				+ "/src/test/resources/UpdateExample.xlsx");
	}

	@AfterClass
	public void afterTests() throws Exception {
		recordset.close();
		connection.close();
	}

	@Test
	public void updateTest1() throws Exception {
		String query = "Update Sheet1 Set Country='Niiue' where ID='7'";
		connection.executeUpdate(query);

		recordset = connection.executeQuery("Select Country From Sheet1");
		for (int i = 0; i < 7; i++) {
			recordset.next();
		}
		System.err.println(recordset.getField(0).value());
	}

	@Test
	public void updateTest2() throws Exception {
		query = "Update Sheet1 Set Country='Niue' where ID='7'";
		connection.executeUpdate(query);

		recordset = connection.executeQuery("Select Country From Sheet1");
		for (int i = 0; i < 7; i++) {
			recordset.next();
		}
		System.err.println(recordset.getField(0).value());
	}
}
