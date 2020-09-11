package jpuppeteer.api.browser;

public class UserAgent {

    private String userAgent;

    private String acceptLanguage;

    private String platform;

    public UserAgent(String userAgent, String acceptLanguage, String platform) {
        this.userAgent = userAgent;
        this.acceptLanguage = acceptLanguage;
        this.platform = platform;
    }

    public UserAgent(String userAgent) {
        this(userAgent, null, null);
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getPlatform() {
        return platform;
    }
}
