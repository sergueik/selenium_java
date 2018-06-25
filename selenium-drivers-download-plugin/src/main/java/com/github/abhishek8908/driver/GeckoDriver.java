package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class GeckoDriver extends Logger implements IDriver {

	private final String driverName = "geckodriver";
	private String ver;
	private String os;
	private String driverDir;
	private String ext;

	public GeckoDriver(DriverSettings settings) {
		this.ver = settings.getVer();
		System.setProperty("ver", this.ver);
		getLog().info("Set System property: " + "ver=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("Set System property: " + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", setExt());
		getLog().info("Set System property: " + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		DriverUtil.checkDriverDirExists(driverDir);
		return DriverUtil.checkDriverVersionExists(driverName, ver, driverDir);
	}

	private String setExt() {
		if (os.toLowerCase().contains("win")) {
			this.ext = "zip";
		} else {
			this.ext = "tar.gz";
		}
		return ext;
	}

	public GeckoDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(driverName, driverDir, ver);
		} else {
			getLog().info(driverName + " already exists at location " + driverDir);
			// setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.gecko.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("*****Setting webdriver.gecko.driver : "
				+ System.getProperty("webdriver.gecko.driver"));
	}

}
