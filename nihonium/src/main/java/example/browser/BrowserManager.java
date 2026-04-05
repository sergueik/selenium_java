package example.browser;

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

public class BrowserManager {

	private static final Logger log = LoggerFactory.getLogger(BrowserManager.class);

	private static final String CFT_STABLE_URL = "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json";

	private static final String CFT_KNOWN_VERSIONS_URL = "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json";

	static final Path CACHE_ROOT = Path.of(System.getProperty("user.home"), ".cache", "nihonium");

	private final BrowserType browserType;

	private final String requestedVersion;

	private final Platform platform;
	private final Gson gson;
	private final HttpClient httpClient;

	public BrowserManager(BrowserType browserType) {
		this(browserType, null);
	}

	public BrowserManager(BrowserType browserType, String requestedVersion) {
		this.browserType = browserType;
		this.requestedVersion = requestedVersion;
		this.platform = Platform.current();
		this.gson = new Gson();
		this.httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
	}

	public String getBrowserPath() throws IOException {
		Path cached = findInCache();
		if (cached != null) {
			log.info("Using cached {} binary: {}", browserType, cached);
			return cached.toString();
		}

		log.info("Browser not found in cache — downloading {} ({}) for {}...", browserType,
				requestedVersion != null ? requestedVersion : "latest stable", platform);
		return download();
	}

	public Path getCacheRoot() {
		return CACHE_ROOT;
	}

	public Platform getPlatform() {
		return platform;
	}

	private Path findInCache() throws IOException {
		Path browserCache = CACHE_ROOT.resolve(browserType.getId()).resolve(platform.getIdentifier());

		if (!Files.exists(browserCache)) {
			return null;
		}

		if (requestedVersion != null) {
			Path versioned = browserCache.resolve(requestedVersion);
			return Files.isDirectory(versioned) ? locateBinaryIn(versioned) : null;
		}

		// Pick the highest semantic version present in cache
		try (var stream = Files.list(browserCache)) {
			return stream.filter(Files::isDirectory).max(this::compareVersionPaths).map(this::locateBinaryInQuietly)
					.orElse(null);
		}
	}

	private Path locateBinaryIn(Path root) throws IOException {
		String binaryName = platform.getBinaryName(browserType);
		try (var walk = Files.walk(root)) {
			return walk.filter(p -> p.getFileName().toString().equals(binaryName)).filter(Files::isRegularFile)
					.findFirst()
					.orElseThrow(() -> new IOException("Binary '" + binaryName + "' not found under " + root));
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
		return compareVersionStrings(a.getFileName().toString(), b.getFileName().toString());
	}

	private String download() throws IOException {
		DownloadInfo info = resolveDownloadInfo();

		Path versionDir = CACHE_ROOT.resolve(browserType.getId()).resolve(platform.getIdentifier())
				.resolve(info.getVersion());
		Files.createDirectories(versionDir);

		Path zipFile = versionDir.resolve("browser.zip");
		log.info("Downloading {} {} from {}", browserType, info.getVersion(), info.getUrl());
		downloadFile(info.getUrl(), zipFile);

		log.info("Extracting archive to {}", versionDir);
		extractZip(zipFile, versionDir);
		Files.deleteIfExists(zipFile);

		Path binary = locateBinaryIn(versionDir);
		makeExecutable(binary);
		log.info("Browser ready: {}", binary);
		return binary.toString();
	}

	private DownloadInfo resolveDownloadInfo() throws IOException {
		return requestedVersion == null ? fetchLatestStable() : fetchSpecificVersion(requestedVersion);
	}

	private DownloadInfo fetchLatestStable() throws IOException {
		String json = fetchUrl(CFT_STABLE_URL);
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonObject stable = root.getAsJsonObject("channels").getAsJsonObject("Stable");
		String version = stable.get("version").getAsString();
		String url = extractDownloadUrl(stable.getAsJsonObject("downloads"), version);
		return new DownloadInfo(version, url);
	}

	private DownloadInfo fetchSpecificVersion(String version) throws IOException {
		String json = fetchUrl(CFT_KNOWN_VERSIONS_URL);
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonArray versions = root.getAsJsonArray("versions");

		for (JsonElement el : versions) {
			JsonObject obj = el.getAsJsonObject();
			if (version.equals(obj.get("version").getAsString())) {
				String url = extractDownloadUrl(obj.getAsJsonObject("downloads"), version);
				return new DownloadInfo(version, url);
			}
		}

		throw new IOException("Version " + version + " not found in Chrome for Testing known-good-versions feed. "
				+ "Check available versions at " + CFT_KNOWN_VERSIONS_URL);
	}

	private String extractDownloadUrl(JsonObject downloads, String version) throws IOException {
		// The API key matches BrowserType.getId(): "chrome" or "chromium"
		JsonArray platformList = downloads.getAsJsonArray(browserType.getId());
		if (platformList == null) {
			throw new IOException("No '" + browserType.getId() + "' downloads found for version " + version);
		}

		String platformId = platform.getIdentifier();
		for (JsonElement el : platformList) {
			JsonObject entry = el.getAsJsonObject();
			if (platformId.equals(entry.get("platform").getAsString())) {
				return entry.get("url").getAsString();
			}
		}

		throw new IOException("No download URL for platform '" + platformId + "' in version " + version
				+ ". Available platforms may not include this combination.");
	}

	private String fetchUrl(String url) throws IOException {
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new IOException("HTTP " + response.statusCode() + " fetching " + url);
			}
			return response.body();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Interrupted while fetching " + url, e);
		}
	}

	private void downloadFile(String url, Path target) throws IOException {
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			httpClient.send(request, HttpResponse.BodyHandlers.ofFile(target));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Interrupted while downloading " + url, e);
		}
	}

	private void extractZip(Path zipFile, Path targetDir) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(Files.newInputStream(zipFile)))) {
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

	private class DownloadInfo {
		private String version;
		private String url;

		public String getVersion() {
			return version;
		}

		public String getUrl() {
			return url;
		}

		public DownloadInfo(String version, String url) {
			this.version = version;
			this.url = url;

		}
	}
}
