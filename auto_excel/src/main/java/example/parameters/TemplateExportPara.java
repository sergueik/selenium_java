package example.parameters;

import example.DataDirection;

/**
 * The parameter for exporting with template
 */
public class TemplateExportPara extends ExportPara implements TemplatePara {
	private String dataSourceName;
	private boolean isInserted;
	private DataDirection dataDirection = DataDirection.Down;
	private boolean copyCellStyle = true;

	public TemplateExportPara(String dataSourceName, Object dataSource) {
		this.dataSourceName = dataSourceName;
		super.setDataSource(dataSource);
	}

	@Override
	public String getDataSourceName() {
		return dataSourceName;
	}

	@Override
	public void setDataSourceName(String value) {
		dataSourceName = value;
	}

	public boolean isInserted() {
		return isInserted;
	}

	public void setInserted(boolean value) {
		isInserted = value;
	}

	public DataDirection getDataDirection() {
		return dataDirection;
	}

	public void setDataDirection(DataDirection value) {
		dataDirection = value;
	}

	public boolean isCopyCellStyle() {
		return copyCellStyle;
	}

	public void setCopyCellStyle(boolean value) {
		copyCellStyle = value;
	}
}