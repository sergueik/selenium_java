package jpuppeteer.cdp.cdp.constant.tracing;

/**
* experimental
*/
public enum TraceConfigRecordMode {

    RECORDUNTILFULL("recordUntilFull"),
    RECORDCONTINUOUSLY("recordContinuously"),
    RECORDASMUCHASPOSSIBLE("recordAsMuchAsPossible"),
    ECHOTOCONSOLE("echoToConsole"),
    ;

    private String value;

    TraceConfigRecordMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static TraceConfigRecordMode findByValue(String value) {
        for(TraceConfigRecordMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}