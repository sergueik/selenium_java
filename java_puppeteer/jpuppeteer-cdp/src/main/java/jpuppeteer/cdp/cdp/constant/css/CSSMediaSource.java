package jpuppeteer.cdp.cdp.constant.css;

/**
* experimental
*/
public enum CSSMediaSource {

    MEDIARULE("mediaRule"),
    IMPORTRULE("importRule"),
    LINKEDSHEET("linkedSheet"),
    INLINESHEET("inlineSheet"),
    ;

    private String value;

    CSSMediaSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CSSMediaSource findByValue(String value) {
        for(CSSMediaSource val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}