package jpuppeteer.cdp.cdp.constant.webaudio;

/**
* Enum of BaseAudioContext types
* experimental
*/
public enum ContextType {

    REALTIME("realtime"),
    OFFLINE("offline"),
    ;

    private String value;

    ContextType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ContextType findByValue(String value) {
        for(ContextType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}