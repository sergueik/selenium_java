package jpuppeteer.cdp.cdp.constant.fetch;

/**
* experimental
*/
public enum AuthChallengeResponseResponse {

    DEFAULT("Default"),
    CANCELAUTH("CancelAuth"),
    PROVIDECREDENTIALS("ProvideCredentials"),
    ;

    private String value;

    AuthChallengeResponseResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AuthChallengeResponseResponse findByValue(String value) {
        for(AuthChallengeResponseResponse val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}