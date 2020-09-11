package jpuppeteer.cdp.cdp.constant.network;

/**
* Resource type as it was perceived by the rendering engine.
*/
public enum ResourceType {

    DOCUMENT("Document"),
    STYLESHEET("Stylesheet"),
    IMAGE("Image"),
    MEDIA("Media"),
    FONT("Font"),
    SCRIPT("Script"),
    TEXTTRACK("TextTrack"),
    XHR("XHR"),
    FETCH("Fetch"),
    EVENTSOURCE("EventSource"),
    WEBSOCKET("WebSocket"),
    MANIFEST("Manifest"),
    SIGNEDEXCHANGE("SignedExchange"),
    PING("Ping"),
    CSPVIOLATIONREPORT("CSPViolationReport"),
    OTHER("Other"),
    ;

    private String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ResourceType findByValue(String value) {
        for(ResourceType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}