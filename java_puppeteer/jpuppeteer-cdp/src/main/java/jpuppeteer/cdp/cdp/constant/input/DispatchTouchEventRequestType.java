package jpuppeteer.cdp.cdp.constant.input;

/**
*/
public enum DispatchTouchEventRequestType {

    TOUCHSTART("touchStart"),
    TOUCHEND("touchEnd"),
    TOUCHMOVE("touchMove"),
    TOUCHCANCEL("touchCancel"),
    ;

    private String value;

    DispatchTouchEventRequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static DispatchTouchEventRequestType findByValue(String value) {
        for(DispatchTouchEventRequestType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}