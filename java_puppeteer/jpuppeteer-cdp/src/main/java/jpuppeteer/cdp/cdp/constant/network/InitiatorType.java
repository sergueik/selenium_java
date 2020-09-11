package jpuppeteer.cdp.cdp.constant.network;

/**
*/
public enum InitiatorType {

    PARSER("parser"),
    SCRIPT("script"),
    PRELOAD("preload"),
    SIGNEDEXCHANGE("SignedExchange"),
    OTHER("other"),
    ;

    private String value;

    InitiatorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static InitiatorType findByValue(String value) {
        for(InitiatorType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}