package contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

	@JsonProperty("default")
	private String default1;

	private String mobile;

	public String getDefault1() {
		return default1;
	}

	public void setDefault1(String data) {
		default1 = data;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String data) {
		mobile = data;
	}
}
