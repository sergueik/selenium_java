package example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.sql.RowId;

// https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

public class FileDatabase {

	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName("org.sqlite.JDBC");
		final String tableName = "COMPANY";
		final Random r = new Random();
		final Map<String, String> env = System.getenv();
		final String databaseName = String.format("%s%stest.db",
				env.get(osName.equals("windows") ? "USERPROFILE" : "HOME"),
				File.separator);
		Connection connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:" + databaseName.replaceAll("\\\\", "/"));
			System.out.println(
					"Opened database connection successfully to " + databaseName);
			System.err.println(
					"Querying data : " + connection.getMetaData().getDatabaseProductName()
							+ "\t" + "catalog: " + connection.getCatalog() + "\t" + "schema: "
							+ connection.getSchema());

			Statement statement = connection.createStatement();
			String sql = String.format(
					"CREATE TABLE IF NOT EXISTS %s (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL,"
							+ "ADDRESS CHAR(50), SALARY REAL)",
					tableName);
			statement.executeUpdate(sql);
			statement.executeUpdate("delete from COMPANY");
			statement.close();

			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO COMPANY (NAME, ID, AGE) VALUES (?, ?, ?)");
			java.util.List<java.util.List<Object>> rows = Arrays.asList(
					Arrays.asList("RedHat", r.nextInt(100000), 28),
					Arrays.asList("IBM", r.nextInt(100000), 110));
			for (java.util.List<Object> row : rows) {
				preparedStatement.setString(1, row.get(0).toString());
				preparedStatement.setInt(2, Integer.parseInt(row.get(1).toString()));
				preparedStatement.setInt(3, Integer.parseInt(row.get(2).toString()));
				preparedStatement.execute();

			}
			ResultSet resultSet = statement
					.executeQuery("SELECT ROWID, NAME, ID, AGE FROM COMPANY");
			while (resultSet.next()) {
				System.err.println("rowid = " + resultSet.getString("ROWID") + "\t"
						+ " name = " + resultSet.getString("NAME") + "\t" + "id = "
						+ resultSet.getInt("ID") + "\t" + "age = "
						+ resultSet.getInt("AGE"));
			}

			statement = connection.createStatement();

		} catch (SQLException e) {
			System.err.println("Exception (ignored)" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected exception " + e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(1);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}

	private static String osName = getOSName();

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

}
