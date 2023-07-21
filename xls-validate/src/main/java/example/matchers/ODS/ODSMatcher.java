package example.matchers.ODS;

import org.apache.commons.lang3.StringUtils;
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

	@Override
	protected void describeMismatchSafely(ODS item,
			Description mismatchDescription) {

		for (int i = 0; i < item.ods.getSheetCount(); i++) {
			Sheet sheet = item.ods.getSheet(i);
			int columnCount = sheet.getColumnCount();
			int rowCount = sheet.getRowCount();
			@SuppressWarnings("rawtypes")
			Cell cell = null;
			for (int rowIndex = 1; rowIndex < rowCount && StringUtils.isNotBlank(sheet
					.getImmutableCellAt(0, rowIndex).getValue().toString()); rowIndex++) {
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					cell = sheet.getImmutableCellAt(columnIndex, rowIndex);
					if (StringUtils.isNotBlank(cell.getValue().toString())) {
						Object cellValue = item.safeOOCellValue(cell);
						mismatchDescription.appendText(cellValue.toString())
								.appendText("\t");
					}
				}
			}
		}
	}
}
