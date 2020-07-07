package jpuppeteer.cdp.cdp.constant.webauthn;

/**
* experimental
*/
public enum AuthenticatorTransport {

    USB("usb"),
    NFC("nfc"),
    BLE("ble"),
    CABLE("cable"),
    INTERNAL("internal"),
    ;

    private String value;

    AuthenticatorTransport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AuthenticatorTransport findByValue(String value) {
        for(AuthenticatorTransport val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}