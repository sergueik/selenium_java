package example;

import java.util.List;

public class Configuration {
	private String version;
	// NOTE: has to be named "Services" , not "Service"
	private Services services;
	private Settings settings;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Configuration(String version, Services services, Settings settings) {
		this.version = version;
		this.services = services;
		this.settings = settings;
	}

	public Configuration() {
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
}
