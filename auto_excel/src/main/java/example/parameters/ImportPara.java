package example.parameters;

import example.DataDirection;

public class ImportPara implements TemplatePara {
	private String dataSourceName;
	private DataDirection dataDirection;

	public ImportPara(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		this.dataDirection = DataDirection.None;
	}

	public ImportPara(String dataSourceName, DataDirection dataDirection) {
		this.dataSourceName = dataSourceName;
		this.dataDirection = dataDirection;
	}

	@Override
	public String getDataSourceName() {
		return dataSourceName;
	}

	@Override
	public void setDataSourceName(String value) {
		dataSourceName = value;
	}

	public DataDirection getDataDirection() {
		return dataDirection;
	}

	public void setDataDirection(DataDirection value) {
		dataDirection = value;
	}
}
