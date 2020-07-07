package jpuppeteer.cdp.cdp.constant.accessibility;

/**
* Enum of possible property sources.
* experimental
*/
public enum AXValueSourceType {

    ATTRIBUTE("attribute"),
    IMPLICIT("implicit"),
    STYLE("style"),
    CONTENTS("contents"),
    PLACEHOLDER("placeholder"),
    RELATEDELEMENT("relatedElement"),
    ;

    private String value;

    AXValueSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static AXValueSourceType findByValue(String value) {
        for(AXValueSourceType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}