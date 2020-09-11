package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum MouseButton {

    NONE("none"),
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right"),
    BACK("back"),
    FORWARD("forward"),
    ;

    private String value;

    MouseButton(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static MouseButton findByValue(String value) {
        for(MouseButton val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}