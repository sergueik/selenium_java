package io.github.ashwithpoojary98.browser;

/**
 * Represents an OS/architecture combination supported by the
 * <a href="https://googlechromelabs.github.io/chrome-for-testing/">Chrome for Testing</a>
 * download API.
 *
 * <p>Platform identifiers exactly match the keys used in the Chrome for Testing JSON feed
 * (e.g. {@code "linux64"}, {@code "win64"}, {@code "mac-x64"}).
 *
 * <p>Call {@link #current()} to detect the platform at runtime.
 */
public enum Platform {

    WIN32("win32", true),
    WIN64("win64", true),
    LINUX64("linux64", false),
    MAC_X64("mac-x64", false),
    MAC_ARM64("mac-arm64", false);

    private final String identifier;
    private final boolean windows;

    Platform(String identifier, boolean windows) {
        this.identifier = identifier;
        this.windows = windows;
    }

    /**
     * Returns the platform identifier as used in the Chrome for Testing API and
     * as the cache sub-directory name.
     *
     * @return platform identifier string (e.g. {@code "linux64"})
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns {@code true} if this platform is Windows.
     *
     * @return {@code true} for {@link #WIN32} and {@link #WIN64}
     */
    public boolean isWindows() {
        return windows;
    }

    /**
     * Returns the executable filename for the given browser on this platform.
     *
     * <p>On Windows this is {@code chrome.exe}; on all other platforms it is {@code chrome}.
     *
     * @param browserType the browser type (currently unused — Chrome for Testing always
     *                    uses the same binary name)
     * @return the binary filename
     */
    public String getBinaryName(BrowserType browserType) {
        return windows ? "chrome.exe" : "chrome";
    }

    /**
     * Detects the current runtime platform from system properties.
     *
     * @return the detected {@link Platform}
     * @throws UnsupportedOperationException if the OS/architecture combination is not supported
     */
    public static Platform current() {
        String os   = System.getProperty("os.name",  "").toLowerCase();
        String arch = System.getProperty("os.arch",  "").toLowerCase();

        if (os.contains("win")) {
            return arch.contains("64") ? WIN64 : WIN32;
        }
        if (os.contains("mac") || os.contains("darwin")) {
            return (arch.contains("aarch64") || arch.contains("arm")) ? MAC_ARM64 : MAC_X64;
        }
        if (os.contains("linux") || os.contains("nux")) {
            return LINUX64;
        }

        throw new UnsupportedOperationException(
                "Unsupported platform: os=" + System.getProperty("os.name")
                + ", arch=" + System.getProperty("os.arch"));
    }
}
