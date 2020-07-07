package jpuppeteer.cdp.cdp.constant.cachestorage;

/**
* type of HTTP response cached
* experimental
*/
public enum CachedResponseType {

    BASIC("basic"),
    CORS("cors"),
    DEFAULT("default"),
    ERROR("error"),
    OPAQUERESPONSE("opaqueResponse"),
    OPAQUEREDIRECT("opaqueRedirect"),
    ;

    private String value;

    CachedResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CachedResponseType findByValue(String value) {
        for(CachedResponseType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}