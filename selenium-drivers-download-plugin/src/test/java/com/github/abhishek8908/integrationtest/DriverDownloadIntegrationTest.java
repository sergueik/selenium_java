package com.github.abhishek8908.integrationtest;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.File;

import com.github.abhishek8908.util.DriverUtil;

public class DriverDownloadIntegrationTest {

	private static String osName = DriverUtil.getOSName();
	private static String tmpDir = (osName.equals("windows")) ? "c:\\temp"
			: "/tmp";
	private static String version;
	private static String os;
	private static String extension;

	// TODO: establish order
	@Test(enabled = false)
	public void downloadChomeDriverTest()
			throws IOException, ConfigurationException {
		version = "2.39";
		os = "win32";
		extension = "zip";
		System.setProperty("ver", version);
		System.setProperty("os", os);
		System.setProperty("ext", extension);
		DriverUtil.download("chromedriver", tmpDir, version);
		assertTrue((new File(tmpDir + File.separator
				+ String.format("chromedriver-%s-%s.exe", version, os))).exists());
	}

	@Test(enabled = false)
	public void downloadEdgeeDriverTest()
			throws IOException, ConfigurationException {
		version = "1"; // not used with edge ?
		os = "win32";
		extension = "zip";
		System.setProperty("ver", version);
		System.setProperty("os", os);
		System.setProperty("ext", extension);
		DriverUtil.download("edgedriver", tmpDir, version);
		assertTrue((new File(tmpDir + File.separator
				+ String.format("MicrosoftWebDriver-%s-%s.exe", version, os)))
						.exists());
	}

	@Test(enabled = true)
	public void downloadGeckoDriverTest()
			throws IOException, ConfigurationException {
		version = "0.21.0"; // not used with edge ?
		os = "linux64";
		extension = "tar.gz";
		System.setProperty("ver", "0.21.0");
		System.setProperty("os", "linux64");
		System.setProperty("ext", "tar.gz");
		DriverUtil.download("geckodriver", tmpDir, version);
		assertTrue((new File(tmpDir + File.separator
				+ String.format("geckodriver-%s-%s", version, os))).exists());
	}

}
