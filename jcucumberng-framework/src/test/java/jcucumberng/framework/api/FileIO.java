package jcucumberng.framework.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * {@code FileIO} handles actions for manipulating files or documents.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class FileIO {

	private FileIO() {
		// Prevent instantiation
	}

	/**
	 * Checks if a file exists in a specified directory. Set {@code file.dir} in
	 * {@code framework.properties}.
	 * 
	 * @param prefix the beginning of a filename, can be a substring
	 * @param suffix can be the file extension (e.g. {@code .txt})
	 * @return boolean - true if matching file is found using given prefix and
	 *         suffix
	 * @throws IOException
	 */
	public static boolean doesFileExist(String prefix, String suffix) throws IOException {
		String directory = ConfigLoader.frameworkConf("file.dir");
		File[] files = new File(directory).listFiles();

		String fileName = null;
		for (File file : files) {
			if (file.isFile()) {
				fileName = file.getName();
				if (fileName.startsWith(prefix)) {
					if (fileName.endsWith(suffix)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Extracts readable text from a specified PDF file. Set {@code pdf.file.path}
	 * in {@code framework.properties}. File path must be absolute.
	 * 
	 * @return String - extracted text from PDF file
	 * @throws IOException
	 */
	public static String extractPdfText() throws IOException {
		PdfReader pdfReader = new PdfReader(ConfigLoader.frameworkConf("pdf.file.path"));
		int pages = pdfReader.getNumberOfPages();

		String pdfText = "";
		for (int ctr = 1; ctr < pages + 1; ctr++) {
			pdfText += PdfTextExtractor.getTextFromPage(pdfReader, ctr); // Page number cannot be 0 or will throw NPE
		}

		pdfReader.close();
		return pdfText;
	}

	/**
	 * Converts an Excel file (xlsx only) to a 2D array. The xlsx can have 1 header
	 * row. If no header row is present, begin data on second row. Else, data in the
	 * first row will be omitted. Each row must have the same number of columns.
	 * Each column must have a value.
	 * 
	 * @param xlsxFilePath the absolute path of the xlsx file
	 * @param sheetName    the name of the sheet to be read (defaults to first sheet
	 *                     if blank)
	 * @return Object[ ][ ] - the String values in 2D array
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Object[][] convertExcelTo2DArray(String xlsxFilePath, String sheetName)
			throws FileNotFoundException, IOException {

		File xlsxFile = new File(xlsxFilePath);
		InputStream inStream = new FileInputStream(xlsxFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inStream);

		XSSFSheet sheet = null;
		if (StringUtils.isBlank(sheetName)) {
			sheet = workbook.getSheetAt(0); // Default to first sheet
		} else {
			sheet = workbook.getSheet(sheetName);
		}

		int totalRows = sheet.getLastRowNum(); // Remove header row
		int totalColumns = FileIO.getColumnCount(sheet);
		String[][] testData = new String[totalRows][totalColumns];

		int rowIndex = 0;
		int columnIndex = 0;
		DataFormatter dataFormatter = new DataFormatter();

		// Iterate each row
		Row row = null;
		Cell cell = null;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			// Skip first row
			if (0 == row.getRowNum())
				continue;

			// Iterate each cell in row
			Iterator<Cell> cellIterator = row.iterator();
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();

				// Format cell value to String
				testData[rowIndex][columnIndex] = dataFormatter.formatCellValue(cell).trim();

				columnIndex++;

				// Reset columnIndex after complete iteration of row
				if (totalColumns == columnIndex) {
					columnIndex = 0;
				}
			}

			rowIndex++;
		}

		workbook.close();
		inStream.close();

		return testData;
	}

	/**
	 * Returns the largest number of non-empty columns among all rows.
	 * 
	 * @param sheet the sheet from the xlsx file
	 * @return int - the number of columns
	 */
	private static int getColumnCount(XSSFSheet sheet) {
		int largestColumnNumber = 0;
		int currentColumnNumber = 0;

		Row row = null;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			currentColumnNumber = row.getLastCellNum();
			if (currentColumnNumber > largestColumnNumber) {
				largestColumnNumber = currentColumnNumber;
			}
		}

		return largestColumnNumber;
	}

}
