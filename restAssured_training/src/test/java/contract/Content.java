
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
		return this.a1;
	}

	public void set1(String a1) {
		this.a1 = a1;
	}

	public String geta5() {
		return this.a5;
	}

	public void set5(String a5) {
		this.a5 = a5;
	}

	public String geta6() {
		return this.a6;
	}

	public void seta6(String a6) {
		this.a6 = a6;
	}
}
