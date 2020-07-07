package jpuppeteer.cdp.cdp.constant.storage;

/**
* Enum of possible storage types.
* experimental
*/
public enum StorageType {

    APPCACHE("appcache"),
    COOKIES("cookies"),
    FILE_SYSTEMS("file_systems"),
    INDEXEDDB("indexeddb"),
    LOCAL_STORAGE("local_storage"),
    SHADER_CACHE("shader_cache"),
    WEBSQL("websql"),
    SERVICE_WORKERS("service_workers"),
    CACHE_STORAGE("cache_storage"),
    ALL("all"),
    OTHER("other"),
    ;

    private String value;

    StorageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static StorageType findByValue(String value) {
        for(StorageType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}