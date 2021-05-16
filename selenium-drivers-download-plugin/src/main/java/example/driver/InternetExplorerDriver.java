package example.driver;

import example.driver.logger.Logger;
import example.util.DriverUtil;

import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class InternetExplorerDriver extends Logger implements IDriver {

	private final String driverName = "IEDriverServer_Win32";
	// IEDriverServer_Win32_3.12.0.zip private String version;
	private String ver;
	private String url;
	private String os;
	private String driverDir;
	private String ext;

	public InternetExplorerDriver(DriverSettings settings) {
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
		return DriverUtil.checkDriverVersionExists(driverName, ver, driverDir);
	}

	private String setExt() {
		this.ext = "zip";
		return ext;
	}

	public InternetExplorerDriver getDriver()
			throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(driverName, driverDir, ver);
		} else {
			setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.ie.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("Set webdriver.ie.driver: "
				+ System.getProperty("webdriver.ie.driver"));
	}

}
