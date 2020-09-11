package jpuppeteer.cdp.cdp.constant.audits;

/**
* experimental
*/
public enum GetEncodedResponseRequestEncoding {

    WEBP("webp"),
    JPEG("jpeg"),
    PNG("png"),
    ;

    private String value;

    GetEncodedResponseRequestEncoding(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static GetEncodedResponseRequestEncoding findByValue(String value) {
        for(GetEncodedResponseRequestEncoding val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}