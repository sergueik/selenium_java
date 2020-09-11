package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

public class ReadWordTable {

	private List<String> omitCellsList = new ArrayList<>();

	public String generateOmitCellStr(int row, int col) {
		return row + ":" + col;
	}

	public int getColspan(CTTcPr tcPr) {
		CTDecimalNumber gridSpan = null;
		if ((gridSpan = tcPr.getGridSpan()) != null) { // 合并的起始列
			BigInteger num = gridSpan.getVal();
			return num.intValue();
		} else {
			return 1;
		}
	}

	public int getRowspan(XWPFTable table, int row, int col) {

		XWPFTableCell cell = table.getRow(row).getCell(col);
		if (!isContinueRow(cell) && !isRestartRow(cell)) {
			return 1;
		}
		int cellWidth = getCellWidth(table, row, col);
		int leftWidth = getLeftWidth(table, row, col);

		List<Boolean> list = new ArrayList<>();
		getRowspan(table, row, cellWidth, leftWidth, list);

		return list.size() + 1;
	}

	private void getRowspan(XWPFTable table, int row, int cellWidth, int leftWidth, List<Boolean> list) {
		if (row + 1 >= table.getNumberOfRows()) {
			return;
		}
		row = row + 1;
		int colsNum = table.getRow(row).getTableCells().size();
		for (int i = 0; i < colsNum; i++) {
			XWPFTableCell testTable = table.getRow(row).getCell(i);
			if (isContinueRow(testTable)) {
				if (getCellWidth(table, row, i) == cellWidth && getLeftWidth(table, row, i) == leftWidth) {
					list.add(true);
					addOmitCell(row, i);
					getRowspan(table, row, cellWidth, leftWidth, list);
					break;
				}
			}
		}
	}

	public boolean isRestartRow(XWPFTableCell tableCell) {
		CTTcPr tcPr = tableCell.getCTTc().getTcPr();
		if (tcPr.getVMerge() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal().toString().equalsIgnoreCase("restart")) {
			return true;
		}
		return false;
	}

	public boolean isContinueRow(XWPFTableCell tableCell) {
		CTTcPr tcPr = tableCell.getCTTc().getTcPr();
		if (tcPr.getVMerge() == null) {
			return false;
		}
		if (tcPr.getVMerge().getVal() == null) {
			return true;
		}
		return false;
	}

	public int getLeftWidth(XWPFTable table, int row, int col) {
		int leftWidth = 0;
		for (int i = 0; i < col; i++) {
			leftWidth += getCellWidth(table, row, i);
		}
		return leftWidth;
	}

	public int getCellWidth(XWPFTable table, int row, int col) {
		BigInteger width = table.getRow(row).getCell(col).getCTTc().getTcPr().getTcW().getW();
		return width.intValue();
	}

	public void addOmitCell(int row, int col) {
		String omitCellStr = generateOmitCellStr(row, col);
		omitCellsList.add(omitCellStr);
	}

	public boolean isOmitCell(int row, int col) {
		String cellStr = generateOmitCellStr(row, col);
		return omitCellsList.contains(cellStr);
	}

	public String readTable(XWPFTable table) throws IOException {
		// 表格行数
		int tableRowsSize = table.getRows().size();
		StringBuilder tableToHtmlStr = new StringBuilder("<table>");

		for (int i = 0; i < tableRowsSize; i++) {
			tableToHtmlStr.append("<tr>");
			int tableCellsSize = table.getRow(i).getTableCells().size();
			for (int j = 0; j < tableCellsSize; j++) {
				if (isOmitCell(i, j)) {
					continue;
				}
				XWPFTableCell tableCell = table.getRow(i).getCell(j);
				// 获取单元格的属性
				CTTcPr tcPr = tableCell.getCTTc().getTcPr();
				int colspan = getColspan(tcPr);
				if (colspan > 1) { // 合并的列
					tableToHtmlStr.append("<td colspan='" + colspan + "'");
				} else { // 正常列
					tableToHtmlStr.append("<td");
				}

				int rowspan = getRowspan(table, i, j);
				if (rowspan > 1) { // 合并的行
					tableToHtmlStr.append(" rowspan='" + rowspan + "'>");
				} else {
					tableToHtmlStr.append(">");
				}
				String text = tableCell.getText();
				tableToHtmlStr.append(text + "</td>");

			}
			tableToHtmlStr.append("</tr>");
		}
		tableToHtmlStr.append("</table>");

		clearTableInfo();

		return tableToHtmlStr.toString();
	}

	public void clearTableInfo() {
		// System.out.println(omitCellsList);
		omitCellsList.clear();
	}

	public static void main(String[] args) {
		ReadWordTable readWordTable = new ReadWordTable();

		try (FileInputStream fileInputStream = new FileInputStream("E:\\下载\\table1.docx");
				XWPFDocument document = new XWPFDocument(fileInputStream);) {
			List<XWPFTable> tables = document.getTables();
			for (XWPFTable table : tables) {
				System.out.println(readWordTable.readTable(table));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
