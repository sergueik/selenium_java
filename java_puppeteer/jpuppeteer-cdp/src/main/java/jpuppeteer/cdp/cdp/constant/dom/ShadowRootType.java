package jpuppeteer.cdp.cdp.constant.dom;

/**
* Shadow root type.
*/
public enum ShadowRootType {

    USER_AGENT("user-agent"),
    OPEN("open"),
    CLOSED("closed"),
    ;

    private String value;

    ShadowRootType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ShadowRootType findByValue(String value) {
        for(ShadowRootType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}