package jpuppeteer.cdp.cdp.constant.security;

/**
*/
public enum SafetyTipStatus {

    BADREPUTATION("badReputation"),
    LOOKALIKE("lookalike"),
    ;

    private String value;

    SafetyTipStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SafetyTipStatus findByValue(String value) {
        for(SafetyTipStatus val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}