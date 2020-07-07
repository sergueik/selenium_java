package jpuppeteer.cdp.cdp.constant.network;

/**
* Represents the cookie's 'SameSite' status: https://tools.ietf.org/html/draft-west-first-party-cookies
*/
public enum CookieSameSite {

    STRICT("Strict"),
    LAX("Lax"),
    NONE("None"),
    ;

    private String value;

    CookieSameSite(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CookieSameSite findByValue(String value) {
        for(CookieSameSite val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}