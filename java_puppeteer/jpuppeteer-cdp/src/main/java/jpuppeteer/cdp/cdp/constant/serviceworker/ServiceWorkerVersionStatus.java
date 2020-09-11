package jpuppeteer.cdp.cdp.constant.serviceworker;

/**
* experimental
*/
public enum ServiceWorkerVersionStatus {

    NEW("new"),
    INSTALLING("installing"),
    INSTALLED("installed"),
    ACTIVATING("activating"),
    ACTIVATED("activated"),
    REDUNDANT("redundant"),
    ;

    private String value;

    ServiceWorkerVersionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ServiceWorkerVersionStatus findByValue(String value) {
        for(ServiceWorkerVersionStatus val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}