package jpuppeteer.cdp.cdp.constant.fetch;

/**
* Stages of the request to handle. Request will intercept before the request is sent. Response will intercept after the response is received (but before response body is received.
* experimental
*/
public enum RequestStage {

    REQUEST("Request"),
    RESPONSE("Response"),
    ;

    private String value;

    RequestStage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static RequestStage findByValue(String value) {
        for(RequestStage val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}