package jpuppeteer.cdp.cdp.constant.network;

/**
* Network level fetch failure reason.
*/
public enum ErrorReason {

    FAILED("Failed"),
    ABORTED("Aborted"),
    TIMEDOUT("TimedOut"),
    ACCESSDENIED("AccessDenied"),
    CONNECTIONCLOSED("ConnectionClosed"),
    CONNECTIONRESET("ConnectionReset"),
    CONNECTIONREFUSED("ConnectionRefused"),
    CONNECTIONABORTED("ConnectionAborted"),
    CONNECTIONFAILED("ConnectionFailed"),
    NAMENOTRESOLVED("NameNotResolved"),
    INTERNETDISCONNECTED("InternetDisconnected"),
    ADDRESSUNREACHABLE("AddressUnreachable"),
    BLOCKEDBYCLIENT("BlockedByClient"),
    BLOCKEDBYRESPONSE("BlockedByResponse"),
    ;

    private String value;

    ErrorReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ErrorReason findByValue(String value) {
        for(ErrorReason val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}