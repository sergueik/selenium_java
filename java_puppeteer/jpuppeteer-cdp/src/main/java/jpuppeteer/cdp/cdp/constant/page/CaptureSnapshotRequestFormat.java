package jpuppeteer.cdp.cdp.constant.page;

/**
*/
public enum CaptureSnapshotRequestFormat {

    MHTML("mhtml"),
    ;

    private String value;

    CaptureSnapshotRequestFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static CaptureSnapshotRequestFormat findByValue(String value) {
        for(CaptureSnapshotRequestFormat val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}