package example.parameters;

import java.util.List;

/**
 * The parameter for exporting directly
 */
public class DirectExportPara extends ExportPara {
	private String sheetName;

	private List<FieldSetting> fieldSettings;

	public DirectExportPara(Object dataSource) {
		super.setDataSource(dataSource);
	}

	public DirectExportPara(Object dataSource, String sheetName, List<FieldSetting> fieldSettings) {
		super.setDataSource(dataSource);
		this.sheetName = sheetName;
		this.fieldSettings = fieldSettings;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String value) {
		sheetName = value;
	}

	public List<FieldSetting> getFieldSettings() {
		return fieldSettings;
	}

	public void setFieldSettings(List<FieldSetting> value) {
		fieldSettings = value;
	}
}
