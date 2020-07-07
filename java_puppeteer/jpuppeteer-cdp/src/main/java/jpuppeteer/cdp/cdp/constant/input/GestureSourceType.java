package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum GestureSourceType {

    DEFAULT("default"),
    TOUCH("touch"),
    MOUSE("mouse"),
    ;

    private String value;

    GestureSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static GestureSourceType findByValue(String value) {
        for(GestureSourceType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}