package io.github.ashwithpoojary98.browser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Manages browser binary downloads and caching for Nihonium.
 *
 * <h2>Cache layout (Selenium Manager convention)</h2>
 * <pre>
 *   ~/.cache/nihonium/
 *   └── chrome/
 *       ├── linux64/
 *       │   └── 131.0.6778.108/
 *       │       └── chrome-linux64/
 *       │           └── chrome          ← executable
 *       ├── win64/
 *       │   └── 131.0.6778.108/
 *       │       └── chrome-win64/
 *       │           └── chrome.exe
 *       └── mac-x64/
 *           └── 131.0.6778.108/
 *               └── chrome-mac-x64/
 *                   └── Google Chrome for Testing.app/...
 * </pre>
 *
 * <h2>Download source</h2>
 * <p>Binaries are fetched from the official
 * <a href="https://googlechromelabs.github.io/chrome-for-testing/">Chrome for Testing</a>
 * JSON API. No ChromeDriver or external tools are required.
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * // Latest stable Chrome
 * BrowserManager manager = new BrowserManager(BrowserType.CHROME);
 * String binaryPath = manager.getBrowserPath();
 *
 * // Specific version
 * BrowserManager manager = new BrowserManager(BrowserType.CHROME, "131.0.6778.108");
 * String binaryPath = manager.getBrowserPath();
 * }</pre>
 */
public class BrowserManager {

    private static final Logger log = LoggerFactory.getLogger(BrowserManager.class);

    /** Chrome for Testing JSON endpoint — latest stable channel versions. */
    private static final String CFT_STABLE_URL =
            "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json";

    /** Chrome for Testing JSON endpoint — all known good versions (for specific-version lookup). */
    private static final String CFT_KNOWN_VERSIONS_URL =
            "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json";

    /**
     * Root cache directory.
     * Mirrors the Selenium Manager convention: {@code ~/.cache/nihonium/}.
     */
    static final Path CACHE_ROOT =
            Path.of(System.getProperty("user.home"), ".cache", "nihonium");

    private final BrowserType browserType;

    /**
     * Version string requested by the caller, or {@code null} for latest stable.
     * Must be a dotted version such as {@code "131.0.6778.108"}.
     */
    private final String requestedVersion;

    private final Platform platform;
    private final Gson gson;
    private final HttpClient httpClient;

    // ─────────────────────────────────────────────────────────────────────────
    //  Construction
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Creates a manager for the given browser type, targeting the latest stable version.
     *
     * @param browserType browser to manage
     */
    public BrowserManager(BrowserType browserType) {
        this(browserType, null);
    }

