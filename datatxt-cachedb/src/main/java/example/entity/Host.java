package example.entity;

import java.util.UUID;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Host {

	private String hostname;
	private String appid;
	private String datacenter;
	private String environment;
	private static String staticInfo;
	private int id;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String value) {
		hostname = value;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String value) {
		appid = value;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String value) {
		datacenter = value;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String value) {
		environment = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int value) {
		id = value;
	}

	public Host() {
		staticInfo = UUID.randomUUID().toString();
	}

	public /* static */ String getStaticInfo() {
		return Host.staticInfo;
	}

	public Host(int id, String hostname, String datacenter, String environment,
			String appid) {
		super();
		if (Host.staticInfo == null) {
			Host.staticInfo = UUID.randomUUID().toString();
		}
		this.hostname = hostname;
		this.id = id;
		this.datacenter = datacenter;
		this.environment = environment;
		this.appid = appid;
	}

	@Override
	public String toString() {
		return "Host{" + "id=" + this.id + ", hostname=" + this.hostname
				+ ", datacenter=" + this.datacenter + ", environment=" + this.environment
				+ ", appid=" + this.appid
				/* + ", addtime=" + addtime */ + '}';
	}

}
