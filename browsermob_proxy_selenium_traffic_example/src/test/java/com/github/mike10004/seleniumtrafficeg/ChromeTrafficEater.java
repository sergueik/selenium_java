/*
 * (c) 2016 Novetta
 *
 * Created by mike
 */
package com.github.mike10004.seleniumtrafficeg;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

class ChromeTrafficEater extends TrafficEater {

	@Override
	protected WebDriver createWebDriver(BrowserMobProxy proxy) {
		ChromeDriverManager.getInstance().setup();
		org.openqa.selenium.Proxy seleniumProxy = ClientUtil
				.createSeleniumProxy(proxy);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
		WebDriver driver = new ChromeDriver(capabilities);
		return driver;
	}

}
