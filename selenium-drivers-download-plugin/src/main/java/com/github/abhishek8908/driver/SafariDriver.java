package com.github.abhishek8908.driver;

public class SafariDriver implements IDriver {

	private String ver;
	private String url;
	private String driverUrl;
	private String os;
	private String driverPath;

	private String ext;

	public SafariDriver(String ver, String os, String driverPath) {
		this.ver = ver;
		this.os = os;
		this.driverPath = driverPath;
	}

	public void isDriverAvailable() {

	}

	public ChromeDriver getDriver() {

		return null;
	}

	@Override
	public void setDriverInSystemProperty() {

	}

	public void setDriverPath() {

	}

}
