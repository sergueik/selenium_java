package jpuppeteer.cdp.cdp.constant.webaudio;

/**
* Enum of AudioParam::AutomationRate from the spec
* experimental
*/
public enum AutomationRate {

    A_RATE("a-rate"),
    K_RATE("k-rate"),
    ;

    private String value;

    AutomationRate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AutomationRate findByValue(String value) {
        for(AutomationRate val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}