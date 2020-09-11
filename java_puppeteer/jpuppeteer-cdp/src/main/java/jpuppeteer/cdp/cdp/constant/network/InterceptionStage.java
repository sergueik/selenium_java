package jpuppeteer.cdp.cdp.constant.network;

/**
* Stages of the interception to begin intercepting. Request will intercept before the request is sent. Response will intercept after the response is received.
*/
public enum InterceptionStage {

    REQUEST("Request"),
    HEADERSRECEIVED("HeadersReceived"),
    ;

    private String value;

    InterceptionStage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static InterceptionStage findByValue(String value) {
        for(InterceptionStage val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}