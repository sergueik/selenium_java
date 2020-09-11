package jpuppeteer.cdp.cdp.constant.emulation;

/**
*/
public enum ScreenOrientationType {

    PORTRAITPRIMARY("portraitPrimary"),
    PORTRAITSECONDARY("portraitSecondary"),
    LANDSCAPEPRIMARY("landscapePrimary"),
    LANDSCAPESECONDARY("landscapeSecondary"),
    ;

    private String value;

    ScreenOrientationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ScreenOrientationType findByValue(String value) {
        for(ScreenOrientationType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}