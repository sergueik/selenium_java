package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class EdgeDriver extends Logger implements IDriver {

	private final String driverName = "MicrosoftWebDriver";
	private String ver;
	private String url;
	private String os;
	private String driverDir;
	private String ext;

	public EdgeDriver(DriverSettings settings) {
		this.ver = settings.getVer();
		if (this.ver == null) {
			try {
				List<URL> driverUrls = DriverUtil.getDriversFromHTML(
						"https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/");
			} catch (IOException e) {
				throw new RuntimeException(e); // ConfigurationException ?
			}
		} else {
			// no need to keep ver. One may need the Windows 10 buildNumber rather
			System.setProperty("ver", this.ver);
		}
		getLog().info("Set System property: " + "ver=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("Set System property: " + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", setExt());
		getLog().info("Set System property: " + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		return DriverUtil.checkDriverVersionExists(driverName, ver, driverDir);
	}

	private String setExt() {
		this.ext = "exe";
		return ext;
	}

	public EdgeDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(driverName, driverDir, ver);
		} else {
			setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.edge.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("Set webdriver.edge.driver: "
				+ System.getProperty("webdriver.edge.driver"));
	}
}
