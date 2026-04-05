package io.github.ashwithpoojary98.browser;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration options for launching Chrome/Chromium browser.
 *
 * <p>Use the builder to construct instances:
 * <pre>{@code
 * BrowserOptions options = BrowserOptions.builder()
 *     .browserType(BrowserType.CHROME)   // which browser to use
 *     .autoDownload(true)                 // download if not installed
 *     .headless(true)
 *     .windowSize(1920, 1080)
 *     .build();
 * }</pre>
 *
 * <p>When {@code autoDownload} is {@code true} and no local installation is found,
 * {@link BrowserManager} downloads the binary from the Chrome for Testing API and
 * caches it under {@code ~/.cache/nihonium/} (Selenium Manager cache convention).
 */
@Data
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
        this.binaryPath     = builder.binaryPath;
        this.browserType    = builder.browserType;
        this.browserVersion = builder.browserVersion;
        this.autoDownload   = builder.autoDownload;
        this.headless       = builder.headless;
        this.arguments      = new ArrayList<>(builder.arguments);
        this.preferences    = new HashMap<>(builder.preferences);
        this.debuggingPort  = builder.debuggingPort;
        this.userDataDir    = builder.userDataDir;
        this.windowWidth    = builder.windowWidth;
        this.windowHeight   = builder.windowHeight;
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

    /**
     * Builder for BrowserOptions.
     */
    public static class Builder {
        private String binaryPath;
        private BrowserType browserType    = BrowserType.CHROME;
        private String browserVersion      = null; // null = latest stable
        private boolean autoDownload       = true;
        private boolean headless           = false;
        private List<String> arguments     = new ArrayList<>();
        private Map<String, String> preferences = new HashMap<>();
        private int debuggingPort          = 0; // 0 means auto-select
        private String userDataDir;
        private int windowWidth            = 1280;
        private int windowHeight           = 720;

        /**
         * Sets the browser type to launch (default: {@link BrowserType#CHROME}).
         *
         * <p>When {@link #autoDownload} is {@code true}, the specified browser type
         * is downloaded via {@link BrowserManager} if not found locally.
         *
         * @param browserType the browser to use
         * @return this builder
         */
        public Builder browserType(BrowserType browserType) {
            this.browserType = browserType;
            return this;
        }

        /**
         * Pins the browser to a specific version (e.g. {@code "131.0.6778.108"}).
         *
         * <p>If not set, the latest stable release is used when auto-downloading.
         * Has no effect when {@link #autoDownload} is {@code false} and a binary
         * is already supplied via {@link #binaryPath}.
         *
         * @param version dotted version string
         * @return this builder
         */
        public Builder browserVersion(String version) {
            this.browserVersion = version;
            return this;
        }

        /**
         * Controls whether the browser is automatically downloaded when no local
         * installation is found (default: {@code true}).
         *
         * <p>When {@code true}, {@link BrowserManager} is invoked as a fallback after
         * the system-installation search fails. The binary is cached at
         * {@code ~/.cache/nihonium/} so subsequent launches are instant.
         *
         * @param autoDownload {@code true} to enable auto-download
         * @return this builder
         */
        public Builder autoDownload(boolean autoDownload) {
            this.autoDownload = autoDownload;
            return this;
        }

        /**
         * Sets the path to the Chrome/Chromium binary.
         * If not specified, the browser will be auto-detected or auto-downloaded.
         *
         * @param binaryPath Path to the browser binary
         * @return This builder
         */
        public Builder binaryPath(String binaryPath) {
            this.binaryPath = binaryPath;
            return this;
        }

        /**
         * Sets whether to run in headless mode.
         *
         * @param headless true for headless mode
         * @return This builder
         */
        public Builder headless(boolean headless) {
            this.headless = headless;
            return this;
        }

        /**
         * Adds a command-line argument for the browser.
         *
         * @param argument The argument to add
         * @return This builder
         */
        public Builder addArgument(String argument) {
            this.arguments.add(argument);
            return this;
        }

        /**
         * Adds multiple command-line arguments for the browser.
         *
         * @param arguments The arguments to add
         * @return This builder
         */
        public Builder addArguments(List<String> arguments) {
            this.arguments.addAll(arguments);
            return this;
        }

        /**
         * Sets a browser preference.
         *
         * @param key Preference key
         * @param value Preference value
         * @return This builder
         */
        public Builder setPreference(String key, String value) {
            this.preferences.put(key, value);
            return this;
        }

        /**
         * Sets the remote debugging port.
         * If 0 (default), a random available port will be used.
         *
         * @param port The port number
         * @return This builder
         */
        public Builder debuggingPort(int port) {
            this.debuggingPort = port;
            return this;
        }

        /**
         * Sets the user data directory for the browser profile.
         * If not specified, a temporary directory will be used.
         *
         * @param userDataDir Path to user data directory
         * @return This builder
         */
        public Builder userDataDir(String userDataDir) {
            this.userDataDir = userDataDir;
            return this;
        }

        /**
         * Sets the window size.
         *
         * @param width Window width in pixels
         * @param height Window height in pixels
         * @return This builder
         */
        public Builder windowSize(int width, int height) {
            this.windowWidth = width;
            this.windowHeight = height;
            return this;
        }

        /**
         * Disables the GPU.
         * Useful for headless mode and avoiding GPU-related issues.
         *
         * @return This builder
         */
        public Builder disableGpu() {
            this.arguments.add("--disable-gpu");
            return this;
        }

        /**
         * Disables browser sandbox.
         * WARNING: Only use this in trusted environments (e.g., Docker containers).
         *
         * @return This builder
         */
        public Builder noSandbox() {
            this.arguments.add("--no-sandbox");
            return this;
        }

        /**
         * Disables the shared memory usage.
         * Useful in Docker/containerized environments.
         *
         * @return This builder
         */
        public Builder disableDevShmUsage() {
            this.arguments.add("--disable-dev-shm-usage");
            return this;
        }

        /**
         * Starts the browser in maximized mode.
         *
         * @return This builder
         */
        public Builder startMaximized() {
            this.arguments.add("--start-maximized");
            return this;
        }

        /**
         * Disables browser extensions.
         *
         * @return This builder
         */
        public Builder disableExtensions() {
            this.arguments.add("--disable-extensions");
            return this;
        }

        /**
         * Sets a custom user agent.
         *
         * @param userAgent The user agent string
         * @return This builder
         */
        public Builder userAgent(String userAgent) {
            this.arguments.add("--user-agent=" + userAgent);
            return this;
        }

        /**
         * Enables incognito mode.
         *
         * @return This builder
         */
        public Builder incognito() {
            this.arguments.add("--incognito");
            return this;
        }

        /**
         * Builds the BrowserOptions instance.
         *
         * @return A new BrowserOptions instance
         */
        public BrowserOptions build() {
            return new BrowserOptions(this);
        }
    }
}
