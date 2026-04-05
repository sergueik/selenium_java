package example.browser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserLauncher {

	private static final Logger log = LoggerFactory.getLogger(BrowserLauncher.class);

	private static final String FLAG_REMOTE_DEBUGGING_PORT = "--remote-debugging-port=";
	private static final String FLAG_HEADLESS = "--headless=new";
	private static final String FLAG_WINDOW_SIZE = "--window-size=";
	private static final String FLAG_USER_DATA_DIR = "--user-data-dir=";
	private static final String FLAG_NO_FIRST_RUN = "--no-first-run";
	private static final String FLAG_NO_DEFAULT_BROWSER_CHECK = "--no-default-browser-check";
	private static final String INITIAL_PAGE = "about:blank";
	private static final String TEMP_PROFILE_PREFIX = "nihonium-chrome-profile-";

	private static final String CDP_JSON_PATH = "/json";
	private static final String CDP_HOST = "http://localhost:";
	private static final int CDP_CONNECT_TIMEOUT_MILLIS = 1_000;
	private static final int CDP_READ_TIMEOUT_MILLIS = 1_000;
	private static final int HTTP_OK = 200;

	private static final String TARGET_KEY_TYPE = "type";
	private static final String TARGET_TYPE_PAGE = "page";
	private static final String TARGET_KEY_WS_URL = "webSocketDebuggerUrl";

	private static final int CDP_MAX_RETRIES = 10;

	private static final long CDP_RETRY_DELAY_MILLIS = 500L;

	private static final long SHUTDOWN_GRACE_SECONDS = 5L;

	private static final long SHUTDOWN_FORCE_SECONDS = 2L;

	private static final String ENV_CHROME_PATH = "CHROME_PATH";
	private static final String ENV_LOCALAPPDATA = "LOCALAPPDATA";

	private Process browserProcess;
	private final BrowserOptions options;
	private final Gson gson;

	public BrowserLauncher() {
		this(BrowserOptions.builder().build());
	}

	public BrowserLauncher(BrowserOptions options) {
		this.options = options;
		this.gson = new Gson();
	}

	public LaunchResult launch() throws IOException {
		String binaryPath = findChromePath();
		int port = options.getDebuggingPort() != 0 ? options.getDebuggingPort() : findAvailablePort();

		List<String> command = buildCommandLine(binaryPath, port);
		log.debug("Launching browser: {}", command);

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		browserProcess = pb.start();

		if (!browserProcess.isAlive()) {
			throw new IOException("Browser process failed to start immediately after launch");
		}

		String webSocketUrl = waitForWebSocketUrl(port);
		log.info("Browser started on port {} (WS: {})", port, webSocketUrl);
		return new LaunchResult(browserProcess, webSocketUrl);
	}

	public boolean isRunning() {
		return browserProcess != null && browserProcess.isAlive();
	}

	public Process getProcess() {
		return browserProcess;
	}

	public void shutdown() {
		if (browserProcess == null || !browserProcess.isAlive()) {
			return;
		}
		browserProcess.destroy();
		try {
			boolean exited = browserProcess.waitFor(SHUTDOWN_GRACE_SECONDS, TimeUnit.SECONDS);
			if (!exited) {
				log.warn("Browser did not exit within {} s — forcing termination", SHUTDOWN_GRACE_SECONDS);
				browserProcess.destroyForcibly();
				browserProcess.waitFor(SHUTDOWN_FORCE_SECONDS, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			browserProcess.destroyForcibly();
		}
	}

	private String findChromePath() throws IOException {
		// 1. Explicit path
		String explicit = options.getBinaryPath();
		if (explicit != null) {
			if (Files.exists(Paths.get(explicit))) {
				return explicit;
			}
			throw new IOException("Specified browser binary not found: " + explicit);
		}

		String envPath = System.getenv(ENV_CHROME_PATH);
		if (envPath != null) {
			if (Files.exists(Paths.get(envPath))) {
				log.debug("Using browser from {}: {}", ENV_CHROME_PATH, envPath);
				return envPath;
			}
			log.warn("{} is set but '{}' does not exist — falling back to system search", ENV_CHROME_PATH, envPath);
		}

		String systemPath = findSystemInstallation();
		if (systemPath != null) {
			log.debug("Using system browser: {}", systemPath);
			return systemPath;
		}

		if (options.isAutoDownload()) {
			log.info("No browser found locally. Initiating auto-download ({} {})...", options.getBrowserType(),
					options.getBrowserVersion() != null ? options.getBrowserVersion() : "latest");
			return new BrowserManager(options.getBrowserType(), options.getBrowserVersion()).getBrowserPath();
		}

		throw new IOException("Browser binary not found. Install Chrome, set the " + ENV_CHROME_PATH
				+ " environment variable, or enable autoDownload. " + "Browser type: " + options.getBrowserType());
	}

	private String findSystemInstallation() {
		String os = System.getProperty("os.name", "").toLowerCase();
		List<String> candidates = new ArrayList<>();

		if (os.contains("win")) {
			candidates.add("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			candidates.add("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			String localAppData = System.getenv(ENV_LOCALAPPDATA);
			if (localAppData != null) {
				candidates.add(localAppData + "\\Google\\Chrome\\Application\\chrome.exe");
			}
			candidates.add("C:\\Program Files\\Chromium\\Application\\chrome.exe");
		} else if (os.contains("mac") || os.contains("darwin")) {
			candidates.add("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
			candidates.add("/Applications/Chromium.app/Contents/MacOS/Chromium");
			candidates.add(
					System.getProperty("user.home") + "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
		} else {
			candidates.add("/usr/bin/google-chrome");
			candidates.add("/usr/bin/google-chrome-stable");
			candidates.add("/usr/bin/chromium");
			candidates.add("/usr/bin/chromium-browser");
			candidates.add("/snap/bin/chromium");
		}

		return candidates.stream().filter(p -> Files.exists(Paths.get(p))).findFirst().orElse(null);
	}

	private List<String> buildCommandLine(String binaryPath, int port) throws IOException {
		List<String> cmd = new ArrayList<>();
		cmd.add(binaryPath);
		cmd.add(FLAG_REMOTE_DEBUGGING_PORT + port);

		if (options.isHeadless()) {
			cmd.add(FLAG_HEADLESS);
		}

		cmd.add(FLAG_WINDOW_SIZE + options.getWindowWidth() + "," + options.getWindowHeight());

		String userDataDir = options.getUserDataDir();
		if (userDataDir != null) {
			cmd.add(FLAG_USER_DATA_DIR + userDataDir);
		} else {
			Path tempDir = Files.createTempDirectory(TEMP_PROFILE_PREFIX);
			cmd.add(FLAG_USER_DATA_DIR + tempDir.toAbsolutePath());
		}

		cmd.add(FLAG_NO_FIRST_RUN);
		cmd.add(FLAG_NO_DEFAULT_BROWSER_CHECK);
		cmd.addAll(options.getArguments());
		cmd.add(INITIAL_PAGE);

		return cmd;
	}

	private String waitForWebSocketUrl(int port) throws IOException {
		String endpointUrl = CDP_HOST + port + CDP_JSON_PATH;

		for (int attempt = 1; attempt <= CDP_MAX_RETRIES; attempt++) {
			try {
				String wsUrl = fetchPageWebSocketUrl(endpointUrl);
				log.debug("CDP page target found on attempt {}", attempt);
				return wsUrl;
			} catch (IOException e) {
				if (attempt == CDP_MAX_RETRIES) {
					throw new IOException(
							"CDP endpoint not ready after " + CDP_MAX_RETRIES + " attempts: " + endpointUrl, e);
				}
				log.debug("CDP endpoint not ready (attempt {}/{}), retrying in {} ms", attempt, CDP_MAX_RETRIES,
						CDP_RETRY_DELAY_MILLIS);
				try {
					Thread.sleep(CDP_RETRY_DELAY_MILLIS);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					throw new IOException("Interrupted while waiting for browser to start", ie);
				}
			}
		}

		// Unreachable, but satisfies the compiler
		throw new IOException("Failed to obtain WebSocket debugger URL from " + endpointUrl);
	}

	private String fetchPageWebSocketUrl(String endpointUrl) throws IOException {
		URL url = new URL(endpointUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(CDP_CONNECT_TIMEOUT_MILLIS);
		conn.setReadTimeout(CDP_READ_TIMEOUT_MILLIS);

		if (conn.getResponseCode() != HTTP_OK) {
			throw new IOException("CDP endpoint returned HTTP " + conn.getResponseCode());
		}

		StringBuilder body = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				body.append(line);
			}
		}

		JsonArray targets = gson.fromJson(body.toString(), JsonArray.class);
		for (int i = 0; i < targets.size(); i++) {
			JsonObject target = targets.get(i).getAsJsonObject();
			if (TARGET_TYPE_PAGE.equals(target.get(TARGET_KEY_TYPE).getAsString())) {
				return target.get(TARGET_KEY_WS_URL).getAsString();
			}
		}

		throw new IOException("CDP endpoint is up but no '" + TARGET_TYPE_PAGE + "' target found yet");
	}

	private int findAvailablePort() throws IOException {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		}
	}
}
