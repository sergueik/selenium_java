package com.driver.locator;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.driver.locator.utility.ResourceHelper;
import com.driver.locator.writer.FileType;

public class PropertyFileReader {

	private Properties aProperty = null;
	private String bFileName = "";

	private String getFilePath() {
		return this.getClass().getResource("/").getPath() + "configfile/"
				+ this.bFileName;
	}

	public PropertyFileReader() {
		this.bFileName = "config.properties";

		try {
			aProperty = new Properties();
			aProperty.load(ResourceHelper
					.getResourcePathInputStream("configfile/" + this.bFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertyFileReader(String cConfigPropFileName) {
		this.bFileName = cConfigPropFileName;

		try {
			aProperty = new Properties();
			aProperty.load(new FileInputStream(getFilePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getWebsiteNames() {
		Enumeration<Object> keys = aProperty.keys();
		Map<String, String> urlMap = new LinkedHashMap<>();
		while (keys.hasMoreElements()) {
			Object object = (Object) keys.nextElement();
			if (object.toString().contains("Website"))
				urlMap.put(object.toString(), aProperty.getProperty(object.toString()));
		}
		return urlMap;
	}

	public FileType getFileType() {
		return FileType.valueOf(aProperty.getProperty("Type"));
	}
}
