package jpuppeteer.cdp.cdp.constant.tracing;

/**
* experimental
*/
public enum StartRequestTransferMode {

    REPORTEVENTS("ReportEvents"),
    RETURNASSTREAM("ReturnAsStream"),
    ;

    private String value;

    StartRequestTransferMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StartRequestTransferMode findByValue(String value) {
        for(StartRequestTransferMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}