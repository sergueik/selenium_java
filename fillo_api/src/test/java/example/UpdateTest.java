package example;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class UpdateTest {
	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	// private static Recordset recordset = null;
	private static String query = null;

	@Before
	public void beforeTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/TestData/StatesData.xlsx");

	}

	@Test
	public void updateExampleTest() throws FilloException {
		query = "update States set Capital='Hyderabad' where State='Andhra Pradesh'";
		connection.executeUpdate(query);
	}
}
