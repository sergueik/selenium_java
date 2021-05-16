package example.driver;

import example.driver.logger.Logger;
import example.util.DriverUtil;

import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GeckoDriver extends Logger implements IDriver {

	private final String driverName = "geckodriver";
	private String ver;
	private String os;
	private String driverDir;
	private String ext;

	public GeckoDriver(DriverSettings settings) {
		this.ver = settings.getVer();
		if (this.ver == null) {
			List<URL> driverUrls = DriverUtil.getDriversFromJSON(
					"https://api.github.com/repos/mozilla/geckodriver/releases");
			this.ver = "0.21.0";
		}
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
		this.ext = (os.toLowerCase().contains("win")) ? "zip" : "tar.gz";
		return ext;
	}

	public GeckoDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(driverName, driverDir, ver);
		} else {
			getLog().info(
					"Driver " + driverName + " already exists at location " + driverDir);
			// setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.gecko.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("Setting webdriver.gecko.driver : "
				+ System.getProperty("webdriver.gecko.driver"));
	}
}
