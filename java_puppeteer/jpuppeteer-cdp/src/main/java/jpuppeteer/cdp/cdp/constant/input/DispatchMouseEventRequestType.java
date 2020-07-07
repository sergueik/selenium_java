package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum DispatchMouseEventRequestType {

    MOUSEPRESSED("mousePressed"),
    MOUSERELEASED("mouseReleased"),
    MOUSEMOVED("mouseMoved"),
    MOUSEWHEEL("mouseWheel"),
    ;

    private String value;

    DispatchMouseEventRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static DispatchMouseEventRequestType findByValue(String value) {
        for(DispatchMouseEventRequestType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}