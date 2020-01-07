package sqlite_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.sql.RowId;

// https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

public class FileDatabase {
	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName("org.sqlite.JDBC");
		final String tableName = "COMPANY";
		final Map<String, String> env = System.getenv();
		final String databaseName = String.format("%s\\test.db",
				env.get("USERPROFILE"));
		Connection connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:" + databaseName.replaceAll("\\\\", "/"));
			System.out.println("Opened database connection successfully");

			Statement statement = connection.createStatement();
			String sql = String.format(
					"CREATE TABLE %s (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL,"
							+ "ADDRESS CHAR(50), SALARY REAL)",
					tableName);
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("Exception (ignored)" + e.getMessage());
			System.exit(0);
		} catch (Exception e) {
			System.err.println("Unexpected exception " + e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(1);
		}
		System.out
				.println(String.format("Table %s was created successfully", tableName));
	}
}
