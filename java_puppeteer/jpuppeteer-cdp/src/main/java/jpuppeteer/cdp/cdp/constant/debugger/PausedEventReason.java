package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum PausedEventReason {

    AMBIGUOUS("ambiguous"),
    ASSERT("assert"),
    DEBUGCOMMAND("debugCommand"),
    DOM("DOM"),
    EVENTLISTENER("EventListener"),
    EXCEPTION("exception"),
    INSTRUMENTATION("instrumentation"),
    OOM("OOM"),
    OTHER("other"),
    PROMISEREJECTION("promiseRejection"),
    XHR("XHR"),
    ;

    private String value;

    PausedEventReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PausedEventReason findByValue(String value) {
        for(PausedEventReason val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}