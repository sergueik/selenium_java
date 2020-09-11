package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum SetDownloadBehaviorRequestBehavior {

    DENY("deny"),
    ALLOW("allow"),
    DEFAULT("default"),
    ;

    private String value;

    SetDownloadBehaviorRequestBehavior(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetDownloadBehaviorRequestBehavior findByValue(String value) {
        for(SetDownloadBehaviorRequestBehavior val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}