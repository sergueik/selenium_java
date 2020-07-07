package jpuppeteer.cdp.cdp.constant.webaudio;

/**
* Enum of AudioNode::ChannelInterpretation from the spec
* experimental
*/
public enum ChannelInterpretation {

    DISCRETE("discrete"),
    SPEAKERS("speakers"),
    ;

    private String value;

    ChannelInterpretation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ChannelInterpretation findByValue(String value) {
        for(ChannelInterpretation val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}