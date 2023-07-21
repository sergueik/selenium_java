package example.matchers.ODS;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.hamcrest.Description;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;

import example.ODS;

public class ContainsRow extends ODSMatcher {
	private final String[] cellTexts;

	public ContainsRow(String... cellTexts) {
		this.cellTexts = cellTexts;
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
				Queue<String> expectedTexts = new LinkedList<>(asList(cellTexts));
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

					Cell cell = sheet.getImmutableCellAt(columnIndex, rowIndex);
					if (cell.isEmpty())
						break;
					String expectedText = expectedTexts.peek();
					if (item.safeOOCellValue(cell).toString().contains(expectedText))
						expectedTexts.remove();
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
