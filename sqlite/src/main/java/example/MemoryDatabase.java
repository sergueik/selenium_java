package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.sql.RowId;

// https://bitbucket.org/xerial/sqlite-jdbc
// https://docs.oracle.com/javase/tutorial/jdbc/basics/sqlrowid.html

public class MemoryDatabase {

	private static final Random r = new Random();

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite::memory:");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);

			statement.executeUpdate("DROP TABLE IF EXISTS " + "person");

			statement.executeUpdate(String.format(
					"CREATE TABLE IF NOT EXISTS %s "
							+ "(id INTEGER PRIMARY KEY NOT NULL, name STRING NOT NULL)",
					"person"));

			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'hrr')");
			statement.executeUpdate("insert into person values(3, 'moo')");

			ResultSet rs = statement.executeQuery("select name, id from person");
			int cnt = 0;
			// NPE
			// System.err.println("Got row id: " + rowId.toString());
			while (rs.next()) {
				RowId rowId = rs.getRowId(cnt++);
				System.err.println("Got row id: " + rowId); // null
				System.err.println("id = " + rs.getInt("id") + "\t" + " name = "
						+ rs.getString("name"));
			}

			statement.executeUpdate("DELETE FROM person WHERE name IN (SELECT name FROM  PERSON LIMIT 1 )");

			final String name = "name";

			for (cnt = 0; cnt != 20; cnt++) {

				try {
					statement = connection.createStatement();
					statement.setQueryTimeout(10);

					PreparedStatement preparedStatement = connection
							.prepareStatement("INSERT INTO person (id, name) VALUES (?, ?)");
					int id = r.nextInt(1_000_000_000);
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, String.format("%s-%d", name, cnt));
					preparedStatement.execute();

				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
			rs = statement.executeQuery("select name, id from person");
			while (rs.next()) {
				System.err.println("name = " + rs.getString("name") + "\t" + "id = "
						+ rs.getInt("id"));
			}
		} catch (SQLException e) {
			// NOTE: the "out of memory" error message is likely because no database
			// file is found
			// NOTE: [SQLITE_CONSTRAINT] Abort due to constraint violation
			// (UNIQUE constraint failed: person.id)
			// when unique key start to repeat
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
