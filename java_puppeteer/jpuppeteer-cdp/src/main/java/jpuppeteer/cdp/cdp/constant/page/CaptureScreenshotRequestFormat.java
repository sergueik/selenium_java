package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum CaptureScreenshotRequestFormat {

    JPEG("jpeg"),
    PNG("png"),
    ;

    private String value;

    CaptureScreenshotRequestFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CaptureScreenshotRequestFormat findByValue(String value) {
        for(CaptureScreenshotRequestFormat val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}