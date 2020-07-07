package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum SetTouchEmulationEnabledRequestConfiguration {

    MOBILE("mobile"),
    DESKTOP("desktop"),
    ;

    private String value;

    SetTouchEmulationEnabledRequestConfiguration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetTouchEmulationEnabledRequestConfiguration findByValue(String value) {
        for(SetTouchEmulationEnabledRequestConfiguration val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}