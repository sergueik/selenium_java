package io.github.ashwithpoojary98.browser;

/**
 * Browser types supported by Nihonium's auto-download mechanism.
 *
 * <p>Browsers are downloaded from the
 * <a href="https://googlechromelabs.github.io/chrome-for-testing/">Chrome for Testing</a>
 * distribution, which provides stable, automation-friendly builds.
 *
 * <p>Usage example:
 * <pre>{@code
 * BrowserOptions options = BrowserOptions.builder()
 *     .browserType(BrowserType.CHROME)
 *     .autoDownload(true)
 *     .build();
 * }</pre>
 */
public enum BrowserType {

    /**
     * Google Chrome (Chrome for Testing — stable channel).
     * Uses the official {@code chrome} artifact from the Chrome for Testing distribution.
     */
    CHROME("chrome"),

    /**
     * Chromium open-source browser.
     * Uses the {@code chromium} artifact from the Chrome for Testing distribution.
     */
    CHROMIUM("chromium");

    private final String id;

    BrowserType(String id) {
        this.id = id;
    }

    /**
     * Returns the lowercase identifier used for cache directory names and
     * Chrome for Testing API artifact keys.
     *
     * @return identifier string (e.g. {@code "chrome"} or {@code "chromium"})
     */
    public String getId() {
        return id;
    }
}
