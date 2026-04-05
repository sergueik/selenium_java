package io.github.ashwithpoojary98.browser;

/**
 * Result of launching a browser process.
 *
 * @param process      The browser process
 * @param webSocketUrl The WebSocket debugger URL for CDP connection
 */
public class LaunchResult {

	private Process process;
	private String webSocketUrl;

	public LaunchResult(Process process, String webSocketUrl) {
		this.webSocketUrl = webSocketUrl;
		this.process = process;
	}

	public Process getProcess() {
		return this.process;
	}

	public String getWebSocketUrl() {
		return this.webSocketUrl;
	}
}