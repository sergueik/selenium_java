package contract;

public class ResponseContainer {
	private String apiVersion;
	private Data data;

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String value) {
		apiVersion = value;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data value) {
		data = value;
	}
}
