package example.matchers.ODS;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;

import example.ODS;

public class ContainsRow extends ODSMatcher {
	private final String[] cellTexts;

	public ContainsRow(String... cellTexts) {
		this.cellTexts = cellTexts;
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
				Queue<String> expectedTexts = new LinkedList<>(asList(cellTexts));
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					cell = sheet.getImmutableCellAt(columnIndex, rowIndex);
					if (StringUtils.isNotBlank(cell.getValue().toString())) {
						Object cellValue = item.safeOOCellValue(cell);
						String expectedText = expectedTexts.peek();
						if (cellValue.toString().contains(expectedText)) {
							expectedTexts.remove();
						}
					}
				}
				if (expectedTexts.isEmpty())
					return true;
			}

		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("a ODS containing row ")
				.appendValue(Arrays.toString(cellTexts));
	}
}
