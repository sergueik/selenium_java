package example;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class InsertScenario1Test {
	private static Fillo fillo = new Fillo();
	private static Connection connection = null;
	private static String query = null;

	@BeforeClass
	public void beforeTest() throws FilloException {
		connection = fillo.getConnection(
				System.getProperty("user.dir") + "/src/test/resources/StatesData.xlsx");
	}

	@Test
	public void insertExampleTest() throws FilloException {
		query = "insert into States(Sno,State,Capital,Region) values(8,'Rajastan','Jaipur','North')";
		connection.executeUpdate(query);
	}
}
