package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum ObjectPreviewType {

    OBJECT("object"),
    FUNCTION("function"),
    UNDEFINED("undefined"),
    STRING("string"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    SYMBOL("symbol"),
    BIGINT("bigint"),
    ;

    private String value;

    ObjectPreviewType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ObjectPreviewType findByValue(String value) {
        for(ObjectPreviewType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}