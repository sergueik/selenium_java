package jpuppeteer.cdp.cdp.constant.network;

/**
* Whether the request complied with Certificate Transparency policy.
*/
public enum CertificateTransparencyCompliance {

    UNKNOWN("unknown"),
    NOT_COMPLIANT("not-compliant"),
    COMPLIANT("compliant"),
    ;

    private String value;

    CertificateTransparencyCompliance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CertificateTransparencyCompliance findByValue(String value) {
        for(CertificateTransparencyCompliance val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}