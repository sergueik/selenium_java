package com.github.abhishek8908.plugin;

public class Driver {

	private String name;
	private String version;
	private String os;

	public String getName() {
		return name;
	}

	public String getOs() {
		return os;
	}

	public String getVersion() {
		return version;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Driver{" + "name='" + name + '\'' + ", version='" + version + '\''
				+ ", os='" + os + '\'' + '}';
	}
}
