package jpuppeteer.cdp.cdp.constant.browser;

/**
*/
public enum PermissionSetting {

    GRANTED("granted"),
    DENIED("denied"),
    PROMPT("prompt"),
    ;

    private String value;

    PermissionSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PermissionSetting findByValue(String value) {
        for(PermissionSetting val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}