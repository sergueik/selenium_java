package example.enums;
import static example.utils.PropertyUtils.Constants.*;

public enum DataSource {
	AUTOMATION_SOURCE(DATA_SOURCE_CONFIG);

	private String source;

	DataSource(final String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
}
