package example.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetUtil {
	public static Cell setValue(Sheet sheet, int rowIndex, int colIndex, Object value) {
		Cell cell = getOrCreateCell(sheet, rowIndex, colIndex);
		CellUtil.setValue(cell, value);
		return cell;
	}

	public static Cell getOrCreateCell(Sheet sheet, int rowIndex, int colIndex) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		Cell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex);
		}
		return cell;
	}
}
