package example;

import java.io.File;

/**
 * Copyright 2019 Serguei Kouzmine
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

// org.apache.log4j.Category 
// was deprecated and replaced by  
// org.apache.log4j.Logger;
// import org.apache.log4j.Logger;
// 
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel export and load class for Test Randomizer
 * see also: https://github.com/sergueik/SWET/blob/master/src/main/java/com/github/sergueik/swet/ExcelFileUtils.java
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class ExcelFileUtils {

	private String sheetName = "Sheet1"; // name of the sheet
	private Map<Integer, String> rowData = new HashMap<>();
	private List<String> columnHeaders = new ArrayList<>();
	private List<String> extractColumnHeaders = new ArrayList<>();
	private String sheetColumnHeader;
	private boolean useTemporaryFileWhenSave = true;
	// useful to set to true when developing new functionality,
	// protects against leaked file handles breaking write operations
	private String newColumnHeader = null;
	private boolean debug = false;
	// name of excel file - default is under src/test/resources of the current
	// project
	private String spreadsheetFilePath = null;
	private String tmpSaveFilePath = null;
	private String sheetFormat = "Excel 2007"; // format of the sheet

	private List<Map<Integer, String>> tableData = new ArrayList<>();

	public void setSheetName(String value) {
		this.sheetName = value;
	}

	public void setUseTemporaryFileWhenSave(Boolean value) {
		this.useTemporaryFileWhenSave = value;
	}

	public boolean getUseTemporaryFileWhenSave() {
		return this.useTemporaryFileWhenSave;
	}

	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> data) {
		this.columnHeaders = data;
	}

	public List<String> getExtractColumnHeaders() {
		return extractColumnHeaders;
	}

	public void setExtractColumnHeaders(List<String> data) {
		this.extractColumnHeaders = data;
	}

	public String getSheetColumnHeader() {
		return sheetColumnHeader;
	}

	public void setSheetColumnHeader(String value) {
		sheetColumnHeader = value;
	}

	public void setTableData(List<Map<Integer, String>> data) {
		this.tableData = data;
	}

	public void setDebug(Boolean value) {
		this.debug = value;
	}

	public void setSpreadsheetFilePath(String value) {
		this.spreadsheetFilePath = value;
		this.tmpSaveFilePath = System.getenv("TEMP") + "/"
				+ value.replaceAll("^.+/", "");
	}

	public String getSpreadsheetFilePath() {
		return this.spreadsheetFilePath;
	}

	public void setSheetFormat(String data) {
		this.sheetFormat = data;
	}

	@SuppressWarnings("unused")
	public List<String> readSheetNames() {
		List<String> result = new ArrayList<>();
		XSSFWorkbook xssWorkBook = null;
		try {
			xssWorkBook = new XSSFWorkbook(spreadsheetFilePath);
			if (xssWorkBook == null) {
				return new ArrayList<>();
			}
			xssWorkBook.getActiveSheetIndex();
			int numberOfSheets = xssWorkBook.getNumberOfSheets();
			for (int cnt = 0; cnt != numberOfSheets; cnt++) {
				XSSFSheet sheet = xssWorkBook.getSheetAt(cnt);
				result.add(sheet.getSheetName());
			}
			xssWorkBook.close();
		} catch (IOException e) {
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void readSpreadsheet() {
		readSpreadsheet(Optional.empty());
	}

	public void readSpreadsheet(
			Optional<List<Map<Integer, String>>> dataCollector) {

		List<Map<Integer, String>> _dataCollector = (dataCollector.isPresent())
				? dataCollector.get() : new ArrayList<>();

		if (sheetFormat.matches("(?i:Excel 2007)")) {
			if (debug) {
				System.err.println("Reading Excel 2007 data sheet.");
			}
			readXLSXFile(_dataCollector);
		} else if (sheetFormat.matches("(?i:Excel 2003)")) {
			if (debug) {
				System.err.println("Reading Excel 2003 data sheet.");
			}
			readXLSFile(_dataCollector);
		} else {
			if (debug) {
				System.err.println("Unrecognized data sheet format: " + sheetFormat);
			}
		}
		if (debug) {
			for (Map<Integer, String> rowData : _dataCollector) {
				for (Map.Entry<Integer, String> columnData : rowData.entrySet()) {
					System.err.println(
							columnData.getKey().toString() + " => " + columnData.getValue());
				}
				System.err.println("---");
			}
		}
	}

	private void readXLSFile(List<Map<Integer, String>> data) {

		Map<Integer, String> rowData = new HashMap<>();
		try (InputStream fileInputStream = new FileInputStream(
				spreadsheetFilePath)) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {

				row = (HSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();

				int cellNum = 0;
				rowData = new HashMap<>();
				while (cells.hasNext()) {

					cell = (HSSFCell) cells.next();
					CellType type = cell.getCellTypeEnum();
					String cellValue = null;
					if (type == org.apache.poi.ss.usermodel.CellType.STRING) {
						cellValue = cell.getStringCellValue();
					} else if (type == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
						cellValue = String.format("%f", cell.getNumericCellValue());
					} else if (type == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
						cellValue = String.format("%b", cell.getBooleanCellValue());
					} else {
						// NOTE: not parsing either of
						// org.apache.poi.ss.usermodel.CellType.FORMULA
						// org.apache.poi.ss.usermodel.CellType.ERROR
						cellValue = "?";
					}
					if (debug) {
						if (debug) {
							System.err.println("=>" + cellValue + " ");
						}
					}
					rowData.put(cellNum, cellValue);
					cellNum++;
				}
				data.add(rowData);
				if (debug) {
					System.err.println("");
				}
			}
			hssfWorkbook.close();
			fileInputStream.close();
			// java 7 improvement smell:
			// [WARNING] "explicit call to close() on an auto-closeable resource"
			// NOTE: appears impossible to suppress that warning, at least not via
			// @SuppressWarnings("resource")
			// see also:
			// https://dzone.com/articles/a-subtle-autocloseable-contract-change-between-jav
			// https://stackoverflow.com/questions/1205995/what-is-the-list-of-valid-suppresswarnings-warning-names-in-java
		} catch (IOException e) {
			System.err
					.println(String.format("Exception reading XLS file %s (ignored): ",
							spreadsheetFilePath) + e.getMessage());
		}
	}

	private void readXLSXFile(List<Map<Integer, String>> data) {

		Map<Integer, String> rowData = new HashMap<>();
		try (InputStream fileInputStream = new FileInputStream(
				spreadsheetFilePath)) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
			XSSFRow row;
			// Iterator<org.apache.poi.ss.usermodel.Cell> cells;
			Iterator<Cell> cells;
			Map<String, String> columns = new HashMap<>();
			XSSFCell cell;
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();

				if (row.getRowNum() == 0) {
					cells = row.cellIterator();
					while (cells.hasNext()) {

						cell = (XSSFCell) cells.next();
						int columnIndex = cell.getColumnIndex();
						String columnHeader = cell.getStringCellValue();
						String columnName = CellReference
								.convertNumToColString(cell.getColumnIndex());
						if (extractColumnHeaders.contains(columnHeader)) {
							columns.put(columnName, columnHeader);
							if (debug) {
								System.err.println(columnIndex + " = " + columnName + " "
										+ columns.get(columnName));
							}
						}
					}
					// skip the header
					continue;
				}

				// Iterator<Cell> cells = row.cellIterator();
				cells = row.cellIterator();
				int cellNum = 0;
				rowData = new HashMap<>();
				while (cells.hasNext()) {
					String cellValue = null;
					cell = (XSSFCell) cells.next();
					CellType type = cell.getCellTypeEnum();

					// TODO: switch to columnName
					String columnName = CellReference
							.convertNumToColString(cell.getColumnIndex());
					if (columns.containsKey(columnName)) {
						if (type == org.apache.poi.ss.usermodel.CellType.STRING) {
							cellValue = cell.getStringCellValue();
						} else if (type == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
							cellValue = String.format("%f", cell.getNumericCellValue());
						} else if (type == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
							cellValue = String.format("%b", cell.getBooleanCellValue());
						} else {
							// NOTE: not parsing either of
							// org.apache.poi.ss.usermodel.CellType.FORMULA
							// org.apache.poi.ss.usermodel.CellType.ERROR
							cellValue = "?";
						}
						if (debug) {
							System.err.println("=>" + cellValue + " ");
						}
						rowData.put(cellNum, cellValue);
						cellNum++;
					}
				}
				data.add(rowData);
				if (debug) {
					System.err.println("");
				}
			}
			xssfWorkbook.close();
			fileInputStream.close();
		} catch (IOException e) {
			System.err
					.println(String.format("Exception reading XLSX file %s (ignored): ",
							spreadsheetFilePath) + e.getMessage());
		}
	}

	public void writeSpreadsheet() {
		if (sheetFormat.matches("(?i:Excel 2007)")) {
			if (debug) {
				System.err.println("Writing Excel 2007 data sheet.");
			}
			writeXLSXFile();
		} else if (sheetFormat.matches("(?i:Excel 2003)")) {
			if (debug) {
				System.err.println("Writing Excel 2003 data sheet.");
			}
			writeXLSFile();
		} else {
			if (debug) {
				System.err.println("Unrecognized data sheet format: " + sheetFormat);
			}
		}
	}

	private void writeXLSXFile() {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xssfWorkbook.createSheet(sheetName);
		for (int row = 0; row < tableData.size(); row++) {
			XSSFRow xssfRow = sheet.createRow(row);
			rowData = tableData.get(row);
			for (int col = 0; col < rowData.size(); col++) {
				XSSFCell cell = xssfRow.createCell(col);
				cell.setCellValue(rowData.get(col));
				if (debug) {
					System.err
							.println("Writing " + row + " " + col + "  " + rowData.get(col));
				}
			}
		}
		if (useTemporaryFileWhenSave) {
			// because of the bug in earlier revision
			// the spreadsheetFilePath was locked
			try {
				if (debug) {
					System.err.println("Writing file: " + tmpSaveFilePath);
				}
				OutputStream fileOut = new FileOutputStream(tmpSaveFilePath);
				xssfWorkbook.write(fileOut);
				fileOut.flush();
				fileOut.close();

				xssfWorkbook.close();
			} catch (IOException e) {
				System.err.println(
						"Exception (ignored) during saving the workbook " + e.toString());
			}

			copyFile(new File(tmpSaveFilePath), new File(spreadsheetFilePath));
			deleteFile(tmpSaveFilePath);
		} else {
			// save the file into the designated spreadsheetFilePath property
			// without using a temporary tmpSaveFilePath file
			if (debug) {
				System.err.println("Writing file: " + spreadsheetFilePath);
			}
			// java 7 improvement smell
			try (FileOutputStream fileOutputStream = new FileOutputStream(
					spreadsheetFilePath)) {
				xssfWorkbook.write(fileOutputStream);
				xssfWorkbook.close();
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				System.err.println(String.format(
						"Exception saving the workbook to XLSX file %s (ignored): ",
						spreadsheetFilePath) + e.getMessage());
			}
		}

	}

	private void writeXLSFile() {

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);

		for (int row = 0; row < tableData.size(); row++) {
			HSSFRow hssfRow = sheet.createRow(row);
			rowData = tableData.get(row);
			for (int col = 0; col < rowData.size(); col++) {
				HSSFCell hssfCell = hssfRow.createCell(col);
				hssfCell.setCellValue(rowData.get(col));
			}
		}

		if (useTemporaryFileWhenSave) {
			// because of the bug in earlier revision
			// the spreadsheetFilePath was locked
			if (debug) {
				System.err.println("Writing file: " + tmpSaveFilePath);
			}

			try (FileOutputStream fileOutputStream = new FileOutputStream(
					tmpSaveFilePath)) {
				hssfWorkbook.write(fileOutputStream);
				hssfWorkbook.close();
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				System.err
						.println(String.format("Exception saving XLS file %s (ignored): ",
								tmpSaveFilePath) + e.getMessage());
			}
			if (debug) {
				System.err.println(
						"Rename the file " + tmpSaveFilePath + " " + spreadsheetFilePath);
			}
			copyFile(new File(tmpSaveFilePath), new File(spreadsheetFilePath));
			deleteFile(tmpSaveFilePath);
		} else {
			// save the file into the designated spreadsheetFilePath property
			// without using a temporary tmpSaveFilePath file
			if (debug) {
				System.err.println("Writing file: " + spreadsheetFilePath);
			}
			try (FileOutputStream fileOutputStream = new FileOutputStream(
					spreadsheetFilePath)) {
				hssfWorkbook.write(fileOutputStream);
				hssfWorkbook.close();
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				System.err
						.println(String.format("Exception saving XLS file %s (ignored): ",
								spreadsheetFilePath) + e.getMessage());
			}
		}
	}

	public List<String> readColumnHeaders() {

		List<String> result = new ArrayList<>();
		Map<String, String> columns = new HashMap<>();
		XSSFWorkbook xssWorkBook = null;
		try {
			xssWorkBook = new XSSFWorkbook(spreadsheetFilePath);
		} catch (IOException e) {
		}
		if (xssWorkBook == null) {
			return new ArrayList<>();
		}
		XSSFSheet sheet = xssWorkBook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				Iterator<org.apache.poi.ss.usermodel.Cell> cells = row.cellIterator();
				while (cells.hasNext()) {

					XSSFCell cell = (XSSFCell) cells.next();
					int columnIndex = cell.getColumnIndex();
					String columnHeader = cell.getStringCellValue();
					String columnName = CellReference
							.convertNumToColString(cell.getColumnIndex());
					columns.put(columnName, columnHeader);
					if (debug) {
						System.err
								.println(columnIndex + "[" + columnName + "]: " + columnHeader);
					}
				}
			}
		}
		try {
			xssWorkBook.close();
			result = new ArrayList<String>(columns.values());
		} catch (IOException e) {
			System.err.println("Exception (ignored): " + e.toString());
		}
		if (debug) {
			System.err.println("Return: " + result.toString());
		}
		this.columnHeaders = result;
		return result;
	}

	// TODO: narrow to only be used for known/unknown commenting
	public void appendColumnHeader(String columnHeader) {
		// TODO: process Excel 2003 files
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(this.spreadsheetFilePath);
		} catch (IOException e) {
		}
		if (workBook == null) {
			return;
		}
		XSSFSheet sheet = workBook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		Iterator<org.apache.poi.ss.usermodel.Cell> cells;
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				XSSFCell cell = row.createCell(columnHeaders.size());
				cell.setCellValue(columnHeader);
				if (debug) {
					System.err.println("Adding column # " + (columnHeaders.size())
							+ " with the name: " + columnHeader);
				}
				cells = row.cellIterator();
			}
		}

		// because of the bug in earlier revision
		// the spreadsheetFilePath was locked
		if (useTemporaryFileWhenSave) {

			try {
				OutputStream fileOutputStream = new FileOutputStream(tmpSaveFilePath);
				workBook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();

				workBook.close();
				copyFile(new File(tmpSaveFilePath), new File(spreadsheetFilePath));
			} catch (IOException e) {
				System.err.println(
						"Exception (ignored) during saving the workbook " + e.toString());
			}
			deleteFile(tmpSaveFilePath);
		} else {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(
						spreadsheetFilePath);
				workBook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				workBook.close();
			} catch (IOException e) {
				System.err.println(
						"Exception (ignored) during saving the workbook " + e.toString());
			}
		}
	}

	private void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.delete()) {
			if (debug) {
				System.out.println("File " + filePath + " deleted successfully");
			}
		} else {
			if (debug) {
				System.out.println("Failed to delete the file " + filePath);
			}
		}
	}

	private void copyFile(File source, File destination) {
		try {
			if (destination.isDirectory())
				destination = new File(destination, source.getName());

			FileInputStream fileInputStream = new FileInputStream(source);
			copyFile(fileInputStream, destination);
		} catch (IOException e) {
			System.err.println(String.format("Exception copying %s to %s (ignored): ",
					source.getName(), destination.getName()) + e.getMessage());
		}
	}

	private void copyFile(InputStream inputStream, File destination) {

		try (OutputStream fileOutputStream = new FileOutputStream(destination)) {

			byte[] buffer = new byte[1024];
			int bytesRead = inputStream.read(buffer);

			while (bytesRead >= 0) {
				fileOutputStream.write(buffer, 0, bytesRead);
				bytesRead = inputStream.read(buffer);
			}
			inputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			System.err
					.println(String.format("Exception copying to file %s (ignored): ",
							destination.getName()) + e.getMessage());
		}
	}
}
