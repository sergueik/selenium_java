package com.sqlibri.model;

import com.sqlibri.util.PrettyStatus;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * All essential operations for working with database
 */
public class Database {

  private final String GET_ALL_TABLES = "SELECT name FROM sqlite_master WHERE type='table';";

  //path to the database file
  private File file;

  /**
   * @return path to the database file
   */
  public File getFile() {
    return file;
  }

  /**
   * Initializes with path to the database file
   * @param file path to database file
   */
  public Database(File file) {
    this.file = file;
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates database by given path
   * @throws SQLException throws if database cannot be created
   */
  public void connect() throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.toString());
    try {
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Drops database (current database file)
   */
  public void drop() {
    file.delete();
  }

  /**
   * Get all tables from database
   * @return all tables
   */
  public List<String> getAllTables() {

    List<String> tables = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.toString());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_TABLES)) {
      while (resultSet.next()) {
        tables.add(resultSet.getString(1));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return tables;
  }

  /**
   * Executes query 
   * @param query to execute
   * @return results from executed query
   * @throws SQLException throws if there is any error in sql query
   * @throws Exception throws if crap happen 
   */
  public QueryResult executeQuery(String query) throws Exception, SQLException {
    QueryResult queryResult = new QueryResult();
    ResultSet resultSet = null;
    query = query.replaceAll("\\s+", " ");

    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.toString());
        Statement statement = connection.createStatement()) {

        long start = System.currentTimeMillis();
        if (isSelectQuery(query)) {
            resultSet = statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
        }
        long end = System.currentTimeMillis();

        queryResult.setExecutionInfo(PrettyStatus.success(query, (end - start)));

        if (resultSet == null) return queryResult;

        queryResult.setColumnNames(new ArrayList<>());
        int columns = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columns; i++) {
            queryResult.getColumnNames().add(resultSet.getMetaData().getColumnName(i));
        }

        queryResult.setTableData(new ArrayList<>());
        for (int row = 0; resultSet.next(); row++) {
            queryResult.getTableData().add(new ArrayList<>());
            for (int column = 0; column < columns; column++) {
                queryResult.getTableData().get(row).add(resultSet.getString(column + 1));
            }
        }
      } catch (SQLException e) {
          throw e;
      } catch (Exception e) {
          throw e;
      } finally {
          if (isSelectQuery(query) && resultSet != null) resultSet.close();
      }

    return queryResult;
  }

  // Checks is query is 'SELECT' query
  private boolean isSelectQuery(String query) {
    return query.toUpperCase().matches("^\\s*SELECT.*$");
  }

}
