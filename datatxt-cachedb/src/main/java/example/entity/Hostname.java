package example.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Hostname implements Serializable {

	private static final long serialVersionUID = 6973576143316146252L;
	private String hostname;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String value) {
		hostname = value;
	}

	@Override
	public String toString() {
		return "Host{" + "hostname=" + this.hostname + '}';
	}
}
