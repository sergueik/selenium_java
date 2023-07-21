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

	@Override
	protected boolean matchesSafely(ODS item) {

		for (int i = 0; i < item.ods.getSheetCount(); i++) {
			// Sheet sheet = item.excel.getSheetAt(i);
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
						if (cellValue.toString().contains(substring))
							return true;
					}
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
