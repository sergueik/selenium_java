package com.sqlibri.model;

import java.util.List;

/**
 * Represents table's data returned from query 
 * with query execution info
 */
public class QueryResult {

  private String executionInfo;
  private List<List<String>> tableData;
  private List<String> columnNames;

  public List<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }

  public QueryResult() {}

  public QueryResult(String executionInfo, List<List<String>> tableData) {
    this.executionInfo = executionInfo;
    this.tableData = tableData;
  }

  public String getExecutionInfo() {
    return executionInfo;
  }

  public List<List<String>> getTableData() {
    return tableData;
  }

  public void setExecutionInfo(String executionInfo) {
    this.executionInfo = executionInfo;
  }

  public void setTableData(List<List<String>> tableData) {
    this.tableData = tableData;
  }

  public int getColumnCount() {
    return tableData.get(0).size();
  }

  public int getRowCount() {
    return tableData.size();
  }

  @Override
  public String toString() {
    return getExecutionInfo();
  }

}
