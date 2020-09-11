package jpuppeteer.cdp.cdp.constant.network;

/**
* Field type for a signed exchange related error.
*/
public enum SignedExchangeErrorField {

    SIGNATURESIG("signatureSig"),
    SIGNATUREINTEGRITY("signatureIntegrity"),
    SIGNATURECERTURL("signatureCertUrl"),
    SIGNATURECERTSHA256("signatureCertSha256"),
    SIGNATUREVALIDITYURL("signatureValidityUrl"),
    SIGNATURETIMESTAMPS("signatureTimestamps"),
    ;

    private String value;

    SignedExchangeErrorField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SignedExchangeErrorField findByValue(String value) {
        for(SignedExchangeErrorField val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}