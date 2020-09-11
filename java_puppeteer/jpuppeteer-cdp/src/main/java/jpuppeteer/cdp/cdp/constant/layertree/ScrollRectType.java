package jpuppeteer.cdp.cdp.constant.layertree;

/**
* experimental
*/
public enum ScrollRectType {

    REPAINTSONSCROLL("RepaintsOnScroll"),
    TOUCHEVENTHANDLER("TouchEventHandler"),
    WHEELEVENTHANDLER("WheelEventHandler"),
    ;

    private String value;

    ScrollRectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ScrollRectType findByValue(String value) {
        for(ScrollRectType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}