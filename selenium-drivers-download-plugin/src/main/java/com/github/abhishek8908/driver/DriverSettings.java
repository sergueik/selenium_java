package com.github.abhishek8908.driver;

import java.net.URL;
import java.util.List;

public class DriverSettings {

	private String ver;
	private String url;
	private String os;
	private String driverDir;

	public String getVer() {
		return ver;
	}

	public void setVer(String data) {
		this.ver = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String data) {
		this.url = data;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String data) {
		this.os = data;
	}

	public String getDriverDir() {
		return driverDir;
	}

	public void setDriverDir(String data) {
		this.driverDir = data;
	}

}
