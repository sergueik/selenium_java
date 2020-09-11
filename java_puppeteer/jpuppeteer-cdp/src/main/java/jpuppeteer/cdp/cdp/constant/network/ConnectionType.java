package jpuppeteer.cdp.cdp.constant.network;

/**
* The underlying connection technology that the browser is supposedly using.
*/
public enum ConnectionType {

    NONE("none"),
    CELLULAR2G("cellular2g"),
    CELLULAR3G("cellular3g"),
    CELLULAR4G("cellular4g"),
    BLUETOOTH("bluetooth"),
    ETHERNET("ethernet"),
    WIFI("wifi"),
    WIMAX("wimax"),
    OTHER("other"),
    ;

    private String value;

    ConnectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static ConnectionType findByValue(String value) {
        for(ConnectionType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}