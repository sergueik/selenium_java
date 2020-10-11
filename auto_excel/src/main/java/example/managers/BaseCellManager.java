package example.managers;

public abstract class BaseCellManager {
	private String cellName;
	private int rowIndex;
	private int colIndex;

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String value) {
		cellName = value;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int value) {
		rowIndex = value;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int value) {
		colIndex = value;
	}
}
