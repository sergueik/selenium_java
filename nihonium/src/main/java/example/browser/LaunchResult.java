package example.browser;

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