package com.sqlibri.util;

import java.util.List;

/**
 * Parses Data to CSV format
 */
public class CSVParser {

  /**
   * Provides parsing to CSV format
   * @param columnNames - list of all column names
   * @param rowData - data which going to be parsed to CSV
   * @return parsed CSV string
   */
  public static String parseToCSV(final List<String> columnNames, final List<List<String>> rowData) {

    final StringBuilder csv = new StringBuilder();

    columnNames.forEach(e -> {
      csv.append(e);
      if (columnNames.indexOf(e) < columnNames.size() - 1)
        csv.append(",");
    });

    csv.append("\n");

    rowData.forEach(row -> {
      int index = rowData.indexOf(row);
      row.forEach(column -> {
        csv.append(column);
        if (rowData.get(index).indexOf(column) < rowData.get(index).size() - 1)
          csv.append(",");
      });
      csv.append("\n");
    });

    return csv.toString();
  }

}
