package passwordkeeper;

import java.sql.*;

public class DB {

	private static DB DBInstance;
	private static Connection con;

	private DB() {
	}

	public static DB getInstance() {
		if (DBInstance == null) {
			DBInstance = new DB();
		}
		return DBInstance;
	}

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager
						.getConnection("jdbc:sqlite:" + Constants.PATH_TO_DB);
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return con;
	}

}
