package jpuppeteer.cdp.cdp.constant.dom;

/**
* Pseudo element type.
*/
public enum PseudoType {

    FIRST_LINE("first-line"),
    FIRST_LETTER("first-letter"),
    BEFORE("before"),
    AFTER("after"),
    MARKER("marker"),
    BACKDROP("backdrop"),
    SELECTION("selection"),
    FIRST_LINE_INHERITED("first-line-inherited"),
    SCROLLBAR("scrollbar"),
    SCROLLBAR_THUMB("scrollbar-thumb"),
    SCROLLBAR_BUTTON("scrollbar-button"),
    SCROLLBAR_TRACK("scrollbar-track"),
    SCROLLBAR_TRACK_PIECE("scrollbar-track-piece"),
    SCROLLBAR_CORNER("scrollbar-corner"),
    RESIZER("resizer"),
    INPUT_LIST_BUTTON("input-list-button"),
    ;

    private String value;

    PseudoType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PseudoType findByValue(String value) {
        for(PseudoType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}