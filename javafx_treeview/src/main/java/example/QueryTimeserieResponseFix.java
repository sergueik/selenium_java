package example;

import java.util.List;

public class QueryTimeserieResponseFix {
	private String target;
	private List<List<Object>> datapoints;
	// double metric, long timestamp

	public String getTarget() {
		return target;
	}

	public void setTarget(String data) {
		target = data;
	}

	// left column is a Metric as a float value,
	// right column unix epoch in milliseconds
	public List<List<Object>> getDatapoints() { // Double
		return datapoints;
	}

	public void setDatapoints(List<List<Object>> data) {
		datapoints = data;
	}

}
