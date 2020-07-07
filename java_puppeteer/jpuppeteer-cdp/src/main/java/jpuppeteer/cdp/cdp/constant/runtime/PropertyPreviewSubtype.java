package jpuppeteer.cdp.cdp.constant.runtime;

/**
*/
public enum PropertyPreviewSubtype {

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
    ;

    private String value;

    PropertyPreviewSubtype(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PropertyPreviewSubtype findByValue(String value) {
        for(PropertyPreviewSubtype val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}