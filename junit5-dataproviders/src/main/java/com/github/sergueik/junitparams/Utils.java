package com.github.sergueik.junitparams;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Copyright 2017-2019 Serguei Kouzmine
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.github.sergueik.junitparams.SheetsServiceUtil;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Common utilities for JUnitParams Dataproviders
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class Utils {

	private static Utils instance = new Utils();

	private Utils() {
	}

	public static Utils getInstance() {
		return instance;
	}


	private String controlColumn = null;
	public void setControlColumn(String value) {
		this.controlColumn = value;
	}

	private String withValue = null;
	public void setWithValue(String value) {
		this.withValue = value;
	}

	private boolean loadEmptyColumns = true;
	public void setLoadEmptyColumns(boolean value) {
		this.loadEmptyColumns = value;
	}

	private String sheetName;
	public void setSheetName(String value) {
		this.sheetName = value;
	}

	private String columnNames = "*";
	public void setColumnNames(String value) {
		this.columnNames = value;
	}

	private boolean debug = false;
	public void setDebug(boolean value) {
		this.debug = value;
	}

	// will use name path
	private String secretFilePath = null;
	public void setSecretFilePath(String data) {
		this.secretFilePath = data;
	}

	// TODO: refactor to make loadable through name attribute
	private String applicationName = null;
	public void setApplicationName(String data) {
		this.applicationName = data;
	}

	private SheetsServiceUtil sheetsServiceUtil = null;

	private static String osName = getOsName();
	private static final String homeDir = System.getenv((osName.startsWith("windows")) ? "USERPROFILE" : "HOME");

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		/*
		 * System.err.println("original input: " + input + "\n" + "osname: " + osName +
		 * "\n" + "processing input: " +
		 * input.replaceAll("(?:HOME|HOMEDIR|USERPROFILE)", osName.equals("windows") ?
		 * "USERPROFILE" : "HOME"));
		 */
		// NOTE: currently ignoring $HOMEDRIVE, $HOMEPATH on Windows
		Matcher matcher = Pattern.compile("\\$(?:\\{(?:env:)?(\\w+)\\}|(\\w+))").matcher(
				input.replaceAll("(?:HOME|HOMEDIR|USERPROFILE)", osName.equals("windows") ? "USERPROFILE" : "HOME"));
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			String envVarName = null == matcher.group(1) ? matcher.group(2) : matcher.group(1);
			String envVarValue = System.getenv(envVarName);
			matcher.appendReplacement(stringBuffer, null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		matcher.appendTail(stringBuffer);

		return stringBuffer.toString().replaceAll("(?:\\\\|/)", (File.separator.indexOf("\\") > -1) ? "\\\\" : "/");
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}

	// https://www.jopendocument.org/docs/org/jopendocument/dom/spreadsheet/Table.html
	public List<Object[]> createDataFromOpenOfficeSpreadsheet(SpreadSheet spreadSheet) {

		HashMap<String, String> columns = new HashMap<>();
		List<Object[]> result = new LinkedList<>();
		Sheet sheet = null;
		if (sheetName.isEmpty()) {
			if (debug) {
				System.err.println("Reading 1st Open Office sheet");
			}
			sheet = spreadSheet.getFirstSheet();
		} else {
			if (debug) {
				System.err.println("Reading Open Office sheet named: " + sheetName);
			}
			sheet = spreadSheet.getSheet(sheetName);
			if (sheet == null) {
				if (debug) {
					System.err.println(String.format(
							"Failed to find Open Office sheet named: \"%s\". Fallback to opening 1st sheet",
							sheetName));
				}
				sheet = spreadSheet.getFirstSheet();
			}
		}

		int columnCount = sheet.getColumnCount();
		int rowCount = sheet.getRowCount();
		@SuppressWarnings("rawtypes")
		Cell cell = null;
		@SuppressWarnings("rawtypes")
		Cell controlCell = null;
		int controlColumnIndex = -1;
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			String columnHeader = sheet.getImmutableCellAt(columnIndex, 0).getValue().toString();

			if (StringUtils.isBlank(columnHeader)) {
				break;
			}

			String columnName = CellReference.convertNumToColString(columnIndex);
			if (controlColumn == null || controlColumn.isEmpty() || !controlColumn.equals(columnHeader)) {
				columns.put(columnName, columnHeader);
			} else {
				controlColumnIndex = columnIndex;
				System.err.println(String.format("Determined control column index %d and name \"%s\" for \"%s\"",
						columnIndex, columnName, columnHeader));
			}
		}
		// NOTE: often there can be no ranges defined
		Set<String> rangeeNames = sheet.getRangesNames();
		Iterator<String> rangeNamesIterator = rangeeNames.iterator();

		while (rangeNamesIterator.hasNext()) {
			if (debug) {
				System.err.println("Range: " + rangeNamesIterator.next());
			}
		}
		for (int rowIndex = 1; rowIndex < rowCount
				&& StringUtils.isNotBlank(sheet.getImmutableCellAt(0, rowIndex).getValue().toString()); rowIndex++) {
			List<Object> resultRow = new LinkedList<>();

			if (controlColumnIndex != -1) {
				controlCell = sheet.getImmutableCellAt(controlColumnIndex, rowIndex);
				String controlCellValue = controlCell.getValue().toString();
				if (!controlCellValue.equals(withValue.toString())) {
					continue;
				}
			}
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				cell = sheet.getImmutableCellAt(columnIndex, rowIndex);
				String columnName = CellReference.convertNumToColString(columnIndex);
				if (!columns.containsKey(columnName)) {
					continue;
				}
				if (StringUtils.isNotBlank(cell.getValue().toString())) {
					// TODO: column selection
					@SuppressWarnings("unchecked")
					Object cellValue = safeOOCellValue(cell);
					resultRow.add(cellValue);
				} else {
					if (loadEmptyColumns) {
						resultRow.add(null);
					}
				}
			}
			if (debug) {
				System.err.println("Added row of parameters: " + resultRow.toString());
			}
			result.add(resultRow.toArray());
		}
		return result;
	}

	public List<Object[]> createDataFromOpenOfficeSpreadsheet(InputStream inputStream) {
		List<Object[]> result = new LinkedList<>();
		try {
			// https://www.programcreek.com/java-api-examples/index.php?api=org.jopendocument.dom.spreadsheet.Sheet
			SpreadSheet spreadSheet = SpreadSheet.get(new ODPackage(inputStream));
			result = createDataFromOpenOfficeSpreadsheet(spreadSheet);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Object[]> createDataFromOpenOfficeSpreadsheet(String filePath) {

		List<Object[]> result = new LinkedList<>();

		try {
			if (debug) {
				System.err.println("Reading Open Office file: " + filePath);
			}
			File file = new File(filePath);
			SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
			result = createDataFromOpenOfficeSpreadsheet(spreadSheet);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2003(HSSFWorkbook workBook) {
		List<Object[]> result = new LinkedList<>();

		Iterator<org.apache.poi.ss.usermodel.Cell> cells;
		HSSFRow row;
		HSSFCell cell;
		String columnHeader = null;
		String columnName = null;
		Object cellValue = null;

		Map<String, String> columnHeaders = new HashMap<>();
		HSSFSheet sheet = (sheetName.isEmpty()) ? workBook.getSheetAt(0) : workBook.getSheet(sheetName);
		if (debug) {
			System.err.println("createDataFromExcel2003: Reading Excel 2003 sheet: " + sheet.getSheetName());
		}
		// alternatively compute index boundaries explicitly
		// https://poi.apache.org/apidocs/org/apache/poi/xssf/usermodel/
		// the commented code fragment below exercises that
		// NOTE: only applicable to XSS, HSS (?), not collection-friendly
		// and does not handle sparse sheets with empty cells
		/*
		 * row = sheet.getRow(sheet.getFirstRowNum()); for (int columnIndex =
		 * row.getFirstCellNum(); columnIndex < row .getLastCellNum(); columnIndex++) {
		 * cell = row.getCell(columnIndex); columnHeader = cell.getStringCellValue();
		 * columnName = CellReference.convertNumToColString(cell.getColumnIndex()); //
		 * columnHeaders.put(columnName, columnHeader); if (debug) { System.err.println(
		 * String.format("createDataFromExcel2003: Header[%d](%s) = %s", columnIndex,
		 * columnName, columnHeader)); } } for (int rowIndex = sheet.getFirstRowNum() +
		 * 1; rowIndex <= sheet .getLastRowNum(); rowIndex++) { row =
		 * sheet.getRow(rowIndex); List<Object> resultRow = new LinkedList<>(); for (int
		 * columnIndex = row.getFirstCellNum(); columnIndex <= row .getLastCellNum();
		 * columnIndex++) { cell = row.getCell(columnIndex); if (cell != null) {
		 * cellValue = safeUserModeCellValue(cell); if (debug) { try {
		 * System.err.println(String.format(
		 * "createDataFromExcel2003: Loading Cell[%d] = %s %s", columnIndex,
		 * cellValue.toString(), cellValue.getClass())); } catch (NullPointerException
		 * e) { System.err .println("Exception loading cell " + cell.getColumnIndex());
		 * } } resultRow.add(cellValue); } } result.add(resultRow.toArray()); } return
		 * result;
		 */
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();

			if (row.getRowNum() == 0) {
				cells = row.cellIterator();
				while (cells.hasNext()) {

					cell = (HSSFCell) cells.next();
					int columnIndex = cell.getColumnIndex();
					columnHeader = cell.getStringCellValue();
					columnName = CellReference.convertNumToColString(cell.getColumnIndex());
					columnHeaders.put(columnName, columnHeader);
					if (debug) {
						System.err.println(String.format("Header[%d](%s) = %s", columnIndex, columnName, columnHeader));
					}
				}
				// skip the header
				if (debug) {
					System.err.println("Skipped the header");
				}
				continue;
			}

			cells = row.cellIterator();
			if (cells.hasNext()) {
				// NOTE: Local variable resultRow defined in an enclosing scope must be
				// final or effectively final
				List<Object> resultRow = new LinkedList<>();
				if (loadEmptyColumns) {
					// fill the Array with nulls
					IntStream.range(0, columnHeaders.keySet().size()).forEach(o -> resultRow.add(null));
					// inject sparsely defined columns
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if (cell != null) {
							cellValue = safeUserModeCellValue(cell);
							if (debug) {
								System.err.println(String.format("Cell address: row: %d col: %d",
										cell.getAddress().getRow(), cell.getAddress().getColumn()));
							}
							if (debug) {
								try {
									System.err.println(String.format("Loading Cell[%d] = value: \"%s\" class: \"%s\"",
											cell.getColumnIndex(), cellValue.toString(),
											cellValue.getClass().getName()));
								} catch (NullPointerException e) {
									System.err.println("Exception loading cell " + cell.getColumnIndex());
								}
							}
							resultRow.set(cell.getColumnIndex(), cellValue);
						}
					}
				} else {
					// push columns
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if (cell != null) {
							cellValue = safeUserModeCellValue(cell);
							if (debug) {
								System.err.println(String.format("Cell address: row: %d col: %d",
										cell.getAddress().getRow(), cell.getAddress().getColumn()));
							}
							if (debug) {
								try {
									System.err.println(String.format("Loading Cell[%d] = value: \"%s\" class: \"%s\"",
											cell.getColumnIndex(), cellValue.toString(),
											cellValue.getClass().getName()));
								} catch (NullPointerException e) {
									System.err.println("Exception loading cell " + cell.getColumnIndex());
								}
							}
							resultRow.add(cellValue);
						}
					}
				}
				result.add(resultRow.toArray());
			}
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2003(String filePath) {

		List<Object[]> result = new LinkedList<>();
		HSSFWorkbook workBook = null;

		try {
			InputStream ExcelFileToRead = new FileInputStream(filePath);
			workBook = new HSSFWorkbook(ExcelFileToRead);
			result = createDataFromExcel2003(workBook);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2003(InputStream inputStream) {

		List<Object[]> result = new LinkedList<>();
		HSSFWorkbook workBook = null;

		try {
			workBook = new HSSFWorkbook(inputStream);
			result = createDataFromExcel2003(workBook);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2007(XSSFWorkbook workBook) {

		List<Object[]> result = new LinkedList<>();
		Map<String, String> columns = new HashMap<>();
		XSSFSheet sheet = (sheetName.isEmpty()) ? workBook.getSheetAt(0) : workBook.getSheet(sheetName);

		Iterator<Row> rows = sheet.rowIterator();
		Iterator<org.apache.poi.ss.usermodel.Cell> cells;
		while (rows.hasNext()) {

			XSSFRow row = (XSSFRow) rows.next();
			XSSFCell cell;
			if (row.getRowNum() == 0) {
				cells = row.cellIterator();
				while (cells.hasNext()) {

					cell = (XSSFCell) cells.next();
					int columnIndex = cell.getColumnIndex();
					String columnHeader = cell.getStringCellValue();
					String columnName = CellReference.convertNumToColString(cell.getColumnIndex());
					columns.put(columnName, columnHeader);
					if (debug) {
						System.err.println(String.format("Header[%d](%s) = %s", columnIndex, columnName, columnHeader));
					}
				}
				// skip the header
				if (debug) {
					System.err.println("Skipped the header");
				}
				continue;
			}
			List<Object> resultRow = new LinkedList<>();
			cells = row.cellIterator();
			if (cells.hasNext()) {
				if (loadEmptyColumns) {
					// fill the Array with nulls
					IntStream.range(0, columns.keySet().size()).forEach(o -> resultRow.add(null));
					// inject sparsely defined columns
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						// TODO: column selection
						if (cell != null) {
							Object cellValue = safeUserModeCellValue(cell);
							if (debug) {
								System.err.println(String.format("Cell address: row: %d col: %d",
										cell.getAddress().getRow(), cell.getAddress().getColumn()));
							}
							if (debug) {
								System.err.println(String.format("Cell value: \"%s\" class: \"%s\"",
										cellValue.toString(), cellValue.getClass().getName()));
							}
							resultRow.add(cellValue);
						}
					}
				} else {
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						// TODO: column selection
						if (cell != null) {
							Object cellValue = safeUserModeCellValue(cell);
							if (debug) {
								System.err.println(String.format("Cell address: row: %d col: %d",
										cell.getAddress().getRow(), cell.getAddress().getColumn()));
							}
							if (debug) {
								System.err.println(String.format("Cell Value: \"%s\" class: %s", cellValue.toString(),
										cellValue.getClass().getName()));
							}
							resultRow.add(cellValue);
						}
					}
					result.add(resultRow.toArray());
				}
			}
		}
		if (debug) {
			System.err.println("Loaded " + result.size() + " rows");
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2007(String filePath) {
		List<Object[]> result = new LinkedList<>();
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(filePath);
			result = createDataFromExcel2007(workBook);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	public List<Object[]> createDataFromExcel2007(InputStream inputStream) {

		List<Object[]> result = new LinkedList<>();
		XSSFWorkbook workBook = null;

		try {
			workBook = new XSSFWorkbook(inputStream);
			result = createDataFromExcel2007(workBook);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	public List<Object[]> createDataFromGoogleSpreadsheet(String spreadsheetId) {
		return createDataFromGoogleSpreadsheet(spreadsheetId, "*");
	}

	// temporarily add to signature
	public List<Object[]> createDataFromGoogleSpreadsheet(String spreadsheetId, String sheetName) {
		// TODO: deal with unspecified sheetName
		String range = String.format("%s!A1:Z", sheetName);
		// A2:Z for value columns only
		List<Object[]> result = new LinkedList<>();

		try {
			sheetsServiceUtil = SheetsServiceUtil.getInstance();
			sheetsServiceUtil.setApplicationName(applicationName);
			sheetsServiceUtil.setSecretFilePath(secretFilePath);
			Sheets sheetsService = sheetsServiceUtil.getSheetsService();

			ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();

			List<List<Object>> resultRows = response.getValues();
			assertThat(resultRows, notNullValue());
			assertThat(resultRows.size() != 0, is(true));
			if (debug) {
				System.err.println("Got " + resultRows.size() + " result rows");
			}
			int row = 0;
			for (List<Object> resultRow : resultRows) {
				if (row == 0) {
					System.err.println("Headers:");
					Object[] resultArray = resultRow.toArray();
					Integer numberOfCols = resultArray.length;
					for (int col = 0; col != numberOfCols; col++) {
						// TODO: column filter
						if (debug) {
							System.err.println(String.format("column[%d]: %s ", col, resultArray[col]));
						}
					}
				} else {
					if (debug) {
						System.err.println("Got: " + resultRow);
					}
					result.add(resultRow.toArray());
				}
				row++;
			}
		} catch (IOException | GeneralSecurityException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
		return result;
	}

	// Safe conversion of type Excel cell object to Object / String value
	public static Object safeUserModeCellValue(org.apache.poi.ss.usermodel.Cell cell) {
		if (cell == null) {
			return null;
		}
		CellType type = cell.getCellTypeEnum();
		Object result;
		switch (type) {
		case _NONE:
			result = null;
			break;
		case NUMERIC:
			result = cell.getNumericCellValue();
			break;
		case STRING:
			result = cell.getStringCellValue();
			break;
		case FORMULA:
			throw new IllegalStateException("The formula cell is not supported");
		case BLANK:
			result = null;
			break;
		case BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case ERROR:
			throw new RuntimeException("Cell has an error");
		default:
			throw new IllegalStateException("Cell type: " + type + " is not supported");
		}
		return result;
		// return (result == null) ? null : result.toString();
	}

	// https://www.jopendocument.org/docs/org/jopendocument/dom/ODValueType.html
	public static Object safeOOCellValue(org.jopendocument.dom.spreadsheet.Cell<ODDocument> cell) {
		if (cell == null) {
			return null;
		}
		Object result;
		ODValueType type = cell.getValueType();
		switch (type) {
		case FLOAT:
			result = Double.valueOf(cell.getValue().toString());
			break;
		case STRING:
			result = cell.getTextValue();
			break;
		case TIME:
			result = null; // TODO
			break;
		case BOOLEAN:
			result = Boolean.getBoolean(cell.getValue().toString());
			break;
		default:
			throw new IllegalStateException("Can't evaluate cell value");
		}
		// return (result == null) ? null : result.toString();
		return result;
	}

	// origin:
	// https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
	public String getDurationBreakdown(long durationMilliseconds) {
		if (durationMilliseconds < 0) {
			throw new IllegalArgumentException("Duration can not be negative");
		}

		long days = TimeUnit.MILLISECONDS.toDays(durationMilliseconds);
		durationMilliseconds -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(durationMilliseconds);
		durationMilliseconds -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMilliseconds);
		durationMilliseconds -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMilliseconds);

		StringBuilder sb = new StringBuilder(64);
		sb.append(days);
		sb.append(" Days ");
		sb.append(hours);
		sb.append(" Hours ");
		sb.append(minutes);
		sb.append(" Minutes ");
		sb.append(seconds);
		sb.append(" Seconds");

		return (sb.toString());
	}

}
