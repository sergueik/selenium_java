package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum ContinueToLocationRequestTargetCallFrames {

    ANY("any"),
    CURRENT("current"),
    ;

    private String value;

    ContinueToLocationRequestTargetCallFrames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ContinueToLocationRequestTargetCallFrames findByValue(String value) {
        for(ContinueToLocationRequestTargetCallFrames val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}