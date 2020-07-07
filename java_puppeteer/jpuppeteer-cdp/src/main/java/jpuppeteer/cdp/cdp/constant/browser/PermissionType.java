package jpuppeteer.cdp.cdp.constant.browser;

/**
*/
public enum PermissionType {

    ACCESSIBILITYEVENTS("accessibilityEvents"),
    AUDIOCAPTURE("audioCapture"),
    BACKGROUNDSYNC("backgroundSync"),
    BACKGROUNDFETCH("backgroundFetch"),
    CLIPBOARDREADWRITE("clipboardReadWrite"),
    CLIPBOARDSANITIZEDWRITE("clipboardSanitizedWrite"),
    DURABLESTORAGE("durableStorage"),
    FLASH("flash"),
    GEOLOCATION("geolocation"),
    MIDI("midi"),
    MIDISYSEX("midiSysex"),
    NFC("nfc"),
    NOTIFICATIONS("notifications"),
    PAYMENTHANDLER("paymentHandler"),
    PERIODICBACKGROUNDSYNC("periodicBackgroundSync"),
    PROTECTEDMEDIAIDENTIFIER("protectedMediaIdentifier"),
    SENSORS("sensors"),
    VIDEOCAPTURE("videoCapture"),
    IDLEDETECTION("idleDetection"),
    WAKELOCKSCREEN("wakeLockScreen"),
    WAKELOCKSYSTEM("wakeLockSystem"),
    ;

    private String value;

    PermissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static PermissionType findByValue(String value) {
        for(PermissionType val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}