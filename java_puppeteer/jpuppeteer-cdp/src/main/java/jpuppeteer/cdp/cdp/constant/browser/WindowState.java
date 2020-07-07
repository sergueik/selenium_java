package jpuppeteer.cdp.cdp.constant.browser;

/**
* The state of the browser window.
*/
public enum WindowState {

    NORMAL("normal"),
    MINIMIZED("minimized"),
    MAXIMIZED("maximized"),
    FULLSCREEN("fullscreen"),
    ;

    private String value;

    WindowState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static WindowState findByValue(String value) {
        for(WindowState val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}