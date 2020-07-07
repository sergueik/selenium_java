package jpuppeteer.cdp.cdp.constant.network;

/**
* Loading priority of a resource request.
*/
public enum ResourcePriority {

    VERYLOW("VeryLow"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    VERYHIGH("VeryHigh"),
    ;

    private String value;

    ResourcePriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ResourcePriority findByValue(String value) {
        for(ResourcePriority val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}