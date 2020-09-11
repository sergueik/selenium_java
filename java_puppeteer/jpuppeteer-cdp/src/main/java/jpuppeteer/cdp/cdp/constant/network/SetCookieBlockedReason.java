package jpuppeteer.cdp.cdp.constant.network;

/**
* Types of reasons why a cookie may not be stored from a response.
*/
public enum SetCookieBlockedReason {

    SECUREONLY("SecureOnly"),
    SAMESITESTRICT("SameSiteStrict"),
    SAMESITELAX("SameSiteLax"),
    SAMESITEUNSPECIFIEDTREATEDASLAX("SameSiteUnspecifiedTreatedAsLax"),
    SAMESITENONEINSECURE("SameSiteNoneInsecure"),
    USERPREFERENCES("UserPreferences"),
    SYNTAXERROR("SyntaxError"),
    SCHEMENOTSUPPORTED("SchemeNotSupported"),
    OVERWRITESECURE("OverwriteSecure"),
    INVALIDDOMAIN("InvalidDomain"),
    INVALIDPREFIX("InvalidPrefix"),
    UNKNOWNERROR("UnknownError"),
    ;

    private String value;

    SetCookieBlockedReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetCookieBlockedReason findByValue(String value) {
        for(SetCookieBlockedReason val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}