    /**
     * Creates a manager for the given browser type and version.
     *
     * @param browserType      browser to manage
     * @param requestedVersion dotted version string (e.g. {@code "131.0.6778.108"}),
     *                         or {@code null} for the latest stable release
     */
    public BrowserManager(BrowserType browserType, String requestedVersion) {
        this.browserType      = browserType;
        this.requestedVersion = requestedVersion;
        this.platform         = Platform.current();
        this.gson             = new Gson();
        this.httpClient       = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Public API
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the absolute path to the browser executable.
     *
     * <p>Resolution order:
     * <ol>
     *   <li>Local Nihonium cache ({@code ~/.cache/nihonium/...})</li>
     *   <li>Download from the Chrome for Testing API</li>
     * </ol>
     *
     * @return absolute path to the browser executable
     * @throws IOException if the browser cannot be located or downloaded
     */
    public String getBrowserPath() throws IOException {
        Path cached = findInCache();
        if (cached != null) {
            log.info("Using cached {} binary: {}", browserType, cached);
            return cached.toString();
        }

        log.info("Browser not found in cache — downloading {} ({}) for {}...",
                browserType,
                requestedVersion != null ? requestedVersion : "latest stable",
                platform);
        return download();
    }

    /**
     * Returns the cache root directory.
     *
     * @return {@code ~/.cache/nihonium/}
     */
    public Path getCacheRoot() {
        return CACHE_ROOT;
    }

    /**
     * Returns the detected platform.
     *
     * @return current platform
     */
    public Platform getPlatform() {
        return platform;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Cache lookup
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Looks for an already-extracted binary in the cache directory.
     *
     * @return the {@link Path} to the binary, or {@code null} if not cached
     */
    private Path findInCache() throws IOException {
        Path browserCache = CACHE_ROOT
                .resolve(browserType.getId())
                .resolve(platform.getIdentifier());

        if (!Files.exists(browserCache)) {
            return null;
        }

        if (requestedVersion != null) {
            Path versioned = browserCache.resolve(requestedVersion);
            return Files.isDirectory(versioned) ? locateBinaryIn(versioned) : null;
        }

        // Pick the highest semantic version present in cache
        try (var stream = Files.list(browserCache)) {
            return stream
                    .filter(Files::isDirectory)
                    .max(this::compareVersionPaths)
                    .map(this::locateBinaryInQuietly)
                    .orElse(null);
        }
    }

    /**
     * Recursively walks {@code root} to find the browser executable.
     *
     * @param root extracted archive root
     * @return path to the executable
     * @throws IOException if the binary is not found
     */
    private Path locateBinaryIn(Path root) throws IOException {
        String binaryName = platform.getBinaryName(browserType);
        try (var walk = Files.walk(root)) {
            return walk
                    .filter(p -> p.getFileName().toString().equals(binaryName))
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .orElseThrow(() -> new IOException(
                            "Binary '" + binaryName + "' not found under " + root));
        }
    }

    private Path locateBinaryInQuietly(Path root) {
        try {
            return locateBinaryIn(root);
        } catch (IOException e) {
            return null;
        }
    }

    private int compareVersionPaths(Path a, Path b) {
        return compareVersionStrings(
                a.getFileName().toString(),
                b.getFileName().toString());
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Download
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Downloads, extracts, and returns the path to the browser binary.
     *
     * @return path to the browser executable
     * @throws IOException on download or extraction failure
     */
    private String download() throws IOException {
        DownloadInfo info = resolveDownloadInfo();

        Path versionDir = CACHE_ROOT
                .resolve(browserType.getId())
                .resolve(platform.getIdentifier())
                .resolve(info.version());
        Files.createDirectories(versionDir);

        Path zipFile = versionDir.resolve("browser.zip");
        log.info("Downloading {} {} from {}", browserType, info.version(), info.url());
        downloadFile(info.url(), zipFile);

        log.info("Extracting archive to {}", versionDir);
        extractZip(zipFile, versionDir);
        Files.deleteIfExists(zipFile);

        Path binary = locateBinaryIn(versionDir);
        makeExecutable(binary);
        log.info("Browser ready: {}", binary);
        return binary.toString();
    }

    /**
     * Resolves the download URL and version by querying the Chrome for Testing API.
     *
     * @return a {@link DownloadInfo} containing the resolved version and ZIP URL
     * @throws IOException on network or JSON parse error
     */
    private DownloadInfo resolveDownloadInfo() throws IOException {
        return requestedVersion == null
                ? fetchLatestStable()
                : fetchSpecificVersion(requestedVersion);
    }

    /**
     * Fetches the latest stable release metadata from the Chrome for Testing API.
     */
    private DownloadInfo fetchLatestStable() throws IOException {
        String json   = fetchUrl(CFT_STABLE_URL);
        JsonObject root   = gson.fromJson(json, JsonObject.class);
        JsonObject stable = root.getAsJsonObject("channels").getAsJsonObject("Stable");
        String version    = stable.get("version").getAsString();
        String url        = extractDownloadUrl(stable.getAsJsonObject("downloads"), version);
        return new DownloadInfo(version, url);
    }

    /**
     * Finds a specific version in the known-good-versions JSON feed.
     *
     * @param version dotted version string to look up
     */
    private DownloadInfo fetchSpecificVersion(String version) throws IOException {
        String json       = fetchUrl(CFT_KNOWN_VERSIONS_URL);
        JsonObject root   = gson.fromJson(json, JsonObject.class);
        JsonArray versions = root.getAsJsonArray("versions");

        for (JsonElement el : versions) {
            JsonObject obj = el.getAsJsonObject();
            if (version.equals(obj.get("version").getAsString())) {
                String url = extractDownloadUrl(obj.getAsJsonObject("downloads"), version);
                return new DownloadInfo(version, url);
            }
        }

        throw new IOException(
                "Version " + version + " not found in Chrome for Testing known-good-versions feed. "
                + "Check available versions at " + CFT_KNOWN_VERSIONS_URL);
    }

    /**
     * Extracts the platform-appropriate download URL from a {@code downloads} JSON object.
     *
     * @param downloads the {@code "downloads"} JSON object from the API response
     * @param version   version string used in error messages
     * @return download URL for this platform
     * @throws IOException if no URL is available for this platform
     */
    private String extractDownloadUrl(JsonObject downloads, String version) throws IOException {
        // The API key matches BrowserType.getId(): "chrome" or "chromium"
        JsonArray platformList = downloads.getAsJsonArray(browserType.getId());
        if (platformList == null) {
            throw new IOException(
                    "No '" + browserType.getId() + "' downloads found for version " + version);
        }

        String platformId = platform.getIdentifier();
        for (JsonElement el : platformList) {
            JsonObject entry = el.getAsJsonObject();
            if (platformId.equals(entry.get("platform").getAsString())) {
                return entry.get("url").getAsString();
            }
        }

        throw new IOException(
                "No download URL for platform '" + platformId + "' in version " + version
                + ". Available platforms may not include this combination.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  I/O helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Fetches the body of an HTTP GET request as a string.
     *
     * @param url URL to fetch
     * @return response body
     * @throws IOException on HTTP error or interruption
     */
    private String fetchUrl(String url) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("HTTP " + response.statusCode() + " fetching " + url);
            }
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while fetching " + url, e);
        }
    }

    /**
     * Downloads a file from {@code url} and saves it to {@code target}.
     *
     * @param url    source URL
     * @param target destination file path
     * @throws IOException on HTTP error or interruption
     */
    private void downloadFile(String url, Path target) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofFile(target));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while downloading " + url, e);
        }
    }

    /**
     * Extracts a ZIP archive into {@code targetDir}, preserving the internal directory
     * structure. Zip-slip entries are rejected.
     *
     * @param zipFile   source ZIP file
     * @param targetDir destination directory
     * @throws IOException on I/O error or Zip-slip detection
     */
    private void extractZip(Path zipFile, Path targetDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(Files.newInputStream(zipFile)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path resolved = targetDir.resolve(entry.getName()).normalize();
                // Guard against Zip Slip
                if (!resolved.startsWith(targetDir)) {
                    throw new IOException("Zip slip detected in entry: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(resolved);
                } else {
                    Files.createDirectories(resolved.getParent());
                    Files.copy(zis, resolved, StandardCopyOption.REPLACE_EXISTING);
                }
                zis.closeEntry();
            }
        }
    }

    /**
     * Sets the executable bit on the binary on POSIX systems.
     * This is a no-op on Windows.
     *
     * @param binary path to the browser binary
     */
    private void makeExecutable(Path binary) {
        if (platform.isWindows()) {
            return;
        }
        try {
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(binary);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(binary, perms);
        } catch (IOException e) {
            log.warn("Could not set executable permission on {}: {}", binary, e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Version comparison
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Compares two dotted version strings numerically, segment by segment.
     * Example: {@code "131.0.6778.108"} vs {@code "130.0.6723.0"}.
     *
     * @param a first version string
     * @param b second version string
     * @return negative, zero, or positive as {@code a} is less than, equal to, or greater than {@code b}
     */
    private int compareVersionStrings(String a, String b) {
        String[] partsA = a.split("\\.");
        String[] partsB = b.split("\\.");
        int len = Math.max(partsA.length, partsB.length);
        for (int i = 0; i < len; i++) {
            int numA = i < partsA.length ? parseIntSafe(partsA[i]) : 0;
            int numB = i < partsB.length ? parseIntSafe(partsB[i]) : 0;
            if (numA != numB) {
                return Integer.compare(numA, numB);
            }
        }
        return 0;
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Internal DTOs
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Carries the resolved version string and download URL returned by the API.
     *
     * @param version dotted version string (e.g. {@code "131.0.6778.108"})
     * @param url     ZIP download URL for this platform
     */
    private record DownloadInfo(String version, String url) {}
}
