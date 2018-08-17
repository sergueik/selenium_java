package contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

	@JsonProperty("default")
	private String default1;

	private String mobile;

	public String getDefault1() {
		return this.default1;
	}

	public void setDefault1(String default1) {
		this.default1 = default1;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
