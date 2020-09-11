package jpuppeteer.cdp.cdp.constant.log;

/**
*/
public enum LogEntrySource {

    XML("xml"),
    JAVASCRIPT("javascript"),
    NETWORK("network"),
    STORAGE("storage"),
    APPCACHE("appcache"),
    RENDERING("rendering"),
    SECURITY("security"),
    DEPRECATION("deprecation"),
    WORKER("worker"),
    VIOLATION("violation"),
    INTERVENTION("intervention"),
    RECOMMENDATION("recommendation"),
    OTHER("other"),
    ;

    private String value;

    LogEntrySource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static LogEntrySource findByValue(String value) {
        for(LogEntrySource val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}