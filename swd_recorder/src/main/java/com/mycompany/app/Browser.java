package com.mycompany.app;

import static java.lang.String.format;


public final class Browser {
	public String name;
	public String platform;
	public String driver_path;
	public String driver_version;
	public String version;

	public String getName() {
		return name;
	}

	public void setName(String data) {
		this.name = data;
	}

	public String getDriverVersion() {
		return driver_version;
	}

	public void setDriverVersion(String data) {
		this.driver_version = data;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String data) {
		this.version = data;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String data) {
		this.platform = data;
	}

	public String getDriverPath() {
		return driver_path;
	}

	public void setDriverPath(String data) {
		this.driver_path = data;
	}

	@Override
	public String toString() {
		return String.format("Browser '%s' version '%s'%s on '%s'%s", getName(),
				getVersion(),
				(getDriverVersion() == null) ? ""
						: String.format(", driver version '%s'", getDriverVersion()),
				getPlatform(),
				(getDriverPath() == null) ? ""
						: String.format(", with path to driver set to '%s'",
								getDriverPath()));
	}
}