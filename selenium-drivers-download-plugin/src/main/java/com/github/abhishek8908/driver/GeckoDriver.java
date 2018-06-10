package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class GeckoDriver extends Logger implements IDriver {

	private final String DRIVER_NAME = "geckodriver";
	private String version;
	private String os;
	private String driverDir;
	private String ext;

	public GeckoDriver(DriverSettings settings) {
		this.version = settings.getVersion();
		System.setProperty("ver", this.version);
		getLog()
				.info("****System property :" + "version=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("****System property :" + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", setExt());
		getLog().info("****System property :" + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		return DriverUtil.checkDriverVersionExists(DRIVER_NAME, version, driverDir);
	}

	private String setExt() {
		if (os.toLowerCase().contains("win")) {
			this.ext = "zip";
		} else if (os.toLowerCase().contains("linux")) {
			this.ext = "tar.gz";
		} else if (os.toLowerCase().contains("mac")) {
			this.ext = "tar.gz";
		}
		return ext;
	}

	public GeckoDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(DRIVER_NAME, driverDir, version);
		} else {
			setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.gecko.driver",
				driverDir + File.separator + DRIVER_NAME + "-" + version + "-" + os);
		getLog().info("*****Setting webdriver.gecko.driver : "
				+ System.getProperty("webdriver.gecko.driver"));
	}

}
