package com.sqlibri.util;

/**
 * Formatter for SQL queries
 */
public class PrettyStatus {

  /**
   * Parse Error query 
   * @param query to parse
   * @return parsed error query
   */
  public static String error(String query) {
    if(query.isEmpty()) return "Empty";
     return "SQL Error in query '" + shortenQuery(query) + "'";
  }

  /**
   * Parses success queries
   * @param query to parse
   * @param milliseconds query execution time
   * @return parsed success query
   */
  public static String success(String query, long milliseconds) {
    if(query.isEmpty()) return "Empty";
    StringBuilder result = new StringBuilder();

    result.append("Query '");
    result.append(shortenQuery(query));
    result.append("'");
    result.append(" executed in: ");
    result.append(milliseconds);
    result.append(" ms");
    
    return result.toString();
  }
  
  /**
   * Trims all whitespaces from the beginning and cuts string to 20 characters
   * @param query - to cut
   * @return if query length is less than 20 character method returns the same 
   * query else returns cut to 20 character query with ...
   */
  private static String shortenQuery(String query) {
    query = query.replaceAll("^\\s*", "");
    if(query.length() > 20) return query.substring(0, 20) + "...";
    else return query;
  }

}
