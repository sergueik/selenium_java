package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum FileChooserOpenedEventMode {

    SELECTSINGLE("selectSingle"),
    SELECTMULTIPLE("selectMultiple"),
    ;

    private String value;

    FileChooserOpenedEventMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static FileChooserOpenedEventMode findByValue(String value) {
        for(FileChooserOpenedEventMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}