package jpuppeteer.cdp.cdp.constant.serviceworker;

/**
* experimental
*/
public enum ServiceWorkerVersionRunningStatus {

    STOPPED("stopped"),
    STARTING("starting"),
    RUNNING("running"),
    STOPPING("stopping"),
    ;

    private String value;

    ServiceWorkerVersionRunningStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ServiceWorkerVersionRunningStatus findByValue(String value) {
        for(ServiceWorkerVersionRunningStatus val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}