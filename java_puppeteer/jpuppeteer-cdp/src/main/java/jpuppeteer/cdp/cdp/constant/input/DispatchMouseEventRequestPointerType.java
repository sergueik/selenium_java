package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum DispatchMouseEventRequestPointerType {

    MOUSE("mouse"),
    PEN("pen"),
    ;

    private String value;

    DispatchMouseEventRequestPointerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static DispatchMouseEventRequestPointerType findByValue(String value) {
        for(DispatchMouseEventRequestPointerType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}