package jpuppeteer.cdp.cdp.constant.fetch;

/**
* experimental
*/
public enum AuthChallengeSource {

    SERVER("Server"),
    PROXY("Proxy"),
    ;

    private String value;

    AuthChallengeSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AuthChallengeSource findByValue(String value) {
        for(AuthChallengeSource val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}