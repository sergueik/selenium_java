package jpuppeteer.api.constant;

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
        //@TODO 此处可以改为静态的条件判断, 以提高性能
        for(ResourceType type : ResourceType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return OTHER;
    }

}
