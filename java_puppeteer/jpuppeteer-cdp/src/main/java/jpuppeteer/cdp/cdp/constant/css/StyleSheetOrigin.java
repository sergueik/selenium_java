package jpuppeteer.cdp.cdp.constant.css;

/**
* Stylesheet type: "injected" for stylesheets injected via extension, "user-agent" for user-agent stylesheets, "inspector" for stylesheets created by the inspector (i.e. those holding the "via inspector" rules), "regular" for regular stylesheets.
* experimental
*/
public enum StyleSheetOrigin {

    INJECTED("injected"),
    USER_AGENT("user-agent"),
    INSPECTOR("inspector"),
    REGULAR("regular"),
    ;

    private String value;

    StyleSheetOrigin(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StyleSheetOrigin findByValue(String value) {
        for(StyleSheetOrigin val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}