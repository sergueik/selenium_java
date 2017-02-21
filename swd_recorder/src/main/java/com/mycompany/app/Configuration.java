package com.mycompany.app;

import static java.lang.String.format;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

final class Configuration {
	public Date created;
	public Date updated;
	public String version;
	public String seleniumVersion;
	public Browser browser;
	public List<String> browsers;
	public HashMap<String, HashMap<String, String>> elements;
	public Map<String, String> plugins;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date data) {
		this.created = data;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date data) {
		this.updated = data;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String data) {
		this.version = data;
	}

	public String getSeleniumVersion() {
		return seleniumVersion;
	}

	public void setSeleniumVersion(String data) {
		this.seleniumVersion = data;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser data) {
		this.browser = data;
	}

	public List<String> getbrowsers() {
		return browsers;
	}

	public void setbrowsers(List<String> browsers) {
		this.browsers = browsers;
	}

	public Map<String, String> getPlugins() {
		return plugins;
	}

	public void setPlugins(Map<String, String> data) {
		this.plugins = data;
	}

	public HashMap<String, HashMap<String, String>> getelements() {
		return elements;
	}

	public void setelements(HashMap<String, HashMap<String, String>> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(format("Version: %s\n", version))
				.append(format("created: %s\n", created))
				.append(format("Selenium version: %s\n", seleniumVersion))
				.append(format("Supported browsers: %s\n", browsers))
				.append(format("Using: %s\n", browser))
				.append(format("Plugins: %s\n", plugins))
				.append(format("elements: %s\n", elements)).toString();
	}
}
