package example.matchers.ODS;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;

import example.ODS;

abstract class ODSMatcher extends TypeSafeMatcher<ODS>
		implements SelfDescribing {

	protected String reduceSpaces(String text) {
		return text.replaceAll("[\\s\\n\\r\u00a0]+", " ").trim();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void describeMismatchSafely(ODS item,
			Description mismatchDescription) {

		for (int sheetIndex = 0; sheetIndex < item.ods
				.getSheetCount(); sheetIndex++) {

			Sheet sheet = item.ods.getSheet(sheetIndex);
			int columnCount = sheet.getColumnCount();

			for (int rowIndex = 0; rowIndex < sheet.getRowCount(); rowIndex++) {
				if (sheet.getImmutableCellAt(0, rowIndex).isEmpty())
					break;
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

					Cell cell = sheet.getImmutableCellAt(columnIndex, rowIndex);
					if (cell.isEmpty())
						break;
					mismatchDescription.appendText(item.safeOOCellValue(cell).toString())
							.appendText("\t");
				}
			}
		}
	}
}
