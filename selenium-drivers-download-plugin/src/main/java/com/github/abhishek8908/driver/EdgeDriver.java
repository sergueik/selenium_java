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

	/*
	 https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
	 XHTML:
	 cssSelector
	 section#downloads ul[class *= 'driver-downloads']
	 
	 <ul class="bare subsection__body driver-downloads">
	<li class="driver-download">
	  <p class="subtitle" aria-label="WebDriver for Windows Insiders and future Windows releases">Insiders and future releases</p>
	  <p class="driver-download__meta">Microsoft WebDriver is now a Windows Feature on Demand.</p>
	  <p class="driver-download__meta">To install run the following in an elevated command prompt:</p>
	  <p class="driver-download__meta">DISM.exe /Online /Add-Capability /CapabilityName:Microsoft.WebDriver~~~~0.0.1.0</p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/F/8/A/F8AF50AB-3C3A-4BC4-8773-DC27B32988DD/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 17134">Release 17134</a>
	  <p class="driver-download__meta">Version: 6.17134 | Edge version supported: 17.17134 | <a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/D/4/1/D417998A-58EE-4EFE-A7CC-39EF9E020768/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 16299">Release 16299</a>
	  <p class="driver-download__meta">Version: 5.16299 | Edge version supported: 16.16299 | <a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/3/4/2/342316D7-EBE0-4F10-ABA2-AE8E0CDF36DD/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 15063">Release 15063</a>
	  <p class="driver-download__meta">Version: 4.15063 | Edge version supported: 15.15063 | <a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/3/2/D/32D3E464-F2EF-490F-841B-05D53C848D15/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 14393">Release 14393</a>
	  <p class="driver-download__meta">Version: 3.14393 | Edge version supported: 14.14393 | <a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/C/0/7/C07EBF21-5305-4EC8-83B1-A6FCC8F93F45/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 10586">Release 10586</a>
	  <p class="driver-download__meta">Version: 2.10586 | Edge version supported: 13.10586 | 
	<a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	<li class="driver-download">
	  <a class="subtitle" href="https://download.microsoft.com/download/8/D/0/8D0D08CF-790D-4586-B726-C6469A9ED49C/MicrosoftWebDriver.exe" aria-label="WebDriver for release number 10240">
	Release 10240</a>
	  <p class="driver-download__meta">Version: 1.10240 | 
	Edge version supported: 12.10240 | 
	<a href="https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf">License terms</a></p>
	</li>
	</ul>
	 */

}
