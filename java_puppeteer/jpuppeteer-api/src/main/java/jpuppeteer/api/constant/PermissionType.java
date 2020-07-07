package jpuppeteer.api.constant;

public enum PermissionType {

    ACCESSIBILITYEVENTS("accessibilityEvents"),
    AUDIOCAPTURE("audioCapture"),
    BACKGROUNDSYNC("backgroundSync"),
    BACKGROUNDFETCH("backgroundFetch"),
    CLIPBOARDREAD("clipboardRead"),
    CLIPBOARDWRITE("clipboardWrite"),
    DURABLESTORAGE("durableStorage"),
    FLASH("flash"),
    GEOLOCATION("geolocation"),
    MIDI("midi"),
    MIDISYSEX("midiSysex"),
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
}
