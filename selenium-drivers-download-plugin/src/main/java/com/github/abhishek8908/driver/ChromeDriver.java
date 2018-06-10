package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class ChromeDriver extends Logger implements IDriver {

	private final String EXT = "zip";
	private final String DRIVER_NAME = "chromedriver";
	private String version;
	private String os;
	private String driverDir;

	public ChromeDriver(DriverSettings settings) {
		this.version = settings.getVersion();
		System.setProperty("ver", this.version);
		getLog()
				.info("****System property :" + "version=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("****System property :" + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", this.EXT);
		getLog().info("****System property :" + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		return DriverUtil.checkDriverVersionExists(DRIVER_NAME, version, driverDir);

	}

	public ChromeDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(DRIVER_NAME, driverDir, version);
		} else {
			getLog().info("***********" + DRIVER_NAME + " already exists at location "
					+ driverDir);
			setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.chrome.driver",
				driverDir + File.separator + DRIVER_NAME + "-" + version + "-" + os);
		getLog().info("*****Setting webdriver.chrome.driver : "
				+ System.getProperty("webdriver.chrome.driver"));
	}

}
