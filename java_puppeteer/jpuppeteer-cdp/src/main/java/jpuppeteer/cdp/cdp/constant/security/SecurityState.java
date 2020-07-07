package jpuppeteer.cdp.cdp.constant.security;

/**
* The security level of a page or resource.
*/
public enum SecurityState {

    UNKNOWN("unknown"),
    NEUTRAL("neutral"),
    INSECURE("insecure"),
    SECURE("secure"),
    INFO("info"),
    INSECURE_BROKEN("insecure-broken"),
    ;

    private String value;

    SecurityState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SecurityState findByValue(String value) {
        for(SecurityState val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}