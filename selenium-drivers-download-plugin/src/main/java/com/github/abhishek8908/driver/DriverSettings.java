package com.github.abhishek8908.driver;

public class DriverSettings {

	private String version;
	private String os;
	private String driverDir;

	public String getDriverDir() {
		return driverDir;
	}

	public String getOs() {
		return os;
	}

	public String getVersion() {
		return version;
	}

	public void setDriverDir(String driverDir) {
		this.driverDir = driverDir;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
