package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum SetWebLifecycleStateRequestState {

    FROZEN("frozen"),
    ACTIVE("active"),
    ;

    private String value;

    SetWebLifecycleStateRequestState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetWebLifecycleStateRequestState findByValue(String value) {
        for(SetWebLifecycleStateRequestState val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}