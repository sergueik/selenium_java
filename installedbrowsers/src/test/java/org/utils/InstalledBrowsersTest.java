package org.utils;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

import org.utils.InstalledBrowsers;

public class InstalledBrowsersTest {
	private static boolean debug = false;

	private static Map<String, String> browserNames = new HashMap<>();
	static {
		browserNames.put("chrome.exe", "Google Chrome");
		browserNames.put("iexplore.exe", "Internet Explorer");
		browserNames.put("firefox.exe", "Mozilla Firefox");
	}

	@Test
	public void test() {
		InstalledBrowsers.setDebug(debug);
		List<String> browsers = InstalledBrowsers.getInstalledBrowsers();
		assertTrue(browsers.size() > 0);
		out.println("Your browsers: " + browsers);

		for (String browserName : browserNames.keySet()) {
			if (browsers.contains(browserName)) {
				assertTrue(InstalledBrowsers.isInstalled(browserName));
				assertTrue(InstalledBrowsers.getMajorVersion(browserName) > 0);
				out.println(
						String.format("%s version: %s", browserNames.get(browserName),
								InstalledBrowsers.getVersion(browserName)));
			} else {
				assertFalse(InstalledBrowsers.isInstalled(browserName));
				assertTrue(InstalledBrowsers.getMajorVersion(browserName) == 0);
			}
		}
	}
}