package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum DispatchKeyEventRequestType {

    KEYDOWN("keyDown"),
    KEYUP("keyUp"),
    RAWKEYDOWN("rawKeyDown"),
    CHAR("char"),
    ;

    private String value;

    DispatchKeyEventRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static DispatchKeyEventRequestType findByValue(String value) {
        for(DispatchKeyEventRequestType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}