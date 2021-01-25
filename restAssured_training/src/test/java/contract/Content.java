
package contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	@JsonProperty("1")
	private String a1;

	@JsonProperty("5")
	private String a5;

	@JsonProperty("6")
	private String a6;

	public String geta1() {
		return a1;
	}

	public void set1(String data) {
		a1 = data;
	}

	public String geta5() {
		return a5;
	}

	public void set5(String data) {
		a5 = data;
	}

	public String geta6() {
		return a6;
	}

	public void seta6(String data) {
		a6 = data;
	}
}
