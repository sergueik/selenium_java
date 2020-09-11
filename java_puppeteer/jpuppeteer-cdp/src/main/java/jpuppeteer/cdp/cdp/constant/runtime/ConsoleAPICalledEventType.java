package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum ConsoleAPICalledEventType {

    LOG("log"),
    DEBUG("debug"),
    INFO("info"),
    ERROR("error"),
    WARNING("warning"),
    DIR("dir"),
    DIRXML("dirxml"),
    TABLE("table"),
    TRACE("trace"),
    CLEAR("clear"),
    STARTGROUP("startGroup"),
    STARTGROUPCOLLAPSED("startGroupCollapsed"),
    ENDGROUP("endGroup"),
    ASSERT("assert"),
    PROFILE("profile"),
    PROFILEEND("profileEnd"),
    COUNT("count"),
    TIMEEND("timeEnd"),
    ;

    private String value;

    ConsoleAPICalledEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ConsoleAPICalledEventType findByValue(String value) {
        for(ConsoleAPICalledEventType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}