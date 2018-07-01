package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class InternetExplorerDriver extends Logger implements IDriver {

	private final String driverName = "IEDriverServer_Win32";
	// IEDriverServer_Win32_3.12.0.zip private String version;
	private String ver;
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
		if (os.toLowerCase().contains("win")) {
			this.ext = "zip";
		} else if (os.toLowerCase().contains("linux")) {
			this.ext = "tar.gz";
		} else if (os.toLowerCase().contains("mac")) {
			this.ext = "tar.gz";
		}
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
		System.setProperty("webdriver.gecko.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("Set webdriver.gecko.driver: "
				+ System.getProperty("webdriver.gecko.driver"));
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
	/*
	 https://selenium-release.storage.googleapis.com/
	 XML:
	 <?xml version="1.0"?>
	<ListBucketResult xmlns="http://doc.s3.amazonaws.com/2006-03-01">
	  <Name>selenium-release</Name>
	  <Prefix/>
	  <Marker/>
	  <IsTruncated>false</IsTruncated>
	  <Contents>
	    <Key>2.39/IEDriverServer_Win32_2.39.0.zip</Key>
	    <Generation>1389651460351000</Generation>
	    <MetaGeneration>4</MetaGeneration>
	    <LastModified>2014-01-13T22:17:40.327Z</LastModified>
	    <ETag>"bd4bc2b77a04999148e7fab974336e99"</ETag>
	    <Size>836478</Size>
	  </Contents>
	  <Contents>
	    <Key>2.39/IEDriverServer_x64_2.39.0.zip</Key>
	    <Generation>1389651273362000</Generation>
	    <MetaGeneration>2</MetaGeneration>
	    <LastModified>2014-01-13T22:14:33.323Z</LastModified>
	    <ETag>"7d19f3d7ffb9cb40fc26cc38885b9160"</ETag>
	    <Size>946479</Size>
	  </Contents>
	</ListBucketResult>

	 */

}
