package org.utils.util;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

// https://stackoverflow.com/questions/42320221/spring-boot-how-to-add-an-unconventional-propertysource?rq=1
// @Configuration
// @PropertySource("file:application.properties")
public class JDBCUtils {

	@Autowired
	private Environment env;

	private static final Logger logger = Logger
			.getLogger(JDBCUtils.class.getName());
	public static Connection connection = null;
	public static PreparedStatement preparedStatement = null;
	public static ResultSet resultSet = null;

	private JDBCUtils() {
	}

	@Value("${spring.datasource.url}")
	private static String url;

	public static Connection getConnection() {

		// resolveEnvVars(System.getProperty("spring.datasource.url"));
		String url = resolveEnvVars(
				"jdbc:sqlite:${USERPROFILE}\\sqlite\\springboot.db"); //
		// "jdbc:sqlserver://localhost:1434;databaseName=student";
		String username = "";
		String password = "";
		if (connection != null) {
			return connection;
		}
		try {
			Class
					.forName(
							"org.sqlite.JDBC" /* "com.microsoft.sqlserver.jdbc.SQLServerDriver" */)
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

	private static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{(\\w+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = System.getenv(envVarName);
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
