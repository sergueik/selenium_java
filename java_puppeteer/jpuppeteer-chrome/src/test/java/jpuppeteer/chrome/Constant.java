package jpuppeteer.chrome;

import java.nio.file.Paths;

public class Constant {

	protected static String osName = getOSName();
	// TODO: handle mac os
	// "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
	public static final String CHROMEDRIVER_EXECUTABLE_PATH = Paths
			.get(System.getProperty("user.home")).resolve("Downloads")
			.resolve(
					osName.equals("windows") ? "chromedriver.exe" : "chromium-browser")
			.toAbsolutePath().toString();

	public static final String CHROME_EXECUTABLE_PATH = osName.equals("windows")
			? "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
			: "/usr/bin/chromium";

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
