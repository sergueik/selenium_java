package jpuppeteer.cdp.cdp.constant.webauthn;

/**
* experimental
*/
public enum AuthenticatorProtocol {

    U2F("u2f"),
    CTAP2("ctap2"),
    ;

    private String value;

    AuthenticatorProtocol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AuthenticatorProtocol findByValue(String value) {
        for(AuthenticatorProtocol val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}