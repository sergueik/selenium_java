package jpuppeteer.cdp.cdp.constant.indexeddb;

/**
* experimental
*/
public enum KeyPathType {

    NULL("null"),
    STRING("string"),
    ARRAY("array"),
    ;

    private String value;

    KeyPathType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static KeyPathType findByValue(String value) {
        for(KeyPathType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}