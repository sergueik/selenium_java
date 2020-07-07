package jpuppeteer.cdp.cdp.constant.webaudio;

/**
* Enum of AudioNode::ChannelCountMode from the spec
* experimental
*/
public enum ChannelCountMode {

    CLAMPED_MAX("clamped-max"),
    EXPLICIT("explicit"),
    MAX("max"),
    ;

    private String value;

    ChannelCountMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ChannelCountMode findByValue(String value) {
        for(ChannelCountMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}