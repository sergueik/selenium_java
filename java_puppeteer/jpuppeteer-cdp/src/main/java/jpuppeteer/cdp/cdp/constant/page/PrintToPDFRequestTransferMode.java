package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum PrintToPDFRequestTransferMode {

    RETURNASBASE64("ReturnAsBase64"),
    RETURNASSTREAM("ReturnAsStream"),
    ;

    private String value;

    PrintToPDFRequestTransferMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PrintToPDFRequestTransferMode findByValue(String value) {
        for(PrintToPDFRequestTransferMode val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}