package jpuppeteer.api.constant;

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
