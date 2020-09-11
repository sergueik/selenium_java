package jpuppeteer.cdp.constant;

public enum TargetType {

    PAGE("page"),
    BACKGROUND_PAGE("background_page"),
    WORKER("worker"),
    BROWSER("browser"),
    OTHER("other"),
    ;

    private String value;

    TargetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TargetType findByValue(String value) {
        for(TargetType type : TargetType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
