package jpuppeteer.cdp.cdp.constant.memory;

/**
* Memory pressure level.
* experimental
*/
public enum PressureLevel {

    MODERATE("moderate"),
    CRITICAL("critical"),
    ;

    private String value;

    PressureLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PressureLevel findByValue(String value) {
        for(PressureLevel val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}