package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum BreakLocationType {

    DEBUGGERSTATEMENT("debuggerStatement"),
    CALL("call"),
    RETURN("return"),
    ;

    private String value;

    BreakLocationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static BreakLocationType findByValue(String value) {
        for(BreakLocationType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}