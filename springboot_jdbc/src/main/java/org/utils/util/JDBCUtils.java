package org.utils.util;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUtils {

	private static final Logger logger = Logger
			.getLogger(JDBCUtils.class.getName());
	public static Connection connection = null;
	public static PreparedStatement preparedStatement = null;
	public static ResultSet resultSet = null;

	private JDBCUtils() {
	}

	public static Connection getConnection() {
		// ?
		String url = "jdbc:sqlite:c:\\Users\\Serguei\\sqlite\\springboot.db"; //  "jdbc:sqlserver://localhost:1434;databaseName=student";
		String username = "";
		String password = "";
		if (connection != null) {
			return connection;
		}
		try {
			Class.forName("org.sqlite.JDBC" /* "com.microsoft.sqlserver.jdbc.SQLServerDriver" */)
					.newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		return connection;
	}

	public static void close() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, null, e);
		}
	}

	public static List<?> TranverseToList(ResultSet rs, Class<?> clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		ResultSetMetaData rsm = rs.getMetaData();
		int colNumber = rsm.getColumnCount();
		List<Object> list = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {
			Object obj = clazz.newInstance();
			for (int i = 1; i <= colNumber; i++) {
				Object value = rs.getObject(i);
				for (Field f : fields) {
					if (f.getName().equals(rsm.getColumnName(i))) {
						boolean flag = f.isAccessible();
						f.setAccessible(true);
						f.set(obj, value);
						f.setAccessible(flag);
						break;
					}
				}
			}
			list.add(obj);
		}
		return list;
	}
}
