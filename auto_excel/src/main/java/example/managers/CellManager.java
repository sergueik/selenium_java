package example.managers;

import org.apache.poi.ss.usermodel.CellStyle;

public class CellManager extends BaseCellManager {
	private String colName;
	private CellStyle cellStyle;

	public String getColName() {
		return colName;
	}

	public void setColName(String value) {
		colName = value;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle value) {
		cellStyle = value;
	}
}