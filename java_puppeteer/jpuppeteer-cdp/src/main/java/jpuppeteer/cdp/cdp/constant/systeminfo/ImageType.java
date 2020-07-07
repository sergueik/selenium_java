package jpuppeteer.cdp.cdp.constant.systeminfo;

/**
* Image format of a given image.
* experimental
*/
public enum ImageType {

    JPEG("jpeg"),
    WEBP("webp"),
    UNKNOWN("unknown"),
    ;

    private String value;

    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ImageType findByValue(String value) {
        for(ImageType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}