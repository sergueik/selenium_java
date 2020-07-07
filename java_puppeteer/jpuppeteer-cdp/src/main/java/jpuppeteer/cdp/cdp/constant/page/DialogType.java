package jpuppeteer.cdp.cdp.constant.page;

/**
* Javascript dialog type.
*/
public enum DialogType {

    ALERT("alert"),
    CONFIRM("confirm"),
    PROMPT("prompt"),
    BEFOREUNLOAD("beforeunload"),
    ;

    private String value;

    DialogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static DialogType findByValue(String value) {
        for(DialogType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}