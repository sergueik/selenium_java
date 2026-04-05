package example.browser;

public enum BrowserType {

	CHROME("chrome"), CHROMIUM("chromium");

	private final String id;

	BrowserType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
