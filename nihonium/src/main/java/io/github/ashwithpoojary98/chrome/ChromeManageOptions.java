package io.github.ashwithpoojary98.chrome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.Cookie;
import io.github.ashwithpoojary98.WebDriver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CDP-backed implementation of {@link WebDriver.Options}.
 *
 * <p>Cookie operations are forwarded to the CDP {@code Network} domain via
 * {@link io.github.ashwithpoojary98.cdp.domain.NetworkDomain}.
 * Timeout values are stored locally; script/page-load timeouts are applied
 * when the corresponding CDP session commands are invoked.
 */
class ChromeManageOptions implements WebDriver.Options {

    // ── Default timeout values ────────────────────────────────────────────────

    private static final long DEFAULT_IMPLICIT_WAIT_MILLIS   = 0L;
    private static final long DEFAULT_SCRIPT_TIMEOUT_MILLIS  = 30_000L;
    private static final long DEFAULT_PAGE_LOAD_TIMEOUT_MILLIS = 30_000L;

    // ── CDP response field names ──────────────────────────────────────────────

    private static final String FIELD_COOKIES   = "cookies";
    private static final String FIELD_NAME      = "name";
    private static final String FIELD_VALUE     = "value";
    private static final String FIELD_DOMAIN    = "domain";
    private static final String FIELD_PATH      = "path";
    private static final String FIELD_EXPIRES   = "expires";
    private static final String FIELD_SECURE    = "secure";
    private static final String FIELD_HTTP_ONLY = "httpOnly";

    /** CDP uses {@code -1} for session cookies that have no expiry date. */
    private static final long SESSION_COOKIE_EXPIRES = -1L;

    // ─────────────────────────────────────────────────────────────────────────

    private final ChromeDriver driver;

    private long implicitWaitMillis    = DEFAULT_IMPLICIT_WAIT_MILLIS;
    private long scriptTimeoutMillis   = DEFAULT_SCRIPT_TIMEOUT_MILLIS;
    private long pageLoadTimeoutMillis = DEFAULT_PAGE_LOAD_TIMEOUT_MILLIS;

    ChromeManageOptions(ChromeDriver driver) {
        this.driver = driver;
    }

    // ── Cookie management ─────────────────────────────────────────────────────

    /**
     * Adds a cookie to the current browser session.
     *
     * <p>The cookie is sent to the CDP {@code Network.setCookie} command.
     *
     * @param cookie the cookie to add; must not be {@code null}
     */
    @Override
    public void addCookie(Cookie cookie) {
        long expiresSeconds = SESSION_COOKIE_EXPIRES;
        if (cookie.getExpiry() != null) {
            expiresSeconds = cookie.getExpiry().getTime() / 1_000L;
        }

        driver.getNetworkDomain().setCookie(
                cookie.getName(),
                cookie.getValue(),
                cookie.getDomain(),
                cookie.getPath(),
                cookie.isSecure(),
                cookie.isHttpOnly(),
                expiresSeconds
        ).join();
    }

    /**
     * Deletes the cookie with the given name visible from the current page.
     *
     * @param name cookie name to delete; must not be {@code null}
     */
    @Override
    public void deleteCookieNamed(String name) {
        driver.getNetworkDomain().deleteCookies(name, null).join();
    }

    /**
     * Deletes the specified cookie.
     *
     * @param cookie cookie to delete (name and domain are used for matching)
     */
    @Override
    public void deleteCookie(Cookie cookie) {
        driver.getNetworkDomain().deleteCookies(cookie.getName(), cookie.getDomain()).join();
    }

    /** Clears all cookies in the browser. */
    @Override
    public void deleteAllCookies() {
        driver.getNetworkDomain().clearBrowserCookies().join();
    }

    /**
     * Returns all cookies visible to the current page.
     *
     * @return set of {@link Cookie} instances (never {@code null}, may be empty)
     */
    @Override
    public Set<Cookie> getCookies() {
        try {
            JsonObject response = driver.getNetworkDomain().getCookies().join();
            JsonArray  rawList  = response.getAsJsonArray(FIELD_COOKIES);
            Set<Cookie> result  = new HashSet<>();

            for (JsonElement el : rawList) {
                JsonObject raw = el.getAsJsonObject();
                result.add(parseCookie(raw));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve cookies", e);
        }
    }

    /**
     * Returns the cookie with the given name, or {@code null} if not found.
     *
     * @param name cookie name to search for
     * @return the matching {@link Cookie}, or {@code null}
     */
    @Override
    public Cookie getCookieNamed(String name) {
        return getCookies().stream()
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .orElse(null);
    }

    // ── Timeouts ──────────────────────────────────────────────────────────────

    @Override
    public WebDriver.Timeouts timeouts() {
        return new ChromeTimeouts(this);
    }

    @Override
    public WebDriver.Window window() {
        return new ChromeWindow(driver);
    }

    // ── Package-visible accessors (used by ChromeElement auto-wait) ───────────

    long getImplicitWaitMillis()           { return implicitWaitMillis; }
    void setImplicitWaitMillis(long millis){ this.implicitWaitMillis = millis; }

    long getScriptTimeoutMillis()            { return scriptTimeoutMillis; }
    void setScriptTimeoutMillis(long millis) { this.scriptTimeoutMillis = millis; }

    long getPageLoadTimeoutMillis()              { return pageLoadTimeoutMillis; }
    void setPageLoadTimeoutMillis(long millis)   { this.pageLoadTimeoutMillis = millis; }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Converts a raw CDP cookie JSON object into a {@link Cookie}.
     *
     * @param raw CDP cookie JSON object
     * @return corresponding {@link Cookie}
     */
    private Cookie parseCookie(JsonObject raw) {
        String name  = raw.get(FIELD_NAME).getAsString();
        String value = raw.get(FIELD_VALUE).getAsString();

        String domain   = raw.has(FIELD_DOMAIN)    ? raw.get(FIELD_DOMAIN).getAsString()    : null;
        String path     = raw.has(FIELD_PATH)      ? raw.get(FIELD_PATH).getAsString()      : null;
        boolean secure  = raw.has(FIELD_SECURE)    && raw.get(FIELD_SECURE).getAsBoolean();
        boolean httpOnly = raw.has(FIELD_HTTP_ONLY) && raw.get(FIELD_HTTP_ONLY).getAsBoolean();

        Date expiry = null;
        if (raw.has(FIELD_EXPIRES)) {
            long expiresSeconds = raw.get(FIELD_EXPIRES).getAsLong();
            if (expiresSeconds > 0) {
                expiry = new Date(expiresSeconds * 1_000L);
            }
        }

        return new Cookie(name, value, domain, path, expiry, secure, httpOnly);
    }

    // ── Timeouts inner class ──────────────────────────────────────────────────

    private static final class ChromeTimeouts implements WebDriver.Timeouts {

        private final ChromeManageOptions options;

        ChromeTimeouts(ChromeManageOptions options) {
            this.options = options;
        }

        @Override
        public WebDriver.Timeouts implicitlyWait(long time, TimeUnit unit) {
            options.setImplicitWaitMillis(unit.toMillis(time));
            return this;
        }

        @Override
        public WebDriver.Timeouts setScriptTimeout(long time, TimeUnit unit) {
            options.setScriptTimeoutMillis(unit.toMillis(time));
            return this;
        }

        @Override
        public WebDriver.Timeouts pageLoadTimeout(long time, TimeUnit unit) {
            options.setPageLoadTimeoutMillis(unit.toMillis(time));
            return this;
        }
    }
}
