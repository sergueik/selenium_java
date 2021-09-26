package example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.RowId;

// https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

public class TwoJDBCEx {

	final static Map<String, String> env = System.getenv();
	final static List<String> databaseNames = Arrays.asList("test.db",
			"test2.db");
	static final Random r = new Random();

	public static List<Connection> initConnections(List<String> databaseFiles) {
		List<Connection> connections = new ArrayList<>();
		for (String databaseFile : databaseFiles) {
			try {
				// does one need the call ?
				// Class.forName("org.sqlite.JDBC");
				Connection connection = null;
				connection = DriverManager.getConnection(
						"jdbc:sqlite:" + databaseFile.replaceAll("\\\\", "/"));
				System.out.println("Created database connection successfully to "
						+ connection.toString());
				System.err.println("Querying data : "
						+ connection.getMetaData().getDatabaseProductName() + "\t"
						+ "catalog: " + connection.getCatalog() + "\t" + "schema: "
						+ connection.getSchema());
				connections.add(connection);
			} catch (/* ClassNotFoundException | */ SQLException e) {
				System.err.println("Unexpected exception " + e.getClass().getName()
						+ ": " + e.getMessage());
				System.exit(1);
			}
		}
		System.err.println("returning " + connections.size() + " connections");
		return connections;
	}

	public static void main(String[] args) throws ClassNotFoundException {

		final List<String> databaseFiles = new ArrayList<>();
		List<Connection> connections = new ArrayList<>();
		final String tableName = "COMPANY";
		for (String databaseName : databaseNames) {
			databaseFiles.add(String.format("%s%s%s",
					env.get(osName.equals("windows") ? "USERPROFILE" : "HOME"),
					File.separator, databaseName));
		}

		connections = initConnections(databaseFiles);
		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		String sql = null;
		Connection connection2 = null;
		ResultSet resultSet2 = null;
		Statement statement2 = null;
		PreparedStatement preparedStatement2 = null;
		String sql2 = null;
		try {
			connection = connections.get(0);
			System.err.println("get connection");
			System.out.println(
					"Opened database connection successfully to " + databaseNames.get(0));
			System.err.println(
					"Querying data : " + connection.getMetaData().getDatabaseProductName()
							+ "\t" + "catalog: " + connection.getCatalog() + "\t" + "schema: "
							+ connection.getSchema());

			statement = connection.createStatement();
			sql = String.format(
					"CREATE TABLE IF NOT EXISTS %s (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL,"
							+ "ADDRESS CHAR(50), SALARY REAL)",
					tableName);
			System.err.println("creating table");
			statement.executeUpdate(sql);
			System.err.println("created table");
			// https://stackoverflow.com/questions/50714123/how-to-list-the-tables-names-in-the-sqlite3-database-in-java
			resultSet = connection.getMetaData().getTables(null, null, null, null);
			while (resultSet.next()) {
				System.err.println(resultSet.getString("TABLE_NAME"));
			}
			statement.executeUpdate("delete from COMPANY");
			// statement.close();

			preparedStatement = connection.prepareStatement(
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
			connection2 = connections.get(1);
			statement2 = connection2.createStatement();
			statement2.executeUpdate(sql);
			statement2.executeUpdate("delete from COMPANY");
			// statement2.close();

			resultSet = statement
					.executeQuery("SELECT ROWID, NAME, ID, AGE FROM COMPANY");
			while (resultSet.next()) {
				System.err.println("rowid = " + resultSet.getString("ROWID") + "\t"
						+ " name = " + resultSet.getString("NAME") + "\t" + "id = "
						+ resultSet.getInt("ID") + "\t" + "age = "
						+ resultSet.getInt("AGE"));
				preparedStatement2 = connection2.prepareStatement(
						"INSERT INTO COMPANY (NAME, ID, AGE) VALUES (?, ?, ?)");
				preparedStatement2.setString(1, resultSet.getString("NAME"));
				preparedStatement2.setInt(2, resultSet.getInt("ID") + 1);
				preparedStatement2.setInt(3, resultSet.getInt("AGE"));
				preparedStatement2.execute();
			}
			resultSet2 = statement2
					.executeQuery("SELECT ROWID, NAME, ID, AGE FROM COMPANY");
			while (resultSet2.next()) {
				System.err.println("rowid = " + resultSet2.getString("ROWID") + "\t"
						+ " name = " + resultSet2.getString("NAME") + "\t" + "id = "
						+ resultSet2.getInt("ID") + "\t" + "age = "
						+ resultSet2.getInt("AGE"));
			}
		} catch (SQLException e) {
			System.err.println("Exception (ignored)" + e.getMessage());
			/*
			} catch (Exception e) {
			System.err.println("Unexpected exception " + e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(1);
			*/
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
