package example;

import java.util.List;

public class QueryTimeserieResponse {
	private String target;
	private List<List<Float>> datapoints;

	public String getTarget() {
		return target;
	}

	public void setTarget(String data) {
		target = data;
	}

	// left column is a Metric as a float value,
	// right column unix epoch in milliseconds
	public List<List<Float>> getDatapoints() { // Double
		return datapoints;
	}

	public void setDatapoints(List<List<Float>> data) {
		datapoints = data;
	}

}
