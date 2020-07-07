package jpuppeteer.cdp.cdp.constant.animation;

/**
* experimental
*/
public enum AnimationType {

    CSSTRANSITION("CSSTransition"),
    CSSANIMATION("CSSAnimation"),
    WEBANIMATION("WebAnimation"),
    ;

    private String value;

    AnimationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AnimationType findByValue(String value) {
        for(AnimationType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}