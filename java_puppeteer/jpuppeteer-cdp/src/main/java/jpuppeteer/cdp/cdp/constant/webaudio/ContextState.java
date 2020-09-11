package jpuppeteer.cdp.cdp.constant.webaudio;

/**
* Enum of AudioContextState from the spec
* experimental
*/
public enum ContextState {

    SUSPENDED("suspended"),
    RUNNING("running"),
    CLOSED("closed"),
    ;

    private String value;

    ContextState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ContextState findByValue(String value) {
        for(ContextState val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}