package example.browser;

public enum Platform {

	WIN32("win32", true), WIN64("win64", true), LINUX64("linux64", false), MAC_X64("mac-x64", false),
	MAC_ARM64("mac-arm64", false);

	private final String identifier;
	private final boolean windows;

	Platform(String identifier, boolean windows) {
		this.identifier = identifier;
		this.windows = windows;
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean isWindows() {
		return windows;
	}

	public String getBinaryName(BrowserType browserType) {
		return windows ? "chrome.exe" : "chrome";
	}

	public static Platform current() {
		String os = System.getProperty("os.name", "").toLowerCase();
		String arch = System.getProperty("os.arch", "").toLowerCase();

		if (os.contains("win")) {
			return arch.contains("64") ? WIN64 : WIN32;
		}
		if (os.contains("mac") || os.contains("darwin")) {
			return (arch.contains("aarch64") || arch.contains("arm")) ? MAC_ARM64 : MAC_X64;
		}
		if (os.contains("linux") || os.contains("nux")) {
			return LINUX64;
		}

		throw new UnsupportedOperationException("Unsupported platform: os=" + System.getProperty("os.name") + ", arch="
				+ System.getProperty("os.arch"));
	}
}
