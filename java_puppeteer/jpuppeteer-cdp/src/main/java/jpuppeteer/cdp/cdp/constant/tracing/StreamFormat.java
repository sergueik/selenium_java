package jpuppeteer.cdp.cdp.constant.tracing;

/**
* Data format of a trace. Can be either the legacy JSON format or the protocol buffer format. Note that the JSON format will be deprecated soon.
* experimental
*/
public enum StreamFormat {

    JSON("json"),
    PROTO("proto"),
    ;

    private String value;

    StreamFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StreamFormat findByValue(String value) {
        for(StreamFormat val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}