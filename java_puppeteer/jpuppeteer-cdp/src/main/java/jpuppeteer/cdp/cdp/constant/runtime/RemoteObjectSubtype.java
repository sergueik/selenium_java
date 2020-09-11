package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum RemoteObjectSubtype {

    ARRAY("array"),
    NULL("null"),
    NODE("node"),
    REGEXP("regexp"),
    DATE("date"),
    MAP("map"),
    SET("set"),
    WEAKMAP("weakmap"),
    WEAKSET("weakset"),
    ITERATOR("iterator"),
    GENERATOR("generator"),
    ERROR("error"),
    PROXY("proxy"),
    PROMISE("promise"),
    TYPEDARRAY("typedarray"),
    ARRAYBUFFER("arraybuffer"),
    DATAVIEW("dataview"),
    ;

    private String value;

    RemoteObjectSubtype(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static RemoteObjectSubtype findByValue(String value) {
        for(RemoteObjectSubtype val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}