package jpuppeteer.cdp.cdp.constant.overlay;

/**
* experimental
*/
public enum InspectMode {

    SEARCHFORNODE("searchForNode"),
    SEARCHFORUASHADOWDOM("searchForUAShadowDOM"),
    CAPTUREAREASCREENSHOT("captureAreaScreenshot"),
    SHOWDISTANCES("showDistances"),
    NONE("none"),
    ;

    private String value;

    InspectMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static InspectMode findByValue(String value) {
        for(InspectMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}