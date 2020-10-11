package example.parameters;

/**
 * Setting for the column displayed in the final excel.
 */
public class FieldSetting {
	public FieldSetting() {
	}

	public FieldSetting(String fieldName, String displayName) {
		this.fieldName = fieldName;
		this.displayName = displayName;
	}

	private String fieldName;
	private String displayName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String value) {
		fieldName = value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String value) {
		displayName = value;
	}
}
