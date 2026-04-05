package example.browser;

// import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Data
public class BrowserOptions {

	private final String binaryPath;
	private final BrowserType browserType;
	private final String browserVersion;
	private final boolean autoDownload;
	private final boolean headless;
	private final List<String> arguments;
	private final Map<String, String> preferences;
	private final int debuggingPort;
	private final String userDataDir;
	private final int windowWidth;
	private final int windowHeight;

	private BrowserOptions(Builder builder) {
		this.binaryPath = builder.binaryPath;
		this.browserType = builder.browserType;
		this.browserVersion = builder.browserVersion;
		this.autoDownload = builder.autoDownload;
		this.headless = builder.headless;
		this.arguments = new ArrayList<>(builder.arguments);
		this.preferences = new HashMap<>(builder.preferences);
		this.debuggingPort = builder.debuggingPort;
		this.userDataDir = builder.userDataDir;
		this.windowWidth = builder.windowWidth;
		this.windowHeight = builder.windowHeight;
	}

	public String getBinaryPath() {
		return binaryPath;
	}

	public BrowserType getBrowserType() {
		return browserType;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public boolean isAutoDownload() {
		return autoDownload;
	}

	public boolean isHeadless() {
		return headless;
	}

	public List<String> getArguments() {
		return new ArrayList<>(arguments);
	}

	public Map<String, String> getPreferences() {
		return new HashMap<>(preferences);
	}

	public int getDebuggingPort() {
		return debuggingPort;
	}

	public String getUserDataDir() {
		return userDataDir;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	/**
	 * Creates a new Builder instance.
	 *
	 * @return A new Builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String binaryPath;
		private BrowserType browserType = BrowserType.CHROME;
		private String browserVersion = null; // null = latest stable
		private boolean autoDownload = true;
		private boolean headless = false;
		private List<String> arguments = new ArrayList<>();
		private Map<String, String> preferences = new HashMap<>();
		private int debuggingPort = 0; // 0 means auto-select
		private String userDataDir;
		private int windowWidth = 1280;
		private int windowHeight = 720;

		public Builder browserType(BrowserType browserType) {
			this.browserType = browserType;
			return this;
		}

		public Builder browserVersion(String version) {
			this.browserVersion = version;
			return this;
		}

		public Builder autoDownload(boolean autoDownload) {
			this.autoDownload = autoDownload;
			return this;
		}

		public Builder binaryPath(String binaryPath) {
			this.binaryPath = binaryPath;
			return this;
		}

		public Builder headless(boolean headless) {
			this.headless = headless;
			return this;
		}

		public Builder addArgument(String argument) {
			this.arguments.add(argument);
			return this;
		}

		public Builder addArguments(List<String> arguments) {
			this.arguments.addAll(arguments);
			return this;
		}

		public Builder setPreference(String key, String value) {
			this.preferences.put(key, value);
			return this;
		}

		public Builder debuggingPort(int port) {
			this.debuggingPort = port;
			return this;
		}

		public Builder userDataDir(String userDataDir) {
			this.userDataDir = userDataDir;
			return this;
		}

		public Builder windowSize(int width, int height) {
			this.windowWidth = width;
			this.windowHeight = height;
			return this;
		}

		public Builder disableGpu() {
			this.arguments.add("--disable-gpu");
			return this;
		}

		public Builder noSandbox() {
			this.arguments.add("--no-sandbox");
			return this;
		}

		public Builder disableDevShmUsage() {
			this.arguments.add("--disable-dev-shm-usage");
			return this;
		}

		public Builder startMaximized() {
			this.arguments.add("--start-maximized");
			return this;
		}

		public Builder disableExtensions() {
			this.arguments.add("--disable-extensions");
			return this;
		}

		public Builder userAgent(String userAgent) {
			this.arguments.add("--user-agent=" + userAgent);
			return this;
		}

		public Builder incognito() {
			this.arguments.add("--incognito");
			return this;
		}

		public BrowserOptions build() {
			return new BrowserOptions(this);
		}
	}
}
