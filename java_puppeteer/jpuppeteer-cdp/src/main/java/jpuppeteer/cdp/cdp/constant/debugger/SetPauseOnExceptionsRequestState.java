package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum SetPauseOnExceptionsRequestState {

    NONE("none"),
    UNCAUGHT("uncaught"),
    ALL("all"),
    ;

    private String value;

    SetPauseOnExceptionsRequestState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetPauseOnExceptionsRequestState findByValue(String value) {
        for(SetPauseOnExceptionsRequestState val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}