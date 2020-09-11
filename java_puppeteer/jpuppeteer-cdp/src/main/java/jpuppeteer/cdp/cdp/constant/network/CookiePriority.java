package jpuppeteer.cdp.cdp.constant.network;

/**
* Represents the cookie's 'Priority' status: https://tools.ietf.org/html/draft-west-cookie-priority-00
*/
public enum CookiePriority {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    ;

    private String value;

    CookiePriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CookiePriority findByValue(String value) {
        for(CookiePriority val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}