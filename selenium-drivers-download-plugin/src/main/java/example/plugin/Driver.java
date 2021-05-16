package example.plugin;

public class Driver {

	private String name;
	private String ver;
	private String url; // cannot set directly
	private String os;

	public String getName() {
		return name;
	}

	public String getOs() {
		return os;
	}

	public String getVer() {
		return ver;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	@Override
	public String toString() {
		return "Driver{" + "name='" + name + '\'' + ", ver='" + ver + '\''
				+ ", os='" + os + '\'' + '}';
	}
}
