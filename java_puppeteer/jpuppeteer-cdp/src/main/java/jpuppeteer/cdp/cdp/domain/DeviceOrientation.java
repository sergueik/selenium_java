package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class DeviceOrientation {

    private jpuppeteer.cdp.CDPSession session;

    public DeviceOrientation(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Clears the overridden Device Orientation.
    * experimental
    */
    public void clearDeviceOrientationOverride(int timeout) throws Exception {
        session.send("DeviceOrientation.clearDeviceOrientationOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearDeviceOrientationOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DeviceOrientation.clearDeviceOrientationOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the Device Orientation.
    * experimental
    */
    public void setDeviceOrientationOverride(jpuppeteer.cdp.cdp.entity.deviceorientation.SetDeviceOrientationOverrideRequest request, int timeout) throws Exception {
        session.send("DeviceOrientation.setDeviceOrientationOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDeviceOrientationOverride(jpuppeteer.cdp.cdp.entity.deviceorientation.SetDeviceOrientationOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DeviceOrientation.setDeviceOrientationOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}