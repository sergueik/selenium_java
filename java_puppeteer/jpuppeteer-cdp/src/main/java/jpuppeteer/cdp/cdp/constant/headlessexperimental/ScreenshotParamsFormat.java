package jpuppeteer.cdp.cdp.constant.headlessexperimental;

/**
* experimental
*/
public enum ScreenshotParamsFormat {

    JPEG("jpeg"),
    PNG("png"),
    ;

    private String value;

    ScreenshotParamsFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ScreenshotParamsFormat findByValue(String value) {
        for(ScreenshotParamsFormat val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}