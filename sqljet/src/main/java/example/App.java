package example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.apache.logging.log4j.Logger;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import org.apache.logging.log4j.LogManager;

/**
 * based on Oracle's example at
 * https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
 */

public class App {

	private static Logger logger = LogManager.getLogger(App.class);
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private final static Options options = new Options();

	private static long countRecords(ISqlJetCursor cursor)
			throws SqlJetException {
		return cursor.getRowCount();
	}

	private static void printRecords(ISqlJetCursor cursor, String column1,
			String column2) throws SqlJetException {
		try {
			if (!cursor.eof()) {
				do {
					logger.info(cursor.getRowId() + " : " + cursor.getString(column1)
							+ " " + cursor.getString(
									column2) /* + cursor.getInteger(intColumn)
														+ formatDate(cursor.getInteger(DOB_FIELD))*/);
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	private static String databaseName = null;
	private static String tableName = "cache_table";
	private static String column1Name = "fname";
	private static String column2Name = "ds";
	private static SqlJetDb db = null;
	private static ISqlJetTable table;

	public static void main(String[] args) {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "database", true, "Database");
		options.addOption("t", "table", true, "Table");
		try {
			commandLine = commandLineparser.parse(options, args);
			databaseName = commandLine.getOptionValue("database");
			if (databaseName == null) {
				logger.info("Missing required argument: database");
				return;
			}
			tableName = commandLine.getOptionValue("table");
			if (tableName == null) {
				logger.info("Missing required argument: table");
				return;
			}
		} catch (ParseException e) {
			return;
		}
		try {
			File dbFile = new File(databaseName);

			db = SqlJetDb.open(dbFile, true);

			table = db.getTable(tableName);
			db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
			// ISqlJetCursor cursor = table.open();
			logger.info("number of rows: " + countRecords(table.open()));
			printRecords(table.open(), column1Name, column2Name);
		} catch (SqlJetException e) {
			// ignore
		} finally {
			if (db != null) {
				try {
					db.commit();
				} catch (SqlJetException e) {
				}
			}
		}
	}
}
