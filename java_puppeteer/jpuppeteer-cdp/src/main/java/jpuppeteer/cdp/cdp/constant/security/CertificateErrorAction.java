package jpuppeteer.cdp.cdp.constant.security;

/**
* The action to take when a certificate error occurs. continue will continue processing the request and cancel will cancel the request.
*/
public enum CertificateErrorAction {

    CONTINUE("continue"),
    CANCEL("cancel"),
    ;

    private String value;

    CertificateErrorAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CertificateErrorAction findByValue(String value) {
        for(CertificateErrorAction val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}