package jpuppeteer.cdp.cdp.constant.indexeddb;

/**
* experimental
*/
public enum KeyType {

    NUMBER("number"),
    STRING("string"),
    DATE("date"),
    ARRAY("array"),
    ;

    private String value;

    KeyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static KeyType findByValue(String value) {
        for(KeyType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}