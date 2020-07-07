package jpuppeteer.cdp.cdp.constant.tracing;

/**
* Compression type to use for traces returned via streams.
* experimental
*/
public enum StreamCompression {

    NONE("none"),
    GZIP("gzip"),
    ;

    private String value;

    StreamCompression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StreamCompression findByValue(String value) {
        for(StreamCompression val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}