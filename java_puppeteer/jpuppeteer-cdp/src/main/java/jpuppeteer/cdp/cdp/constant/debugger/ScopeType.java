package jpuppeteer.cdp.cdp.constant.debugger;

/**
*/
public enum ScopeType {

    GLOBAL("global"),
    LOCAL("local"),
    WITH("with"),
    CLOSURE("closure"),
    CATCH("catch"),
    BLOCK("block"),
    SCRIPT("script"),
    EVAL("eval"),
    MODULE("module"),
    ;

    private String value;

    ScopeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ScopeType findByValue(String value) {
        for(ScopeType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}