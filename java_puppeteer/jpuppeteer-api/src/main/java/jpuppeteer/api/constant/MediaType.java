package jpuppeteer.api.constant;

public enum MediaType {

    SCREEN("screen"),
    PRINT("print"),
    NULL("null")
    ;

    private String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
