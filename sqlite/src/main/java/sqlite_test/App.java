package sqlite_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.RowId;
// https://bitbucket.org/xerial/sqlite-jdbc

public class App {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite::memory:");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("drop table if exists person");
			statement.executeUpdate("create table person (id integer, name string)");

			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'hrr')");
			statement.executeUpdate("insert into person values(3, 'moo')");

			ResultSet rs = statement
					.executeQuery("select rowid, name, id from person");
			RowId rowId = rs.getRowId(1);
			System.err.println("Got row id: " + rowId); // null
			// NPE
			// System.err.println("Got row id: " + rowId.toString()); 
			while (rs.next()) {
				System.err
						.println("rowid = " + rs.getString("rowid") + "\t" + " name = "
								+ rs.getString("name") + "\t" + "id = " + rs.getInt("id"));
			}

			// https://docs.oracle.com/javase/tutorial/jdbc/basics/sqlrowid.html

			statement.executeUpdate("delete from person where name = 'hrr'");

			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into person (id , name ) values(?, ?)");

			preparedStatement.setString(2, "james");
			preparedStatement.setInt(1, 42);
			// preparedStatement.setRowId(1, rowId);
			preparedStatement.execute();
			rs = statement.executeQuery("select rowid, name, id from person");
			while (rs.next()) {
				System.err
						.println("rowid = " + rs.getString("rowid") + "\t" + " name = "
								+ rs.getString("name") + "\t" + "id = " + rs.getInt("id"));
			}
		} catch (SQLException e) {
			// NOTE: the "out of memory" error message is likely because no database
			// file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}
}
