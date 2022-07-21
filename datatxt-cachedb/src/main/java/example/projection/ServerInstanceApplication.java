package example.projection;

public class ServerInstanceApplication {

	private String serverName;
	private String instanceName;
	private String applicationName;

	public ServerInstanceApplication() {

	}

	public ServerInstanceApplication(String serverName, String instanceName,
			String applicationName) {

		this.serverName = serverName;
		this.instanceName = instanceName;
		this.applicationName = applicationName;

	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String value) {
		instanceName = value;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String value) {
		serverName = value;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String value) {
		applicationName = value;
	}
}
