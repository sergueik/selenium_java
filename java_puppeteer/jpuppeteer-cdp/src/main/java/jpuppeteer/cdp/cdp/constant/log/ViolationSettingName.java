package jpuppeteer.cdp.cdp.constant.log;

/**
*/
public enum ViolationSettingName {

    LONGTASK("longTask"),
    LONGLAYOUT("longLayout"),
    BLOCKEDEVENT("blockedEvent"),
    BLOCKEDPARSER("blockedParser"),
    DISCOURAGEDAPIUSE("discouragedAPIUse"),
    HANDLER("handler"),
    RECURRINGHANDLER("recurringHandler"),
    ;

    private String value;

    ViolationSettingName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ViolationSettingName findByValue(String value) {
        for(ViolationSettingName val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}