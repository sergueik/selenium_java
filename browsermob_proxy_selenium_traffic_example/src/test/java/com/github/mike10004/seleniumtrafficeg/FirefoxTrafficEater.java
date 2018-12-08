/*
 * (c) 2016 Novetta
 *
 * Created by mike
 */
package com.github.mike10004.seleniumtrafficeg;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

class FirefoxTrafficEater extends TrafficEater {

	@Override
	protected WebDriver createWebDriver(BrowserMobProxy proxy) {
		MarionetteDriverManager.getInstance().setup();
		org.openqa.selenium.Proxy seleniumProxy = ClientUtil
				.createSeleniumProxy(proxy);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
		FirefoxProfile profile = new CertificateSupplementingFirefoxProfile(
				Resources.asByteSource(getClass().getResource("/cert8.db")));
		// https://stackoverflow.com/questions/2887978/webdriver-and-proxy-server-for-firefox
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.http", "localhost");
		profile.setPreference("network.proxy.http_port", proxy.getPort());
		profile.setPreference("network.proxy.ssl", "localhost");
		profile.setPreference("network.proxy.ssl_port", proxy.getPort());
		WebDriver driver = new FirefoxDriver(new FirefoxBinary(), profile,
				capabilities);
		return driver;
	}

	private static class CertificateSupplementingFirefoxProfile
			extends SupplementingFirefoxProfile {

		public static final String CERTIFICATE_DB_FILENAME = "cert8.db";

		private final ByteSource certificateDbSource;

		private CertificateSupplementingFirefoxProfile(
				ByteSource certificateDbSource) {
			this.certificateDbSource = checkNotNull(certificateDbSource);
		}

		@Override
		protected void profileCreated(File profileDir) {
			File certificateDbFile = new File(profileDir, CERTIFICATE_DB_FILENAME);
			try {
				certificateDbSource.copyTo(Files.asByteSink(certificateDbFile));
			} catch (IOException e) {
				throw new IllegalStateException(
						"failed to copy certificate database to profile dir", e);
			}
		}
	}

	private static class SupplementingFirefoxProfile extends FirefoxProfile {

		protected void profileCreated(File profileDir) {
			// no op
		}

		@Override
		public File layoutOnDisk() {
			File profileDir = super.layoutOnDisk();
			profileCreated(profileDir);
			return profileDir;
		}
	}

}
