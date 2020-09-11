package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum PropertyPreviewType {

    OBJECT("object"),
    FUNCTION("function"),
    UNDEFINED("undefined"),
    STRING("string"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    SYMBOL("symbol"),
    ACCESSOR("accessor"),
    BIGINT("bigint"),
    ;

    private String value;

    PropertyPreviewType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PropertyPreviewType findByValue(String value) {
        for(PropertyPreviewType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}