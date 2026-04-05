package io.github.ashwithpoojary98.browser;

/**
 * Result of launching a browser process.
 *
 * @param process The browser process
 * @param webSocketUrl The WebSocket debugger URL for CDP connection
 */
public record LaunchResult(Process process, String webSocketUrl) {
}
