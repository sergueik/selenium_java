package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum RemoteObjectType {

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

    RemoteObjectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static RemoteObjectType findByValue(String value) {
        for(RemoteObjectType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}