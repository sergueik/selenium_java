package jpuppeteer.cdp.cdp.constant.log;

/**
*/
public enum LogEntryLevel {

    VERBOSE("verbose"),
    INFO("info"),
    WARNING("warning"),
    ERROR("error"),
    ;

    private String value;

    LogEntryLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static LogEntryLevel findByValue(String value) {
        for(LogEntryLevel val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}