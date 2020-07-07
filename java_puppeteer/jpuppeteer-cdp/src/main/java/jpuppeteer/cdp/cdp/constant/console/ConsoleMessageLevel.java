package jpuppeteer.cdp.cdp.constant.console;

/**
*/
public enum ConsoleMessageLevel {

    LOG("log"),
    WARNING("warning"),
    ERROR("error"),
    DEBUG("debug"),
    INFO("info"),
    ;

    private String value;

    ConsoleMessageLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ConsoleMessageLevel findByValue(String value) {
        for(ConsoleMessageLevel val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}