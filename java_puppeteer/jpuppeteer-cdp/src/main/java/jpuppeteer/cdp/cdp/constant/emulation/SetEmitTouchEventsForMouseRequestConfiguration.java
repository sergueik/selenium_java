package jpuppeteer.cdp.cdp.constant.emulation;

/**
*/
public enum SetEmitTouchEventsForMouseRequestConfiguration {

    MOBILE("mobile"),
    DESKTOP("desktop"),
    ;

    private String value;

    SetEmitTouchEventsForMouseRequestConfiguration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetEmitTouchEventsForMouseRequestConfiguration findByValue(String value) {
        for(SetEmitTouchEventsForMouseRequestConfiguration val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}