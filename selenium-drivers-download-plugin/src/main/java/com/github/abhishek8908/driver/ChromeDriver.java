package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class ChromeDriver extends Logger implements IDriver {

	private final String ext = "zip";
	private final String driverName = "chromedriver";
	private String ver;
	private String os;
	private String driverDir;

	public ChromeDriver(DriverSettings settings) {
		this.ver = settings.getVer();
		System.setProperty("ver", this.ver);
		getLog().info("Set System property: " + "ver=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("Set System property: " + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", this.ext);
		getLog().info("Set System property :" + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		DriverUtil.checkDriverDirExists(driverDir);
		return DriverUtil.checkDriverVersionExists(driverName, ver, driverDir);
	}

	public ChromeDriver getDriver() throws IOException, ConfigurationException {

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
		System.setProperty("webdriver.chrome.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("System Setting webdriver.chrome.driver : "
				+ System.getProperty("webdriver.chrome.driver"));
	}
/*
 https://chromedriver.storage.googleapis.com/
 XML:
 <?xml version="1.0"?>
<ListBucketResult xmlns="http://doc.s3.amazonaws.com/2006-03-01">
  <Name>chromedriver</Name>
  <Prefix/>
  <Marker/>
  <IsTruncated>false</IsTruncated>
  <Contents>
    <Key>2.0/chromedriver_linux32.zip</Key>
    <Generation>1380149859530000</Generation>
    <MetaGeneration>4</MetaGeneration>
    <LastModified>2013-09-25T22:57:39.349Z</LastModified>
    <ETag>"c0d96102715c4916b872f91f5bf9b12c"</ETag>
    <Size>7262134</Size>
  </Contents>
  <Contents>
    <Key>2.0/chromedriver_linux64.zip</Key>
    <Generation>1380149860664000</Generation>
    <MetaGeneration>4</MetaGeneration>
    <LastModified>2013-09-25T22:57:40.449Z</LastModified>
    <ETag>"858ebaf47e13dce7600191ed59974c09"</ETag>
    <Size>7433593</Size>
  </Contents>
</ListBucketResult>
 */
}
