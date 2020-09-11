package jpuppeteer.cdp.cdp.constant.network;

/**
* The reason why request was blocked.
*/
public enum BlockedReason {

    OTHER("other"),
    CSP("csp"),
    MIXED_CONTENT("mixed-content"),
    ORIGIN("origin"),
    INSPECTOR("inspector"),
    SUBRESOURCE_FILTER("subresource-filter"),
    CONTENT_TYPE("content-type"),
    COLLAPSED_BY_CLIENT("collapsed-by-client"),
    ;

    private String value;

    BlockedReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static BlockedReason findByValue(String value) {
        for(BlockedReason val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}