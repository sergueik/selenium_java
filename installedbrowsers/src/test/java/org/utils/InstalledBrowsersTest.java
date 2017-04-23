package org.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.utils.InstalledBrowsers;

public class InstalledBrowsersTest {

	private static Map<String, String> browserNames = new HashMap<>();
	static {
		browserNames.put("chrome.exe", "Google Chrome");
		browserNames.put("iexplore.exe", "Internet Explorer");
		browserNames.put("firefox.exe", "Mozilla Firefox");
	}

	@Test
	public void test() {
		List<String> browsers = InstalledBrowsers.getInstalledBrowsers();
		assertTrue(browsers.size() > 0);
		System.out.println("Your browsers: " + browsers);

		for (String browserName : browserNames.keySet()) {
			if (browsers.contains(browserName)) {
				assertTrue(InstalledBrowsers.isInstalled(browserName));
				assertTrue(InstalledBrowsers.getMajorVersion(browserName) > 0);
				System.out.println(
						String.format("%s version: %s", browserNames.get(browserName),
								InstalledBrowsers.getVersion(browserName)));
			} else {
				assertFalse(InstalledBrowsers.isInstalled(browserName));
				assertTrue(InstalledBrowsers.getMajorVersion(browserName) == 0);
			}
		}
	}
}