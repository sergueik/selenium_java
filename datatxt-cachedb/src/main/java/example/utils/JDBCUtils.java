package example.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDBCUtils {

	private static final Logger logger = Logger
			.getLogger(JDBCUtils.class.getName());
	public static Connection connection = null;
	public static PreparedStatement preparedStatement = null;
	public static ResultSet resultSet = null;
	final static Map<String, String> env = System.getenv();

	private static Utils utils = Utils.getInstance();

	private static String osName = utils.getOSName();
	private static String sqliteDatabaseName = "cache.db";
	final static String databasePath = String.format("%s%s%s",
			env.get(osName.equals("windows") ? "USERPROFILE" : "HOME"),
			File.separator, sqliteDatabaseName);

	private static String datasourceUrl = "jdbc:sqlite:"
			+ databasePath.replaceAll("\\\\", "/");

	private JDBCUtils() {
	}

	// NOTE: not connecting to "cache.datasource.url"
	public static Connection getConnection() {

		Properties prop = utils.getProperties();

		String url = utils
				.resolveEnvVars(prop.getProperty("datasource.url", datasourceUrl));
		String filename = utils
				.resolveEnvVars(prop.getProperty("datasource.filename"));
		logger.info("Connect to database " + url + " " + filename);
		String username = prop.getProperty("datasource.username");
		String password = prop.getProperty("datasource.password");

		if (connection != null) {
			return connection;
		}
		try {
			Class.forName(prop.getProperty("datasource.driver-class-name"))
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

	public static List<?> TranverseToList(ResultSet resultSet,
			Class<?> targetClass)
			throws SQLException, InstantiationException, IllegalAccessException {
		ResultSetMetaData resultMetaData = resultSet.getMetaData();
		int columnCount = resultMetaData.getColumnCount();
		List<Object> list = new ArrayList<>();
		Field[] fields = targetClass.getDeclaredFields();
		while (resultSet.next()) {
			Object obj = targetClass.newInstance();
			for (int column = 1; column <= columnCount; column++) {
				Object value = resultSet.getObject(column);
				for (Field field : fields) {
					if (field.getName().equals(resultMetaData.getColumnName(column))) {
						boolean flag = field.isAccessible();
						field.setAccessible(true);
						field.set(obj, value);
						field.setAccessible(flag);
						break;
					}
				}
			}
			list.add(obj);
		}
		return list;
	}

}