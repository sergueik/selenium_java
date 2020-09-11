package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum StartScreencastRequestFormat {

    JPEG("jpeg"),
    PNG("png"),
    ;

    private String value;

    StartScreencastRequestFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StartScreencastRequestFormat findByValue(String value) {
        for(StartScreencastRequestFormat val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}