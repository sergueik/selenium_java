package example.matchers.ODS;

import org.apache.commons.lang3.StringUtils;

import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;

import org.hamcrest.Description;

import example.ODS;

public class ContainsText extends ODSMatcher {
	private final String substring;

	public ContainsText(String substring) {
		this.substring = reduceSpaces(substring);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean matchesSafely(ODS item) {

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
					if (item.safeOOCellValue(cell).toString().contains(substring))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("a ODS containing text ")
				.appendValue(reduceSpaces(substring));
	}
}
