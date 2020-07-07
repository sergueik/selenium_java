package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum EmulateTouchFromMouseEventRequestType {

    MOUSEPRESSED("mousePressed"),
    MOUSERELEASED("mouseReleased"),
    MOUSEMOVED("mouseMoved"),
    MOUSEWHEEL("mouseWheel"),
    ;

    private String value;

    EmulateTouchFromMouseEventRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static EmulateTouchFromMouseEventRequestType findByValue(String value) {
        for(EmulateTouchFromMouseEventRequestType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}