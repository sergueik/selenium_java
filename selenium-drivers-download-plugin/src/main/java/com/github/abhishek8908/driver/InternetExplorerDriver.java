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
