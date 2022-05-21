package io.vodqa.ext2poi;

/**
 * Created by Sergio on 10/05/2017.
 */

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class with static methods for manipulating data in MS Excel sheets.
 * Simply import static method from this class to use it and provide necessary parameters.
 */

public class Excel {
	private static final Logger log = LoggerFactory
			.getLogger(Excel.class.getName());
	private static String formattedDateValue = "";
	private static boolean bClosedWorkbook;
	private static Sheet sheet = null;
	private static Workbook workbook = null;
	private static Row row = null;
	private static Cell cell = null;
	private static List<String> returnColumnValues = null;
	private static List<String> returnRowValues = null;
	private static String returnCellValue = null;

	public Excel() {
	}

	public static class Read {
		public Read() {
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", 0, 0) returns value from the cell A1 in 'test' sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", 10, 10) returns value from the cell K11 from 'another test' sheet
		 * </pre></blockquote>
		 *
		 * @param filePath     path of excel workbook file
		 * @param sheetName    name of the sheet in excel workbook
		 * @param rowIndex     row index (0 based)
		 * @param colIndex     column index (0 based)
		 * @return              value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, String sheetName,
				int rowIndex, int colIndex) {
			Row cellRowCount = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				int lastRowNum = sheet.getLastRowNum();

				if (lastRowNum < rowIndex) {
					System.err.println(
							"ERROR: row number value is greater than existing row with data in excel sheet.");
					log.debug("Sheet name param: " + sheetName);
					log.debug("Row number param: " + rowIndex);
					log.debug("Column index param: " + colIndex);
					returnCellValue = "";
				} else {
					cellRowCount = sheet.getRow(rowIndex);

					if (cellRowCount != null) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Row is null. Not able to get the cell data with specified parameters.");
						log.debug("Sheet name param: " + sheetName);
						log.debug("Row index param: " + rowIndex);
						log.debug("Column index param: " + colIndex);
						returnCellValue = "";
					}
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", 0, "Column 1") returns value from the cell located in 1st row and column with 'Column 1' header in 'test' sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", 10, "Column 2") returns value from the cell located in 1st row and column with 'Column 2' header in 'another test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetName        name of the sheet in excel workbook
		 * @param rowIndex         row index (0 based)
		 * @param colHeaderName    column header name (column header names must be specified in first (1) row)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, String sheetName,
				int rowIndex, String colHeaderName) {
			Row cellRowCount = null;
			int colIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				int lastRowNumber = sheet.getLastRowNum();

				if (lastRowNumber < rowIndex) {
					System.err.println(
							"ERROR: row number value is greater than existing row with data in excel sheet.");
					log.debug("Sheet name param: " + sheetName);
					log.debug("Row index param: " + rowIndex);
					log.debug("Column header param: " + colHeaderName);
					returnCellValue = "";
				} else {
					row = sheet.getRow(0);
					colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
					cellRowCount = sheet.getRow(rowIndex);

					if (cellRowCount != null) {
						if (colIndex != 0) {
							cell = cellRowCount.getCell(colIndex,
									Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							returnCellValue = InnerHelper.formatCellToString(cell);
						} else {
							System.err.println(
									"ERROR: Column header name not found. Verify spelling.");
							log.debug("Sheet name param: " + sheetName);
							log.debug("Row index param: " + rowIndex);
							log.debug("Column header param: " + colHeaderName);
							returnCellValue = "";
						}
					} else {
						System.err.println(
								"ERROR: Row is null. Not able to get the cell data with specified parameters.");
						log.debug("Sheet name param: " + sheetName);
						log.debug("Row index param: " + rowIndex);
						log.debug("Column header param: " + colHeaderName);
						returnCellValue = "";
					}
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", "Row 1", 3) returns value from the cell located in row with name 'Row 1' and column D in 'test' sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", "Row 2", 1) returns value from the cell located in row with name 'Row 2' and column B in 'another test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath    path of excel workbook file
		 * @param sheetName   name of the sheet in excel workbook
		 * @param rowName     row name (must be specified in first (A) column)
		 * @param colIndex    column index (0 based)
		 * @return             value from the cell formatted as string
		 */

		public static String getCellValue(String filePath, String sheetName,
				String rowName, int colIndex) {
			Row cellRowCount = null;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet name param: " + sheetName);
						log.debug("Row name param: " + rowName);
						log.debug("Column index param: " + colIndex);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet name param: " + sheetName);
					log.debug("Row name param: " + rowName);
					log.debug("Column index param: " + colIndex);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetName        name of the sheet in excel workbook
		 * @param rowName          row name (must be specified in first (A) column)
		 * @param colHeaderName    column header name (must be specified in first (1) row)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, String sheetName,
				String rowName, String colHeaderName) {
			Row cellRowCount = null;
			int colIndex = 0;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet name param: " + sheetName);
						log.debug("Row name param: " + rowName);
						log.debug("Column header param: " + colHeaderName);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet name param: " + sheetName);
					log.debug("Row name param: " + rowName);
					log.debug("Column header param: " + colHeaderName);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", "AA3") returns value from the cell AA3 in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetName        name of the sheet in excel workbook
		 * @param sCellCoordinates  excel cell coordinates (currently supports up to AZ column)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, String sheetName,
				String sCellCoordinates) {
			Row cellRowCount = null;
			int colIndex = 0;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);

				colIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getRight();
				log.debug("Returning column index: " + colIndex);
				rowIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getLeft();
				log.debug("Returning row index: " + rowIndex);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet name param: " + sheetName);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet name param: " + sheetName);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException
					| IllegalArgumentException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, 1, 0, 0) returns value from the cell A1 in 2nd sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", 10, 10) returns value from the cell K11 from 'another test' sheet
		 * </pre></blockquote>
		 *
		 * @param filePath     path of excel workbook file
		 * @param sheetIndex   index of the sheet in excel workbook (0 based)
		 * @param rowIndex     row index (0 based)
		 * @param colIndex     column index (0 based)
		 * @return              value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, int sheetIndex,
				int rowIndex, int colIndex) {
			Row cellRowCount = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				int lastRowNum = sheet.getLastRowNum();

				if (lastRowNum < rowIndex) {
					System.err.println(
							"ERROR: row number value is greater than existing row with data in excel sheet.");
					log.debug("Sheet index param: " + sheetIndex);
					log.debug("Row number param: " + rowIndex);
					log.debug("Column index param: " + colIndex);
					returnCellValue = "";
				} else {
					cellRowCount = sheet.getRow(rowIndex);

					if (cellRowCount != null) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Row is null. Not able to get the cell data with specified parameters.");
						log.debug("Sheet index param: " + sheetIndex);
						log.debug("Row index param: " + rowIndex);
						log.debug("Column index param: " + colIndex);
						returnCellValue = "";
					}
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", 0, "Column 1") returns value from the cell located in 1st row and column with 'Column 1' header in 'test' sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", 10, "Column 2") returns value from the cell located in 1st row and column with 'Column 2' header in 'another test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetIndex       index of the sheet in excel workbook (0 based)
		 * @param rowIndex         row index (0 based)
		 * @param colHeaderName    column header name (column header names must be specified in first (1) row)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, int sheetIndex,
				int rowIndex, String colHeaderName) {
			Row cellRowCount = null;
			int colIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				int lastRowNumber = sheet.getLastRowNum();

				if (lastRowNumber < rowIndex) {
					System.err.println(
							"ERROR: row number value is greater than existing row with data in excel sheet.");
					log.debug("Sheet index param: " + sheetIndex);
					log.debug("Row index param: " + rowIndex);
					log.debug("Column header param: " + colHeaderName);
					returnCellValue = "";
				} else {
					row = sheet.getRow(0);
					colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
					cellRowCount = sheet.getRow(rowIndex);

					if (cellRowCount != null) {
						if (colIndex != 0) {
							cell = cellRowCount.getCell(colIndex,
									Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							returnCellValue = InnerHelper.formatCellToString(cell);
						} else {
							System.err.println(
									"ERROR: Column header name not found. Verify spelling.");
							log.debug("Sheet index param: " + sheetIndex);
							log.debug("Row index param: " + rowIndex);
							log.debug("Column header param: " + colHeaderName);
							returnCellValue = "";
						}
					} else {
						System.err.println(
								"ERROR: Row is null. Not able to get the cell data with specified parameters.");
						log.debug("Sheet index param: " + sheetIndex);
						log.debug("Row index param: " + rowIndex);
						log.debug("Column header param: " + colHeaderName);
						returnCellValue = "";
					}
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", "Row 1", 3) returns value from the cell located in row with name 'Row 1' and column D in 'test' sheet in specified excel file
		 * getCellValue(excelFilePath, "another test", "Row 2", 1) returns value from the cell located in row with name 'Row 2' and column B in 'another test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath    path of excel workbook file
		 * @param sheetIndex  index of the sheet in excel workbook (0 based)
		 * @param rowName     row name (must be specified in first (A) column)
		 * @param colIndex    column index (0 based)
		 * @return             value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, int sheetIndex,
				String rowName, int colIndex) {
			Row cellRowCount = null;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet index param: " + sheetIndex);
						log.debug("Row name param: " + rowName);
						log.debug("Column index param: " + colIndex);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet index param: " + sheetIndex);
					log.debug("Row name param: " + rowName);
					log.debug("Column index param: " + colIndex);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetIndex       index of the sheet in excel workbook (0 based)
		 * @param rowName          row name (must be specified in first (A) column)
		 * @param colHeaderName    column header name (must be specified in first (1) row)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, int sheetIndex,
				String rowName, String colHeaderName) {
			Row cellRowCount = null;
			int colIndex = 0;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet index param: " + sheetIndex);
						log.debug("Row name param: " + rowName);
						log.debug("Column header param: " + colHeaderName);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet index param: " + sheetIndex);
					log.debug("Row name param: " + rowName);
					log.debug("Column header param: " + colHeaderName);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get value from cell with specified parameters.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getCellValue(excelFilePath, "test", "AA3") returns value from the cell AA3 in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path of excel workbook file
		 * @param sheetIndex       index of the sheet in excel workbook (0 based)
		 * @param sCellCoordinates  excel cell coordinates (currently supports up to AZ column)
		 * @return                  value from the cell formatted as string
		 */
		public static String getCellValue(String filePath, int sheetIndex,
				String sCellCoordinates) {
			Row cellRowCount = null;
			int colIndex = 0;
			int rowIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);

				colIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getRight();
				log.debug("Returning column index: " + colIndex);
				rowIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getLeft();
				log.debug("Returning row index: " + rowIndex);
				cellRowCount = sheet.getRow(rowIndex);

				if (cellRowCount != null) {
					if (colIndex != 0 & rowIndex != 0) {
						cell = cellRowCount.getCell(colIndex,
								Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						returnCellValue = InnerHelper.formatCellToString(cell);
					} else {
						System.err.println(
								"ERROR: Column or row name not found. Verify spelling.");
						log.debug("Sheet index param: " + sheetIndex);
						returnCellValue = "";
					}
				} else {
					System.err.println(
							"ERROR: Row is null. Not able to get the cell data with specified parameters.");
					log.debug("Sheet index param: " + sheetIndex);
					returnCellValue = "";
				}

				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			log.debug("Returning cell value from method: " + returnCellValue);
			return returnCellValue;
		}

		/**
		 * Get list of values in specified column starting from second row (Column header row is not included in returned ArrayList)
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", "Column 1", boolean) returns ArrayList of values from the column with header 'Column 1' in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetName        name of the sheet
		 * @param colHeaderName    column header name (must be declared in first (1) row)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */

		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, boolean inclBlankCells) {
			int colIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				row = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, 1, colIndex,
						inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from second row (Column header row is not included in returned ArrayList)
		 * for values matching specified row header.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", "Column 1", boolean) returns ArrayList of values from the column with header 'Column 1' in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetName        name of the sheet
		 * @param colHeaderName    column header name (must be declared in first (1) row)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @param sRowMatcher       row name matcher
		 * @param iRowEnd           integer for last row to retreive
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, String sRowMatcher, int iRowEnd,
				boolean inclBlankCells) {
			int colIndex;
			int iRowStartIndex;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				iRowStartIndex = InnerHelper.getRowIndex(sheet, sRowMatcher);
				Row colHeaderRow = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(colHeaderRow, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEnd, colIndex, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from second row (Column header row is not included in returned ArrayList)
		 * for values matching specified row header.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", "Column 1", boolean) returns ArrayList of values from the column with header 'Column 1' in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetName        name of the sheet
		 * @param colHeaderName    column header name (must be declared in first (1) row)
		 * @param sRowMatcher       row name matcher
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, String sRowMatcher,
				boolean inclBlankCells) {
			int colIndex;
			int iRowStartIndex;
			int iRowEndIndex;
			Pair<Integer, Integer> rowsRange;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				rowsRange = InnerHelper.getRowsRange(sheet, sRowMatcher);
				iRowStartIndex = rowsRange.getLeft();
				iRowEndIndex = rowsRange.getRight();
				Row colHeaderRow = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(colHeaderRow, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from second row (Column header row is not included in returned ArrayList)
		 * for values matching specified row header.
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", "Column 1", boolean) returns ArrayList of values from the column with header 'Column 1' in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath             path to excel file
		 * @param sheetName            name of the sheet
		 * @param sColFirstHeaderName   column header name (must be declared in first (1) row)
		 * @param sColSecondHeaderName  column header name (must be declared in first (1) row)
		 * @param sDelimiter            delimiter value to concatenate two cells
		 * @param sRowMatcher           row name matcher
		 * @param inclBlankCells        boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                      {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String sColFirstHeaderName,
				String sColSecondHeaderName, String sDelimiter, String sRowMatcher,
				boolean inclBlankCells) {
			int colIndex1;
			int colIndex2;
			int iRowStartIndex;
			int iRowEndIndex;
			Pair<Integer, Integer> rowsRange;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				rowsRange = InnerHelper.getRowsRange(sheet, sRowMatcher);
				iRowStartIndex = rowsRange.getLeft();
				iRowEndIndex = rowsRange.getRight();
				Row colHeaderRow = sheet.getRow(0);
				colIndex1 = InnerHelper.getColumnIndex(colHeaderRow,
						sColFirstHeaderName);
				colIndex2 = InnerHelper.getColumnIndex(colHeaderRow,
						sColSecondHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex1, colIndex2, sDelimiter, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		// TODO: 8/17/2017 javadoc
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, int iColHeaderIndent,
				String sDelimiter, String sRowMatcher, boolean inclBlankCells) {
			int colIndex1;
			int colIndex2;
			int iRowStartIndex;
			int iRowEndIndex;
			Pair<Integer, Integer> rowsRange;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				rowsRange = InnerHelper.getRowsRange(sheet, sRowMatcher);
				iRowStartIndex = rowsRange.getLeft();
				iRowEndIndex = rowsRange.getRight();
				Row colHeaderRow = sheet.getRow(0);
				colIndex1 = InnerHelper.getColumnIndex(colHeaderRow, colHeaderName);
				colIndex2 = colIndex1 + iColHeaderIndent;
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex1, colIndex2, sDelimiter, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		// TODO: 8/17/2017 javadoc
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, boolean copyCellValue,
				String sDelimiterStart, String sDelimiterEnd, String sRowMatcher,
				boolean inclBlankCells) {
			int colIndex;
			int iRowStartIndex;
			int iRowEndIndex;
			Pair<Integer, Integer> rowsRange;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				rowsRange = InnerHelper.getRowsRange(sheet, sRowMatcher);
				iRowStartIndex = rowsRange.getLeft();
				iRowEndIndex = rowsRange.getRight();
				Row colHeaderRow = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(colHeaderRow, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex, copyCellValue, sDelimiterStart,
						sDelimiterEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		// TODO: 8/17/2017 javadoc
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, String colHeaderName, boolean copyCellValue,
				String sDelimiterStart, String sDelimiterEnd, boolean inclBlankCells) {
			int colIndex;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				row = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, 1, colIndex,
						copyCellValue, sDelimiterStart, sDelimiterEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from first row
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", 1, boolean) returns ArrayList of values from the column B in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetName        name of the sheet
		 * @param colIndex         column index (0 based)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, int colIndex, boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				returnColumnValues = InnerHelper.getColDataList(sheet, 0, colIndex,
						inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from specific row
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, "test", 3, 1, boolean) returns ArrayList of values starting from row 4 in column B in 'test' sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetName        name of the sheet
		 * @param iRowStart         first row to retrieve data from (0 based)
		 * @param colIndex         column index (0 based)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				String sheetName, int iRowStart, int colIndex, boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnColumnValues = new ArrayList<>();
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStart,
						colIndex, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from second row (Column header row is not included in returned ArrayList)
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, 0, "Column 1", boolean) returns ArrayList of values from the column with header 'Column 1' in first sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetIndex       index of the sheet (0 based)
		 * @param colHeaderName    column header name (must be declared in first (1) row)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				int sheetIndex, String colHeaderName, boolean inclBlankCells) {
			int colIndex = 0;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				returnColumnValues = new ArrayList<>();
				row = sheet.getRow(0);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				returnColumnValues = InnerHelper.getColDataList(sheet, 1, colIndex,
						inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from first row
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, 1, 1, boolean) returns ArrayList of values from the column B in second sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetIndex       index of the sheet (0 based)
		 * @param colIndex         column index (0 based)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				int sheetIndex, int colIndex, boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				returnColumnValues = new ArrayList<>();
				returnColumnValues = InnerHelper.getColDataList(sheet, 0, colIndex,
						inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		/**
		 * Get list of values in specified column starting from specific row
		 * <p>
		 * Examples:
		 * <blockquote><pre>
		 * getListOfColumnValues(excelFilePath, 0, 3, 1, boolean) returns ArrayList of values starting from row 4 in column B in first sheet in specified excel file
		 * </pre></blockquote>
		 *
		 * @param filePath         path to excel file
		 * @param sheetIndex       index of the sheet
		 * @param iRowStart         first row to retrieve data from (0 based)
		 * @param colIndex         column index (0 based)
		 * @param inclBlankCells    boolean parameter to determine whether blank cells should be included in returned ArrayList (if false, blank cells will be skipped)
		 * @return                  {@link ArrayList} of column values
		 */
		public static List<String> getListOfColumnValues(String filePath,
				int sheetIndex, int iRowStart, int colIndex, boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				returnColumnValues = new ArrayList<>();
				returnColumnValues = InnerHelper.getColDataList(sheet, iRowStart,
						colIndex, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnColumnValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				String sheetName, int rowIndex, int iColStart, int iColEnd,
				boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnRowValues = new ArrayList<>();
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				String sheetName, String rowName, int iColStart, int iColEnd,
				boolean inclBlankCells) {
			int rowIndex;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				returnRowValues = new ArrayList<>();
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				String sheetName, int rowIndex, String sColHeaderStart,
				String sColHeaderEnd, boolean inclBlankCells) {
			int iColStart;
			int iColEnd;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(0);
				returnRowValues = new ArrayList<>();
				iColStart = InnerHelper.getColumnIndex(row, sColHeaderStart);
				iColEnd = InnerHelper.getColumnIndex(row, sColHeaderEnd);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				String sheetName, String rowName, String sColHeaderStart,
				String sColHeaderEnd, boolean inclBlankCells) {
			int rowIndex;
			int iColStart;
			int iColEnd;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(0);
				returnRowValues = new ArrayList<>();
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				iColStart = InnerHelper.getColumnIndex(row, sColHeaderStart);
				iColEnd = InnerHelper.getColumnIndex(row, sColHeaderEnd);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				int sheetIndex, int rowIndex, int iColStart, int iColEnd,
				boolean inclBlankCells) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				returnRowValues = new ArrayList<>();
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				int sheetIndex, String rowName, int iColStart, int iColEnd,
				boolean inclBlankCells) {
			int rowIndex;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				returnRowValues = new ArrayList<>();
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				int sheetIndex, int rowIndex, String sColHeaderStart,
				String sColHeaderEnd, boolean inclBlankCells) {
			int iColStart;
			int iColEnd;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(0);
				returnRowValues = new ArrayList<>();
				iColStart = InnerHelper.getColumnIndex(row, sColHeaderStart);
				iColEnd = InnerHelper.getColumnIndex(row, sColHeaderEnd);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		public static List<String> getListOfRowValues(String filePath,
				int sheetIndex, String rowName, String sColHeaderStart,
				String sColHeaderEnd, boolean inclBlankCells) {
			int rowIndex;
			int iColStart;
			int iColEnd;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(0);
				returnRowValues = new ArrayList<>();
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				iColStart = InnerHelper.getColumnIndex(row, sColHeaderStart);
				iColEnd = InnerHelper.getColumnIndex(row, sColHeaderEnd);
				returnRowValues = InnerHelper.getRowDataList(sheet, rowIndex, iColStart,
						iColEnd, inclBlankCells);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}

			return returnRowValues;
		}

		// TODO: 8/30/2017 rework to include last row without specifying "END row
		// marker"
		public static Map<String, String> getMapOfColumnValues(String filePath,
				String sheetName, String sKeyColHeaderName, String sValColHeaderName,
				String sRowMatcher, boolean inclBlankCells) {
			Map<String, String> returnColValMap = null;
			List<String> columnKeys = null;
			List<String> columnValues = null;
			int colIndex1;
			int colIndex2;
			int iRowStartIndex;
			int iRowEndIndex;
			Pair<Integer, Integer> rowsRange;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowsRange = InnerHelper.getRowsRange(sheet, sRowMatcher);
				iRowStartIndex = rowsRange.getLeft();
				iRowEndIndex = rowsRange.getRight();
				Row colHeaderRow = sheet.getRow(0);
				colIndex1 = InnerHelper.getColumnIndex(colHeaderRow, sKeyColHeaderName);
				colIndex2 = InnerHelper.getColumnIndex(colHeaderRow, sValColHeaderName);
				columnKeys = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex1, inclBlankCells);
				columnValues = InnerHelper.getColDataList(sheet, iRowStartIndex,
						iRowEndIndex, colIndex2, inclBlankCells);
				if (columnKeys.size() == columnValues.size()) {
					returnColValMap = new LinkedHashMap<>();
					for (int i = 0; i < columnKeys.size(); i++) {
						returnColValMap.put(columnKeys.get(i), columnValues.get(i));
					}
				} else {
					log.error("Column keys and values lists differ in size.");
				}
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnColValMap;
		}

	}

	public static class Write {
		public Write() {
		}

		public static void writeStringValueToCell(String filePath, String sheetName,
				String sCellCoordinates, String sValue) {
			Integer colIndex = null;
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getLeft();
				colIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getRight();
				if (rowIndex != null & colIndex != null) {
					row = sheet.getRow(rowIndex);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Cell coordinates are invalid.");
					throw new IllegalArgumentException("Cell coordinates are invalid");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, String sheetName,
				int rowIndex, int colIndex, String sValue) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(rowIndex);
				if (row != null) {
					cell = row.createCell(colIndex, CellType.STRING);
					cell.setCellValue(sValue);
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					row = sheet.createRow(rowIndex);
					try {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} catch (Exception e) {
						log.error(e.getMessage());
						log.error(InnerHelper.getExceptionMessage(e));
						throw new NullPointerException(
								"Row is not initialized. Returning null.");
					}
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, String sheetName,
				int rowIndex, String colHeaderName, String sValue) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(rowIndex);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				if (colIndex != 0) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Column index is null");
					throw new IllegalArgumentException("Column index is null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, String sheetName,
				String rowName, String colHeaderName, String sValue) {
			Integer colIndex = null;
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				row = sheet.getRow(rowIndex);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				if (rowIndex != 0 & colIndex != 0) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Column and row are null");
					throw new IllegalArgumentException("Column and row are null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, String sheetName,
				String rowName, int colIndex, String sValue) {
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				row = sheet.getRow(rowIndex);
				if (rowIndex != 0) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Row is null");
					throw new IllegalArgumentException("Row is null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, int sheetIndex,
				String sCellCoordinates, String sValue) {
			Integer colIndex = null;
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				rowIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getLeft();
				colIndex = InnerHelper.getCellCoordinates(sCellCoordinates).getRight();
				if (rowIndex != null & colIndex != null) {
					row = sheet.getRow(rowIndex);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Cell coordinates are invalid.");
					throw new IllegalArgumentException("Cell coordinates are invalid");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, int sheetIndex,
				int rowIndex, int colIndex, String sValue) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(rowIndex);
				if (row != null) {
					cell = row.createCell(colIndex, CellType.STRING);
					cell.setCellValue(sValue);
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					row = sheet.createRow(rowIndex);
					try {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} catch (Exception e) {
						log.error(e.getMessage());
						log.error(InnerHelper.getExceptionMessage(e));
						throw new NullPointerException(
								"Row is not initialized. Returning null.");
					}
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, int sheetIndex,
				int rowIndex, String colHeaderName, String sValue) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(rowIndex);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				if (colIndex != 0) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Column index is null");
					throw new IllegalArgumentException("Column index is null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, int sheetIndex,
				String rowName, String colHeaderName, String sValue) {
			Integer colIndex = null;
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				row = sheet.getRow(rowIndex);
				colIndex = InnerHelper.getColumnIndex(row, colHeaderName);
				if (rowIndex != null & colIndex != null) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Column and row are null");
					throw new IllegalArgumentException("Column and row are null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValueToCell(String filePath, int sheetIndex,
				String rowName, int colIndex, String sValue) {
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				row = sheet.getRow(rowIndex);
				if (rowIndex != 0) {
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(sValue);
						InnerHelper.writeToWorkbook(workbook, filePath);
						InnerHelper.closeWorkbook(workbook);
					} else {
						row = sheet.createRow(rowIndex);
						try {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(sValue);
							InnerHelper.writeToWorkbook(workbook, filePath);
							InnerHelper.closeWorkbook(workbook);
						} catch (Exception e) {
							log.error(e.getMessage());
							log.error(InnerHelper.getExceptionMessage(e));
							throw new NullPointerException(
									"Row is not initialized. Returning null.");
						}
					}
				} else {
					log.error("Row is null");
					throw new IllegalArgumentException("Row is null");
				}
			} catch (IOException | InvalidFormatException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				String sheetName, String colHeaderName, List<String> values) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				colIndex = InnerHelper.getColumnIndex(sheet, 0, colHeaderName);
				if (colIndex != 0) {
					for (int i = 0; i < values.size(); i++) {
						row = sheet.getRow(i + 1);
						if (row != null) {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						} else {
							row = sheet.createRow(i + 1);
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					log.error("Column index is null");
					throw new NullPointerException("Column index is null");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				String sheetName, int iRowStartIndex, String colHeaderName,
				List<String> values) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				colIndex = InnerHelper.getColumnIndex(sheet, 0, colHeaderName);
				if (colIndex != 0) {
					for (int i = 0; i < values.size(); i++) {
						row = sheet.getRow(iRowStartIndex + i);
						if (row != null) {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						} else {
							row = sheet.createRow(iRowStartIndex + i);
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					throw new NullPointerException(
							"Column index is null. Please verify if specified column header i scorrect");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				String sheetName, int colIndex, List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				for (int i = 0; i < values.size(); i++) {
					row = sheet.getRow(i + 1);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					} else {
						row = sheet.createRow(i + 1);
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				String sheetName, int iRowStartIndex, int colIndex,
				List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				for (int i = 0; i < values.size(); i++) {
					row = sheet.getRow(iRowStartIndex + i);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					} else {
						row = sheet.createRow(iRowStartIndex = i);
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				int sheetIndex, String colHeaderName, List<String> values) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				colIndex = InnerHelper.getColumnIndex(sheet, 0, colHeaderName);
				if (colIndex != 0) {
					for (int i = 0; i < values.size(); i++) {
						row = sheet.getRow(i + 1);
						if (row != null) {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						} else {
							row = sheet.createRow(i + 1);
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					log.error("Column index is null");
					throw new NullPointerException("Column index is null");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				int sheetIndex, int iRowStartIndex, String colHeaderName,
				List<String> values) {
			Integer colIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				colIndex = InnerHelper.getColumnIndex(sheet, 0, colHeaderName);
				if (colIndex != 0) {
					for (int i = 0; i < values.size(); i++) {
						row = sheet.getRow(iRowStartIndex + i);
						if (row != null) {
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						} else {
							row = sheet.createRow(iRowStartIndex + i);
							cell = row.createCell(colIndex, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					throw new NullPointerException(
							"Column index is null. Please verify if specified column header i scorrect");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				int sheetIndex, int colIndex, List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				for (int i = 0; i < values.size(); i++) {
					row = sheet.getRow(i + 1);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					} else {
						row = sheet.createRow(i + 1);
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToColumn(String filePath,
				int sheetIndex, int iRowStartIndex, int colIndex, List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				for (int i = 0; i < values.size(); i++) {
					row = sheet.getRow(iRowStartIndex + i);
					if (row != null) {
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					} else {
						row = sheet.createRow(iRowStartIndex = i);
						cell = row.createCell(colIndex, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToRow(String filePath, String sheetName,
				int rowIndex, int colStartIndex, List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(rowIndex);
				if (row != null) {
					for (int i = 0; i < values.size(); i++) {
						cell = row.createCell(colStartIndex + i, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				} else {
					row = sheet.createRow(rowIndex);
					for (int i = 0; i < values.size(); i++) {
						cell = row.createCell(colStartIndex + i, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToRow(String filePath, String sheetName,
				String rowName, int colStartIndex, List<String> values) {
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				if (rowIndex != 0) {
					row = sheet.getRow(rowIndex);
					if (row != null) {
						for (int i = 0; i < values.size(); i++) {
							cell = row.createCell(colStartIndex + i, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					} else {
						row = sheet.createRow(rowIndex);
						for (int i = 0; i < values.size(); i++) {
							cell = row.createCell(colStartIndex + i, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					throw new NullPointerException(
							"Row index is null. Verify row name parameter.");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToRow(String filePath, int sheetIndex,
				int rowIndex, int colStartIndex, List<String> values) {

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				row = sheet.getRow(rowIndex);
				if (row != null) {
					for (int i = 0; i < values.size(); i++) {
						cell = row.createCell(colStartIndex + i, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				} else {
					row = sheet.createRow(rowIndex);
					for (int i = 0; i < values.size(); i++) {
						cell = row.createCell(colStartIndex + i, CellType.STRING);
						cell.setCellValue(values.get(i));
					}
				}
				InnerHelper.writeToWorkbook(workbook, filePath);
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

		public static void writeStringValuesToRow(String filePath, int sheetIndex,
				String rowName, int colStartIndex, List<String> values) {
			Integer rowIndex = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheetAt(sheetIndex);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				if (rowIndex != 0) {
					row = sheet.getRow(rowIndex);
					if (row != null) {
						for (int i = 0; i < values.size(); i++) {
							cell = row.createCell(colStartIndex + i, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					} else {
						row = sheet.createRow(rowIndex);
						for (int i = 0; i < values.size(); i++) {
							cell = row.createCell(colStartIndex + i, CellType.STRING);
							cell.setCellValue(values.get(i));
						}
					}
					InnerHelper.writeToWorkbook(workbook, filePath);
					InnerHelper.closeWorkbook(workbook);
				} else {
					throw new NullPointerException(
							"Row index is null. Verify row name parameter.");
				}
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
		}

	}

	private static class InnerHelper {

		private static Workbook openWorkbookFromFilePath(String filePath)
				throws IOException, InvalidFormatException {
			Workbook returnworkbook = null;
			FileInputStream fis = null;
			File file = null;

			try {
				file = new File(filePath);
				fis = new FileInputStream(file);
				returnworkbook = WorkbookFactory.create(fis);
				bClosedWorkbook = false;
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				getExceptionMessage(e);
			} finally {
				assert fis != null;
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
				}
			}

			return returnworkbook;
		}

		private static void writeToWorkbook(Workbook workbook, String filePath) {
			FileOutputStream fo = null;

			try {
				fo = new FileOutputStream(filePath);
				workbook.write(fo);
				fo.flush();
				fo.close();
			} catch (IOException e) {
				log.error(e.getMessage());
				log.error(getExceptionMessage(e));
			}
		}

		private static void closeWorkbook(Workbook wb) {
			try {
				if (wb != null && !bClosedWorkbook) {
					workbook.close();
					bClosedWorkbook = true;
				} else {
					log.debug("Workbook already cosed.");
				}
			} catch (IOException | NullPointerException e) {
				log.error(e.getMessage());
				log.error(getExceptionMessage(e));
			}
		}

		private static Pair<Integer, Integer> getCellCoordinates(
				String sCellCoordinates) {
			Pair<Integer, Integer> returnCoordPair;
			sCellCoordinates = sCellCoordinates.toUpperCase();
			String sCol = "";
			String sRow = "";
			Integer rowIndex = null;
			Integer colIndex = null;

			if (sCellCoordinates.length() == 2) {
				sCol = String.valueOf(sCellCoordinates.charAt(0));
				sRow = String.valueOf(sCellCoordinates.charAt(1));
			} else if (sCellCoordinates.length() == 3) {
				sCol = sCellCoordinates.substring(0, 2);
				sRow = String.valueOf(sCellCoordinates.charAt(2));
			} // todo add more scenarios to cover broader range of columns

			rowIndex = Integer.valueOf(sRow) - 1;
			log.debug("Returning row index: " + rowIndex);
			colIndex = InnerHelper.getColumnIndex(sCol);
			log.debug("Returning column index: " + colIndex);

			returnCoordPair = new ImmutablePair<Integer, Integer>(rowIndex, colIndex);

			return returnCoordPair;
		}

		// TODO verify how boolean fix is performing with different scenarios
		private static List<String> getColDataList(Sheet sheet, int rowStart,
				int colIndex, boolean includeBlankCells) {
			List<String> returnColumnValues = new ArrayList<>();
			String cellValue = "";

			log.debug("Last row number: " + sheet.getLastRowNum());

			for (int i = rowStart; i <= sheet.getLastRowNum(); ++i) {
				row = sheet.getRow(i);
				if (row == null & includeBlankCells) {
					returnColumnValues.add("");
				} else {
					cell = row.getCell(colIndex,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue = formatCellToString(cell);
					if (!includeBlankCells && cellValue.equals("")) {
						continue;
					}
					returnColumnValues.add(cellValue);

					if (!includeBlankCells
							& cell.getCellTypeEnum().equals(CellType.FORMULA)) {
						returnColumnValues.remove(cell.toString());
					}
				}
			}

			return returnColumnValues;
		}

		private static List<String> getColDataList(Sheet sheet, int rowStart,
				int rowEnd, int colIndex, boolean includeBlankCells) {
			List<String> returnColumnValues = new ArrayList<>();
			String cellValue = "";

			log.debug("Last row number: " + rowEnd);

			for (int i = rowStart; i <= rowEnd - 1; ++i) {
				row = sheet.getRow(i);
				if (row == null & includeBlankCells) {
					returnColumnValues.add("");
				} else {
					cell = row.getCell(colIndex,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue = formatCellToString(cell);

					if (!includeBlankCells && cellValue.equals("")) {
						continue;
					}

					returnColumnValues.add(cellValue);
					if (!includeBlankCells
							& cell.getCellTypeEnum().equals(CellType.FORMULA)) {
						returnColumnValues.remove(cell.toString());
					}
				}
			}
			return returnColumnValues;
		}

		private static List<String> getColDataList(Sheet sheet, int rowStart,
				int rowEnd, int colIndex1, int colIndex2, String sDelimiter,
				boolean includeBlankCells) {
			List<String> returnColumnValues = new ArrayList<>();
			Cell cell1;
			Cell cell2;
			String cellValue1 = "";
			String cellValue2 = "";
			String returnCellValue = "";

			log.debug("Last row number: " + rowEnd);

			for (int i = rowStart; i <= rowEnd - 1; i++) {
				row = sheet.getRow(i);
				if (row == null & includeBlankCells) {
					returnColumnValues.add("");
				} else {
					cell1 = row.getCell(colIndex1,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue1 = formatCellToString(cell1);
					cell2 = row.getCell(colIndex2,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue2 = formatCellToString(cell2);

					if (!includeBlankCells
							&& (cellValue1.equals("") || cellValue2.equals(""))) {
						continue;
					}
					returnCellValue = cellValue1 + sDelimiter + cellValue2;
					returnColumnValues.add(returnCellValue);
				}
				// TODO: 8/16/2017 modify according to new functionality with double
				// cell value
				/*if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}
				if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}*/
			}
			return returnColumnValues;
		}

		private static List<String> getColDataList(Sheet sheet, int rowIndex,
				int colIndex, boolean copyCellValue, String sDelimiterStart,
				String sDelimiterEnd, boolean includeBlankCells) {
			List<String> returnColumnValues = new ArrayList<>();
			String cellValue = "";
			String returnCellValue = "";
			int lastRowNum = sheet.getLastRowNum();

			log.debug("Last row number: " + lastRowNum);

			for (int i = rowIndex; i <= lastRowNum; i++) {
				row = sheet.getRow(i);
				if (row == null & includeBlankCells) {
					returnColumnValues.add("");
				} else {
					cell = row.getCell(colIndex,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue = formatCellToString(cell);

					if (!includeBlankCells && cellValue.equals("")) {
						continue;
					}

					if (copyCellValue) {
						returnCellValue = cellValue + sDelimiterStart + cellValue
								+ sDelimiterEnd;
						returnColumnValues.add(returnCellValue);
					} else {
						returnCellValue = cellValue;
						returnColumnValues.add(returnCellValue);
					}
				}
				// TODO: 8/16/2017 modify according to new functionality with double
				// cell value
				/*if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}
				if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}*/
			}
			return returnColumnValues;
		}

		private static List<String> getColDataList(Sheet sheet, int rowStart,
				int rowEnd, int colIndex, boolean copyCellValue, String sDelimiterStart,
				String sDelimiterEnd, boolean includeBlankCells) {
			List<String> returnColumnValues = new ArrayList<>();
			String cellValue = "";
			String returnCellValue = "";

			log.debug("Last row number: " + rowEnd);

			for (int i = rowStart; i <= rowEnd - 1; i++) {
				row = sheet.getRow(i);
				if (row == null & includeBlankCells) {
					returnColumnValues.add("");
				} else {
					cell = row.getCell(colIndex,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellValue = formatCellToString(cell);

					if (!includeBlankCells && cellValue.equals("")) {
						continue;
					}

					if (copyCellValue) {
						returnCellValue = cellValue + sDelimiterStart + cellValue
								+ sDelimiterEnd;
						returnColumnValues.add(returnCellValue);
					} else {
						returnCellValue = cellValue;
						returnColumnValues.add(returnCellValue);
					}
				}
				// TODO: 8/16/2017 modify according to new functionality with double
				// cell value
				/*if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}
				if (!includeBlankCells & cell2.getCellTypeEnum().equals(CellType.FORMULA)) {
				    returnColumnValues.remove(cell1.toString());
				}*/
			}
			return returnColumnValues;
		}

		private static List<String> getRowDataList(Sheet sheet, int rowIndex,
				int iColStart, int iColEnd, boolean includeBlankCells) {
			List<String> returnRowValues = new ArrayList<>();
			String cellValue = "";

			log.debug("Last row number: " + sheet.getLastRowNum());

			for (int i = iColStart; i <= iColEnd; i++) {
				row = sheet.getRow(rowIndex);
				cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cellValue = formatCellToString(cell);
				if (!includeBlankCells && cellValue.equals("")) {
					continue;
				}
				returnRowValues.add(cellValue);
				if (!includeBlankCells
						& cell.getCellTypeEnum().equals(CellType.FORMULA)) {
					returnRowValues.remove(cell.toString());
				}
			}

			return returnRowValues;
		}

		private static Integer getLastCellNumber(Sheet sheet, int rowIndex,
				boolean includeBlank) {
			row = sheet.getRow(rowIndex);
			Integer lastCell = Math.max(row.getLastCellNum(), 3);

			if (!includeBlank) {
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					if (formatCellToString(cells.next()).equals("")) {
						lastCell = lastCell - 1;
					}
				}
			}

			log.debug("Returning last cell num: " + lastCell);

			return lastCell;
		}

		/**
		 * Get the index of a column with specified header name
		 * Header must be declared in the first (1) row in Excel sheet
		 *
		 * @param colHeaderName header name to look for in excel sheet
		 * @return column index value as int
		 * @throws NullPointerException if the column header does not match any of the values in first row of Excel sheet
		 */
		private static int getColumnIndex(Row row, String colHeaderName)
				throws NullPointerException {
			Integer returnColIndex = null;
			short iLastCellNumber = row.getLastCellNum();
			String headerCellData;

			for (int i = 0; i <= iLastCellNumber; ++i) {
				cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

				if (!cell.getStringCellValue().equals("")) {
					headerCellData = formatCellToString(cell);
					log.debug("Returning cell data for header cell: " + headerCellData);

					if (headerCellData.equals(colHeaderName)) {
						returnColIndex = i;
						log.debug("Found a matching header with index: " + returnColIndex);
						break;
					}
				}
			}

			if (returnColIndex == null) {
				try {
					throw new NullPointerException(
							"Column with specified name not found");
				} catch (NullPointerException e) {
					// System.err.println("ERROR. Column with specified name not found.
					// Verify it's presence in Excel sheet and spelling.");
					// System.err.println("Returning null for column index value.");
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
					throw e;
				}
			} else {
				return returnColIndex;
			}
		}

		/**
		 * Get the index of a column with specified header name
		 * Header must be declared in a row with rowIndex parameter in Excel sheet
		 *
		 * @param colHeaderName header name to look for in excel sheet
		 * @param rowIndex      index of the row where column header is
		 * @return column index value as int
		 * @throws NullPointerException if the column header does not match any of the values
		 */
		private static int getColumnIndex(Sheet sheet, int rowIndex,
				String colHeaderName) throws NullPointerException {
			Integer returnColIndex = null;
			row = sheet.getRow(rowIndex);
			short iLastCellNumber = row.getLastCellNum();
			String headerCellData = null;

			for (int i = 0; i <= iLastCellNumber; ++i) {
				cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				headerCellData = formatCellToString(cell);
				if (!headerCellData.equals("")) {
					// headerCellData = formatCellToString(cell);
					log.debug("Returning cell data for header cell: " + headerCellData);

					if (headerCellData.equals(colHeaderName)) {
						returnColIndex = i;
						log.debug("Found a matching header with index: " + returnColIndex);
						break;
					}
				}
			}

			if (returnColIndex == null) {
				try {
					throw new NullPointerException(
							"Column with specified name not found");
				} catch (NullPointerException e) {
					// System.err.println("ERROR. Column with specified name not found.
					// Verify it's presence in Excel sheet and spelling.");
					// System.err.println("Returning null for column index value.");
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
					throw e;
				}
			} else {
				return returnColIndex;
			}
		}

		/**
		 * Get the index of a column with specified header name
		 *
		 * @param columnName name of the column to look for in excel sheet (i.e. A, B, AZ) - supports up to AZ column
		 * @return column index value as int
		 * @throws IllegalArgumentException if the column value is greater than AZ
		 */
		private static int getColumnIndex(String columnName)
				throws IllegalArgumentException {
			int length = columnName.length();
			int outerIndex = 0;
			int innerIndex = 0;
			int i = 0;
			ArrayList<Character> chars = new ArrayList<>();
			Integer returnColIndex = null;

			columnName = columnName.toUpperCase();

			if (length > 2) {
				try {
					throw new IllegalArgumentException(
							"Column values must be between A and AZ");
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
					throw e;
				}
			}

			for (int l = 0; l < length; l++) {
				chars.add(columnName.charAt(l));
				char character = chars.get(l);
				// log.debug("Char at: " + l + " is: " + character);
				log.debug(String.format("Char at: %d is: %c", l, chars.get(l)));
				for (char col = 'A'; col <= 'Z'; col++) {
					if (col == character) {
						log.debug("Success");
						log.debug(String.format("%d", i));
						innerIndex = i;
						break;
					} else {
						log.debug("Error");
						i++;
					}
				}

				if (l == 0) {
					outerIndex = innerIndex;
				} else {
					outerIndex = 26 + innerIndex;
				}
				log.debug("Outer index: " + outerIndex);
			}
			returnColIndex = outerIndex;

			return returnColIndex;
		}

		/**
		 * Get the index of a row with specified name in the first (A) column
		 *
		 * @param rowName row name to look for in excel sheet (to be declared in the first column)
		 * @return row index value as int
		 * @throws NullPointerException if the row name does not match any of the values in first row of Excel sheet
		 */
		private static int getRowIndex(Sheet sheet, String rowName)
				throws NullPointerException {
			Integer returnRowIndex = null;
			int lastRowNumber = sheet.getLastRowNum();
			String rowCellData;

			for (int i = 0; i <= lastRowNumber; ++i) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					rowCellData = formatCellToString(cell);
					if (!rowCellData.equals("")) {
						log.debug("Returning cell data for row: " + rowCellData);

						if (rowCellData.equals(rowName)) {
							returnRowIndex = i;
							log.debug("Found a matching row with index: " + returnRowIndex);
							break;
						}
					}
				} else {
					throw new NullPointerException("Row is not present. Returning null");
				}
			}

			if (returnRowIndex == null) {
				try {
					throw new NullPointerException("Row with specified name not found");
				} catch (NullPointerException e) {
					System.err.println(
							"ERROR. Row with specified name not found. Verify it's presence in Excel sheet and spelling.");
					System.err.println("Returning null for row index value.");
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
					throw e;
				}
			} else {
				return returnRowIndex;
			}
		}

		/**
		 * Get the range of a rows starting from row with specified name in the first (A) column
		 *
		 * @param rowName row name to look for in excel sheet (to be declared in the first column)
		 * @return row index value as int
		 * @throws NullPointerException if the row name does not match any of the values in first row of Excel sheet
		 */
		private static Pair<Integer, Integer> getRowsRange(Sheet sheet,
				String rowName) throws NullPointerException {
			Pair<Integer, Integer> returnRowRangePair = null;
			Integer startRowIndex = null;
			Integer endRowIndex = null;

			int lastRowNumber = sheet.getLastRowNum();
			String rowCellData;
			Row row;
			Cell cell;

			firstRow: for (int i = 0; i <= lastRowNumber; ++i) {
				log.debug("Getting start row index");
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					rowCellData = formatCellToString(cell);
					if (!rowCellData.equals("")) {
						log.debug("Returning cell data for row: " + rowCellData);

						if (rowCellData.equals(rowName)) {
							startRowIndex = i;
							log.debug(
									"Found a matching start row with index: " + startRowIndex);
							break firstRow;
						}
					}
				} else {
					throw new NullPointerException("Row is not present. Returning null");
				}
			}

			if (startRowIndex != null) {
				log.debug("Getting end row index");
				endRow: for (int iRowStartCount = startRowIndex
						+ 1; iRowStartCount <= lastRowNumber; iRowStartCount++) {
					row = sheet.getRow(iRowStartCount);
					if (row != null) {
						cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						rowCellData = formatCellToString(cell);
						if (!rowCellData.equals("")) {
							endRowIndex = iRowStartCount;
							log.debug("Found a matching end row with index: " + endRowIndex);
							break endRow;
						} else {
							endRowIndex = lastRowNumber;
							log.debug("Found a matching end row with index: " + endRowIndex);
						}
					} else {
						throw new NullPointerException(
								"Row is not present. Returning null");
					}
				}
				returnRowRangePair = new ImmutablePair<Integer, Integer>(startRowIndex,
						endRowIndex);

			} else {
				throw new NullPointerException("Starting row index is null.");
			}

			if (returnRowRangePair.getLeft() == null
					& returnRowRangePair.getRight() == null) {
				try {
					throw new NullPointerException("Row with specified name not found");
				} catch (NullPointerException e) {
					System.err.println(
							"ERROR. Row with specified name not found. Verify it's presence in Excel sheet and spelling.");
					System.err.println("Returning null for row index value.");
					log.error(e.getMessage());
					log.error(getExceptionMessage(e));
					throw e;
				}
			} else {
				log.debug(
						"Returning range pair: first = " + returnRowRangePair.getLeft()
								+ ", second = " + returnRowRangePair.getRight());
				return returnRowRangePair;
			}
		}

		private static String formatCellToString(Cell cell) {
			log.debug("Getting formated value for cell: " + cell);
			String cellValue = "";
			DataFormatter formatter = new DataFormatter();
			CellType cellType = cell.getCellTypeEnum();

			try {
				Workbook workbook;
				log.debug("Switching cell types");
				switch (cellType) {
				case NUMERIC:
					// log.debug("Switch case NUMERIC");
					cellValue = String.valueOf(formatter.formatCellValue(cell));
					log.debug("Cell value: " + cellValue);
					if (cellDateFormat(cellValue)) {
						workbook = null;
						String dateFormatString = getDateFormat();
						// log.debug("Date format string: " + dateFormatString);
						SimpleDateFormat simpleDateFormat;

						if (!dateFormatString.equals("")) {
							simpleDateFormat = new SimpleDateFormat(getDateFormat());
						} else {
							simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
						}

						Date today = cell.getDateCellValue();
						// log.debug("Date: " + today);
						cellValue = simpleDateFormat.format(today);
						// log.debug("Cell value: " + cellValue);
					}
					break;

				case STRING:
					// log.debug("Switch case STRING");
					cellValue = String.valueOf(formatter.formatCellValue(cell));
					// log.debug("Cell value: " + cellValue);
					break;

				case FORMULA:
					// log.debug("Switch case FORMULA");
					workbook = cell.getSheet().getWorkbook();
					// log.debug("Workbook: " + workbook);
					FormulaEvaluator formulaEval = workbook.getCreationHelper()
							.createFormulaEvaluator();
					// log.debug("Formula evaluator: " + formulaEval);
					formulaEval.evaluate(cell);
					cellValue = String
							.valueOf(formatter.formatCellValue(cell, formulaEval));
					// log.debug("Cell value: " + cellValue);
					if (cellDateFormat(cellValue)) {
						DateFormat dateFormat = null;
						String dateFormatString = getDateFormat();
						// log.debug("Date format string: " + dateFormatString);

						if (!dateFormatString.equals("")) {
							dateFormat = new SimpleDateFormat(getDateFormat());
							// log.debug("Date format: " + dateFormat);
						} else {
							dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							// log.debug("Date format: " + dateFormat);
						}

						Date today = cell.getDateCellValue();
						// log.debug("Date: " + today);
						cellValue = dateFormat.format(today);
						// log.debug("Cell value: " + cellValue);
					}
					break;

				case BLANK:
					// log.debug("Switch case BLANK");
					cellValue = String.valueOf("");
					// log.debug("Cell value: " + cellValue);
					break;

				case BOOLEAN:
					// log.debug("Switch case BOOLEAN");
					cellValue = String.valueOf(formatter.formatCellValue(cell));
					// log.debug("Cell value: " + cellValue);
					break;

				case ERROR:
					// log.debug("Switch case ERROR");
					cellValue = String.valueOf(formatter.formatCellValue(cell));
					// log.debug("Cell value: " + cellValue);
				case _NONE:
					// log.debug("Switch case NONE");
					break;
				default:
					break;
				}
			} catch (Exception e) {
				getExceptionMessage(e);
			}
			log.debug("Returning cell value: " + cellValue);
			return cellValue;
		}

		private static boolean cellDateFormat(String dateValue) {
			String regexDDMMYY = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
			String regexDDMMYYYY = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
			String regexMMDDYYYY = "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$";
			return Pattern.matches(regexMMDDYYYY, dateValue)
					|| (Pattern.matches(regexDDMMYYYY, dateValue)
							|| Pattern.matches(regexDDMMYY, dateValue));
		}

		private static String getDateFormat() {
			log.debug("Returning date format String value: " + formattedDateValue);
			return formattedDateValue;
		}

		private static String getExceptionMessage(Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			return writer.toString();
		}
	}

	public static class Utils {
		public Utils() {
		}

		/**
		 * Set a date format to format dates as per requirement
		 *
		 * @param dateFormatString date format (i.e. "dd-MM-yyyy")
		 */
		public static void setDateFormat(String dateFormatString) {
			formattedDateValue = dateFormatString;
			log.debug("Set date format string value: " + dateFormatString);
		}

		public static Integer getNumberOfNonEmptyColumns(String filePath,
				String sheetName, String rowName, boolean includeBlank) {
			int rowIndex;
			Integer returnLastCell = null;

			try {
				workbook = InnerHelper.openWorkbookFromFilePath(filePath);
				sheet = workbook.getSheet(sheetName);
				rowIndex = InnerHelper.getRowIndex(sheet, rowName);
				returnLastCell = Integer.valueOf(
						InnerHelper.getLastCellNumber(sheet, rowIndex, includeBlank));
				InnerHelper.closeWorkbook(workbook);
			} catch (IOException | InvalidFormatException e) {
				log.error(e.getMessage());
				log.error(InnerHelper.getExceptionMessage(e));
			} finally {
				InnerHelper.closeWorkbook(workbook);
			}
			return returnLastCell;
		}

	}
}
