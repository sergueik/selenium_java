package jpuppeteer.cdp.cdp.constant.media;

/**
* Break out events into different types
* experimental
*/
public enum PlayerEventType {

    PLAYBACKEVENT("playbackEvent"),
    SYSTEMEVENT("systemEvent"),
    MESSAGEEVENT("messageEvent"),
    ;

    private String value;

    PlayerEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PlayerEventType findByValue(String value) {
        for(PlayerEventType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}