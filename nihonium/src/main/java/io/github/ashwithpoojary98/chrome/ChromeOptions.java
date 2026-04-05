package io.github.ashwithpoojary98.chrome;

import io.github.ashwithpoojary98.browser.BrowserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration options for launching Chrome/Chromium via {@link ChromeDriver}.
 *
 * <p>This class mirrors the Selenium {@code ChromeOptions} API and adds Nihonium-specific
 * settings such as browser auto-download.
 *
 * <pre>{@code
 * ChromeOptions options = new ChromeOptions()
 *     .setHeadless(true)
 *     .setBrowserType(BrowserType.CHROME)
 *     .setAutoDownload(true);
 *
 * WebDriver driver = new ChromeDriver(options);
 * }</pre>
 */
public class ChromeOptions {

    // ── Defaults ──────────────────────────────────────────────────────────────

    private static final int     DEFAULT_WINDOW_WIDTH  = 1280;
    private static final int     DEFAULT_WINDOW_HEIGHT = 720;
    private static final int     DEFAULT_DEBUGGING_PORT = 0; // 0 = auto-select
    private static final boolean DEFAULT_HEADLESS      = false;
    private static final boolean DEFAULT_AUTO_DOWNLOAD = true;

    // ── Fields ────────────────────────────────────────────────────────────────

    private String              binaryPath;
    private BrowserType         browserType    = BrowserType.CHROME;
    private String              browserVersion = null; // null = latest stable
    private boolean             autoDownload   = DEFAULT_AUTO_DOWNLOAD;
    private boolean             headless       = DEFAULT_HEADLESS;
    private List<String>        arguments      = new ArrayList<>();
    private Map<String, Object> experimentalOptions = new HashMap<>();
    private int                 debuggingPort  = DEFAULT_DEBUGGING_PORT;
    private String              userDataDir;
    private int                 windowWidth    = DEFAULT_WINDOW_WIDTH;
    private int                 windowHeight   = DEFAULT_WINDOW_HEIGHT;

    public ChromeOptions() { }

    // ── Binary / browser selection ────────────────────────────────────────────

    /**
     * Sets the path to an existing Chrome/Chromium binary.
     * Supplying a path disables auto-download for that invocation.
     *
     * @param path absolute path to the binary
     * @return {@code this}
     */
    public ChromeOptions setBinaryPath(String path) {
        this.binaryPath = path;
        return this;
    }

    /** @deprecated Use {@link #setBinaryPath(String)} */
    @Deprecated(forRemoval = true)
    public ChromeOptions setBinary(String path) {
        return setBinaryPath(path);
    }

    public String getBinaryPath() { return binaryPath; }

    /** @deprecated Use {@link #getBinaryPath()} */
    @Deprecated(forRemoval = true)
    public String getBinary() { return binaryPath; }

    /**
     * Selects which browser to download/launch (default: {@link BrowserType#CHROME}).
     *
     * @param browserType the browser type
     * @return {@code this}
     */
    public ChromeOptions setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
        return this;
    }

    public BrowserType getBrowserType() { return browserType; }

    /**
     * Pins the auto-download to a specific browser version (e.g. {@code "131.0.6778.108"}).
     * Pass {@code null} (the default) to always use the latest stable release.
     *
     * @param version dotted version string, or {@code null} for latest
     * @return {@code this}
     */
    public ChromeOptions setBrowserVersion(String version) {
        this.browserVersion = version;
        return this;
    }

    public String getBrowserVersion() { return browserVersion; }

    /**
     * Controls whether Nihonium automatically downloads the browser when no local
     * installation is found (default: {@code true}).
     *
     * @param autoDownload {@code false} to disable auto-download
     * @return {@code this}
     */
    public ChromeOptions setAutoDownload(boolean autoDownload) {
        this.autoDownload = autoDownload;
        return this;
    }

    public boolean isAutoDownload() { return autoDownload; }

    // ── Launch flags ──────────────────────────────────────────────────────────

    /**
     * Enables or disables headless mode (default: {@code false}).
     *
     * @param headless {@code true} for headless
     * @return {@code this}
     */
    public ChromeOptions setHeadless(boolean headless) {
        this.headless = headless;
        return this;
    }

    public boolean isHeadless() { return headless; }

    /**
     * Appends one or more command-line arguments to the browser launch command.
     *
     * @param arguments arguments to add (e.g. {@code "--no-sandbox"})
     * @return {@code this}
     */
    public ChromeOptions addArguments(String... arguments) {
        for (String arg : arguments) {
            this.arguments.add(arg);
        }
        return this;
    }

    public ChromeOptions addArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
        return this;
    }

    public List<String> getArguments() { return new ArrayList<>(arguments); }

    /**
     * Sets a Chrome experimental option.
     * Use this for options that do not have a dedicated setter.
     *
     * @param name  option name
     * @param value option value
     * @return {@code this}
     */
    public ChromeOptions setExperimentalOption(String name, Object value) {
        this.experimentalOptions.put(name, value);
        return this;
    }

    public Object getExperimentalOption(String name) {
        return experimentalOptions.get(name);
    }

    public Map<String, Object> getExperimentalOptions() {
        return new HashMap<>(experimentalOptions);
    }

    // ── Connection ────────────────────────────────────────────────────────────

    /**
     * Sets the remote-debugging port Chrome should listen on.
     * Use {@code 0} (the default) to let the OS assign a free port automatically.
     *
     * @param port port number, or {@code 0} for automatic
     * @return {@code this}
     */
    public ChromeOptions setDebuggingPort(int port) {
        this.debuggingPort = port;
        return this;
    }

    public int getDebuggingPort() { return debuggingPort; }

    /** No-op — kept for Selenium API compatibility. */
    public ChromeOptions setDebuggerAddress(String address) {
        return this;
    }

    // ── Profile / window ──────────────────────────────────────────────────────

    /**
     * Sets the user-data directory for the browser profile.
     * If not set, a temporary directory is created for each session.
     *
     * @param userDataDir path to the user data directory
     * @return {@code this}
     */
    public ChromeOptions setUserDataDir(String userDataDir) {
        this.userDataDir = userDataDir;
        return this;
    }

    public String getUserDataDir() { return userDataDir; }

    /**
     * Sets the initial browser window size.
     *
     * @param width  width in pixels
     * @param height height in pixels
     * @return {@code this}
     */
    public ChromeOptions setWindowSize(int width, int height) {
        this.windowWidth  = width;
        this.windowHeight = height;
        return this;
    }

    public int getWindowWidth()  { return windowWidth; }
    public int getWindowHeight() { return windowHeight; }
}
