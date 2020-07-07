package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum SetInstrumentationBreakpointRequestInstrumentation {

    BEFORESCRIPTEXECUTION("beforeScriptExecution"),
    BEFORESCRIPTWITHSOURCEMAPEXECUTION("beforeScriptWithSourceMapExecution"),
    ;

    private String value;

    SetInstrumentationBreakpointRequestInstrumentation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetInstrumentationBreakpointRequestInstrumentation findByValue(String value) {
        for(SetInstrumentationBreakpointRequestInstrumentation val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}